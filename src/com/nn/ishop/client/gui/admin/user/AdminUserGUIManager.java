package com.nn.ishop.client.gui.admin.user;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
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
import com.nn.ishop.client.gui.GUIManager;
import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.components.CDialogsLabelButton;
import com.nn.ishop.client.gui.components.CLineBorder;
import com.nn.ishop.client.gui.components.CTwoModePanel;
import com.nn.ishop.client.gui.components.CTwoModePanel.DOCKING_DIRECTION;
import com.nn.ishop.client.gui.components.CWhitePanel;
import com.nn.ishop.client.gui.components.VerticalFlowLayout;
import com.nn.ishop.client.gui.dialogs.CConstants;
import com.nn.ishop.client.gui.helper.DialogHelper;
import com.nn.ishop.client.logic.object.EmployeeWrapper;
import com.nn.ishop.client.util.Library;
import com.nn.ishop.client.util.Util;

public class AdminUserGUIManager extends AbstractGUIManager implements
		GUIActionListener, CActionListener, TableModelListener,
		ListSelectionListener {
	CTwoModePanel userListPanel;	
	CWhitePanel userInforContainer;
	
	CWhitePanel listAndRoleAssignmentContainer;
	CWhitePanel 	userInformationActionContainer;
	
	
	CWhitePanel roleTabbedPaneContainer;
	JSplitPane mainSplit;
	CDialogsLabelButton userInforSaveButton;
	
	public AdminUserGUIManager (String locale){
		setLocale(locale);
		init();
	}
	void init(){
		if (getLocale() != null && !getLocale().equals("")){
			initTemplate(this, "admin/nguoidung/adminUserMasterPage.xml", getLocale());
		}else{
			initTemplate(this, "admin/nguoidung/adminUserMasterPage.xml");		
		}
		render();
		bindEventHandlers();
		UserInformationManager uInfor = new UserInformationManager(getLocale());
		
		UserListManager uList = new UserListManager(getLocale());
		
		UserRoleManager uRole = new UserRoleManager(getLocale());
		
		uInfor.addCActionListener(this);
		uInfor.addCActionListener(uList);
		this.addCActionListener(uInfor);
		uInfor.addCActionListener(uRole);
		
		this.addCActionListener(uRole);
		uRole.addCActionListener(this);
		
		this.addCActionListener(uList);
		uList.addCActionListener(this);
		uList.addCActionListener(uInfor);
		uList.addCActionListener(uRole);
		
		userInforContainer.add(uInfor.getRootComponent(),BorderLayout.CENTER);
		userListPanel.addContent(uList.getRootComponent());
		
		userListPanel.addGUIActionListener(this);
		
		userListPanel.setManagerClazz(UserListManager.class);
		userListPanel.setDockableBarLayout(new VerticalFlowLayout(1, VerticalFlowLayout.TOP), DOCKING_DIRECTION.East);
		roleTabbedPaneContainer.add(uRole.getRootComponent(), BorderLayout.CENTER);
		
		if(uList.userListPaging.getListData()!= null && uList.userListPaging.getListData().size()>0){
			uList.userListPaging.setSelectedIndex(1);
			EmployeeWrapper u = (EmployeeWrapper) uList.userListPaging.getSelectedValue();
			CActionEvent ae = new CActionEvent(this, CActionEvent.LIST_SELECT_ITEM, u);
			uList.fireCAction(ae);
		}
	}
	
	@Override
	protected void applyStyles() {
		listAndRoleAssignmentContainer.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		if(mainSplit != null){
			customizeSplitPaneHideFirstComponent(mainSplit);
			mainSplit.setOrientation(1);		
		}
		userInformationActionContainer.setBorder(BorderFactory.createCompoundBorder(
				new CLineBorder( CConstants.TEXT_BORDER_COLOR, CLineBorder.DRAW_TOP_BORDER),
				BorderFactory.createEmptyBorder(2, 2, 2, 2)
				));
	}

	@Override
	protected void bindEventHandlers() {
		saveAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK));			
		userInforSaveButton.getActionMap().put("saveAction", saveAction);
		userInforSaveButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
		        (KeyStroke) saveAction.getValue(Action.ACCELERATOR_KEY), "saveAction");
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
			if(srcObj.equals(UserListManager.class))
				mainSplit.setDividerLocation(location);
		}
		if(guideType.equals( GUIActionType.MAXIMIZE_WINDOW)){
			if(srcObj.equals(UserListManager.class))
				mainSplit.resetToPreferredSizes();
		}
	}

	public void fireCAction(CActionEvent action) {
		for(CActionListener al:listeners)
			al.cActionPerformed(action);
	}
	public void cActionPerformed(CActionEvent action){
		
	}

	public void tableChanged(TableModelEvent e) {
	}

	public void valueChanged(ListSelectionEvent e) {
	}
	@Override
	protected void checkPermission() {
	}
	
	Vector<CActionListener> listeners = new Vector<CActionListener>();
	public void addCActionListener(CActionListener al) {
		listeners.add(al);
	}
	public void removeCActionListener(CActionListener al) {
		listeners.remove(al);
	}
	public Action saveAction = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent arg0) {
			fireCAction(new CActionEvent(this, 
					CActionEvent.SAVE_USER, null));
		}
	};

	public Action newAction = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent arg0) {
			fireCAction(new CActionEvent(this, 
					CActionEvent.NEW_USER,null));
		}
	};
	public Action editRoleAction = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent arg0) {
			DialogHelper.openRoleDialog(GUIManager.mainFrame);
		}
	};
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
		guiManager = new AdminUserGUIManager(Language.getInstance().getLocale());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().add(guiManager.getRootComponent());		
		
		f.pack();
		f.validate();
		
		f.setVisible(true);
	}
}
