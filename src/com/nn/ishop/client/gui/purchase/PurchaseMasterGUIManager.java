package com.nn.ishop.client.gui.purchase;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import com.nn.ishop.client.CActionEvent;
import com.nn.ishop.client.CActionListener;
import com.nn.ishop.client.gui.AbstractGUIManager;
import com.nn.ishop.client.gui.GUIActionEvent;
import com.nn.ishop.client.gui.GUIActionEvent.GUIActionType;
import com.nn.ishop.client.gui.GUIActionListener;
import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.admin.customer.CTabbedPane;
import com.nn.ishop.client.gui.components.CButton2;
import com.nn.ishop.client.gui.components.CPaging;
import com.nn.ishop.client.gui.components.CTable;
import com.nn.ishop.client.gui.components.CTextField;
import com.nn.ishop.client.gui.components.CTwoModePanel;
import com.nn.ishop.client.gui.components.CWhitePanel;
import com.nn.ishop.client.gui.dialogs.SystemMessageDialog;
import com.nn.ishop.client.gui.helper.TableUtil;
import com.nn.ishop.client.logic.admin.CustomerLogic;
import com.nn.ishop.client.logic.admin.PurchaseGrnLogic;
import com.nn.ishop.client.logic.admin.PurchaseLogic;
import com.nn.ishop.client.logic.transaction.DailyTranxLogic;
import com.nn.ishop.server.dto.bstranx.DailyTransaction;
import com.nn.ishop.server.dto.customer.Customer;
import com.nn.ishop.server.dto.grn.PurchaseGrn;
import com.nn.ishop.server.dto.purchase.PurchasingPlan;
import com.nn.ishop.server.util.Formatter;

