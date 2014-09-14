package com.nn.ishop.client.gui.purchase;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultCellEditor;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.nn.ishop.client.CActionEvent;
import com.nn.ishop.client.CActionListener;
import com.nn.ishop.client.gui.AbstractGUIManager;
import com.nn.ishop.client.gui.GUIActionEvent;
import com.nn.ishop.client.gui.GUIActionEvent.GUIActionType;
import com.nn.ishop.client.gui.GUIActionListener;
import com.nn.ishop.client.gui.GUIManager;
import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.common.ShowGRNHistoryManager;
import com.nn.ishop.client.gui.components.CButton2;
import com.nn.ishop.client.gui.components.CComboBox;
import com.nn.ishop.client.gui.components.CPaging;
import com.nn.ishop.client.gui.components.CTable;
import com.nn.ishop.client.gui.components.CTableModel;
import com.nn.ishop.client.gui.components.CTwoModePanel;
import com.nn.ishop.client.gui.components.CWhitePanel;
import com.nn.ishop.client.gui.components.DoublesCellEditor;
import com.nn.ishop.client.gui.components.IntegerCellEditor;
import com.nn.ishop.client.gui.dialogs.SystemMessageDialog;
import com.nn.ishop.client.gui.helper.DialogHelper;
import com.nn.ishop.client.gui.helper.TableUtil;
import com.nn.ishop.client.gui.purchase.POInputItemGUIManager.InputItem;
import com.nn.ishop.client.logic.CommonLogic;
import com.nn.ishop.client.logic.admin.CustomerLogic;
import com.nn.ishop.client.logic.admin.ProductLogic;
import com.nn.ishop.client.logic.admin.PurchaseGrnLogic;
import com.nn.ishop.client.logic.admin.PurchaseLogic;
import com.nn.ishop.client.logic.transaction.DailyTranxLogic;
import com.nn.ishop.client.util.Library;
import com.nn.ishop.client.util.Util;
import com.nn.ishop.server.dto.AbstractIshopEntity;
import com.nn.ishop.server.dto.bstranx.DailyTransaction;
import com.nn.ishop.server.dto.customer.Customer;
import com.nn.ishop.server.dto.exrate.Currency;
import com.nn.ishop.server.dto.grn.PurchaseGrn;
import com.nn.ishop.server.dto.grn.PurchaseGrnDetail;
import com.nn.ishop.server.dto.pricelist.PriceList;
import com.nn.ishop.server.dto.product.ProductItem;
import com.nn.ishop.server.dto.purchase.PurchasingPlan;
import com.nn.ishop.server.dto.purchase.PurchasingPlanDetail;
import com.nn.ishop.server.dto.warehouse.ImportInvoke;
import com.nn.ishop.server.util.Formatter;

public class GRNMainGUIManager extends AbstractGUIManager implements CActionListener, GUIActionListener, ListSelectionListener, TableModelListener {

	GRNHeaderGUIManager gmGRNHeaderGUI;
	CWhitePanel pnlGRNHeaderContainer;
	Vector<CActionListener> vctListener = new Vector<CActionListener>();
	static boolean isActived = false;
	static boolean isSaved = false;
	CPaging pagePurchaseTranx;
	DailyTranxLogic logicDailyTranx = DailyTranxLogic.getInstance();
	
	JSplitPane splitGRNTranx;
	CTwoModePanel xpnlTranxTwoModePanel, xpnlDetailTwoModePanel;
	CWhitePanel    pnlPurchaseTranxListPageAtGRNCnt, pnlGRNMainPanel;
	CPaging pageGRNItemTable;
	PurchaseGrnLogic logicPurchaseGRN = PurchaseGrnLogic.getInstance();
	private final int COL_PLAN_QTY=3,COL_INPUT_QTY=4, COL_DAMAGE_QTY=5, COL_TOTAL_QTY=6;
	private final int COL_UNIT_PRICE=7,  COL_CURRENCY=8,COL_DISCOUNT=9, COL_OTHER_DEDUCT=10,COL_VAT_RATE = 11, COL_TOTAL_AMT=12 ;
	DailyTransaction currentTranx = null;
	
	public GRNMainGUIManager(String locale) {
		setLocale(locale);
		init();
	}

	void init() {
		if (getLocale() != null && !getLocale().equals("")) {
			initTemplate(this, "purchase/po/grnMainPage.xml", getLocale());
		} else {
			initTemplate(this, "purchase/po/grnMainPage.xml");
		}
		render();
		gmGRNHeaderGUI = new GRNHeaderGUIManager(getLocale());
		pnlGRNHeaderContainer.add(gmGRNHeaderGUI.getRootComponent());
		bindEventHandlers();
		loadTranxList();
	}

	public void tableChanged(TableModelEvent e) {
	}

	public void valueChanged(ListSelectionEvent e) {

	}

