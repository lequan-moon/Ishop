package com.nn.ishop.client.gui.sale;

import java.awt.Dimension;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JComponent;
import javax.swing.JRadioButton;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.jboss.logging.Logger;

import com.nn.ishop.client.CActionEvent;
import com.nn.ishop.client.CActionListener;
import com.nn.ishop.client.config.SystemConfiguration;
import com.nn.ishop.client.gui.AbstractGUIManager;
import com.nn.ishop.client.gui.GUIActionEvent;
import com.nn.ishop.client.gui.GUIActionListener;
import com.nn.ishop.client.gui.GUIManager;
import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.GUIActionEvent.GUIActionType;
import com.nn.ishop.client.gui.admin.customer.CTabbedPane;
import com.nn.ishop.client.gui.common.ChooseCustomerDialogManager;
import com.nn.ishop.client.gui.common.ChooseProductDialogManager;
import com.nn.ishop.client.gui.components.CButton2;
import com.nn.ishop.client.gui.components.CCheckBox;
import com.nn.ishop.client.gui.components.CComboBox;
import com.nn.ishop.client.gui.components.CDialogButton;
import com.nn.ishop.client.gui.components.CDialogsLabelButton;
import com.nn.ishop.client.gui.components.CLineBorder;
import com.nn.ishop.client.gui.components.CPaging;
import com.nn.ishop.client.gui.components.CTableModel;
import com.nn.ishop.client.gui.components.CTextField;
import com.nn.ishop.client.gui.components.CTwoModePanel;
import com.nn.ishop.client.gui.components.CWhitePanel;
import com.nn.ishop.client.gui.components.ComponentLocker;
import com.nn.ishop.client.gui.dialogs.GenericDialog;
import com.nn.ishop.client.gui.dialogs.SystemMessageDialog;
import com.nn.ishop.client.gui.helper.DialogHelper;
import com.nn.ishop.client.gui.helper.TableUtil;
import com.nn.ishop.client.logic.CommonLogic;
import com.nn.ishop.client.logic.admin.CustomerLogic;
import com.nn.ishop.client.logic.admin.ProductCatLogic;
import com.nn.ishop.client.logic.admin.ProductLogic;
import com.nn.ishop.client.logic.admin.WarehouseLogic;
import com.nn.ishop.client.logic.sale.SaleOrderLogic;
import com.nn.ishop.client.logic.transaction.DailyTranxLogic;
import com.nn.ishop.client.util.Util;
import com.nn.ishop.client.validator.Validator;
import com.nn.ishop.server.dto.bstranx.DailyTransaction;
import com.nn.ishop.server.dto.customer.Customer;
import com.nn.ishop.server.dto.exrate.Currency;
import com.nn.ishop.server.dto.pricelist.PriceList;
import com.nn.ishop.server.dto.product.ProductItem;
import com.nn.ishop.server.dto.product.ProductUOM;
import com.nn.ishop.server.dto.sale.SaleOrder;
import com.nn.ishop.server.dto.sale.SaleOrderDetail;
import com.nn.ishop.server.dto.sale.SaleOrderType;
import com.nn.ishop.server.dto.warehouse.Warehouse;
import com.toedter.calendar.JDateChooser;

