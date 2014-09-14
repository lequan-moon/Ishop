package com.nn.ishop.client.gui.sale;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jboss.logging.Logger;

import com.nn.ishop.client.CActionEvent;
import com.nn.ishop.client.CActionListener;
import com.nn.ishop.client.config.SystemConfiguration;
import com.nn.ishop.client.gui.AbstractGUIManager;
import com.nn.ishop.client.gui.GUIActionEvent;
import com.nn.ishop.client.gui.GUIActionListener;
import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.GUIActionEvent.GUIActionType;
import com.nn.ishop.client.gui.admin.customer.CTabbedPane;
import com.nn.ishop.client.gui.components.CButton2;
import com.nn.ishop.client.gui.components.CLineBorder;
import com.nn.ishop.client.gui.components.CPaging;
import com.nn.ishop.client.gui.components.CTable;
import com.nn.ishop.client.gui.components.CTextField;
import com.nn.ishop.client.gui.components.CTwoModePanel;
import com.nn.ishop.client.gui.components.CWhitePanel;
import com.nn.ishop.client.gui.helper.TableUtil;
import com.nn.ishop.client.gui.purchase.GRNMainGUIManager;
import com.nn.ishop.client.gui.purchase.PurchaseMasterGUIManager;
import com.nn.ishop.client.logic.admin.CustomerLogic;
import com.nn.ishop.client.logic.admin.UserLogic;
import com.nn.ishop.client.logic.admin.WarehouseLogic;
import com.nn.ishop.client.logic.sale.SaleOrderLogic;
import com.nn.ishop.client.logic.transaction.DailyTranxLogic;
import com.nn.ishop.client.util.Util;
import com.nn.ishop.server.dto.bstranx.DailyTransaction;
import com.nn.ishop.server.dto.bstranx.TransactionType;
import com.nn.ishop.server.dto.customer.Customer;
import com.nn.ishop.server.dto.sale.SaleOrder;
import com.nn.ishop.server.dto.user.Employee;
import com.nn.ishop.server.dto.warehouse.Warehouse;


