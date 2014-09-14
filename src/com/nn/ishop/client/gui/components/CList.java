package com.nn.ishop.client.gui.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.nn.ishop.client.gui.GUIActionEvent;
import com.nn.ishop.client.gui.GUIActionListener;
import com.nn.ishop.client.util.Identifiable;
import com.nn.ishop.client.util.Library;
import com.nn.ishop.client.util.StoppableThread;
import com.nn.ishop.client.util.Util;

@SuppressWarnings("rawtypes")
public class CList extends JList {
	private static final long serialVersionUID = -8007697056060284919L;

    protected Vector<ActionListener> vctActionListeners;
    protected Vector<GUIActionListener> vctGUIActionListeners;
    /**
     * selectNextItem
     */
    protected StoppableThread selectNextItem;

    /**
     * icon of item
     */
    protected Icon itemIcon;

    /**
     * delIcon
     */
    protected Icon delIcon;
    /**
     * mouseOverItemNr
     */
    protected Integer mouseOverItemNr;

    /**
     * isMouseOverDel
     */
    protected boolean isMouseOverDel;

    public CList() {
    	Dimension d = new Dimension(200,768);
        setMinimumSize(d);
    	vctActionListeners = new Vector<ActionListener>();
        vctGUIActionListeners = new Vector<GUIActionListener>();
    	delIcon = Util.getIcon(Library.BUTTON_DEL_SMALL);
        mouseOverItemNr = new Integer(-1);

        addMouseMotionListener(new MouseMotionAdapter()
        {
                public void mouseMoved(MouseEvent e)
                {
                    boolean isEmpty = false;
                    try {
						isEmpty = ((Identifiable) getModel().getElementAt(
								0)).getId().equals(Library.SELECTED_NONE);
					} catch (Exception ex) {
						// TODO: handle exception
					}
					Integer actualMouseOverItemNr = new Integer(!isEmpty 
							? CList.this.locationToIndex(e.getPoint()) : -1);
                    boolean isOverDel = !isEmpty && e.getPoint().x > (getWidth() - delIcon.getIconWidth() - 6);

                    // if there are changes, update renderer
                    if ((actualMouseOverItemNr.intValue() != mouseOverItemNr.intValue()) ||
                            (isOverDel != isMouseOverDel)) {
                        mouseOverItemNr = actualMouseOverItemNr;
                        isMouseOverDel = isOverDel;
                        setCellRenderer(new CListCellRenderer());
                    }
                }
            });
        addMouseListener(new MouseAdapter() {

        		//-- need only this method 
                public void mouseExited(MouseEvent e) {
                    mouseOverItemNr = new Integer(-1);
                    setCellRenderer(new CListCellRenderer());
                }
            });

        setCellRenderer(new CListCellRenderer());  
	}
    
    public List<Object> getListData(){
    	List<Object> ret = new ArrayList<Object>();
    	for(int i=0;i<this.getModel().getSize();i++){
    		ret.add(this.getModel().getElementAt(i));
    	}
    	return ret;
    }
 
    /**
     * Register ActionListener
     *
     * @param al an instance of type ActionListener
     */
    public void addActionListener(ActionListener al) {
        vctActionListeners.add(al);
    }

    public void addGUIActionListener(GUIActionListener al) {
        vctGUIActionListeners.add(al);
    }
    public void removeGUIActionListener(GUIActionListener al) {
        vctGUIActionListeners.remove(al);
    }
    /**
     * Remove ActionListener
     *
     * @param al an instance of type ActionListener
     */
    public void removeActionListener(ActionListener al) {
        vctActionListeners.remove(al);
    }
    protected void callGUIActionListeners(GUIActionEvent e) {
        for (int i = 0; i < vctGUIActionListeners.size(); i++)
            ((GUIActionListener) vctGUIActionListeners.get(i)).guiActionPerformed(e);
    }
    /**
     * Call ActionListener
     *
     * @param e an instance of type ActionEvent
     */
    protected void callActionListeners(ActionEvent e) {
        for (int i = 0; i < vctActionListeners.size(); i++)
            ((ActionListener) vctActionListeners.get(i)).actionPerformed(e);
    }


