package com.nn.ishop.client.gui.admin.warehouse;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import com.nn.ishop.client.CActionEvent;
import com.nn.ishop.client.CActionListener;
import com.nn.ishop.client.gui.AbstractGUIManager;
import com.nn.ishop.client.gui.GUIActionEvent;
import com.nn.ishop.client.gui.GUIActionListener;
import com.nn.ishop.client.gui.admin.company.CompanyHelper;
import com.nn.ishop.client.gui.components.CComboBox;
import com.nn.ishop.client.gui.components.CTextField;
import com.nn.ishop.client.logic.CommonLogic;
import com.nn.ishop.client.logic.admin.CompanyLogic;
import com.nn.ishop.server.dto.company.Company;
import com.nn.ishop.server.dto.geographic.Location;
import com.nn.ishop.server.dto.warehouse.Warehouse;

public class WarehouseDetailManager  extends AbstractGUIManager implements CActionListener, 
GUIActionListener, ListSelectionListener, ItemListener,TableModelListener {
	
	
	CComboBox comboCountry;
	CComboBox comboProvince;
	CComboBox comboDistrict;
	CTextField txtWarehouseName;
	CTextField txtCompanyName;
	CTextField txtWarehouseAddress;
	
	Company selectCompany = null;
	Warehouse updateWarehouse = null;
	boolean isUpdateWh = false;	
	
	public WarehouseDetailManager (String locale){
		setLocale(locale);
		init();
	}
	void init(){
		if (getLocale() != null && !getLocale().equals("")){
			initTemplate(this, "admin/kho/whDetail.xml", getLocale());
		}else{
			initTemplate(this, "admin/kho/whDetail.xml");		
			}
		render();
		bindEventHandlers();
	}
	public void tableChanged(TableModelEvent e) {
		
	}

	public void valueChanged(ListSelectionEvent e) {
		
	}

	public void guiActionPerformed(GUIActionEvent action) {
		
	}

	public void addCActionListener(CActionListener al) {
		
	}

	public void removeCActionListener(CActionListener al) {
		
	}

	public void cActionPerformed(CActionEvent action) {
		if(action.getAction() == CActionEvent.LOAD_WARE_HOUSE){
			Warehouse wh = (Warehouse)action.getData();
			loadWarehouse(wh);
		}
	}

	@Override
	protected void bindEventHandlers() {
		prepareData();
		comboCountry.addItemListener(this);
		comboProvince.addItemListener(this);
		comboDistrict.addItemListener(this);
		txtWarehouseName.getDocument().addDocumentListener(new DocumentListener() {
			public void removeUpdate(DocumentEvent e) {
				if(e.getOffset() >0)
					isDataChanged = true;			
			}
			
			public void insertUpdate(DocumentEvent e) {
				if(e.getOffset() >0)
					isDataChanged = true;				
			}
			
			public void changedUpdate(DocumentEvent e) {
			}
		});
	
		txtCompanyName.getDocument().addDocumentListener(new DocumentListener() {
			public void removeUpdate(DocumentEvent e) {
				if(e.getOffset() >0)
					isDataChanged = true;			
			}
			
			public void insertUpdate(DocumentEvent e) {
				if(e.getOffset() >0)
					isDataChanged = true;				
			}
			
			public void changedUpdate(DocumentEvent e) {
			}
		});
		txtWarehouseAddress.getDocument().addDocumentListener(new DocumentListener() {
			public void removeUpdate(DocumentEvent e) {
				if(e.getOffset() >0)
					isDataChanged = true;			
			}
			
			public void insertUpdate(DocumentEvent e) {
				if(e.getOffset() >0)
					isDataChanged = true;				
			}
			public void changedUpdate(DocumentEvent e) {
			}
		});
	}
	boolean isDataChanged = false;
	@Override
	public Object getData(String var) {
		return null;
	}

	@Override
	protected void applyStyles() {
		
	}

	@Override
	public void update() {
		
	}

	@Override
	public void updateList() {
		
	}

	@Override
	protected void checkPermission() {
		
	}
	void prepareData() {		
		try {
			CommonLogic.updateComboBox(CommonLogic.loadCountry(), comboCountry);		
			if(!comboCountry.getSelectedItemId().equals("-1")){
				CommonLogic.updateComboBox(
						CommonLogic.loadLocationByParentId(Integer.parseInt(comboCountry.getSelectedItemId()), 1)
						, comboProvince);
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/* Use while loading warehouse to recognize the warehouse change or combo value change*/
	Warehouse tempWh = null;
	void loadWarehouse(Warehouse wh){
		tempWh = wh;
		txtWarehouseName.setText(wh.getName());
		txtWarehouseAddress.setText(wh.getLocation());
		selectCompany = CompanyLogic.getInstance().getCompanyById(wh.getCompany_id());
		if(selectCompany != null ) 
			txtCompanyName.setText(selectCompany.getName());
		try {		
			comboCountry.setSelectedItemById(String.valueOf(wh.getCountry_id()));
			comboProvince.setSelectedItemById(String.valueOf(wh.getProvince_id()));
			comboDistrict.setSelectedItemById(String.valueOf(wh.getDistrict()));			
			isUpdateWh = true;
			updateWarehouse = wh;
			tempWh= null;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
	}
	public Action ACT_OPEN_COMPANY_DIALOG = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			selectCompany = CompanyHelper.chooseCompany();
			if (selectCompany != null) {
				txtCompanyName.setText(String.valueOf(selectCompany.getName()));
			}
		}
	};
	boolean isValidData(){
		boolean ret = true;
		ret &= selectCompany != null;
		ret &= txtWarehouseName.getText() != null && !txtWarehouseName.getText().equals("");
		return ret;
	}
	
	public void itemStateChanged(ItemEvent e) {		
		try {			
			if (e.getSource() == comboCountry) {
				if(comboCountry.getSelectedItemId().equals("-1"))
					return;
				loadSubComboData(Integer.parseInt(comboCountry.getSelectedItemId()),
						comboProvince, 1);
			} else if (e.getSource() == comboProvince) {
				if(comboProvince.getSelectedItemId().equals("-1"))
					return;
				loadSubComboData(Integer.parseInt(comboProvince.getSelectedItemId()),
						comboDistrict, 2);
			}
			//-- While loading warehouse  (selected warehouse change) the tempWh will not be null. It will be null after loading. 
			if(tempWh== null){
				isDataChanged = true;
			}	
		} catch (Exception e2) {
			logger.error(e2.getMessage());			
		}
	}	

	/**
	 * Load sub ComboBox data when parent changed
	 * @param parentId
	 * @param targetCombobox
	 * @param locationType
	 */
	private void loadSubComboData(int parentId, CComboBox targetCombobox, int locationType){
		try {
			List<Location> lstLocation = CommonLogic.loadLocationByParentId(parentId, locationType);
			CommonLogic.updateComboBox(lstLocation, targetCombobox);			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
}
