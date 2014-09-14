package com.nn.ishop.client.gui.components;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

public class CButtonRenderer extends C20x20Button implements TableCellRenderer {
	private static final long serialVersionUID = 1L;

	public CButtonRenderer() {
		    setOpaque(true);
		  }

		  public Component getTableCellRendererComponent(JTable table, Object value,
		      boolean isSelected, boolean hasFocus, int row, int column) {
		    if (isSelected) {
		      setForeground(table.getSelectionForeground());
		      setBackground(table.getSelectionBackground());
		    } else {
		      setForeground(table.getForeground());
		      setBackground(UIManager.getColor("Button.background"));
		    }
		    setText((value == null) ? "" : value.toString());
		    return this;
		  }
		}
