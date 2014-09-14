package com.nn.ishop.client.gui.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

import com.nn.ishop.client.CActionEvent;
import com.nn.ishop.client.CActionListener;
import com.nn.ishop.client.gui.GUIActionEvent;
import com.nn.ishop.client.gui.GUIActionEvent.GUIActionType;
import com.nn.ishop.client.gui.GUIActionEvent.GUIType;
import com.nn.ishop.client.gui.GUIActionListener;
import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.admin.product.ProductHelper;
import com.nn.ishop.client.gui.dialogs.SystemMessageDialog;
import com.nn.ishop.client.gui.helper.TableUtil;
import com.nn.ishop.client.util.ItemWrapper;
import com.nn.ishop.client.util.Library;
import com.nn.ishop.client.util.Util;
import com.nn.ishop.server.dto.product.ProductCategory;

/** It maybe redundant class - we did not use it */
public class CPaging extends JPanel implements CActionListener, TableModelListener{
	private static final long serialVersionUID = 1094923649194296890L;
    static Logger logger = Logger.getLogger(CPaging.class);
	/** 
	 * Main model for this paging table, modify by setTableModel()
	 * The table model always use last column as hidden column for storing Id 
	 */
	private CTable table = new CTable();	
	private PageActionPanel actionPanel;
	private JScrollPane tableScrollPane;
	
	private GUIType tableGUIType;

	public GUIType getTableGUIType() {
		return tableGUIType;
	}
	public void setTableGUIType(GUIType tableGUIType) {
		this.tableGUIType = tableGUIType;
	}

	/**
	 * Update paging table data
	 * @param columnNames
	 * @throws Exception
	 */
	public void updatePagingData(String[] columnNames) throws Exception{
		
	}
	
	public CPaging(){
		setBackground(Color.WHITE);
		setLayout(new BorderLayout());		
		//-- Build the tool bar
		tableScrollPane = new JScrollPane(table,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		actionPanel = new PageActionPanel();
		
		//-- Laying components
		add(/*filterPanel*/buildToolBar(), BorderLayout.NORTH);
		add(tableScrollPane,BorderLayout.CENTER);
		add(actionPanel,BorderLayout.SOUTH);
		
		table.setBackground(Color.WHITE);
		//table.setModel(new CTableModel());
		
		tableScrollPane.getViewport().setBackground(Color.WHITE);
		updateScrollPaneDimension();
		setBorder(BorderFactory.createCompoundBorder(
				new RoundedCornerBorder()/*new CLineBorder(null, CLineBorder.DRAW_ALL_BORDER)*/, 
				BorderFactory.createEmptyBorder(1,1,1,1) 
				));
		table.addCActionListener(this);
		//chkSearchInList.setBackground(Library.DEFAULT_WINDOW_AND_MENU_BACKGROUND);
		setBackground(Library.DEFAULT_WINDOW_AND_MENU_BACKGROUND);
	}
	public CTextField txtSearchByNameId = new CTextField();
	CTextField txtChoosenCat = new CTextField();
	ProductCategory prodCatEntity = null;
	public JToolBar toolBar = new JToolBar(JToolBar.HORIZONTAL);
	//CCheckBox chkSearchInList = new CCheckBox(Language.getInstance().getString("search.in.list"));
	
	public CButton2 addBtn = new CButton2(Util.getIcon("button/add.png"));
	public CButton2 saveBtn = new CButton2(Util.getIcon("button/save2db.png"));		
	public CButton2 modifyBtn = new CButton2(Util.getIcon("button/edit.png"));
	public CButton2 deleteBtn = new CButton2(Util.getIcon("button/del.png"));				
	public CButton2 refreshBtn = new CButton2(Util.getIcon("button/refresh.png"));		
	public CButton2 btnSearchButton = new CButton2(Util.getIcon("button/search.png"));
	
	public void hideToolbar(){
		toolBar.setVisible(false);
	}
	public void setToolBar(JToolBar newToolBar){
		remove(toolBar);
		toolBar=newToolBar;
		add(toolBar, BorderLayout.NORTH);		
	}
	protected JToolBar buildToolBar(){
		toolBar.setBorder(new CLineBorder(null,CLineBorder.DRAW_BOTTOM_BORDER));
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT,0,2));
		toolBar.setBackground(Library.DEFAULT_WINDOW_AND_MENU_BACKGROUND);		
		toolBar.setFloatable(false);
		
