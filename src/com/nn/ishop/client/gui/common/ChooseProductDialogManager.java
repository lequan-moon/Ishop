package com.nn.ishop.client.gui.common;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.apache.log4j.Logger;

import com.nn.ishop.client.gui.AbstractGUIManager;
import com.nn.ishop.client.gui.GUIActionEvent;
import com.nn.ishop.client.gui.GUIActionListener;
import com.nn.ishop.client.gui.admin.product.ProductHelper;
import com.nn.ishop.client.gui.components.CPaging;
import com.nn.ishop.client.gui.components.CTextField;
import com.nn.ishop.client.gui.dialogs.GenericDialog;
import com.nn.ishop.client.gui.helper.TableUtil;
import com.nn.ishop.client.logic.admin.ProductLogic;
import com.nn.ishop.server.dto.product.ProductCategory;
import com.nn.ishop.server.dto.product.ProductItem;

public class ChooseProductDialogManager extends AbstractGUIManager implements
GUIActionListener, TableModelListener, ListSelectionListener{	
	public static Logger logger = Logger.getLogger(ChooseProductDialogManager.class);
	
	
	void init() {
		initTemplate(this, "common/chooseProductDialogPage.xml");
		render();
		bindEventHandlers();
		
	}
	public ChooseProductDialogManager(String locale){
			setLocale(locale);
			init();
	}
	@Override
	protected void applyStyles() {			
			
	}

	@Override
	protected void bindEventHandlers() {
		initializeData();
		pdListPage.getTable().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(pdListPage.getTable().getModel() == null 
						|| pdListPage.getTable().getModel().getRowCount()<=0)
					return;
				if(e.getClickCount() ==2){
					int row = pdListPage.getTable().getSelectedRow();
					
					String prodId = (String)pdListPage.getTable().getModel().getValueAt(
							pdListPage.getTable().convertRowIndexToModel(row), 1);
					selectedProduct = ProductLogic.getInstance().getItem(prodId);
					((GenericDialog)getRootComponent()).setVisible(false);
				}
				super.mouseReleased(e);
			}
		});
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
	void postAction(){		
		
	}
	public void tableChanged(TableModelEvent e) {
		System.out.println(e);
	}
	public void valueChanged(ListSelectionEvent e) {
	}
	
	private void initializeData(){
		
	}
	
	public Action ACT_OPEN_CATEGORY_DIALOG = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {			
			try {
				ProductCategory prodCat = ProductHelper.chooseCategory();
				List<ProductItem> products = ProductLogic.getInstance().loadItemByCat(prodCat.getCategoryId());
				TableUtil.addListToTable(pdListPage, pdListPage.getTable(), products, Arrays.asList(new Integer(0)));
			} catch (Throwable ex) {			
				logger.info(ex);			
			}
			
		}
	};
	CTextField txtChoosenProduct;
		
	Object[][] priceListTabularData = null;
	CPaging pdListPage;
	
	
	ProductItem selectedProduct = null;
	Boolean isUpdatePriceList = false;
	
	public ProductItem getChoosenProduct(){
		((GenericDialog)getRootComponent()).dispose();
		return selectedProduct;
		
	}
}
