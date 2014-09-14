package com.nn.ishop.client.gui.admin.product;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.Vector;

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
import com.nn.ishop.client.gui.GUIActionEvent.GUIType;
import com.nn.ishop.client.gui.GUIActionListener;
import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.components.CPaging;
import com.nn.ishop.client.gui.components.CTableModel;
import com.nn.ishop.client.gui.dialogs.SystemMessageDialog;
import com.nn.ishop.client.gui.helper.TableUtil;
import com.nn.ishop.client.logic.CommonLogic;
import com.nn.ishop.client.logic.admin.ProductCatLogic;
import com.nn.ishop.client.logic.admin.ProductLogic;
import com.nn.ishop.server.dto.product.ProductCategory;
import com.nn.ishop.server.dto.product.ProductItem;

public class ProductListManager extends AbstractGUIManager implements
		GUIActionListener, ListSelectionListener, TableModelListener, CActionListener {
	void init() {
		initTemplate(this, "admin/sanpham/productSearchPage.xml");
		render();
		bindEventHandlers();
		
	}
	public ProductListManager(String locale){
		setLocale(locale);
		init();
	}
	CPaging searchResultPaging;
	Object[][] pdData = null;
	
	void searchItem(String itemName){
		
		List<ProductItem> lst = ProductLogic.getInstance().searchItemByName(itemName);
		if(lst == null)
			return;
		try {
			updateItemList(lst);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(" searchItem error: "+e.getMessage());
		}
	}
	
	void updateItemList(List<ProductItem> lst) throws Exception{
		try {
			//if(searchResultPaging.getTable().getModel().getRowCount()<=0)
				//return;
			TableUtil.addListToTable(searchResultPaging, searchResultPaging.getTable(), lst, Arrays.asList(new Integer[]{0,2,3,4,5,6,7,8,9,10}));
		} catch (Throwable e) {			
			//logger.info(" updateCatGroupInfor: "+e.getMessage()); ignore			
		}
	}
	
	/**
	 * List all product - test purpose only
	 * @throws Exception 
	 */
	void updateItemList(int catId) throws Exception{		
		try {
			List<ProductItem> lst = ProductLogic.getInstance().loadItemByCat(catId);
			if(lst == null)
				return;
			updateItemList(lst);			
			
		} catch (Exception e) {
			throw e;
		}	
	}
	
	void updateItemList(ProductCategory pCat){
		try {
			updateItemList(pCat.getCategoryId());
			searchResultPaging.updateSelectedCat(pCat);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void applyStyles() {
		
	}

	@Override
	protected void bindEventHandlers() {
		searchResultPaging.setTableGUIType(GUIType.PRODUCT);		
		
		searchResultPaging.addCActionListener(this);
		searchResultPaging.addGUIActionListener(this);
		
		//-- add more feature to tool bar paging
		searchResultPaging.addToolbarPlugin();
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
		if(action.getGuiType() == GUIType.PRODUCT){
			if(action.getAction() == GUIActionType.SAVE){
				Object data = action.getData();
				/*
				 * Get Object[] from table model data based on row number changed, then convert to ProductItem object 
				 * 
				 * */
				if(data != null && data instanceof Hashtable){
					Hashtable<Integer, Hashtable<Integer,String>> rowsChange = (Hashtable<Integer, Hashtable<Integer,String>>) action.getData();
					Set<Integer> rowsChangeSet =  rowsChange.keySet();
					if(searchResultPaging.getTable().getModel() instanceof CTableModel){
						CTableModel model = (CTableModel)searchResultPaging.getModel();
						Object[][] dataArr= model.getData();
						for(Integer i: rowsChangeSet){
							Object[] obj = dataArr[i];							
							ProductItem item = null;
							try {
								item = ProductItem.fromObjectArray(obj);
							} catch (Throwable e) {
								SystemMessageDialog.showMessageDialog(null, 
										Language.getInstance().getString("error"), 
										SystemMessageDialog.SHOW_OK_OPTION);								
							}
							// If not null then perform update.
							if(item != null){
								ProductLogic logic = ProductLogic.getInstance();
								logic.saveProduct(item, CAction.UPDATE);
								//notify for product update
							}
						}
					}
					
				}				
			}
		}
				
		

	}

	public void valueChanged(ListSelectionEvent e) {

	}

	public void tableChanged(TableModelEvent e) {

	}
	@Override
	protected void checkPermission() {
		
	}
	
	public void addCActionListener(CActionListener al) {
		cActionListenerVector.add(al);
	}
	
	
	public void cActionPerformed(CActionEvent evt) {		
		if(evt.getAction() == CActionEvent.PRODUCT_CHANGE)
		{
			//-- Add new product event from product detail manager
			ProductItem pi = (ProductItem)evt.getData();
			try {
				updateItemList(pi.getItemCategory());
				searchResultPaging.updateSelectedCat(
						ProductCatLogic.getInstance().getCatById(pi.getItemCategory()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(evt.getAction() == CActionEvent.LOAD_PRODUCT_BY_CAT_TREE){
			ProductCategory pCat = (ProductCategory)evt.getData();
			updateItemList(pCat);			
		}else if(evt.getAction() == CActionEvent.SEARCH_PRODUCT_LIST){
			String nameOrId = (String)evt.getData();
			searchItem(nameOrId);
		}else if(evt.getAction() == CActionEvent.DELETE_PRODUCT){
			String prodId = (String)evt.getData();
			ProductItem pd = ProductLogic.getInstance().getItem(prodId);
			CommonLogic.setExtraEntityInfor(pd, CAction.DELETE);		
			ProductLogic.getInstance().saveProduct(pd, CAction.DELETE);
			
			//-- Fire update event - list product by cat 
			try {
				ProductCategory pCat = ProductCatLogic.getInstance()
						.getCatById(pd.getItemCategory());
				updateItemList(pCat.getCategoryId());				
			} catch (Exception e) {
				e.printStackTrace();
			}	
			fireCAction(evt);
		}else{
			fireCAction(evt);
		}
	}
	private Vector<CActionListener> cActionListenerVector = new Vector<CActionListener>();
	
	private void fireCAction(CActionEvent action){
		for(CActionListener al:cActionListenerVector)
			al.cActionPerformed(action);
	}
	public void removeCActionListener(CActionListener al) {
		cActionListenerVector.remove(al);
	}

}
