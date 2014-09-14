package com.nn.ishop.client.gui.components;

import java.awt.Color;

import javax.swing.JSeparator;

import com.nn.ishop.client.util.Library;

public class CSeparator extends JSeparator {

	public CSeparator() {
		super();		
		initDefault();
	}

	public CSeparator(int orientation) {
		super(orientation);
		initDefault();
	}
	
	void initDefault(){
		setBackground(new Color(240,240,240));
		setForeground(new Color(240,240,240));
		setOrientation(JSeparator.HORIZONTAL);
	}

}
