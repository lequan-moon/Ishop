package com.nn.ishop.client.gui.purchase;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import com.nn.ishop.client.CActionEvent;
import com.nn.ishop.client.CActionListener;
import com.nn.ishop.client.gui.AbstractGUIManager;
import com.nn.ishop.client.gui.GUIActionEvent;
import com.nn.ishop.client.gui.GUIActionListener;
import com.nn.ishop.client.gui.GUIActionEvent.GUIActionType;
import com.nn.ishop.client.gui.components.CPaging;
import com.nn.ishop.client.gui.components.CTwoModePanel;
import com.nn.ishop.client.gui.components.CWhitePanel;
import com.nn.ishop.client.gui.helper.TableUtil;
import com.nn.ishop.client.logic.admin.PurchaseGrnLogic;
import com.nn.ishop.client.logic.transaction.DailyTranxLogic;
import com.nn.ishop.server.dto.bstranx.DailyTransaction;
import com.nn.ishop.server.dto.grn.PurchaseGrnDetail;

public class WHSCMainGUIManager extends AbstractGUIManager implements CActionListener, GUIActionListener, ListSelectionListener, TableModelListener {

//	CWhitePanel whscHeaderContainer;
//	WHSCHeaderGUIManager whscHeaderGUIManager = new WHSCHeaderGUIManager(getLocale());
	// CWhitePanel whscDetailContainer;
	// WHSCDetailGUIManager whscDetailGUIManager = new
	// WHSCDetailGUIManager(getLocale());
	CWhitePanel whscItemListContainer;
	WHSCItemListGUIManager whscItemListGUIManager = new WHSCItemListGUIManager(getLocale());
	Vector<CActionListener> lstListener = new Vector<CActionListener>();
	CPaging purchaseTranxListPageAtWHSC;
	DailyTranxLogic dailyTranxLogic = DailyTranxLogic.getInstance();
	DailyTransaction currentTranx;
	JSplitPane whscTranxSplit;

	CTwoModePanel tranxTwoModePanel, detailTwoModePanel;
	CWhitePanel    purchaseTranxListPageAtWHSCCnt, whscMainPage;
	
	public WHSCMainGUIManager(String locale) {
		setLocale(locale);
		init();
	}

	void init() {
		if (getLocale() != null && !getLocale().equals("")) {
			initTemplate(this, "purchase/po/whscMainPage.xml", getLocale());
		} else {
			initTemplate(this, "purchase/po/whscMainPage.xml");
		}
		render();		
//		whscHeaderContainer.add(whscHeaderGUIManager.getRootComponent());
		// whscDetailContainer.add(whscDetailGUIManager.getRootComponent());
		whscItemListContainer.add(whscItemListGUIManager.getRootComponent());
		bindEventHandlers();
	}

	public Action cmdSave = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			try {
				if (currentTranx != null) {
					CActionEvent action = new CActionEvent(this, CActionEvent.SAVE, null);
					fireCActionEvent(action);
					currentTranx.setStatus("CLOSED");
					dailyTranxLogic.update(currentTranx);
					currentTranx = null;
					reloadTranxList();
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	};

	public Action cmdCancel = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			CActionEvent action = new CActionEvent(this, CActionEvent.CANCEL, null);
			fireCActionEvent(action);
			reloadTranxList();
		}
	};

	private void fireCActionEvent(CActionEvent action) {
		for (CActionListener listener : lstListener) {
			listener.cActionPerformed(action);
		}
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
			if(srcObj.equals(WHSCMainGUIManager.class)){
				whscTranxSplit.setDividerLocation(location);
			}else{
				int newLoc = whscTranxSplit.getLeftComponent().getSize().height
						 + whscTranxSplit.getRightComponent().getSize().height
						 - location;
				whscTranxSplit.setDividerLocation(newLoc);
			}
		}
		if(guideType.equals( GUIActionType.MAXIMIZE_WINDOW)){
			whscTranxSplit.resetToPreferredSizes();
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
				// Load all transactions with step = STOCKCARD
				reloadTranxList();
			} else if (action.getAction() == CActionEvent.INIT_UPDATE_PRODUCT) {
				String tranx_id = (String) action.getData();
				currentTranx = dailyTranxLogic.get(tranx_id);
				String grn_id = currentTranx.getVoucher_id();
				PurchaseGrnLogic purchaseGrnLogic = PurchaseGrnLogic.getInstance();
				List<PurchaseGrnDetail> listGrn_detail = purchaseGrnLogic.getPurchaseGrnDetail(grn_id);
				CActionEvent stockcardAction = new CActionEvent(this, CActionEvent.LIST_SELECT_ITEM, listGrn_detail);
				fireCActionEvent(stockcardAction);
			}else if(action.getAction() == CActionEvent.UPDATE_STOCK_CARD){
				currentTranx = (DailyTransaction)action.getData();
				String grn_id = currentTranx.getVoucher_id();
				PurchaseGrnLogic purchaseGrnLogic = PurchaseGrnLogic.getInstance();
				List<PurchaseGrnDetail> listGrn_detail = purchaseGrnLogic.getPurchaseGrnDetail(grn_id);
				CActionEvent stockcardAction = new CActionEvent(this, CActionEvent.LIST_SELECT_ITEM, listGrn_detail);
				fireCActionEvent(stockcardAction);
			}else {
				fireCActionEvent(action);
				reloadTranxList();
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	private void reloadTranxList() {
		try {
			purchaseTranxListPageAtWHSC.clearData();
			List<DailyTransaction> lstData = dailyTranxLogic.searchByStep("STOCK_CARD");
			if (lstData != null && lstData.size() > 0) {
				TableUtil.addListToTable(purchaseTranxListPageAtWHSC, purchaseTranxListPageAtWHSC.getTable(), lstData);
			}
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void bindEventHandlers() {
		this.addCActionListener(this.whscItemListGUIManager);
//		whscHeaderGUIManager.addCActionListener(this.whscItemListGUIManager);
		purchaseTranxListPageAtWHSC.addCActionListener(this);
		detailTwoModePanel.addGUIActionListener(this);
		tranxTwoModePanel.addGUIActionListener(this);
		tranxTwoModePanel.setManagerClazz(WHSCMainGUIManager.class);
		detailTwoModePanel.setManagerClazz(String.class);
//		tranxTwoModePanel.addContent(purchaseTranxListPageAtWHSCCnt);
		detailTwoModePanel.addContent(whscMainPage);
	}

	@Override
	public Object getData(String var) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void applyStyles() {
		customizeSplitPaneHideFirstComponent(whscTranxSplit);

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateList() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void checkPermission() {
		// TODO Auto-generated method stub

	}

}