		addBtn.setBackground(Library.DEFAULT_WINDOW_AND_MENU_BACKGROUND);
		addBtn.setOriBgColor(Library.DEFAULT_WINDOW_AND_MENU_BACKGROUND);
		
		saveBtn.setBackground(Library.DEFAULT_WINDOW_AND_MENU_BACKGROUND);
		saveBtn.setOriBgColor(Library.DEFAULT_WINDOW_AND_MENU_BACKGROUND);
		
		modifyBtn.setBackground(Library.DEFAULT_WINDOW_AND_MENU_BACKGROUND);
		modifyBtn.setOriBgColor(Library.DEFAULT_WINDOW_AND_MENU_BACKGROUND);
		
		deleteBtn.setBackground(Library.DEFAULT_WINDOW_AND_MENU_BACKGROUND);
		deleteBtn.setOriBgColor(Library.DEFAULT_WINDOW_AND_MENU_BACKGROUND);
		
		refreshBtn.setBackground(Library.DEFAULT_WINDOW_AND_MENU_BACKGROUND);
		refreshBtn.setOriBgColor(Library.DEFAULT_WINDOW_AND_MENU_BACKGROUND);
		
		btnSearchButton.setBackground(Library.DEFAULT_WINDOW_AND_MENU_BACKGROUND);
		btnSearchButton.setOriBgColor(Library.DEFAULT_WINDOW_AND_MENU_BACKGROUND);
		
//		addBtn.setOpaque(false);modifyBtn.setOpaque(false);deleteBtn.setOpaque(false);
//		refreshBtn.setOpaque(false);
//		saveBtn.setOpaque(false);
		
		toolBar.add(saveBtn);toolBar.add(addBtn);toolBar.add(modifyBtn);toolBar.add(deleteBtn);toolBar.add(refreshBtn);
		toolBar.addSeparator();
		toolBar.addSeparator();
		toolBar.addSeparator();		
//		txtSearchByNameId.setOpaque(false);
		toolBar.add(txtSearchByNameId);		
		toolBar.addSeparator();
		
		toolBar.add(btnSearchButton);
		
		btnSearchButton.addActionListener(new AbstractAction() {			
			public void actionPerformed(ActionEvent e) {
				performSearchByName();				
			}
		});
		
		refreshBtn.addActionListener(new AbstractAction() {			
			public void actionPerformed(ActionEvent e) {				
				if(tableGUIType != null && tableGUIType.equals(GUIType.PRODUCT) && prodCatEntity != null){
					CActionEvent evt = new CActionEvent(this, 
							CActionEvent.LOAD_PRODUCT_BY_CAT_TREE, prodCatEntity);
					fireCAction(evt);
				}else if(tableGUIType != null && tableGUIType.equals(GUIType.WH_LOT_LIST)) {
					CActionEvent evt = new CActionEvent(this, 
							CActionEvent.LOAD_WARE_HOUSE_LOT, null);
					fireCAction(evt);
				}else{
					performSearchByName();
					fireCAction(
							new CActionEvent(CPaging.this
							,CActionEvent.PAGING_SEARCH_EVENT, txtSearchByNameId.getText()));
				}
			}
		});
		
