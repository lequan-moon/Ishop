package com.nn.ishop.client.gui.components;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JToggleButton;

public class CToggleButton extends JToggleButton {
	private static final long serialVersionUID = -5355355348591162741L;

	public CToggleButton(Icon icon) {
		super(icon);
		commonSetting();
	}

	void commonSetting(){
		setHorizontalTextPosition(JButton.CENTER);
		setBorderPainted(false);
		setFocusPainted(false);
		setBorder(BorderFactory.createEmptyBorder());
		setSize(20,20);
	}
}
