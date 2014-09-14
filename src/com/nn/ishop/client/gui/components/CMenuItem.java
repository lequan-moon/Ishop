package com.nn.ishop.client.gui.components;

import java.awt.Color;
import java.awt.Insets;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JMenuItem;

import com.nn.ishop.client.util.Library;

public class CMenuItem extends JMenuItem {

	public CMenuItem() {
		customSetting();
	}

	public CMenuItem(Icon icon) {
		super(icon);
		customSetting();
	}

	public CMenuItem(String text) {
		super(text);
		customSetting();
	}

	public CMenuItem(Action a) {
		super(a);
		customSetting();
	}

	public CMenuItem(String text, Icon icon) {
		super(text, icon);
		customSetting();
	}

	public CMenuItem(String text, int mnemonic) {
		super(text, mnemonic);
		customSetting();
	}
	protected void customSetting(){
		setUI(new CMenuItemUI());
		//setBackground(new Color(240,240,240));
		setMargin(new Insets(2,2,2,2));
	}
	
}
