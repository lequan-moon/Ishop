package com.nn.ishop.client.gui.components;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import com.nn.ishop.server.util.Formatter;
import com.toedter.calendar.JDateChooser;

public class CDateChooserTableCellEditor extends AbstractCellEditor implements
		TableCellEditor, ActionListener {
	private static final long serialVersionUID = 1L;
	String currentDateStr;
    JDateChooser dateChooser;
    
	public CDateChooserTableCellEditor() {
		dateChooser = new JDateChooser();	
	}

	public Object getCellEditorValue() {
		return Formatter.date2str(dateChooser.getDate());
	}

	public void actionPerformed(ActionEvent e) {
		fireEditingStopped(); 
	}

	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {		
		return dateChooser;
	}
}
