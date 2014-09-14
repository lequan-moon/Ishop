package com.nn.ishop.client.gui.components;

import java.awt.Color;

import javax.swing.BoundedRangeModel;
import javax.swing.JProgressBar;

import com.nn.ishop.client.util.Library;

public class CProgressBar extends JProgressBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3406376433369812455L;

	public CProgressBar() {
		super();
		commonSettings();
	}

	public CProgressBar(BoundedRangeModel newModel) {
		super(newModel);
		commonSettings();
	}

	public CProgressBar(int orient, int min, int max) {
		super(orient, min, max);
		commonSettings();
	}

	public CProgressBar(int min, int max) {
		super(min, max);
		commonSettings();
	}

	public CProgressBar(int orient) {
		super(orient);
		commonSettings();
	}
	void commonSettings()
	{		
		setUI(new CProgressBarUI());
		setBackground(Library.C_PROGRESS_BAR);
		setForeground(new Color(166,235,158));		
	}

}
