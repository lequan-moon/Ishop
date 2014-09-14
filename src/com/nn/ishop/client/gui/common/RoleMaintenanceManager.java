package com.nn.ishop.client.gui.common;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JSplitPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.nn.ishop.client.gui.components.CCheckBox;
import com.nn.ishop.client.gui.AbstractGUIManager;
import com.nn.ishop.client.gui.GUIActionEvent;
import com.nn.ishop.client.gui.GUIActionListener;
import com.nn.ishop.client.gui.components.CDialogsLabelButton;
import com.nn.ishop.client.gui.components.CList;
import com.nn.ishop.client.gui.components.CTextField;
import com.nn.ishop.client.gui.components.RoundedBorderComponent;
import com.nn.ishop.client.gui.components.RoundedCornerBorder;
import com.nn.ishop.client.gui.dialogs.CConstants;
import com.nn.ishop.client.logic.admin.UserLogic;
import com.nn.ishop.server.dto.user.Function;
import com.nn.ishop.server.dto.user.UserRole;

public class RoleMaintenanceManager extends AbstractGUIManager implements
		GUIActionListener, ListSelectionListener {
	CList cListAvailableMaintainRole;
	CList lstAvailableFunction;
	CList lstAssginedFunction;
	
	CDialogsLabelButton assignFuncButton;
	CDialogsLabelButton removeFuncButton;
	
	CDialogsLabelButton newRoleButton;
	CDialogsLabelButton saveRoleButton;
	JSplitPane roleMaintainSplit;
    CTextField txtRoleId = new CTextField();
	
	CTextField txtRoleName;
	CTextField txtRoleDesc;
	com.nn.ishop.client.gui.components.CCheckBox chkSystem;
	RoundedBorderComponent lstAvailableFunctionPanel, containerListAvailableMaintainRole, lstAssginedFunctionPanel;

	void init() {
		initTemplate(this, "admin/nguoidung/userRoleMaitenancePage.xml");
		render();
		bindEventHandlers();
		loadAvailableRoles();	
	}
	public RoleMaintenanceManager(String locale){
			setLocale(locale);
			init();
	}
	public RoleMaintenanceManager() {
	}

	public void guiActionPerformed(GUIActionEvent action) {
	}

	@Override
	protected void bindEventHandlers() {
		cListAvailableMaintainRole.addListSelectionListener(this);
		List<Function> availFuncs = UserLogic.getInstance().loadAvailableFunction();
		Vector<Function> vctFunc = new Vector<Function>();
		for(Function f:availFuncs)
			vctFunc.add(f);
		lstAvailableFunction.setListData(vctFunc);
		txtRoleId.setText("-1");
	}

	@Override
	public Object getData(String var) {
		return null;
	}

	@Override
	protected void applyStyles() {
		containerListAvailableMaintainRole.setBorder(BorderFactory.createCompoundBorder(
		BorderFactory.createEmptyBorder(1,1,1,1),				
		new RoundedCornerBorder(CConstants.BORDER_CORNER_8PX_RADIUS)
		));
		lstAvailableFunctionPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(1,1,1,1),				
				new RoundedCornerBorder(CConstants.BORDER_CORNER_8PX_RADIUS)
				));
		lstAssginedFunctionPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(1,1,1,1),				
				new RoundedCornerBorder(CConstants.BORDER_CORNER_8PX_RADIUS)
				));
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
	public void valueChanged(ListSelectionEvent e) {
		UserRole ur = (UserRole)cListAvailableMaintainRole.getSelectedValue();
		if(e.getSource()== cListAvailableMaintainRole && ur != null){
			updateRoleInfo(ur);
		}
		
	}
	//==== FUNCTIONS
		void updateRoleInfo(UserRole ur){
			//lstAssginedFunction
			txtRoleName.setText(ur.getRoleName());
			txtRoleDesc.setText(ur.getRoleNote());
			txtRoleId.setText(String.valueOf(ur.getRoleId()));
			
			if(chkSystem != null){
				chkSystem.setSelected(ur.isSystemRole());
			}
			List<Function> funcs = null;
			try {
				funcs = UserLogic.getInstance().loadRoleFunction(ur.getRoleId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			Vector<Function> vctFuncs = new Vector<Function>();
			for (Function f:funcs)
				vctFuncs.add(f);
			lstAssginedFunction.setListData(vctFuncs);
		}
		private List<Function> getAssignedFunctionList(){
			List<Function> ret = new ArrayList<Function>();		
			for(int i=0;i<lstAssginedFunction.getModel().getSize();i++){
				ret.add((Function)lstAssginedFunction.getModel().getElementAt(i));
			}
			return ret;
		}

		void newRole(){
			txtRoleName.setText(null);
			txtRoleDesc.setText(null);
			txtRoleId.setText("-1");
			lstAssginedFunction.setListData(new Vector<Function>());
		}	
		
		//==== Actions
		public Action assignAction = new AbstractAction() {
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e) {
				assign(lstAvailableFunction, lstAssginedFunction);
			}
		};
		
		public Action removeAction = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				unassign(lstAssginedFunction);
			}
		};
		public Action newRoleButtonAction = new AbstractAction() {
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e) {
				newRole();
			}
		};
		public Action saveRoleButtonAction = new AbstractAction() {
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e) {
				saveRole();
			}
		};		
		void saveRole(){			
			UserRole ur = new UserRole();
			ur.setRoleName(txtRoleName.getText());
			ur.setRoleNote(txtRoleDesc.getText());
			ur.setSystemRole(false);
			
			List<Function> funcs = getAssignedFunctionList();
			ur.setRoleFunctions(funcs);
			if(txtRoleId.getText().equals("-1")){
				//-- add new
				UserLogic.getInstance().insertRole(ur);
				newRole();
			}else{
				//-- update
				ur.setRoleId(Integer.parseInt(txtRoleId.getText()));
				UserLogic.getInstance().updateRole(ur);
				newRole();
			}	
			loadAvailableRoles();
		}
		public void loadAvailableRoles(){
			try {
				List<UserRole> availRoleList = UserLogic.getInstance().loadAvailableUserRoles();				
				cListAvailableMaintainRole.setListData(availRoleList.toArray());				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		/**
		 * Assign Roles  to Employee or assign Functions to Role. GUI only action, no interaction to database
		 * @param srcListComp available list
		 * @param destListComp assigned list
		 * @param assignRole
		 */
		@SuppressWarnings("unchecked")
		void assign(CList srcListComp, CList destListComp){
			List<Function> selectedFuncList = (List<Function>)srcListComp.getSelectedValuesList();
			List<Function> assignedFuncList = getAssignedFunctionList();
			
			for(Function f:selectedFuncList){
				boolean assigned = false;
				for(Function f2: assignedFuncList){
					if(f2.getFunctionId() == f.getFunctionId())
						assigned = true;
				}
				if(!assigned){
					assignedFuncList.add(f);
				}
			}
			destListComp.setListData(assignedFuncList.toArray());
			destListComp.validate();
				
		}
		/**
		 * Remove selected Role or Function from list component
		 * unassigns Role from Employee or Function from Role
		 * @param assignedList assigned list
		 * @param assignRole true if do this for role and vice versa
		 */
		void unassign(CList assignedList){
			List<Function> selectedFunc = (List<Function>)assignedList.getSelectedValuesList();
			
			if (selectedFunc != null) {
				List<Function> assignedFuncLst = getAssignedFunctionList();
				for(Function f:selectedFunc){
					assignedFuncLst.remove(f);
				}
				Object[] displayedList = assignedFuncLst.toArray();
				assignedList.setListData(displayedList);					
			}
		}
}