		deleteBtn.addActionListener(new AbstractAction() {			
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				if(getTable().getSelectedRow() == -1)
					return;
				final Object obj = getTable().getModel().getValueAt(
						getTable().convertRowIndexToModel(getTable().getSelectedRow()),1);
				if(obj == null)
					return;
				if(tableGUIType != null && tableGUIType.equals(GUIType.PRODUCT)){					
					int res = SystemMessageDialog.showConfirmDialog(null, 
  							Language.getInstance().getString("confirm.delete.node") , 
  							SystemMessageDialog.SHOW_OK_CANCEL_OPTION);
  					if(res == 0)
	  					fireCAction(
	  							new CActionEvent(CPaging.this
	  							,CActionEvent.DELETE_PRODUCT, obj)
	  					);
				}else if(tableGUIType != null && tableGUIType.equals(GUIType.WH_LOT_LIST)) {
					deleteLots();
				}else{
					fireCAction(
							new CActionEvent(CPaging.this
							,CActionEvent.PAGING_DELETE_EVENT, null));
				}
			}
		});
		modifyBtn.addActionListener(new AbstractAction() {			
			public void actionPerformed(ActionEvent e) {			
				if(getTable().getSelectedRow() == -1)
					return;
				Object obj = null;
					obj = getTable().getModel().getValueAt(
							getTable().convertRowIndexToModel(getTable().getSelectedRow()), 1);
				if(obj == null)
					return;
				if(tableGUIType != null && tableGUIType.equals(GUIType.PRODUCT)){
					fireCAction(
  							new CActionEvent(CPaging.this
  							,CActionEvent.INIT_UPDATE_PRODUCT, obj)
  					);
				
				}else{
					fireCAction(
							new CActionEvent(CPaging.this
							,CActionEvent.PAGING_EDIT_EVENT, null));
				}
			}
		});
		addBtn.addActionListener(new AbstractAction() {			
			public void actionPerformed(ActionEvent e) {				
				if(tableGUIType != null && tableGUIType.equals(GUIType.PRODUCT)){					
					fireCAction(
  							new CActionEvent(CPaging.this
  							,CActionEvent.INIT_ADD_PRODUCT, null)
  					);
				}else{
					fireCAction(
  							new CActionEvent(CPaging.this
  							,CActionEvent.PAGING_ADD_EVENT, null)
  							);
				}
			}
		});
		saveBtn.addActionListener(new AbstractAction(){
			private static final long serialVersionUID = -7294625772220697010L;
			public void actionPerformed(ActionEvent e) {
				//-- Save tag changed, data is a vector of selected item id
				fireGUIActionEvent(new GUIActionEvent(saveBtn, GUIActionType.SAVE,
						tableGUIType,
						rowsChange));
				fireCAction(
							new CActionEvent(CPaging.this
							,CActionEvent.PAGING_SAVE_EVENT, null)
							);
			}
		});
		
		return toolBar;
	}
	void performSearchByName(){		
		searchInList();
	}
	/**
	 * Search product by name or id based on current loaded items
	 * this event raised by search button in tool bar
	 */
	void searchInList(){
		// Case 1: no data in the list
		if(table.getModel() instanceof DefaultTableModel){
			//- if product list then fire event to search all products
			if(tableGUIType != null && (tableGUIType.equals(GUIType.PRODUCT)
					|| tableGUIType.equals(GUIType.PRICELIST)) ){
				int action = tableGUIType.equals(GUIType.PRODUCT)
				? CActionEvent.SEARCH_PRODUCT_LIST:CActionEvent.SEARCH_PRICELIST;
				CActionEvent evt = new CActionEvent(this, 
						action, txtSearchByNameId.getText());
				fireCAction(evt);
			}
		}
	}
	/**
	 * Add additional feature base on tableGUIType
	 */
	public void addToolbarPlugin(){
		if(tableGUIType != null && (
				tableGUIType.equals(GUIType.PRODUCT)
				|| tableGUIType.equals(GUIType.PRICELIST)
						)){
			CButton2 btnChooseCatTree = new CButton2(Util.getIcon("button/treeicon2.png"));
			btnChooseCatTree.setBackground(Library.DEFAULT_WINDOW_AND_MENU_BACKGROUND);
			txtChoosenCat.setOpaque(false);
			toolBar.add(txtChoosenCat);
			btnChooseCatTree.setOpaque(false);
			toolBar.add(btnChooseCatTree);
			txtChoosenCat.setEditable(false);
			btnChooseCatTree.addActionListener(new AbstractAction() {			
				private static final long serialVersionUID = 1L;
				public void actionPerformed(ActionEvent e) {
					if(tableGUIType.equals(GUIType.PRODUCT))
						searchByProductCat(CActionEvent.LOAD_PRODUCT_BY_CAT_TREE);
					else if( tableGUIType.equals(GUIType.PRICELIST)){
						searchByProductCat(CActionEvent.LOAD_PRICELIST_BY_CAT);
					}
					
				}
			});
		}
	}
	void searchByProductCat(int action){
		prodCatEntity = ProductHelper.chooseCategory();
		if(prodCatEntity == null) return;
		txtChoosenCat.setText(prodCatEntity.toString());	
		CActionEvent evt = new CActionEvent(this, 
				action, prodCatEntity);
		fireCAction(evt);
	}

	/**
	 * Update choosen product category information. Use for product paging only. 
	 * @param prodCatEntity
	 */
	public void updateSelectedCat(ProductCategory prodCatEntity){
		this.prodCatEntity = prodCatEntity;
		txtChoosenCat.setText(prodCatEntity.toString());
	}
	
	void updateScrollPaneDimension()
	{
		Dimension d = table.getPreferredSize();
		d.height+=100;
		tableScrollPane.setPreferredSize(d);
		//tableScrollPane.setMaximumSize(d);
		tableScrollPane.setMinimumSize(d);
	}
	
	/**
	 * Important method call when entity selection changed event
	 * the table model to show based on associated mode
	 * @param tableModel
	 */
	public void setTableModel(CTableModel tableModel)
	{
		
	}
	/**
	 * Get selected ids stored in the last column (hidden column) of the current table model
	 * NOTICE: the model is not always <code>mainTableModel</code>
	 * @return
	 */
	public Vector<String> getSelectedItem()
	{
		if(! (table.getModel() instanceof CTableModel ))
			return null;// In case the table have nothing
		Vector<String> selectedIdsVector = new Vector<String>();
		CTableModel currentModel = ((CTableModel)table.getModel());
		try {
			for (int i = 0; i < currentModel.getRealRowCount(); i++)
				if (((Boolean) currentModel.getValueAt(
						table.convertRowIndexToModel(i), 0)).booleanValue() == true)
					selectedIdsVector.add((String) currentModel.getValueAt(
							table.convertRowIndexToModel(i),
							currentModel.getColumnCount()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return selectedIdsVector;
	}
	/**
	 * get the table model of this Paging
	 * @return
	 */
	public CTableModel getModel() {
		return (CTableModel)getTable().getModel();/*this.mainTableModel*/
	}
	/**
	 * get the table of this Paging
	 * @return
	 */
	public CTable getTable() {
		return this.table;
	}
	/**
	 * Button navigation inner class, events raised by alphabet button will be processed internally
	 * @author connect.shift-think.com
	 *
	 */
	class ButtonNavigationPanel extends JPanel
	{
		private static final long serialVersionUID = 8624235862821096138L;
		JPanel pnlLeft;
		JPanel pnlRight;

		protected CButton[] buttonArrays = new CButton[37];

		
		CButton clickedButton = null;
		public ButtonNavigationPanel()
		{
			setLayout(new BorderLayout());
			setBorder(BorderFactory.createCompoundBorder(
					new CLineBorder(null,CLineBorder.DRAW_BOTTOM_BORDER),
					BorderFactory.createEmptyBorder(1,1,1,1)));
			FlowLayout fo1 = new FlowLayout(FlowLayout.LEFT,2,2);
			pnlLeft = new JPanel(fo1);

			FlowLayout fo = new FlowLayout(FlowLayout.RIGHT,2,2);
			pnlRight = new JPanel(fo);

			applyStyle();
			bindEventHandler();

			JPanel tempPnl = new JPanel(new BorderLayout()){
				private static final long serialVersionUID = 1L;
				private Graphics2D graphics;
				@Override
				protected void paintComponent(Graphics g) {  
					
			        graphics = (Graphics2D) g;
			        // Paint a gradient from top to bottom
			        GradientPaint gp = new GradientPaint(
			            0, 0,new Color(255,255,255),
			            0, getHeight()-1, new Color(178,178,178));

			        graphics.setPaint( gp );
			        graphics.fillRect( 0, 0,  getWidth()-1, getHeight()-1 );
			        //graphics.setColor(getBackground());
			        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
			    			RenderingHints.VALUE_ANTIALIAS_ON);
			        //graphics.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 8, 8);
			        setOpaque( false );
			        super.paintComponent(graphics);
			        setOpaque( true );
			        
			    }
			};
			pnlLeft.setOpaque(false);
			tempPnl.add(pnlLeft, BorderLayout.WEST);
			pnlRight.setOpaque(false);
			tempPnl.add(pnlRight, BorderLayout.EAST);

			ScrollableBar mainScrollableBar = new ScrollableBar(tempPnl);
			this.add(mainScrollableBar, BorderLayout.CENTER);
			this.setAlignmentY(Component.CENTER_ALIGNMENT);
			this.setAlignmentX(Component.CENTER_ALIGNMENT);			
		}

		private void applyStyle()
		{
		}

		private void bindEventHandler()
		{
			
		}

		/**
		 * Handles selected letter in case person
		 * and organization tab is changed. Just 0 or 1 value
		 * is passed
		 * @param index Selected letter index
		 */
		public void setDefaultSelectedLetter(int index){

			//selected
			if(index == 0)
			{
				buttonArrays[index].setForeground(Color.white);
			}
			 else
			{
				 buttonArrays[index].setForeground(Color.white);
			}

			int len = buttonArrays.length;

			for(int i = 0; i < len; i++){
				if(i == index)
					continue;

				if(i == 0)
				{
					buttonArrays[i].setOpaque(true);
					buttonArrays[i].setForeground(Color.black);
				}
				 else
				{
					buttonArrays[i].setOpaque(true);
					buttonArrays[i].setForeground(Color.black);
				}
			}
		}
		public void setShowAssociated(boolean showAssociated) {
		}

	}
	CLabel pagingDescriptionLabel = new CLabel();
	/**
	 * Action Panel for Paging, all event are processed internally except <code>SAVE</code> event
	 * @author connect.shift-think.com
	 *
	 */
	class PageActionPanel extends JPanel {
		private static final long serialVersionUID = 8018143565556043666L;
		private JPanel pnlLeft = new JPanel(new FlowLayout(FlowLayout.LEFT,2,2));
		private JPanel pnlRight = new JPanel(new FlowLayout(FlowLayout.RIGHT,5,2));
		private CDialogsLabelButton impButton;
		private CDialogsLabelButton delButton;
		private CDialogsLabelButton expButton;
		private BasicPrevArrowButton btnPrevious;
		private BasicNextArrowButton btnNext;

		private JCheckBox chkCheckAll;
		CLabel lblEntries = new CLabel(Language.getInstance().getString("paging.panel.show.entries")
				/*"Show entries"*/);
		
		Vector<String> rowPerPageComboModel
			= new Vector<String>(
					Arrays.asList(new String[]{"10","20", "30", "50", "100","All"}));
		private CComboBox rowPerPageComboBox;
		public PageActionPanel(){
			setLayout(new BorderLayout());
			setBorder(BorderFactory.createCompoundBorder(
					new CLineBorder(null,CLineBorder.DRAW_TOP_BORDER),
					BorderFactory.createEmptyBorder(1,1,1,1)));
			chkCheckAll = new CCheckBox(Language.getInstance().getString("checkbox.check.all"));
			chkCheckAll.setBackground(Library.DEFAULT_WINDOW_AND_MENU_BACKGROUND);
			expButton = new CDialogsLabelButton(
					Language.getInstance().getString("buttonExport")/*,
					Util.getIcon("dialog/btn_normal.png"),
					Util.getIcon("dialog/btn_over.png")*/);
			delButton =  new CDialogsLabelButton(
					Language.getInstance().getString("buttonDelete")/*,
					Util.getIcon("dialog/btn_normal.png"),
					Util.getIcon("dialog/btn_over.png")*/);
			impButton =  new CDialogsLabelButton(
					Language.getInstance().getString("buttonImport")/*,
					Util.getIcon("dialog/btn_normal.png"),
					Util.getIcon("dialog/btn_over.png")*/);
			
			pnlLeft.add(chkCheckAll);
			pnlLeft.add(delButton);
			pnlLeft.add(impButton);
			pnlLeft.add(expButton);
			add(pnlLeft,BorderLayout.WEST);

			rowPerPageComboBox = new CComboBox(rowPerPageComboModel);			
			btnPrevious = new BasicPrevArrowButton();
			btnNext     = new BasicNextArrowButton();

			pnlRight.add(lblEntries);
			pnlRight.add(rowPerPageComboBox);
			pnlRight.add(pagingDescriptionLabel);
			//pagingDescriptionLabel.setPreferredSize(new Dimension(80,22));
			pnlRight.add(btnPrevious);
			pnlRight.add(btnNext);
			
			
			add(pnlRight,BorderLayout.EAST);
			pnlRight.setBackground(Library.DEFAULT_WINDOW_AND_MENU_BACKGROUND);
			pnlLeft.setBackground(Library.DEFAULT_WINDOW_AND_MENU_BACKGROUND);
			setBackground(Library.DEFAULT_WINDOW_AND_MENU_BACKGROUND);
			bindEventHandlers();
		}
		private void bindEventHandlers(){
			btnPrevious.addActionListener(new AbstractAction(){
				private static final long serialVersionUID = -1210538320425039094L;
				public void actionPerformed(ActionEvent e) {
					((CTableModel)table.getModel()).pageUp();
					setPagingInfo();
				}
			});
			btnNext.addActionListener(new AbstractAction(){
				private static final long serialVersionUID = -7294625772220697010L;
				public void actionPerformed(ActionEvent e) {
					if(table.getModel() instanceof CTableModel){
						((CTableModel)table.getModel()).pageDown();
						setPagingInfo();
					}
				}
			});
			chkCheckAll.addActionListener(new AbstractAction(){
				private static final long serialVersionUID = 8179555646503375551L;
				public void actionPerformed(ActionEvent e) {
					handleCheckAllAction(chkCheckAll.isSelected());
				}
			});
//			saveButton.addActionListener(new AbstractAction(){
//				private static final long serialVersionUID = -7294625772220697010L;
//				public void actionPerformed(ActionEvent e) {
//					//-- Save tag changed, data is a vector of selected item id
//					synchronized (ils) {
//						callGUIActionListener(new GUIActionEvent(saveButton, GUIActionType.SAVE,
//								tableGUIType,
//								rowsChange));
//					}
//					rowsChange.clear();
//					
//				}
//			});
			delButton.addActionListener(new AbstractAction(){
				private static final long serialVersionUID = -7294625772220697010L;
				public void actionPerformed(ActionEvent e) {
					/*if(table.getModel() == null || table.getModel().getRowCount()<=0)
						return;*/
					//-- Save tag changed, data is a vector of selected item id
					deleteLots();
				}
			});
//			cancelButton.addActionListener(new AbstractAction(){
//				private static final long serialVersionUID = -7294625772220697010L;
//				public void actionPerformed(ActionEvent e) {
//					//-- Save tag changed, data is a vector of selected item id
//					callGUIActionListener(new GUIActionEvent(cancelButton, GUIActionType.CANCEL,
//							tableGUIType,
//							getSelectedItem()));
//				}
//			});
			rowPerPageComboBox.addItemListener(new ItemListener(){
				public void itemStateChanged(ItemEvent e) {
					changeRowPerPage();
				}
			});
			rowPerPageComboBox.setSelectedIndex(0);
			expButton.addActionListener(new AbstractAction() {
				public void actionPerformed(ActionEvent arg0) {
					CActionEvent exportEvent = new CActionEvent(this, CActionEvent.EXPORT_DATA, null);
					fireCAction(exportEvent);
				}
			});
			impButton.addActionListener(new AbstractAction() {
				public void actionPerformed(ActionEvent arg0) {
					CActionEvent exportEvent = new CActionEvent(this, CActionEvent.IMPORT_DATA, null);
					fireCAction(exportEvent);
				}
			});
			
		}
		void handleCheckAllAction(Boolean checkedValue){
			if(table.getModel().getRowCount() <=0)
				return;			
			CTableModel model = (CTableModel)table.getModel();
			int cuPage = model.getPageOffset();
			model.gotoPage(0);
			int pageSize =model.getPageSize();		
		    for(int i=0;i<model.getPageCount();i++) {
		    	for(int j=0; j<pageSize;j++){
			    	model.setValueAt(checkedValue, j, 0);
		    	}
		    	model.pageDown();								
		    }
		    //-- return current page
		    model.gotoPage(cuPage);
		    setPagingInfo();
		}
		int getCurrentPageNumberForRow(int row){
			int ret = 1;
			try{
				CTableModel model = (CTableModel)table.getModel();
	    		ret = (int)Math.floor((double) row/model.getRealRowCount());
	    	}catch(Exception ex){
	    		
	    	}
			return ret;
		}
		public String getPageSize(){
			if(table.getModel().getRowCount() <=0 || rowPerPageComboBox.getSelectedItem().toString().equals("All") )
				return "1";
			if(rowPerPageComboBox.getSelectedItem() != null)
				return (String)rowPerPageComboBox.getSelectedItem();
			else
				return rowPerPageComboModel.get(0);
		}
		/**
		 * Change row per page for this Pagination, it invoked by row-per-page combo box item changed event.
		 */
		void changeRowPerPage(){
			if(table.getModel() instanceof CTableModel){
				int s;
				try {
					s = Integer.parseInt((String)rowPerPageComboBox.getSelectedItem());
				} catch (Exception ex) {
					s = ((CTableModel)table.getModel()).getRealRowCount();
				}
				((CTableModel)table.getModel()).setPageSize(s);
				((CTableModel)table.getModel()).gotoPage(0);
				
				int totalPage = ((CTableModel)table.getModel()).getPageCount();		
				if(totalPage >1){
					btnNext.setEnabled(true);
					btnPrevious.setEnabled(true);
				}else{
					btnNext.setEnabled(false);
					btnPrevious.setEnabled(false);
				}
				setPagingInfo();
			}		
		}
	}
	
	//-- end class action panel
	public int getPageSize(){
		int ret = 20;
		try
		{
			ret = Integer.parseInt(actionPanel.getPageSize());
		}
		catch(Exception ex)
		{
			// do nothing
		}
		return ret;
	}
	
	Hashtable<Integer, Hashtable<Integer,String>> rowsChange 
		= new Hashtable<Integer, Hashtable<Integer,String>>();
	
	/* (non-Javadoc)
	 * @see javax.swing.event.TableModelListener#tableChanged(javax.swing.event.TableModelEvent)
	 */
	public void tableChanged(TableModelEvent e) {		
		if(e.getColumn() <= 0 )
			return;
		//-- test
		int row = e.getFirstRow();
        int column = e.getColumn();
        
        CTableModel model = (CTableModel)e.getSource();
        Object dat = model.getValueAt(
        		getTable().convertRowIndexToModel(row), column);
        String data = null;
        if(dat instanceof String) 
        	data = (String)dat;
        else if (dat instanceof  ItemWrapper)
        	data = ((ItemWrapper)dat).getId();
        else {
        	try{
        		data = String.valueOf(dat);
        	}catch(Exception ex){
        		System.out.println("Fail to get table cell valued: " + dat );
        	}
        }       	
        	
        //-- check if row already added
		if(rowsChange.containsKey(new Integer(e.getFirstRow()))){
			Hashtable<Integer,String> cols = rowsChange.get(new Integer(e.getFirstRow()));
			if(!cols.contains(new Integer(e.getColumn()))){
				cols.put(new Integer(e.getColumn()), data);
				rowsChange.remove(new Integer(e.getFirstRow()));
				rowsChange.put(new Integer(e.getFirstRow()), cols);		
			}
		}else
		{
			Hashtable<Integer,String> cols = new Hashtable<Integer,String>(); 
			cols.put(new Integer(e.getColumn()), data);
			rowsChange.put(new Integer(e.getFirstRow()), cols);
		}		
		setPagingInfo();
	}
	public void changeRowPerPage(){
		actionPanel.changeRowPerPage();
	}
	public void setPagingInfo()
	{		
		pagingDescriptionLabel.setText("  "
				+ String.valueOf(((CTableModel)table.getModel()).getPageOffset() * ((CTableModel)table.getModel()).getPageSize()+1) + " - "
				+ String.valueOf(((CTableModel)table.getModel()).getPageOffset() * ((CTableModel)table.getModel()).getPageSize()
						+ ((CTableModel)table.getModel()).getPageSize())
				+ " of " + String.valueOf(((CTableModel)table.getModel()).getRealRowCount()));
		FontMetrics fm = pagingDescriptionLabel.getFontMetrics(pagingDescriptionLabel.getFont());
		int fontWidth = fm.stringWidth(pagingDescriptionLabel.getText());
		Dimension d = pagingDescriptionLabel.getSize();
		Dimension newDim = new Dimension(fontWidth +15, d.height );
		pagingDescriptionLabel.setSize(newDim);		
		repaint();		
	}
	
	private Vector<CActionListener> vctActListener = new Vector<CActionListener>();
	public void addCActionListener(CActionListener al) {
		vctActListener.add(al);
		
	}
	public void cActionPerformed(CActionEvent action) {
		// Pass specified events to another listeners, assume that this class do nothing
		fireCAction(action);
		//System.out.println(action.actionDesc()+"-----"+ action.getAction());
		
	} 
	private void fireCAction(CActionEvent action) {
		for(CActionListener a:vctActListener){
			a.cActionPerformed(action);
		}
	}
	public void removeCActionListener(CActionListener al) {
		vctActListener.remove(al);		
	}
	
	public void clearData(){		
		int colCnt = getTable().getColumnCount();
		Object[][] data = new Object[0][];
		String[] colNames = new String[colCnt];
		for(int i=0;i<colCnt;i++){
			colNames[i] = getTable().getColumnName(i);
		}
		TableUtil.formatTable(this, getTable(), data, colNames, Arrays.asList(new Integer[]{0,1,2}));
		getTable().setData(null);
		validate();
		
	}	
	private Vector<GUIActionListener> guiActionListenerVector = new Vector<GUIActionListener>();   
	
	public void addGUIActionListener(GUIActionListener l){
		guiActionListenerVector.add(l);
	}
	public void removeGUIActionListener(GUIActionListener l){
		guiActionListenerVector.remove(l);
	}
	private void fireGUIActionEvent(GUIActionEvent evt){
		for(GUIActionListener g:guiActionListenerVector)
			g.guiActionPerformed(evt);	
	}
	
	public void deleteLots(){
		Vector<String> selectedItems = getSelectedItem();
		if(selectedItems != null && selectedItems.size()>0)
			fireGUIActionEvent(new GUIActionEvent(this, GUIActionType.DELETE,
					tableGUIType,
					selectedItems));
	}
}