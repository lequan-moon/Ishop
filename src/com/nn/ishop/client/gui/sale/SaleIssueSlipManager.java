package com.nn.ishop.client.gui.sale;

import java.awt.Dimension;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.apache.commons.collections15.map.HashedMap;

import com.nn.ishop.client.CActionEvent;
import com.nn.ishop.client.CActionListener;
import com.nn.ishop.client.Main;
import com.nn.ishop.client.config.SystemConfiguration;
import com.nn.ishop.client.gui.AbstractGUIManager;
import com.nn.ishop.client.gui.GUIActionEvent;
import com.nn.ishop.client.gui.GUIActionListener;
import com.nn.ishop.client.gui.GUIManager;
import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.GUIActionEvent.GUIActionType;
import com.nn.ishop.client.gui.admin.customer.CTabbedPane;
import com.nn.ishop.client.gui.components.CComboBox;
import com.nn.ishop.client.gui.components.CDialogsLabelButton;
import com.nn.ishop.client.gui.components.CLineBorder;
import com.nn.ishop.client.gui.components.CPaging;
import com.nn.ishop.client.gui.components.CTableModel;
import com.nn.ishop.client.gui.components.CTextField;
import com.nn.ishop.client.gui.components.CTwoModePanel;
import com.nn.ishop.client.gui.components.CWhitePanel;
import com.nn.ishop.client.gui.components.IntegerCellEditor;
import com.nn.ishop.client.gui.dialogs.SystemMessageDialog;
import com.nn.ishop.client.gui.helper.DialogHelper;
import com.nn.ishop.client.gui.helper.TableUtil;
import com.nn.ishop.client.gui.purchase.PurchaseMasterGUIManager;
import com.nn.ishop.client.logic.CommonLogic;
import com.nn.ishop.client.logic.admin.WarehouseLogic;
import com.nn.ishop.client.logic.sale.SaleOrderLogic;
import com.nn.ishop.client.logic.transaction.DailyTranxLogic;
import com.nn.ishop.client.util.Util;
import com.nn.ishop.server.dto.bstranx.DailyTransaction;
import com.nn.ishop.server.dto.sale.SaleIssueSlip;
import com.nn.ishop.server.dto.sale.SaleOrder;
import com.nn.ishop.server.dto.sale.SaleOrderDetail;
import com.nn.ishop.server.dto.warehouse.Lot;
import com.nn.ishop.server.dto.warehouse.Warehouse;
import com.nn.ishop.server.util.Formatter;
import com.toedter.calendar.JDateChooser;