	public void guiActionPerformed(GUIActionEvent action) {
		GUIActionType guideType = action.getAction(); 
		Object srcObj = action.getSource();
		if(guideType.equals( GUIActionType.MINIMIZE_WINDOW)){
			int location = ((Dimension)action.getData()).height;
			if(srcObj.equals(GRNMainGUIManager.class)){
				splitGRNTranx.setDividerLocation(location);
			}else{
				int newLoc = splitGRNTranx.getLeftComponent().getSize().height
						 + splitGRNTranx.getRightComponent().getSize().height
						 - location;
				splitGRNTranx.setDividerLocation(newLoc);
			}
		}
		if(guideType.equals( GUIActionType.MAXIMIZE_WINDOW)){
			splitGRNTranx.resetToPreferredSizes();
		}
	}

	public void addCActionListener(CActionListener al) {
		vctListener.add(al);
	}

	public void removeCActionListener(CActionListener al) {
	}

	public void cActionPerformed(CActionEvent action) {
		try {
			if (action.getAction() == CActionEvent.RELOAD_TRANX_LIST) {
				// Load all transaction with step = GRN
				loadTranxList();
			}else if(action.getAction() == CActionEvent.UPDATE_GRN){				
				//do updating
				newGRN((DailyTransaction)action.getData());
			}if (action.getAction() == CActionEvent.PAGING_ADD_EVENT){
				//Do the same as new button click
			}if (action.getAction() == CActionEvent.PAGING_DELETE_EVENT){
				//Handle delete command from Pagination 
			}if (action.getAction() == CActionEvent.PAGING_EDIT_EVENT){
				//Handle refresh command from pagination
			}if (action.getAction() == CActionEvent.PAGING_REFRESH_EVENT){
				loadTranxList();
			}if (action.getAction() == CActionEvent.PAGING_SAVE_EVENT){
				// Handle save command from pagination
			}if (action.getAction() == CActionEvent.PAGING_SEARCH_EVENT){
				//Handle search command from pagination
			}if (/*action.getAction() == CActionEvent.ADD 
					||*/ action.getAction() == CActionEvent.SAVE 
					|| action.getAction() == CActionEvent.CANCEL){
				fireCActionEvent(action);
				loadTranxList();
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	private void fireCActionEvent(CActionEvent action) {
		for (CActionListener listener : vctListener) {
			listener.cActionPerformed(action);
		}
	}

	private void loadTranxList() {
		try {
			pagePurchaseTranx.clearData();
			List<DailyTransaction> lstData = logicDailyTranx.searchByStep("GOOD_RECEIVE_NOTE");
			if (lstData != null && lstData.size() > 0) {
				TableUtil.addListToTable(pagePurchaseTranx, pagePurchaseTranx.getTable(), lstData);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void bindEventHandlers() {
		addCActionListener(gmGRNHeaderGUI);
		pagePurchaseTranx.addCActionListener(this);
		xpnlDetailTwoModePanel.addGUIActionListener(this);
		xpnlTranxTwoModePanel.addGUIActionListener(this);
		xpnlTranxTwoModePanel.setManagerClazz(GRNMainGUIManager.class);
		xpnlDetailTwoModePanel.setManagerClazz(String.class);
		xpnlTranxTwoModePanel.addContent(pnlPurchaseTranxListPageAtGRNCnt);
		xpnlTranxTwoModePanel.setTitle(Language.getInstance().getString("purchase.tranx.4.grn"));
		xpnlDetailTwoModePanel.setTitle(Language.getInstance().getString("detail"));
		xpnlDetailTwoModePanel.addContent(pnlGRNMainPanel);
		pageGRNItemTable.addCActionListener(this);
		pagePurchaseTranx.getTable().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2){
					newGRN(null);
				}	
				super.mouseClicked(e);
			}
		});
		
		// Mapping key stroke for saving action
		JComponent rootComp = (JComponent)getRootComponent();
		rootComp.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_S,
                java.awt.event.InputEvent.CTRL_DOWN_MASK),
        "saveAction");
		rootComp.getActionMap().put("saveAction", cmdSave);
		// Mapping keys stroke for new action
		rootComp.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_N,
                java.awt.event.InputEvent.CTRL_DOWN_MASK),
        "newAction");
		rootComp.getActionMap().put("newAction", cmdNew);
		// Mapping keys stroke for cancel  action
		rootComp.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_W,
                java.awt.event.InputEvent.CTRL_DOWN_MASK),
        "cmdCancel");
		rootComp.getActionMap().put("cmdCancel", cmdCancel);
		//Customize transaction paging tool bar	
		CButton2 btnHistory = new CButton2(Util.getIcon("button/history.png"));	
		btnHistory.setBackground(Library.DEFAULT_WINDOW_AND_MENU_BACKGROUND);
		btnHistory.setOriBgColor(Library.DEFAULT_WINDOW_AND_MENU_BACKGROUND);		
		
		btnHistory.addActionListener(cmdOpenHistory);
		
		pagePurchaseTranx.toolBar.removeAll();
		pagePurchaseTranx.toolBar.add(btnHistory);
		pagePurchaseTranx.toolBar.add(pagePurchaseTranx.refreshBtn);
		
		pagePurchaseTranx.refreshBtn.addActionListener(cmdRefreshTxList);
		
		pagePurchaseTranx.toolBar.add(pagePurchaseTranx.txtSearchByNameId);
		pagePurchaseTranx.toolBar.add(pagePurchaseTranx.btnSearchButton);
		//Detail 
		pageGRNItemTable.toolBar.removeAll();
		pageGRNItemTable.toolBar.add(pageGRNItemTable.deleteBtn);
		pageGRNItemTable.deleteBtn.addActionListener( REMOVE_GRN_DETAIL_ROW);		
	}
	Action REMOVE_GRN_DETAIL_ROW = new AbstractAction("REMOVE_GRN_ROW") {		
		public void actionPerformed(ActionEvent e) {
			try {
				removeGRNDetailRow();
			} catch (Throwable e2) {
				e2.printStackTrace();
			}			
		}
	};
	void removeGRNDetailRow()throws Throwable{
		if(pageGRNItemTable.getTable().getModel().getRowCount() <=1) return;
		
		CTable  tblGrnDtl = pageGRNItemTable.getTable();
		for (int i=0; i<((CTableModel)tblGrnDtl.getModel()).getRealRowCount();i++){
			Boolean selected = (Boolean)tblGrnDtl.getModel().getValueAt(
					i, 
					0);
			if(selected.booleanValue()){
				pageGRNItemTable.getTable().removeRow2(i);
				buildNewDetailTable((List<GRNMainGUIManager.PPDetailWrapper>) pageGRNItemTable.getTable().getLstData());
				/*TableUtil.addListToTable(pageGRNItemTable, pageGRNItemTable.getTable(), pageGRNItemTable.getTable().getLstData(),
						Arrays.asList(new Integer[]{COL_INPUT_QTY,COL_DAMAGE_QTY,
						 COL_CURRENCY,COL_DISCOUNT,COL_OTHER_DEDUCT,COL_VAT_RATE}));			*/
			}
		}
	}
	void resetData(){
		pageGRNItemTable.clearData();
		((CTable)pageGRNItemTable.getTable()).setLstData(new ArrayList<InputItem>());
		currentTranx = null;
	}
	
	boolean isCLoseablePP(PurchaseGrn beanPgrn){
		boolean ret = true;
		
		Map<String, Long> mapGrnQty = new HashMap<String, Long>();
		PurchaseGrnDetail[] arrDetails = beanPgrn.getGrnDetails();
		for(PurchaseGrnDetail grnDtl:arrDetails){
			mapGrnQty.put(grnDtl.getItem_id(), grnDtl.getReceive_quantity());
		}
		//Iterating item in pp and compare with grn
		Map<String, Long> mapPpQty = new HashMap<String, Long>();
		List<PurchasingPlanDetail>  listPpdtl  = PurchaseLogic.getInstance().getListPurchasingPlanDetail(beanPgrn.getPp_id());
		for (PurchasingPlanDetail ppdtl:listPpdtl){
			if(!mapGrnQty.containsKey(ppdtl.getItem_id()))
				return false;
			if(mapGrnQty.get(ppdtl.getItem_id())<ppdtl.getQuantity())
				return false;
		}
		return ret;
	}
	@SuppressWarnings("unchecked")
	void saveGRN(){
		List<PPDetailWrapper> lstData = (List<PPDetailWrapper>)((CTable) pageGRNItemTable.getTable()).getLstData();
		if (lstData == null) {
			lstData = new ArrayList<PPDetailWrapper>();
		}
		//- Build GRN and GRN Detail
		PurchaseGrn beanPgrn = new PurchaseGrn();
		beanPgrn.setGrn_id(gmGRNHeaderGUI.txtGRNId.getText());
		beanPgrn.setPp_id(gmGRNHeaderGUI.txtPPId.getText());
		beanPgrn.setReceive_date(gmGRNHeaderGUI.dcReceiveDate.getDate().toString());
		beanPgrn.setNote(gmGRNHeaderGUI.txtNote.getText());
		beanPgrn.setTranx_id(gmGRNHeaderGUI.beanCurrentTranx.getId());
		
		PurchaseGrnDetail[] arrDetails = new PurchaseGrnDetail[lstData.size()];
		int idx =0;
		
		for (PPDetailWrapper ppDetailWrapper : lstData) {
			PurchaseGrnDetail newPPGRNDetail = new PurchaseGrnDetail();
			newPPGRNDetail.setGrn_id(gmGRNHeaderGUI.txtGRNId.getText());
			newPPGRNDetail.setItem_id(ppDetailWrapper.getItem().getProductId());
			newPPGRNDetail.setReceive_quantity(ppDetailWrapper.getPp_detail().getReceive_quantity());
			newPPGRNDetail.setCorrupted_quantity(ppDetailWrapper.getPp_detail().getCorrupted_quantity());
			newPPGRNDetail.setPurchasing_price(ppDetailWrapper.getPp_detail().getPurchasing_price());
			newPPGRNDetail.setPurchasing_discount(ppDetailWrapper.getPp_detail().getPurchasing_discount());
			newPPGRNDetail.setVat(ppDetailWrapper.getPp_detail().getVat());
			newPPGRNDetail.setOther_discount(ppDetailWrapper.getPp_detail().getOther_discount());
			newPPGRNDetail.setCurrency_id(ppDetailWrapper.getPp_detail().getCurrency_id());
			arrDetails[idx] = newPPGRNDetail;
			idx++;
		}
		beanPgrn.setGrnDetails(arrDetails);
		//Build logic method to save wrapper data
		beanPgrn =PurchaseGrnLogic.getInstance().insertPurchaseGrn(beanPgrn);
		gmGRNHeaderGUI.beanCurrentTranx.setCurrent_step("STOCK_CARD");
		gmGRNHeaderGUI.beanCurrentTranx.appendVoucher_id(gmGRNHeaderGUI.txtGRNId.getText());
		try {
			//Update transaction phase, just update when receipt all item in Purchase Plan
			if(isCLoseablePP(beanPgrn))
				gmGRNHeaderGUI.logicDailyTranxLogic.update(gmGRNHeaderGUI.beanCurrentTranx);
		} catch (Exception e) {
			e.printStackTrace();
		}
		resetData();
	}
	@Override
	public Object getData(String var) {
		return null;
	}

	@Override
	protected void applyStyles() {

	}

	@Override
	public void update() {

	}

	@Override
	public void updateList() {

	}

	@Override
	protected void checkPermission() {

	}
	
	protected void newGRN(DailyTransaction selectedTranx){
		if (!isActived) {
			try {
				String tranxId;
				if(selectedTranx == null){
					int selectedRow = -1;
					selectedRow = pagePurchaseTranx.getTable().getSelectedRow();
					if(selectedRow == -1){
						int ret = SystemMessageDialog.showConfirmDialog(null, 
								Language.getInstance().getString("confirm.auto.select.tranx"), 
								SystemMessageDialog.SHOW_OK_CANCEL_OPTION);
						if(ret != 0) 
							return;
						else{
							pagePurchaseTranx.getTable().setRowSelectionInterval(0, 0);
							selectedRow = 0;
						}
					}
					CTableModel model = pagePurchaseTranx.getModel();
					tranxId = (String)model.getValueAt(selectedRow, 1);		
					currentTranx = DailyTranxLogic.getInstance().get(tranxId);
				}else{
					currentTranx = selectedTranx;
					tranxId = selectedTranx.getId();
				}
				
				//fire action to announce listener about update
				CActionEvent newAction = new CActionEvent(this, CActionEvent.UPDATE_GRN, currentTranx);
				fireCActionEvent(newAction);
				
				//1. Load purchase plan from transaction ID
				PurchasingPlan pp = PurchaseLogic.getInstance().getPurchasingPlanByTranxId(tranxId);				
				//2. Load purchase plan detail
				List<PurchasingPlanDetail> listPpDt = PurchaseLogic.getInstance().getListPurchasingPlanDetail(pp.getPpId());
				//3. Convert to ppDetailWraper, update currency, ex. rate,...
				List<PPDetailWrapper> listPPDW = new ArrayList<PPDetailWrapper>();
				for(PurchasingPlanDetail ppd:listPpDt){
					PPDetailWrapper ppdw = new PPDetailWrapper();
					PurchaseGrnDetail pp_detail = new PurchaseGrnDetail();
					pp_detail.setCurrency_id("VND");
					pp_detail.setReceive_quantity(ppd.getQuantity());
					pp_detail.setItem_id(ppd.getItem_id());					
					
					ProductItem item = ProductLogic.getInstance().getItem(ppd.getItem_id());
					Customer c = CustomerLogic.getInstance().getCustomer( pp.getProviderId());
					PriceList pl = CommonLogic.getPriceList(item.getProductId(), "VND", c.getCustomer_rank_id());
					if(pl==null){
						SystemMessageDialog.showMessageDialog(null, 
								Language.getInstance().getString("no.price.list.attach.to.customer.type") +c.getCustomer_rank_id(),
								SystemMessageDialog.SHOW_OK_OPTION);
					}
					pp_detail.setPurchasing_price(pl!=null?pl.getPurchase_price():0);
					pp_detail.setPurchasing_discount(pl!=null?pl.getDiscount():0);
					
					ppdw.setCustomer(c);
					ppdw.setItem(item);
					ppdw.setCurrency("VND");				
					double totalAmt = (pl!=null?pl.getPurchase_price():0)*pp_detail.getReceive_quantity();
					ppdw.setTotalAmount(Formatter.num2str(totalAmt));
					ppdw.setTotalQuantity(Formatter.num2str(ppd.getQuantity()));
					
					ppdw.setPlanQty(String.valueOf(ppd.getQuantity()));
					ppdw.setPp_detail(pp_detail);
					listPPDW.add(ppdw);					
				}
				buildNewDetailTable(listPPDW);
			} catch (Throwable e2) {
				e2.printStackTrace();
			}				
		}else{
		}
	}	
	void buildNewDetailTable(List<PPDetailWrapper>  listPPDW){
		try {
			if (!isActived) {
				String tranxId;
				if(currentTranx == null){
					int selectedRow = -1;
					selectedRow = pagePurchaseTranx.getTable().getSelectedRow();
					if (selectedRow == -1) {
						int ret = SystemMessageDialog.showConfirmDialog(
								null,
								Language.getInstance().getString(
										"confirm.auto.select.tranx"),
								SystemMessageDialog.SHOW_OK_CANCEL_OPTION);
						if (ret != 0)
							return;
						else {
							pagePurchaseTranx.getTable().setRowSelectionInterval(0,
									0);
							selectedRow = 0;
						}
					}
					CTableModel model = pagePurchaseTranx.getModel();
					tranxId = (String) model.getValueAt(selectedRow, 1);
				}else{
					tranxId = currentTranx.getId();
				}
				//4. Add to table
				if (listPPDW != null && listPPDW.size() > 0) {
					TableUtil.addListToTable(
							pageGRNItemTable,
							pageGRNItemTable.getTable(),
							listPPDW,
							Arrays.asList(new Integer[] { COL_INPUT_QTY,
									COL_DAMAGE_QTY, COL_CURRENCY, COL_DISCOUNT,
									COL_OTHER_DEDUCT, COL_VAT_RATE }));

					//============================== Input Qty Cell Editor  ==============================//
					final IntegerCellEditor ce = new IntegerCellEditor(0,
							99999999);
					ce.addCellEditorListener(new CellEditorListener() {
						public void editingStopped(ChangeEvent e) {
							rowChangedAction(ce.getCellEditorValue());
						}

						public void editingCanceled(ChangeEvent e) {
						}

					});
					pageGRNItemTable.getTable().getColumnModel()
							.getColumn(COL_INPUT_QTY).setCellEditor(ce);

					//============================== Damage Qty Cell Editor  ==============================//
					IntegerCellEditor ce2 = new IntegerCellEditor(0, 99999999);
					ce2.addCellEditorListener(new CellEditorListener() {
						public void editingStopped(ChangeEvent e) {
							rowChangedAction(ce.getCellEditorValue());
						}

						public void editingCanceled(ChangeEvent e) {
						}
					});
					pageGRNItemTable.getTable().getColumnModel()
							.getColumn(COL_DAMAGE_QTY).setCellEditor(ce2);

					//============================== Discount Cell Editor  ==============================//
					IntegerCellEditor ce3 = new IntegerCellEditor(0, 99999999);
					ce3.addCellEditorListener(new CellEditorListener() {
						public void editingStopped(ChangeEvent e) {
							rowChangedAction(ce.getCellEditorValue());
						}

						public void editingCanceled(ChangeEvent e) {
						}
					});
					pageGRNItemTable.getTable().getColumnModel()
							.getColumn(COL_DISCOUNT).setCellEditor(ce3);

					//============================== Other Deduct Cell Editor  ==============================//
					DoublesCellEditor ce4 = new DoublesCellEditor(
							new JFormattedTextField());
					ce4.addCellEditorListener(new CellEditorListener() {
						public void editingStopped(ChangeEvent e) {
							rowChangedAction(ce.getCellEditorValue());
						}

						public void editingCanceled(ChangeEvent e) {
						}
					});
					pageGRNItemTable.getTable().getColumnModel()
							.getColumn(COL_OTHER_DEDUCT).setCellEditor(ce4);

					//============================== VAT Cell Editor  ==============================//
					List<String> lstVat = Arrays.asList("0%", "5%", "10%",
							"KCT");
					final CComboBox cbVat = new CComboBox();

					final CComboBox cbCurrency = new CComboBox();
					List<Currency> lstCurrency = CommonLogic.loadCurrency();
					CommonLogic.updateComboBox(lstCurrency, cbCurrency);
					cbCurrency.setSelectedItemById("VND");

					CommonLogic.updateComboBox(lstVat, cbVat);
					cbVat.setSelectedItemById("10%");

					DefaultCellEditor vatCellEdt = new DefaultCellEditor(cbVat) {
						private static final long serialVersionUID = 1L;

						@Override
						public Object getCellEditorValue() {
							return cbVat.getSelectedItemId();
						}
					};
					cbVat.addActionListener(new AbstractAction() {
						private static final long serialVersionUID = 1L;

						public void actionPerformed(ActionEvent e) {
							try {
								String vatRateString = cbVat
										.getSelectedItemId();
								rowChangedAction(vatRateString);
							} catch (Exception e2) {
								e2.printStackTrace();
							}

						}
					});
					pageGRNItemTable.getTable().getColumnModel()
							.getColumn(COL_VAT_RATE).setCellEditor(vatCellEdt);

					//============================== Currency Cell Editor  ==============================//
					DefaultCellEditor currencyCellEdt = new DefaultCellEditor(
							cbCurrency) {
						private static final long serialVersionUID = 1L;

						@Override
						public Object getCellEditorValue() {
							return cbCurrency.getSelectedItemId();
						}
					};
					cbCurrency.addActionListener(new AbstractAction() {
						private static final long serialVersionUID = 1L;

						public void actionPerformed(ActionEvent e) {
							try {
								String currencyId = cbCurrency
										.getSelectedItemId();
								rowChangedAction(currencyId);
							} catch (Exception e2) {
								e2.printStackTrace();
							}

						}
					});
					pageGRNItemTable.getTable().getColumnModel()
							.getColumn(COL_CURRENCY)
							.setCellEditor(currencyCellEdt);
				}
				xpnlDetailTwoModePanel.updateTitleWithStatus(true, Language
						.getInstance().getString("detail") + " - " + tranxId);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	/**
	 * Execute when user change any value in table
	 * @param val
	 */
	void rowChangedAction(Object val){
		try {
			//     Damage QTY will not be calculated for payment.			
			int row = pageGRNItemTable.getTable().getSelectedRow();
			int col = pageGRNItemTable.getTable().getSelectedColumn();
			if (row < 0 || col < 0)return;	
			List<PPDetailWrapper> listPPDW = (List<PPDetailWrapper>) ((CTable) pageGRNItemTable
					.getTable()).getLstData();
			
			PPDetailWrapper dtl = listPPDW.get(pageGRNItemTable.getTable()
					.convertRowIndexToModel(row));			
			long receiptQty =Formatter.obj2num(pageGRNItemTable
					.getTable()
					.getModel()
					.getValueAt(
							pageGRNItemTable.getTable().convertRowIndexToModel(row),
							COL_INPUT_QTY)).longValue();
			
			pageGRNItemTable.getTable().getModel()
					.setValueAt(receiptQty, row, COL_TOTAL_QTY);
			
			String planQtyStr =(String)pageGRNItemTable
					.getTable()
					.getModel()
					.getValueAt(
							pageGRNItemTable.getTable().convertRowIndexToModel(row),
							COL_PLAN_QTY);
			long planQty = Formatter.str2num(planQtyStr).longValue();
			
			long corruptedQty = Formatter.obj2num(pageGRNItemTable
					.getTable()
					.getModel()
					.getValueAt(
							pageGRNItemTable.getTable().convertRowIndexToModel(row),
							COL_DAMAGE_QTY)).longValue();
			
			double discountAmt =Formatter.obj2num(pageGRNItemTable
					.getTable()
					.getModel()
					.getValueAt(
							pageGRNItemTable.getTable().convertRowIndexToModel(row),
							COL_DISCOUNT)).doubleValue();
			
			double otherDeductAmt = Formatter.obj2num(pageGRNItemTable
					.getTable()
					.getModel()
					.getValueAt(
							pageGRNItemTable.getTable().convertRowIndexToModel(row),
							COL_OTHER_DEDUCT)).doubleValue();
			
			String  vatRateStr  =  (String) pageGRNItemTable
					.getTable()
					.getModel()
					.getValueAt(
							pageGRNItemTable.getTable().convertRowIndexToModel(row),
							COL_VAT_RATE);		
			
			double  vatRatePct = (vatRateStr != null && (vatRateStr.equalsIgnoreCase("KCT")|| vatRateStr.equals("0%"))?0.0d:
				Formatter.str2num(vatRateStr.replaceAll("%","")).doubleValue()/100);
			
			double unitPrice = Formatter.obj2num(pageGRNItemTable
					.getTable()
					.getModel()
					.getValueAt(
							pageGRNItemTable.getTable().convertRowIndexToModel(row),
							COL_UNIT_PRICE)).doubleValue();
			double vatRateAmt = receiptQty*unitPrice*vatRatePct;
			String totalAmt = Formatter.num2str(unitPrice * receiptQty-vatRateAmt-otherDeductAmt-discountAmt);
			switch (col) {
			case COL_INPUT_QTY:
				//-- re-calculate COL_TOTAL_QTY=COL_INPUT_QTY, COL_TOTAL_AMT=COL_TOTAL_QTY*COL_UNIT_PRICE		
				receiptQty = Formatter.obj2num(val).longValue();
				if(receiptQty>planQty){
					SystemMessageDialog.showMessageDialog(null, Language.getInstance().getString("quantity.over.limit")+"(r="+row+","+receiptQty+">"+planQty+")", 
							SystemMessageDialog.SHOW_OK_OPTION);
					pageGRNItemTable.getTable().getModel().setValueAt(planQty,COL_INPUT_QTY, 
							pageGRNItemTable.getTable().convertRowIndexToModel(row));
					dtl.getPp_detail().setReceive_quantity(planQty);
					receiptQty = planQty;
				}
				pageGRNItemTable.getTable().getModel()
						.setValueAt(totalAmt, row, COL_TOTAL_AMT);
				dtl.getPp_detail().setReceive_quantity(receiptQty);
				break;
			case COL_CURRENCY:
				//Reload pricelist and update COL_UNIT_PRICE and re-calculate: COL_TOTAL_AMT
				String currencyId = (String) val;
				receiptQty = Integer.parseInt(String
						.valueOf(pageGRNItemTable
								.getTable()
								.getModel()
								.getValueAt(
										pageGRNItemTable.getTable()
												.convertRowIndexToModel(row),
										COL_INPUT_QTY)));
				//-- get product id
				PriceList pl = CommonLogic.getPriceList(dtl.getItem().getProductId(),
						currencyId, dtl.getCustomer().getCustomer_rank_id());
				if (pl == null) {
					SystemMessageDialog.showMessageDialog(null, Language.getInstance()
							.getString("no.price.list.attach.to.customer.type")
							+ dtl.getCustomer().getCustomer_rank_id(),
							SystemMessageDialog.SHOW_OK_OPTION);
				}
				//Re-update detail wrapper
				dtl.getPp_detail().setPurchasing_price(pl!=null?pl.getPurchase_price():0);
				dtl.getPp_detail().setPurchasing_discount(pl!=null?pl.getDiscount():0);
				dtl.getPp_detail().setCurrency_id(currencyId);
				dtl.setCurrency(currencyId);
				
				unitPrice = pl!=null?pl.getPurchase_price():0;
				vatRateAmt = receiptQty*unitPrice*vatRatePct;
				totalAmt = Formatter.num2str(unitPrice * receiptQty-vatRateAmt-otherDeductAmt-discountAmt);
				pageGRNItemTable.getTable().getModel()
						.setValueAt(unitPrice, pageGRNItemTable.getTable().convertRowIndexToModel(row), COL_UNIT_PRICE);				
				pageGRNItemTable.getTable().getModel()
				.setValueAt( totalAmt, 
						pageGRNItemTable.getTable().convertRowIndexToModel(row), 
						COL_TOTAL_AMT);		
				break;
			case COL_DISCOUNT:
				pageGRNItemTable.getTable().getModel()
				.setValueAt( totalAmt, 
						pageGRNItemTable.getTable().convertRowIndexToModel(row), 
						COL_TOTAL_AMT);		
				dtl.getPp_detail().setPurchasing_discount(discountAmt);
				break;
			case COL_OTHER_DEDUCT:
				pageGRNItemTable.getTable().getModel()
				.setValueAt( totalAmt, 
						pageGRNItemTable.getTable().convertRowIndexToModel(row), 
						COL_TOTAL_AMT);		
				dtl.getPp_detail().setOther_discount(otherDeductAmt);
				break;
			case COL_VAT_RATE:
				pageGRNItemTable.getTable().getModel()
				.setValueAt( totalAmt, 
						pageGRNItemTable.getTable().convertRowIndexToModel(row), 
						COL_TOTAL_AMT);
				break;
			default:
				break;
			}
			dtl.getPp_detail().setVat(vatRateAmt);
			dtl.getPp_detail().setCorrupted_quantity(corruptedQty);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	void validateInputQty(){
		int totalRow = pageGRNItemTable.getTable().getModel().getRowCount();
		for(int i=0;i<totalRow;i++){
			int contractQty = Formatter.str2num((String) pageGRNItemTable.getTable().getModel().getValueAt(i, 2)).intValue();
			int actualQty = Formatter.str2num(String.valueOf(pageGRNItemTable.getTable().getModel().getValueAt(i, 3))).intValue();
			if(actualQty > contractQty || actualQty <= 0){
				SystemMessageDialog.showMessageDialog(null, Language.getInstance().getString("quantity.over.limit")+"(r="+i+","+actualQty+">"+contractQty+")", 
						SystemMessageDialog.SHOW_OK_OPTION);
				pageGRNItemTable.getTable().getModel().setValueAt(contractQty, i, 3);
			}
		}
	}
	public Action cmdSave = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			saveGRN();
			gmGRNHeaderGUI.isActived = false;
			gmGRNHeaderGUI.resetData();
			isActived = false;
			loadTranxList();
		}
	};
	public Action cmdRefreshTxList = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {			
			loadTranxList();
		}
	};
	public Action cmdNew = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			newGRN(null);
		}
	};

	public Action cmdCancel = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			cActionPerformed(new CActionEvent(this, CActionEvent.CANCEL, null));
			isActived = false;
			isSaved = true;
			pageGRNItemTable.clearData();
		}
	};
	
	public Action cmdOpenHistory = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			if(pagePurchaseTranx.getTable().getSelectedRow() != -1){
				String ppId = (String)pagePurchaseTranx.getTable().getValueAt(pagePurchaseTranx.getTable().getSelectedRow(), 6);
				ShowGRNHistoryManager   grnHisGui = new ShowGRNHistoryManager(ppId);
				DialogHelper.showDialog(GUIManager.mainFrame, grnHisGui.getRootComponent());		
			}			
		}
	};
	
	public void exportData(List<PPDetailWrapper> lstData, String type) {
		Workbook newWorkbook = new XSSFWorkbook();
		Sheet sheet = newWorkbook.createSheet();

		// Generate Tilte
		Row row0 = sheet.createRow(0);
		Cell cell00 = row0.createCell(0);
		cell00.setCellValue(type);

		Row row1 = sheet.createRow(1);
		Cell cell13 = row1.createCell(3, 1);
		cell13.setCellValue("GOODS RECEIVE NOTE LIST DATA");

		// generate collumn header
		Row row4 = sheet.createRow(4);
		PPDetailWrapper firstItem = lstData.get(0);
		String[] headers = firstItem.getObjectHeader();
		for (int i = 0; i < headers.length; i++) {
			Cell cell = row4.createCell(i);
			cell.setCellValue(headers[i]);
		}

		// Generate data rows
		int inte = 5;
		for (PPDetailWrapper ppdetailWrapper : lstData) {
			Row row = sheet.createRow(inte);
			ProductItem item = ppdetailWrapper.getItem();
			PurchaseGrnDetail grndetail = ppdetailWrapper.getPp_detail();
			row.createCell(1).setCellValue(item.getProductId());
			row.createCell(2).setCellValue(item.getProductName());
			row.createCell(3).setCellValue(grndetail.getReceive_quantity());
			row.createCell(4).setCellValue(grndetail.getCorrupted_quantity());
			row.createCell(5).setCellValue(ppdetailWrapper.getTotalQuantity());
			row.createCell(6).setCellValue(grndetail.getPurchasing_price());
			row.createCell(7).setCellValue(ppdetailWrapper.getCurrency());
			row.createCell(8).setCellValue(grndetail.getPurchasing_discount());
			row.createCell(9).setCellValue(grndetail.getOther_discount());
			row.createCell(10).setCellValue(grndetail.getVat());
			row.createCell(11).setCellValue(ppdetailWrapper.getTotalAmount());
			inte++;
		}

		try {
			OutputStream out = new FileOutputStream("testGRN.xlsx");
			newWorkbook.write(out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static class PPDetailWrapper extends AbstractIshopEntity {
		private ProductItem item;
		private String currency;
		private PurchaseGrn purchaseGrn;
		private PurchaseGrnDetail pp_detail;
		private String totalQuantity;
		private String planQty;
		private String totalAmount;
		private Customer customer;
		
		public Customer getCustomer() {
			return customer;
		}

		public void setCustomer(Customer customer) {
			this.customer = customer;
		}

		public PPDetailWrapper() {
			super();
			item = new ProductItem();
			pp_detail = new PurchaseGrnDetail();
		}

		public ProductItem getItem() {
			return item;
		}

		public String getPlanQty() {
			return planQty;
		}

		public void setPlanQty(String planQty) {
			this.planQty = planQty;
		}

		public void setItem(ProductItem item) {
			this.item = item;
		}

		public String getCurrency() {
			return currency;
		}
		public PurchaseGrn getPurchaseGrn() {
			return purchaseGrn;
		}

		public void setPurchaseGrn(PurchaseGrn purchaseGrn) {
			this.purchaseGrn = purchaseGrn;
		}
		@ImportInvoke(type = "String", mapping = "Currency")
		public void setCurrency(String currency) {
			this.currency = currency;
			pp_detail.setCurrency_id(currency);
		}

		public PurchaseGrnDetail getPp_detail() {
			return pp_detail;
		}

		public void setPp_detail(PurchaseGrnDetail pp_detail) {
			this.pp_detail = pp_detail;
		}

		public String getTotalQuantity() {
			return totalQuantity;
		}

		@ImportInvoke(type = "String", mapping = "totalQuantity")
		public void setTotalQuantity(String totalQuantity) {
			this.totalQuantity = totalQuantity;
		}

		public String getTotalAmount() {
			return totalAmount;
		}

		@ImportInvoke(type = "String", mapping = "TotalPrice")
		public void setTotalAmount(String totalAmount) {
			this.totalAmount = totalAmount;
		}

		@Override
		public  String[] getObjectHeader() {
			return new String[] { "", "SKU", "ten.mat.hang","PlanQty", "Received", "Corrupted", "totalQuantity", "UnitPrice", "Currency", "Discount",
					"OtherDiscount", "VAT", "TotalPrice" };
		}

		@Override
		public Object[] toObjectArray() {
			return new Object[] { Boolean.FALSE, item.getProductId(), item.getProductName(),planQty, pp_detail.getReceive_quantity(),
					pp_detail.getCorrupted_quantity(), totalQuantity, 
					Formatter.num2str(pp_detail.getPurchasing_price()), 
					currency, pp_detail.getPurchasing_discount(),
					pp_detail.getOther_discount(), Formatter.num2str(pp_detail.getVat()), totalAmount };
		}
		
		@ImportInvoke(type = "int", mapping = "VAT")
		public void setVat(int vat){
			pp_detail.setVat(vat);
		}
		
		@ImportInvoke(type = "double", mapping = "OtherDiscount")
		public void setOtherDiscount(double otherDiscount){
			pp_detail.setOther_discount(otherDiscount);
		}
		
		@ImportInvoke(type = "int", mapping = "Discount")
		public void setPurchasingDiscount(int purchasingDiscount){
			pp_detail.setPurchasing_discount(purchasingDiscount);
		}
		
		@ImportInvoke(type = "double", mapping = "UnitPrice")
		public void setPurchasing_price(double purchasing_price){
			pp_detail.setPurchasing_price(purchasing_price);
		}
		
		@ImportInvoke(type = "long", mapping = "Corrupted")
		public void setCorrupted_quantity(long corrupted_quantity){
			pp_detail.setCorrupted_quantity(corrupted_quantity);
		}
		
		@ImportInvoke(type = "long", mapping = "Received")
		public void setReceive_quantity(long receive_quantity){
			pp_detail.setReceive_quantity(receive_quantity);
		}
		
		@ImportInvoke(type = "String", mapping = "ten.mat.hang")
		public void setProductName(String productName){
			item.setProductName(productName);
		}
		
		@ImportInvoke(type = "String", mapping = "SKU")
		public void setProductId(String productId){
			item.setProductId(productId);
		}
		
	}
	
	public static void main(String[] args ) throws Exception{
		AbstractGUIManager guiManager; 
		try {
		    UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
		} catch (Exception e) {
		    e.printStackTrace();
		}	
		Language.loadLanguage("vn");
		
		JFrame f = new JFrame();
		
		guiManager = new GRNMainGUIManager(Language.getInstance().getLocale());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().add(guiManager.getRootComponent());		
		
		f.pack();
		f.validate();
		
		f.setVisible(true);
	}
}
