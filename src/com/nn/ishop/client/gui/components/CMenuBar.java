package com.nn.ishop.client.gui.components;

import javax.swing.Action;
import javax.swing.JMenuBar;

import com.nn.ishop.client.util.Library;

public class CMenuBar extends JMenuBar {
	private static final long serialVersionUID = 6402645599228656116L;
	public CMenuBar(Action a) {
		initDefault();
	}

	public CMenuBar(String s, boolean b) {
		initDefault();
	}

	public CMenuBar(String s) {
		initDefault();
	}

	public CMenuBar() {
		initDefault();
	}
	

	void initDefault(){
		//setBackground(Library.DEFAULT_WINDOW_AND_MENU_BACKGROUND);
	}
}
