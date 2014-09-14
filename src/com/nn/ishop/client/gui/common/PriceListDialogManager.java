package com.nn.ishop.client.gui.common;

import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.apache.log4j.Logger;

import com.nn.ishop.client.CActionEvent;
import com.nn.ishop.client.CActionListener;
import com.nn.ishop.client.gui.AbstractGUIManager;
import com.nn.ishop.client.gui.GUIManager;
import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.GUIActionEvent.GUIType;
import com.nn.ishop.client.gui.components.CComboBox;
import com.nn.ishop.client.gui.components.CPaging;
import com.nn.ishop.client.gui.components.CTextField;
import com.nn.ishop.client.gui.dialogs.GenericDialog;
import com.nn.ishop.client.gui.dialogs.SystemMessageDialog;
import com.nn.ishop.client.gui.helper.TableUtil;
import com.nn.ishop.client.logic.CommonLogic;
import com.nn.ishop.client.util.Util;
import com.nn.ishop.server.dto.exrate.Currency;
import com.nn.ishop.server.dto.pricelist.PriceList;
import com.nn.ishop.server.dto.pricelist.PriceListType;
import com.nn.ishop.server.dto.product.ProductCategory;
import com.nn.ishop.server.dto.product.ProductItem;
import com.nn.ishop.server.util.Formatter;

