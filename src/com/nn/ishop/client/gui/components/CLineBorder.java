package com.nn.ishop.client.gui.components;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.border.AbstractBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

public class CLineBorder extends AbstractBorder {

		/**
		 * 
		 */
		private static final long serialVersionUID = 786923239632535178L;
		public static final int DRAW_ALL_BORDER        = 0;
		public static final int DRAW_LEFT_BORDER       = 1;
		public static final int DRAW_RIGHT_BORDER      = 2;
		public static final int DRAW_TOP_BORDER        = 3;
		public static final int DRAW_BOTTOM_BORDER     = 4;
		public static final int DRAW_TOP_BOTTOM_BORDER = 5;
		public static final int DRAW_LEFT_RIGHT_BORDER = 6;
		
		public static final int DRAW_TOP_LEFT_BORDER = 7;
		public static final int DRAW_TOP_RIGHT_BORDER = 8;
		
		public static final int DRAW_BOTTOM_LEFT_BORDER = 9;
		public static final int DRAW_BOTTOM_RIGHT_BORDER = 10;
			
		public static final int DRAW_BOTTOM_TOP_LEFT_BORDER = 11;
		public static final int DRAW_BOTTOM_TOP_RIGHT_BORDER = 12;
		
		public static final int DRAW_TOP_LEFT_RIGHT_BORDER = 13;
		public static final int DRAW_BOTTOM_LEFT_RIGHT_BORDER = 14;
		public static final CompoundBorder BORDER_ALL = BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(2, 22, 2, 22),
				new LineBorder(new Color(233,233,216)));
		public static final CompoundBorder BORDER_RIGHT_LEFT = BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(2, 22, 2, 22),
				new CLineBorder(null, CLineBorder.DRAW_BOTTOM_LEFT_RIGHT_BORDER));
		public static Color HIGHLIGHT_CONTENT_COLOR = new Color(156, 188, 241);
		
		/**
		 * borderType = 0: draw all boder
		 * borderType = 1: draw left side border
		 * borderType = 2: draw right side border
		 * borderType = 3: draw top side border
		 * borderType = 4: draw bottom side border
		 * borderType = 5: draw top+bottom side border
		 * borderType = 6: draw left+right side border
		 */
		private int borderType = 0; // Draw all border
		/**
		 * Put null object to get default value
		 */
		private Color drawColor = new Color(187,187,187);
		
		public CLineBorder(Color drawColor, int borderType)
		{
			if(drawColor != null)
			{
				this.drawColor = drawColor;
			}
			this.borderType = borderType;
		}
		public CLineBorder()
		{	
			this.borderType = 0;//4;
		}
		
	    public void paintBorder(Component c, Graphics g
	            , int x, int y, int width, int height)
	    {
	        g.translate(x, y);
	        g.setColor(drawColor);	
	        switch (borderType)
	        {
		        case 0://all
		        	g.drawRect(0, 0, width-1, 1/2);
		        	g.drawRect(0, 0, 1/2, height - 1);	  
		        	g.drawRect(width-1, 0, 1/2, height-1);
		        	g.drawRect(0, height-1, width-1, 1/2);	
		        	break;
		        case 1://left
		        	//g.drawRect(0, 0, 1/2, height-1);
		        	g.drawRect(0, 0, 1/2, height);
		    	    break;
		        case 2://right
		        	//g.drawRect(width - 1, 0, 1/2, height-1);
		        	g.drawRect(width-1, 0, 1/2, height-1);
		    	    break;
		        case 3://top
		        	g.drawRect(0, 0, width-1, 1/2);	     	   
		     	    break;	        	
		        case 4://bottom	        	
		     	    g.drawRect(0, height-1, width-1, 1/2);	
		     	    break;	        	
		        case 5://top-bottom
		        	g.drawRect(0, 0, width-1, 1/2);//top
		        	g.drawRect(0, height-1, width-1, 1/2);//bottom
		     	    break;
		        case 6://left-right
		        	g.drawRect(0, 0, 1/2, height);//left
		        	g.drawRect(width-1, 0, 1/2, height-1);//right
		    	    break;
		        case 7://top-left
		        	g.drawRect(0, 0, 1/2, height);//left
		        	g.drawRect(0, 0, width-1, 1/2);//top
		    	    break;
		        case 8://top-right
		        	g.drawRect(0, 0, width-1, 1/2);//top
		        	g.drawRect(width-1, 0, 1/2, height-1);//right
		    	    break;
		        case 9://bottom-left
		        	g.drawRect(0, height-1, width-1, 1/2);
		        	g.drawRect(0, 0, 1/2, height);//left
		    	    break;	    	    
		        case 10://bottom-right
		        	g.drawRect(0, height-1, width-1, 1/2);//bottom
		        	g.drawRect(width-1, 0, 1/2, height-1);//right
		    	    break;	  
		        case 11://top-bottom-left
		        	g.drawRect(0, height-1, width-1, 1/2);//bottom
		        	g.drawRect(0, 0, width-1, 1/2);//top
		        	g.drawRect(0, 0, 1/2, height);//left
		    	    break;	    	    
		        case 12://top-bottom-right
		        	g.drawRect(0, height-1, width-1, 1/2);//bottom
		        	g.drawRect(0, 0, width-1, 1/2);//top
		        	g.drawRect(width-1, 0, 1/2, height-1);//right
		    	    break;	    	  
		        case 13://top-left-right
		        	g.drawRect(0, 0, 1/2, height);//left
		        	g.drawRect(0, 0, width-1, 1/2);//top
		        	g.drawRect(width-1, 0, 1/2, height-1);//right
		    	    break;	    	  
		        case 14://bottom-left-right
		        	g.drawRect(0, height-1, width-1, 1/2);//bottom
		        	g.drawRect(0, 0, 1/2, height);//left
		        	g.drawRect(width-1, 0, 1/2, height-1);//right
		    	    break;	    	  	    	    
		        default: break; 
	        }
	 	    
	        g.translate(0 - x, 0 - y);
	    }
	}