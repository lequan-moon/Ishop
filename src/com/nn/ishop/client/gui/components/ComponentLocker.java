package com.nn.ishop.client.gui.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Hashtable;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.util.Library;
import com.nn.ishop.client.util.Util;


/**
 * This class should use to lock screen from JFrame (rootpane)
 * @author NguyenNghia
 *
 */
public class ComponentLocker {
    JLayeredPane rootPane;
    Hashtable<JComponent, JPanel> glassPanes;
    Hashtable<JComponent, Boolean> isPaneLocked;

    
    public ComponentLocker(JLayeredPane rootPane) {
        super();
        this.rootPane = rootPane;
        glassPanes = new Hashtable<JComponent, JPanel>();
        isPaneLocked = new Hashtable<JComponent, Boolean>();
    }

    JPanel myGlassPane;
    public void setLocked(JComponent jc, boolean locked) {
        // if locking
        if (locked) {
            isPaneLocked.put(jc, Library.TRUE);

            JPanel glassPane = null;

            if (!glassPanes.containsKey(jc)) {
                final JComponent myComponent = jc;
                final JPanel myGlassPane = new JPanel();
                       
                ImageLabel lockedLabel = new ImageLabel(Util.getIcon("access_denied.png"));
                
                lockedLabel.setText(Language.getInstance().getString("function.denied"));
                lockedLabel.setVerticalTextPosition(JLabel.CENTER);
                
                lockedLabel.setFont(new Font("Arial",Font.BOLD,16));
                lockedLabel.setForeground(new Color(179,179,179));
                myGlassPane.add(lockedLabel);
                myGlassPane.setBackground(new Color(255, 255, 255, 153));
                
                rootPane.add(myGlassPane, JLayeredPane.MODAL_LAYER);
                glassPanes.put(jc, myGlassPane);
                myComponent.addHierarchyListener(new HierarchyListener() {
                        public void hierarchyChanged(HierarchyEvent e) {
                        	
                            if (myComponent.isShowing() && ((Boolean) isPaneLocked.get(myComponent)).booleanValue()) {
                            	myGlassPane.setVisible(true);   
                            } else {
                                myGlassPane.setVisible(false);
                            }
                        }
                    });
                jc.addComponentListener(new ComponentAdapter() {
                        public void componentResized(ComponentEvent e) {
                            resizeGlassPaneForComponent(myComponent);
                            myComponent.updateUI();
                        }
                    });
                myGlassPane.addMouseListener(new MouseListener() {
                        public void mouseEntered(MouseEvent e) {
                            e.consume();
                        }

                        public void mouseExited(MouseEvent e) {
                            e.consume();
                        }

                        public void mousePressed(MouseEvent e) {
                            e.consume();
                        }

                        public void mouseReleased(MouseEvent e) {
                            e.consume();
                        }

                        public void mouseClicked(MouseEvent e) {
                            e.consume();
                        }
                    });
                glassPane = myGlassPane;
            } else {
                glassPane = (JPanel) glassPanes.get(jc);
            }

            resizeGlassPaneForComponent(jc);

            if (jc.isShowing()) {
                glassPane.setVisible(true);
                rootPane.moveToFront(glassPane);
                rootPane.validate();
            }

            

        } else {
            isPaneLocked.put(jc, Library.FALSE);

            if (glassPanes.containsKey(jc)) {
                ((JPanel) glassPanes.get(jc)).setVisible(false);
            }
        }

        
    }

      protected void resizeGlassPaneForComponent(JComponent jc) {
        if (jc.isShowing()) {
            JPanel glassPane = (JPanel) glassPanes.get(jc);

            if (jc instanceof JTabbedPane && (((JTabbedPane) jc).getTabCount() > 0)) {
                jc = (JComponent) ((JTabbedPane) jc).getSelectedComponent();
            }

            if (jc.isShowing()) {
                Point l1 = rootPane.getLocationOnScreen();
                Point l2 = jc.getLocationOnScreen();
                glassPane.setBounds(l2.x - l1.x, l2.y - l1.y, jc.getWidth(), jc.getHeight());
            }
        }
    }

}
