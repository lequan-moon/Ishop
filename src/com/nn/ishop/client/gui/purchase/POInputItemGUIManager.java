package com.nn.ishop.client.gui.purchase;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import com.nn.ishop.client.CActionEvent;
import com.nn.ishop.client.CActionListener;
import com.nn.ishop.client.gui.AbstractGUIManager;
import com.nn.ishop.client.gui.GUIActionEvent;
import com.nn.ishop.client.gui.GUIActionListener;
import com.nn.ishop.client.gui.GUIManager;
import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.common.ChooseProductDialogManager;
import com.nn.ishop.client.gui.components.CLabel;
import com.nn.ishop.client.gui.components.CPaging;
import com.nn.ishop.client.gui.components.CTable;
import com.nn.ishop.client.gui.components.CTableModel;
import com.nn.ishop.client.gui.components.CTextField;
import com.nn.ishop.client.gui.components.CWhitePanel;
import com.nn.ishop.client.gui.dialogs.GenericDialog;
import com.nn.ishop.client.gui.dialogs.SystemMessageDialog;
import com.nn.ishop.client.gui.helper.TableUtil;
import com.nn.ishop.client.logic.admin.ProductLogic;
import com.nn.ishop.client.logic.admin.PurchaseLogic;
import com.nn.ishop.client.util.SimpleToolExcel;
import com.nn.ishop.client.util.Util;
import com.nn.ishop.client.validator.Validator;
import com.nn.ishop.server.dto.AbstractIshopEntity;
import com.nn.ishop.server.dto.product.ProductItem;
import com.nn.ishop.server.dto.product.ProductUOM;
import com.nn.ishop.server.dto.purchase.PurchasingPlan;
import com.nn.ishop.server.dto.purchase.PurchasingPlanDetail;
import com.nn.ishop.server.dto.warehouse.ImportInvoke;
import com.nn.ishop.server.util.Formatter;

