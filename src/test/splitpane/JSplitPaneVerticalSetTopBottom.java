package test.splitpane;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JSplitPane;

public class JSplitPaneVerticalSetTopBottom {
	public static void main(String[] a) {
	    JFrame horizontalFrame = new JFrame();
	    horizontalFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    JComponent topButton = new JButton("Left");
	    JComponent bottomButton = new JButton("Right");
	    JSplitPane splitPane = new JSplitPane();
	    splitPane.setTopComponent(topButton);
	    splitPane.setBottomComponent(bottomButton);
	    splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);

	    horizontalFrame.add(splitPane, BorderLayout.CENTER);
	    horizontalFrame.setSize(150, 150);
	    horizontalFrame.setVisible(true);

	  }

}
