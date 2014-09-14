package com.nn.ishop.client.gui.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import com.nn.ishop.client.util.Library;

/**
 * This Label button will not be highlight
 * @author nvnghia
 *
 */
public class CButton2 extends JButton{
	private static final long serialVersionUID = -1563526140429704735L;
	Color oriBgColor = Color.white;//Library.DEFAULT_WINDOW_AND_MENU_BACKGROUND;
	Border oriBorder = new EmptyBorder(3,3,3,3);
	Color oriBorderColor = null;
	Color highLightColor = new Color(255,233,157);
	Border highLightBorder = BorderFactory.createCompoundBorder(
			/*new LineBorder(new Color(216,182,101), 1, true)*/
			new RoundedCornerBorder(new Color(216,182,101))
			,new EmptyBorder(2,2,2,2));
	public CButton2() {
		super();
		commonSetting();
	}
	public CButton2(Action a) {
		super(a);
		commonSetting();
	}
	public CButton2(Icon icon) {
		super(icon);
		commonSetting();
	}
	public CButton2(String text, Icon icon) {
		super(text, icon);
		commonSetting();
	}
	public CButton2(String text) {
		super(text);
		commonSetting();
	}
	void commonSetting(){
		setBackground(Color.WHITE);
		setOpaque(true);
		oriBgColor = getBackground();		
		setUI(new CButtonUI());		
		setHorizontalTextPosition(JButton.CENTER);
		setVerticalTextPosition(JButton.BOTTOM);
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		addFocusListener(new FocusAdapter() {			
			@Override
			public void focusLost(FocusEvent e) {	
				setSelected(false);
				setBorder(oriBorder);
				setBackground(oriBgColor);
				CButton2.this.validate();
				super.focusLost(e);
			}			
			@Override
			public void focusGained(FocusEvent e) {
				if(isEnabled()){
					setSelected(true);
					setBorder(highLightBorder);
					setBackground(highLightColor);
					CButton2.this.validate();
				}
				super.focusGained(e);
			}
		});
		addMouseListener(new MouseAdapter(){
			@Override
			public void mouseEntered(MouseEvent e) {
				if(!CButton2.this.isSelected() && CButton2.this.isEnabled()) 
					highLight();
				super.mouseEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if(!CButton2.this.isSelected() && CButton2.this.isEnabled())
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
		setBorder(oriBorder);	
		setOpaque(false);
		
	}
	
	
	
	protected void highLight(){
		setBorder(highLightBorder);
		setBackground(highLightColor);
	}
	protected void unHighLight(){	
		if(!isSelected()){
			setBorder(oriBorder);
			setBackground(oriBgColor);
		}else{
			setBorder(highLightBorder);
			setBackground(highLightColor);
		}
	}
	@Override
	public void setSelected(boolean b) {
		if(b)
			highLight();
		else
			unHighLight();	
		super.setSelected(b);
		
	}
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(Graphics g) {        
        graphics = (Graphics2D) g;
        graphics.setColor(getBackground());
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
    			RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 8, 8);
        super.paintComponent(graphics);
   }
   private Graphics2D graphics;

public Color getOriBgColor() {
	return oriBgColor;
}
public void setOriBgColor(Color oriBgColor) {
	this.oriBgColor = oriBgColor;
}
}
