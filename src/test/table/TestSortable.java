package test.table;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class TestSortable {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame("Sorting JTable");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    Object rows[][] = { { "A", "A", 1 }, { "E", "E", 4 }, { "Y", "Y", 3 } };
	    String columns[] = { "Symbol", "Name", "Price" };
	    TableModel model = new DefaultTableModel(rows, columns) {
	      public Class getColumnClass(int column) {
	        Class returnValue;
	        if ((column >= 0) && (column < getColumnCount())) {
	          returnValue = getValueAt(0, column).getClass();
	        } else {
	          returnValue = Object.class;
	        }
	        return returnValue;
	      }
	    };

	    JTable table = new JTable(model);
	    RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
	    table.setRowSorter(sorter);
	    JScrollPane pane = new JScrollPane(table);
	    frame.add(pane, BorderLayout.CENTER);
	    frame.setSize(300, 150);
	    frame.setVisible(true);

	}

}
