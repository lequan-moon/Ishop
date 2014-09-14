package com.nn.ishop.client.gui.admin.company;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import com.nn.ishop.client.CActionEvent;
import com.nn.ishop.client.CActionListener;
import com.nn.ishop.client.gui.AbstractGUIManager;
import com.nn.ishop.client.gui.GUIActionEvent;
import com.nn.ishop.client.gui.GUIManager;
import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.GUIActionEvent.GUIActionType;
import com.nn.ishop.client.gui.GUIActionEvent.GUIType;
import com.nn.ishop.client.gui.GUIActionListener;
import com.nn.ishop.client.gui.components.CTwoModePanel;
import com.nn.ishop.client.gui.components.CTwoModePanel.DOCKING_DIRECTION;
import com.nn.ishop.client.gui.components.RoundedBorderComponent;
import com.nn.ishop.client.gui.components.RoundedCornerBorder;
import com.nn.ishop.client.gui.components.VerticalFlowLayout;
import com.nn.ishop.client.gui.home.HomeGUIManager;

public class AdminCompanyGUIManger extends AbstractGUIManager implements
		CActionListener, GUIActionListener, ListSelectionListener,
		TableModelListener {
	RoundedBorderComponent companyInforPanelContainer;
	CTwoModePanel companyListPanelContainer;
	//CWhitePanel companyListPanelContainer;
	JSplitPane mainSplit;
	
	public AdminCompanyGUIManger (String locale){
		setLocale(locale);
		init();
	}
	void init(){
		if (getLocale() != null && !getLocale().equals("")){
			initTemplate(this, "admin/congty/adminCompanyMasterPage.xml", getLocale());
		}else{
			initTemplate(this, "admin/congty/adminCompanyMasterPage.xml");		
			}
		render();
		bindEventHandlers();
		CompanyInformationManager companyInformationManager = new CompanyInformationManager(getLocale());
		CompanyListManager companyListManager = new CompanyListManager(getLocale());
		
		companyListManager.addCActionListener(companyInformationManager);
		//-- Implement content here
		companyInforPanelContainer.add(companyInformationManager.getRootComponent());
		companyListPanelContainer.addContent(companyListManager.getRootComponent());
		companyListPanelContainer.addGUIActionListener(this);
		companyListPanelContainer.setDockableBarLayout(new VerticalFlowLayout(1, VerticalFlowLayout.TOP)
		, DOCKING_DIRECTION.North);		
		companyListPanelContainer.setManagerClazz(AdminCompanyGUIManger.class);
		//companyListPanelContainer.add(companyListManager.getRootComponent());
		
	}
	@Override
	protected void applyStyles() {
		companyInforPanelContainer.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(1,10,1,1),				
				new RoundedCornerBorder(8)
				));
	}

	@Override
	protected void bindEventHandlers() {
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
	}

	public void guiActionPerformed(GUIActionEvent action) {
		GUIActionType guideType = action.getAction(); 
		if(guideType.equals( GUIActionType.MINIMIZE_WINDOW)){
			int location = ((Dimension)action.getData()).height;
			mainSplit.setDividerLocation(location);
		}
		if(guideType.equals( GUIActionType.MAXIMIZE_WINDOW)){
		  mainSplit.resetToPreferredSizes();
		}
		
	}

	public void valueChanged(ListSelectionEvent e) {
	}

	public void tableChanged(TableModelEvent e) {
	}
	protected void checkPermission() {
	}
	public void addCActionListener(CActionListener al) {
	}
	public void removeCActionListener(CActionListener al) {
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
		
		guiManager = new AdminCompanyGUIManger(Language.getInstance().getLocale());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().add(guiManager.getRootComponent());		
		
		f.pack();
		f.validate();
		
		f.setVisible(true);
	}
}