public class PriceListDialogManager extends AbstractGUIManager implements
CActionListener, TableModelListener, ListSelectionListener{	
	public static Logger logger = Logger.getLogger(PriceListDialogManager.class);
	
	
	void init() {
		initTemplate(this, "common/priceListDialogPage.xml");
		render();
		bindEventHandlers();
		
	}
	public PriceListDialogManager(String locale){
			setLocale(locale);
			init();
	}
	@Override
	protected void applyStyles() {				
	}

	@Override
	protected void bindEventHandlers() {
		plListPage.setTableGUIType(GUIType.PRICELIST);
		plListPage.addToolbarPlugin();
		plListPage.addCActionListener(this);
		reloadPriceListTable();
		initializeData();
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
		
	void postAction(){		
		
	}
	public void tableChanged(TableModelEvent e) {
	}
	public void valueChanged(ListSelectionEvent e) {
	}
	
	private void saveData(){
		if (selectedProduct == null
				|| comboPriceListCurency.getSelectedItemId().equals("-1"))
		{
			SystemMessageDialog.showMessageDialog(null, Language.getInstance().getString("null.value.for.major.field"),
					SystemMessageDialog.SHOW_OK_OPTION);
			return;
		}
		
		try {			
			PriceList pl = new PriceList(selectedProduct.getProductId(), 
					comboPriceListCurency.getSelectedItemId(), 
					comboPriceListType.getSelectedItemId(), 
					Formatter.str2num(
							(txtPriceListPurchasingPrice.getText() != null && !txtPriceListPurchasingPrice.getText().equals(""))?
									txtPriceListPurchasingPrice.getText():"0"
							).doubleValue(),
					Formatter.str2num(
							(txtPriceListSellingPrice.getText() != null && !txtPriceListSellingPrice.getText().equals(""))?
									txtPriceListSellingPrice.getText():"0"
							).doubleValue(),
					Formatter.str2num(
							(txtPriceListDiscount.getText() != null && !txtPriceListDiscount.getText().equals(""))?
									txtPriceListDiscount.getText():"0"
							).doubleValue());
			CommonLogic.insertPriceList(pl);
			reloadPriceListTable();			
			updatePriceList = null;
			isUpdatePriceList = false;
			
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}
	private void newData(){		
		selectedProduct = null;
		updatePriceList = null;	
		if(comboPriceListCurency.getModel().getSize() > 0)
			comboPriceListCurency.setSelectedIndex(0);
		comboPriceListType.setSelectedIndex(0);
		txtPriceListProduct.setText(null);
		txtPriceListSellingPrice.setText(null);
		txtPriceListDiscount.setText(null);
		updatePriceList = null;
		isUpdatePriceList = false;
	}
	public Action ACT_SAVE_EXRATE = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			saveData();
		}
	};
	public Action ACT_NEW_EXRATE = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			newData();
			updatePriceList = null;
		}
	};
	
	private void reloadPriceListTable(){
		try {
			List<PriceList> data = CommonLogic.loadPriceList();
			TableUtil.addListToTable(plListPage, plListPage.getTable(), data, Arrays.asList(new Integer(0)));
		} catch (Throwable e) {			
			logger.info(" updateCatGroupInfor: "+e.getMessage());			
		}
	}
	
	private void initializeData(){
		try {			
			List<Currency> listCurrency = CommonLogic.loadCurrency();
			List<PriceListType> listPlType = CommonLogic.loadPriceListType();
			CommonLogic.updateComboBox(listCurrency, comboPriceListCurency);
			CommonLogic.updateComboBox(listPlType, comboPriceListType);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		
	}
	
	public Action ACT_SAVE_PRICELIST = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			saveData();
		}
	};

	public Action ACT_NEW_PRICELIST = new AbstractAction() {
		private static final long serialVersionUID = 1L;
	
		public void actionPerformed(ActionEvent e) {
			newData();
		}
	};
	public Action ACT_OPEN_PRODUCT_DIALOG = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			ChooseProductDialogManager dlgGui = new ChooseProductDialogManager(Language.getInstance().getLocale());
			GenericDialog dlg = (GenericDialog)dlgGui.getRootComponent();		
			dlg.setIconImage(Util.getImage("logo32.png"));		
			dlg.centerDialogRelative(GUIManager.mainFrame, dlg);		
			//dlg.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dlg.pack();
			dlg.validate();
			dlg.setVisible(true);			
			selectedProduct = dlgGui.getChoosenProduct();			
			txtPriceListProduct.setText(selectedProduct != null? selectedProduct.getProductName():"");			
		}
	};
	
	
	CComboBox comboPriceListCurency;
	CComboBox comboPriceListType;
	
	CTextField txtPriceListProduct;
	CTextField txtPriceListSellingPrice;
	CTextField txtPriceListPurchasingPrice;
	CTextField txtPriceListDiscount;

		
	Object[][] priceListTabularData = null;
	CPaging plListPage;
	
	PriceList updatePriceList = null;
	ProductItem selectedProduct = null;
	Boolean isUpdatePriceList = false;


	private Vector<CActionListener> cActionListenerVector = new Vector<CActionListener>();
	public void addCActionListener(CActionListener al) {
		cActionListenerVector.add(al);
		
	}
	public void cActionPerformed(CActionEvent evt) {
		if(evt.getAction() == CActionEvent.LOAD_PRICELIST_BY_CAT)
		{
			try {
				// data is an instance of ProductCategory
				List<PriceList> lstPriceList = CommonLogic
						.loadPriceListByCat(((ProductCategory) evt.getData()).getCategoryId());
				TableUtil.addListToTable(plListPage, plListPage.getTable(), lstPriceList, Arrays.asList(new Integer(0)));
			} catch (Throwable e) {
				logger.error(e);
			}
		}else if (evt.getAction() == CActionEvent.SEARCH_PRICELIST){			
			//data is name or id as a String, use as PL Type, product id or currency
			String obj = String.valueOf(evt.getData());
			if(obj == null || obj.equals("")){
				reloadPriceListTable();
			}else{
				try {
					List<PriceList> lstPriceList = CommonLogic
							.searchPriceList(obj);
					TableUtil.addListToTable(plListPage, plListPage.getTable(),
							lstPriceList, Arrays.asList(new Integer(0)));
				} catch (Throwable e) {
					logger.error(e);
				}
			}
		}		
	}
	
	private void fireCAction(CActionEvent action){
		for(CActionListener al:cActionListenerVector)
			al.cActionPerformed(action);
	}
	
	public void removeCActionListener(CActionListener al) {
		cActionListenerVector.remove(al);
	}
}