public class SaleMasterGUIManager extends AbstractGUIManager implements
		CActionListener, GUIActionListener, ListSelectionListener{
	CWhitePanel saleOrderComp, pnlTranxDetail, /*actionPanel,*/
	saleOrderWarehouseIssueSlipComp, saleOrderStatsComp;
	CTabbedPane saleTabbedPane, tranxDetailTabbedPane;
	JSplitPane saleTranxSplit;
	CButton2 newTranxButton,quoteButton,reservedItemButton,createSOButton,debtMgmtButton,issueSlipButton;

	CPaging saleMasterTranxListPage;
	
	CTextField txtCustomerName,txtSOAmountMoney,txtSOCurrencyType,txtSONumber,txtSOVATTax,txtSOType;
	CTextField txtSOOtherExpense,txtFromWarehouse,txtSOGrossAmount,txtSOCreator,txtSOCreateDate,txtSOModifyUser;
	CTextField txtSOModifyDate, txtSOPhase, txtSOCurrentVoucherNumber;
	
	CTwoModePanel tranxTwoModePanel, detailTwoModePanel;
	CWhitePanel    pnlMasterTranxList, tranxDetailTabbedPaneContainer;
	
	public SaleMasterGUIManager (String locale){
		setLocale(locale);
		init();
	}
	void init(){
		if (getLocale() != null && !getLocale().equals("")){
			initTemplate(this, "sale/saleMasterPage.xml", getLocale());
		}else{
			initTemplate(this, "sale/saleMasterPage.xml");		
			}
		render();
//		bindEventHandlers();
		SaleOrderGUIManager saleOrderGUI = null;
		if(saleOrderComp != null){
			saleOrderGUI = new SaleOrderGUIManager(getLocale());
			saleOrderComp.add(saleOrderGUI.getRootComponent(),BorderLayout.CENTER);
			saleOrderGUI.addCActionListener(this);
			this.addCActionListener(saleOrderGUI);
			
		}
		
		SaleIssueSlipManager saleIssueSlipGUI = null;
		if(saleOrderWarehouseIssueSlipComp != null){
			saleIssueSlipGUI = new SaleIssueSlipManager(getLocale());
			saleOrderWarehouseIssueSlipComp.add(saleIssueSlipGUI.getRootComponent(),BorderLayout.CENTER);
			if(saleOrderGUI != null) {
				saleOrderGUI.addCActionListener(saleIssueSlipGUI);
			}
		}
		
		SaleStatsManager statPage = null;
		
		if(saleOrderStatsComp != null){
			statPage = new SaleStatsManager(getLocale());
			saleOrderStatsComp.add(statPage.getRootComponent(),BorderLayout.CENTER);
		}
		//-- be avoided recursive listener
		
		if(saleIssueSlipGUI != null){
			this.addCActionListener(saleIssueSlipGUI);		
			saleIssueSlipGUI.addCActionListener(this);
		}
		if(detailTwoModePanel != null){//- privilege issue
			detailTwoModePanel.addGUIActionListener(this);
			detailTwoModePanel.setManagerClazz(String.class);
			detailTwoModePanel.addContent(tranxDetailTabbedPaneContainer);
		}
		
		if(tranxTwoModePanel != null){//- privilege issue
			tranxTwoModePanel.addGUIActionListener(this);
			tranxTwoModePanel.setManagerClazz(SaleMasterGUIManager.class);			
			tranxTwoModePanel.addContent(pnlMasterTranxList);
		}
		
		bindEventHandlers();
	}
	@Override
	protected void applyStyles() {
		Map<Object, String> tabTitleMap = new HashMap<Object, String>();		
		//Map<Object, ImageIcon> tabIconMap = new HashMap<Object, ImageIcon>();
		List<Object> tabObjects = new ArrayList<Object>();
		
		if(saleTranxSplit != null){
			customizeSplitPaneHideSecondComponent(saleTranxSplit);
			saleTranxSplit.setOrientation(0);
			tabObjects.add(saleTranxSplit);
			tabTitleMap.put(saleTranxSplit,Language.getInstance().getString("sale.tabbedpane.txlist"));
		}
		
		if(saleOrderComp != null){
			tabObjects.add(saleOrderComp);
			tabTitleMap.put(saleOrderComp,Language.getInstance().getString("sale.tabbedpane.saleorder"));
		}
		
		if(saleOrderWarehouseIssueSlipComp != null){
			tabObjects.add(saleOrderWarehouseIssueSlipComp);
			tabTitleMap.put(saleOrderWarehouseIssueSlipComp,Language.getInstance().getString("sale.tabbedpane.issue.slip"));
		}
		
		if(saleOrderStatsComp != null){
			tabObjects.add(saleOrderStatsComp);
			tabTitleMap.put(saleOrderStatsComp,Language.getInstance().getString("sale.tabbedpane.sale.stats"));
		}
		
		/*if(tranxDetailTabbedPane != null){
			tabObjects.add(tranxDetailTabbedPane);
			tabTitleMap.put(tranxDetailTabbedPane,Language.getInstance().getString("sale.tabbedpane.detail.txinfor"));
		}*/
		
		//cTabbedPanePanel.setBackground(Color.WHITE);
		
		for(int i=0;i<tabObjects.size();i++){
			Object obj = tabObjects.get(i);
			saleTabbedPane.setTitleAt(i,tabTitleMap.get(obj));
			//saleTabbedPane.setIconAt(i,tabIconMap.get(obj));
		}
		
		/*if(saleTranxSplit != null){
			customizeSplitPaneHideSecondComponent(saleTranxSplit);
			saleTranxSplit.setOrientation(0);
		}
		
		saleTabbedPane.setTitleAt(0, Language.getInstance().getString("sale.tabbedpane.txlist"));
		saleTabbedPane.setTitleAt(1, Language.getInstance().getString("sale.tabbedpane.saleorder"));
		saleTabbedPane.setTitleAt(2, Language.getInstance().getString("sale.tabbedpane.issue.slip"));
		saleTabbedPane.setTitleAt(3, Language.getInstance().getString("sale.tabbedpane.sale.stats"));
		*/
		if(pnlTranxDetail != null){
			tranxDetailTabbedPane.setTitleAt(0, Language.getInstance().getString("sale.tabbedpane.detail.txinfor"));
			//-- Set border
//			pnlTranxDetail.setBorder(BorderFactory.createCompoundBorder(
//					new CLineBorder(null,CLineBorder.DRAW_BOTTOM_LEFT_RIGHT_BORDER), 
//					new EmptyBorder(10,3,3,3))
//					);
		}
	}

	@Override
	protected void bindEventHandlers(){
		try {
			
			loadDailyTransaction();
			if(saleMasterTranxListPage != null)
			saleMasterTranxListPage.getTable().addMouseListener(new MouseAdapter() {				
				@Override
				public void mouseClicked(MouseEvent e) {					
					try {
						int row1 = saleMasterTranxListPage.getTable()
								.rowAtPoint(e.getPoint());
						int row = saleMasterTranxListPage.getTable()
								.convertRowIndexToModel(row1);
						String phase = String.valueOf(saleMasterTranxListPage
								.getTable().getModel().getValueAt(row, 4));
						final String tranxId = String
								.valueOf(saleMasterTranxListPage.getTable()
										.getModel().getValueAt(
												saleMasterTranxListPage.getTable().convertRowIndexToModel(row), 1));
						logger.info("Selected sale order transaction phase = "
								+ phase);
						if (phase != null && phase.equalsIgnoreCase("INIT")) {
							// never happen
						} else if (phase != null
								&& phase.equalsIgnoreCase("SALE_ORDER")) {
							setEnabledFlowButton(3);
							if (e.getClickCount() == 2 && tranxId != null
									&& !tranxId.equals("")) {
								saleTabbedPane.setSelectedIndex(1);
								fireCAction(new CActionEvent(this,
										CActionEvent.UPDATE, tranxId));
							}
						} else if (phase != null
								&& phase.equalsIgnoreCase("PAYMENT")) {
							setEnabledFlowButton(4);
						} else if (phase != null
								&& phase.equalsIgnoreCase("ISSUE_SLIP")) {
							setEnabledFlowButton(5);
							if (e.getClickCount() == 2 && tranxId != null
									&& !tranxId.equals("")) {
								saleTabbedPane.setSelectedIndex(2);
								fireCAction(new CActionEvent(this,
										CActionEvent.UPDATE, tranxId));
							}
						}
						if (tranxId != null && !tranxId.equals("")) {
							Runnable loadTranxDetail = new Runnable() {
								public void run() {
									loadTransactionDetail(tranxId);
								}
							};
							SwingUtilities.invokeLater(loadTranxDetail);
						}
						super.mouseReleased(e);
					} catch (Exception e2) {
						logAction(SaleMasterGUIManager.class, LOG_LEVEL.ERROR, e);
						//e2.printStackTrace();
					}
				}
			});
			if(saleMasterTranxListPage != null)
			saleMasterTranxListPage.getTable().addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP){
						int row = saleMasterTranxListPage.getTable().getSelectedRow();
						String phase = String.valueOf(saleMasterTranxListPage.getTable().getModel().getValueAt(
								saleMasterTranxListPage.getTable().convertRowIndexToModel(row), 4));
						final String tranxId = String.valueOf(saleMasterTranxListPage.getTable().getModel().getValueAt(
								saleMasterTranxListPage.getTable().convertRowIndexToModel(row), 1));
						if(phase != null && phase.equalsIgnoreCase("INIT")){
							// never happen
						}else if(phase != null && phase.equalsIgnoreCase("SALE_ORDER")){
							setEnabledFlowButton(3);
						}else if(phase != null && phase.equalsIgnoreCase("PAYMENT")){
							setEnabledFlowButton(4);
						}else if(phase != null && phase.equalsIgnoreCase("ISSUE_SLIP")){
							setEnabledFlowButton(5);
						}
						if(tranxId != null && !tranxId.equals("")){
							Runnable loadTranxDetail = new Runnable() {							
								public void run() {
									loadTransactionDetail(tranxId);								
								}
							};
							SwingUtilities.invokeLater(loadTranxDetail);
						}
					}
					if(e.getKeyCode() == KeyEvent.VK_ENTER){
						int row = saleMasterTranxListPage.getTable().getSelectedRow();						
						final String tranxId = String.valueOf(saleMasterTranxListPage.getTable().getModel().getValueAt(
								saleMasterTranxListPage.getTable().convertRowIndexToModel(row), 1));
						if(tranxId != null && !tranxId.equals("")){
							 saleTabbedPane.setSelectedIndex(1);
							 fireCAction(new CActionEvent(this, CActionEvent.UPDATE, tranxId));
						 }
					}	
					super.keyReleased(e);
				}
			});
			//-- Assign F5 to refresh tranx list action
			ACT_REFRESH_TRANX_LIST.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
			if(btnF5!= null){
				btnF5.getActionMap().put("refreshAction", ACT_REFRESH_TRANX_LIST);
				btnF5.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				        (KeyStroke) ACT_REFRESH_TRANX_LIST.getValue(Action.ACCELERATOR_KEY), "refreshAction");
			}
		} catch (Throwable e) {
			e.printStackTrace();
			logger.error(e);			
		}
		
	}
	/**
	 * Set enabled flow button
	 * newTranxButton = 0,quoteButton =1 , reservedItemButton=2,createSOButton=3,debtMgmtButton=4,saleButton=5
	 * @param idx
	 */
	void setEnabledFlowButton(int idx){
		switch (idx){
		case 0:
			// never happen
		case 1:
			quoteButton.setEnabled(true);reservedItemButton.setEnabled(false);
			createSOButton.setEnabled(false);debtMgmtButton.setEnabled(false);
			issueSlipButton.setEnabled(false);
			break;
		case 2:
			quoteButton.setEnabled(false);reservedItemButton.setEnabled(true);
			createSOButton.setEnabled(false);debtMgmtButton.setEnabled(false);
			issueSlipButton.setEnabled(false);
			break;
		case 3:
			quoteButton.setEnabled(false);reservedItemButton.setEnabled(false);
			createSOButton.setEnabled(true);debtMgmtButton.setEnabled(false);
			issueSlipButton.setEnabled(false);
			break;
		case 4:
			quoteButton.setEnabled(false);reservedItemButton.setEnabled(false);
			createSOButton.setEnabled(false);debtMgmtButton.setEnabled(true);
			issueSlipButton.setEnabled(false);
			break;
		case 5:
			quoteButton.setEnabled(false);reservedItemButton.setEnabled(false);
			createSOButton.setEnabled(false);debtMgmtButton.setEnabled(false);
			issueSlipButton.setEnabled(true);
			break;
		default:
			break;
			
		}
		
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

	public void cActionPerformed(CActionEvent action) {
		if(action.getAction() == CActionEvent.RELOAD_TRANX_LIST){
			try {
				loadDailyTransaction();
			} catch (Throwable t) {
				logger.error(t);
			}
		}
	}
	
	
	public void guiActionPerformed(GUIActionEvent action) {
		GUIActionType guideType = action.getAction(); 
		Object srcObj = action.getSource();
		if(guideType.equals( GUIActionType.MINIMIZE_WINDOW)){
			int location = ((Dimension)action.getData()).height;
			if(srcObj.equals(SaleMasterGUIManager.class)){
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
	
	@Override
	protected void checkPermission() {
		
	}
	java.util.Vector<CActionListener> cListener = new Vector<CActionListener>();
	public void addCActionListener(CActionListener al) {
		cListener.add(al);
	}
	public void removeCActionListener(CActionListener al) {
		cListener.remove(al);
	}
	
	public static final String SALE_TX_TYPE_ID = "SO_TRANSACTION";
	public static List<TransactionType> txTypeList = new ArrayList<TransactionType>();
	static {
		txTypeList = DailyTranxLogic.loadTransactionType();
	}
	protected void newTransaction(){
		Properties props = Util.getProperties("cfg/daily_tranx.cfg");
		DailyTransaction dTranx = new DailyTransaction(
				UUID.randomUUID().toString(),
				SALE_TX_TYPE_ID, 
				props.getProperty(SALE_TX_TYPE_ID), 
				props.getProperty(SALE_TX_TYPE_ID).split(",")[1],//-- go to step 1
				props.getProperty("transaction.status").split(",")[0],
				null
				//Util.generateSaleOrder()
				);
		try {
			DailyTranxLogic.getInstance().insert(dTranx);
			loadDailyTransaction();
			fireCAction(new CActionEvent(this, CActionEvent.RELOAD_TRANX_LIST, dTranx));
		} catch (Throwable e) {
			e.printStackTrace();
			logger.error(e);
		}		
	}
	void loadDailyTransaction() throws Throwable{
		logAction(SaleMasterGUIManager.class, LOG_LEVEL.INFO, "Loading daily transaction...");
		List<DailyTransaction> dailyTxList = DailyTranxLogic.getInstance().loadTranxByTxType(SALE_TX_TYPE_ID);
		if(saleMasterTranxListPage!= null && dailyTxList != null && dailyTxList.size() >0)
			TableUtil.addListToTable(saleMasterTranxListPage, saleMasterTranxListPage.getTable(), dailyTxList, null);	
	}
	
	
	public static Logger logger = Logger.getLogger(SaleMasterGUIManager.class);

	void fireCAction(CActionEvent action) {
		for(CActionListener al: cListener)
			al.cActionPerformed(action);		
	}
	void clearDetail(){
		txtSOPhase.setText(null);
		txtSOCurrentVoucherNumber.setText(null);
		txtCustomerName.setText(null);
		txtSOAmountMoney.setText(null);
		txtSOCurrencyType.setText(null);
		txtSONumber.setText(null);
		txtSOVATTax.setText(null);
		txtSOType.setText(null);
		txtSOOtherExpense.setText(null);
		txtFromWarehouse.setText(null);
		txtSOGrossAmount.setText(null);				
		txtSOCreator.setText(null);
		txtSOCreateDate.setText(null);
		txtSOModifyUser.setText(null);
		txtSOModifyDate.setText(null);	
	}
	void loadTransactionDetail(String tranxId){
		try {			
			clearDetail();
		    DailyTransaction tranx = DailyTranxLogic
					.getInstance().get(tranxId);		
		    
		    txtSOPhase.setText(tranx.getCurrent_step());
			txtSOCurrentVoucherNumber.setText(tranx.getVoucher_id());
			
			Employee user = UserLogic.getInstance().getUser(tranx.getIns_user_id());
			txtSOCreator.setText(user.getName());
			txtSOCreateDate.setText(tranx.getIns_time());
			String soId = null;
			
			if(tranx.getVoucher_id().indexOf(";") != -1){
				soId = tranx.getVoucher_id().split(";")[0];
			}else
				soId = tranx.getVoucher_id();
			SaleOrder so = null;
			if(soId != null)
				so = SaleOrderLogic.getInstance().get(soId);
			if(so != null){
				Customer cust = CustomerLogic.getInstance().getCustomer(so.getCustomer_id());
				txtCustomerName.setText(cust == null ? "":cust.getName());
				txtSOAmountMoney.setText(SystemConfiguration.decfm.format(so.getNet_amount()));
				txtSOCurrencyType.setText(so.getCurrency_id());
				txtSONumber.setText(so.getOrder_id());
				txtSOVATTax.setText(SystemConfiguration.decfm.format(so.getVat_amount()));
				txtSOType.setText(so.getOrder_type());
				txtSOOtherExpense.setText(SystemConfiguration.decfm.format(so.getExpense_amount()));
				
				Warehouse wh = WarehouseLogic.getWarehouse(so.getFrom_warehouse());				
				txtFromWarehouse.setText(wh.getName());
				txtSOGrossAmount.setText(SystemConfiguration.decfm.format(so.getGross_amount()));				
				
				//txtSOModifyUser;
				txtSOModifyDate.setText(so.getUpd_time());				
			}
		} catch (Throwable e2) {
			logger.error(e2);
		}
	}
	public static int[] getSelectedRowsModelIndices(CTable table) {
	    if (table == null) {
	        throw new NullPointerException("table == null");
	    }

	    int[] selectedRowIndices = table.getSelectedRows();
	    int countSelected = selectedRowIndices.length;
	       
	    for (int i = 0; i < countSelected; i++) {
	        selectedRowIndices[i] = table.convertRowIndexToModel(selectedRowIndices[i]);
	    }
	    return selectedRowIndices;
	}
	public Action ACT_NEW_TRANX = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {			
			newTransaction();
		}
	};
	public Action ACT_QUOTE = new AbstractAction() {	
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {			
			
		}
	};
	public Action ACT_RESERVED = new AbstractAction() {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {			
			
		}
	};
	public Action ACT_CREATE_SO = new AbstractAction() {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {			
			saleTabbedPane.setSelectedIndex(1);
		}
	};
	public Action ACT_DEBT_MGMT = new AbstractAction() {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {			
			
		}
	};
	public Action ACT_ISSUE_SLIP = new AbstractAction() {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {			
			saleTabbedPane.setSelectedIndex(2);
		}
	};
	public Action ACT_REFRESH_TRANX_LIST = new AbstractAction() {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {			
			try {
				loadDailyTransaction();
			} catch (Throwable e2) {
				logAction(SaleMasterGUIManager.class, LOG_LEVEL.ERROR, e2);
			}
		}
	};
	JButton btnF5 = new JButton(ACT_REFRESH_TRANX_LIST);
	public static void main(String[] args ) throws Exception{
		AbstractGUIManager guiManager; 
		try {
		    UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
		} catch (Exception e) {
		    e.printStackTrace();
		}	
		Language.loadLanguage("vn");
		
		JFrame f = new JFrame();
		
		guiManager = new SaleMasterGUIManager(Language.getInstance().getLocale());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().add(guiManager.getRootComponent());		
		
		f.pack();
		f.validate();
		
		f.setVisible(true);
	}
}
