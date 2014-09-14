package com.nn.ishop.client.gui.components;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

public class CComboEditor extends AbstractCellEditor implements TableCellEditor {
	private static final long serialVersionUID = 1L;
	private JComboBox cb = new JComboBox();

	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		if (value instanceof String[]) {
			DefaultComboBoxModel m = new DefaultComboBoxModel((String[]) value);
			cb.setModel(m);
			return cb;
		} else {
			return null;
		}
	}

	public Object getCellEditorValue() {
		return cb.getSelectedItem();
	}
}