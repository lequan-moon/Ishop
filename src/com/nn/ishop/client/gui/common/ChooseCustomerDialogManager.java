package com.nn.ishop.client.gui.common;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;

import com.nn.ishop.client.gui.AbstractGUIManager;
import com.nn.ishop.client.gui.GUIActionEvent;
import com.nn.ishop.client.gui.GUIActionListener;
import com.nn.ishop.client.gui.components.CPaging;
import com.nn.ishop.client.gui.dialogs.GenericDialog;
import com.nn.ishop.client.gui.helper.TableUtil;
import com.nn.ishop.client.logic.admin.CustomerLogic;
import com.nn.ishop.client.logic.admin.ProductLogic;
import com.nn.ishop.server.dto.customer.Customer;
import com.nn.ishop.server.dto.product.ProductItem;

public class ChooseCustomerDialogManager extends AbstractGUIManager implements
		GUIActionListener {

	void init() {
		initTemplate(this, "common/chooseCustomerDialogPage.xml");
		render();
		bindEventHandlers();
		
	}
	public ChooseCustomerDialogManager(String locale){
			setLocale(locale);
			init();
	}
	@Override
	protected void applyStyles() {

	}

	@Override
	protected void bindEventHandlers() {	
		loadData();
		custListPage.getTable().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				try {
					if (custListPage.getTable().getModel() == null
							|| custListPage.getTable().getModel().getRowCount() <= 0)
						return;
					if (e.getClickCount() == 2) {
						int row = custListPage.getTable().getSelectedRow();

						Integer custId = (Integer) custListPage.getTable()
								.getModel().getValueAt(
										custListPage.getTable().convertRowIndexToModel(row), 1);
						selectedCustomer = CustomerLogic.getInstance()
								.getCustomer(custId);
						((GenericDialog) getRootComponent()).setVisible(false);
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				super.mouseReleased(e);
			}
		});
	}
	private void loadData(){
		try {
			List<Customer> cusList = CustomerLogic.getInstance().loadCustomer();
			TableUtil.addListToTable(custListPage, custListPage.getTable(), cusList, Arrays.asList(new Integer(0)));
		} catch (Throwable ex) {			
			logger.info(ex);			
		}
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

	}
	CPaging custListPage;
	Customer selectedCustomer = null;
	Boolean isUpdate = false;
	public Customer getChoosenCustomer(){
		((GenericDialog)getRootComponent()).dispose();
		return selectedCustomer;
		
	}

}
