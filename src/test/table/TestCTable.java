package test.table;

import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.RowSorter;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.nn.ishop.client.gui.components.CComboBox;
import com.nn.ishop.client.gui.components.CTable;
import com.nn.ishop.client.gui.components.CTableCellRenderer;
import com.nn.ishop.client.gui.components.CTableCheckBoxRenderer;
import com.nn.ishop.client.gui.components.CTableHeaderRenderer;
import com.nn.ishop.client.gui.components.CTableModel;
import com.nn.ishop.client.logic.CommonLogic;
import com.nn.ishop.client.logic.admin.ProductLogic;
import com.nn.ishop.client.util.Library;
import com.nn.ishop.server.dto.bank.Bank;

public class TestCTable {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		CTable tbl = new CTable();
		
		List<Bank> bank = ProductLogic.loadBank();
		Object[][] data = new Object[bank.size()][];
		int i=0;
		CComboBox cb = new CComboBox();
		DefaultCellEditor cellEdt = new DefaultCellEditor(cb);
		
		CommonLogic.updateComboBox(bank, cb);
		String[] columnNames = bank.get(0).getObjectHeader();
		
		for(Bank b: bank){
			data[i] = b.toObjectArray();
			i++;
		}
		CTableModel model = new CTableModel(data, columnNames, data.length){
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int col) {				
				return true;
			}
		};		
		tbl.setModel(model);
		
		
		RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
		tbl.setRowSorter(sorter);
		//- end new
//		tbl.setDefaultRenderer(String.class, new CTableCellRenderer());
		tbl.setSelectionBackground(Library.C_SELECTED_ROW_COLOR);
		TableColumnModel columnModel = tbl.getColumnModel();
		for(int j = 0; j < columnNames.length; j++){
			TableColumn column = columnModel.getColumn(j);
			column.setHeaderRenderer(new CTableHeaderRenderer());
			if(j == 1)column.setCellEditor(cellEdt);
			if(j == 0 ){
				column.setCellEditor(new DefaultCellEditor(new JCheckBox()));
				//column.setCellRenderer(new CTableCheckBoxRenderer());				
			}
			/*else if(j>0 && j<100){				
				column.setPreferredWidth(Math.round((p.getSize().width - 50)/(columnNames.length - 1)));
				column.setMaxWidth(50);column.setMinWidth(50);
			}*/
			
		}
		
		JScrollPane scroll = new JScrollPane(tbl);
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().add(scroll);
		f.pack();
		f.validate();		
		f.setVisible(true);

	}

}
