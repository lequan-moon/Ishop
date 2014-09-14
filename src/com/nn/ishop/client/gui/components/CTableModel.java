package com.nn.ishop.client.gui.components;

import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.nn.ishop.client.typedef.TableModelType;
import com.nn.ishop.server.dto.AbstractIshopEntity;

public class CTableModel extends /*AbstractTableMode*/ DefaultTableModel{
	private static final long serialVersionUID = -1573771700497746792L;
	public Object[][] data;
	
	
	public Object[][] getData() {
		return data;
	}

	public String[] columnNames;
	public String[] getColumnNames() {
		return columnNames;
	}

	public Vector<String> vctSelectedListIds;
	private Hashtable<String, String> htOldIds = new Hashtable<String, String>();
	/** This type indicate the type of this table model */
	private TableModelType modelType = null;

	/** Number of records per page */
	protected int pageSize;

	/** current page offset start with 0 */
	protected int pageOffset;

	/**
	 * 
	 */
	public CTableModel() {
	}

	/**
	 * @param data1
	 * @param columns1
	 * @param pageSize
	 * @param modelType
	 */
	public CTableModel(Object[][] data1, String[] columns1, int pageSize, TableModelType modelType ) {		
		this.data = data1;
		this.columnNames = columns1;
		this.pageSize = pageSize;
		this.modelType = modelType;
	}
	
	/**
	 * @param data1
	 * @param columns1
	 * @param pageSize
	 */
	public CTableModel(Object[][] data1, String[] columns1, int pageSize) {
		this.data = data1;
		this.columnNames = columns1;
		this.pageSize = pageSize;
	}

	/**
	 * @param selectedIds
	 * @param data1
	 * @param columns1
	 * @param pageSize
	 */
	public CTableModel(Vector<String> selectedIds, Object[][] data1,
			String[] columns1, int pageSize) {		
		this.data = data1;
		this.columnNames = columns1;
		vctSelectedListIds = selectedIds;
		this.pageSize = pageSize;
		storeOldId();

	}

	// for add new data
	/**
	 * 
	 */
	private void storeOldId() {
		if (vctSelectedListIds != null)
			for (int i = 0; i < vctSelectedListIds.size(); i++) {
				String id = (String) vctSelectedListIds.elementAt(i);
				htOldIds.put(id, id);
			}
	}

	/**
	 * @param newData
	 */
	public void setData(String[][] newData) {
		data = newData;
	}