public class SaleOrderGUIManager extends AbstractGUIManager
		implements CActionListener, GUIActionListener, ListSelectionListener,
		TableModelListener {
	final int QTY_COL = 4,UNIT_PRICE_COL = 5, GROSS_AMT_COL = 6, DISCOUNT_RATE_COL = 8
	,UOM_COL=7, DISCOUNT_AMT_COL=9, NET_AMT_COL=12;
	final int VAT_RATE_COL = 10, VAT_AMT_COL=11;
	//-- Add item to table
	final List<Integer> editableColums = Arrays.asList(new Integer[]{0,3,DISCOUNT_RATE_COL,QTY_COL,VAT_RATE_COL});
	final List<Integer> editableColums3 = Arrays.asList(new Integer[]{0});
	CWhitePanel pnlTranxDetail, itemDetailContainer,tranxDetailParentPage, itemDiscountContainer, itemAccountingContainer;
	CTabbedPane tranxDetailTabbedPane;
	JSplitPane saleTranxSplit;
	CPaging saleTranxListPage;
	Customer selectedCustomer = null;
	//===================
	CTextField txtSONumber,txtInvoiceNumber,txtContractNumber,txtOrderNote,txtPaymentDue,txtCustomerDebtBalance, txtCustomerName;
	CTextField txtSumGrossAmount,txtSumDiscountAmount,txtSumNetAmount,txtSumVATAmount;
	CComboBox comboSoType, /*comboCustomer,*/ comboPurchasePlan, comboStatus, comboCurrencyType, comboFromWarehouse, comboToWarehouse;
	JDateChooser dcOrderDate;	
	CCheckBox chkISL;	
	CDialogButton btnCustomerDebtRefresh;
	CButton2 btnChooseCustomer;
	
	ProductItem selectedProduct;
	PriceList selectedPriceList;
	
//	ComponentLocker guiLocker;
	
	CPaging saleOrderItemListPage;

	//=== Tab2 - Discount =================
	CComboBox comboDiscountPolicy;
	CTextField txtDiscountAmount;
	JRadioButton radioDisstributeByQuantity, radioDisstributeByAmount;
	
	//-----
	CPaging saleOrderItemListPage1;

	//============ Tab3 Accounting =============//
	//---
	CPaging saleOrderItemListPage3;
	//=== Tab1 =================
	CTextField txtItemId,txtItemName,txtItemQuantity,txtItemUom;
	ButtonGroup btnDistributeMethod;
	CDialogsLabelButton btnSaveOrder;
	
	static int lastKey = -1; 
	Vector<CActionListener> vctCListener = new Vector<CActionListener>();
	
	public static final Logger logger = Logger.getLogger(SaleOrderGUIManager.class);
	CTwoModePanel tranxTwoModePanel, detailTwoModePanel;
	CWhitePanel    pnlTranxList;
	
	public SaleOrderGUIManager (String locale){
		setLocale(locale);
		init();
	}
	void init(){
		if (getLocale() != null && !getLocale().equals("")){
			initTemplate(this, "sale/saleOrderPage.xml", getLocale());
		}else{
			initTemplate(this, "sale/saleOrderPage.xml");		
			}
		render();
		detailTwoModePanel.addGUIActionListener(this);
		tranxTwoModePanel.addGUIActionListener(this);
		tranxTwoModePanel.setManagerClazz(SaleOrderGUIManager.class);
		detailTwoModePanel.setManagerClazz(String.class);
		tranxTwoModePanel.addContent(pnlTranxList);
		detailTwoModePanel.addContent( tranxDetailParentPage);
		bindEventHandlers();
		
	}
	@Override
	protected void applyStyles() {
		tranxDetailTabbedPane.setTitleAt(0, Language.getInstance().getString("so.tabbed.item.detail"));
		tranxDetailTabbedPane.setTitleAt(1, Language.getInstance().getString("so.tabbed.item.discount"));
		tranxDetailTabbedPane.setTitleAt(2, Language.getInstance().getString("so.tabbed.item.accounting"));
	}
	synchronized void loadTransaction(){	
		logAction(SaleOrderGUIManager.class, LOG_LEVEL.INFO, "Start loading sale transaction...");
		try {
			List<DailyTransaction> dailyTxList = DailyTranxLogic.getInstance()
					.loadTranxByStep("SALE_ORDER");
			if (saleTranxListPage.getTable().getModel().getRowCount() > 0)
				saleTranxListPage.getTable().setData(null);
			if (dailyTxList != null && dailyTxList.size() > 0)
				TableUtil.addListToTable(saleTranxListPage, saleTranxListPage
						.getTable(), dailyTxList, null);
		} catch (Throwable e) {
			logAction(SaleOrderGUIManager.class, LOG_LEVEL.ERROR, e);
		}
		logAction(SaleOrderGUIManager.class, LOG_LEVEL.INFO, "End loading sale transaction.");
	}
	@Override
	protected void bindEventHandlers() {
//		guiLocker = new ComponentLocker(GUIManager.mainFrame.getLayeredPane());
//		guiLocker.setLocked(tranxDetailParentPage, true);
		
		try {
			
			loadTransaction();
			/** Select a first row and load sale order data for him */
			if(saleTranxListPage.getTable().getModel() != null && saleTranxListPage.getTable().getModel().getRowCount()>0)
			{
				saleTranxListPage.getTable().setRowSelectionInterval(0, 0);
				String tranxId = String.valueOf(saleTranxListPage.getTable().getModel().getValueAt(0, 1));
				String orderId = String.valueOf(saleTranxListPage.getTable().getModel().getValueAt(0, 6));
				if(tranxId != null && !tranxId.equals("")){					
					loadSaleOrder(tranxId, orderId);				
				}
			}
			saleTranxListPage.getTable().addMouseListener(new MouseAdapter() {				
				@Override
				public void mouseClicked(MouseEvent e) {	
//					guiLocker.setLocked(tranxDetailParentPage, true);
					int row = saleTranxListPage.getTable().rowAtPoint(e.getPoint());
					final String tranxId = String.valueOf(saleTranxListPage.getTable().getModel().getValueAt(
							saleTranxListPage.getTable().convertRowIndexToModel(row), 1));
					String orderId = String.valueOf(saleTranxListPage.getTable().getModel().getValueAt(
							saleTranxListPage.getTable().convertRowIndexToModel(row), 6));
					if(e.getClickCount() ==2) {
						try {
							if (tranxId != null && !tranxId.equals("")) {
								logAction(SaleOrderGUIManager.class,LOG_LEVEL.INFO, "Loading SO...");
								loadSaleOrder(tranxId, orderId);
								Runnable hideSplitThread = new Runnable() {						
									public void run() {
										try {
											//-- minimize sale tranx panel
											saleTranxSplit.getTopComponent().setMinimumSize(new Dimension());
											saleTranxSplit.setDividerLocation(0.0d);
											txtSONumber.requestFocus();
											if(dcOrderDate.getDate() == null) 
												dcOrderDate.setDate(new Date());
//											guiLocker.setLocked(tranxDetailParentPage, false);
										} catch (Exception e2) {
											e2.printStackTrace();
										}
									}
								};
								SwingUtilities.invokeLater(hideSplitThread);
							}
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}else if(e.getClickCount() ==1){												
//						guiLocker.setLocked(tranxDetailParentPage, true);
						tranxDetailParentPage.validate();
					}
					super.mouseReleased(e);
				}
			});
			txtItemId.addActionListener(ACT_SEARCH_PRODUCT);
			
			txtItemId.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					if(e.getKeyCode() == KeyEvent.VK_F2){
						chooseProduct();
					}
					super.keyReleased(e);
				}
			});
				
			txtItemId.getInputMap(
					JComponent.WHEN_IN_FOCUSED_WINDOW).put(
							KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, Event.CTRL_MASK), ACT_FOCUS_ITEM_ID);
			saleOrderItemListPage.getInputMap(
					JComponent.WHEN_IN_FOCUSED_WINDOW).put(
							KeyStroke.getKeyStroke(KeyEvent.VK_E, Event.CTRL_MASK), ACT_FOCUS_ITEM_ID);
			
			txtItemId.addFocusListener(new FocusListener() {
				public void focusLost(FocusEvent e) {
				}
				public void focusGained(FocusEvent e) {
					txtItemId.selectAll();
					txtItemQuantity.setText("");
					txtItemUom.setText("");
				}
			});
			
			saleOrderItemListPage.getTable().setCellSelectionEnabled(true);
			saleOrderItemListPage1.getTable().setCellSelectionEnabled(true);
			saleOrderItemListPage3.getTable().setCellSelectionEnabled(true);
			
			saleOrderItemListPage.getTable().addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {					
					if(e.getKeyCode() == KeyEvent.VK_SPACE && lastKey == KeyEvent.VK_CONTROL){
						txtItemId.requestFocus();
						txtItemId.selectAll();	
						if(saleOrderItemListPage.getTable().isEditing()){
							saleOrderItemListPage.getTable().getCellEditor().stopCellEditing();
						}
						e.consume();
					}else{
						lastKey = -1;
					}
					if(e.getKeyCode() == KeyEvent.VK_ENTER){
						
					}
					super.keyReleased(e);
				}
				@Override
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode() == KeyEvent.VK_CONTROL)
						lastKey = e.getKeyCode();
					super.keyPressed(e);
				}
			});		
			
			
			itemDetailContainer.getInputMap().put(KeyStroke.getKeyStroke(
                    KeyEvent.VK_F3, 0),
                    "check");
			itemDetailContainer.getActionMap().put("check", new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
					txtItemId.requestFocus();
				}
			});
			
			ACT_SAVE_ORDER.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK));			
			btnSaveOrder.getActionMap().put("saveOrderAction", ACT_SAVE_ORDER);
			btnSaveOrder.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
			        (KeyStroke) ACT_SAVE_ORDER.getValue(Action.ACCELERATOR_KEY), "saveOrderAction");
			
		} catch (Throwable e) {		
			logAction(SaleOrderGUIManager.class,LOG_LEVEL.ERROR, e);
		} 
	}
	String currentTranxId;

	void loadSaleOrder(String tranxId, String orderId){
		try {
			//-- cache transaction Id locally
			currentTranxId = tranxId;
			DailyTransaction dailyTranx = DailyTranxLogic.getInstance().get(
					tranxId);			
			if(dailyTranx == null)return;			
			SaleOrder so = null;
			if(orderId == null || orderId.equals("") || orderId.equals("null")){
				so = new SaleOrder(tranxId, Util.generateSaleOrder(),
						"VND", 
						-1, 
						"-1", 
						"RETAIL_SO", 
						new Date(), 
						"ACTIVE", 
						null, 
						null, 
						null, 
						null, 
						null, 
						null, 
						0.0d, 
						0.0d, 
						0.0d, 
						0.0d, 
						"New Sale Order", 
						-1, 
						null);
			}else{				
				so = SaleOrderLogic.getInstance().get(orderId);
				if(so == null){
					SystemMessageDialog.showMessageDialog(null
							, Language.getInstance().getString("can.not.load.so") + orderId
							, SystemMessageDialog.SHOW_OK_OPTION);
					return;			
				}				
				loadSaleOrderDetail(so);
			}
				
			if(so == null){				
				return;			
			}
			//-- Update UI
			txtSONumber.setText(so.getOrder_id());txtInvoiceNumber.setText(so.getInvoice_number());
			txtContractNumber.setText(so.getContract_number());
			txtOrderNote.setText(so.getDescription());txtPaymentDue.setText(null);
			txtCustomerDebtBalance.setText(null);
			
			
			List<SaleOrderType> soType = SaleOrderLogic.loadSaleOrderType();
			if(soType != null && soType.size()>0){
				SaleOrderLogic.updateComboBox(soType, comboSoType);
				comboSoType.setSelectedIndex(1);
			}
			
			Properties props = Util.getProperties("cfg/daily_tranx.cfg");
			String status = props.getProperty("transaction.status");
			if(status != null && !status.equals("")){
				String[] statusArr = status.split(",");
				List<String> statusList = Arrays.asList(statusArr);
				if(statusList != null && statusList.size()>0){
					SaleOrderLogic.updateComboBox(statusList, comboStatus);
					comboStatus.setSelectedIndex(0);
				}
			}
			
			List<Currency> currencyList = SaleOrderLogic.loadCurrency();
			if(currencyList != null && currencyList.size()>0){
				SaleOrderLogic.updateComboBox(currencyList, comboCurrencyType);
				comboCurrencyType.setSelectedItemById("VND");
			}
			
			List<Warehouse> whList = WarehouseLogic.loadWarehouse();
			if(whList != null && whList.size()>0)
				SaleOrderLogic.updateComboBox(whList, comboFromWarehouse);			
			if(whList != null && whList.size()>0)
				SaleOrderLogic.updateComboBox(whList, comboToWarehouse);	
			
			
			logAction(SaleOrderGUIManager.class,LOG_LEVEL.INFO, "Loading default customer...");
			selectedCustomer = CustomerLogic.getInstance().getCustomer(24);
			//-- load customer infor 
			loadCustomerInfo();		
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Throwable a){
			a.printStackTrace();
		}		
	}
	void loadSaleOrderDetail(SaleOrder so){
		//-- Load sale order detail
		logAction(SaleOrderGUIManager.class, LOG_LEVEL.INFO, "Start loading order detail for sale order "+so.getOrder_id());
		SaleOrderDetail[] dts = so.getDetails();
		logAction(SaleOrderGUIManager.class, LOG_LEVEL.INFO, "Total item line in this order "+dts.length);
		if(dts.length == 0){
			SystemMessageDialog.showMessageDialog(null
					, Language.getInstance().getString("can.not.load.so.detail") + so.getOrder_id()
					, SystemMessageDialog.SHOW_OK_OPTION);
		}
		String[] colHeader = dts[0].getObjectHeader();
		Object[][] rowData = new Object[dts.length][];
		for(int i=0;i<dts.length;i++){
			SaleOrderDetail sod = dts[i];
			rowData[i] = sod.toObjectArray();
		}
		CTableModel model1 = preparedModel(rowData, colHeader, editableColums);
		CTableModel model2 = preparedModel(rowData, colHeader, editableColums3);
		CTableModel model3 = preparedModel(rowData, colHeader, editableColums3);
		
		TableUtil.formatTable(saleOrderItemListPage, saleOrderItemListPage.getTable(), model1);
		TableUtil.formatTable(saleOrderItemListPage3, saleOrderItemListPage3.getTable(), model3);
		TableUtil.formatTable(saleOrderItemListPage1, saleOrderItemListPage1.getTable(), model2);
		toggleSOHeader(false);
		logAction(SaleOrderGUIManager.class, LOG_LEVEL.INFO, "End loading order detail for sale order "+so.getOrder_id());
	}
	void loadCustomerInfo(){
		if(selectedCustomer == null) return;
		txtCustomerName.setText(selectedCustomer.getName());
		txtPaymentDue.setText(String.valueOf(selectedCustomer.getDue_date()));
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

	public void guiActionPerformed(GUIActionEvent action) {
		GUIActionType guideType = action.getAction(); 
		Object srcObj = action.getSource();
		if(guideType.equals( GUIActionType.MINIMIZE_WINDOW)){
			int location = ((Dimension)action.getData()).height;
			if(srcObj.equals(SaleOrderGUIManager.class)){
				saleTranxSplit.setDividerLocation(location);
			}else{
				int newLoc = saleTranxSplit.getLeftComponent().getSize().height
						 + saleTranxSplit.getRightComponent().getSize().height
						 - location;
				saleTranxSplit.setDividerLocation(newLoc);
			}
		}
		if(guideType.equals( GUIActionType.MAXIMIZE_WINDOW)){
			saleTranxSplit.resetToPreferredSizes();
		}
	}

	public void valueChanged(ListSelectionEvent e) {

	}

	public void tableChanged(TableModelEvent e) {
		updateItemLineChange(e.getFirstRow(), e.getColumn(), false);
	}
	
	@Override
	protected void checkPermission() {
		
	}
	
	public void addCActionListener(CActionListener al) {
		vctCListener.add(al);
	}
	public void cActionPerformed(CActionEvent action) {
		try {
			/** Need to clarify transaction type for each tabbed pane to reload tranx*/
			if (action.getAction() == CActionEvent.RELOAD_TRANX_LIST)
				loadTransaction();
		} catch (Throwable e) {
			logAction(SaleOrderGUIManager.class,LOG_LEVEL.ERROR, e);
		}
	}
	public void removeCActionListener(CActionListener al) {
		vctCListener.remove(al);
	}
	void fireCAction(CActionEvent action) {
		for(CActionListener cAl:vctCListener){
			cAl.cActionPerformed(action);
		}
	}
	
	void chooseProduct(){
		ChooseProductDialogManager dlgGui = new ChooseProductDialogManager(Language.getInstance().getLocale());
		GenericDialog dlg = (GenericDialog)dlgGui.getRootComponent();		
		dlg.setIconImage(Util.getImage("logo32.png"));		
		dlg.centerDialogRelative(GUIManager.mainFrame, dlg);		
		//dlg.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dlg.pack();
		dlg.validate();
		dlg.setVisible(true);			
		selectedProduct = dlgGui.getChoosenProduct();			
		txtItemName.setText(selectedProduct != null? selectedProduct.getProductName():"");
		txtItemId.setText(selectedProduct != null? selectedProduct.getProductId():"");
		txtItemUom.setText(selectedProduct != null? String.valueOf(selectedProduct.getItemUOM()):"");
		if(selectedProduct != null)
			txtItemQuantity.requestFocus();
	}
	
	protected PriceList loadPriceList(String itemId)throws Exception{
		boolean validCustomer = Validator.validateEmpty(txtCustomerName, SystemConfiguration.DEFAULT_REQUIRED_TEXT, 
				Language.getInstance().getString("customer"));
		if(validCustomer == false)
			return null;
		String customerRank = selectedCustomer .getCustomer_rank_id();
		PriceList pl = CommonLogic.getPriceList(itemId, comboCurrencyType.getSelectedItemId().trim(), customerRank.trim());
		return pl;
	}
	
	protected void addItemLine(){
		try {
			boolean ret1 = true;
			boolean ret2 = Validator.validateNumber(txtItemQuantity, Long.class, SystemConfiguration.DEFAULT_REQUIRED_TEXT, 
					Language.getInstance().getString("item.quantity"));
			if(ret2 != true)
				txtItemQuantity.requestFocus();
			ret1 &= ret2;
			boolean ret3 = Validator.validateEmpty(txtItemUom, SystemConfiguration.DEFAULT_REQUIRED_TEXT, 
					Language.getInstance().getString("item.uom"));
			if(ret3 != true)
				txtItemUom.requestFocus();
			ret1 &= ret3;
			boolean ret4 = Validator.validateObjectNotNull(selectedProduct,Language.getInstance().getString("pl.product"));
			ret1 &= ret4;
			if(ret4 != true)
				SystemMessageDialog.showMessageDialog(null, Language.getInstance().getString("error.notChooseAProduct"), SystemMessageDialog.SHOW_OK_OPTION);
			if(ret1 != true)
				return;				
			
			SaleOrderDetail saleOrderDetail = new SaleOrderDetail(
					String.valueOf(saleOrderItemListPage.getTable().getModel().getRowCount())
					, txtSONumber.getText(), selectedProduct.getProductId()
					, Integer.parseInt((txtItemQuantity.getText()==null ||txtItemQuantity.getText().equals(""))?"0":txtItemQuantity.getText()) , 
					txtItemUom.getText());
			//-- Set product name for so detail
			saleOrderDetail.setItem_name(selectedProduct.getProductName());
			
			//-- Set unit price from price list attached to customer
			saleOrderDetail.setUnit_price(-1.0d);
			selectedPriceList = loadPriceList(selectedProduct.getProductId());
			if(selectedPriceList == null){
				SystemMessageDialog.showMessageDialog(null, 
						Language.getInstance().getString("price.not.available"),
						SystemMessageDialog.SHOW_OK_OPTION);
				return;
			}else{
				saleOrderDetail.setUnit_price(selectedPriceList.getSelling_price());
				saleOrderDetail.setDiscount_rate(selectedPriceList.getDiscount());
				double grossAmt = selectedPriceList.getSelling_price() * saleOrderDetail.getQuantity();
				saleOrderDetail.setGross_amount(grossAmt);
				saleOrderDetail.setDiscount_amount(selectedPriceList.getDiscount() * grossAmt/100);
				saleOrderDetail.setNet_amount(grossAmt - selectedPriceList.getDiscount()* grossAmt/100 );
			}
			
			Object[][] rowData = new Object[][]{saleOrderDetail.toObjectArray()};
			String[] colHeader = saleOrderDetail.getObjectHeader();

			if(saleOrderItemListPage.getTable().getModel().getRowCount() <=0){
				CTableModel model1 = preparedModel(rowData, colHeader, editableColums);
				CTableModel model2 = preparedModel(rowData, colHeader, editableColums3);
				CTableModel model3 = preparedModel(rowData, colHeader, editableColums3);
				
				TableUtil.formatTable(saleOrderItemListPage, saleOrderItemListPage.getTable(), model1);
				TableUtil.formatTable(saleOrderItemListPage3, saleOrderItemListPage3.getTable(), model3);
				TableUtil.formatTable(saleOrderItemListPage1, saleOrderItemListPage1.getTable(), model2);
				saleOrderItemListPage.getTable().setRowSorter(null);
				saleOrderItemListPage1.getTable().setRowSorter(null);
				saleOrderItemListPage3.getTable().setRowSorter(null);
				toggleSOHeader(false);
			}else{
				boolean valid = Validator.validateSOContainProduct(saleOrderItemListPage.getTable(), 2, saleOrderDetail);
				if(valid == true && saleOrderItemListPage.getTable().getModel().getRowCount()>0){
					//-- Duplicate product, do nothing				
					return;
				}
				CTableModel model = (CTableModel)saleOrderItemListPage.getTable().getModel();
				Object[][] data = model.addData(saleOrderDetail.toObjectArray());
				CTableModel model1 = preparedModel(data, colHeader, editableColums);
				CTableModel model2 = preparedModel(data, colHeader, editableColums3);
				CTableModel model3 = preparedModel(data, colHeader, editableColums3);
				
				TableUtil.formatTable(saleOrderItemListPage,  saleOrderItemListPage.getTable(), model1);				
				TableUtil.formatTable(saleOrderItemListPage1, saleOrderItemListPage1.getTable(), model2);
				TableUtil.formatTable(saleOrderItemListPage3, saleOrderItemListPage3.getTable(), model3);
			}
			updateSumaryData(saleOrderItemListPage.getTable().getModel());
			
			saleOrderItemListPage.getTable().requestFocus();
			saleOrderItemListPage.getTable().setRowSelectionInterval(0,0);
			saleOrderItemListPage.getTable().editCellAt(0,3);
			//- Select to edit cell at [column 3, last row]
			List<String> vatLst = Arrays.asList("0%","5%","10%","KCT");
			final CComboBox vatCb = new CComboBox();
			
			CommonLogic.updateComboBox(vatLst, vatCb);
			vatCb.setSelectedItemById("10%");
			
			DefaultCellEditor vatCellEdt = new DefaultCellEditor(vatCb){
				private static final long serialVersionUID = 1L;

				@Override
				public Object getCellEditorValue() {
					return vatCb.getSelectedItemId();
				}
			};
			
			vatCb.addActionListener(new AbstractAction() {				
				private static final long serialVersionUID = 1L;
				public void actionPerformed(ActionEvent e) {
					try {
						String vatRateString = vatCb.getSelectedItemId();
						int row = saleOrderItemListPage.getTable()
								.getEditingRow();
						int col = saleOrderItemListPage.getTable()
								.getEditingColumn();
						if (row < 0 || col < 0)
							return;						
						if (vatRateString.equalsIgnoreCase("0%") || vatRateString.equalsIgnoreCase("KCT"))
							saleOrderItemListPage.getTable().getModel()
									.setValueAt(new Double(0.0d), row, col + 1);
						else {
							updateItemLineChange(row,VAT_RATE_COL, false);							
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					
				}
			});
			saleOrderItemListPage.getTable().getColumnModel().getColumn(VAT_RATE_COL).setCellEditor(vatCellEdt);			
			saleOrderItemListPage.getTable().changeSelection(saleOrderItemListPage.getTable().getModel().getRowCount()-1,QTY_COL,false, false);
		} catch (Throwable e) {
			e.printStackTrace();
			logAction(SaleOrderGUIManager.class,LOG_LEVEL.ERROR, e);
		}
		txtItemQuantity.setText("");
		txtItemUom.setText("");	
		selectedProduct = null;
		selectedPriceList = null;
	}

	void updateItemLineChange(int row, int col, boolean isDistributeDiscount) {
		saleOrderItemListPage.getModel().removeTableModelListener(SaleOrderGUIManager.this);
		saleOrderItemListPage1.getModel().removeTableModelListener(SaleOrderGUIManager.this);
		try {
			long itemQty = Long.parseLong(String.valueOf(saleOrderItemListPage.getTable().getModel()
					.getValueAt(
							saleOrderItemListPage.getTable().convertRowIndexToModel(row), QTY_COL)));
			double unitPrice = (Double) saleOrderItemListPage.getTable()
					.getModel().getValueAt(saleOrderItemListPage.getTable().convertRowIndexToModel(row), UNIT_PRICE_COL);
			double grossAmt = (Double) saleOrderItemListPage.getTable()
					.getModel().getValueAt(saleOrderItemListPage.getTable().convertRowIndexToModel(row), GROSS_AMT_COL);
			if (col == QTY_COL) {
				grossAmt = itemQty * unitPrice;
				saleOrderItemListPage.getTable().getModel().setValueAt(
						grossAmt, row, GROSS_AMT_COL);
			}
			String vatRate = (String) saleOrderItemListPage1.getTable()
					.getModel().getValueAt(row, VAT_RATE_COL);
			double vatRateNum = (vatRate != null && vatRate
					.equalsIgnoreCase("5%")) ? 0.05d
					: (vatRate != null && vatRate.equalsIgnoreCase("10%")) ? 0.1d
							: 0.0d;

			double vatAmt = ((Double) saleOrderItemListPage1.getTable()
					.getModel().getValueAt(saleOrderItemListPage1.getTable().convertRowIndexToModel(row), VAT_AMT_COL)).doubleValue();
			if (col == VAT_RATE_COL || col == QTY_COL) {
				vatAmt = vatRateNum * grossAmt;
				saleOrderItemListPage1.getTable().getModel().setValueAt(vatAmt,
						row, VAT_AMT_COL);
			}

			double discountRate = Double.parseDouble(String.valueOf(saleOrderItemListPage1.getTable()
					.getModel().getValueAt(saleOrderItemListPage1.getTable().convertRowIndexToModel(row), DISCOUNT_RATE_COL)));
			double discountAmt = ((Double) saleOrderItemListPage1.getTable()
					.getModel().getValueAt(saleOrderItemListPage1.getTable().convertRowIndexToModel(row), DISCOUNT_AMT_COL))
					.doubleValue();
			if (col == DISCOUNT_RATE_COL) {
				discountAmt = grossAmt/100 * discountRate;
				saleOrderItemListPage1.getTable().getModel().setValueAt(
						discountAmt, row, DISCOUNT_AMT_COL);
			}

			double netAmt = grossAmt - vatAmt - discountAmt;
			saleOrderItemListPage1.getTable().getModel().setValueAt(netAmt,
					row, NET_AMT_COL);
			saleOrderItemListPage.getTable().getModel().setValueAt(netAmt,
					row, NET_AMT_COL);
			
		} catch (Exception e2) {
			logAction(SaleOrderGUIManager.class,LOG_LEVEL.ERROR, e2);
			e2.printStackTrace();
		}
		saleOrderItemListPage.getModel().addTableModelListener(SaleOrderGUIManager.this);
		saleOrderItemListPage1.getModel().addTableModelListener(SaleOrderGUIManager.this);
		updateSumaryData(saleOrderItemListPage.getTable().getModel());
	}
	void updateSumaryData(TableModel model){		
		double sumDiscount = 0.0d, sumGross = 0.0d, sumNet = 0.0d, sumVat = 0.0d;		
		for(int i=0;i<model.getRowCount();i++){	
			sumGross += (Double)model.getValueAt(i, GROSS_AMT_COL);
			sumDiscount += (Double)model.getValueAt(i, DISCOUNT_AMT_COL);
			sumNet += (Double)model.getValueAt(i, NET_AMT_COL);
			sumVat += (Double)model.getValueAt(i, VAT_AMT_COL);			
		}		
		txtSumDiscountAmount.setText(SystemConfiguration.decfm.format(sumDiscount));
		txtSumGrossAmount.setText(SystemConfiguration.decfm.format(sumGross));
		txtSumNetAmount.setText(SystemConfiguration.decfm.format(sumNet));
		txtSumVATAmount.setText(SystemConfiguration.decfm.format(sumVat));
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
				if(column ==UNIT_PRICE_COL || column ==GROSS_AMT_COL
						|| column == DISCOUNT_RATE_COL|| column == DISCOUNT_AMT_COL
						|| column == VAT_AMT_COL || column == NET_AMT_COL)
					return Double.class;
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
	protected void toggleSOHeader(boolean enabled){
		comboCurrencyType.setEditable(enabled);
		comboCurrencyType.setEnabled(enabled);
		btnChooseCustomer.setEnabled(enabled);		
	}	
	//------
	
	boolean validateOrder(){
		boolean ret = true;
		ret &= saleOrderItemListPage.getTable().getModel().getRowCount() != 0;
		ret &= Validator.validateEmpty(txtSONumber, SystemConfiguration.DEFAULT_REQUIRED_TEXT, Language.getInstance().getString("so.number") );
		ret &= Validator.validateEmpty(txtPaymentDue, SystemConfiguration.DEFAULT_REQUIRED_TEXT, Language.getInstance().getString("payment.due"));
		ret &= Validator.validateNumber(txtPaymentDue, Integer.class, SystemConfiguration.DEFAULT_REQUIRED_TEXT, Language.getInstance().getString("payment.due"));
		ret &= Validator.validateEmpty(txtCustomerName, SystemConfiguration.DEFAULT_REQUIRED_TEXT, Language.getInstance().getString("customer"));		
		ret &= Validator.validateSelectedComboBox(comboSoType, SystemConfiguration.DEFAULT_REQUIRED_TEXT, Language.getInstance().getString("so.type"));		
		ret &= Validator.validateSelectedComboBox(comboStatus, SystemConfiguration.DEFAULT_REQUIRED_TEXT, Language.getInstance().getString("order.status"));
		ret &= Validator.validateSelectedComboBox(comboCurrencyType, SystemConfiguration.DEFAULT_REQUIRED_TEXT, Language.getInstance().getString("currency.type"));
		ret &= Validator.validateSelectedComboBox(comboFromWarehouse, SystemConfiguration.DEFAULT_REQUIRED_TEXT, Language.getInstance().getString("from.warehouse"));		
		ret &= Validator.validateDateChooser(dcOrderDate, Language.getInstance().getString("order.date"));
		ret &= Validator.validateTableNotNull(saleOrderItemListPage.getTable(), Language.getInstance().getString("so.tabbed.item.detail"));
		return ret;
	}
	//====================
	public Action ACT_NEXT_STEP = new AbstractAction() {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			
		}
	};
	public Action ACT_PRINT_INVOICE = new AbstractAction() {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			
		}
	};
	
	public Action ACT_NEW_ORDER= new AbstractAction() {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {			
			
		}
	};
	public Action ACT_SAVE_ORDER= new AbstractAction() {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			btnSaveOrder.requestFocusInWindow();
			boolean orderCanSave = true;
			orderCanSave = validateOrder();			
			if(orderCanSave != true)
				return;
			try {
				logAction(SaleOrderGUIManager.class,LOG_LEVEL.DEBUG, ":Start saving order...");
				// -- Step 1: build SaleOrder with SaleOrderDetail
				SaleOrderDetail[] details = new SaleOrderDetail[saleOrderItemListPage
						.getModel().getRealRowCount()];
				for (int i = 0; i < details.length; i++) {
					SaleOrderDetail soDtl = new SaleOrderDetail(
							(String) saleOrderItemListPage.getModel()
									.getValueAt(saleOrderItemListPage.getTable().convertRowIndexToModel(i), 1), (String) txtSONumber
									.getText(), (String) saleOrderItemListPage
									.getModel().getValueAt(saleOrderItemListPage.getTable().convertRowIndexToModel(i), 2),
							Integer.parseInt(String.valueOf(saleOrderItemListPage.getModel()
									.getValueAt(saleOrderItemListPage.getTable().convertRowIndexToModel(i), QTY_COL))),
							(String) saleOrderItemListPage.getModel()
									.getValueAt(saleOrderItemListPage.getTable().convertRowIndexToModel(i), UOM_COL));
					soDtl.setItem_name((String) saleOrderItemListPage
							.getModel().getValueAt(saleOrderItemListPage.getTable().convertRowIndexToModel(i), QTY_COL - 1));
					soDtl.setUnit_price((Double)saleOrderItemListPage
							.getModel().getValueAt(saleOrderItemListPage.getTable().convertRowIndexToModel(i), UNIT_PRICE_COL));
					soDtl.setGross_amount((Double)  saleOrderItemListPage
							.getModel().getValueAt(saleOrderItemListPage.getTable().convertRowIndexToModel(i), GROSS_AMT_COL));
					soDtl.setDiscount_rate((Double) saleOrderItemListPage
							.getModel().getValueAt(saleOrderItemListPage.getTable().convertRowIndexToModel(i), DISCOUNT_RATE_COL));
					soDtl.setDiscount_amount((Double)  saleOrderItemListPage
							.getModel().getValueAt(saleOrderItemListPage.getTable().convertRowIndexToModel(i), DISCOUNT_AMT_COL));
					soDtl.setVat_rate((String) saleOrderItemListPage.getModel()
							.getValueAt(saleOrderItemListPage.getTable().convertRowIndexToModel(i), VAT_RATE_COL));
					soDtl.setVat_amount((Double)  saleOrderItemListPage
							.getModel().getValueAt(saleOrderItemListPage.getTable().convertRowIndexToModel(i), VAT_AMT_COL));
					soDtl.setNet_amount(( Double) saleOrderItemListPage
							.getModel().getValueAt(saleOrderItemListPage.getTable().convertRowIndexToModel(i), NET_AMT_COL));					
					soDtl.setDebit_account((Integer) saleOrderItemListPage
							.getModel().getValueAt(saleOrderItemListPage.getTable().convertRowIndexToModel(i), NET_AMT_COL+1));
					soDtl.setCredit_account((Integer) saleOrderItemListPage
							.getModel().getValueAt(saleOrderItemListPage.getTable().convertRowIndexToModel(i), NET_AMT_COL+2));
					soDtl.setDiscount_account((Integer) saleOrderItemListPage
							.getModel().getValueAt(saleOrderItemListPage.getTable().convertRowIndexToModel(i), NET_AMT_COL+3));					
					details[i] = soDtl;

				}
				SaleOrder so = new SaleOrder(currentTranxId, txtSONumber
						.getText(), comboCurrencyType.getSelectedItemId(),
						selectedCustomer.getId(), comboFromWarehouse
								.getSelectedItemId(), comboSoType
								.getSelectedItemId(), dcOrderDate.getDate(),
						comboStatus.getSelectedItemId(), "", "", dcOrderDate
								.getDate(), "", "", "invoice_num"
						, SystemConfiguration.decfm.parse(txtSumGrossAmount.getText()).doubleValue()
						, SystemConfiguration.decfm.parse(txtSumVATAmount.getText()).doubleValue()
						, 0.0d
						, SystemConfiguration.decfm.parse(txtSumNetAmount.getText()).doubleValue()
						, txtOrderNote.getText(), -1, details);
				//-- Updating transaction number
				so.setTranx_id(currentTranxId);
				SaleOrderLogic.getInstance().insert(so);				
				SystemMessageDialog.showMessageDialog(null, "Save order success!", SystemMessageDialog.SHOW_OK_OPTION);
				
				DailyTransaction dTranx = DailyTranxLogic.getInstance().get(currentTranxId);
				dTranx.setVoucher_id(so.getOrder_id());				
				dTranx.setCurrent_step("ISSUE_SLIP");
				dTranx.setCustomer_id(selectedCustomer.getId());
				DailyTranxLogic.getInstance().update(dTranx);	
				loadTransaction();
				clearForm();				
				fireCAction(new CActionEvent(this, CActionEvent.RELOAD_TRANX_LIST, null));				
				
			} catch (Throwable t) {
				SystemMessageDialog.showMessageDialog(null, t.getMessage(), SystemMessageDialog.SHOW_OK_OPTION);
				t.printStackTrace();
			}
			
		}
	};
	void clearForm(){
		txtSONumber.setText("");dcOrderDate.setDate(null);saleOrderItemListPage.getModel().setData(null);saleOrderItemListPage.validate();
		txtSumDiscountAmount.setText("");txtSumGrossAmount.setText("");
		txtSumNetAmount.setText("");txtSumVATAmount.setText("");txtItemId.setText("");txtItemName.setText("");
		txtItemQuantity.setText("");txtItemUom.setText("");		
		saleOrderItemListPage.getTable().setData(null);
		saleOrderItemListPage1.getTable().setData(null);
		saleOrderItemListPage3.getTable().setData(null);
		toggleSOHeader(true);
	}
	public Action ACT_FOCUS_ITEM_ID = new AbstractAction() {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			txtItemId.requestFocus();
		}
	};
	public Action  ACT_ADD_ITEM_TO_SO = new AbstractAction() {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			addItemLine();
		}
	};	
	public Action ACT_REFRESH_DEBT = new AbstractAction() {		
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {		
			if(txtCustomerName.getText() == null || txtCustomerName.getText().equals("")){
				SystemMessageDialog.showMessageDialog(GUIManager.mainFrame, "must.select.customer.first"
						, SystemMessageDialog.SHOW_OK_OPTION);
			}
		}
	};
	
	public Action ACT_OPEN_PRODUCT_DIALOG = new AbstractAction() {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			selectedProduct =  null;
			chooseProduct();
		}
	};
	public Action ACT_SEARCH_PRODUCT = new AbstractAction() {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			try {
				selectedProduct = null;
				if (txtItemId.getText() == null
						|| txtItemId.getText().equals(""))
					return;
				selectedProduct = ProductLogic.getInstance().getItem(
						txtItemId.getText().replaceAll("\n", ""));
				if (selectedProduct == null)
					chooseProduct();
				else {
					txtItemQuantity.requestFocus();
					txtItemName.setText(selectedProduct.getProductName());

					ProductUOM uom = ProductCatLogic.getInstance().getUOM(
							selectedProduct.getItemUOM());
					if (uom != null)
						txtItemUom.setText(uom.getUomName());
					txtItemQuantity.setText("1");
					txtItemQuantity.selectAll();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}	
		}
	};
		
	
	public Action ACT_OPEN_CUSTOMER_DIALOG = new AbstractAction() {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			ChooseCustomerDialogManager dlgGui = new ChooseCustomerDialogManager(Language.getInstance().getLocale());
			GenericDialog dlg = (GenericDialog)dlgGui.getRootComponent();		
			dlg.setIconImage(Util.getImage("logo32.png"));
			//dlg.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dlg.pack();
			dlg.validate();
			dlg.centerDialogRelative(GUIManager.mainFrame, dlg);
			dlg.setLocation(dlg.getLocation().x, 0);
			dlg.setVisible(true);			
			selectedCustomer = dlgGui.getChoosenCustomer();
			txtCustomerName.setText(selectedCustomer != null? selectedCustomer.getName():"");			
			txtPaymentDue.setText(selectedCustomer != null? String.valueOf(selectedCustomer.getDue_date()):"");
			if(selectedCustomer != null){
				txtCustomerName.setText(selectedCustomer.getName());			
				txtPaymentDue.setText(String.valueOf(selectedCustomer.getDue_date()));
			}
		}
	};
	public Action ACT_ONLINE_STOCK = new AbstractAction() {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			DialogHelper.openItemStockDialog(GUIManager.mainFrame);
		}
	};
	public Action ACT_DISTRIBUTE_EXPENSE = new AbstractAction() {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			boolean ret = true;
			ret &= Validator.validateEmpty(txtDiscountAmount, txtDiscountAmount.getBackground()
					, Language.getInstance().getString("discount.amount"));
			ret &= Validator.validateNumber(txtDiscountAmount, Double.class, txtDiscountAmount.getBackground()
					, Language.getInstance().getString("discount.amount"));
			ret &= Validator.validateTableNotNull(saleOrderItemListPage1.getTable()
					, Language.getInstance().getString("so.tabbed.item.detail"));			
			if(ret != true) 
				return;
			Enumeration<AbstractButton> elm =  btnDistributeMethod.getElements();
			boolean isSelected = false;
			while (elm.hasMoreElements()){
				AbstractButton b = elm.nextElement();
				if(b instanceof JRadioButton && ((JRadioButton)b).isSelected())
					isSelected = true;
			}
			if(isSelected != true){
				SystemMessageDialog.showMessageDialog(null, Language.getInstance().getString("select.distribute.method")
						, SystemMessageDialog.SHOW_OK_OPTION);
				return;
			}
			int userChoice = SystemMessageDialog.showConfirmDialog(null, Language.getInstance().getString("confirm.distribute.method")
					, SystemMessageDialog.SHOW_OK_CANCEL_OPTION);
			if(userChoice != 0)
				return;
			double vatDistributeAmount = 0.0d;
			double sumGrossAmt = 0.0d;
			try {
				vatDistributeAmount = SystemConfiguration.decfm.parse(
						txtDiscountAmount.getText()).doubleValue();
				sumGrossAmt = SystemConfiguration.decfm.parse(
						txtSumGrossAmount.getText()).doubleValue();				
			} catch (Exception e2) {
				logAction(SaleOrderGUIManager.class,LOG_LEVEL.ERROR, e2);
				e2.printStackTrace();
			}
			if(vatDistributeAmount == 0.0d)
				return;
			//-- stop listen to table model, handle event manually
			saleOrderItemListPage1.getModel().removeTableModelListener(SaleOrderGUIManager.this);
			double oneCentDiscount = vatDistributeAmount/sumGrossAmt;
			for(int i=0;i<saleOrderItemListPage1.getTable().getModel().getRowCount();i++){
				//-- Calculate discount rate base on amount				
				try {
					double lineGrossAmt =(Double)saleOrderItemListPage1.getModel()
									.getValueAt(saleOrderItemListPage1.getTable().convertRowIndexToModel(i), GROSS_AMT_COL);
					double lineDiscount = oneCentDiscount * lineGrossAmt;
					saleOrderItemListPage1.getModel().setValueAt(lineDiscount, i, DISCOUNT_AMT_COL);
					saleOrderItemListPage1.getModel().setValueAt(0.0d, i, DISCOUNT_RATE_COL);	
					saleOrderItemListPage1.getModel().addTableModelListener(SaleOrderGUIManager.this);
					updateItemLineChange(i, -1, true);
					saleOrderItemListPage1.getModel().removeTableModelListener(SaleOrderGUIManager.this);
				} catch (Exception e2) {
					logAction(SaleOrderGUIManager.class,LOG_LEVEL.ERROR, e2);
					e2.printStackTrace();
				}
			}
			//-- Re-listen to table model change
			saleOrderItemListPage1.getModel().addTableModelListener(SaleOrderGUIManager.this);
			updateSumaryData(saleOrderItemListPage1.getModel());
			
		}
	};	
}
