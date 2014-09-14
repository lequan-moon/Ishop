package com.nn.ishop.client.gui.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import com.nn.ishop.client.CActionEvent;
import com.nn.ishop.client.CActionListener;
import com.nn.ishop.client.ProgressEvent;
import com.nn.ishop.client.ProgressListener;
import com.nn.ishop.client.gui.AbstractGUIManager;
import com.nn.ishop.client.gui.GUIActionEvent;
import com.nn.ishop.client.gui.GUIActionListener;
import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.admin.company.AdminCompanyGUIManger;
import com.nn.ishop.client.gui.admin.country.AdminCountryGUIManager;
import com.nn.ishop.client.gui.admin.customer.AdminCustomerGUIManager;
import com.nn.ishop.client.gui.admin.customer.CTabbedPane;
import com.nn.ishop.client.gui.admin.product.AdminProductGUIManager;
import com.nn.ishop.client.gui.admin.user.AdminUserGUIManager;
import com.nn.ishop.client.gui.admin.warehouse.AdminWarehouseGUIManager;
import com.nn.ishop.client.gui.components.CWhitePanel;
import com.nn.ishop.client.util.ItemWrapper;
import com.nn.ishop.client.util.Util;

/**
 * 
 */
public class AdminGUIManager extends AbstractGUIManager implements
		GUIActionListener,CActionListener, TableModelListener, ListSelectionListener, ProgressListener {
	JSplitPane mainSplit;
	private JPanel cTabbedPanePanel;

	public static final int OVERVIEW_TAB = 0;
	public static final int DATA_TAB = 1;
	public static final int TAG_TAB = 2;
//	private boolean addDataMode = false;
	

	CTabbedPane adminTabbedPane;

	/*
	 * GUIAction listener vector
	 */
	private Vector<GUIActionListener> guiListenerVector = new Vector<GUIActionListener>();
	
	private Vector<ProgressListener> progressListenerVector = new Vector<ProgressListener>();

	AdminProductGUIManager adminProductGUIManager;
	AdminUserGUIManager adminUserGUIManager;
	AdminCustomerGUIManager adminCustomerGUIManager; 
	AdminWarehouseGUIManager adminWarehouseGUIManager;	
	AdminCompanyGUIManger adminCompanyGUIManger;	
	AdminCountryGUIManager adminCountryGUIManager;
	
	CWhitePanel itemTopPanel;
	CWhitePanel userInformationPanel;
	CWhitePanel customerFormPanel;
	CWhitePanel warehouseFormPanel;

	/*CWhitePanel lotFormPanel;*/
	CWhitePanel userFormPanel;
	
	CWhitePanel companyFormPanel;
	
	CWhitePanel adminContentPanel;
	
	void init() {
		if (getLocale() != null && !getLocale().equals(""))
			initTemplate(this, "admin/adminGUIManager.xml", getLocale());
		else
			initTemplate(this, "admin/adminGUIManager.xml");

		render();
		adminProductGUIManager = new AdminProductGUIManager(getLocale());
		adminProductGUIManager.addProgressListener(this);
		adminProductGUIManager.addCActionListener(this);
		
		
		adminUserGUIManager = new AdminUserGUIManager(getLocale());
		adminUserGUIManager.addCActionListener(this);
		
		adminCustomerGUIManager = new AdminCustomerGUIManager(getLocale());		
		adminWarehouseGUIManager = new AdminWarehouseGUIManager(getLocale());
		adminCountryGUIManager = new AdminCountryGUIManager(getLocale());
		adminCompanyGUIManger = new AdminCompanyGUIManger(getLocale());
		if(itemTopPanel != null){
			itemTopPanel.add(adminProductGUIManager.getRootComponent(), BorderLayout.CENTER);
		}
		if(userInformationPanel != null){
			userInformationPanel.add(adminUserGUIManager.getRootComponent(), BorderLayout.CENTER);
		}
		if(customerFormPanel != null){
			customerFormPanel.add(adminCustomerGUIManager.getRootComponent(), BorderLayout.CENTER);
		}
		if(warehouseFormPanel != null){
			warehouseFormPanel.add(adminWarehouseGUIManager.getRootComponent(), BorderLayout.CENTER);
		}
		if(companyFormPanel != null){
			companyFormPanel.add(adminCompanyGUIManger.getRootComponent(), BorderLayout.CENTER);
		}
		
		/*lotFormPanel.add(adminLotGUIManager.getRootComponent(), BorderLayout.CENTER);*/
		bindEventHandlers();		
	}


	/**
	 * Constructor
	 * 
	 * @param graphData
	 */
	public AdminGUIManager(String locale) {
		setLocale(locale);
		init();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.shiftthink.connect.client.gui.AbstractGUIManager#getData(java.lang
	 * .String)
	 */
	public Object getData(String var) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.shiftthink.connect.client.gui.AbstractGUIManager#bindEventHandlers()
	 */
	protected void bindEventHandlers() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.shiftthink.connect.client.gui.AbstractGUIManager#setData(java.lang
	 * .String, java.lang.Object)
	 */
	public void setData(String var, Object data) {

	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.shiftthink.connect.client.gui.AbstractGUIManager#applyStyles()
	 */
	protected void applyStyles() {		
		Map<Object, String> tabTitleMap = new HashMap<Object, String>();		
		Map<Object, ImageIcon> tabIconMap = new HashMap<Object, ImageIcon>();
		List<Object> tabObjects = new ArrayList<Object>();
		
		if(companyFormPanel != null){
			tabObjects.add(companyFormPanel);
			tabTitleMap.put(companyFormPanel,Language.getInstance().getString("adminTabCompany") );
			tabIconMap.put(companyFormPanel, Util.getIcon("tabbed/tabbed-icon-company.png"));
		}
		if(warehouseFormPanel != null){
			tabObjects.add(warehouseFormPanel);
			tabTitleMap.put(warehouseFormPanel,Language.getInstance().getString("adminTabWarehouse") );
			tabIconMap.put(warehouseFormPanel, Util.getIcon("tabbed/tabbed-icon-warehouse.png"));
		}
		if(userInformationPanel != null){
			tabObjects.add(userInformationPanel);
			tabTitleMap.put(userInformationPanel,Language.getInstance().getString("adminTabUser") );
			tabIconMap.put(userInformationPanel, Util.getIcon("tabbed/tabbed-icon-user.png"));
		}
		if(customerFormPanel != null){
			tabObjects.add(customerFormPanel);
			tabTitleMap.put(customerFormPanel,Language.getInstance().getString("adminTabCustomer") );
			tabIconMap.put(customerFormPanel, Util.getIcon("tabbed/tabbed-icon-customer.png"));
		}
		if(itemTopPanel != null){
			tabObjects.add(itemTopPanel);
			tabTitleMap.put(itemTopPanel,Language.getInstance().getString("adminTabProduct"));
			tabIconMap.put(itemTopPanel, Util.getIcon("tabbed/tabbed-icon-item.png"));
		}
		
		cTabbedPanePanel.setBackground(Color.WHITE);
		
		for(int i=0;i<tabObjects.size();i++){
			Object obj = tabObjects.get(i);
			adminTabbedPane.setTitleAt(i,tabTitleMap.get(obj));
			adminTabbedPane.setIconAt(i,tabIconMap.get(obj));
		}
	}

	/*
	 * Realize method
	 * 
	 * @see
	 * com.shiftthink.connect.client.gui.AbstractGUIManager#guiActionPerformed
	 * (com.shiftthink.connect.client.gui.GUIActionEvent)
	 */
	public void guiActionPerformed(GUIActionEvent evt) {
//		Object data = evt.getData();
		if (evt.getAction() == GUIActionEvent.GUIActionType.SORT_ASD)
			builEntityList(ItemWrapper.nameComparatorAsd);
		if (evt.getAction() == GUIActionEvent.GUIActionType.SORT_DES)
			builEntityList(ItemWrapper.nameComparatorDes);
		if (evt.getAction() == GUIActionEvent.GUIActionType.NEW) {

			if (evt.getSource() instanceof JList) {
			}
		}		
		
		// **********************************************************************/
		// super.guiActionPerformed(evt); move to sub class
		if (evt.getAction() == GUIActionEvent.GUIActionType.RESTART_GUI) {
			// String componentId = evt.getData().toString();
			// TODO perform refresh panel code go here
		}
	}



	/**
	 * Build the list content from DirectedSparseGraph
	 * 
	 * @param comparator
	 */
	void builEntityList(Comparator<ItemWrapper> comparator) {
	}

	public void addGUIActionListener(GUIActionListener guiActionListioner) {
		guiListenerVector.add(guiActionListioner);
	}

	public void removeGUIActionListener(GUIActionListener guiActionListioner) {
		guiListenerVector.remove(guiActionListioner);
	}

	/*private void callGUIActionListener(GUIActionEvent action) {
		for (GUIActionListener guiActionListioner : guiListenerVector)
			guiActionListioner.guiActionPerformed(action);
	}*/
	
	public void addProgressListener(ProgressListener progressListener){
		progressListenerVector.add(progressListener);
	}
	public void removeProgressListener(ProgressListener progressListener){
		progressListenerVector.remove(progressListener);
	}
	protected void callProgressListener(ProgressEvent e){
		for(ProgressListener progressListener:progressListenerVector)
			progressListener.progressStatusChanged(e);
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @seejavax.swing.event.TableModelListener#tableChanged(javax.swing.event.
	 * TableModelEvent)
	 */
	public void tableChanged(TableModelEvent e) {		
	}
	
	@Override
	public void update() {
	}

	@Override
	public void updateList() {
	}

	/*
	 * Listening to CList selection action
	 * 
	 * @see
	 * javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event
	 * .ListSelectionEvent)
	 */
	public void valueChanged(ListSelectionEvent e) {
	}

	
	protected void checkPermission(){
	}

	public void progressStatusChanged(ProgressEvent evt) {	
		//-- Pass the event to upper level (GUIManager)
		callProgressListener(evt);		
	}

	Vector<CActionListener> vctActLsnr = new Vector<CActionListener>();
	public void addCActionListener(CActionListener al) {
		vctActLsnr.add(al);
		
	}


	public void cActionPerformed(CActionEvent action) {
		for(CActionListener al:vctActLsnr)
			al.cActionPerformed(action);		
	}

	
	public void removeCActionListener(CActionListener al) {
		vctActLsnr.remove(al);
		
	}
}
