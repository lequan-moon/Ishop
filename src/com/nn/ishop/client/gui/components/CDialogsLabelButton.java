package com.nn.ishop.client.gui.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import com.nn.ishop.client.util.Library;

public class CDialogsLabelButton extends JButton {
	private static final long serialVersionUID = 1L;
	Color oriBgColor = Library.DEFAULT_FORM_BACKGROUND/*new Color(222,223,236)*/;
	Border oriBorder = BorderFactory.createCompoundBorder(
			/*new LineBorder(new Color(216,182,101), 1, true)*/
			new RoundedCornerBorder(new Color(121,121,121),15)
			,new EmptyBorder(2,2,2,2));
	Color oriBorderColor = new Color(121,121,121);
	Color highLightColor = new Color(255,233,157);
	Border highLightBorder = BorderFactory.createCompoundBorder(
			/*new LineBorder(new Color(216,182,101), 1, true)*/
			new RoundedCornerBorder(new Color(216,182,101),15)
			,new EmptyBorder(2,2,2,2));
	private Graphics2D graphics;
	
	private Color paintBgOriColor = new Color(178,178,178);
	
	public CDialogsLabelButton() {
		super();
		commonSetting();
	}

	public CDialogsLabelButton(Action arg0) {
		super(arg0);
		commonSetting();
	}

	public CDialogsLabelButton(Icon arg0) {
		super(arg0);
		commonSetting();
	}

	public CDialogsLabelButton(String arg0, Icon arg1) {
		super(arg0, arg1);
		commonSetting();
	}

	public CDialogsLabelButton(String arg0) {
		super(arg0);
		commonSetting();
	}
	void commonSetting(){
		//setOpaque(true);
		setUI(new CButtonUI());
		setPreferredSize(new Dimension(80,20));
		setMinimumSize(new Dimension(80,20));
		setHorizontalTextPosition(JButton.CENTER);
		setVerticalTextPosition(JButton.BOTTOM);
		setFont(new Font("Arial",Font.BOLD,11)); 
		setBackground(oriBgColor);
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		addFocusListener(new FocusAdapter() {			
			@Override
			public void focusLost(FocusEvent e) {
				if(isEnabled()){
					setSelected(false);
					setBorder(oriBorder);		
					setBackground(oriBgColor);
					CDialogsLabelButton.this.validate();
				}
				super.focusLost(e);
			}			
			@Override
			public void focusGained(FocusEvent e) {
				if(isEnabled()){
					setBorder(highLightBorder);
					setBackground(highLightColor);
					setSelected(true);
				}
				super.focusGained(e);
			}
		});
		addMouseListener(new MouseAdapter(){
			@Override
			public void mouseEntered(MouseEvent e) {
				if(!CDialogsLabelButton.this.isSelected() && isEnabled()) 
					highLight();
				super.mouseEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if(!CDialogsLabelButton.this.isSelected() && isEnabled())
					unHighLight();
				super.mouseExited(e);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				// Force status: selected = true
//				if(e.getButton() == MouseEvent.BUTTON1)setSelected(true);
				super.mouseClicked(e);
			}
			
		});
		oriBgColor = getBackground();
		setBorder(oriBorder);	
		setFocusable(true);
		setFocusPainted(true);
		setOpaque(false);
	}
	
	protected void highLight(){
		//oriBgColor = getBackground();
		setBorder(highLightBorder);
		setBackground(highLightColor);
		isHighlight= true;
	}
	protected void unHighLight(){
		if(!isSelected()){
			setBorder(oriBorder);
			setBackground(oriBgColor);
		}else{
			setBorder(highLightBorder);
			setBackground(highLightColor);
		}
		isHighlight= false;
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
        GradientPaint gp = new GradientPaint(
            0, 0,new Color(255,255,255),
            0, getHeight()-1,  (isSelected() || isHightlight())?highLightColor:paintBgOriColor);

        graphics.setPaint( gp );
        //graphics.fillRect( 0, 0, w, h );
        //graphics.setColor(getBackground());
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

}
