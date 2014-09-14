package com.nn.ishop.client.gui.components;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.Rectangle;

import javax.swing.Icon;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

import com.nn.ishop.client.util.Library;


public class CTabbedPaneUI extends BasicTabbedPaneUI {

    private final Color SELECTED_TAB_COLOR = new Color(255,255,255);//(153, 186, 243/*10, 36, 106*/);
    private final Color NORMAL_TAB_BGROUND = Library.DEFAULT_WINDOW_AND_MENU_BACKGROUND;// new Color(241,244,247);
    private final Color CORNER_TAB_COLOR1 = new Color(239, 239, 239);
    private final Color CORNER_TAB_COLOR2 = new Color(203, 203, 203);
    private final Color CORNER_TAB_COLOR3 = new Color(228, 228, 228);
    private final Color CORNER_TAB_MAIN_COLOR = new Color(188, 188, 188);
    
    
    /**
     * FIXME: selected border has rounded top corners
     * 
     * @see javax.swing.plaf.basic.BasicTabbedPaneUI#paintTabBorder(java.awt.Graphics, int, int, int, int, int, int, boolean)
     */
    protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, 
            int x, int y, int w, int h, boolean isSelected) {

    	g.setColor(Color.GRAY);
      
        if (tabPlacement == BOTTOM) {
            g.drawLine(x, y + h, x + w, y + h);
        }
        
        // right
        g.setColor(Color.WHITE);
        g.drawLine(x + w - 1, y, x + w - 1, y + h );// -1
        g.setColor(Color.GRAY);
        g.drawLine(x + w - 2, y, x + w - 2, y + h );
        g.drawLine(x + w - 2, y + h, x + w, y + h );
        
        if (tabPlacement == TOP) {
            // And a white line to the left and top
            g.setColor(Color.WHITE);
            g.drawLine(x, y, x, y + h -1);//left border   -1         
            g.drawLine(x, y, x + w - 2, y);//top border
            
            // PAINT TOP-LEFT CORNER
            if(!isSelected)
            {
            	// More line for inactive tab
	            g.setColor(CORNER_TAB_MAIN_COLOR);
	            g.drawLine(x+1, y+1, x+1, y + h -1);
	            g.drawLine(x+1, y+1, x + w - 3, y+1);
	            
	            //1
	            g.setColor(Color.WHITE);
	            g.drawLine(x+1, y+1, x+1, y+1);
	            
	            g.setColor(CORNER_TAB_COLOR1);
	            g.drawLine(x+2, y+1, x+2, y+1);
	            
	            g.setColor(CORNER_TAB_COLOR2);
	            g.drawLine(x+3, y+1, x+3, y+1);
	            //2
	            g.setColor(CORNER_TAB_COLOR1);
	            g.drawLine(x+1, y+2, x+1, y+2);
	            g.setColor(CORNER_TAB_MAIN_COLOR);
	            g.drawLine(x+2, y+2, x+2, y+2);            
	            g.setColor(CORNER_TAB_COLOR2);
	            g.drawLine(x+3, y+2, x+3, y+2);
	            g.setColor(CORNER_TAB_COLOR3);
	            g.drawLine(x+4, y+2, x+4, y+2);
	            //3
	            g.setColor(CORNER_TAB_COLOR2);
	            g.drawLine(x+1, y+3, x+1, y+3);
	            g.setColor(CORNER_TAB_COLOR2);
	            g.drawLine(x+2, y+3, x+2, y+3);
	            //4
	            g.setColor(CORNER_TAB_COLOR3);
	            g.drawLine(x+2, y+4, x+2, y+4);
	            
	            //RIGHT CORNER
	            // line 1
	            g.setColor(new Color(204, 204, 204));
	            g.drawLine(x + w - 4, y + 1, x + w - 4, y + 1);
	            
	            g.setColor(new Color(239,239,239));
	            g.drawLine(x + w - 3, y+1, x + w - 3, y+1);
	            
	            g.setColor(Color.WHITE);
	            g.drawLine(x + w - 2, y+1, x + w - 2, y+1);
	            
	            //line 2
	            g.setColor(new Color(228, 228, 228));
	            g.drawLine(x + w - 5, y + 2, x + w - 5, y + 2);
	            
	            g.setColor(new Color(204,204,204));
	            g.drawLine(x + w - 4, y+2, x + w - 4, y+2);
	            
	            g.setColor(new Color(190,190,190));
	            g.drawLine(x + w - 3, y+2, x + w - 3, y+2);
	            
	            g.setColor(new Color(239,239,239));
	            g.drawLine(x + w - 2, y+2, x + w - 2, y+2);
	            
	            //line 3
	            g.setColor(new Color(203,203,203));
	            g.drawLine(x + w - 3, y+3, x + w - 3, y+3);
	            
	            g.drawLine(x + w - 2, y+3, x + w - 2, y+3);
	            
	            //line 4
	            g.setColor(new Color(226,226,226));
	            g.drawLine(x + w - 3, y+4, x + w - 3, y+4);
	            
            }else
            {
            	//1
	            g.setColor(Color.WHITE);
	            g.drawLine(x+1, y+1, x+1, y+1);
	            
	            g.setColor(new Color(226,226,226));
	            g.drawLine(x+2, y+1, x+2, y+1);
	            
	            g.setColor(new Color(149,149,149));
	            g.drawLine(x+3, y+1, x+3, y+1);
	            
	            //2
	            g.setColor(new Color(226,226,226));
	            g.drawLine(x+1, y+2, x+1, y+2);
	            
	            g.setColor(new Color(112,112,112));
	            g.drawLine(x+2, y+2, x+2, y+2);            
	           
	            //3
	            g.setColor(new Color(150,150,150));
	            g.drawLine(x+1, y+3, x+1, y+3);
	            
	            //-- A line for left edge
	            g.setColor(new Color(149,149,149));
	            g.drawLine(x+1, y+3, x+1, y + h -1);
	            
	            // A LINE for top edge
	            g.setColor(new Color(149,149,149));
	            g.drawLine(x + 4, y+1, x + w - 5, y+1);
	            
	            //RIGHT CORNER
	            // line 1
	            g.setColor(new Color(149,149,149));
	            g.drawLine(x + w - 4, y+1, x + w - 4, y+1);
	            
	            g.setColor(new Color(226,226,226));
	            g.drawLine(x + w - 3, y+1, x + w - 3, y+1);
	            
	            g.setColor(Color.WHITE);
	            g.drawLine(x + w - 2, y+1, x + w - 2, y+1);
	            
	            //line 2
	            g.setColor(new Color(112,112,112));
	            g.drawLine(x + w - 3, y+2, x + w - 3, y+2);
	            
	            g.setColor(new Color(226,226,226));
	            g.drawLine(x + w - 2, y+2, x + w - 2, y+2);
	            
	            //line 3
	            g.setColor(new Color(150,150,150));
	            g.drawLine(x + w - 2, y+3, x + w - 2, y+3);
            }
        }
        
        if (tabPlacement == BOTTOM && isSelected) {
            g.setColor(Color.BLACK);
            // Left
            g.drawLine(x + 1, y + 1, x + 1, y + h);//left
            
            // Right
            g.drawLine(x + w - 2, y, x + w - 2, y + h);//right
            // Top
            g.drawLine(x + 1, y + 1, x + w - 2, y + 1);//top
            // Bottom
            g.drawLine(x + 1, y + h - 1, x + w - 2, y + h - 1);
        }
    }

    /**
     * Give selected tab blue color with a gradient!!.
     * Next time we will make it rounded
     * @see javax.swing.plaf.basic.BasicTabbedPaneUI#paintTabBackground(java.awt.Graphics, int, int, int, int, int, int, boolean)
     */
    protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, 
            int x, int y, int w, int h, boolean isSelected) {
        
        @SuppressWarnings("unused")
		Color color = UIManager.getColor("control");
        GradientPaint gp = new GradientPaint(
                0, 0,new Color(255,255,255),
                0, h-1, new Color(178, 178, 178));
        GradientPaint gp2 = new GradientPaint(
                0, 0, new Color(200, 200, 200),
                0, h-1,new Color(255,255,255));
        if (isSelected) {
            if (tabPlacement == TOP) {
                Graphics2D g2 = (Graphics2D)g;
                Paint storedPaint = g2.getPaint();  
                g2.setPaint(gp2/*SELECTED_TAB_COLOR*/);    
                
                g2.fillRect(x, y, w, h+1);
                
                g2.setPaint(storedPaint);
            }
        } else {
            Graphics2D g2 = (Graphics2D)g;
            Paint storedPaint = g2.getPaint();
            g2.setPaint(gp/*NORMAL_TAB_BGROUND*/);            
            g2.fillRect(x, y, w, h);
            g2.setPaint(storedPaint);
        }
    }

    /**
     * Do not paint a focus indicator.
     * 
     * @see javax.swing.plaf.basic.BasicTabbedPaneUI#paintFocusIndicator(java.awt.Graphics, int, java.awt.Rectangle[], int, java.awt.Rectangle, java.awt.Rectangle, boolean)
     */
    protected void paintFocusIndicator(Graphics arg0, int arg1, Rectangle[] arg2, int arg3, Rectangle arg4, Rectangle arg5, boolean arg6) {
        // Leave it
    }

    /**
     * We do not want the tab to "lift up" when it is selected.
     * 
     * @see javax.swing.plaf.basic.BasicTabbedPaneUI#installDefaults()
     */  
    protected void installDefaults() {
        super.installDefaults();        
        tabAreaInsets = new Insets(0, 0, 0, 0);
        selectedTabPadInsets = new Insets(0, 0, 0, 0);
        contentBorderInsets = new Insets(1, 0, 0, 0);
    }

    /**
     * Nor do we want the label to move.
     */
    protected int getTabLabelShiftY(int tabPlacement, int tabIndex, boolean isSelected) {
        return 0;
    }
    
    
    /* 
     * (non-Javadoc)
     * @see javax.swing.plaf.basic.BasicTabbedPaneUI#calculateTabHeight(int, int, int)
     */
    protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight) {
        
        return fontHeight + 10;
    }
    
    protected int calculateTabWidth(int tabPlacement, int tabIndex,
    		FontMetrics metrics) {
    	return super.calculateTabWidth(tabPlacement, tabIndex, metrics)/*+35*/;
    }
    
    protected void layoutLabel(int arg0, FontMetrics arg1, int arg2, 
    		String arg3, Icon arg4, Rectangle arg5, Rectangle arg6, Rectangle arg7, boolean arg8) 
    {

        super.layoutLabel(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8);
    }

    /**
     * Selected labels have a white color.
     * 
     * @see javax.swing.plaf.basic.BasicTabbedPaneUI#paintText(java.awt.Graphics, int, java.awt.Font, java.awt.FontMetrics, int, java.lang.String, java.awt.Rectangle, boolean)
     */
    @Override
    protected void paintText(Graphics g, int tabPlacement, Font font, 
            FontMetrics metrics, int tabIndex, String title, Rectangle textRect, 
            boolean isSelected) {
        
        /*if (isSelected && tabPlacement == TOP) 
            g.setColor(Color.WHITE);
         else */
            g.setColor(Color.BLACK);
          //-- Change to fit tab icon       
//        Font tabFont = null;
//        if (isSelected && tabPlacement == TOP) 
//        	tabFont = new Font("Arial Unicode MS", Font.PLAIN, 11);
//        else
//        	tabFont = new Font("Arial Unicode MS", Font.PLAIN, 10);
            
//        g.setFont(tabFont);
       	g.drawString(title, textRect.x, textRect.y + metrics.getAscent());
    }        
    
    /* 
     * (non-Javadoc)
     * @see javax.swing.plaf.basic.BasicTabbedPaneUI#paintContentBorderTopEdge(java.awt.Graphics, int, int, int, int, int, int)
     */
    protected void paintContentBorderTopEdge(Graphics g, int tabPlacement, 
            int selectedIndex, int x, int y, int w, int h) {
//        if (selectedIndex != -1 && tabPlacement == TOP) {
//            g.setColor(new Color(187,187,187));
//            g.drawLine(x, y, x + w, y);
//        }
    }

    /* (non-Javadoc)
     * @see javax.swing.plaf.basic.BasicTabbedPaneUI#paintContentBorderBottomEdge(java.awt.Graphics, int, int, int, int, int, int)
     */
    protected void paintContentBorderBottomEdge(Graphics g, int tabPlacement, 
            int selectedIndex, int x, int y, int w, int h) {
//    	if (selectedIndex != -1 && tabPlacement == BOTTOM) {
//    		g.setColor(new Color(187,187,187));        
//    		g.drawLine(x, y + h, x + w, y + h);
//    	}
    }
    
    /* 
     * (non-Javadoc)
     * @see javax.swing.plaf.basic.BasicTabbedPaneUI#paintContentBorder(java.awt.Graphics, int, int)
     */
    protected void paintContentBorder(Graphics g, int tabPlacement,
    		int selectedIndex) {
    	g.setColor(new Color(187,187,187));
    	super.paintContentBorder(g, tabPlacement, selectedIndex);
    }
    
    /* (non-Javadoc)
     * @see javax.swing.plaf.basic.BasicTabbedPaneUI#paintContentBorderLeftEdge(java.awt.Graphics, int, int, int, int, int, int)
     */
    protected void paintContentBorderLeftEdge(Graphics g, int tabPlacement, 
            int selectedIndex, int x, int y, int w, int h) {
//    	if (selectedIndex != -1 && tabPlacement == LEFT) {
//    		g.setColor(Color.RED/*new Color(187,187,187)*/);
//    		g.drawLine(x, y, x, y + h);
//    	}
    }

    /* (non-Javadoc)
     * @see javax.swing.plaf.basic.BasicTabbedPaneUI#paintContentBorderRightEdge(java.awt.Graphics, int, int, int, int, int, int)
     */
    protected void paintContentBorderRightEdge(Graphics g, int tabPlacement, 
            int selectedIndex, int x, int y, int w, int h) {
//    	if (selectedIndex != -1 && tabPlacement == RIGHT) {
//	    	g.setColor(new Color(187,187,187));
//	        g.drawLine(x + w, y, x + w, y + h);
//    	}
    }
    
   
}