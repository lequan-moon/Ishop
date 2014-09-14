package com.nn.ishop.client.gui.components;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class CComboCellRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;

	public CComboCellRenderer() {
        setOpaque(false);
        setHorizontalAlignment(SwingConstants.LEFT);
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column) {

        if (value instanceof String[]) {
            //don't actually create a new one every time if you don't need to
            final JComboBox cb = new JComboBox(((String[]) value));
            cb.setEditable(false);
            cb.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    cb.setSelectedIndex(0);
                }
            });
//            setColors(cb, row, isSelected);
            cb.setOpaque(true);
            cb.setFont(table.getFont());
            return cb;
        } else {
            try {
                setText(value.toString());
            } catch (Exception e) {
                setText(null);
            }
        }
        setFont(table.getFont());
        setOpaque(true);
//        setColors(this, row, isSelected);
        return this;
    }
}
