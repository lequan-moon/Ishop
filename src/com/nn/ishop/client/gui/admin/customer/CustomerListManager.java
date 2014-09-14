package com.nn.ishop.client.gui.admin.customer;

import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.nn.ishop.client.CActionEvent;
import com.nn.ishop.client.CActionListener;
import com.nn.ishop.client.gui.AbstractGUIManager;
import com.nn.ishop.client.gui.GUIActionEvent;
import com.nn.ishop.client.gui.GUIActionListener;
import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.components.CLineBorder;
import com.nn.ishop.client.gui.components.CList;
import com.nn.ishop.client.gui.components.CTextField;
import com.nn.ishop.client.gui.dialogs.SystemMessageDialog;
import com.nn.ishop.client.logic.CommonLogic;
import com.nn.ishop.client.logic.admin.CustomerLogic;
import com.nn.ishop.server.dto.customer.Customer;

public class CustomerListManager extends AbstractGUIManager implements
		CActionListener, GUIActionListener, ListSelectionListener{
	CList customerListPane;
	JToolBar customerListTreeToolbar;	
	CTextField txtSearch;
	public CustomerListManager (String locale){
		setLocale(locale);
		init();
		
	}
	
	void init(){
		if (getLocale() != null && !getLocale().equals("")){
			initTemplate(this, "admin/khachhang/customerListPage.xml", getLocale());
		}else{
			initTemplate(this, "admin/khachhang/customerListPage.xml");		
			}
		render();
		bindEventHandlers();
		//-- Initialize sub panel 
		prepareData();
	}
	
	@Override	
	protected void applyStyles() {
		customerListTreeToolbar.setBorder(BorderFactory.createCompoundBorder(
				new CLineBorder(null, CLineBorder.DRAW_BOTTOM_BORDER),
				new EmptyBorder(1,1,1,1)
				));		
		searchStr = txtSearch.getText();
	}
	
	private void prepareData() {		
		try {
			Vector<Customer> customerList = CustomerLogic.getInstance()
					.loadCustomerVector();
			customerListPane.setListData(customerList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void bindEventHandlers() {
		customerListPane.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
			}			
		});
		customerListPane.addListSelectionListener(this);
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

	void showClistPopup(){	
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
		if(action.getAction() == CActionEvent.NOTIFY_MODIFY_CUSTOMER){
			prepareData();
		}
		
	}

	public void guiActionPerformed(GUIActionEvent action) {
	}
	
	protected void checkPermission() {
	}
	
	public void addCActionListener(CActionListener al) {
		vctCActionListener.add(al);
	}
	
	public void removeCActionListener(CActionListener al) {
		vctCActionListener.remove(al);
	}
	
	
	Vector<CActionListener> vctCActionListener = new Vector<CActionListener>();	
	
	public void fireCAction(CActionEvent action) {
		for (CActionListener al : vctCActionListener) {
			al.cActionPerformed(action);
		}
	}
	
	public void valueChanged(ListSelectionEvent e) {
		CList src = (CList) e.getSource();
		Customer customer = (Customer) src.getSelectedValue();
		CActionEvent ae = new CActionEvent(e.getSource(), CActionEvent.LIST_SELECT_ITEM, customer);
		fireCAction(ae);
	}
	
	/** Indices of matching result */
	Vector<Integer> searchRet = new Vector<Integer>();
	
	String searchStr = null;	
	
	void searchInList(){
		if(txtSearch.getText() == null
				|| txtSearch.getText().equals(""))
			return;		
		try {			
			if(!searchStr.equals(txtSearch.getText())){// Search string changed
				searchStr = txtSearch.getText();
				for (int i = 0; i < customerListPane.getModel().getSize(); i++) {
					Customer c = (Customer) customerListPane.getModel()
							.getElementAt(i);
					if(c != null && c.getName().contains(searchStr))
						searchRet.add(new Integer(i));					
				}
			}
			if(searchRet.size() >0)
			{
				customerListPane.setSelectedIndex(searchRet.get(0).intValue());				
				customerListPane.ensureIndexIsVisible(customerListPane.getSelectedIndex());
				searchRet.remove(0);
			}else{
				SystemMessageDialog.showMessageDialog(null, 
						Language.getInstance().getString("search.last.result"), SystemMessageDialog.SHOW_OK_OPTION);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void sortTheList(int sortOrder){
		CommonLogic.sortTheCList(sortOrder, customerListPane);
	}
	
	public Action ACT_SEARCH = new AbstractAction() {	
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			searchInList();
		}
	};
	
	public Action ACT_DELETE = new AbstractAction() {	
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			if(customerListPane.getModel().getSize() <=0 || customerListPane.getSelectedValue() == null){
				SystemMessageDialog.showMessageDialog(null, 
						Language.getInstance().getString("error.validateSelectedComboBox") , 
						SystemMessageDialog.SHOW_OK_OPTION);
				return;
			}
				
			Customer customer = (Customer) customerListPane.getSelectedValue();
			int res = SystemMessageDialog.showConfirmDialog(null, 
					Language.getInstance().getString("confirm.delete") +customer.getName() , 
					SystemMessageDialog.SHOW_OK_CANCEL_OPTION);
			if(res != 0){
				return;
			}
			
			try {
				CustomerLogic.getInstance().deleteCustomer(customer);
				prepareData();
			} catch (Exception e2) {
				e2.printStackTrace();
			}			
		}
	};
	
	public Action ACT_REFRESH = new AbstractAction() {	
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			prepareData();
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
}
