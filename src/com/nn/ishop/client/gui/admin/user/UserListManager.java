package com.nn.ishop.client.gui.admin.user;

import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import com.nn.ishop.client.CActionEvent;
import com.nn.ishop.client.CActionEvent.CAction;
import com.nn.ishop.client.CActionListener;
import com.nn.ishop.client.gui.AbstractGUIManager;
import com.nn.ishop.client.gui.GUIActionEvent;
import com.nn.ishop.client.gui.GUIActionEvent.GUIActionType;
import com.nn.ishop.client.gui.GUIActionListener;
import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.admin.company.CompanyHelper;
import com.nn.ishop.client.gui.components.CLabel;
import com.nn.ishop.client.gui.components.CLineBorder;
import com.nn.ishop.client.gui.components.CList;
import com.nn.ishop.client.gui.components.CTextField;
import com.nn.ishop.client.gui.dialogs.SystemMessageDialog;
import com.nn.ishop.client.logic.CommonLogic;
import com.nn.ishop.client.logic.admin.UserLogic;
import com.nn.ishop.client.logic.object.EmployeeWrapper;
import com.nn.ishop.server.dto.company.Company;
import com.nn.ishop.server.dto.user.Employee;

public class UserListManager extends AbstractGUIManager implements CActionListener, GUIActionListener, ListSelectionListener, TableModelListener {
	CList userListPaging;

	Vector<EmployeeWrapper> employeeWrapperVector;

	UserLogic logic = UserLogic.getInstance();

	JToolBar userListTreeToolbar;
	CTextField txtSearch;
	Company selectCompany = null;
	CLabel lblTenCongty;

	public UserListManager(String locale) {
		setLocale(locale);
		init();
	}

	void init() {
		if (getLocale() != null && !getLocale().equals("")) {
			initTemplate(this, "admin/nguoidung/userListPage.xml", getLocale());
		} else {
			initTemplate(this, "admin/nguoidung/userListPage.xml");
		}
		render();
		prepareData();
		bindEventHandlers();
	}

	@SuppressWarnings("unchecked")
	private void prepareData() {
		logic = UserLogic.getInstance();
		employeeWrapperVector = logic.loadListEmployeeWrapper();
		userListPaging.setListData(employeeWrapperVector);
		selectFirstUser();
	}
	
	void selectFirstUser(){
		if(userListPaging.getListData()!= null && userListPaging.getListData().size()>0){
			userListPaging.setSelectedIndex(0);
			EmployeeWrapper u = (EmployeeWrapper) userListPaging.getSelectedValue();
			CActionEvent ae = new CActionEvent(this, CActionEvent.LIST_SELECT_ITEM, u);
			fireCAction(ae);
		}
	}

	@SuppressWarnings("unchecked")
	private void prepareDataWithSelectedCompany(Company selectedCompany) {
		logic = UserLogic.getInstance();
		employeeWrapperVector = logic.loadListEmployeeWrapperWithSelectedCompany(selectedCompany);
		userListPaging.setListData(employeeWrapperVector);
		selectFirstUser();
	}

	@Override
	protected void applyStyles() {
		searchStr = txtSearch.getText();
		lblTenCongty.setBorder(new CLineBorder(null, CLineBorder.DRAW_TOP_BORDER));
	}

