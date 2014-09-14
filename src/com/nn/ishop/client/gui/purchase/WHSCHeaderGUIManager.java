package com.nn.ishop.client.gui.purchase;

import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;

import com.nn.ishop.client.CActionEvent;
import com.nn.ishop.client.CActionListener;
import com.nn.ishop.client.gui.AbstractGUIManager;
import com.nn.ishop.client.gui.components.CComboBox;
import com.nn.ishop.client.gui.components.CTextField;
import com.nn.ishop.client.gui.purchase.WHSCItemListGUIManager.WHSCDetailWrapper;
import com.nn.ishop.client.logic.admin.ProductLogic;
import com.nn.ishop.client.logic.admin.WarehouseLogic;
import com.nn.ishop.client.util.ItemWrapper;
import com.nn.ishop.client.validator.Validator;
import com.nn.ishop.server.dto.product.ProductItem;
import com.nn.ishop.server.dto.warehouse.Lot;
import com.nn.ishop.server.dto.warehouse.Warehouse;
import com.nn.ishop.server.dto.warehouse.WarehouseStockcardDetail;

public class WHSCHeaderGUIManager extends AbstractGUIManager implements CActionListener, ItemListener, FocusListener {

//	CComboBox cbbWarehouseName;
//	CComboBox cbbLotName;
//	CTextField txtNote;
//	CTextField txtItem_id;
//	CTextField txtItem_name;
//	CTextField txtQuantity;
	Vector<CActionListener> lstListener = new Vector<CActionListener>();
	ProductLogic productLogicInst = ProductLogic.getInstance();
//	ProductItem currentItem;

	public WHSCHeaderGUIManager(String locale) {
		setLocale(locale);
		init();
	}

	void init() {
		if (getLocale() != null && !getLocale().equals("")) {
			initTemplate(this, "purchase/po/whscHeaderPage.xml", getLocale());
		} else {
			initTemplate(this, "purchase/po/whscHeaderPage.xml");
		}
		render();
		prepareData();
		bindEventHandlers();
	}

	private void prepareData() {
		try {
			List<Warehouse> lstWarehouse = WarehouseLogic.loadWarehouse();
			ItemWrapper[] lstItem = new ItemWrapper[lstWarehouse.size() + 1];
			lstItem[0] = new ItemWrapper("-1", "-Choose a warehouse-");
			int i = 1;
			for (Warehouse warehouse : lstWarehouse) {
				ItemWrapper item = new ItemWrapper(warehouse.getId(), warehouse.getName());
				lstItem[i] = item;
				i++;
			}
//			cbbWarehouseName.setItems(lstItem);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void loadLot(String warehouseId) {
		try {
			List<Lot> lstLot = WarehouseLogic.loadLotByWarehouse(warehouseId);
			ItemWrapper[] lstItem = new ItemWrapper[lstLot.size()];
			int i = 0;
			for (Lot lot : lstLot) {
				ItemWrapper item = new ItemWrapper(lot.getId(), lot.getNote());
				lstItem[i] = item;
				i++;
			}
//			cbbLotName.setItems(lstItem);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected void bindEventHandlers() {
//		cbbWarehouseName.addItemListener(this);
//		txtItem_id.addFocusListener(this);
	}

//	private void resetData(){
//		txtItem_id.setText("");
//		txtItem_name.setText("");
//		txtNote.setText("");
//		txtQuantity.setText("");
//		currentItem = null;
//		cbbWarehouseName.setSelectedItemById("-1");
//		cbbLotName.setItems(new ItemWrapper[]{});
//	}
	
//	public Action cmdAdd = new AbstractAction() {
//
//		@Override
//		public void actionPerformed(ActionEvent arg0) {
//			if (currentItem != null 
//					&& Validator.validateEmpty(txtQuantity)
//					&& Validator.validateNumber(txtQuantity, Long.class)) {
//				WarehouseStockcardDetail warehouseStockcardDetail = new WarehouseStockcardDetail();
//				warehouseStockcardDetail.setLot_id(cbbLotName.getSelectedItemId());
//				warehouseStockcardDetail.setItem_id(currentItem.getProductId());
//				if(!"".equals(txtNote.getText())){
//					warehouseStockcardDetail.setNote(txtNote.getText());
//				}else{
//					warehouseStockcardDetail.setNote("");
//				}
//				
//				warehouseStockcardDetail.setQuantity(Long.valueOf(txtQuantity.getText()));
//				
//				WHSCDetailWrapper whscDetailWrapper = new WHSCDetailWrapper();
//				whscDetailWrapper.setLotName(((ItemWrapper)cbbLotName.getSelectedItem()).toString());
//				whscDetailWrapper.setWarehouseName(((ItemWrapper)cbbWarehouseName.getSelectedItem()).toString());
//				whscDetailWrapper.setWarehouseStockcardDetail(warehouseStockcardDetail);
//				whscDetailWrapper.setItem(currentItem);
//				
//				CActionEvent action = new CActionEvent(this, CActionEvent.ADD, whscDetailWrapper);
//				fireCActionEvent(action);
//				
//				resetData();
//			}
//		}
//	};

	private void fireCActionEvent(CActionEvent action){
		for (CActionListener listener : lstListener) {
			listener.cActionPerformed(action);
		}
	}
	
	@Override
	public Object getData(String var) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void applyStyles() {
		// TODO Auto-generated method stub

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
	public void addCActionListener(CActionListener al) {
		lstListener.add(al);
	}
	public void removeCActionListener(CActionListener al) {
		// TODO Auto-generated method stub

	}
	public void cActionPerformed(CActionEvent action) {
		if(action.getAction() == CActionEvent.CANCEL){
//			resetData();
		} else if(action.getAction() == CActionEvent.SAVE){
			fireCActionEvent(action);
		} else {
			fireCActionEvent(action);
		}

	}
	public void itemStateChanged(ItemEvent arg0) {
		if (arg0.getStateChange() == ItemEvent.SELECTED) {
			String warehouseId = ((ItemWrapper) arg0.getItem()).getId();
			if (!"-1".equals(warehouseId)) {
				loadLot(warehouseId);
			}
		}

	}
	public void focusGained(FocusEvent arg0) {
		// TODO Auto-generated method stub

	}
	public void focusLost(FocusEvent arg0) {
//		if (!"".equals(txtItem_id.getText()) && arg0.getSource() == txtItem_id) {
//			currentItem = productLogicInst.getItem(txtItem_id.getText());
//			if (currentItem != null) {
//				txtItem_name.setText(currentItem.getProductName());
//			}
//
//		}
	}

}
