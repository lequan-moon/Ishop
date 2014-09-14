package test;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import com.nn.ishop.client.util.Util;

public class LayeredPaneDemo extends JFrame implements ActionListener {

    String[] layerStrings = { "Default Layer", "Palette Layer", "Modal Layer",
                              "Popup Layer", "Drag Layer" };

    Integer[] layerValues = { JLayeredPane.DEFAULT_LAYER,
                              JLayeredPane.PALETTE_LAYER,
                              JLayeredPane.MODAL_LAYER,
                              JLayeredPane.POPUP_LAYER,
                              JLayeredPane.DRAG_LAYER };

    Color[] layerColors = { Color.yellow, Color.magenta, Color.cyan,
                            Color.red, Color.green };

    JComboBox layerList;
    JCheckBox onTop;
    JLayeredPane layeredPane;
    JLabel dragLabel;
    int iconHeight, iconWidth;

    public LayeredPaneDemo()    {
        super("LayeredPaneDemo");

        //Create the duke icon
        final ImageIcon icon = Util.getIcon("logo32.png");
        iconWidth = icon.getIconWidth();
        iconHeight = icon.getIconHeight();

        //Create the control panel at the top of window
        layerList = new JComboBox(layerStrings);
        layerList.setSelectedIndex(2);//select modal layer
        layerList.addActionListener(this);

        onTop = new JCheckBox("Position 0");
        onTop.setSelected(true);
        onTop.addActionListener(this);
        
        JPanel controls = new JPanel();
        controls.add(layerList);
        controls.add(onTop);
        controls.setBorder(BorderFactory.createTitledBorder(
                                         "Choose Duke's Layer and Position"));

        //Create the dragging area at the bottom of the window
        Dimension layeredPaneSize = new Dimension(300, 310);
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(layeredPaneSize);
        layeredPane.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseEntered(MouseEvent e) {
                dragLabel.setBounds(e.getX()-15, e.getY()-10,
                                    iconWidth, iconHeight);
            }
            public void mouseMoved(MouseEvent e) {
                dragLabel.setBounds(e.getX()-15, e.getY()-10,
                                    iconWidth, iconHeight);
            }
        });
        layeredPane.setBorder(BorderFactory.createTitledBorder(
                                         "Move the Mouse to Move Duke"));

        //Add dragging area and control pane to demo frame
        Container contentPane = getContentPane();
        //Use a layout manager that respects preferred sizes
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPane.add(controls);
        contentPane.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPane.add(layeredPane);

        //Layered panes don't have a layout manager so
        //we have to use absolute positioning.
        //This is the origin of the default layer's label
        //and the size of all the layers' labels.
        Point origin = new Point(10, 20);
        Dimension size = new Dimension(140, 140);

        //For each layer, add a colored label overlapping the last
        for (int i = 0 ; i < layerValues.length; i++) {

            //Create and set up colored label
            JLabel label = new JLabel(layerStrings[i]);
            label.setVerticalAlignment(JLabel.TOP);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setOpaque(true);
            label.setBackground(layerColors[i]);
            label.setForeground(Color.black);
            label.setBorder(BorderFactory.createLineBorder(Color.black));
            label.setBounds(origin.x, origin.y, size.width, size.height);

            //Add it to the layered pane
            layeredPane.add(label, layerValues[i]);  

            //Adjust origin for next layer
            origin.x += 35;
            origin.y += 35;
        }

        //Create and add the drag label (duke image) to the layered pane
        dragLabel = new JLabel(icon);
        dragLabel.setBounds(15, 225, iconWidth, iconHeight);
        layeredPane.add(dragLabel, JLayeredPane.MODAL_LAYER, 0);
    }

    //This method gets called when the user selects a new layer
    //or a new position within a layer for duke.
    public void actionPerformed(ActionEvent e) {

        //event came from the combo box...change Duke's layer
        if (e.getSource().equals(layerList))
            layeredPane.setLayer(dragLabel,
                      layerValues[layerList.getSelectedIndex()].intValue(),
                      onTop.isSelected()?0:1);

        //event came from the check box...change Duke's position
        else {
            if (onTop.isSelected())
                layeredPane.moveToFront(dragLabel);
            else
                layeredPane.moveToBack(dragLabel);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new LayeredPaneDemo();

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        frame.pack();
        frame.setVisible(true);
    }
}
