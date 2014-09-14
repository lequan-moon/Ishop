package com.nn.ishop.client.gui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;

import javax.swing.JLabel;

public class DottedLabel extends JLabel {
	private static final long serialVersionUID = -2215744607776472346L;
	private Color basicColor = null;
	private static final int DOT_WIDTH = 14;
	public DottedLabel()
	{
		setPreferredSize(new Dimension(20,20));
	}

	public DottedLabel(Color basicColor) {
		super();
		this.basicColor = basicColor;
		setPreferredSize(new Dimension(20,20));
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
		int h =getHeight();
		Ellipse2D.Double s = new Ellipse2D.Double(5, (h-DOT_WIDTH)/2, DOT_WIDTH, DOT_WIDTH);
		g2d.setPaint(basicColor==null?getParent().getBackground():basicColor);
		g2d.draw(s);
		g2d.fill(s);
	}

	public Color getBasicColor() {
		return basicColor;
	}

	public void setBasicColor(Color basicColor) {
		this.basicColor = basicColor;
		repaint();
	}
	@Override
	public void setBackground(Color bg) {
		super.setBackground(bg);
	}

}
