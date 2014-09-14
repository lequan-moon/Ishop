package com.nn.ishop.client.gui.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import com.nn.ishop.client.gui.GUIActionEvent;
import com.nn.ishop.client.gui.GUIActionListener;
import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.GUIActionEvent.GUIActionType;
import com.nn.ishop.client.gui.GUIActionEvent.GUIType;
import com.nn.ishop.client.typedef.TableModelType;
import com.nn.ishop.client.util.Library;
import com.nn.ishop.client.util.Util;

public class CProductDetailPaging extends JPanel implements GUIActionListener, TableModelListener{
		private static final long serialVersionUID = 1094923649194296890L;

		/** main model for this paging table, modify by setTableModel()
		 * The table model always use last column as hidden column for storing Id */
		private CTableModel mainTableModel;
		private CTable table = new CTable();
		private CWhitePanel filterPanel;
		private PageActionPanel actionPanel;
		private JScrollPane tableScrollPane;
		private JTextField elementFilterTxt = new JTextField();
		CDialogsLabelButton elementFilterButton;
		
		
		/**
		 * @deprecated use GUIType instead
		 */
		private TableModelType tableModelType;
		
		private GUIType tableGUIType;

		public GUIType getTableGUIType() {
			return tableGUIType;
		}
		public void setTableGUIType(GUIType tableGUIType) {
			this.tableGUIType = tableGUIType;
		}
		public TableModelType getTableModelType() {
			return tableModelType;
		}
		public void setTableModelType(TableModelType tableModelType) {
			this.tableModelType = tableModelType;
		}
		/**
		 * Update paging table data
		 * @param columnNames
		 * @throws Exception
		 */
		public void updatePagingData(String[] columnNames) throws Exception{
		}
		public CProductDetailPaging(){
			setBackground(Color.BLUE);
			setLayout(new BorderLayout());

			filterPanel = new CWhitePanel(new FlowLayout(FlowLayout.LEFT,5,5));
			filterPanel.setBackground(Library.DEFAULT_FORM_BACKGROUND);
			filterPanel.setBorder(BorderFactory.createCompoundBorder(
					new CLineBorder(null,CLineBorder.DRAW_BOTTOM_BORDER),
					BorderFactory.createEmptyBorder(1,1,1,1)));
			
			elementFilterButton = new CDialogsLabelButton(
					Language.getInstance().getString("button.loc.hang.hoa")/*,
					Util.getIcon("dialog/btn_normal.png"),
					Util.getIcon("dialog/btn_over.png")*/);
			
			tableScrollPane = new JScrollPane(table,
					JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			actionPanel = new PageActionPanel();
			add(filterPanel, BorderLayout.NORTH);
			add(tableScrollPane,BorderLayout.CENTER);
			
			filterPanel.add(new JLabel(Language.getInstance().getString("loc.theo.ten.hoac.id")));
			
			elementFilterTxt.setPreferredSize(new Dimension(110,20));
			filterPanel.add(elementFilterTxt);
			filterPanel.add(elementFilterButton);
			
			add(actionPanel,BorderLayout.SOUTH);
			table.setBackground(Color.WHITE);
			tableScrollPane.getViewport().setBackground(Color.WHITE);
			updateScrollPaneDimension();
			setBorder(BorderFactory.createCompoundBorder(
					new CLineBorder(null, CLineBorder.DRAW_ALL_BORDER), 
					BorderFactory.createEmptyBorder(1,1,1,1) 
					));
		}
		void updateScrollPaneDimension()
		{
//			Dimension d = table.getPreferredSize();
//			d.height+=200;
//			tableScrollPane.setPreferredSize(d);
//			tableScrollPane.setMaximumSize(d);
//			tableScrollPane.setMinimumSize(d);
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
			return this.mainTableModel;
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

				JPanel tempPnl = new JPanel(new BorderLayout());

				tempPnl.add(pnlLeft, BorderLayout.WEST);
				tempPnl.add(pnlRight, BorderLayout.EAST);

				ScrollableBar mainScrollableBar = new ScrollableBar(tempPnl);
				this.add(mainScrollableBar, BorderLayout.CENTER);
				this.setAlignmentY(Component.CENTER_ALIGNMENT);
				this.setAlignmentX(Component.CENTER_ALIGNMENT);

				tempPnl.setBackground(Library.DEFAULT_FORM_BACKGROUND);
				pnlRight.setBackground(Library.DEFAULT_FORM_BACKGROUND);
				pnlLeft.setBackground(Library.DEFAULT_FORM_BACKGROUND);
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
		CLabel pagingDescriptionLabel = new CLabel("");
		/**
		 * Action Panel for Paging, all event are processed internally except <code>SAVE</code> event
		 * @author connect.shift-think.com
		 *
		 */
		class PageActionPanel extends JPanel {
			private static final long serialVersionUID = 8018143565556043666L;
			private JPanel pnlLeft = new JPanel(new FlowLayout(FlowLayout.LEFT,2,2));
			private JPanel pnlRight = new JPanel(new FlowLayout(FlowLayout.RIGHT,5,2));
			//private CDialogsLabelButton saveButton;
			private CDialogsLabelButton delButton;
			//private CDialogsLabelButton cancelButton;
			private BasicPrevArrowButton btnPrevious;
			private BasicNextArrowButton btnNext;

			private JCheckBox chkCheckAll;

			CLabel lblEntries = new CLabel(Language.getInstance().getString("paging.panel.show.entries")
					/*"Show entries"*/);
			
			Vector<String> rowPerPageComboModel
				= new Vector<String>(
						Arrays.asList(new String[]{"20", "30", "50", "100","All"}));
			private JComboBox rowPerPageComboBox;

			public PageActionPanel(){
				setBackground(Library.DEFAULT_FORM_BACKGROUND);
				vctGUIActionListener = new Vector<GUIActionListener>();
				setLayout(new BorderLayout());
				setBorder(BorderFactory.createCompoundBorder(
						new CLineBorder(null,CLineBorder.DRAW_TOP_BORDER),
						BorderFactory.createEmptyBorder(1,1,1,1)));
				chkCheckAll = new CCheckBox(Language.getInstance().getString("checkbox.check.all"));
				chkCheckAll.setBackground(Library.DEFAULT_FORM_BACKGROUND);
//				saveButton = new CDialogsLabelButton(
//						Language.getInstance().getString("button.save"),
//						Util.getIcon("dialog/btn_normal.png"),
//						Util.getIcon("dialog/btn_over.png"));
				delButton =  new CDialogsLabelButton(
						Language.getInstance().getString("buttonDelete")/*,
						Util.getIcon("dialog/btn_normal.png"),
						Util.getIcon("dialog/btn_over.png")*/);
//				cancelButton =  new CDialogsLabelButton(
//						Language.getInstance().getString("buttonCancel"),
//						Util.getIcon("dialog/btn_normal.png"),
//						Util.getIcon("dialog/btn_over.png"));
				pnlLeft.add(chkCheckAll);				
//				pnlLeft.add(saveButton);
				pnlLeft.add(delButton);
//				pnlLeft.add(cancelButton);
				add(pnlLeft,BorderLayout.WEST);

				rowPerPageComboBox = new JComboBox(rowPerPageComboModel);
				btnPrevious = new BasicPrevArrowButton();
				btnNext     = new BasicNextArrowButton();

				pnlRight.add(lblEntries);
				pnlRight.add(rowPerPageComboBox);
				pnlRight.add(pagingDescriptionLabel);
				pagingDescriptionLabel.setPreferredSize(new Dimension(80,22));
				pnlRight.add(btnPrevious);
				pnlRight.add(btnNext);
				add(pnlRight,BorderLayout.EAST);
				pnlRight.setBackground(Library.DEFAULT_FORM_BACKGROUND);
				pnlLeft.setBackground(Library.DEFAULT_FORM_BACKGROUND);
				setBackground(Library.DEFAULT_FORM_BACKGROUND);
				bindEventHandlers();
			}
			private void bindEventHandlers(){
				btnPrevious.addActionListener(new AbstractAction(){
					private static final long serialVersionUID = -1210538320425039094L;
					public void actionPerformed(ActionEvent e) {
						((CTableModel)table.getModel()).pageUp();
						setDescriptionLabel();
					}
				});
				btnNext.addActionListener(new AbstractAction(){
					private static final long serialVersionUID = -7294625772220697010L;
					public void actionPerformed(ActionEvent e) {
						((CTableModel)table.getModel()).pageDown();
						setDescriptionLabel();
					}
				});
				chkCheckAll.addActionListener(new AbstractAction(){
					private static final long serialVersionUID = 8179555646503375551L;
					public void actionPerformed(ActionEvent e) {
						handleCheckAllAction(chkCheckAll.isSelected());
					}
				});
//				saveButton.addActionListener(new AbstractAction(){
//					private static final long serialVersionUID = -7294625772220697010L;
//					public void actionPerformed(ActionEvent e) {
//						//-- Save tag changed, data is a vector of selected item id
//						synchronized (rowsChange) {
//							callGUIActionListener(new GUIActionEvent(saveButton, GUIActionType.SAVE,
//									tableGUIType,
//									rowsChange));
//						}
//						rowsChange.clear();
//						
//					}
//				});
				delButton.addActionListener(new AbstractAction(){
					private static final long serialVersionUID = -7294625772220697010L;
					public void actionPerformed(ActionEvent e) {
						//-- Save tag changed, data is a vector of selected item id
						callGUIActionListener(new GUIActionEvent(delButton, GUIActionType.DELETE,
								tableGUIType,
								getSelectedItem()));
					}
				});
//				cancelButton.addActionListener(new AbstractAction(){
//					private static final long serialVersionUID = -7294625772220697010L;
//					public void actionPerformed(ActionEvent e) {
//						//-- Save tag changed, data is a vector of selected item id
//						callGUIActionListener(new GUIActionEvent(cancelButton, GUIActionType.CANCEL,
//								tableGUIType,
//								getSelectedItem()));
//					}
//				});
				rowPerPageComboBox.addItemListener(new ItemListener(){
					public void itemStateChanged(ItemEvent e) {
						int s;
						try {
							s = Integer.parseInt((String)rowPerPageComboBox.getSelectedItem());
						} catch (Exception ex) {
							s = ((CTableModel)table.getModel()).getRealRowCount();
						}
						((CTableModel)table.getModel()).setPageSize(s);
						((CTableModel)table.getModel()).gotoPage(0);
						setDescriptionLabel();
					}

				});
				
			}
			void handleCheckAllAction(Boolean checkedValue){
				if(table.getModel() instanceof DefaultTableModel){
					return;
				}
				CTableModel model = (CTableModel)table.getModel();
			    for(int i=0;i<model.getRealRowCount();i++) model.setValueAt(checkedValue, i, 0);
			}
			

			public String getPageSize(){
				if(rowPerPageComboBox.getSelectedItem() != null)
					return (String)rowPerPageComboBox.getSelectedItem();
				else
					return rowPerPageComboModel.get(0);
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
		private Vector<GUIActionListener> vctGUIActionListener;

		public void addGUIActionListener(GUIActionListener al){
			vctGUIActionListener.add(al);
		}
		public void removeGUIActionListener(GUIActionListener al){
			vctGUIActionListener.remove(al);
		}
		public void callGUIActionListener(GUIActionEvent e){
			for(GUIActionListener al:vctGUIActionListener)
				al.guiActionPerformed(e);
		}
		public void guiActionPerformed(GUIActionEvent action) {
			callGUIActionListener(action);
		}
		Hashtable<Integer, Hashtable<Integer,String>> rowsChange 
			= new Hashtable<Integer, Hashtable<Integer,String>>();
		public void tableChanged(TableModelEvent e) {
			if(e.getColumn() <= 0 )
				return;
			//-- test
			int row = e.getFirstRow();
	        int column = e.getColumn();
	        
	        CTableModel model = (CTableModel)e.getSource();		        
	        String data = (String)model.getValueAt(
	        		table.convertRowIndexToModel(row), column);
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
			setDescriptionLabel();
		}
		public void setDescriptionLabel()
		{
			pagingDescriptionLabel.setText("  "
					+ String.valueOf(((CTableModel)table.getModel()).getPageOffset() * ((CTableModel)table.getModel()).getPageSize()+1) + " - "
					+ String.valueOf(((CTableModel)table.getModel()).getPageOffset() * ((CTableModel)table.getModel()).getPageSize()
							+ ((CTableModel)table.getModel()).getPageSize())
					+ " of " + String.valueOf(((CTableModel)table.getModel()).getRealRowCount()));
		}
		public static void main(String args[]) throws Exception{
			JFrame f = new JFrame();
			f.getContentPane().add(new CProductDetailPaging());
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.pack();
			f.setVisible(true);
			
		}
	}