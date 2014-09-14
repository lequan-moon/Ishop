package test.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.util.EventObject;
import java.util.Vector;

import javax.swing.CellEditor;
import javax.swing.FocusManager;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.EventListenerList;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import com.nn.ishop.client.util.Util;

public class TabbableCurrencyTable {
	public static void main(String[] args) {
	    try {
	        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
	    } catch (Exception evt) {}
	  
	    JFrame f = new JFrame("Tabbable Currency Table");

	    TabEditTable tbl = new TabEditTable(
	        new TestUpdatableCurrencyTableModel());
	    tbl.setDefaultRenderer(java.lang.Number.class,
	        new FractionCellRenderer(10, 3, SwingConstants.RIGHT));

	    TableColumnModel tcm = tbl.getColumnModel();
	    tcm.getColumn(0).setPreferredWidth(150);
	    tcm.getColumn(0).setMinWidth(150);
	    TextWithIconCellRenderer renderer = new TextWithIconCellRenderer();
	    tcm.getColumn(0).setCellRenderer(renderer);
	    tbl.setShowHorizontalLines(false);
	    tbl.setIntercellSpacing(new Dimension(1, 0));

	    // Add the stripe renderer in the leftmost four columns.
	    StripedTableCellRenderer.installInColumn(tbl, 0, Color.lightGray,
	        Color.white, null, null);
	    StripedTableCellRenderer.installInColumn(tbl, 1, Color.lightGray,
	        Color.white, null, null);
	    StripedTableCellRenderer.installInColumn(tbl, 2, Color.lightGray,
	        Color.white, null, null);
	    StripedTableCellRenderer.installInColumn(tbl, 3, Color.lightGray,
	        Color.white, null, null);

	    // Add the highlight renderer to the difference column.
	    // The following comparator makes it highlight
	    // cells with negative numeric values.
	    Comparator cmp = new Comparator() {
	      public boolean shouldHighlight(JTable tbl, Object value, int row,
	          int column) {
	        if (value instanceof Number) {
	          double columnValue = ((Number) value).doubleValue();
	          return columnValue < (double) 0.0;
	        }
	        return false;
	      }
	    };
	    tcm.getColumn(3).setCellRenderer(
	        new HighlightRenderer(cmp, null, Color.pink, Color.black,
	            Color.pink.darker(), Color.white));

	    // Install a button renderer in the last column
	    ButtonRenderer2 buttonRenderer = new ButtonRenderer2();
	    buttonRenderer.setForeground(Color.blue);
	    buttonRenderer.setBackground(Color.lightGray);
	    tcm.getColumn(4).setCellRenderer(buttonRenderer);

	    // Install a button editor in the last column
	    TableCellEditor editor = new ButtonEditor(new JButton());
	    tcm.getColumn(4).setCellEditor(editor);

	    // Install the list of columns containing tabbable editors
	    tbl.setEditingColumns(new int[] { 1, 2 });

	    // Make the rows wide enough to take the buttons
	    tbl.setRowHeight(20);

	    tbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	    tbl.setPreferredScrollableViewportSize(tbl.getPreferredSize());

	    JScrollPane sp = new JScrollPane(tbl);
	    f.getContentPane().add(sp, "Center");
	    f.pack();
	    f.addWindowListener(new WindowAdapter() {
	      public void windowClosing(WindowEvent evt) {
	        System.exit(0);
	      }
	    });
	    f.setVisible(true);
	  }
	}

	class TabEditTable extends JTable {
	  public TabEditTable() {
	    super();
	  }

	  public TabEditTable(TableModel dm) {
	    super(dm);
	  }

	  public TabEditTable(TableModel dm, TableColumnModel cm) {
	    super(dm, cm);
	  }

	  public TabEditTable(TableModel dm, TableColumnModel cm,
	      ListSelectionModel sm) {
	    super(dm, cm, sm);
	  }

	  public TabEditTable(int numRows, int numColumns) {
	    super(numRows, numColumns);
	  }

	  public TabEditTable(final Vector rowData, final Vector columnNames) {
	    super(rowData, columnNames);
	  }

	  public TabEditTable(final Object[][] rowData, final Object[] columnNames) {
	    super(rowData, columnNames);
	  }

	  // Set the columns that contain tabbable editors
	  public void setEditingColumns(int[] columns) {
	    editingColumns = columns;
	    convertEditableColumnsToView();
	  }

	  public int[] getEditingColumns() {
	    return editingColumns;
	  }

	  // Overrides of JTable methods
	  public boolean editCellAt(int row, int column, EventObject evt) {
	    if (super.editCellAt(row, column, evt) == false) {
	      return false;
	    }

	    if (viewEditingColumns != null) {
	      // Note: column is specified in terms of the column model
	      int length = viewEditingColumns.length;
	      for (int i = 0; i < length; i++) {
	        if (column == viewEditingColumns[i]) {
	          Component comp = getEditorComponent();
	          comp.addKeyListener(tabKeyListener);
	          this.addKeyListener(tabKeyListener);
	          focusManager = FocusManager.getCurrentManager();
	          FocusManager.disableSwingFocusManager();
	          inTabbingEditor = true;
	          comp.requestFocus();
	          break;
	        }
	      }
	    }

	    return true;
	  }

	  public void editingStopped(ChangeEvent evt) {
	    if (inTabbingEditor == true) {
	      Component comp = getEditorComponent();
	      comp.removeKeyListener(tabKeyListener);
	      this.removeKeyListener(tabKeyListener);
	      FocusManager.setCurrentManager(focusManager);

	      inTabbingEditor = false;
	    }

	    super.editingStopped(evt);
	  }

	  protected void convertEditableColumnsToView() {
	    // Convert the editable columns to view column numbers
	    if (editingColumns == null) {
	      viewEditingColumns = null;
	      return;
	    }

	    // Create a set of editable columns in terms of view
	    // column numbers in ascending order. Note that not all
	    // editable columns in the data model need be visible.
	    int length = editingColumns.length;
	    viewEditingColumns = new int[length];
	    int nextSlot = 0;

	    for (int i = 0; i < length; i++) {
	      int viewIndex = convertColumnIndexToView(editingColumns[i]);
	      if (viewIndex != -1) {
	        viewEditingColumns[nextSlot++] = viewIndex;
	      }
	    }

	    // Now create an array of the right length to hold the view indices
	    if (nextSlot < length) {
	      int[] tempArray = new int[nextSlot];
	      System.arraycopy(viewEditingColumns, 0, tempArray, 0, nextSlot);
	      viewEditingColumns = tempArray;
	    }

	    // Finally, sort the view columns into order
	    TableUtilities.sort(viewEditingColumns);
	  }

	  protected void moveToNextEditor(int row, int column, boolean forward) {
	    // Column is specified in terms of the column model
	    if (viewEditingColumns != null) {
	      int length = viewEditingColumns.length;

	      // Move left-to-right or right-to-left across the table
	      for (int i = 0; i < length; i++) {
	        if (viewEditingColumns[i] == column) {
	          // Select the next column to edit
	          if (forward == true) {
	            if (++i == length) {
	              // Reached end of row - wrap
	              i = 0;
	              row++;
	              if (row == getRowCount()) {
	                // End of table - wrap
	                row = 0;
	              }
	            }
	          } else {
	            if (--i < 0) {
	              i = length - 1;
	              row--;
	              if (row < 0) {
	                row = getRowCount() - 1;
	              }
	            }
	          }
	          final int newRow = row;
	          final int newColumn = viewEditingColumns[i];

	          // Start editing at new location
	          SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	              editCellAt(newRow, newColumn);
	              ListSelectionModel rowSel = getSelectionModel();
	              ListSelectionModel columnSel = getColumnModel()
	                  .getSelectionModel();
	              rowSel.setSelectionInterval(newRow, newRow);
	              columnSel
	                  .setSelectionInterval(newColumn, newColumn);
	            }
	          });
	          break;
	        }
	      }
	    }
	  }

	  // Catch changes to the table column model
	  public void columnAdded(TableColumnModelEvent e) {
	    super.columnAdded(e);
	    convertEditableColumnsToView();
	  }

	  public void columnRemoved(TableColumnModelEvent e) {
	    super.columnRemoved(e);
	    convertEditableColumnsToView();
	  }

	  public void columnMoved(TableColumnModelEvent e) {
	    super.columnMoved(e);
	    convertEditableColumnsToView();
	  }

	  public class TabKeyListener extends KeyAdapter {
	    public void keyPressed(KeyEvent evt) {
	      if (evt.getKeyCode() == KeyEvent.VK_TAB) {
	        if (inTabbingEditor == true) {
	          TableCellEditor editor = getCellEditor();
	          int editRow = getEditingRow();
	          int editColumn = getEditingColumn();
	          if (editor != null) {
	            boolean stopped = editor.stopCellEditing();
	            if (stopped == true) {
	              boolean forward = (evt.isShiftDown() == false);
	              moveToNextEditor(editRow, editColumn, forward);
	            }
	          }
	        }
	      }
	    }
	  }

	  protected boolean inTabbingEditor;

	  protected FocusManager focusManager;

	  protected int[] editingColumns; // Model columns

	  protected int[] viewEditingColumns; // View columns

	  protected TabKeyListener tabKeyListener = new TabKeyListener();
	}

	class FractionCellRenderer extends DefaultTableCellRenderer {
	  public FractionCellRenderer(int integer, int fraction, int align) {
	    this.integer = integer; // maximum integer digits
	    this.fraction = fraction; // exact number of fraction digits
	    this.align = align; // alignment (LEFT, CENTER, RIGHT)
	  }

	  protected void setValue(Object value) {
	    if (value != null && value instanceof Number) {
	      formatter.setMaximumIntegerDigits(integer);
	      formatter.setMaximumFractionDigits(fraction);
	      formatter.setMinimumFractionDigits(fraction);
	      setText(formatter.format(((Number) value).doubleValue()));
	    } else {
	      super.setValue(value);
	    }
	    setHorizontalAlignment(align);
	  }

	  protected int integer;

	  protected int fraction;

	  protected int align;

	  protected static NumberFormat formatter = NumberFormat.getInstance();
	}

	class TextWithIconCellRenderer extends DefaultTableCellRenderer {
	  protected void setValue(Object value) {
	    if (value instanceof DataWithIcon) {
	      if (value != null) {
	        DataWithIcon d = (DataWithIcon) value;
	        Object dataValue = d.getData();

	        setText(dataValue == null ? "" : dataValue.toString());
	        setIcon(d.getIcon());
	        setHorizontalTextPosition(SwingConstants.RIGHT);
	        setVerticalTextPosition(SwingConstants.CENTER);
	        setHorizontalAlignment(SwingConstants.LEFT);
	        setVerticalAlignment(SwingConstants.CENTER);
	      } else {
	        setText("");
	        setIcon(null);
	      }
	    } else {
	      super.setValue(value);
	    }
	  }
	}

	class DataWithIcon {
	  public DataWithIcon(Object data, Icon icon) {
	    this.data = data;
	    this.icon = icon;
	  }

	  public Icon getIcon() {
	    return icon;
	  }

	  public Object getData() {
	    return data;
	  }

	  public String toString() {
	    return data.toString();
	  }

	  protected Icon icon;

	  protected Object data;
	}

	class TableUtilities {
	  // Calculate the required width of a table column
	  public static int calculateColumnWidth(JTable table, int columnIndex) {
	    int width = 0; // The return value
	    int rowCount = table.getRowCount();

	    for (int i = 0; i < rowCount; i++) {
	      TableCellRenderer renderer = table.getCellRenderer(i, columnIndex);
	      Component comp = renderer.getTableCellRendererComponent(table,
	          table.getValueAt(i, columnIndex), false, false, i,
	          columnIndex);
	      int thisWidth = comp.getPreferredSize().width;
	      if (thisWidth > width) {
	        width = thisWidth;
	      }
	    }

	    return width;
	  }

	  // Set the widths of every column in a table
	  public static void setColumnWidths(JTable table, Insets insets,
	      boolean setMinimum, boolean setMaximum) {
	    int columnCount = table.getColumnCount();
	    TableColumnModel tcm = table.getColumnModel();
	    int spare = (insets == null ? 0 : insets.left + insets.right);

	    for (int i = 0; i < columnCount; i++) {
	      int width = calculateColumnWidth(table, i);
	      width += spare;

	      TableColumn column = tcm.getColumn(i);
	      column.setPreferredWidth(width);
	      if (setMinimum == true) {
	        column.setMinWidth(width);
	      }
	      if (setMaximum == true) {
	        column.setMaxWidth(width);
	      }
	    }
	  }

	  // Sort an array of integers in place
	  public static void sort(int[] values) {
	    int length = values.length;
	    if (length > 1) {
	      for (int i = 0; i < length - 1; i++) {
	        for (int j = i + 1; j < length; j++) {
	          if (values[j] < values[i]) {
	            int temp = values[i];
	            values[i] = values[j];
	            values[j] = temp;
	          }
	        }
	      }
	    }
	  }
	}

	class StripedTableCellRenderer implements TableCellRenderer {
	  public StripedTableCellRenderer(TableCellRenderer targetRenderer,
	      Color evenBack, Color evenFore, Color oddBack, Color oddFore) {
	    this.targetRenderer = targetRenderer;
	    this.evenBack = evenBack;
	    this.evenFore = evenFore;
	    this.oddBack = oddBack;
	    this.oddFore = oddFore;
	  }

	  // Implementation of TableCellRenderer interface
	  public Component getTableCellRendererComponent(JTable table, Object value,
	      boolean isSelected, boolean hasFocus, int row, int column) {
	    TableCellRenderer renderer = targetRenderer;
	    if (renderer == null) {
	      // Get default renderer from the table
	      renderer = table.getDefaultRenderer(table.getColumnClass(column));
	    }

	    // Let the real renderer create the component
	    Component comp = renderer.getTableCellRendererComponent(table, value,
	        isSelected, hasFocus, row, column);

	    // Now apply the stripe effect
	    if (isSelected == false && hasFocus == false) {
	      if ((row & 1) == 0) {
	        comp.setBackground(evenBack != null ? evenBack : table
	            .getBackground());
	        comp.setForeground(evenFore != null ? evenFore : table
	            .getForeground());
	      } else {
	        comp.setBackground(oddBack != null ? oddBack : table
	            .getBackground());
	        comp.setForeground(oddFore != null ? oddFore : table
	            .getForeground());
	      }
	    }

	    return comp;
	  }

	  // Convenience method to apply this renderer to single column
	  public static void installInColumn(JTable table, int columnIndex,
	      Color evenBack, Color evenFore, Color oddBack, Color oddFore) {
	    TableColumn tc = table.getColumnModel().getColumn(columnIndex);

	    // Get the cell renderer for this column, if any
	    TableCellRenderer targetRenderer = tc.getCellRenderer();

	    // Create a new StripedTableCellRenderer and install it
	    tc.setCellRenderer(new StripedTableCellRenderer(targetRenderer,
	        evenBack, evenFore, oddBack, oddFore));
	  }

	  // Convenience method to apply this renderer to an entire table
	  public static void installInTable(JTable table, Color evenBack,
	      Color evenFore, Color oddBack, Color oddFore) {
	    StripedTableCellRenderer sharedInstance = null;
	    int columns = table.getColumnCount();
	    for (int i = 0; i < columns; i++) {
	      TableColumn tc = table.getColumnModel().getColumn(i);
	      TableCellRenderer targetRenderer = tc.getCellRenderer();
	      if (targetRenderer != null) {
	        // This column has a specific renderer
	        tc.setCellRenderer(new StripedTableCellRenderer(targetRenderer,
	            evenBack, evenFore, oddBack, oddFore));
	      } else {
	        // This column uses a class renderer - use a shared renderer
	        if (sharedInstance == null) {
	          sharedInstance = new StripedTableCellRenderer(null,
	              evenBack, evenFore, oddBack, oddFore);
	        }
	        tc.setCellRenderer(sharedInstance);
	      }
	    }
	  }

	  protected TableCellRenderer targetRenderer;

	  protected Color evenBack;

	  protected Color evenFore;

	  protected Color oddBack;

	  protected Color oddFore;
	}

	class HighlightRenderer implements TableCellRenderer {
	  public HighlightRenderer(Comparator cmp, TableCellRenderer targetRenderer,
	      Color backColor, Color foreColor, Color highlightBack,
	      Color highlightFore) {
	    this.cmp = cmp;
	    this.targetRenderer = targetRenderer;
	    this.backColor = backColor;
	    this.foreColor = foreColor;
	    this.highlightBack = highlightBack;
	    this.highlightFore = highlightFore;
	  }

	  public Component getTableCellRendererComponent(JTable tbl, Object value,
	      boolean isSelected, boolean hasFocus, int row, int column) {
	    TableCellRenderer renderer = targetRenderer;
	    if (renderer == null) {
	      renderer = tbl.getDefaultRenderer(tbl.getColumnClass(column));
	    }
	    Component comp = renderer.getTableCellRendererComponent(tbl, value,
	        isSelected, hasFocus, row, column);
	    if (isSelected == false && hasFocus == false && value != null) {
	      if (cmp.shouldHighlight(tbl, value, row, column)) {
	        comp.setForeground(highlightFore);
	        comp.setBackground(highlightBack);
	      } else {
	        comp.setForeground(foreColor);
	        comp.setBackground(backColor);
	      }
	    }

	    return comp;
	  }

	  protected Comparator cmp;

	  protected TableCellRenderer targetRenderer;

	  protected Color backColor;

	  protected Color foreColor;

	  protected Color highlightBack;

	  protected Color highlightFore;
	}

	interface Comparator {
	  public abstract boolean shouldHighlight(JTable tbl, Object value, int row,
	      int column);
	}

	class ButtonRenderer extends JButton implements TableCellRenderer {
	  public ButtonRenderer() {
	    this.border = getBorder();
	    this.setOpaque(true);
	  }

	  public void setForeground(Color foreground) {
	    this.foreground = foreground;
	    super.setForeground(foreground);
	  }

	  public void setBackground(Color background) {
	    this.background = background;
	    super.setBackground(background);
	  }

	  public void setFont(Font font) {
	    this.font = font;
	    super.setFont(font);
	  }

	  public Component getTableCellRendererComponent(JTable table, Object value,
	      boolean isSelected, boolean hasFocus, int row, int column) {
	    Color cellForeground = foreground != null ? foreground : table
	        .getForeground();
	    Color cellBackground = background != null ? background : table
	        .getBackground();

	    setFont(font != null ? font : table.getFont());

	    if (hasFocus) {
	      setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
	      if (table.isCellEditable(row, column)) {
	        cellForeground = UIManager
	            .getColor("Table.focusCellForeground");
	        cellBackground = UIManager
	            .getColor("Table.focusCellBackground");
	      }
	    } else {
	      setBorder(border);
	    }

	    super.setForeground(cellForeground);
	    super.setBackground(cellBackground);

	    // Customize the component's appearance
	    setValue(value);

	    return this;
	  }

	  protected void setValue(Object value) {
	    if (value == null) {
	      setText("");
	      setIcon(null);
	    } else if (value instanceof Icon) {
	      setText("");
	      setIcon((Icon) value);
	    } else if (value instanceof DataWithIcon) {
	      DataWithIcon d = (DataWithIcon) value;
	      setText(d.toString());
	      setIcon(d.getIcon());
	    } else {
	      setText(value.toString());
	      setIcon(null);
	    }
	  }

	  protected Color foreground;

	  protected Color background;

	  protected Font font;

	  protected Border border;
	}

	class ButtonEditor extends BasicCellEditor implements ActionListener,
	    TableCellEditor {
	  public ButtonEditor(JButton button) {
	    super(button);
	    button.addActionListener(this);
	  }

	  public void setForeground(Color foreground) {
	    this.foreground = foreground;
	    editor.setForeground(foreground);
	  }

	  public void setBackground(Color background) {
	    this.background = background;
	    editor.setBackground(background);
	  }

	  public void setFont(Font font) {
	    this.font = font;
	    editor.setFont(font);
	  }

	  public Object getCellEditorValue() {
	    return value;
	  }

	  public void editingStarted(EventObject event) {
	    // Edit starting - click the button if necessary
	    if (!(event instanceof MouseEvent)) {
	      // Keyboard event - click the button
	      SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
	          ((JButton) editor).doClick();
	        }
	      });
	    }
	  }

	  public Component getTableCellEditorComponent(JTable tbl, Object value,
	      boolean isSelected, int row, int column) {
	    editor.setForeground(foreground != null ? foreground : tbl
	        .getForeground());
	    editor.setBackground(background != null ? background : tbl
	        .getBackground());
	    editor.setFont(font != null ? font : tbl.getFont());

	    this.value = value;
	    setValue(value);
	    return editor;
	  }

	  protected void setValue(Object value) {
	    JButton button = (JButton) editor;
	    if (value == null) {
	      button.setText("");
	      button.setIcon(null);
	    } else if (value instanceof Icon) {
	      button.setText("");
	      button.setIcon((Icon) value);
	    } else if (value instanceof DataWithIcon) {
	      DataWithIcon d = (DataWithIcon) value;
	      button.setText(d.toString());
	      button.setIcon(d.getIcon());
	    } else {
	      button.setText(value.toString());
	      button.setIcon(null);
	    }
	  }

	  public void actionPerformed(ActionEvent evt) {
	    // Button pressed - stop the edit
	    stopCellEditing();
	  }

	  protected Object value;

	  protected Color foreground;

	  protected Color background;

	  protected Font font;
	}

	class BasicCellEditor implements CellEditor, PropertyChangeListener {
	  public BasicCellEditor() {
	    this.editor = null;
	  }

	  public BasicCellEditor(JComponent editor) {
	    this.editor = editor;
	    editor.addPropertyChangeListener(this);
	  }

	  public Object getCellEditorValue() {
	    return null;
	  }

	  public boolean isCellEditable(EventObject evt) {
	    editingEvent = evt;
	    return true;
	  }

	  public boolean shouldSelectCell(EventObject evt) {
	    return true;
	  }

	  public boolean stopCellEditing() {
	    fireEditingStopped();
	    return true;
	  }

	  public void cancelCellEditing() {
	    fireEditingCanceled();
	  }

	  public void addCellEditorListener(CellEditorListener l) {
	    listeners.add(CellEditorListener.class, l);
	  }

	  public void removeCellEditorListener(CellEditorListener l) {
	    listeners.remove(CellEditorListener.class, l);
	  }

	  // Returns the editing component
	  public JComponent getComponent() {
	    return editor;
	  }

	  // Sets the editing component
	  public void setComponent(JComponent comp) {
	    editor = comp;
	  }

	  // Returns the event that triggered the edit
	  public EventObject getEditingEvent() {
	    return editingEvent;
	  }

	  // Method invoked when the editor is installed in the table.
	  // Overridden in derived classes to take any convenient
	  // action.
	  public void editingStarted(EventObject event) {
	  }

	  protected void fireEditingStopped() {
	    Object[] l = listeners.getListenerList();
	    for (int i = l.length - 2; i >= 0; i -= 2) {
	      if (l[i] == CellEditorListener.class) {
	        if (changeEvent == null) {
	          changeEvent = new ChangeEvent(this);
	        }
	        ((CellEditorListener) l[i + 1]).editingStopped(changeEvent);
	      }
	    }
	  }

	  protected void fireEditingCanceled() {
	    Object[] l = listeners.getListenerList();
	    for (int i = l.length - 2; i >= 0; i -= 2) {
	      if (l[i] == CellEditorListener.class) {
	        if (changeEvent == null) {
	          changeEvent = new ChangeEvent(this);
	        }
	        ((CellEditorListener) l[i + 1]).editingCanceled(changeEvent);
	      }
	    }
	  }

	  // Implementation of the PropertyChangeListener interface
	  public void propertyChange(PropertyChangeEvent evt) {
	    if (evt.getPropertyName().equals("ancestor")
	        && evt.getNewValue() != null) {
	      // Added to table - notify the editor
	      editingStarted(editingEvent);
	    }
	  }

	  protected static JCheckBox checkBox = new JCheckBox();

	  protected static ChangeEvent changeEvent;

	  protected JComponent editor;

	  protected EventListenerList listeners = new EventListenerList();

	  protected EventObject editingEvent;
	}

	class TestUpdatableCurrencyTableModel extends UpdatableCurrencyTableModel {
	  public void updateTable(Object value, int row, int column) {
	  }
	}

	abstract class UpdatableCurrencyTableModel extends EditableCurrencyTableModel {
	  public int getColumnCount() {
	    return super.getColumnCount() + 1;
	  }

	  public Object getValueAt(int row, int column) {
	    if (column == BUTTON_COLUMN) {
	      return "Update";
	    }
	    return super.getValueAt(row, column);
	  }

	  public Class getColumnClass(int column) {
	    if (column == BUTTON_COLUMN) {
	      return String.class;
	    }
	    return super.getColumnClass(column);
	  }

	  public String getColumnName(int column) {
	    if (column == BUTTON_COLUMN) {
	      return "";
	    }
	    return super.getColumnName(column);
	  }

	  public boolean isCellEditable(int row, int column) {
	    return column == BUTTON_COLUMN || super.isCellEditable(row, column);
	  }

	  public void setValueAt(Object value, int row, int column) {
	    if (column == BUTTON_COLUMN) {
	      // Button press - do whatever is needed to update the table source
	      updateTable(value, row, column);
	      return;
	    }

	    // Other columns - use superclass
	    super.setValueAt(value, row, column);
	  }

	  // Used to implement the table update
	  protected abstract void updateTable(Object value, int row, int column);

	  protected static final int BUTTON_COLUMN = 4;
	}

	class EditableCurrencyTableModel extends CurrencyTableModel {
	  public boolean isCellEditable(int row, int column) {
	    return column == OLD_RATE_COLUMN || column == NEW_RATE_COLUMN;
	  }

	  public void setValueAt(Object value, int row, int column) {
	    try {
	      if (column == OLD_RATE_COLUMN || column == NEW_RATE_COLUMN) {
	        Double newObjectValue; // New value as an Object
	        double newValue; // double, for validity checking

	        if (value instanceof Number) {
	          // Convert Number to Double
	          newValue = ((Number) value).doubleValue();
	          newObjectValue = new Double(newValue);
	        } else if (value instanceof String) {
	          // Convert a String to a Double
	          newObjectValue = new Double((String) value);
	          newValue = newObjectValue.doubleValue();
	        } else {
	          // Unrecognized - ignore
	          return;
	        }

	        if (newValue > (double) 0.0) {
	          // Store new value, but reject zero or negative values
	          data[row][column] = newObjectValue;
	          data[row][DIFF_COLUMN] = new Double(
	              ((Double) data[row][NEW_RATE_COLUMN]).doubleValue()
	                  - ((Double) data[row][OLD_RATE_COLUMN])
	                      .doubleValue());

	          fireTableRowsUpdated(row, row);
	        }
	      }
	    } catch (NumberFormatException e) {
	      // Ignore a badly-formatted number
	    }
	  }
	}

	class CurrencyTableModel extends AbstractTableModel {
	  protected String[] columnNames = { "Currency", "Yesterday", "Today",
	      "Change" };

	  // Constructor: calculate currency change to create the last column
	  public CurrencyTableModel() {
	    for (int i = 0; i < data.length; i++) {
	      data[i][DIFF_COLUMN] = new Double(
	          ((Double) data[i][NEW_RATE_COLUMN]).doubleValue()
	              - ((Double) data[i][OLD_RATE_COLUMN]).doubleValue());
	    }
	  }

	  // Implementation of TableModel interface
	  public int getRowCount() {
	    return data.length;
	  }

	  public int getColumnCount() {
	    return COLUMN_COUNT;
	  }

	  public Object getValueAt(int row, int column) {
	    return data[row][column];
	  }

	  public Class getColumnClass(int column) {
	    return (data[0][column]).getClass();
	  }

	  public String getColumnName(int column) {
	    return columnNames[column];
	  }

	  protected static final int OLD_RATE_COLUMN = 1;

	  protected static final int NEW_RATE_COLUMN = 2;

	  protected static final int DIFF_COLUMN = 3;

	  protected static final int COLUMN_COUNT = 4;

	  protected static final Class thisClass = CurrencyTableModel.class;

	  protected Object[][] data = new Object[][] {
	      {
	          new DataWithIcon("Belgian Franc", Util.getIcon("logo16.png")),
	          new Double(37.6460110), new Double(37.6508921), null },
	          
	      {
	          new DataWithIcon("British Pound", Util.getIcon("logo16.png")),
	          new Double(0.6213051), new Double(0.6104102), null },
	          
	      {
	          new DataWithIcon("Canadian Dollar", Util.getIcon("logo16.png")),
	          new Double(1.4651209), new Double(1.5011104), null }
	      };
	}
