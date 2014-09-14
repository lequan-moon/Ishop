package test.table;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.nn.ishop.client.logic.CommonLogic;
import com.nn.ishop.server.dto.exrate.Currency;

public class TestTable {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Throwable{
		JFrame f = new JFrame();
		JTable table = new JTable();
		List<Currency> lstCurrency = CommonLogic.loadCurrency();
		Currency c = lstCurrency.get(0);
		Object[][] data = new Object[][]{c.toObjectArray()};
		DefaultTableModel model = new DefaultTableModel(data,c.getObjectHeader()){
			@Override
			public boolean isCellEditable(int row, int col) {				
				return true;
			}
		};
		table.setModel(model);
		JScrollPane scr = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		f.getContentPane().add(scr,BorderLayout.CENTER);
		f.validate();
		f.setVisible(true);
	}

}
