package com.nn.ishop.client.gui.components;

import java.awt.Color;
import java.awt.Component;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;
import javax.swing.table.TableCellRenderer;

import com.nn.ishop.client.config.SystemConfiguration;
import com.nn.ishop.client.util.Library;
import com.nn.ishop.server.util.Formatter;

public class CTableCellRenderer extends CLabel implements TableCellRenderer
{
	private static final long serialVersionUID = 4544568725746060125L;

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {		
		setOpaque(true);	
		if(column == 0)
			setHorizontalAlignment(CLabel.LEFT);
		if (row % 2 == 0) {
			setBackground(Library.C_ACTIVE_ROW);								
		} else {
				setBackground(Library.C_SECOND_ROW);
		}
	
		if (isSelected)
			setBackground(table.getSelectionBackground());
		if(hasFocus){	
			if(table.isCellEditable(row, column)){
				setBorder(BorderFactory.createCompoundBorder(new DashedBorder(Color.BLUE), 
						BorderFactory.createEmptyBorder(0, 2, 0, 2)));
				setBackground(Color.WHITE);
			}else
				setBorder(BorderFactory.createCompoundBorder(new DashedBorder(Color.BLACK), 
						BorderFactory.createEmptyBorder(0, 2, 0, 2)));
		}else
			setBorder(new CLineBorder(Library.TBL_BORDER_COLOR, CLineBorder.DRAW_BOTTOM_RIGHT_BORDER));
			//setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 2));
		
		if(value != null){
			Class<?> c = table.getColumnClass(column);
			if(c.equals(Double.class)){
				setText(SystemConfiguration.decfm.format(Double.parseDouble(value.toString())));
			}else if(value instanceof  Date){
				setText(Formatter.date2str((Date)value));				
			}else{
				setText(value.toString());
			}
		}else
			// Do nothing
			setText(null);
		return this;
	}
}