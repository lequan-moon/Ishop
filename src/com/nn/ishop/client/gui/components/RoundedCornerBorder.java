package com.nn.ishop.client.gui.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.border.AbstractBorder;

import com.nn.ishop.client.util.Library;

public class RoundedCornerBorder extends AbstractBorder {
	private static final long serialVersionUID = 1L;
	public Color selectColor = Library.DEFAULT_BORDER_COLOR;//new Color(216,182,101);
	private int cornerRadius = 8;
	public RoundedCornerBorder(Color selectColor){
		this.selectColor = selectColor;
	}
	public RoundedCornerBorder(){
		
	}
	public RoundedCornerBorder(Color selectColor, int cornerRadius){
		this.selectColor = selectColor;
		this.cornerRadius = cornerRadius;
	}
	public RoundedCornerBorder(int cornerRadius){
		this.cornerRadius = cornerRadius;
	}
	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width,
			int height) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		//int r = 8;//12
		int w = width - 1;
		int h = height - 1;
		Area round = new Area(new RoundRectangle2D.Float(x, y, w, h, cornerRadius, cornerRadius));
		Container parent = c.getParent();
		if (parent != null) {
			g2.setColor(parent.getBackground());
			Area corner = new Area(new Rectangle2D.Float(x, y, width, height));
			corner.subtract(round);
			g2.fill(corner);
		}
		g2.setPaint(selectColor);
		g2.draw(round);
		g2.dispose();
	}

	@Override
	public Insets getBorderInsets(Component c) {
		return new Insets(1,1,1,1);
	}

	@Override
	public Insets getBorderInsets(Component c, Insets insets) {
		insets.left = insets.right = 1;
		insets.top = insets.bottom = 1;
		return insets;
	}
}
