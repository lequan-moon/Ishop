package com.nn.ishop.client.gui.admin.warehouse;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import com.nn.ishop.client.CActionEvent;
import com.nn.ishop.client.CActionListener;
import com.nn.ishop.client.gui.AbstractGUIManager;
import com.nn.ishop.client.gui.GUIActionEvent;
import com.nn.ishop.client.gui.GUIActionListener;
import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.components.CButton2;
import com.nn.ishop.client.gui.components.CLineBorder;
import com.nn.ishop.client.gui.components.CList;
import com.nn.ishop.client.gui.components.CTextField;
import com.nn.ishop.client.gui.dialogs.SystemMessageDialog;
import com.nn.ishop.client.logic.CommonLogic;
import com.nn.ishop.client.logic.admin.WarehouseLogic;
import com.nn.ishop.server.dto.warehouse.Warehouse;

public class WarehouseListManager extends AbstractGUIManager implements CActionListener, GUIActionListener,
ListSelectionListener, ItemListener,TableModelListener {
	JToolBar whListToolbar;
	
	String searchStr = null;
	CTextField txtSearch;
	CList whListPane;
	Warehouse selectedWh;
//	CButton2 deleteBtn;
	public WarehouseListManager (String locale){
		setLocale(locale);
		init();
	}
	void init(){
		if (getLocale() != null && !getLocale().equals("")){
			initTemplate(this, "admin/kho/whList.xml", getLocale());
		}else{
			initTemplate(this, "admin/kho/whList.xml");		
			}
		render();
		bindEventHandlers();
	}
	@Override
	protected void bindEventHandlers() {
		whListPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()>1){
					selectedWh = (Warehouse) whListPane.getSelectedValue();					
					fireCActionEvent(new CActionEvent(this, CActionEvent.LOAD_WARE_HOUSE, selectedWh));
				}
				super.mouseClicked(e);
			}
		});
		whListPane.setToolTipText(Language.getInstance().getString("double.click.to.edit"));
		whListPane.addListSelectionListener(this);
		txtSearch.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtSearch.setText("");
				super.focusGained(e);
			}
			@Override
			public void focusLost(FocusEvent e) {
				txtSearch.setText(Language.getInstance().getString("defaultSearchText"));
				super.focusLost(e);
			}
		});
//		deleteBtn.addActionListener(new ActionListener() {			
//			public void actionPerformed(ActionEvent e) {
//				fireCActionEvent(new CActionEvent(deleteBtn, CActionEvent.DELETE_WH,  (Warehouse) whListPane.getSelectedValue()));				
//			}
//		});
		/*refreshBtn.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
				prepareData();				
			}
		});*/
		prepareData();
	}

	@Override
	public Object getData(String var) {
		return null;
	}

	@Override
	protected void applyStyles() {
		whListToolbar.setBorder(BorderFactory.createCompoundBorder(
				new CLineBorder(null, CLineBorder.DRAW_BOTTOM_BORDER),
				new EmptyBorder(1,1,1,1)
				));		
		searchStr = txtSearch.getText();
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

	public void tableChanged(TableModelEvent e) {
		
	}

	public void itemStateChanged(ItemEvent e) {
		
	}
	boolean isDataChanged = false;
	public void valueChanged(ListSelectionEvent e) {
		
	}

	public void guiActionPerformed(GUIActionEvent action) {
		
		
	}

	public void cActionPerformed(CActionEvent action) {
		
	}
	protected void prepareData() {		
		try {
			Vector<Warehouse> whList = WarehouseLogic.loadWarehouseVector();
			whListPane.setListData(whList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public Action ACT_SEARCH = new AbstractAction() {	
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			searchInList();
		}
	};
	
	public Action ACT_DELETE = new AbstractAction() {	
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {			
			if(whListPane.getModel().getSize()==0 || whListPane.getSelectedValue() == null)
				return;
			Warehouse wh = (Warehouse) whListPane.getSelectedValue();
			int userConfirm = SystemMessageDialog.showConfirmDialog(null, 
					Language.getInstance().getString("confirm.delete") +wh.getName()+"?", 
					SystemMessageDialog.SHOW_OK_CANCEL_OPTION);
			if(userConfirm != 0)
				return;			
			wh.setUsage_flg(-1);
			try {
				WarehouseLogic.updateWarehouse(wh);
				prepareData();
			} catch (Exception e2) {
				System.out.println("Error while deleting warehouse...'");
				e2.printStackTrace();
			}			
		}
	};
	
	public Action ACT_REFRESH = new AbstractAction() {	
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			prepareData();
		}
	};
	
	public Action ACT_SORT_AZ = new AbstractAction() {	
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			sortTheList(0);
		}
	};
	
	public Action ACT_SORT_ZA = new AbstractAction() {	
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			sortTheList(1);
		}
	};
	/** Indices of matching result */
	Vector<Integer> searchRet = new Vector<Integer>();
	void searchInList(){
		if(txtSearch.getText() == null
				|| txtSearch.getText().equals(""))
			return;		
		try {			
			if(!searchStr.equals(txtSearch.getText())){// Search string changed
				searchStr = txtSearch.getText();
				for (int i = 0; i < whListPane.getModel().getSize(); i++) {
					Warehouse wh = (Warehouse) whListPane.getModel()
							.getElementAt(i);
					if(wh != null && wh.getName().contains(searchStr))
						searchRet.add(new Integer(i));					
				}
			}
			if(searchRet.size() >0)
			{
				whListPane.setSelectedIndex(searchRet.get(0).intValue());				
				whListPane.ensureIndexIsVisible(whListPane.getSelectedIndex());
				searchRet.remove(0);
			}else{
				SystemMessageDialog.showMessageDialog(null, 
						Language.getInstance().getString("search.last.result"), SystemMessageDialog.SHOW_OK_OPTION);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	void sortTheList(int sortOrder){
		CommonLogic.sortTheCList(sortOrder, whListPane);
	}
	Vector<CActionListener> cActionListnerVct = new Vector<CActionListener>();

	public void addCActionListener(CActionListener al) {
		cActionListnerVct.add(al);
	}
	
	private void fireCActionEvent(CActionEvent action){
		for (CActionListener al:cActionListnerVct){
			al.cActionPerformed(action);
		}
	}

	public void removeCActionListener(CActionListener al) {
		cActionListnerVct.remove(al);
	}
}
