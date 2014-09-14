package com.nn.ishop.client.gui.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JTextField;

import com.nn.ishop.client.gui.dialogs.CConstants;

public class CTextField extends JTextField implements ActionListener{
	private static final long serialVersionUID = 2950180458949708843L;
	
	public CTextField() {		
		setPreferredSize(CConstants.DIM_110X22);
		setMinimumSize(CConstants.DIM_110X22);		
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(Graphics g) {        
        graphics = (Graphics2D) g;
        graphics.setColor((isEditable())?getBackground():new Color(234,234,234));
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
    			RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 8, 8);
        super.paintComponent(graphics);
   }
	
   /* (non-Javadoc)
 * @see javax.swing.JComponent#paintBorder(java.awt.Graphics)
 */
@Override
   protected void paintBorder(Graphics g) {
	   graphics = (Graphics2D) g;
	   graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
   			RenderingHints.VALUE_ANTIALIAS_ON);
	   graphics.setColor(CConstants.TEXT_BORDER_COLOR);
       graphics.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 8, 8);
   }
   
   @Override
   public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 8, 8);
        }
        return shape.contains(x, y);
   }
   private Shape shape;
   private Graphics2D graphics;
   
public void actionPerformed(ActionEvent arg0) {
}
}