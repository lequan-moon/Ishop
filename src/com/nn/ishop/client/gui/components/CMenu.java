package com.nn.ishop.client.gui.components;

import java.awt.Color;

import javax.swing.Action;
import javax.swing.JMenu;

import com.nn.ishop.client.util.Library;


public class CMenu extends JMenu {
	private static final long serialVersionUID = 6402645599228656116L;
	public CMenu(Action a) {
		super(a);
		initDefault();
	}

	public CMenu(String s, boolean b) {
		super(s, b);
		initDefault();
	}

	public CMenu(String s) {
		super(s);
		initDefault();
	}

	public CMenu() {
		initDefault();
	}
	void initDefault(){
		//setBackground(Library.DEFAULT_WINDOW_AND_MENU_BACKGROUND);
	}
	
}
