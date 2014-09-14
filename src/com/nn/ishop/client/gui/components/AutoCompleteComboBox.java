package com.nn.ishop.client.gui.components;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * This class use for auto complete task, a predefine list of data 
 * will be show as runtime to help user 
 * know which element of data has been existed.  
 * To use with Swixml it require invoking addItem method
 * @author connect.shift-think.com
 *
 */
public class AutoCompleteComboBox extends JComboBox	implements JComboBox.KeySelectionManager
{
	private static final long serialVersionUID = 4269869064074453322L;
	private String searchFor;
	private long lap;
	public class CBDocument extends PlainDocument
	{
		private static final long serialVersionUID = -3300809158659020984L;

		public void insertString(int offset, String str, AttributeSet a) throws BadLocationException
		{
			if (str==null) return;
			super.insertString(offset, str, a);
			if(!isPopupVisible() && str.length() != 0) fireActionEvent();
		}
	}
	public AutoCompleteComboBox()
	{
		this(new String[]{" "});	
		
	}
	public AutoCompleteComboBox(Object[] items)
	{
		super(items);
		lap = new java.util.Date().getTime();
		setKeySelectionManager(this);
		JTextField tf;
		if(getEditor() != null)
		{
			tf = (JTextField)getEditor().getEditorComponent();
			if(tf != null)
			{
				tf.setDocument(new CBDocument());
				addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent evt)
					{
						JTextField tf = (JTextField)getEditor().getEditorComponent();
						String text = tf.getText();
						ComboBoxModel aModel = getModel();
						String current;
						for(int i = 0; i < aModel.getSize(); i++)
						{
							current = aModel.getElementAt(i).toString();
							if(current.toLowerCase().startsWith(text.toLowerCase()))
							{
								tf.setText(current);
								tf.setSelectionStart(text.length());
								tf.setSelectionEnd(current.length());
								break;
							}
						}
					}
				});
			}
		}
		setOpaque(true);
		setPreferredSize(new Dimension(210,20));
		setEditable(true);
	}
	public String getInputText(){
		JTextField tf = (JTextField)getEditor().getEditorComponent();
		if(tf==null)
			return null;
		else
			return tf.getText();
	}
	public int selectionForKey(char aKey, ComboBoxModel aModel)
	{
		long now = new java.util.Date().getTime();
		if (searchFor!=null && aKey==KeyEvent.VK_BACK_SPACE &&	searchFor.length()>0)
		{
			searchFor = searchFor.substring(0, searchFor.length() -1);
		}
		else
		{			
			if(lap + 1000 < now)
				searchFor = "" + aKey;
			else
				searchFor = searchFor + aKey;
		}
		lap = now;
		String current;
		for(int i = 0; i < aModel.getSize(); i++)
		{
			current = aModel.getElementAt(i).toString().toLowerCase();
			if (current.toLowerCase().startsWith(searchFor.toLowerCase())) return i;
		}
		return -1;
	}
	public void fireActionEvent()
	{
		super.fireActionEvent();
	}	
}
