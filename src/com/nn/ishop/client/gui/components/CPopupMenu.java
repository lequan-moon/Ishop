package com.nn.ishop.client.gui.components;

import java.awt.Color;

import javax.swing.JPopupMenu;

public class CPopupMenu extends JPopupMenu {
	private static final long serialVersionUID = 5530518242719208041L;
	public CPopupMenu() {
		super();		
	}

	public CPopupMenu(String label) {
		super(label);
	}
	void defaultInit()
	{
		setBackground(Color.WHITE);
	}

}