public class POInputItemGUIManager extends AbstractGUIManager implements CActionListener, GUIActionListener, ListSelectionListener,
		TableModelListener, KeyListener, FocusListener {
	CTextField txtItem_id;
	CTextField txtItem_name;
	CTextField txtQuantity;
	CLabel lblUnit;
	CWhitePanel purchasePOItemListContainer;
	Vector<CActionListener> lstActionListener = new Vector<CActionListener>();
	ProductItem currentItem;
	ProductLogic instance = ProductLogic.getInstance();
	CPaging poDetailList;
	PurchaseLogic poLogicInstance = PurchaseLogic.getInstance();
	boolean isDataChanged = false;

	public POInputItemGUIManager(String locale) {
		setLocale(locale);
		init();
	}

	void init() {
		if (getLocale() != null && !getLocale().equals("")) {
			initTemplate(this, "purchase/po/poDetailPage.xml", getLocale());
		} else {
			initTemplate(this, "purchase/po/poDetailPage.xml");
		}
		render();
		bindEventHandlers();
	}

	private ProductItem getItem(String item_id) {
		return instance.getItem(item_id);
	}

	private void resetControlData() {		
		this.txtItem_id.setText("");
		this.txtItem_name.setText("");
		this.txtQuantity.setText("");
		this.lblUnit.setText("");
	}
	private void resetData() {
		try {
			poDetailList.clearData();
			((CTable)poDetailList.getTable()).setLstData(new ArrayList<InputItem>());
			isDataChanged = false;
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	public void fireCActionEvent(CActionEvent event) {
		for (CActionListener listener : lstActionListener) {
			listener.cActionPerformed(event);
		}
	}

	@Override
	protected void applyStyles() {
	}

	@Override
	protected void bindEventHandlers() {
		txtItem_id.addKeyListener(this);
		txtItem_id.addFocusListener(this);
		txtQuantity.addFocusListener(this);
		txtQuantity.addKeyListener(this);
		poDetailList.addCActionListener(this);		
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
		if (action.getAction() == CActionEvent.CANCEL) {
			resetData();
			resetControlData();
		}
		if(action.getAction() == CActionEvent.PAGING_DELETE_EVENT) {
			// remove selected row in Table
			try {
				int totalRow = poDetailList.getTable().getModel().getRowCount();			
				List<Object[]> retData = new ArrayList<Object[]>();
				for(int i=0;i< totalRow;i++){
					boolean chk =( (Boolean)poDetailList.getModel().getValueAt(poDetailList.getTable().convertRowIndexToModel(i), 0)).booleanValue();
					if(!chk)
						retData.add(poDetailList.getModel().getData()[i]);
				}				
				if(retData.size() ==0) return;
				Object[][] items = new Object[retData.size()][];
				//String[] colNames = null;
				for (int i=0;i<retData.size();i++){
					items[i] = retData.get(i);				
				}
				
				if (items != null && items.length > 0)
					TableUtil.formatTable(poDetailList,
							poDetailList.getTable(), items, poDetailList.getModel().getColumnNames(), Arrays.asList(new Integer[]{0,11}));
					isDataChanged = true;
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		if (action.getAction() == CActionEvent.SAVE) {
			if (action.getData() instanceof PurchasingPlan) {
				List<InputItem> lst = (List<InputItem>)((CTable) poDetailList.getTable()).getLstData();
				if (lst != null) {
					if (lst.size() != 0) {
						PurchasingPlan pp = (PurchasingPlan) action.getData();
						for (InputItem inputItem : lst) {
							PurchasingPlanDetail pp_detail = new PurchasingPlanDetail();
							pp_detail.setPpId(pp.getPpId());
							pp_detail.setItem_id(inputItem.getItem().getProductId());
							pp_detail.setQuantity(Long.valueOf(inputItem.getQuantity()));
							poLogicInstance.insertPurchasingPlanDetail(pp_detail);
						}
						resetData();
					}
				}
			}
			resetControlData();
		} else if (action.getAction() == CActionEvent.EXPORT_DATA) {
			List<InputItem> listData = (List<InputItem>)((CTable) poDetailList.getTable()).getLstData();
			if (listData != null && listData.size() > 0) {
				SimpleToolExcel<InputItem> tool = new SimpleToolExcel<POInputItemGUIManager.InputItem>();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				String sequence = formatter.format(new Date());
				tool.exportData(listData, listData.get(0).getObjectHeader(), "Purchasing plan data list", "PURCHASE_PLAN", "PurchasingPlan_" + sequence);
//				exportData(listData, "PURCHASE_PLAN");
			}
		} else if (action.getAction() == CActionEvent.IMPORT_DATA) {
			SimpleToolExcel<InputItem> tool = new SimpleToolExcel<POInputItemGUIManager.InputItem>();
			List<InputItem> listData = tool.importData(new InputItem(), "PURCHASE_PLAN", InputItem.class);
			try {				
					TableUtil.addListToTable(poDetailList, poDetailList.getTable(), listData, Arrays.asList(new Integer[]{0,11}));
			} catch (Throwable e) {
				e.printStackTrace();
			}
		} 	
	}
	void addItemToList(Object data){		
		String tmpDataProductId = ((InputItem) data).getItem().getProductId();
		List<InputItem> lst = (List<InputItem>) ((CTable)poDetailList.getTable()).getLstData();
		if (data instanceof InputItem) {
			if (lst == null) {
				lst = new ArrayList<InputItem>();
			}

			// check lst, if already has that item then merge it
			boolean isContain = false;
			for (InputItem inputItem : lst) {
				String tmpInputItemId = inputItem.getItem().getProductId();
				if (tmpDataProductId.equals(tmpInputItemId)) {
					Long total = Long.valueOf(inputItem.getQuantity()) + Long.valueOf(((InputItem) data).getQuantity());
					inputItem.setQuantity(String.valueOf(total));
					isContain = isContain || true;
				}
			}
			if (!isContain) {
				lst.add((InputItem) data);
			}
			try {				
					TableUtil.addListToTable(poDetailList, poDetailList.getTable(), lst, Arrays.asList(new Integer[]{0,11}));
				isDataChanged = true;
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}		
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
		lstActionListener.add(al);
	}

	public void removeCActionListener(CActionListener al) {
	}

	public void keyPressed(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
		if (e.getSource() == txtItem_id && e.getKeyCode() == KeyEvent.VK_ENTER) {
			currentItem = getItem(txtItem_id.getText());
			if(currentItem == null){
				SystemMessageDialog.showMessageDialog(null, Language.getInstance().getString("ERROR.NO.ITEM.FOUND"), 0);
				lblUnit.setText("");
			}else{
				txtItem_name.setText(currentItem.getProductName());
				ProductUOM productUOM = instance.getUOM(currentItem.getItemUOM());
				lblUnit.setText(productUOM.getUomName());
				txtQuantity.requestFocus();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER && e.getSource() == txtQuantity) {
			txtItem_id.requestFocus();
		}
	}

	public void keyTyped(KeyEvent e) {
	}

	public void focusGained(FocusEvent arg0) {
	}

	public void focusLost(FocusEvent arg0) {
		if (arg0.getSource() == txtQuantity && !"".equals(txtItem_id.getText())) {
			if (Validator.validateEmpty(txtQuantity, Color.WHITE, Language.getInstance().get("Quantity")) 
					&& Validator.validateNumber(txtQuantity, Long.class, Color.WHITE, Language.getInstance().get("Quantity")) ) {
				if (currentItem != null) {
					addItemToList( new InputItem(currentItem, txtQuantity.getText()));
					/*CActionEvent event = new CActionEvent(this, CActionEvent.LOST_FOCUS, new InputItem(currentItem, txtQuantity.getText()));
					fireCActionEvent(event);*/
					resetControlData();
					currentItem = null;
				}
			}
		}else if (arg0.getSource() == txtItem_id) {
			currentItem = getItem(txtItem_id.getText());
			if (currentItem != null) {
				txtItem_name.setText(currentItem.getProductName());
				ProductUOM productUOM = instance.getUOM(currentItem.getItemUOM());
				lblUnit.setText(productUOM.getUomName());
				txtQuantity.requestFocus();
			}
		}
	}

	public static class InputItem extends AbstractIshopEntity {
		ProductItem item;
		String quantity;
		
		public InputItem(){
			super();
			item = new ProductItem();
		}

		public InputItem(ProductItem item, String quantity) {
			super();
			this.item = item;
			this.quantity = quantity;
		}

		public ProductItem getItem() {
			return item;
		}

		public void setItem(ProductItem item) {
			this.item = item;
		}

		public String getQuantity() {
			return quantity;
		}

		@ImportInvoke(type = "String", mapping = "Quantity")
		public void setQuantity(String quantity) {
			this.quantity = quantity;
		}

		@Override
		public String[] getObjectHeader() {
			return new String[] { "", "ma.hang", "ten.mat.hang", "quy.cach.san.pham", "nuoc.san.xuat", "cong.ty.san.xuat", "ngay.san.xuat",
					"ngay.het.han", "don.vi.tinh", "danh.muc.hang", "item.ghi.chu", "Quantity" };
		}

		@Override
		public Object[] toObjectArray() {
			return new Object[] { Boolean.FALSE, item.getProductId(), item.getProductName(), item.getItemPackingType(),
					item.getProductMadein(), item.getMade_by(), Formatter.date2str(item.getProductProducedDate().getTime()),
					Formatter.date2str(item.getProductExpiredDate().getTime()), item.getItemUnitPrice(),
					item.getItemCategory(), item.getProductDescription(), getQuantity() };
		}
		
		@ImportInvoke(type = "String", mapping = "item.ghi.chu")
		public void setProductDescription(String description){
			item.setProductDescription(description);
		}
		
		@ImportInvoke(type = "int", mapping = "danh.muc.hang")
		public void setItemCategory(int category){
			item.setItemCategory(category);
		}
		
		@ImportInvoke(type = "int", mapping = "don.vi.tinh")
		public void setItemUnitPrice(int itemUnitPrice){
			item.setItemUnitPrice(itemUnitPrice);
		}
		
		@ImportInvoke(type = "String", mapping = "ngay.het.han")
		public void setProductExpiredDate(String expiredDateStr){
			try {
				Date expiredDate = Formatter.str2date(expiredDateStr);
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(expiredDate);
				item.setProductExpiredDate(calendar);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		@ImportInvoke(type = "String", mapping = "ngay.san.xuat")
		public void setProductProducedDate(String producedDateStr){
			try {
				Date producedDate = Formatter.str2date(producedDateStr);
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(producedDate);
				item.setProductProducedDate(calendar);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		@ImportInvoke(type = "String", mapping = "cong.ty.san.xuat")
		public void setMadeBy(String madeBy){
			item.setMade_by(madeBy);
		}
		
		@ImportInvoke(type = "String", mapping = "nuoc.san.xuat")
		public void setProductMadeIn(String productMadeIn){
			item.setProductMadein(productMadeIn);
		}
		
		@ImportInvoke(type = "int", mapping = "quy.cach.san.pham")
		public void setItemPackingType(int itemPackingType){
			item.setItemPackingType(itemPackingType);
		}
		
		@ImportInvoke(type = "String", mapping = "ten.mat.hang")
		public void setProductName(String productName){
			item.setProductName(productName);
		}
		
		@ImportInvoke(type = "String", mapping = "ma.hang")
		public void setProductId(String productId){
			item.setProductId(productId);
		}

	}

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
			currentItem = dlgGui.getChoosenProduct();			
			txtItem_id.setText(currentItem.getProductId());
			txtItem_name.setText(currentItem.getProductName());
			txtQuantity.requestFocus();
			//txtItem_name.setText(selectedProduct.getProductName());
		}
	};
}
