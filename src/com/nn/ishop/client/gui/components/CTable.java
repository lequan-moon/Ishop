package com.nn.ishop.client.gui.components;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import com.nn.ishop.client.CActionEvent;
import com.nn.ishop.client.CActionListener;
import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.dialogs.SystemMessageDialog;
import com.nn.ishop.client.util.Library;

public class CTable extends JTable implements CActionListener {
	/** ACTION CODE CONSTANT **/

	private static final long serialVersionUID = 1L;

	/** TABLE ID CONSTANT **/

	protected Vector<ActionListener> vctActionListeners;

	private String tableId = "";
	public CMap map;
	
	
	public CTable(String tableId) {
		jbInit(tableId);
		buildPopupMenu();
	}

	/*
	 * When using a Map
	 */
	public CTable(String tableId, CMap map) {
		this.map = map;
		jbInit(tableId);
		buildPopupMenu();
	}

	/**
	 * Valid only with CMap associated
	 */
	public Rectangle getCellRect(int row, int column, boolean includeSpacing) {
		// required because getCellRect is used in JTable constructor
		if (map == null)
			return super.getCellRect(row, column, includeSpacing);
		// add widths of all spanned logical cells
		int sk = 1;
		if ((row == getRowCount() - 1) && (column == 0) && (column < 5)) {
			sk = 0;
		} else
			sk = column;
		Rectangle r1 = super.getCellRect(row, sk, includeSpacing);
		try {
			if ((row == getRowCount() - 1) && (column == 0))/*
															 * position occurred
															 * span cells
															 */
				for (int i = 1; i < 5; i++) {
					r1.width += getColumnModel().getColumn(sk + i).getWidth();
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return r1;
	}

	/**
	 * Valid only with CMap associated
	 */
	public int columnAtPoint(Point p) {
		int x = super.columnAtPoint(p);
		// -1 is returned by columnAtPoint if the point is not in the table
		if (x < 0)
			return x;
		int y = super.rowAtPoint(p);
		if (((x >= 0) && (x < 5)) && y == getRowCount())
			return 1;
		else
			return x;
	}

	/**
	 * @see com.gbits.wsms.helper.ui.CList
	 * @param al
	 */
	public void addActionListener(ActionListener al) {
		vctActionListeners.add(al);
	}

	/**
	 * @see com.gbits.wsms.helper.ui.CList
	 * @param al
	 */
	public void removeActionListener(ActionListener al) {
		vctActionListeners.remove(al);
	}

	/**
	 * @see com.gbits.wsms.helper.ui.CList
	 * @param e
	 */
	protected void callActionListeners(ActionEvent e) {
		for (int i = 0; i < vctActionListeners.size(); i++)
			((ActionListener) vctActionListeners.get(i)).actionPerformed(e);
	}

	private void jbInit(String tableId) {
		setOpaque(true);
		setModel(new CTableModel());
		this.tableId = tableId;
		vctActionListeners = new Vector<ActionListener>();
		this.getTableHeader().setReorderingAllowed(true);
		this.setRowHeight(22);
		setShowGrid(true);
		setShowHorizontalLines(true);
		setShowVerticalLines(true);
		setGridColor(Library.TBL_BORDER_COLOR);		
		this.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		setFillsViewportHeight(true);
		setModel(new CTableModel());
	}

	@Override
	protected JTableHeader createDefaultTableHeader() {
		return super.createDefaultTableHeader();
	}

	public CTable() {
		jbInit(tableId);
		buildPopupMenu();
	}

	public CTable(Object[][] rowData, String[] columns, String tableId) {
		super(rowData, columns);
		jbInit(tableId);
		buildPopupMenu();
	}

	public CTable(Object[][] rowData, String[] columns, String tableId, CMap map) {
		super(rowData, columns);
		jbInit(tableId);
		this.map = map;
		buildPopupMenu();
	}

	public CTable(TableModel dm) {
		super(dm);
		jbInit(tableId);
		buildPopupMenu();
	}

	public CTable(TableModel dm, TableColumnModel cm, String tableId) {
		super(dm, cm);
		jbInit(tableId);
		buildPopupMenu();
	}

	public CTable(TableModel dm, TableColumnModel cm, ListSelectionModel sm,
			String tableId) {
		super(dm, cm, sm);
		jbInit(tableId);
		buildPopupMenu();
	}

	public CTable(int numRows, int numColumns, String tableId) {
		super(numRows, numColumns);
		jbInit(tableId);
		buildPopupMenu();
	}

	public CTable(Vector rowData, Vector columnNames, String tableId) {
		super(rowData, columnNames);
		jbInit(tableId);
		buildPopupMenu();
	}
	public TableCellRenderer getCellRenderer(int row, int column) {
		TableColumn tableColumn = getColumnModel().getColumn(column);
		TableCellRenderer renderer = tableColumn.getCellRenderer();
		if (renderer == null) {
			Class c = getColumnClass(column);
			if (c.equals(Object.class)) {
				Object o = null;
				o = getValueAt(
						convertRowIndexToModel(row), column);
				if (o != null) {
					c = getValueAt(convertRowIndexToModel(row), column).getClass();
				}else if(o == Boolean.FALSE){
					c = Boolean.class;
				}else{
					c = String.class;
				}
			}
			renderer = getDefaultRenderer(c);
		}
		return renderer;
	}

	/**
	 * set data for table model
	 * 
	 * @param data
	 *            An two-dimension array that contains data of table
	 */
	public void setData(String[][] data) {
		if(this.getModel() instanceof CTableModel){
			((CTableModel) this.getModel()).setData(data);	
		}
		else
		{
			this.setModel(new CTableModel());
			((CTableModel) this.getModel()).setData(data);
		}
	}

	Vector<CActionListener> cactionLstnVct = new Vector<CActionListener>();

	public void addCActionListener(CActionListener al) {
		cactionLstnVct.add(al);

	}

	public void cActionPerformed(CActionEvent evt) {
		/*for (CActionListener al : cactionLstnVct) {
			al.cActionPerformed(evt);
		}*/

	}
	public void fireCAction(CActionEvent evt) {
		for (CActionListener al : cactionLstnVct) {
			al.cActionPerformed(evt);
		}

	}

	public void removeCActionListener(CActionListener al) {
		cactionLstnVct.remove(al);
	}
	private void buildPopupMenu() {
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Point p = e.getPoint();
				final int rowNumber = CTable.this.rowAtPoint(p);

				final Object obj = (getModel() != null && getModel()
						.getRowCount() > 0) ? getModel().getValueAt(rowNumber,
						1) : null;

				// -- Set selected row to current point
				ListSelectionModel model2 = CTable.this.getSelectionModel();
				model2.setSelectionInterval(rowNumber, rowNumber);

				if (SwingUtilities.isRightMouseButton(e)) {
					JPopupMenu popup = new JPopupMenu();

					JMenuItem addItem = new JMenuItem(Language.getInstance()
							.getString("buttonNew"));
					addItem.setBackground(Color.WHITE);
					addItem.addActionListener(new AbstractAction() {
						public void actionPerformed(ActionEvent e) {

							fireCAction(new CActionEvent(CTable.this,
									CActionEvent.INIT_ADD_PRODUCT, obj));
						}
					});

					JMenuItem editItem = new JMenuItem(Language.getInstance()
							.getString("buttonEdit"));
					editItem.setBackground(Color.WHITE);
					editItem.addActionListener(new AbstractAction() {
						public void actionPerformed(ActionEvent e) {
							fireCAction(new CActionEvent(CTable.this,
									CActionEvent.INIT_UPDATE_PRODUCT, obj));
						}
					});
					JMenuItem deleteItem = new JMenuItem(Language.getInstance()
							.getString("buttonDelete"));
					deleteItem.setBackground(Color.WHITE);
					deleteItem.addActionListener(new AbstractAction() {
						public void actionPerformed(ActionEvent e) {
							int res = SystemMessageDialog.showConfirmDialog(
									null, Language.getInstance().getString(
											"confirm.delete.node"),
									SystemMessageDialog.SHOW_OK_CANCEL_OPTION);
							if (res == 0)
								fireCAction(new CActionEvent(CTable.this,
										CActionEvent.DELETE_PRODUCT, obj));
						}
					});
					JMenuItem refreshList = new JMenuItem(Language
							.getInstance().getString("table.refresh"));
					refreshList.setBackground(Color.WHITE);
					refreshList.addActionListener(new AbstractAction() {
						public void actionPerformed(ActionEvent e) {
							fireCAction(new CActionEvent(CTable.this,
									CActionEvent.INIT_UPDATE_PRODUCT, null));
						}
					});
					popup.add(addItem);
					popup.add(editItem);
					popup.add(deleteItem);
					popup.add(refreshList);

					popup.show(CTable.this, e.getX(), e.getY());
				} 
				/*else if (e.getClickCount() == 2 && e.getButton() == 1) {
					fireCAction(new CActionEvent(CTable.this,
							CActionEvent.INIT_UPDATE_PRODUCT, obj));
				}*/
				super.mouseClicked(e);
			}

		});
	}	
	public void removeRow2(int row) {
		if (lstData!= null && lstData.size()>0){
			lstData.remove(row);
		}
		if(getModel() instanceof CTableModel){
			((CTableModel)getModel()).removeRow2(row);
		}
	}
	private List<?> lstData;

	public List<?> getLstData() {
		return lstData;
	}

	public void setLstData(List<?> lstData) {
		this.lstData = lstData;
	}
}
