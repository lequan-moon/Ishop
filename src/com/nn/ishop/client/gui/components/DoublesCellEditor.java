package com.nn.ishop.client.gui.components;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.EventObject;

import javax.swing.AbstractAction;
import javax.swing.DefaultCellEditor;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

public class DoublesCellEditor extends DefaultCellEditor {
	private static final long serialVersionUID = 1L;
	private JFormattedTextField textField;
    NumberFormat dFormat;

    public DoublesCellEditor(JFormattedTextField jft) {
        super(jft);
        this.textField = jft;
        dFormat  = NumberFormat.getIntegerInstance();
        NumberFormatter intFormatter = new NumberFormatter(dFormat);
        intFormatter.setFormat(dFormat);
        textField.setFormatterFactory(
                new DefaultFormatterFactory(intFormatter));
        textField.setHorizontalAlignment(JTextField.TRAILING);
        textField.setFocusLostBehavior(JFormattedTextField.PERSIST);

        //React when the user presses Enter while the editor is
        //active.  (Tab is handled as specified by
        //JFormattedTextField's focusLostBehavior property.)
        textField.getInputMap().put(KeyStroke.getKeyStroke(
                                        KeyEvent.VK_ENTER, 0),
                                        "check");
        textField.getActionMap().put("check", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
		if (!textField.isEditValid()) { //The text is invalid.
                    	textField.postActionEvent(); //inform the editor
                } else try {              //The text is valid,
                	textField.commitEdit();     //so use it.
                    textField.postActionEvent(); //stop editing
                } catch (java.text.ParseException exc) { }
            }
        });
        super.delegate = new EditorDelegate() {            
			private static final long serialVersionUID = 1L;

			public void setValue(Object value) {
                textField.setValue(value != null ? ((Number) value).doubleValue() : value);
            }

            public Object getCellEditorValue() {
                Object value = textField.getValue();
                return value != null ? ((Number) value).doubleValue() : value;
            }
        };
    }

	@Override
	public Object getCellEditorValue() {
		 JFormattedTextField ftf = (JFormattedTextField)getComponent();
	        Object o = ftf.getValue();
	        if (o instanceof Double) {
	            return o;
	        } else if (o instanceof Number) {
	            return new Double(((Number)o).intValue());
	        } else {
	            try {
	                return dFormat.parseObject(o.toString());
	            } catch (ParseException exc) {
	                System.err.println("getCellEditorValue: can't parse o: " + o);
	                return null;
	            }
	        }
	}

	

	@Override
	public boolean stopCellEditing() {
		JFormattedTextField ftf = (JFormattedTextField)getComponent();
        if (ftf.isEditValid()) {
            try {
                ftf.commitEdit();
            } catch (java.text.ParseException exc) { }	    
        }         
        return super.stopCellEditing();
	}

}
