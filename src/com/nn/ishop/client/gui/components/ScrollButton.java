package com.nn.ishop.client.gui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

import com.nn.ishop.client.util.Library;


public class ScrollButton extends JButton {

  private static final long serialVersionUID = -8756336643361426468L;
  private static Color shadowColor;
  private static Color highlightColor;
  private int direction;
  private int buttonWidth;
  private boolean small;

  public ScrollButton(int direction, int width) {
    this(direction, width, true);
  }

  public ScrollButton(int direction, int width, boolean small) {
 
    this.direction = direction;
    this.small = small;
    buttonWidth = width;

    shadowColor = UIManager.getColor("ScrollBar.darkShadow");
    highlightColor = UIManager.getColor("ScrollBar.highlight");
    setBackground(Library.UI_SIC_GRAY);
  }

  public void setSmallArrows(boolean small) {
    if (small != this.small) {
      this.small = small;
    }
    repaint();
  }

  public boolean isSmallArrows() {
    return small;
  }

  public void paint(Graphics g ) {
 
    //boolean leftToRight = this.getComponentOrientation().isLeftToRight();
    boolean isEnabled = getParent().isEnabled();

    Color arrowColor = isEnabled ? 
      MetalLookAndFeel.getControlInfo(): 
      MetalLookAndFeel.getControlDisabled();
    boolean isPressed = getModel().isPressed();
    int width = getWidth();
    int height = getHeight();
    int w = width;
    int h = height;
    int arrowHeight;
    if (direction == WEST || direction == EAST) {
      if (small) {
        arrowHeight = (width+1) / 4;
      }
      else {
        arrowHeight = (height > width+5)?(width+1) / 2:(width+1) / 3;
      }
    }
    else {
      if (small) {
        arrowHeight = (height+1) / 4;
      }
      else {
        arrowHeight = (width > height+5)?(height+1) / 2:(height+1) / 3;
      }
    }

    if (isPressed) {
      g.setColor(MetalLookAndFeel.getControlShadow());
    }
    else {
      g.setColor(getBackground());
    }

    g.fillRect(0, 0, width, height);

    if (direction == NORTH) {
     
      // Draw the arrow
      g.setColor(arrowColor);

      int startY = ((h+1) - arrowHeight) / 2;
      int startX = (w / 2);
      for (int line = 0; line < arrowHeight; line++) {
        g.drawLine( startX-line, startY+line, startX +line+1, startY+line);
      }
    }
    else if ( direction == SOUTH ) {
     
      // Draw the arrow
      g.setColor(arrowColor);

      int startY = (((h+1) - arrowHeight) / 2)+ arrowHeight-1;
      int startX = (w / 2);

      for (int line = 0; line < arrowHeight; line++) {
        g.drawLine(startX-line, startY-line, startX +line+1, startY-line);
      }
    }
    /** We just take care for these cases WEST & EAST*/
    else if (direction == EAST) {
     
      // Draw the arrow
      g.setColor(arrowColor);

      int startX = (((w+1) - arrowHeight) / 2) + arrowHeight-1;
      int startY = (h / 2);

      for (int line = 0; line < arrowHeight; line++) {
        g.drawLine( startX-line, startY-line, startX -line, startY+line+1);
      }
    }
    else if (direction == WEST) {
     
      // Draw the arrow
      g.setColor(arrowColor);

      int startX = (((w+1) - arrowHeight) / 2);
      int startY = (h / 2);

      for (int line = 0; line < arrowHeight; line++) {
        g.drawLine( startX+line, startY-line, startX +line, startY+line+1);
      }
    }
  }

  public Dimension getPreferredSize() {
    if (direction == NORTH || direction == SOUTH) {
      return new Dimension( buttonWidth, buttonWidth - 1 );
    }
    else if (direction == EAST || direction == WEST) {
      return new Dimension( buttonWidth - 1, buttonWidth );
    }
    else {
      return new Dimension( 0, 0 );
    }
  }

  public Dimension getMinimumSize() {
    return getPreferredSize();
  }

  public Dimension getMaximumSize() {
    return new Dimension( Integer.MAX_VALUE, Integer.MAX_VALUE );
  }
    
  public int getButtonWidth() {
    return buttonWidth;
  }

public static Color getShadowColor() {
	return shadowColor;
}

public static Color getHighlightColor() {
	return highlightColor;
}
}
