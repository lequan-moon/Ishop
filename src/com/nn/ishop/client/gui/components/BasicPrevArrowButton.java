package com.nn.ishop.client.gui.components;

import javax.swing.plaf.basic.BasicArrowButton;

import com.nn.ishop.client.util.Library;

public class BasicPrevArrowButton extends BasicArrowButton {
	
	private static final long serialVersionUID = 727486647701022599L;

	public BasicPrevArrowButton(){
		super(BasicArrowButton.WEST);
		setBackground(Library.C_BUTTON_BG);
		setBorderPainted(false);
	}
}
