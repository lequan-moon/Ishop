package com.nn.ishop.client.gui.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
public class C20x20Button extends JButton{
	private static final long serialVersionUID = -1563526140429704735L;
	Color oriBgColor;
	
	public C20x20Button() {
		super();
		commonSetting();
	}
	public C20x20Button(Action a) {
		super(a);
		commonSetting();
	}
	public C20x20Button(Icon icon) {
		super(icon);
		commonSetting();
	}
	public C20x20Button(String text, Icon icon) {
		super(text, icon);
		commonSetting();
	}
	public C20x20Button(String text) {
		super(text);
		commonSetting();
	}
	void commonSetting(){
		setUI(new CButtonUI());		
		setHorizontalTextPosition(JButton.CENTER);
		setVerticalTextPosition(JButton.BOTTOM);
		setFont(new Font("Arial",Font.BOLD,11)); 
		setForeground(Color.BLUE);
		
		addFocusListener(new FocusListener() {			
			public void focusLost(FocusEvent e) {								
			}			
			public void focusGained(FocusEvent e) {				
			}
		});
		addMouseListener(new MouseAdapter(){
			@Override
			public void mouseEntered(MouseEvent e) {
				if(!C20x20Button.this.isSelected()) 
					highLight();
				super.mouseEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if(!C20x20Button.this.isSelected())
					unHighLight();
				super.mouseExited(e);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				// Force status: selected = true
				//if(e.getButton() == MouseEvent.BUTTON1)setSelected(true);
				super.mouseClicked(e);
			}
			
		});
		oriBgColor = getBackground();		
		oriBorder = BorderFactory.createCompoundBorder(
				 new LineBorder(oriBgColor, 1, true)
				,new EmptyBorder(2,2,2,2));;
		setBorder(oriBorder);	
		
	}
	
	Border oriBorder = null;
	Color oriBorderColor = null;
	Color highLightColor = new Color(161,190,240);
	Border highLightBorder = BorderFactory.createCompoundBorder(
			new LineBorder(new Color(33,72,128), 1, true)
			,new EmptyBorder(2,2,2,2));
	
	protected void highLight(){
		setBorder(highLightBorder);
		setBackground(highLightColor);
	}
	protected void unHighLight(){
		setBorder(oriBorder);
		setBackground(oriBgColor);
	}
	@Override
	public void setSelected(boolean b) {
		if(b)
			highLight();
		else
			unHighLight();	
		super.setSelected(b);
		
	}
}
