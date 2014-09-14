package com.nn.ishop.client.gui.components;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import com.nn.ishop.client.util.Library;

public class CTableHeaderRenderer extends CLabel implements TableCellRenderer {
	private static final long serialVersionUID = -4964256163724052321L;

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		// TODO Auto-generated method stub
		setText(value.toString());
		setForeground(Library.C_BLUE_NAME_COLOR);
		setFont(Library.TABLE_HEADER_FONT);

        // Set tool tip if desired
        setToolTipText((String)value);
        if(column <= table.getColumnCount())
	        setBorder(BorderFactory.createCompoundBorder(				
					new CLineBorder(null,
							CLineBorder.DRAW_BOTTOM_RIGHT_BORDER)
					,BorderFactory.createEmptyBorder(1, 1, 1, 1)));
        else
        	setBorder(BorderFactory.createCompoundBorder(				
					new CLineBorder(null,
							CLineBorder.DRAW_BOTTOM_BORDER)
					,BorderFactory.createEmptyBorder(1, 1, 1, 1)));
        setHorizontalAlignment(CLabel.CENTER);
        if(column == 0){
        	setPreferredSize(new Dimension(5,20));
        	setMaximumSize(new Dimension(5,20));
        }

        // Since the renderer is a component, return itself
        return this;
	}

}
