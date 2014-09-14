package com.nn.ishop.client.gui.admin.user;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import apple.awt.CCheckbox;

import com.nn.ishop.client.CActionEvent;
import com.nn.ishop.client.CActionListener;
import com.nn.ishop.client.gui.AbstractGUIManager;
import com.nn.ishop.client.gui.GUIActionEvent;
import com.nn.ishop.client.gui.GUIActionListener;
import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.admin.customer.CTabbedPane;
import com.nn.ishop.client.gui.components.CDialogsLabelButton;
import com.nn.ishop.client.gui.components.CList;
import com.nn.ishop.client.gui.components.CTextField;
import com.nn.ishop.client.gui.components.CWhitePanel;
import com.nn.ishop.client.gui.components.RoundedBorderComponent;
import com.nn.ishop.client.gui.components.RoundedCornerBorder;
import com.nn.ishop.client.gui.dialogs.CConstants;
import com.nn.ishop.client.logic.admin.UserLogic;
import com.nn.ishop.client.logic.object.EmployeeWrapper;
import com.nn.ishop.client.util.Util;
import com.nn.ishop.server.dto.user.Function;
import com.nn.ishop.server.dto.user.UserRole;

public class UserRoleManager extends AbstractGUIManager implements CActionListener,
		GUIActionListener, ListSelectionListener, TableModelListener {

	List<CActionListener> cActionListnerVct = new Vector<CActionListener>();
	CList lstAvailableRole;
	CList lstAssginedRole;
	
	CDialogsLabelButton userRoleAssignButton;
	CDialogsLabelButton userRoleRemoveButton;
	
	CTabbedPane userRoleAssignmentTabbedPane;
	CWhitePanel userRoleAssignmentContainer2;
	CWhitePanel userRoleAssignmentContainer;
	CWhitePanel roleMaintainActionPanel;
	
	RoundedBorderComponent pnlAvailableRole;
	RoundedBorderComponent pnlAssginedRole;
	/**
	 * 
	 * @param locale
	 */
	public UserRoleManager(String locale) {
		setLocale(locale);
		init();
	}

	void init() {
		if (getLocale() != null && !getLocale().equals("")) {
			initTemplate(this, "admin/nguoidung/userRoleAssignmentPage.xml", getLocale());
		} else {
			initTemplate(this, "admin/nguoidung/userRoleAssignmentPage.xml");
		}
		render();		
		loadAvailableRoles();
		bindEventHandlers();
	}
		
	@Override
	protected void applyStyles() {
		userRoleAssignmentContainer2.setBorder(
				BorderFactory.createEmptyBorder(3, 3, 3, 3)
				);
		userRoleAssignmentTabbedPane.setTitleAt(0, Language.getInstance().getString("user.role.asignment"));
		
		userRoleAssignmentTabbedPane.setIconAt(0, Util.getIcon("tabbed/tabbed-icon-assign-role.png"));

		 pnlAvailableRole.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createEmptyBorder(1,1,1,1),				
					new RoundedCornerBorder(CConstants.BORDER_CORNER_8PX_RADIUS)
					));
		pnlAssginedRole.setBorder(BorderFactory.createCompoundBorder(
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
	public void guiActionPerformed(GUIActionEvent action) {
	}

	public void valueChanged(ListSelectionEvent e) {

	}
	
	public void tableChanged(TableModelEvent e) {
	}

	@Override
	protected void checkPermission() {
	}

	 
	public void addCActionListener(CActionListener al) {
		cActionListnerVct.add(al);
	}

	public void removeCActionListener(CActionListener al) {
		cActionListnerVct.remove(al);
	}
	
	/** TODO Methos related to User Profile */
	@SuppressWarnings("unchecked")
	public void cActionPerformed(CActionEvent action) {
		
		if (CActionEvent.NOTIFY_ADD_USER == action.getAction()){
			 
			loadAvailableRoles();
			List<UserRole> assignedRoleLst = new ArrayList<UserRole>();
			Object[] displayedList = assignedRoleLst.toArray();
			lstAssginedRole.setListData(displayedList);	
		}else if(CActionEvent.NEW_USER  == action.getAction()) {
			List<UserRole> assignedRoleLst = new ArrayList<UserRole>();
			Object[] displayedList = assignedRoleLst.toArray();
			lstAssginedRole.setListData(displayedList);				
		}else if(CActionEvent.NOTIFY_UPDATE_USER == action.getAction()) {
			logger.info("UserRoleManager: start updateing role for selected user");
			EmployeeWrapper emWrapper = (EmployeeWrapper) action.getData();
			List<UserRole> assignedRoleLst  = getAssignedRoleList();	
			logger.info("UserRoleManager: assignedRoleLst " + assignedRoleLst);
			emWrapper.setRole(assignedRoleLst);
			
			UserLogic.getInstance().updateEmployeeRole(emWrapper.getEm().getId(), 
					emWrapper.getRole());
			emWrapper = null;			
			assignedRoleLst = new ArrayList<UserRole>();
			Object[] displayedList = assignedRoleLst.toArray();
			lstAssginedRole.setListData(displayedList);	
		}else if (CActionEvent.LIST_SELECT_ITEM == action.getAction()) {
			EmployeeWrapper emWrapper = (EmployeeWrapper) action.getData();
			if(emWrapper == null) return;
			List<UserRole> assignedRoleLst = emWrapper.getRole();
			if (assignedRoleLst != null) {
				Object[] displayedList = assignedRoleLst.toArray();
				lstAssginedRole.setListData(displayedList);
			}
			try {
				loadAvailableRoles();
			} catch (Exception e) {
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void loadAvailableRoles(){
		try {
			List<UserRole> availRoleList = UserLogic.getInstance().loadAvailableUserRoles();
			lstAvailableRole.setListData(availRoleList.toArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List<UserRole> getAssignedRoleList(){
		List<UserRole> ret = new ArrayList<UserRole>();		
		for(int i=0;i<lstAssginedRole.getModel().getSize();i++){
			ret.add((UserRole)lstAssginedRole.getModel().getElementAt(i));
		}
		return ret;
	}
	
	/**
	 * Assign Roles  to Employee or assign Functions to Role. GUI only action, no interaction to database
	 * @param srcListComp available list
	 * @param destListComp assigned list
	 * @param assignRole
	 */
	@SuppressWarnings("unchecked")
	void assign(CList srcListComp, CList destListComp, boolean assignRole){
		if(assignRole){
				List<UserRole> selectedRoleList = (List<UserRole>) srcListComp.getSelectedValuesList();
				List<UserRole> assignedRoleLst = getAssignedRoleList();
				for(UserRole selectedRole: selectedRoleList)
				{
					if (selectedRole != null) {
						if (assignedRoleLst != null) {
							boolean isAlreadyAssignedRole = false;
							for(UserRole ur:assignedRoleLst){
								if(ur.getRoleId() == selectedRole.getRoleId())
									isAlreadyAssignedRole = true;
							}
							if(!isAlreadyAssignedRole)
								assignedRoleLst.add(selectedRole);
						} else {
							cActionPerformed(new CActionEvent(this,
									CActionEvent.NOTIFY_ACTION,
									Language.getInstance().getString("role.already.assigned")));
						}
					}
				}
				destListComp.setListData(assignedRoleLst.toArray());
				destListComp.validate();
		}
	}
	/**
	 * Remove selected Role or Function from list component
	 * unassigns Role from Employee or Function from Role
	 * @param assignedList assigned list
	 * @param assignRole true if do this for role and vice versa
	 */
	@SuppressWarnings("unchecked")
	void unassign(CList assignedList, boolean unassignsRole){
		if(unassignsRole){
				UserRole selectedRole = (UserRole) assignedList.getSelectedValue();
				if (selectedRole != null) {
					List<UserRole> assignedRoleLst = getAssignedRoleList();
					assignedRoleLst.remove(selectedRole);
					Object[] displayedList = assignedRoleLst.toArray();
					assignedList.setListData(displayedList);					
				}
		}
	}
	public Action assignRoleAction = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent arg0) {
			assign(lstAvailableRole, lstAssginedRole, true);
		}
	};

	public Action unassignRoleAction = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			unassign(lstAssginedRole,true);
		}
	};	
}
