package test.swixml;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JSplitPane;
import javax.swing.border.EmptyBorder;

import com.nn.ishop.client.gui.AbstractGUIManager;
import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.admin.customer.CTabbedPane;
import com.nn.ishop.client.gui.components.CLineBorder;
import com.nn.ishop.client.gui.components.CWhitePanel;

public class TestGUIManager extends AbstractGUIManager {

	CWhitePanel saleOrderComp, pnlTranxDetail, actionPanel;
	CTabbedPane saleTabbedPane, tranxDetailTabbedPane;
	
	public TestGUIManager( String locale) {
		init(locale);
	}
	
	public TestGUIManager() {
		init("vn");
	}
	JSplitPane saleTranxSplit;
	@Override
	protected void applyStyles() {		
		saleTranxSplit.setOrientation(0);
		
		saleTabbedPane.setTitleAt(0, Language.getInstance().getString("sale.tabbedpane.txlist"));
		saleTabbedPane.setTitleAt(1, Language.getInstance().getString("sale.tabbedpane.saleorder"));
		saleTabbedPane.setTitleAt(2, Language.getInstance().getString("sale.tabbedpane.issue.slip"));
		saleTabbedPane.setTitleAt(3, Language.getInstance().getString("sale.tabbedpane.sale.stats"));
		
		tranxDetailTabbedPane.setTitleAt(0, Language.getInstance().getString("sale.tabbedpane.detail.txinfor"));
		
		//-- Set border
		pnlTranxDetail.setBorder(BorderFactory.createCompoundBorder(
				new CLineBorder(null,CLineBorder.DRAW_BOTTOM_LEFT_RIGHT_BORDER), 
				new EmptyBorder(10,3,3,3))
				);
		actionPanel.setBorder(new CLineBorder(null,CLineBorder.DRAW_TOP_BOTTOM_BORDER));
		
	}

	@Override
	protected void bindEventHandlers() {}

	@Override
	protected void checkPermission() {}

	@Override
	public Object getData(String var) {return null;}

	@Override
	public void update() {}

	@Override
	public void updateList() {}
	private void setDefaultLookAndFeel(){}
	private void init(String locale) {		
		//setDefaultLookAndFeel();
		//-- Change template here to test
		initTemplate(this, "sale/saleMasterPage.xml", locale);
		render();
		//-- Build sub component
		TestSubGUIManager subGUI = new TestSubGUIManager();
		saleOrderComp.add(subGUI.getRootComponent(),BorderLayout.CENTER);		
		commonSetting(locale);
		bindEventHandlers();
	}
	void commonSetting(String locale){}

}
