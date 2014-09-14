package com.nn.ishop.client.gui.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import com.nn.ishop.client.util.Library;

public class CTableCheckBoxRenderer
    extends CCheckBox implements TableCellRenderer {
	public CTableCheckBoxRenderer() {
		setBorderPainted(true);
        setOpaque(true);
        setHorizontalAlignment(JLabel.CENTER);
    }
  /**
	 * 
	 */
	private static final long serialVersionUID = -1648994768829380292L;

public Component getTableCellRendererComponent(JTable table, Object value,
                                                 boolean isSelected,
                                                 boolean hasFocus, int row,
                                                 int column) {
	setOpaque(true); 
	if (value == null){
		return null;
	}
	boolean b = false;
	try {
		b = ((Boolean) value).booleanValue();
	} catch (Exception e) {
	}
	setSelected( b);
	setHorizontalAlignment(CCheckBox.CENTER);

	if (row % 2 == 0) {
		setBackground(Library.C_ACTIVE_ROW);								
	} else {
		setBackground(Library.C_SECOND_ROW);
	}

	if(hasFocus){	
		if(table.isCellEditable(row, column)){
			setBorder(BorderFactory.createCompoundBorder(new DashedBorder(Color.BLUE), 
					BorderFactory.createEmptyBorder(0, 2, 0, 2)));
			setBackground(Color.WHITE);
		}else
			setBorder(BorderFactory.createCompoundBorder(new DashedBorder(), 
					BorderFactory.createEmptyBorder(0, 2, 0, 2)));
	}else		
		/*setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(0, 2, 0, 2),
				new CLineBorder(Library.TBL_BORDER_COLOR, CLineBorder.DRAW_BOTTOM_RIGHT_BORDER)
				));*/
	setBorder(new CLineBorder(Library.TBL_BORDER_COLOR, CLineBorder.DRAW_BOTTOM_RIGHT_BORDER));
	
	/*if (isSelected)
		setBackground(table.getSelectionBackground());*/
	// check if row is checked (associated) or not
	/*Boolean isChecked = false;
	try {
		isChecked = (Boolean) ((CTableModel) (table.getModel()))
				.getValueAt(row, 0);
		if(!isChecked.booleanValue()){
			 if(isSelected){
				 setBackground(table.getSelectionBackground());
			 }else			 
				 setBackground(Library.C_UNASSOCIATED_ROW);
		}	
	} catch (Exception e) {
		e.printStackTrace();
	}*/
	      
    return this;
	}

}
