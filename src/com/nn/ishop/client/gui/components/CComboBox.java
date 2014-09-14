/*****************************************************************************/
/* File Description  : CComboBox                                             */
/* File Version      : 1.0                                                   */
/* Legal Copyright   : Copyright (c) 2004-2007 by shiftTHINK Ltd. Liab. Co.  */
/* Company Name      : shiftTHINK Ltd. Liab. Co.                             */
/* Original Filename : CComboBox.java                                        */
/* Product Version   : 1.0                                                   */
/* Product Name      : CONNECT Project 							             */
/*****************************************************************************/

package com.nn.ishop.client.gui.components;

import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JComboBox;

import com.nn.ishop.client.gui.dialogs.CConstants;
import com.nn.ishop.client.util.Identifiable;

/**
 * 
 * @author connect.shift-think.com
 *
 */
public class CComboBox extends JComboBox {
	private static final long serialVersionUID = 4747739157353241299L;
	private Hashtable<String,Identifiable> items = new Hashtable<String,Identifiable>();

	
    /**
     * Creates a new CComboBox object.
     */
    public CComboBox() {
        super();        
    }

    /**
     * Creates a new CComboBox object.
     *
     * @param items array of item
     */
    public CComboBox(Object[] items) {
        super();
       setPreferredSize(CConstants.DIM_200X25);
        addItems(items);
    }

    /**
     * add new item
     *
     * @param item an instance of identifiable
     */
    public void addItem(Identifiable item) {
        try {
            if (item.getValue() == "") {
                item.setValue(" "); // to select item with space
            }
            items.put(item.getId(), item);
            super.addItem(item);
        } catch (Exception ex) {
        }
    }

    /**
     * add array of items
     *
     * @param items array of items
     */
    public void addItems(Object[] items) {
        for (int i = 0; i < items.length; i++)
            addItem((Identifiable) items[i]);
    }

    /**
     * set array of ttems
     *
     * @param items array of items
     */
    public void setItems(Object[] items) {
        removeAllItems();
        addItems(items);
    }

    /**
     * set selected item by id
     *
     * @param id id of item
     */
    public void setSelectedItemById(String id) {
        if (id == null) {
            setSelectedIndex(0);
        } else {
        	/** Fix case sensitive compare error  */
            if (items.containsKey(id) 
            		|| items.containsKey(id.toLowerCase()) 
            		|| items.containsKey(id.toUpperCase())) {
                setSelectedItem(items.get(id));
            } else if (getItemCount() != 0) {
                setSelectedIndex(0);
            }
        }
    }

    public CComboBox(Vector<?> items) {
		super(items);
	}

	/**
     * get selected item's id <br/>
     * &emsp;Test
     * @return id of selected item
     */
    public String getSelectedItemId() {
        if (getSelectedItem() == null) {
            return "";
        }

        return ((Identifiable) getSelectedItem()).getId();
    }

    /*class CComboBoxUI extends BasicComboBoxUI {
       protected ComboPopup createPopup() {
         BasicComboPopup popup = new BasicComboPopup( comboBox ) {
           public void show() {
             Dimension popupSize = comboBox.getPopupSize();
             popupSize.setSize( popupSize.width,
               getPopupHeightForRowCount( comboBox.getMaximumRowCount() ) );
             Rectangle popupBounds = computePopupBounds( 0,
               comboBox.getBounds().height, popupSize.width, popupSize.height);
             scroller.setMaximumSize( popupBounds.getSize() );
             scroller.setPreferredSize( popupBounds.getSize() );
             scroller.setMinimumSize( popupBounds.getSize() );
             list.invalidate();
             int selectedIndex = comboBox.getSelectedIndex();
             if ( selectedIndex == -1 ) {
               list.clearSelection();
             } else {
               list.setSelectedIndex( selectedIndex );
             }
             list.ensureIndexIsVisible( list.getSelectedIndex() );
             setLightWeightPopupEnabled( comboBox.isLightWeightPopupEnabled() );
             show( comboBox, popupBounds.x, popupBounds.y );
           }
         };
         popup.getAccessibleContext().setAccessibleParent(comboBox);
         return popup;
       }
       }*/

}
