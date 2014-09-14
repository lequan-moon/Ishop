package com.nn.ishop.client.gui.components;

import javax.swing.plaf.basic.BasicArrowButton;

import com.nn.ishop.client.util.Library;

public class BasicNextArrowButton extends BasicArrowButton {
	private static final long serialVersionUID = 7621888580381215304L;
	public BasicNextArrowButton(){
		super(BasicArrowButton.EAST);
		setBackground(Library.C_BUTTON_BG);
		setBorderPainted(false);
	}
}
