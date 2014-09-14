package com.nn.ishop.client.gui.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.nn.ishop.client.util.Library;

/**
 * This Label button will not be highlight
 * @author nvnghia
 *
 */
public class CButton extends JButton{
	private static final long serialVersionUID = -1563526140429704735L;
	Color oriBgColor;
	
	public CButton() {
		super();
		commonSetting();
	}
	public CButton(Action a) {
		super(a);
		commonSetting();
	}
	public CButton(Icon icon) {
		super(icon);
		commonSetting();
	}
	public CButton(String text, Icon icon) {
		super(text, icon);
		commonSetting();
	}
	public CButton(String text) {
		super(text);
		commonSetting();
	}
	void commonSetting(){
		setUI(new CButtonUI());		
		setHorizontalTextPosition(JButton.CENTER);
		setVerticalTextPosition(JButton.BOTTOM);
		setFont(new Font("Arial",Font.BOLD,11)); 
		addFocusListener(new FocusListener() {			
			public void focusLost(FocusEvent e) {								
			}			
			public void focusGained(FocusEvent e) {				
			}
		});
		addMouseListener(new MouseAdapter(){
			@Override
			public void mouseEntered(MouseEvent e) {
				if(!CButton.this.isSelected()) 
					highLight();
				super.mouseEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if(!CButton.this.isSelected())
					unHighLight();
				super.mouseExited(e);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				// Force status: selected = true
				if(e.getButton() == MouseEvent.BUTTON1)setSelected(true);
				super.mouseClicked(e);
			}
			
		});
		oriBgColor = Library.DEFAULT_WINDOW_AND_MENU_BACKGROUND;//getBackground();	
		setBackground(oriBgColor);
		oriBorder = BorderFactory.createCompoundBorder(
				 new LineBorder(oriBgColor, 1, true)
				,new EmptyBorder(2,2,2,2));;
		setBorder(oriBorder);	
		
	}
	
	Border oriBorder = null;
	Color oriBorderColor = null;
	Color highLightColor = Library.C_SELECTED_ROW_COLOR;//new Color(/*161,190,240*/);
	Border highLightBorder = BorderFactory.createCompoundBorder(
			/*new LineBorder(new Color(33,72,128), 1, true)*/
			new RoundedCornerBorder(new Color(33,72,128),8)
			,new EmptyBorder(2,2,2,2));
	
	
	protected void highLight(){
		setBorder(highLightBorder);
		setBackground(highLightColor);
		isHighlight = true;
	}
	protected void unHighLight(){
		setBorder(oriBorder);
		setBackground(oriBgColor);
		isHighlight = false;
	}
	private boolean isHighlight = false;
	public boolean isHightlight(){
		return isHighlight;
	}
	@Override
	public void setSelected(boolean b) {
		if(b)
			highLight();
		else
			unHighLight();	
		super.setSelected(b);
		
	}
	@Override
	protected void paintComponent(Graphics g) {        
        graphics = (Graphics2D) g;
        // Paint a gradient from top to bottom
      /*  GradientPaint gp = new GradientPaint(
            0, 0,new Color(255,255,255),
            0, getHeight()-1,  (isSelected() || isHightlight())?highLightColor:oriBgColor);

        graphics.setPaint( gp );*/
        //graphics.fillRect( 0, 0, w, h );
        graphics.setColor((isSelected() || isHightlight())?highLightColor:oriBgColor);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
    			RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 8, 8);
        setOpaque(false);
        super.paintComponent(graphics);        
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
}