	  /**
     * This class override functions of DefaultListCellRenderer.
     * Render interface for CList
     */
    class CListCellRenderer extends DefaultListCellRenderer
    {
		private static final long serialVersionUID = 605965546207614825L;
		protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

        CListCellRenderer() {
        }

        public Component getListCellRendererComponent(@SuppressWarnings("rawtypes") JList list, Object object, int index, boolean isSelected,
            boolean hasFocus) {
            JComponent cell;
            JLabel defaultLabel = (JLabel) super.getListCellRendererComponent(list,
            		object,
            		index,
            		isSelected,
            		hasFocus);
            defaultLabel.setToolTipText(defaultLabel.getText());

            String text = defaultLabel.getText();

            defaultLabel.setText(text);

            //System.out.println(object.getClass().getName());
           
            if (itemIcon != null) {
                defaultLabel.setIcon(itemIcon);
            }
          /*  if(object instanceof EmployeeWrapper){
            	EmployeeWrapper ew = (EmployeeWrapper)object;
            	ImageIcon icon = new ImageIcon(ew.getEm().getAvata());
            	defaultLabel.setIcon(icon);
            }*/
            // add more prefix space
            else
            {
            	defaultLabel.setText("   " + defaultLabel.getText());
            }
            if(isSelected)
            	defaultLabel.setFont(new Font(defaultLabel.getName(), Font.BOLD, defaultLabel.getFont().getSize()));
            if ((mouseOverItemNr != null) && (mouseOverItemNr.intValue() == index) &&
                    (list.getSelectedIndices().length == 1)) {
                JPanel cellPanel = new JPanel();
                cellPanel.setLayout(new BorderLayout());

                JLabel copyLabel = new JLabel(defaultLabel.getText());

                //copyLabel.setText(defaultLabel.getText());
                copyLabel.setFont(defaultLabel.getFont());
                copyLabel.setOpaque(false);
                copyLabel.setForeground(defaultLabel.getForeground());
                copyLabel.setBackground(Library.UI_SIC_TRANSPARENT);
                copyLabel.setBorder(null);


                JLabel delContainer = new JLabel(Util.getIcon(isSelected ?
                		Library.BUTTON_DEL_SMALL_HI:
                        Library.BUTTON_DEL_SMALL));
                delContainer.setBorder(new CListRoundedBorder(
                    isMouseOverDel ? (isSelected ? Color.white : Color.black): Library.UI_SIC_TRANSPARENT, 2) );
                cellPanel.add(copyLabel, BorderLayout.CENTER);
                cellPanel.setOpaque(true);
                cellPanel.setBackground(isSelected ? Library.C_ACTIVE_TOP_BTR : Library.UI_SIC_GRAY);

                //for adding delete icon in List
                //cellPanel.add(delContainer, BorderLayout.EAST);
                cell = cellPanel;
            } else {
                cell = defaultLabel;
            }

            cell.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));


        if (!isSelected)
        {
        	cell.setBackground(Library.C_BUTTON_BG);
        }
        else
        {
        	cell.setBackground(Library.C_ACTIVE_TOP_BTR);
        }
        Dimension d = cell.getPreferredSize();
        d.height = 20;
        cell.setPreferredSize(d);

            return cell;
        }
    }

    /**
     * This class override functions of Border.
     */
    class CListRoundedBorder implements Border {
        private Color color;
        private int radius;

        CListRoundedBorder(Color col, int radius) {
            this.color = col;
            this.radius = radius;
        }

        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius, this.radius, this.radius, this.radius);
        }

        public boolean isBorderOpaque() {
            return true;
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.setColor(color);
            g.drawRoundRect(x, y + 2, width - 1, height - 4, radius, radius);
        }
    }
}
