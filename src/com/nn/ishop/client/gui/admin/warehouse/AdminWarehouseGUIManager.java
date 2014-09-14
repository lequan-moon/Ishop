package com.nn.ishop.client.gui.admin.warehouse;

import java.awt.Dimension;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import javafx.scene.input.KeyCode;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.hibernate.dialect.FirebirdDialect;

import com.nn.ishop.client.CActionEvent;
import com.nn.ishop.client.CActionListener;
import com.nn.ishop.client.gui.AbstractGUIManager;
import com.nn.ishop.client.gui.GUIActionEvent;
import com.nn.ishop.client.gui.GUIActionEvent.GUIActionType;
import com.nn.ishop.client.gui.GUIActionEvent.GUIType;
import com.nn.ishop.client.gui.GUIActionListener;
import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.admin.customer.CTabbedPane;
import com.nn.ishop.client.gui.components.CDialogButton;
import com.nn.ishop.client.gui.components.CDialogsLabelButton;
import com.nn.ishop.client.gui.components.CLineBorder;
import com.nn.ishop.client.gui.components.CPaging;
import com.nn.ishop.client.gui.components.CTableModel;
import com.nn.ishop.client.gui.components.CTextField;
import com.nn.ishop.client.gui.components.CTwoModePanel;
import com.nn.ishop.client.gui.components.CTwoModePanel.DOCKING_DIRECTION;
import com.nn.ishop.client.gui.components.CWhitePanel;
import com.nn.ishop.client.gui.components.RoundedBorderComponent;
import com.nn.ishop.client.gui.components.RoundedCornerBorder;
import com.nn.ishop.client.gui.components.VerticalFlowLayout;
import com.nn.ishop.client.gui.dialogs.CConstants;
import com.nn.ishop.client.gui.dialogs.SystemMessageDialog;
import com.nn.ishop.client.gui.helper.TableUtil;
import com.nn.ishop.client.logic.admin.WarehouseLogic;
import com.nn.ishop.server.dto.warehouse.Lot;
import com.nn.ishop.server.dto.warehouse.Warehouse;
import com.nn.ishop.server.util.Formatter;