public class PurchaseMasterGUIManager extends AbstractGUIManager implements CActionListener, GUIActionListener, ListSelectionListener,
		TableModelListener {
	CWhitePanel purchaseMainPage;
	CTabbedPane purchaseTabbedPane;
	CTabbedPane tranxDetailTabbedPane;
	CWhitePanel GRNMainPage;
	CTwoModePanel tranxDetailTwoModePnl;

	CWhitePanel WarehouseStockcardMainPage;

	//CPaging purchaseStatisticsResultPaging;

	PurchaseMainGUIManager purchaseMainGUIManager;
	GRNMainGUIManager grnMainGUIManager;
	WHSCMainGUIManager whscMainGUIManager;

	Vector<CActionListener> lstListener = new Vector<CActionListener>();

	CButton2 newTransactionButton, newPPButton, newGRNButton, newStockcardButton;

	public CTextField txtTranxId,txtInsTime, txtCurrentStep, txtStatus, txtPPId, txtContractId, txtType, txtSignedDate;
	public CTextField txtDeadLine, txtProviderName,txtPPNote, txtReceivedDate, txtGRNNote;
	
	CPaging purchaseTranxListPage;
	DailyTranxLogic dailyTranslogic = DailyTranxLogic.getInstance();
	
	DailyTransaction currentTranx;
	
	JSplitPane purchaseTranxSplit;

	public PurchaseMasterGUIManager(String locale) {
		setLocale(locale);
		init();
	}

	void init() {
		if (getLocale() != null && !getLocale().equals("")) {
			initTemplate(this, "purchase/purchaseMasterPage.xml", getLocale());
		} else {
			initTemplate(this, "purchase/purchaseMasterPage.xml");
		}
		render();		
		if(purchaseMainPage != null){
			purchaseMainGUIManager = new PurchaseMainGUIManager(getLocale());
			purchaseMainPage.add(purchaseMainGUIManager.getRootComponent());
		}
		if(GRNMainPage != null){
			grnMainGUIManager = new GRNMainGUIManager(getLocale());
			GRNMainPage.add(grnMainGUIManager.getRootComponent());
		}
		if(WarehouseStockcardMainPage != null){
			whscMainGUIManager = new WHSCMainGUIManager(getLocale());
			WarehouseStockcardMainPage.add(whscMainGUIManager.getRootComponent());
		}

		bindEventHandlers();
		// Prepare data should be the last step
		prepareData();
	}

	@Override
	protected void applyStyles() {
		Map<Object, String> tabTitleMap = new HashMap<Object, String>();		
		List<Object> tabObjects = new ArrayList<Object>();
		
		if(purchaseTranxSplit != null){
			tabObjects.add(purchaseTranxSplit);
			tabTitleMap.put(purchaseTranxSplit, Language.getInstance().getString("Transaction"));
		}
		if(purchaseMainPage != null){
			tabObjects.add(purchaseMainPage);
			tabTitleMap.put(purchaseMainPage,Language.getInstance().getString("purchase.tab.title1") );
		}
		if(GRNMainPage != null){
			tabObjects.add(GRNMainPage);
			tabTitleMap.put(GRNMainPage,Language.getInstance().getString("purchase.tab.title2") );
		}
		if(WarehouseStockcardMainPage != null){
			tabObjects.add(WarehouseStockcardMainPage);
			tabTitleMap.put(WarehouseStockcardMainPage,Language.getInstance().getString("purchase.tab.title3") );
		}
		for(int i=0;i<tabObjects.size();i++){
			Object obj = tabObjects.get(i);
			purchaseTabbedPane.setTitleAt(i,tabTitleMap.get(obj));
		}
		
		if(tranxDetailTabbedPane != null){
			tranxDetailTabbedPane.setTitleAt(0, Language.getInstance().getString("sale.tabbedpane.detail.txinfor"));
		}	
	}

	private void prepareData() {
		try {
			loadTranxList();
			//Notify Listener for transaction reload
			CActionEvent newAction = new CActionEvent(this, CActionEvent.RELOAD_TRANX_LIST, null);			
			fireCActionEvent(newAction);
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	private void loadTranxList(){
		try{			
			purchaseTranxListPage.setName("purchaseTranxListPage");
			if(purchaseTranxListPage != null){
				purchaseTranxListPage.clearData();
				List<DailyTransaction> lstData = dailyTranslogic.loadTranxByTxType("PO_TRANSACTION");				
				if (lstData != null && lstData.size() > 0) {
					TableUtil.addListToTable(purchaseTranxListPage, purchaseTranxListPage.getTable(), lstData);
				}
			}			
		}catch(Exception ex){
			ex.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
	}

	public Action cmdNewTranx = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent arg0) {
			try {
				@SuppressWarnings("unchecked")
				List<DailyTransaction> lstData =
				(List<DailyTransaction>) ((CTable)purchaseTranxListPage.getTable()).getLstData();
				if (lstData == null) {
					lstData = new ArrayList<DailyTransaction>();
				}
				String newTranxId = Formatter.sdfTimeId.format(new Date(System.currentTimeMillis()));
				System.out.println("new transaction id " + newTranxId);
				DailyTransaction newTranx = new DailyTransaction(newTranxId, "PO_TRANSACTION",
						"INIT,PURCHASE_PLAN,GOOD_RECEIVE_NOTE,STOCK_CARD", "PURCHASE_PLAN", "ACTIVE", "");
				lstData.add(newTranx);				
				dailyTranslogic.insert(newTranx);
				
				TableUtil.addListToTable(purchaseTranxListPage, purchaseTranxListPage.getTable(), lstData);
				loadTranxList();
				CActionEvent newAction = new CActionEvent(this, CActionEvent.RELOAD_TRANX_LIST, newTranx);
				fireCActionEvent(newAction);
				
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	};
	boolean validTransaction(){
		if(currentTranx == null){
			SystemMessageDialog.showMessageDialog(null, 
					Language.getInstance().getString("selected.tranx.null"), 
					SystemMessageDialog.SHOW_OK_OPTION);
		}
		return currentTranx != null;
	}
	public Action cmdNewPP = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent arg0) {
			if(!validTransaction())
				return;
			purchaseTabbedPane.setSelectedIndex(1);			
			CActionEvent action = new CActionEvent(this, CActionEvent.UPDATE_PP, currentTranx);
			fireCActionEvent(action);
		}
	};
	public Action cmdNewGRN = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent arg0) {
			if(!validTransaction())
				return;
			purchaseTabbedPane.setSelectedIndex(2);
			CActionEvent action = new CActionEvent(this, CActionEvent.UPDATE_GRN, currentTranx);
			fireCActionEvent(action);
		}
	};
	public Action cmdNewStockcard = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent arg0) {
			if(!validTransaction())
				return;
			purchaseTabbedPane.setSelectedIndex(3);
			CActionEvent action = new CActionEvent(this, CActionEvent.UPDATE_STOCK_CARD, currentTranx);
			fireCActionEvent(action);
		}
	};

	@Override
	protected void bindEventHandlers() {
		purchaseTranxListPage.setName("purchaseTranxListPage");
		if(purchaseMainGUIManager != null){
			this.addCActionListener(purchaseMainGUIManager);
		}
		if(grnMainGUIManager != null){
			this.addCActionListener(grnMainGUIManager);	
		}
		if(whscMainGUIManager != null){
			this.addCActionListener(whscMainGUIManager);	
		}
		
		// add listener to table => check step to enable buttons
		if(purchaseTranxListPage != null){
			purchaseTranxListPage.addCActionListener(this);
			purchaseTranxListPage.getTable().addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					int selectedRowIndex = purchaseTranxListPage.getTable().rowAtPoint(e.getPoint());
					String tranxId = (String)purchaseTranxListPage.getTable().getModel().getValueAt(
							purchaseTranxListPage.getTable().convertRowIndexToModel(selectedRowIndex), 1);
					if(e.getButton() == MouseEvent.BUTTON1){
						try {
							loadDetailDataOfTranx(tranxId);
							currentTranx = dailyTranslogic.get(tranxId);
						} catch (Exception e2) {
							e2.printStackTrace();
							logAction(PurchaseMasterGUIManager.class, LOG_LEVEL.ERROR, "purchaseTranxListPage.mouseRelease:" + e2.getMessage());
						}
						updateButton(currentTranx);
					}
				}
			});
		}
		if(tranxDetailTwoModePnl != null){
			tranxDetailTwoModePnl.addGUIActionListener(this);
			tranxDetailTwoModePnl.setManagerClazz(PurchaseMasterGUIManager.class);
			tranxDetailTwoModePnl.setTitle(Language.getInstance().getString("sale.tabbedpane.detail.txinfor"));
		}
		// comment out this block because we don't need to reload table each time changing the tabbedpane
		//purchaseTabbedPane.addChangeListener(this);
		JComponent rootComp = (JComponent)getRootComponent();
		rootComp.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_N,
                java.awt.event.InputEvent.CTRL_DOWN_MASK),
        "cmdNewTranx");
		rootComp.getActionMap().put("cmdNewTranx", cmdNewTranx);
	}
	
	public void resetData(){
		txtTranxId.setText("");
		txtInsTime.setText("");
		txtCurrentStep.setText("");
		txtStatus.setText("");
		txtPPId.setText("");
		txtContractId.setText("");
		txtType.setText("");
		txtSignedDate.setText("");
		txtDeadLine.setText("");
		txtProviderName.setText("");
		txtPPNote.setText("");
		txtProviderName.setText("");
		txtGRNNote.setText("");
		txtReceivedDate.setText("");
	}
	
	public void loadDetailDataOfTranx(String tranx_id){
		resetData();
		try {
			if(tranx_id != null){
				DailyTransaction currentTranx = dailyTranslogic.get(tranx_id);
				
				if(currentTranx != null){
					// Binding data into view
					txtTranxId.setText(currentTranx.getId());
					txtInsTime.setText(currentTranx.getIns_time());
					txtCurrentStep.setText(currentTranx.getCurrent_step());
					txtStatus.setText(currentTranx.getStatus());
				}
				
				PurchaseLogic ppLogic = PurchaseLogic.getInstance();
				PurchaseGrnLogic grnLogic = PurchaseGrnLogic.getInstance();
				PurchasingPlan ppOfTranx = ppLogic.getPurchasingPlanByTranxId(tranx_id);
				if(ppOfTranx != null){
					// Bind data into view
					txtPPId.setText(ppOfTranx.getPpId());
					txtContractId.setText(ppOfTranx.getContractId());
					// Search for type
					String typeStr = String.valueOf(ppOfTranx.getPpTypeId());
					txtType.setText(typeStr);
					txtSignedDate.setText(ppOfTranx.getSignedDate());
					txtDeadLine.setText(ppOfTranx.getDeadline());
					// Search for provider
					String providerStr = String.valueOf(ppOfTranx.getProviderId());
					try {
						int cusCd = Formatter.str2num(providerStr).intValue();
						Customer provider = CustomerLogic.getInstance()
								.getCustomer(cusCd);
						txtProviderName.setText(provider.getName());
					} catch (Exception e) {
						txtProviderName.setText(providerStr);
					}
					txtPPNote.setText(ppOfTranx.getNote());
					
					PurchaseGrn grn = grnLogic.getPurchaseGrnByPPId(ppOfTranx.getPpId());
					if(grn != null){
						// Bind data into view
						txtReceivedDate.setText(grn.getReceive_date());
						txtGRNNote.setText(grn.getNote());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

 	private void fireCActionEvent(CActionEvent action) { 		
		for (CActionListener listener : lstListener) {
			listener.cActionPerformed(action);
		}
	}

	private void updateButton(DailyTransaction currentTranx) {
		if(currentTranx == null){
			logAction(PurchaseMasterGUIManager.class,LOG_LEVEL.ERROR, "updateButton(): Selected transaction is null");
			return;
		}
		String currentStep = currentTranx.getCurrent_step();		
		if ("PURCHASE_PLAN".equals(currentStep)) {
			newPPButton.setEnabled(true);
			newGRNButton.setEnabled(false);
			newStockcardButton.setEnabled(false);
		} else if ("GOOD_RECEIVE_NOTE".equals(currentStep)) {
			newPPButton.setEnabled(false);
			newGRNButton.setEnabled(true);
			newStockcardButton.setEnabled(false);
		} else if ("STOCK_CARD".equals(currentStep)) {
			newPPButton.setEnabled(false);
			newGRNButton.setEnabled(false);
			newStockcardButton.setEnabled(true);
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
		if (action.getAction() == CActionEvent.UPDATE_PP) {
			try {
				String tranxId = (String) action.getData();
				currentTranx = dailyTranslogic.get(tranxId);
				updateButton(currentTranx);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void guiActionPerformed(GUIActionEvent action) {
		GUIActionType guideType = action.getAction(); 
		if(guideType.equals( GUIActionType.MINIMIZE_WINDOW)){
			int location = ((Dimension)action.getData()).height;
			int newLoc = purchaseTranxSplit.getLeftComponent().getSize().height
					 + purchaseTranxSplit.getRightComponent().getSize().height
					 - location -26;
			purchaseTranxSplit.setDividerLocation(newLoc);
		}
		if(guideType.equals( GUIActionType.MAXIMIZE_WINDOW)){
			purchaseTranxSplit.resetToPreferredSizes();
		}
	}

	public void valueChanged(ListSelectionEvent e) {

	}

	public void tableChanged(TableModelEvent e) {

	}

	@Override
	protected void checkPermission() {
	}

	public void addCActionListener(CActionListener al) {
		lstListener.add(al);
	}

	public void removeCActionListener(CActionListener al) {
		lstListener.remove(al);
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
		
		guiManager = new PurchaseMasterGUIManager(Language.getInstance().getLocale());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().add(guiManager.getRootComponent());		
		
		f.pack();
		f.validate();
		
		f.setVisible(true);
	}

}
