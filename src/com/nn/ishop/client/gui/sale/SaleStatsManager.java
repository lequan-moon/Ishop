package com.nn.ishop.client.gui.sale;

import java.awt.Dimension;

import javax.swing.JSplitPane;

import com.nn.ishop.client.gui.AbstractGUIManager;
import com.nn.ishop.client.gui.GUIActionEvent;
import com.nn.ishop.client.gui.GUIActionListener;
import com.nn.ishop.client.gui.GUIActionEvent.GUIActionType;
import com.nn.ishop.client.gui.components.CList;
import com.nn.ishop.client.gui.components.CTwoModePanel;
import com.nn.ishop.client.gui.components.CWhitePanel;
import com.nn.ishop.client.gui.components.VerticalFlowLayout;
import com.nn.ishop.client.gui.components.CTwoModePanel.DOCKING_DIRECTION;

public class SaleStatsManager extends AbstractGUIManager  implements GUIActionListener{

	JSplitPane saleStatsResultSplit, saleStatsSplit;
	CWhitePanel pnlSaleStatsCond,pnlSaleStatsResult, pnlSaleStatsDetail;
	CTwoModePanel condTwoModePanel, searchRetTwoModePanel, searchRetDetailTwoModePanel;
	
	public SaleStatsManager (String locale){
		setLocale(locale);
		init();
	}
	void init(){
		if (getLocale() != null && !getLocale().equals("")){
			initTemplate(this, "sale/saleOrderStatsPage.xml", getLocale());
		}else{
			initTemplate(this, "sale/saleOrderStatsPage.xml");		
			}
		render();
		condTwoModePanel.addGUIActionListener(this);
		condTwoModePanel.setDockableBarLayout(new VerticalFlowLayout(1, VerticalFlowLayout.TOP)
		, DOCKING_DIRECTION.North);		
		searchRetTwoModePanel.addGUIActionListener(this);
		searchRetDetailTwoModePanel.addGUIActionListener(this);
		
		condTwoModePanel.setManagerClazz(CList.class);
		searchRetTwoModePanel.setManagerClazz(SaleStatsManager.class);
		searchRetDetailTwoModePanel.setManagerClazz(String.class);
		
		condTwoModePanel.addContent(pnlSaleStatsCond);
		searchRetTwoModePanel.addContent( pnlSaleStatsResult);
		searchRetDetailTwoModePanel.addContent( pnlSaleStatsDetail);
		
		bindEventHandlers();
		
	}
	@Override
	protected void applyStyles() {
		saleStatsResultSplit.setOrientation(0);// Vertical split
		customizeSplitPaneHideFirstComponent(saleStatsResultSplit);
		customizeSplitPaneHideFirstComponent(saleStatsSplit);
		System.out.println(pnlSaleStatsCond.getPreferredSize().toString());
	}

	@Override
	protected void bindEventHandlers() {
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
	public void guiActionPerformed(GUIActionEvent action) {
		GUIActionType guideType = action.getAction(); 
		Object srcObj = action.getSource();
		if(guideType.equals( GUIActionType.MINIMIZE_WINDOW)){
			int location = ((Dimension)action.getData()).height;
			if(srcObj.equals(CList.class)){
				saleStatsSplit.setDividerLocation(location);
			}else if(srcObj.equals(SaleStatsManager.class)){
				saleStatsResultSplit.setDividerLocation(location);
			}else{
				int newLoc = saleStatsResultSplit.getLeftComponent().getSize().height
						 + saleStatsResultSplit.getRightComponent().getSize().height
						 - location;
				saleStatsResultSplit.setDividerLocation(newLoc);
			}
		}
		if(guideType.equals( GUIActionType.MAXIMIZE_WINDOW)){
			if(srcObj.equals(CList.class))
				saleStatsSplit.resetToPreferredSizes();
			else
				saleStatsResultSplit.resetToPreferredSizes();
					
		}
		
	}

}