public class AdminWarehouseGUIManager extends AbstractGUIManager implements
		CActionListener, GUIActionListener, ListSelectionListener, ItemListener,
		TableModelListener {
	CTwoModePanel whListTwoModePnl ;
	CTwoModePanel whDetailTwoModePnl ;
	JSplitPane whHSplit;
	JSplitPane whVSplit;

	CPaging lotListPage;
	CWhitePanel warehouseInformationActionPanel;
	CTextField txtLotDesc;
	CTabbedPane whLotTabbedPane;
	RoundedBorderComponent whLotDetailContainerPnl;
	WarehouseListManager whListMng;
	WarehouseDetailManager whDetailMng;
	boolean isDataChanged = false;
	
	CDialogsLabelButton warehouseInforSaveButton, warehouseInforNewButton;
	
	public AdminWarehouseGUIManager (String locale){
		setLocale(locale);
		init();
	}
	void init(){
		if (getLocale() != null && !getLocale().equals("")){
			initTemplate(this, "admin/kho/adminWarehouseMasterPage.xml", getLocale());
		}else{
			initTemplate(this, "admin/kho/adminWarehouseMasterPage.xml");		
			}
		render();
		whListMng = new WarehouseListManager(getLocale());
		whDetailMng = new WarehouseDetailManager(getLocale());
		whListMng.addCActionListener(this);
		whListMng.addCActionListener(whDetailMng);
		
		
		whListTwoModePnl.addContent(whListMng.getRootComponent());		
		whListTwoModePnl.setManagerClazz(WarehouseListManager.class);		
		whListTwoModePnl.addGUIActionListener(this);
		whListTwoModePnl.setDockableBarLayout(new VerticalFlowLayout(1, VerticalFlowLayout.TOP)
		, DOCKING_DIRECTION.East);
		
		whDetailTwoModePnl.addContent(whDetailMng.getRootComponent());
		whDetailTwoModePnl.setManagerClazz(WarehouseDetailManager.class);
		whDetailTwoModePnl.addGUIActionListener(this);
		
		bindEventHandlers();
	}
	@Override
	protected void applyStyles() {
		warehouseInformationActionPanel.setBorder(BorderFactory.createCompoundBorder(
				new CLineBorder(CConstants.TEXT_BORDER_COLOR, CLineBorder.DRAW_TOP_BORDER),
				new EmptyBorder(2,2,2,2)
				));
		whLotTabbedPane.setTitleAt(0, Language.getInstance().getString("adminTabLot"));
		whLotDetailContainerPnl.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(1,1,1,1),				
				new RoundedCornerBorder(8)
				));
	}
	private void loadWarehouse(Warehouse wh){
		try {
			if(isDataChanged || whDetailMng.isDataChanged){
				int ret = SystemMessageDialog.showConfirmDialog(null, Language.getInstance().getString("confirm.save.changed"),
							SystemMessageDialog.SHOW_OK_CANCEL_OPTION);
				if(ret == 0){
					saveData();
				}
			}
			Lot[] lots = wh.getLots();			
			if(lots == null){
				List<Lot> lotLst = WarehouseLogic.loadLotByWarehouse(wh.getId());
				lots = lotLst.toArray(new Lot[lotLst.size()]);
			}
			if(lots != null && lots.length >0)
				TableUtil.addArrayToTable(lotListPage, lotListPage.getTable(), lots, Arrays.asList(new Integer[]{0,1,2}));
			lotListPage.getTable().getModel().addTableModelListener(this);
			this.isDataChanged = false;
			whDetailMng.isDataChanged = false;
			whDetailMng.isUpdateWh = true;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	protected void bindEventHandlers() {
		lotListPage.addGUIActionListener(this);
		lotListPage.setTableGUIType(GUIType.WH_LOT_LIST);		
		lotListPage.addCActionListener(this);
		lotListPage.addBtn.setEnabled(false);
		lotListPage.modifyBtn.setEnabled(false);
		
		ACT_SAVE_WAREHOUSE.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK));			
		warehouseInforSaveButton.getActionMap().put("saveWarehouseAction", ACT_SAVE_WAREHOUSE);
		warehouseInforSaveButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
		        (KeyStroke) ACT_SAVE_WAREHOUSE.getValue(Action.ACCELERATOR_KEY), "saveWarehouseAction");
		txtLotDesc.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {				
				if(e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_F2){
					addLot();
					txtLotDesc.requestFocusInWindow();
					txtLotDesc.select(0, txtLotDesc.getText().length());
				}
				//super.keyReleased(e);
			}
		});
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
		if(action.getAction() == CActionEvent.LOAD_WARE_HOUSE){
			Warehouse wh = (Warehouse)action.getData();
			loadWarehouse(wh);
		}
		if(action.getAction() == CActionEvent.DELETE_WH){
			deleteSelectedWh((Warehouse)action.getData());
		}
		if(action.getAction() == CActionEvent.LOAD_WARE_HOUSE_LOT){
			if(whDetailMng.updateWarehouse != null)
				loadWarehouse(whDetailMng.updateWarehouse);
		}
	}

	public void guiActionPerformed(GUIActionEvent action) {
		GUIActionType actionType = action.getAction(); 
		Object srcObj = action.getSource();
		if(actionType.equals( GUIActionType.MINIMIZE_WINDOW)){
			int location = ((Dimension)action.getData()).height;
			if(srcObj.equals(WarehouseListManager.class))
				whHSplit.setDividerLocation(location);
			else if(srcObj.equals(WarehouseDetailManager.class))
				whVSplit.setDividerLocation(location);
		}
		if(actionType.equals( GUIActionType.MAXIMIZE_WINDOW)){
			if(srcObj.equals(WarehouseListManager.class))
				whHSplit.resetToPreferredSizes();
			else if(srcObj.equals(WarehouseDetailManager.class))
				whVSplit.resetToPreferredSizes();			
		}
		if(actionType.equals( GUIActionType.DELETE) 
				&& action.getGuiType().equals(GUIType.WH_LOT_LIST)){
			try {
				Vector<String> deleteLots = (Vector<String>)action.getData();
				
				int lastColData = lotListPage.getModel().getColumnCount();
				int totalRow = lotListPage.getTable().getModel().getRowCount();			
				List<Object[]> retData = new ArrayList<Object[]>();
				for(int i=0;i< totalRow;i++){
					if(!deleteLots.contains(lotListPage.getModel().getValueAt(lotListPage.getTable().convertRowIndexToModel(i), 
							lotListPage.getTable().convertColumnIndexToModel(lastColData)))){
						retData.add(lotListPage.getModel().getData()[i]);
					}
				}
				Object[][] lots = new Object[retData.size()][];
				for (int i=0;i<retData.size();i++)
					lots[i] = retData.get(i);
				
				if (lots != null && lots.length > 0)
					TableUtil.formatTable(lotListPage,
							lotListPage.getTable(), lots, lotListPage.getModel().getColumnNames(), Arrays.asList(new Integer[]{0,1,2}));
					saveData();
					isDataChanged = true;
			} catch (Throwable e) {
				e.printStackTrace();
			}
			
		}
		
	}

	public void valueChanged(ListSelectionEvent e) {
	}

	public void tableChanged(TableModelEvent e) {
		isDataChanged = true;
	}
	@Override
	protected void checkPermission() {
	}
	
	Vector<CActionListener> cActionListnerVct = new Vector<CActionListener>();

	public void addCActionListener(CActionListener al) {
		cActionListnerVct.add(al);
	}

	public void removeCActionListener(CActionListener al) {
		cActionListnerVct.remove(al);
	}
	public void itemStateChanged(ItemEvent e) {
	}
	public Action ACT_NEW_LOT = new AbstractAction() {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			txtLotDesc.setText(null);
		}
	};
	void addLot(){
		if((!isDataChanged && !whDetailMng.isUpdateWh)){
			SystemMessageDialog.showMessageDialog(null, 
					Language.getInstance().getString("warning.cannot.add.lot.without.warehouse"), 
					SystemMessageDialog.SHOW_OK_OPTION);
			return;
		}
		String lotId = "LOT_" + UUID.randomUUID().toString();
		Lot lot = new Lot(lotId,"", txtLotDesc.getText());
		int rowIndex = lotListPage.getTable().getModel().getRowCount();
		if( rowIndex > 0){//got data
			
			Object[][] newData = ((CTableModel)lotListPage.getTable().getModel()).addData(lot.toObjectArray());
			String[] columnNames = ((CTableModel)lotListPage.getTable().getModel()).getColumnNames();
			TableUtil.formatTable(lotListPage, lotListPage.getTable(), newData, columnNames, Arrays.asList(new Integer[]{0,1,2}));
			lotListPage.getTable().getModel().addTableModelListener(this);
			isDataChanged = true;
		}else{
			List<Lot> lots = new ArrayList<Lot>();
			lots.add(lot);
			try {
				TableUtil.addListToTable(lotListPage, lotListPage.getTable(), lots, Arrays.asList(new Integer[]{0,1,2}));
				lotListPage.getTable().getModel().addTableModelListener(this);
				isDataChanged = true;
			} catch (Throwable t) {
				System.out.println(t);
			}
		}	
	}
	public Action ACT_ADD_LOT = new AbstractAction() {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
				addLot();
		}
	};
	void deleteSelectedWh(Warehouse wh){
		try{
			int userConfirm = SystemMessageDialog.showConfirmDialog(null, 
					Language.getInstance().getString("confirm.delete") +wh.getName()+"?", 
					SystemMessageDialog.SHOW_OK_CANCEL_OPTION);
			if(userConfirm != 0)
				return;
			wh.setUsage_flg(-1);
			WarehouseLogic.updateWarehouse(wh);
			whDetailMng.comboCountry.setSelectedIndex(0);
			whDetailMng.txtWarehouseName.setText(null);
			whDetailMng.txtCompanyName.setText(null);
			whDetailMng.txtWarehouseAddress.setText(null);
			
			whDetailMng.selectCompany = null;
			whDetailMng.updateWarehouse = null;
			whDetailMng.isUpdateWh = false;		
	        lotListPage.clearData();		
	        isDataChanged = false;
			whDetailMng.updateWarehouse = null;
			whDetailMng.isUpdateWh = false;
			whDetailMng.isDataChanged = false;
			whListMng.prepareData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void saveData(){		
		/*if(lotListPage.getTable().getModel() instanceof CTableModel)
			return;*/
		System.out.println("1");
		int countryId = -1;
		int provinceId = -1;
		int districtId = -1;
		try{
			System.out.println("1");
			countryId = Formatter.str2num(whDetailMng.comboCountry.getSelectedItemId()).intValue();
			System.out.println("2");
			provinceId = Formatter.str2num(whDetailMng.comboProvince.getSelectedItemId()).intValue();
			System.out.println("3");
			districtId = Formatter.str2num(whDetailMng.comboDistrict.getSelectedItemId()).intValue();
			System.out.println("4");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		try {
			System.out.println("Valid data ???" + whDetailMng.isValidData());
			if(!whDetailMng.isValidData()){
				SystemMessageDialog.showMessageDialog(null, Language.getInstance().getString("data.is.not.valid"),
						SystemMessageDialog.SHOW_OK_OPTION);
				return;
			}
			System.out.println("Saving data");
			if(whDetailMng.updateWarehouse == null){
				String whId = "WH_"+UUID.randomUUID().toString();
				whDetailMng.updateWarehouse = new Warehouse(
						whId, 
						whDetailMng.txtWarehouseName.getText(),
						whDetailMng.selectCompany.getId(),
						whDetailMng.txtWarehouseAddress.getText(),
						countryId,
						provinceId,
						districtId
						); 
			}else{
				whDetailMng.updateWarehouse.setName(whDetailMng.txtWarehouseName.getText());
				whDetailMng.updateWarehouse.setCompany_id(whDetailMng.selectCompany.getId());
				whDetailMng.updateWarehouse.setLocation(whDetailMng.txtWarehouseAddress.getText());
				whDetailMng.updateWarehouse.setCountry_id(countryId);
				whDetailMng.updateWarehouse.setProvince_id(provinceId);
				whDetailMng.updateWarehouse.setDistrict(districtId);
			}
			Lot[] lots = null;
			if(((CTableModel)lotListPage.getTable().getModel()).getRealRowCount() >0){
				lots = new Lot[((CTableModel)lotListPage.getTable().getModel()).getRealRowCount()];
				for (int i=0;i<lots.length;i++){					
					lots[i] = new Lot((String)((CTableModel)lotListPage.getTable().getModel()).getValueAt(
							i, 1),whDetailMng.updateWarehouse.getId(),
							(String)((CTableModel)lotListPage.getTable().getModel()).getValueAt(i, 2));					
				}				
			}
			whDetailMng.updateWarehouse.setLots(lots);
			if(whDetailMng.isUpdateWh){				
				WarehouseLogic.updateWarehouse(whDetailMng.updateWarehouse);
			}else{
				WarehouseLogic.insertWarehouse(whDetailMng.updateWarehouse);
			}			
			whDetailMng.prepareData();
			lotListPage.clearData();
			this.isDataChanged = false;
			whDetailMng.isDataChanged = false;
			if(whDetailMng.updateWarehouse != null){
				loadWarehouse(whDetailMng.updateWarehouse);
				whDetailMng.loadWarehouse(whDetailMng.updateWarehouse);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void newData(){
		whDetailMng.comboCountry.setSelectedIndex(0);
		whDetailMng.txtWarehouseName.setText(null);
		whDetailMng.txtCompanyName.setText(null);
		whDetailMng.txtWarehouseAddress.setText(null);		
		whDetailMng.selectCompany = null;
		whDetailMng.updateWarehouse = null;
		whDetailMng.isUpdateWh = false;		
        lotListPage.clearData();		
        isDataChanged = true;        
	}
	
	public Action ACT_NEW_WAREHOUSE = new AbstractAction() {
		
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			newData();
		}
	};
	public Action ACT_SAVE_WAREHOUSE = new AbstractAction() {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			saveData();
			whListMng.prepareData();
		}
	};
	public static void main(String[] args ) throws Exception{
		AbstractGUIManager guiManager; 
		try {
		    UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
		} catch (Exception e) {
		    e.printStackTrace();
		}	
		Language.loadLanguage("vn");
		
		JFrame f = new JFrame();
		
		guiManager = new AdminWarehouseGUIManager(Language.getInstance().getLocale());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().add(guiManager.getRootComponent());		
		
		f.pack();
		f.validate();
		
		f.setVisible(true);
	}
}
