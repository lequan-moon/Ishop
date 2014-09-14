package com.nn.ishop.client.gui.admin.customer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

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
import com.nn.ishop.client.gui.GUIActionListener;
import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.GUIActionEvent.GUIActionType;
import com.nn.ishop.client.gui.admin.user.AdminUserGUIManager;
import com.nn.ishop.client.gui.admin.user.UserListManager;
import com.nn.ishop.client.gui.components.CTwoModePanel;
import com.nn.ishop.client.gui.components.CWhitePanel;
import com.nn.ishop.client.gui.components.RoundedCornerBorder;
import com.nn.ishop.client.gui.components.VerticalFlowLayout;
import com.nn.ishop.client.gui.components.CTwoModePanel.DOCKING_DIRECTION;
import com.nn.ishop.client.gui.dialogs.CConstants;
import com.nn.ishop.client.util.Library;
import com.nn.ishop.client.util.Util;

public class AdminCustomerGUIManager extends AbstractGUIManager implements
		CActionListener, GUIActionListener, ListSelectionListener,
		TableModelListener {
	CTwoModePanel customerListContainer;
	CWhitePanel customerInforPanelContainer;
	CWhitePanel customerInforMasterContainer;
	JSplitPane mainSplit;
	
	public AdminCustomerGUIManager (String locale){
		setLocale(locale);
		init();
	}
	void init(){
		if (getLocale() != null && !getLocale().equals("")){
			initTemplate(this, "admin/khachhang/adminCustomerMasterPage.xml", getLocale());
		}else{
			initTemplate(this, "admin/khachhang/adminCustomerMasterPage.xml");		
			}
		render();
		bindEventHandlers();
		//-- Initialize sub panel 
		CustomerInformationManager cusInfoMng = new CustomerInformationManager(getLocale());
		customerInforMasterContainer.add(cusInfoMng.getRootComponent(), 
				BorderLayout.CENTER);
		
		CustomerListManager cusLstMng = new CustomerListManager(getLocale());
		customerListContainer.addContent(cusLstMng.getRootComponent());
		customerListContainer.setDockableBarLayout(new VerticalFlowLayout(1, VerticalFlowLayout.TOP), DOCKING_DIRECTION.East);
		customerListContainer.addGUIActionListener(this);
		customerListContainer.setManagerClazz(CustomerListManager.class);
		
		cusLstMng.addCActionListener(cusInfoMng);
		cusInfoMng.addCActionListener(cusLstMng);
	}
	@Override
	protected void applyStyles() {
		customerInforMasterContainer.setBackground(Color.WHITE);
		if(mainSplit != null){
			customizeSplitPaneHideFirstComponent(mainSplit);
			mainSplit.setOrientation(1);		
		}
		customerInforMasterContainer.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(1,1,1,1),				
				new RoundedCornerBorder(CConstants.BORDER_CORNER_8PX_RADIUS)
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
		Object srcObj = action.getSource();
		if(guideType.equals( GUIActionType.MINIMIZE_WINDOW)){
			int location = ((Dimension)action.getData()).height;
			if(srcObj.equals(CustomerListManager.class))
				mainSplit.setDividerLocation(location);
		}
		if(guideType.equals( GUIActionType.MAXIMIZE_WINDOW)){
			if(srcObj.equals(CustomerListManager.class))
				mainSplit.resetToPreferredSizes();
		}
	}

	public void valueChanged(ListSelectionEvent e) {
	}

	public void tableChanged(TableModelEvent e) {
	}
	protected void checkPermission(){				
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
		f.setIconImage(Util.getImage(Library.PROGRAM_LOGO));
		guiManager = new AdminCustomerGUIManager(Language.getInstance().getLocale());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().add(guiManager.getRootComponent());		
		
		f.pack();
		f.validate();
		
		f.setVisible(true);
	}
}