	@Override
	protected void bindEventHandlers() {
		userListPaging.addListSelectionListener(this);
		userListPaging.addGUIActionListener(this);
		txtSearch.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtSearch.setText(Language.getInstance().getString(""));
				super.focusGained(e);
			}
			@Override
			public void focusLost(FocusEvent e) {
				txtSearch.setText(Language.getInstance().getString("defaultSearchText"));
				super.focusLost(e);
			}
		});
		txtSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					searchInList();
				super.keyReleased(e);
			}
		});
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
		switch (action.getAction()) {
		case CActionEvent.NOTIFY_UPDATE_USER:
			EmployeeWrapper empWrap = (EmployeeWrapper) action.getData();
			Employee user2update = empWrap.getEm();
			logic.updateEmployee(user2update);
			if (selectCompany != null) {
				prepareDataWithSelectedCompany(selectCompany);
			}
			break;
		case CActionEvent.NOTIFY_ADD_USER:
			Employee newEmployee = (Employee) action.getData();
			newEmployee.setActivated(true);
			newEmployee.setUsage_flg(1);
			newEmployee = logic.insertEmployee(newEmployee);
			EmployeeWrapper newEmployeeWrapper = new EmployeeWrapper(newEmployee);
			employeeWrapperVector.add(newEmployeeWrapper);
			if (selectCompany != null) {
				prepareDataWithSelectedCompany(selectCompany);
			}
			break;
		default:
			break;
		}
	}

	public void guiActionPerformed(GUIActionEvent action) {
		if (GUIActionType.DELETE.equals(action.getAction())) {
			int decision = SystemMessageDialog.showConfirmDialog(null, Language.getInstance().getString("user.confirm.delete: ")
					+ ((EmployeeWrapper) action.getData()), SystemMessageDialog.SHOW_OK_CANCEL_OPTION);
			if (decision == 0) {
				EmployeeWrapper user2remove = (EmployeeWrapper) action.getData();

				Employee emp = user2remove.getEm();
				logic.updateEmployee(emp, CAction.DELETE);

				// set selectedValue to update detail info, then remove
				userListPaging.setSelectedValue(null, false);
				employeeWrapperVector.remove(user2remove);
				// userListPaging.setListData(userList.toArray());
				userListPaging.validate();
			}
		} else if (GUIActionType.EDIT.equals(action.getAction())) {
			CActionEvent ae = new CActionEvent(action.getSource(), CActionEvent.UPDATE, action.getData());
			fireCAction(ae);
		} else if (GUIActionType.NEW.equals(action.getAction())) {
			CActionEvent ae = new CActionEvent(action.getSource(), CActionEvent.ADD, new EmployeeWrapper());
			fireCAction(ae);
		}
	}

	public void valueChanged(ListSelectionEvent e) {
		CList src = (CList) e.getSource();
		EmployeeWrapper u = (EmployeeWrapper) src.getSelectedValue();
		CActionEvent ae = new CActionEvent(e.getSource(), CActionEvent.LIST_SELECT_ITEM, u);
		fireCAction(ae);
	}

	public void fireCAction(CActionEvent action) {
		for (CActionListener al : cActionListnerVct) {
			al.cActionPerformed(action);
		}
	}

	public void tableChanged(TableModelEvent e) {
	}

	@Override
	protected void checkPermission() {
	}

	Vector<CActionListener> cActionListnerVct = new Vector<CActionListener>();

	public void addCActionListener(CActionListener al) {
		cActionListnerVct.add(al);
	}

	public void removeCActionListener(CActionListener al) {
		cActionListnerVct.remove(al);
	}

	void sortTheList(int sortOrder) {
		CommonLogic.sortTheCList(sortOrder, userListPaging);
		if (userListPaging.getModel().getSize() >= 0)
			userListPaging.setSelectedIndex(0);
	}

	public Action ACT_REFRESH = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			prepareData();
			selectCompany = null;
		}
	};

	public Action ACT_SORT_AZ = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			sortTheList(0);
		}
	};

	public Action ACT_SORT_ZA = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			sortTheList(1);
		}
	};

	public Action ACT_SEARCH = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			searchInList();
		}
	};

	public Action ACT_DELETE = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			if (userListPaging.getModel().getSize() <= 0 || userListPaging.getSelectedValue() == null)
				return;	
			
			EmployeeWrapper emw = (EmployeeWrapper) userListPaging.getSelectedValue();
			int res = SystemMessageDialog.showConfirmDialog(null, 
					Language.getInstance().getString("confirm.delete") +emw.getEm().toString() , 
					SystemMessageDialog.SHOW_OK_CANCEL_OPTION);
			if(res != 0){
				return;
			}
			emw.getEm().setUsage_flg(-1);
			try {
				Employee em = emw.getEm();
				UserLogic.getInstance().updateEmployee(em, CAction.DELETE);
				prepareData();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	};

	/** Indices of matching result */
	Vector<Integer> searchRet = new Vector<Integer>();

	String searchStr = null;

	void searchInList() {
		if (txtSearch.getText() == null || txtSearch.getText().equals(""))
			return;
		try {
			if (!searchStr.equals(txtSearch.getText())) {// Search string
															// changed
				searchStr = txtSearch.getText();
				for (int i = 0; i < userListPaging.getModel().getSize(); i++) {
					EmployeeWrapper c = (EmployeeWrapper) userListPaging.getModel().getElementAt(i);
					if (c != null && c.getEm().getName().contains(searchStr))
						searchRet.add(new Integer(i));
				}
			}
			if (searchRet.size() > 0) {
				userListPaging.setSelectedIndex(searchRet.get(0).intValue());
				userListPaging.ensureIndexIsVisible(userListPaging.getSelectedIndex());
				searchRet.remove(0);
			} else {
				SystemMessageDialog.showMessageDialog(null, Language.getInstance().getString("search.last.result"),
						SystemMessageDialog.SHOW_OK_OPTION);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Action cmdChooseCompanyDialog = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			selectCompany = CompanyHelper.chooseCompany();
			if (selectCompany != null) {
				// txtItemCategory.setText(selectCat.getCategoryName());
				prepareDataWithSelectedCompany(selectCompany);
				if (selectCompany != null) {
					lblTenCongty.setText(selectCompany.getName());
				}else{
					lblTenCongty.setText(Language.getInstance().getString("all.company"));
				}
			}
		}
	};
}
