package com.nn.ishop.client.gui.purchase;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.List;
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
import com.nn.ishop.client.gui.components.CDialogsLabelButton;
import com.nn.ishop.client.gui.components.CPaging;
import com.nn.ishop.client.gui.components.CTableModel;
import com.nn.ishop.client.gui.components.CTwoModePanel;
import com.nn.ishop.client.gui.components.CWhitePanel;
import com.nn.ishop.client.gui.dialogs.SystemMessageDialog;
import com.nn.ishop.client.gui.helper.TableUtil;
import com.nn.ishop.client.logic.transaction.DailyTranxLogic;
import com.nn.ishop.server.dto.bstranx.DailyTransaction;
import com.nn.ishop.server.dto.purchase.PurchasingPlan;

public class PurchaseMainGUIManager extends AbstractGUIManager implements CActionListener, GUIActionListener, ListSelectionListener,
		TableModelListener {
	POHeaderGUIManager poHeaderGUIManager;
	CWhitePanel purchasePOHeaderContainer;
	POInputItemGUIManager poInputItemGUIManager;
	CWhitePanel purchasePOInputItemContainer;
	Vector<CActionListener> lstListener = new Vector<CActionListener>();
	boolean isActived = false;
	boolean isSaved = false;
	CPaging purchaseTranxListPageAtPP;
	DailyTranxLogic dailyTranxLogic = DailyTranxLogic.getInstance();
	JSplitPane poTranxSplit;
	CTwoModePanel tranxTwoModePnl, detailTwoModePnl;
	CWhitePanel purchaseMainPage;
	CDialogsLabelButton btnNew, btnSave;
	
	public PurchaseMainGUIManager(String locale) {
		setLocale(locale);
		init();
	}

	void init() {
		if (getLocale() != null && !getLocale().equals("")) {
			initTemplate(this, "purchase/po/poMainPage.xml", getLocale());
		} else {
			initTemplate(this, "purchase/po/poMainPage.xml");
		}
		render();
		poHeaderGUIManager = new POHeaderGUIManager(getLocale());
		purchasePOHeaderContainer.add(poHeaderGUIManager.getRootComponent());
		poInputItemGUIManager = new POInputItemGUIManager(getLocale());
		purchasePOInputItemContainer.add(poInputItemGUIManager.getRootComponent());
		detailTwoModePnl.addContent(purchaseMainPage);
		bindEventHandlers();
	}

	

	/**
	 * Notify listener about the event
	 * @param action
	 */
	private void fireCActionEvent(CActionEvent action) {
		for (CActionListener listener : lstListener) {
			listener.cActionPerformed(action);
		}
	}
	public void tableChanged(TableModelEvent e) {
	}
		
	public void valueChanged(ListSelectionEvent arg0) {
	}
	
	public void guiActionPerformed(GUIActionEvent action) {
		GUIActionType guideType = action.getAction(); 
		Object srcObj = action.getSource();
		if(guideType.equals( GUIActionType.MINIMIZE_WINDOW)){
			int location = ((Dimension)action.getData()).height;
			if(srcObj.equals(PurchaseMainGUIManager.class)){
				poTranxSplit.setDividerLocation(location);
			}else{
				int newLoc = poTranxSplit.getLeftComponent().getSize().height
						 + poTranxSplit.getRightComponent().getSize().height
						 - location;
				poTranxSplit.setDividerLocation(newLoc);
			}
		}
		if(guideType.equals( GUIActionType.MAXIMIZE_WINDOW)){
			poTranxSplit.resetToPreferredSizes();
		}

	}

	public void addCActionListener(CActionListener al) {		
		lstListener.add(al);
	}

	public void removeCActionListener(CActionListener al) {
	}

	public void cActionPerformed(CActionEvent action) {
		try {
			if (action.getAction() == CActionEvent.RELOAD_TRANX_LIST) {
				// load all transaction with step = PURCHASE_PLAN				
				loadTranxList();			
			} else if(action.getAction() == CActionEvent.UPDATE_PP){
				updatePP( (DailyTransaction) action.getData());
			}if (action.getAction() == CActionEvent.PAGING_ADD_EVENT){
				//Do the same as new button click
				newPurchasePlan();
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
			}if (action.getAction() == CActionEvent.ADD 
					|| action.getAction() == CActionEvent.SAVE 
					|| action.getAction() == CActionEvent.CANCEL){
				fireCActionEvent(action);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
   /**
    * Invoke by UPDATE_PP action
	 * @param currentTranx
	 */
	private void updatePP(DailyTransaction currentTranx){
			CActionEvent newAction = new CActionEvent(this, CActionEvent.UPDATE_PP, currentTranx);			
			detailTwoModePnl.updateTitleWithStatus(true, currentTranx.getId() );
			fireCActionEvent(newAction);
			isSaved= false;
   }
	void loadTranxList() {
		try {
			purchaseTranxListPageAtPP.clearData();
			List<DailyTransaction> lstData = dailyTranxLogic.searchByStep("PURCHASE_PLAN");
			
			if (lstData != null && lstData.size() > 0) {
				TableUtil.addListToTable(purchaseTranxListPageAtPP, purchaseTranxListPageAtPP.getTable(), lstData);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void bindEventHandlers() {
		addCActionListener(poHeaderGUIManager);
		poHeaderGUIManager.addCActionListener(poInputItemGUIManager);
		purchaseTranxListPageAtPP.addCActionListener(this);
		
		if(tranxTwoModePnl != null){
			tranxTwoModePnl.addGUIActionListener(this);
			tranxTwoModePnl.setManagerClazz(PurchaseMainGUIManager.class);
			tranxTwoModePnl.setTitle(Language.getInstance().getString("transaction.list"));
		}
		if(detailTwoModePnl != null){
			detailTwoModePnl.addGUIActionListener(this);
			detailTwoModePnl.setManagerClazz(String.class);
			detailTwoModePnl.setTitle(Language.getInstance().getString("po.header.container.title"));
		}
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
	public Action cmdSave = new AbstractAction() {	
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			cActionPerformed(new CActionEvent(this, CActionEvent.SAVE, null));
			isActived = poHeaderGUIManager.isActived();
			detailTwoModePnl.setTitle(Language.getInstance().getString("po.header.container.title"));
		}
	};

	public Action cmdNew = new AbstractAction() {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			newPurchasePlan();
		}
	};
	protected void newPurchasePlan(){
		if (!isActived) {
			try {
				int selectedRow = -1;
				selectedRow = purchaseTranxListPageAtPP.getTable()
						.getSelectedRow();
				if(selectedRow == -1){
					int ret = SystemMessageDialog.showConfirmDialog(null, Language.getInstance().getString("confirm.auto.select.tranx"), 
							SystemMessageDialog.SHOW_OK_CANCEL_OPTION);
					if(ret != 0) 
						return;
					else{
						purchaseTranxListPageAtPP.getTable().setRowSelectionInterval(0, 0);
						selectedRow = 0;
					}
				}
				CTableModel model = purchaseTranxListPageAtPP.getModel();
				String tranxId = (String)model.getValueAt(selectedRow, 1);
				PurchasingPlan pp = new PurchasingPlan();
				pp.setTranx_id(tranxId);				
				cActionPerformed(new CActionEvent(this, CActionEvent.ADD, pp));		
				detailTwoModePnl.updateTitleWithStatus(true, tranxId );
				isActived =poHeaderGUIManager.isActived();
				isSaved = !poHeaderGUIManager.isDataChanged;
			} catch (Exception e2) {
				e2.printStackTrace();
			}				
		}else{
			//TODO notify GUI that cannot create new PP, need to complete other action first.
		}
	}
	public Action cmdCancel = new AbstractAction() {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			cActionPerformed(new CActionEvent(this, CActionEvent.CANCEL, null));			
			isActived =poHeaderGUIManager.isActived();
			isSaved = !poHeaderGUIManager.isDataChanged;
		}
	};
	public static void main(String[] args ) throws Exception{
		PurchaseMainGUIManager guiManager; 
		try {
		    UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
		} catch (Exception e) {
		    e.printStackTrace();
		}	
		Language.loadLanguage("vn");
		
		JFrame f = new JFrame();
		
		guiManager = new PurchaseMainGUIManager(Language.getInstance().getLocale());
		guiManager.loadTranxList();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().add(guiManager.getRootComponent());		
		
		f.pack();
		f.validate();
		
		f.setVisible(true);
	}

}
