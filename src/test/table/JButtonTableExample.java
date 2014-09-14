package test.table;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.nn.ishop.client.gui.components.CButtonEditor;
import com.nn.ishop.client.gui.components.CButtonRenderer;

public class JButtonTableExample extends JFrame {
	public JButtonTableExample() {
	    super("JButtonTable Example");

	    DefaultTableModel dm = new DefaultTableModel();
	    Object data[][] = new Object[][] { 
	    		{ "edi","del", "foo" },
		        { "edi", "del", "foo" } 
	    };
	    Object[] column = new Object[] { "Edit","Delete", "Desc" }; 
	    dm.setDataVector(data, column);

	    JTable table = new JTable(dm);
	    table.getColumn("Edit").setCellRenderer(new CButtonRenderer());
	    table.getColumn("Edit").setCellEditor(
	        new CButtonEditor(new JCheckBox()));
	    
	    table.getColumn("Delete").setCellRenderer(new CButtonRenderer());
	    table.getColumn("Delete").setCellEditor(
	        new CButtonEditor(new JCheckBox()));
	    
	    JScrollPane scroll = new JScrollPane(table);
	    getContentPane().add(scroll);
	    setSize(400, 100);
	    setVisible(true);
	  }

	  public static void main(String[] args) {
	  
	   
	    JButtonTableExample frame = new JButtonTableExample();
	    frame.addWindowListener(new WindowAdapter() {
	      public void windowClosing(WindowEvent e) {
	        System.exit(0);
	      }
	    });
	  }
}

