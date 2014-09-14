package com.nn.ishop.client.gui.helper;

import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.apache.log4j.Logger;

import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.components.CCheckBox;
import com.nn.ishop.client.gui.components.CPaging;
import com.nn.ishop.client.gui.components.CTable;
import com.nn.ishop.client.gui.components.CTableCellRenderer;
import com.nn.ishop.client.gui.components.CTableCheckBoxRenderer;
import com.nn.ishop.client.gui.components.CTableHeaderRenderer;
import com.nn.ishop.client.gui.components.CTableModel;
import com.nn.ishop.client.gui.components.IntegerCellEditor;
import com.nn.ishop.client.gui.components.NumberCellEditor;
import com.nn.ishop.client.gui.dialogs.SystemMessageDialog;
import com.nn.ishop.client.util.Library;
import com.nn.ishop.server.dto.AbstractIshopEntity;
/**
 * Some utilities and constants specially applied for CTable only
 * @author connect.shift-think.com
 *
 */
public class TableUtil {
	static JFrame tempFrame =  new JFrame();
	static Logger log = Logger.getLogger(TableUtil.class);
	static String whichData = "";	
	static final String MODIFY_ACT = "Mod";
	static final String DELETE_ACT = "Del";	
	
	public static void formatTable(CPaging p, final CTable tbl, Object[][] rowData, String[] columnNames)
	{
		CTableModel model = new CTableModel(rowData, columnNames, rowData.length) {
			private static final long serialVersionUID = 1L;

			public Class<?> getColumnClass(int column) {
		        Class<?> returnValue = Object.class;
		        return returnValue;
		      }			
		};		
		formatTable(p, tbl, model);
	}
	
	/**
	 * @param p
	 * @param tbl
	 * @param rowData
	 * @param columnNames
	 * @param editableColums
	 */
	public static void formatTable(CPaging p, final CTable tbl, Object[][] rowData, String[] columnNames, final List<Integer> editableColums)
	{		
		CTableModel model = new CTableModel(rowData, columnNames, rowData.length) {
			private static final long serialVersionUID = 1L;

			public Class<?> getColumnClass(int column) {
		        Class<?> returnValue = Object.class;
		        return returnValue;
		      }
			@Override
			public boolean isCellEditable(int row, int col) {
				if (editableColums != null && editableColums.size()>0 
						&& editableColums.contains(new Integer(col)))
					return true;
				return super.isCellEditable(row, col);
			}
		};		
			
		formatTable(p, tbl, model);
	}
	