public class SaleIssueSlipManager extends AbstractGUIManager implements
CActionListener, TableModelListener, GUIActionListener{

	CWhitePanel pnlItemDetailContainer, pnlItemIssuingHistoryContainer;
	CTabbedPane tabTranxDetailTabbedPane;
	CPaging pageSaleIslTranxListPage;
	CPaging pageIssueItemListPage,pageIssueItemHistoryListPage;
	
	CTextField txtSONumber, txtFromWarehouse, txtIslNumber, txtIssueNote, txtIssuedSONumber;
	JDateChooser dcIssueDate;
	Warehouse beanOrderWh = null;
	
	CDialogsLabelButton btnSaveIsl;
	JSplitPane splitSaleTranx;
	String strSectedTranxId = null;
	SaleOrder beanSelectedSo = null;

	/** Map to store <item,quantity> **/
	Map<String, Integer> mapIssuedItem = new HashedMap<String, Integer>();
	Map<String, Integer> mapCurrentIssueItem = new HashedMap<String, Integer>();
	Map<String, Integer> mapOrderIssueItem = new HashedMap<String, Integer>();
	
	final int WH_COL = 3, LOT_COL = 4, ISSUE_QTY_COL=5, ISSUE_DATE_COL=7, SO_QTY_COL=6,WH_CODE_COL = 9, LOT_CODE_COL = 10;
	final List<Integer> listEditableColums = Arrays.asList(new Integer[]{0,LOT_COL,ISSUE_QTY_COL});
	CTwoModePanel xpnlTranxTwoModePanel, xpnlDetailTwoModePanel;
	CWhitePanel    pnlTranxList,pnlTranxDetailParent;
	public SaleIssueSlipManager (String locale){
		setLocale(locale);
		init();
	}
	void init(){
		if (getLocale() != null && !getLocale().equals("")){
			initTemplate(this, "sale/saleOrderIssueSlipPage.xml", getLocale());
		}else{
			initTemplate(this, "sale/saleOrderIssueSlipPage.xml");		
			}
		render();
		xpnlDetailTwoModePanel.addGUIActionListener(this);
		xpnlTranxTwoModePanel.addGUIActionListener(this);
		xpnlTranxTwoModePanel.setManagerClazz(SaleIssueSlipManager.class);
		xpnlDetailTwoModePanel.setManagerClazz(String.class);
		xpnlTranxTwoModePanel.addContent(pnlTranxList);
		xpnlDetailTwoModePanel.addContent( pnlTranxDetailParent);
		bindEventHandlers();
	}
	@Override
	protected void applyStyles() {
		/*itemDetailContainer.setBorder(BorderFactory.createCompoundBorder(
				new CLineBorder(null,CLineBorder.DRAW_BOTTOM_LEFT_RIGHT_BORDER), 
				new EmptyBorder(10,3,3,3))
				);
		itemIssuingHistoryContainer.setBorder(BorderFactory.createCompoundBorder(
				new CLineBorder(null,CLineBorder.DRAW_BOTTOM_LEFT_RIGHT_BORDER), 
				new EmptyBorder(10,3,3,3))
				);*/
		tabTranxDetailTabbedPane.setTitleAt(0, Language.getInstance().getString("sale.tabbedpane.detail.txinfor"));
		tabTranxDetailTabbedPane.setTitleAt(1, Language.getInstance().getString("issuing.diary"));
		customizeSplitPaneHideFirstComponent(splitSaleTranx);
		
	}

	void loadTransaction() throws Throwable{		
		List<DailyTransaction> dailyTxList = DailyTranxLogic.getInstance().loadTranxByStep("ISSUE_SLIP");
		if(dailyTxList != null && dailyTxList.size() > 0)
			TableUtil.addListToTable(pageSaleIslTranxListPage, pageSaleIslTranxListPage.getTable(), dailyTxList, null);		
	}
	
	@Override
	protected void bindEventHandlers() {
		try {
			logAction(SaleIssueSlipManager.class, LOG_LEVEL.INFO, "Start binding event...");
			loadTransaction();
			pageSaleIslTranxListPage.getTable().addMouseListener(new MouseAdapter() {				
				@Override
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount() >=2)
						try {
							int row1 = pageSaleIslTranxListPage.getTable()
									.rowAtPoint(e.getPoint());
							int row = pageSaleIslTranxListPage.getTable()
									.convertRowIndexToModel(row1);			
							final String tranxId = String
									.valueOf(pageSaleIslTranxListPage.getTable()
											.getModel().getValueAt(pageSaleIslTranxListPage.getTable().convertRowIndexToModel(row), 1));
							
							if (tranxId != null && !tranxId.equals("")) {
								loadTransactionDetail(tranxId);
								strSectedTranxId = tranxId;
							}
							Runnable hideSplitThread = new Runnable() {						
								public void run() {
									try {
										//-- minimize sale tranx panel
										splitSaleTranx.getTopComponent().setMinimumSize(new Dimension());
										splitSaleTranx.setDividerLocation(0.0d);										
									} catch (Exception e2) {
										e2.printStackTrace();
									}
								}
							};
							SwingUtilities.invokeLater(hideSplitThread);
							super.mouseReleased(e);
						} catch (Throwable e2) {
							logAction(SaleMasterGUIManager.class, LOG_LEVEL.ERROR, e);						
						}
				}
			});
			ACT_SAVE_ISL.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK));			
			btnSaveIsl.getActionMap().put("saveIslAction", ACT_SAVE_ISL);
			btnSaveIsl.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
			        (KeyStroke) ACT_SAVE_ISL.getValue(Action.ACCELERATOR_KEY), "saveIslAction");
		} catch (Throwable e) {		
			logger.error(e);
		}
	}
	
	void loadTransactionDetail(String tranxId)throws Throwable{
		logAction(SaleIssueSlipManager.class,LOG_LEVEL.INFO,"Start loading ISL for transaction " +tranxId);		
		try {
			mapIssuedItem.clear();
			mapOrderIssueItem.clear();
			beanSelectedSo = null;
			//-- load sale order and sale order detail based on transaction id
			beanSelectedSo = SaleOrderLogic.getInstance().getSOByTranxId(tranxId);
			if(beanSelectedSo == null)
				SystemMessageDialog.showMessageDialog(null
						, Language.getInstance().getString("error.sale.not.found.for.transaction") + tranxId
						, SystemMessageDialog.SHOW_OK_OPTION);
			if(beanSelectedSo.getDetails() == null || beanSelectedSo.getDetails().length==0)
				SystemMessageDialog.showMessageDialog(null
						, Language.getInstance().getString("sale.order.without.detail") + beanSelectedSo.getOrder_id()
						, SystemMessageDialog.SHOW_OK_OPTION);			
			logAction(SaleIssueSlipManager.class,LOG_LEVEL.INFO
					,"Detail length for the SO  " +beanSelectedSo.getDetails().length);			
			//-- Check ISL related to this Order 			
			List<SaleIssueSlip> oldIslByOrder = SaleOrderLogic.getInstance().loadIssueSlipByOrder(beanSelectedSo.getOrder_id());
			
			if(oldIslByOrder != null && oldIslByOrder.size()>0){
				Object[][] oldRowData = new Object[oldIslByOrder.size()][];				
				int i=0;
				for(SaleIssueSlip ent:oldIslByOrder){
					oldRowData[i] = ent.toObjectArray();
					i++;
				}
				String[] oColHeader = oldIslByOrder.get(0).getObjectHeader();
				CTableModel model = preparedModel(oldRowData, oColHeader, listEditableColums);				
				TableUtil.formatTable(pageIssueItemHistoryListPage, pageIssueItemHistoryListPage.getTable(), model);
				for(SaleIssueSlip isl: oldIslByOrder){
					if(!mapIssuedItem.containsKey(isl.getItem_id()))
						mapIssuedItem.put(isl.getItem_id(), isl.getIssue_quantity());
				}
			}else{//-- clear ISL history 
				TableUtil.addListToTable(pageIssueItemHistoryListPage,  pageIssueItemHistoryListPage.getTable(),oldIslByOrder);
			}
			
			List<SaleIssueSlip> islList = new ArrayList<SaleIssueSlip>();
			String islId = Util.generateSaleIssueSlipSerial();			
			for(SaleOrderDetail dto : beanSelectedSo.getDetails()){
				//-- calculate remain qty. to issue this time
				int maxQty = dto.getQuantity();
				mapOrderIssueItem.put(dto.getItem_id(), dto.getQuantity());
				if(mapIssuedItem.containsKey(dto.getItem_id()))
					maxQty -= mapIssuedItem.get(dto.getItem_id());				
				SaleIssueSlip isl = new SaleIssueSlip(
						islId
						, tranxId
						, beanSelectedSo.getOrder_id()
						, "-1"
						, beanSelectedSo.getWarehouse_id()
						, null
						, dto.getItem_id()
						, maxQty
						, new Date()
						, Main.loggedInUser.getEm().getId()
				);
				isl.setItem_name(dto.getItem_name());
				isl.setLot_id("");
				isl.setIns_time(SystemConfiguration.sdf.format(new Date()));
				isl.setUpd_time("");
				//isl.setUpd_user_id();
				islList.add(isl);
			}	
			
			if(islList!= null && islList.size()>0){				
				dcIssueDate.setDate(new Date());				
				txtIssuedSONumber.setText(beanSelectedSo.getOrder_id());
				txtIssueNote.setText("Automatic create ISL");
				if(beanSelectedSo.getWarehouse_id()!= null){
					beanOrderWh = WarehouseLogic.getWarehouse(beanSelectedSo.getWarehouse_id().toUpperCase());
					txtFromWarehouse.setText(beanOrderWh.getName());
				}
				txtIslNumber.setText(islId);
				txtSONumber.setText(beanSelectedSo.getOrder_id());
				
				Object[][] rowData = new Object[islList.size()][];				
				int i=0;
				for(SaleIssueSlip ent:islList){
					rowData[i] = ent.toObjectArray();
					i++;
				}
				String[] colHeader = islList.get(0).getObjectHeader();
				
				CTableModel model = preparedModel(rowData, colHeader, listEditableColums);				
				TableUtil.formatTable(pageIssueItemListPage, pageIssueItemListPage.getTable(), model);
				pageIssueItemListPage.getTable().changeSelection(0,6,false, false);
			}else//-- Do nothing when null
			{
				logAction(SaleIssueSlipManager.class, LOG_LEVEL.INFO, "No issue slip found for the selected order: "
						+ beanSelectedSo.getOrder_id());
				return;
			}
			try {
				List<Lot> lots = WarehouseLogic
						.loadLotByWarehouse(beanSelectedSo.getWarehouse_id());
				final CComboBox lotCb = new CComboBox();
				DefaultCellEditor lotCellEdt = new DefaultCellEditor(lotCb){
					private static final long serialVersionUID = 1L;
					@Override
					public Object getCellEditorValue() {
						return lotCb.getSelectedItem().toString();
					}
				};	
				CommonLogic.updateComboBox(lots, lotCb);
				pageIssueItemListPage.getTable().getColumnModel().getColumn(LOT_COL).setCellEditor(lotCellEdt);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			IntegerCellEditor ce = new IntegerCellEditor(0,99999999);
			pageIssueItemListPage.getTable().getColumnModel().getColumn(ISSUE_QTY_COL).setCellEditor(ce);
		} catch (Throwable e) {
			SystemMessageDialog.showMessageDialog(null,"Some error during loading ISL: "+ e.getMessage(), SystemMessageDialog.SHOW_OK_OPTION);
			e.printStackTrace();
		}
		logAction(SaleIssueSlipManager.class,LOG_LEVEL.INFO,"End loading ISL for transaction " +tranxId);		
	}
	
	protected CTableModel preparedModel(Object[][] rowData, String[] colHeader, final List<Integer> editableColums){		
		//- Modified column name to for localization
		String[] columnNames = new String[colHeader.length];
		for (int i = 0; i < colHeader.length; i++) {
			String realTitle = Language.getInstance().getString(colHeader[i]);
			columnNames[i] = realTitle;
		}
		CTableModel model = new CTableModel(rowData, columnNames, rowData.length) {
			private static final long serialVersionUID = 1L;

			public Class getColumnClass(int column) {
				if(column == 0)
					return Boolean.class;
				if(column == ISSUE_QTY_COL)
					return Integer.class;
				else
					return Object.class;				        
		      }
			@Override
			public boolean isCellEditable(int row, int col) {
				if (editableColums != null && editableColums.size()>0 
						&& editableColums.contains(new Integer(col)))
					return true;
				return super.isCellEditable(row, col);
			}
		};
		model.addTableModelListener(this);
		return model;
	}
	@Override
	protected void checkPermission() {
	}

	@Override
	public Object getData(String var) {
		return null;
	}

	@Override
	public void update() {

	}

	@Override
	public void updateList() {
	}
	
	Vector<CActionListener> vctCListener = new Vector<CActionListener>();
	
	public void addCActionListener(CActionListener al) {
		vctCListener.add(al);
	}
	
	public void cActionPerformed(CActionEvent action) {
		try {
			/** Need to clarify transaction type for each tabbed pane to reload tranx*/
			if (action.getAction() == CActionEvent.RELOAD_TRANX_LIST)
				loadTransaction();
		} catch (Throwable e) {
			logger.error(e);
		}
	}
	public void removeCActionListener(CActionListener al) {
		vctCListener.remove(al);
	}
	
	void fireCAction(CActionEvent action){
		
	}
	public void tableChanged(TableModelEvent e) {
		try {
			if (e.getColumn() == ISSUE_QTY_COL) {
				int col = e.getColumn();
				int row = e.getFirstRow();
				CTableModel m = pageIssueItemListPage.getModel();
					int isQty = SystemConfiguration.decfm.parse(
							String.valueOf(pageIssueItemListPage.getTable().getModel().getValueAt(
									pageIssueItemListPage.getTable().convertRowIndexToModel(row), col))).intValue();
					int soQty = SystemConfiguration.decfm.parse(
							String.valueOf(pageIssueItemListPage.getTable().getModel().getValueAt(
									pageIssueItemListPage.getTable().convertRowIndexToModel(row), SO_QTY_COL))).intValue();
					if (isQty > soQty){
						logAction(SaleIssueSlipManager.class, LOG_LEVEL.ERROR, "Issue qty is greater than max quantity");
						pageIssueItemListPage.getTable().getModel().setValueAt(soQty, 
								pageIssueItemListPage.getTable().convertRowIndexToModel(row), col);
					}
			}
		} catch (Exception e2) {
			e2.printStackTrace();
			logAction(SaleIssueSlipManager.class, LOG_LEVEL.ERROR, e2);
		}
	}
	public Action ACT_NEXT_STEP = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			logAction(SaleIssueSlipManager.class, LOG_LEVEL.INFO, "Go to next step");
//			TableModel model = pageIssueItemListPage.getTable().getModel();
		}
	};
	public Action ACT_VIEW_ISSUE_HIS = new AbstractAction() {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			logAction(SaleIssueSlipManager.class, LOG_LEVEL.INFO, "View issue history...");
			
		}
	};
	public Action ACT_ONLINE_STOCK = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			DialogHelper.openItemStockDialog(GUIManager.mainFrame);
		}
	};
	public Action ACT_SAVE_ISL= new AbstractAction() {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			try{
				logAction(SaleOrderGUIManager.class,LOG_LEVEL.DEBUG, ":Start saving Issue Slip...");
				//-- request focus on button
				btnSaveIsl.requestFocusInWindow();
				//-- validate
				//-- Build Isl and save
				/**	
					Object[]{ 0-Boolean.FALSE,
						1-id,2-item_name,3-warehouse_id,4-lot_id, 5-issue_quantity, 6-order_quantity
						,7-Formatter.sdf.format(issue_date),8-tranx_id,9-order_id,10-stockcard_id,11-warehouser,12-ins_user_id, 13-ins_time		  
						,14-upd_user_id, 15-upd_time, 16-usage_flg,17-item_id
					}
				 }
				 */
				mapCurrentIssueItem.clear();
				CTableModel model = (CTableModel) pageIssueItemListPage.getTable().getModel();
				Object[][] data = model.getData();
				
				for(int i=0; i<data.length;i++){
					Object[] rd = data[i];
					//--- TODO check item stock
					int issueQty = (Integer)rd[5];
					
					//-- Stock card subtract will be done by server
					SaleIssueSlip s = new SaleIssueSlip((String)rd[1], (String)rd[8], (String)rd[9]
					                                    , String.valueOf(rd[10]), (String)rd[3]
					                                    ,(String)rd[4], (String)rd[17], Integer.parseInt(String.valueOf(rd[5]))
					                                    , Formatter.sdf.parse((String)rd[7]), Integer.parseInt(String.valueOf(rd[11])));
					SaleOrderLogic.getInstance().insertIsl(s);					
					mapCurrentIssueItem.put((String)rd[17], Integer.parseInt(String.valueOf(rd[5])));
				}
				/* Update transaction:
				 * 1. Update voucher id
				 * 2. Update next step
				 */
				SystemMessageDialog.showMessageDialog(null, Language.getInstance().getString("save.success"), SystemMessageDialog.SHOW_OK_OPTION);
				DailyTransaction dTranx = DailyTranxLogic.getInstance().get(strSectedTranxId);
				dTranx.setVoucher_id(beanSelectedSo.getOrder_id());
				//-- Calculate issue slip and order if it can be closed 
				boolean canClose = isCanCloseOrder();
				if(canClose)
					dTranx.setCurrent_step("CLOSED");				
				DailyTranxLogic.getInstance().update(dTranx);	
				//-- Expand the split pane
				Runnable hideSplitThread = new Runnable() {						
					public void run() {
						try {
							//-- minimize sale tranx panel
							splitSaleTranx.getTopComponent().setMinimumSize(new Dimension());
							splitSaleTranx.setDividerLocation(1.0d);							
							loadTransaction();
						} catch (Throwable e2) {
							e2.printStackTrace();
						}
					}
				};
				SwingUtilities.invokeLater(hideSplitThread);
				//-- clear form, map, selected item
				pageIssueItemListPage.clearData();
				// reload transaction list, fire action for other listener
				loadTransaction();
				fireCAction(new CActionEvent(this, CActionEvent.RELOAD_TRANX_LIST, null));				
			}catch(Throwable t){
				SystemMessageDialog.showMessageDialog(null, "Save order fail! " + t.toString(), SystemMessageDialog.SHOW_OK_OPTION);
				logAction(SaleIssueSlipManager.class, LOG_LEVEL.ERROR, t);
			}
		}
	};	
	/**
	 * Calculate based on Map<item, issueQyt> to know whether the order can be closed
	 * @return
	 */
	boolean isCanCloseOrder(){
		if(beanSelectedSo == null)
			return false;
		boolean ret = true;
		for(String key: mapOrderIssueItem.keySet()){
			int lastIssuedQty = (mapIssuedItem != null && mapIssuedItem.size()>0)?mapIssuedItem.get(key):0;
			int currentIssueQty = mapCurrentIssueItem.get(key);
			int orderQty = mapOrderIssueItem.get(key);
			ret &= (orderQty == (lastIssuedQty + currentIssueQty));
		}
		return ret;
	}
	public void guiActionPerformed(GUIActionEvent action) {
		GUIActionType guideType = action.getAction(); 
		Object srcObj = action.getSource();
		if(guideType.equals( GUIActionType.MINIMIZE_WINDOW)){
			int location = ((Dimension)action.getData()).height;
			if(srcObj.equals(SaleIssueSlipManager.class)){
				splitSaleTranx.setDividerLocation(location);
			}else{
				int newLoc = splitSaleTranx.getLeftComponent().getSize().height
						 + splitSaleTranx.getRightComponent().getSize().height
						 - location;
				splitSaleTranx.setDividerLocation(newLoc);
			}
		}
		if(guideType.equals( GUIActionType.MAXIMIZE_WINDOW)){
			splitSaleTranx.resetToPreferredSizes();
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
		
		guiManager = new SaleIssueSlipManager(Language.getInstance().getLocale());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().add(guiManager.getRootComponent());		
		
		f.pack();
		f.validate();
		
		f.setVisible(true);
	}
}
