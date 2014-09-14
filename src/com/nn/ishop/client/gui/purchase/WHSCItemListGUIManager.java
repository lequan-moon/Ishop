package com.nn.ishop.client.gui.purchase;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultCellEditor;

import com.nn.ishop.client.CActionEvent;
import com.nn.ishop.client.CActionListener;
import com.nn.ishop.client.gui.AbstractGUIManager;
import com.nn.ishop.client.gui.components.CComboBox;
import com.nn.ishop.client.gui.components.CPaging;
import com.nn.ishop.client.gui.components.CTable;
import com.nn.ishop.client.gui.components.CTableModel;
import com.nn.ishop.client.gui.helper.TableUtil;
import com.nn.ishop.client.gui.purchase.POInputItemGUIManager.InputItem;
import com.nn.ishop.client.logic.CommonLogic;
import com.nn.ishop.client.logic.admin.ProductLogic;
import com.nn.ishop.client.logic.admin.WarehouseLogic;
import com.nn.ishop.client.logic.admin.WarehouseStockcardLogic;
import com.nn.ishop.client.util.ItemWrapper;
import com.nn.ishop.client.util.SimpleToolExcel;
import com.nn.ishop.server.dto.AbstractIshopEntity;
import com.nn.ishop.server.dto.grn.PurchaseGrnDetail;
import com.nn.ishop.server.dto.product.ProductItem;
import com.nn.ishop.server.dto.warehouse.Lot;
import com.nn.ishop.server.dto.warehouse.Warehouse;
import com.nn.ishop.server.dto.warehouse.WarehouseStockcardDetail;

public class WHSCItemListGUIManager extends AbstractGUIManager implements CActionListener, ItemListener {
	CPaging whscItemList;
	WarehouseStockcardLogic warehouseStockcardLogic = WarehouseStockcardLogic.getInstance();
	ProductLogic productLogic = ProductLogic.getInstance();
	CComboBox cbbwarehouse;
	String currentWarehouse;

	public WHSCItemListGUIManager(String locale) {
		setLocale(locale);
		init();
	}

