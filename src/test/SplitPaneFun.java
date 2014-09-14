package test;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

public class SplitPaneFun {
public static void main(String[] args) {

        //Here I'm messing around with the divider look. This seems to remove the one-touch arrows.  These blocked-out lines illustrate
        // what I'm doing to modify the divider's border.  Does this look right?:
    //------------------------------------------------------------------------------------------------
    JSplitPane withCustomDivider = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JPanel(), new JPanel());
    BasicSplitPaneDivider divider = ( (BasicSplitPaneUI) withCustomDivider.getUI()).getDivider();
    withCustomDivider.setOneTouchExpandable(true);
    divider.setDividerSize(15);
    divider.setBorder(BorderFactory.createTitledBorder(divider.getBorder(), "Custom border title -- gets rid of the one-touch arrows!"));
    //------------------------------------------------------------------------------------------------

        //build a different splitpane with the default look and behavior just for comparison
    JSplitPane withDefaultDivider = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JPanel(), new JPanel());
    withDefaultDivider.setOneTouchExpandable(true);

        //slap it all together and show it...
    CardLayout splitsLayout = new CardLayout();
    final JPanel splits = new JPanel(splitsLayout);
    splits.add(withCustomDivider, "custom");
    splits.add(withDefaultDivider,"default");

    JButton toggle = new JButton( "click to see the other split pane");
    toggle.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            ((CardLayout)splits.getLayout()).next(splits);
        }
    });

    JFrame frame = new JFrame("Split Pane Divider Comparison");
     frame.setLayout(new BorderLayout());
    frame.add(splits, BorderLayout.CENTER);
    frame.add(toggle, BorderLayout.PAGE_END);
    frame.setSize(600,500);
    frame.setVisible(true);
}
}