	/**
	 * @param newData
	 */
	public void setData(Object[][] newData) {
		data = newData;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.DefaultTableModel#getColumnCount()
	 */
	public int getColumnCount() {
		return columnNames != null?columnNames.length:0;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.DefaultTableModel#getRowCount()
	 */
 public int getRowCount() {
		//- 2014.07.05 		Fix bug redundant blank rows
		int ret =0;
		if(data == null || data.length == 0)
			return 0;		
		if((pageOffset+1) * pageSize <=data.length){
			ret= pageSize;
		}else{
			ret= pageSize - ((pageOffset+1) * pageSize - data.length);
		}
		return ret;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.DefaultTableModel#getColumnName(int)
	 */
	public String getColumnName(int col) {
		return (columnNames!=null && columnNames.length>0)?columnNames[col]:null;
	}

	/**
	 * @return
	 */
	public String[] getColumnsName() {
		return columnNames;
	}

	/**
	 * Get value at absolute position (absRow,absCol) the col, row will not
	 * depended on page offset
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	public Object getValueAtAbsolutePosition(int row, int col) {
		if (row > getRealRowCount())
			return null;
		Object ret = null;
		try {
			ret = data[row][col];
		} catch (Exception e) {
		}
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int row, int col) {
		int realRow = row + (pageOffset * pageSize);
		// Use to avoid null pointer exception
		Object ret = null;
		try {
			ret = data[realRow][col];
		} catch (Exception e) {
			// ", Page internal row: " +row
			// +"  col:" + col );
			// e.printStackTrace();
		}
		return ret;
	}

	/*
	 * Don't need to implement this method unless your table's editable.
	 */
	public boolean isCellEditable(int row, int col) {		
		if (col == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @param value
	 * @param row
	 *            real row number not depended on offset
	 * @param col
	 *            real column number
	 */
	public void setValueAtAbsolutePosition(Object value, int row, int col) {
		try {
			// + getRealRowCount());
			data[row][col] = value;

		} catch (Exception e) {
			// e.printStackTrace();
		}
		fireTableCellUpdated(row, col);
	}

	/*
	 * Don't need to implement this method unless your table's data can change.
	 */
	public void setValueAt(Object value, int row, int col) {
		int realRow = row + (pageOffset * pageSize);
		if(realRow >= data.length)
			return;
		try {
			data[realRow][col] = value;
			if(pageOffset >0)
				fireTableCellUpdated(row, col);
			else
				fireTableCellUpdated(realRow, col);		
		} catch (Exception e) {
			System.out.println("Error setting cell values at: [" +row+","+col+"] or ["+realRow + ","+col+"]" );
			// e.printStackTrace();
		}
		//System.out.println("pageOffset, realRow, col " +pageOffset+","+ realRow + "," + col);
		
	}

	// Use this method to figure out which page you are on.
	public void gotoPage(int newOffset) {
		if (newOffset == pageOffset || newOffset > getPageCount())
			return;
		if (pageOffset < newOffset)
			while (pageOffset != newOffset)
				pageDown();
		if (pageOffset > newOffset)
			while (pageOffset != newOffset)
				pageUp();
		// + "\n  ---- Current offset: " + pageOffset
		// + "\n  ---- Page count: "+getPageCount());

		fireTableDataChanged();
		
	}

	// Use this method to figure out which page you are on.
	public int getPageOffset() {
		return pageOffset;
	}

	public int getPageCount() {
		return (int) Math.ceil((double) data.length / pageSize);
	}

	/**
	 * Get the total row counted in model, instead of that the getRowCount() return row count of one page only
	 * @return the whole length of row, not based on Page size while
	 *         <code>getRowCount()</code> return the size of page only
	 * @see getRowCount()
	 */
	public int getRealRowCount() {
		return data.length;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int s) {
		if (s == pageSize) {
			return;
		}
		int oldPageSize = pageSize;
		pageSize = s;
		pageOffset = (oldPageSize * pageOffset) / pageSize;
		fireTableDataChanged();
		/*
		 * if (pageSize < oldPageSize) { fireTableRowsDeleted(pageSize,
		 * oldPageSize - 1); } else { fireTableRowsInserted(oldPageSize,
		 * pageSize - 1); }
		 */
	}
	

	// Update the page offset and fire a data changed (all rows).
	public void pageDown() {
		if (pageOffset < getPageCount() - 1) {
			pageOffset++;
			fireTableDataChanged();
		}
	}

	public void pageUp() {
		if (pageOffset > 0) {
			pageOffset--;
			fireTableDataChanged();
		}

	}

	public TableModelType getModelType() {
		return modelType;
	}
	public Object[][] addData(Object[] obj){
		Object[][] newData = new Object[this.data.length+1][];
		try{			
			int i=0;
			for(Object[] o:data){
				newData[i] = o;
				i++;
			}
			newData[this.data.length] = obj;						
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return newData;
	}
	public Object[][] addData(List<? extends AbstractIshopEntity> list){
		Object[][] newData = new Object[list.size()+ data.length][];
		int i=0;
		for(AbstractIshopEntity ent:list){
			newData[i] = list.get(i).toObjectArray();
			i++;
		}	
		i = list.size()+1;
		for(Object[] o:data){
			newData[i] = o;
			i++;
		}
		return newData;
	}
	@Override
	public Class<?> getColumnClass(int columnIndex) {		
		return Object.class;
	}
	public Object[][] removeRow2(int row) {
		Object[][] newData = new Object[ data.length-1][];
		int i=0;
		for(Object[] o:data){
			if(row != i){
				newData[i] = o;
				i++;
			}
		}
		//if(lstData != null && lstData.size()>0)lstData.remove(i);
		return newData;
	}
	
}
