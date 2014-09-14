package com.nn.ishop.client.gui.components;

import java.awt.Color;

import org.xhtmlrenderer.swing.ScalableXHTMLPanel;

public class CScalableXHTMLPanel extends ScalableXHTMLPanel {

	private static final long serialVersionUID = 2015157244013486949L;
	private Color selectedBgColor = new Color(0x99,0xCC,0xFF);
	public CScalableXHTMLPanel(){
		super(new CXHTMLPanelManager());
		String htmlFilePath = "templates/html/index.html";
		setDocument(htmlFilePath);
	}
}