	void init() {
		if (getLocale() != null && !getLocale().equals("")) {
			initTemplate(this, "purchase/po/whscItemList.xml", getLocale());
		} else {
			initTemplate(this, "purchase/po/whscItemList.xml");
		}
		render();
		try {
			List<Warehouse> lstWarehouse = WarehouseLogic.loadWarehouse();
			CommonLogic.updateComboBox(lstWarehouse, cbbwarehouse);
			cbbwarehouse.addItemListener(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bindEventHandlers();
	}

	@Override
	protected void bindEventHandlers() {
		whscItemList.addCActionListener(this);
	}

	private void resetData() {
		try {
			whscItemList.clearData();
			((CTable)whscItemList.getTable()).setLstData(new ArrayList<InputItem>());
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		// TODO Auto-generated method stub

	}
	public void removeCActionListener(CActionListener al) {
		// TODO Auto-generated method stub

	}
	public void cActionPerformed(CActionEvent action) {
		if (action.getAction() == CActionEvent.ADD) {
			List<WHSCDetailWrapper> lstData =
					(List<WHSCDetailWrapper>) 
					((CTable)whscItemList.getTable()).getLstData();
			if (lstData == null) {
				lstData = new ArrayList<WHSCDetailWrapper>();
			}
			WHSCDetailWrapper newitem = (WHSCDetailWrapper) action.getData();
			lstData.add(newitem);
			try {
				TableUtil.addListToTable(whscItemList, whscItemList.getTable(), lstData);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (action.getAction() == CActionEvent.SAVE) {
			// @SuppressWarnings("unchecked")
			// List<WHSCDetailWrapper> lstData = (List<WHSCDetailWrapper>)
			// whscItemList.getTable().getLstData();
			// if (lstData != null || lstData.size() > 0) {
			// for (WHSCDetailWrapper whscDetailWrapper : lstData) {
			// whscDetailWrapper.getWarehouseStockcardDetail().setLot_id(cbbLot.getSelectedItemId());
			// warehouseStockcardLogic.insertWarehouseStockcardDetail(whscDetailWrapper.getWarehouseStockcardDetail());
			// }
			// resetData();
			// }
			List<WarehouseStockcardDetail> lstData = new ArrayList<WarehouseStockcardDetail>();
			CTable table = whscItemList.getTable();
			for (int i = 0; i < table.getRowCount(); i++) {
				WarehouseStockcardDetail whsc = new WarehouseStockcardDetail();
				String lotId = (String) table.getValueAt(
						table.convertRowIndexToModel(i), 1);
				whsc.setLot_id(lotId);
				String item_id = (String) table.getValueAt(
						table.convertRowIndexToModel(i), 2);
				whsc.setItem_id(item_id);
				Long quantity = Long.valueOf(table.getValueAt(
						table.convertRowIndexToModel(i), 4).toString());
				whsc.setQuantity(quantity);
				String note = (String) table.getValueAt(
						table.convertRowIndexToModel(i), 5);
				whsc.setNote(note);
				lstData.add(whsc);
			}

			for (WarehouseStockcardDetail warehouseStockcardDetail : lstData) {
				warehouseStockcardLogic.insertWarehouseStockcardDetail(warehouseStockcardDetail);
			}
			resetData();
		} else if (action.getAction() == CActionEvent.LIST_SELECT_ITEM) {
			if (action.getData() instanceof List) {
				try {

					List<WHSCDetailWrapper> lstData = new ArrayList<WHSCDetailWrapper>();
					List<PurchaseGrnDetail> data = (List<PurchaseGrnDetail>) action.getData();
					for (PurchaseGrnDetail purchaseGrnDetail : data) {
						WHSCDetailWrapper newItem = new WHSCDetailWrapper();
						ProductItem productItem = productLogic.getItem(purchaseGrnDetail.getItem_id());
						newItem.setItem(productItem);

						WarehouseStockcardDetail whscDetail = new WarehouseStockcardDetail();
						whscDetail.setItem_id(productItem.getProductId());
						whscDetail.setQuantity(purchaseGrnDetail.getReceive_quantity() - purchaseGrnDetail.getCorrupted_quantity());

						newItem.setWarehouseStockcardDetail(whscDetail);
						lstData.add(newItem);
						// WarehouseStockcardDetail warehouseStockcardDetail =
						// warehouseStockcardLogic.getWarehouseStockcardDetail(1);
					}
					TableUtil.addListToTable(whscItemList, whscItemList.getTable(), lstData, Arrays.asList(1, 5));

					currentWarehouse = cbbwarehouse.getSelectedItemId();

					List<Lot> lstLot = WarehouseLogic.loadLotByWarehouse(currentWarehouse);

					// List<Warehouse> lstWareHouse =
//					WarehouseLogic.loadWarehouse();
					final CComboBox cbbLot = new CComboBox();
					CommonLogic.updateComboBox(lstLot, cbbLot);
					DefaultCellEditor lotCellEditor = new DefaultCellEditor(cbbLot) {
						@Override
						public Object getCellEditorValue() {
							return cbbLot.getSelectedItemId();
						}
					};
					whscItemList.getTable().getColumnModel().getColumn(1).setCellEditor(lotCellEditor);

				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else if (action.getAction() == CActionEvent.CANCEL) {
			resetData();
		}
		if(action.getAction() == CActionEvent.EXPORT_DATA){
			List<WarehouseStockcardDetail> lstData = new ArrayList<WarehouseStockcardDetail>();
			CTable table = whscItemList.getTable();
			for (int i = 0; i < table.getRowCount(); i++) {
				WarehouseStockcardDetail whsc = new WarehouseStockcardDetail();
				String lotId = (String) table.getValueAt(table.convertRowIndexToModel(i), 1);
				if(lotId == null || "".equals(lotId)){
					lotId = "1";
				}
				whsc.setLot_id(lotId);
				String item_id = (String) table.getValueAt(table.convertRowIndexToModel(i), 2);
				whsc.setItem_id(item_id);
				String item_name = (String)table.getValueAt(table.convertRowIndexToModel(i), 3);
				whsc.setItem_name(item_name);
				Long quantity = (Long) table.getValueAt(table.convertRowIndexToModel(i), 4);
				whsc.setQuantity(quantity);
				String note = (String) table.getValueAt(table.convertRowIndexToModel(i), 5);
				whsc.setNote(note);
				lstData.add(whsc);
			}
			if(lstData != null && lstData.size() > 0){
				SimpleToolExcel<WarehouseStockcardDetail> tool = new SimpleToolExcel<WarehouseStockcardDetail>();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				String sequence = format.format(new Date());
				tool.exportData(lstData, lstData.get(0).getObjectHeader(), "WAREHOUSE STOCKCARD LIST DATA", "WHSC", "WarehouseStockcard_" + sequence);
//				exportData(lstData, "GOODS_RECEIVE_NOTE");
			}
		}
		if(action.getAction() == CActionEvent.IMPORT_DATA){
			SimpleToolExcel<WarehouseStockcardDetail> tool = new SimpleToolExcel<WarehouseStockcardDetail>();
			List<WarehouseStockcardDetail> lstData = tool.importData(new WarehouseStockcardDetail(), "WHSC", WarehouseStockcardDetail.class);
			try {
				TableUtil.addListToTable(whscItemList, whscItemList.getTable(), lstData);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public class WHSCDetailWrapper extends AbstractIshopEntity {
		private String warehouseName;
		private String lotId;
		private WarehouseStockcardDetail warehouseStockcardDetail;
		private ProductItem item;
		private String note;

		public String getWarehouseName() {
			return warehouseName;
		}

		public void setWarehouseName(String warehouseId) {
			this.warehouseName = warehouseId;
		}

		public WarehouseStockcardDetail getWarehouseStockcardDetail() {
			return warehouseStockcardDetail;
		}

		public void setWarehouseStockcardDetail(WarehouseStockcardDetail warehouseStockcardDetail) {
			this.warehouseStockcardDetail = warehouseStockcardDetail;
		}

		@Override
		public String[] getObjectHeader() {
			return new String[] { "", "lot.id", "item.id", "item.name", "Quantity", "Note" };
		}

		@Override
		public Object[] toObjectArray() {
			return new Object[] { Boolean.FALSE, getLotId(), item.getProductId(), item.getProductName(), warehouseStockcardDetail.getQuantity(),
					getNote() };
		}

		public ProductItem getItem() {
			return item;
		}

		public void setItem(ProductItem item) {
			this.item = item;
		}

		public String getLotId() {
			return lotId;
		}

		public void setLotId(String lotId) {
			this.lotId = lotId;
		}

		public String getNote() {
			return note;
		}

		public void setNote(String note) {
			this.note = note;
		}
	}
	public void itemStateChanged(ItemEvent e) {
		final CComboBox cbbLot = new CComboBox();
		if (e.getStateChange() == ItemEvent.SELECTED) {
			try {
				ItemWrapper selectedItem = (ItemWrapper) e.getItem();
				List<Lot> lstLot = WarehouseLogic.loadLotByWarehouse(selectedItem.getId());
				CommonLogic.updateComboBox(lstLot, cbbLot);

				// List<Warehouse> lstWareHouse =
				// WarehouseLogic.loadWarehouse();
				CommonLogic.updateComboBox(lstLot, cbbLot);
				DefaultCellEditor warehouseCellEditor = new DefaultCellEditor(cbbLot) {
					@Override
					public Object getCellEditorValue() {
						return cbbLot.getSelectedItemId();
					}
				};
				whscItemList.getTable().getColumnModel().getColumn(1).setCellEditor(warehouseCellEditor);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

	}
}