	public static void formatTable(CPaging p, final CTable tbl,CTableModel model)
	{
		model.addTableModelListener(p);		
		tbl.setModel(model);
		
		RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
		tbl.setRowSorter(sorter);
		//- end new
		tbl.setDefaultRenderer(String.class, new CTableCellRenderer());
		tbl.setDefaultRenderer(Integer.class, new CTableCellRenderer());
		tbl.setDefaultRenderer(Double.class, new CTableCellRenderer());
		tbl.setDefaultRenderer(Boolean.class, new CTableCheckBoxRenderer() );
		tbl.setDefaultRenderer(Object.class, new CTableCellRenderer() );
		
		tbl.setDefaultEditor(Double.class, new NumberCellEditor());
//		tbl.setDefaultEditor(Double.class, new DoublesCellEditor(new JFormattedTextField()));
		tbl.setDefaultEditor(Integer.class, new IntegerCellEditor(Integer.MIN_VALUE,Integer.MAX_VALUE));
		tbl.setDefaultEditor(Long.class, new IntegerCellEditor(Integer.MIN_VALUE,Integer.MAX_VALUE));
		tbl.setDefaultEditor(String.class, new DefaultCellEditor(new JTextField()));
		
		tbl.setSelectionBackground(Library.C_SELECTED_ROW_COLOR);		
		/* Update table description each time the data changed */
		p.changeRowPerPage();	
		TableColumnModel columnModel = tbl.getColumnModel();
		for(int j = 0; j < model.getColumnNames().length; j++){
			TableColumn column = columnModel.getColumn(j);
			column.setHeaderRenderer(new CTableHeaderRenderer());		
			if(j == 0 ){
				CCheckBox checkBoxCellEditor = new CCheckBox();				
				column.setCellEditor(new DefaultCellEditor(checkBoxCellEditor));
				column.setPreferredWidth(30);
				column.setMaxWidth(30);
				column.setMinWidth(30);
			}else if(j>0 && j<100){				
				column.setPreferredWidth(Math.round((p.getSize().width - 50)/(model.getColumnNames().length - 1)));
			}
		}
	}
	/**
	 * @param p
	 * @param tbl
	 * @param data
	 * @throws Throwable
	 */
	public static void addListToTable(CPaging p, CTable tbl, List<?> data) throws Throwable{
		
		try {
			if(data.size() ==0){
				tbl.setData(null);
				tbl.setModel(new CTableModel());
				return;
			}
			//-- get header		
			AbstractIshopEntity ent = (AbstractIshopEntity)data.get(0);
			String[] header = ent.getObjectHeader();
			String[] columnNames = new String[header.length];
			
			for (int i = 0; i < header.length; i++) {
				String realTitle = Language.getInstance().getString(header[i]);
				columnNames[i] = realTitle;
			}
			//-- process data
			Object[][] rowData = new Object[data.size()][];
			
			for(int i=0;i<data.size();i++){
				AbstractIshopEntity obj = (AbstractIshopEntity)data.get(i);
				rowData[i] = obj.toObjectArray();
			}
			((CTable)tbl).setLstData(data);
			TableUtil.formatTable(p, tbl, rowData, columnNames);
			
		} catch (ClassCastException cce) {
			cce.printStackTrace();
			SystemMessageDialog.showMessageDialog(null,
					Language.getInstance().getString("item.isnot.abstractishop.entity"), 
					SystemMessageDialog.SHOW_OK_OPTION);
		}catch(Exception ex){
			ex.printStackTrace();
			SystemMessageDialog.showMessageDialog(null,
					Language.getInstance().getString("error") + ex.getMessage(),  
					SystemMessageDialog.SHOW_OK_OPTION);
		}
		
	}
	public static void addListToTable(CPaging p, CTable tbl, List<?> data, final List<Integer> editableColums) throws Throwable{		
		try {
			if(data == null || data.size()<=0)
				return;
			//-- get header		
			AbstractIshopEntity ent = (AbstractIshopEntity)data.get(0);
			String[] header = ent.getObjectHeader();
			String[] columnNames = new String[header.length];
			
			for (int i = 0; i < header.length; i++) {
				String realTitle = Language.getInstance().getString(header[i]);
				columnNames[i] = realTitle;
			}
			//-- process data
			Object[][] rowData = new Object[data.size()][];
			for(int i=0;i<data.size();i++){
				AbstractIshopEntity obj = (AbstractIshopEntity)data.get(i);
				rowData[i] = obj.toObjectArray();
			}
			((CTable)tbl).setLstData(data);
			TableUtil.formatTable(p, tbl, rowData, columnNames, editableColums);			
		} catch (ClassCastException cce) {
			cce.printStackTrace();
			SystemMessageDialog.showMessageDialog(null,
					Language.getInstance().getString("item.isnot.abstractishop.entity"), 
					SystemMessageDialog.SHOW_OK_OPTION);
		}catch(Exception ex){
			ex.printStackTrace();
			SystemMessageDialog.showMessageDialog(null,
					Language.getInstance().getString("error") + ex.getMessage(),  
					SystemMessageDialog.SHOW_OK_OPTION);
		}
		
	}
	
	public static void addArrayToTable(CPaging p, CTable tbl, Object[] data, final List<Integer> editableColums) throws Throwable{
		
		try {
			//-- get header		
			AbstractIshopEntity ent = (AbstractIshopEntity)data[0];
			String[] header = ent.getObjectHeader();
			String[] columnNames = new String[header.length];
			
			// Get column name from column header key
			for (int i = 0; i < header.length; i++) {
				String realTitle = Language.getInstance().getString(header[i]);
				columnNames[i] = realTitle;
			}
			//-- process data
			Object[][] rowData = new Object[data.length][];
			for(int i=0;i<data.length;i++){
				AbstractIshopEntity obj = (AbstractIshopEntity)data[i];
				rowData[i] = obj.toObjectArray();
			}
			TableUtil.formatTable(p, tbl, rowData, columnNames, editableColums);
			
		} catch (ClassCastException cce) {
			cce.printStackTrace();
			SystemMessageDialog.showMessageDialog(null,
					Language.getInstance().getString("item.isnot.abstractishop.entity"), 
					SystemMessageDialog.SHOW_OK_OPTION);
		}catch(Exception ex){
			ex.printStackTrace();
			SystemMessageDialog.showMessageDialog(null,
					Language.getInstance().getString("error") + ex.getMessage(),  
					SystemMessageDialog.SHOW_OK_OPTION);
		}
		
	}	
}