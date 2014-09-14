package test.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class TableDialogEditDemo extends JPanel {
	public class ColorEditor extends AbstractCellEditor implements
			TableCellEditor, ActionListener {
		Color currentColor;
		JButton button;
		JColorChooser colorChooser;
		JDialog dialog;
		protected static final String EDIT = "edit";

		public ColorEditor() {
			// Set up the editor (from the table's point of view), which is a
			// button.
			// This button brings up the color chooser dialog, which is the
			// editor from the user's point of view.
			button = new JButton();
			button.setActionCommand(EDIT);
			button.addActionListener(this);
			button.setBorderPainted(false);

			// Set up the dialog that the button brings up.
			colorChooser = new JColorChooser();
			dialog = JColorChooser.createDialog(button, "Pick a Color", true, // modal
					colorChooser, this, // OK button handler
					null); // no CANCEL button handler
		}

		/**
		 * Handles events from the editor button and from the dialog's OK
		 * button.
		 */
		public void actionPerformed(ActionEvent e) {
			if (EDIT.equals(e.getActionCommand())) {
				// The user has clicked the cell, so bring up the dialog.
				button.setBackground(currentColor);
				colorChooser.setColor(currentColor);
				dialog.setVisible(true);

				// Make the renderer reappear.
				fireEditingStopped();

			} else { // User pressed dialog's "OK" button
				currentColor = colorChooser.getColor();
			}
		}

		public Object getCellEditorValue() {
			return currentColor;
		}

		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			currentColor = (Color) value;
			return button;
		}
	}

	public class ColorRenderer extends JLabel implements TableCellRenderer {
		Border unselectedBorder = null;
		Border selectedBorder = null;
		boolean isBordered = true;

		public ColorRenderer(boolean isBordered) {
			this.isBordered = isBordered;
			setOpaque(true);
		}

		public Component getTableCellRendererComponent(JTable table,
				Object color, boolean isSelected, boolean hasFocus, int row,
				int column) {
			Color newColor = (Color) color;
			setBackground(newColor);
			if (isBordered) {
				if (isSelected) {
					if (selectedBorder == null) {
						selectedBorder = BorderFactory.createMatteBorder(2, 5,
								2, 5, table.getSelectionBackground());
					}
					setBorder(selectedBorder);
				} else {
					if (unselectedBorder == null) {
						unselectedBorder = BorderFactory.createMatteBorder(2,
								5, 2, 5, table.getBackground());
					}
					setBorder(unselectedBorder);
				}
			}
			return this;
		}
	}

	public TableDialogEditDemo() {
		super(new GridLayout());

		JTextField tf1 = new JTextField("tf1");
		add(tf1);
		JTextField tf2 = new JTextField("tf2");
		add(tf2);

		JTable table = new JTable(new MyTableModel());
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);

		JScrollPane scrollPane = new JScrollPane(table);

		table.setDefaultRenderer(Color.class, new ColorRenderer(true));
		table.setDefaultEditor(Color.class, new ColorEditor());

		add(scrollPane);
	}

	class MyTableModel extends AbstractTableModel {
		private String[] columnNames = { "First Name", "Favorite Color",
				"Sport", "# of Years", "Vegetarian" };
		private Object[][] data = {
				{ "Mary", new Color(153, 0, 153), "Snowboarding",
						new Integer(5), new Boolean(false) },
				{ "Alison", new Color(51, 51, 153), "Rowing", new Integer(3),
						new Boolean(true) },
				{ "Kathy", new Color(51, 102, 51), "Knitting", new Integer(2),
						new Boolean(false) },
				{ "Sharon", Color.red, "Speed reading", new Integer(20),
						new Boolean(true) },
				{ "Philip", Color.pink, "Pool", new Integer(10),
						new Boolean(false) } };

		public int getColumnCount() {
			return columnNames.length;
		}

		public int getRowCount() {
			return data.length;
		}

		public String getColumnName(int col) {
			return columnNames[col];
		}

		public Object getValueAt(int row, int col) {
			return data[row][col];
		}

		public Class getColumnClass(int c) {
			return getValueAt(0, c).getClass();
		}

		public boolean isCellEditable(int row, int col) {
			if (col < 1) {
				return false;
			} else {
				return true;
			}
		}

		public void setValueAt(Object value, int row, int col) {
			data[row][col] = value;
			fireTableCellUpdated(row, col);
		}
	}

	private static void createAndShowGUI() {
		JFrame frame = new JFrame("TableDialogEditDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JComponent newContentPane = new TableDialogEditDemo();
		newContentPane.setOpaque(true);
		frame.setContentPane(newContentPane);

		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}