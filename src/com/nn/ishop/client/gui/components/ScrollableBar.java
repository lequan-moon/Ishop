package com.nn.ishop.client.gui.components;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class ScrollableBar extends JComponent implements SwingConstants {
  
	  /**
	   * Serial version UID
	   */
	  private static final long serialVersionUID = -8313260399194427678L;
	  static {
			UIManager.put("ScrollableBarUI", 
		  "com.nn.ishop.client.gui.components.ScrollableBarUI");
	  }
	  public ScrollableBar(){
		  
	  }
	  public ScrollableBar(Component comp) {
	    this(comp, HORIZONTAL);
	  }
	  public ScrollableBar(Component comp, int orientation) {
	    this.comp = comp;
	    if (orientation == HORIZONTAL) {
	      horizontal = true;
	    }
	    else {
	      horizontal = false;
	    }
	    small = true; // Arrow size on scroll button.
	    inc = 4;      // Scroll width in pixels.
	    updateUI();
	  }

	  public String getUIClassID() {
	    return "ScrollableBarUI";
	  }
	
	  public void updateUI() {
	    setUI(UIManager.getUI(this));
	    invalidate();
	  }
	  
	  public Component getComponent() {
	    return comp;
	  }
	
	  public void setComponent(Component comp) {
	    if (this.comp != comp) {
	      Component old = this.comp;
	      this.comp = comp;
	      firePropertyChange("component", old, comp);
	    }
	  }
	
	  public int getIncrement() {
	    return inc;
	  }
	
	  public void setIncrement(int inc) {
	    if (inc > 0 && inc != this.inc) {
	      int old = this.inc;
	      this.inc = inc;
	      firePropertyChange("increment", old, inc);
	    }
	  }
	
	  public boolean isHorizontal() {
	    return horizontal;
	  }
	  
	  public boolean isSmallArrows() {
	    return small;
	  }
	
	  public void setSmallArrows(boolean small) {
	    if (small != this.small) {
	      boolean old = this.small;
	      this.small = small;
	      firePropertyChange("smallArrows", old, small);
	    }
	  }
	
	  private Component comp;
	  private boolean horizontal, small;
	  private int inc;
//	  /**
//	   * Test method
//	   * @param args
//	   * @throws Exception
//	   */
//	  public static void main(String args[]) throws Exception
//	  {
//		  UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
//		    JPanel toolBarPanel = new JPanel(new BorderLayout());
//			JToolBar testToolBar = new JToolBar();
//	        testToolBar.setFloatable(false);
//	        
//	        ScrollableBar sbGraphToolBar 
//	        = new ScrollableBar(testToolBar);
//	        toolBarPanel.add(sbGraphToolBar);
//	        
//	        JButton b1 = new JButton("b1");
//	        JButton b2 = new JButton("b2");
//	        JButton b3 = new JButton("b3");
//	        JButton b4 = new JButton("b4");
//	        JButton b5 = new JButton("b5");
//	        JButton b6 = new JButton("b6");
//	        JButton b7 = new JButton("b7");
//	        JButton b8 = new JButton("b8");
//	        JButton b9 = new JButton("b8");
//	        JButton b10 = new JButton("b8");
//	        JButton b11 = new JButton("b8");
//	        JButton b12 = new JButton("b8");
//	        JButton b13 = new JButton("b8");
//	        JButton b14 = new JButton("b8");
//	        
//	        testToolBar.add(b1);testToolBar.add(b2);
//	        testToolBar.add(b3);testToolBar.add(b4);
//	        testToolBar.add(b5);testToolBar.add(b6);
//	        testToolBar.add(b7);testToolBar.add(b8);
//	        testToolBar.add(b9);testToolBar.add(b10);
//	        testToolBar.add(b11);testToolBar.add(b12);
//	        testToolBar.add(b13);testToolBar.add(b14);
//	        
//	        JFrame frame = new JFrame();
//	        frame.getContentPane().setLayout(new BorderLayout());
//	        frame.getContentPane().add("Center", toolBarPanel);
//	        
//	        
//	        frame.pack();
//	        frame.setVisible(true);
//	        
//	        
//	        
//	  }
}
