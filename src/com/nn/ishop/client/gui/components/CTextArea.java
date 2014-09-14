package com.nn.ishop.client.gui.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import javax.swing.text.Document;

public class CTextArea extends JTextArea {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public CTextArea() {
		 commonSetting();
	}

	public CTextArea(String text) {
		super(text);
		commonSetting();
	}

	public CTextArea(Document doc) {
		super(doc);
		commonSetting();
	}

	public CTextArea(int rows, int columns) {
		super(rows, columns);
		commonSetting();
	}

	public CTextArea(String text, int rows, int columns) {
		super(text, rows, columns);
		commonSetting();
	}

	public CTextArea(Document doc, String text, int rows, int columns) {
		super(doc, text, rows, columns);
		commonSetting();
	}
	void commonSetting(){
		setBorder(BorderFactory.createCompoundBorder(
				new CLineBorder(null,CLineBorder.DRAW_ALL_BORDER),
				BorderFactory.createEmptyBorder(2, 2, 2, 2)));
		setAlignmentX(TOP_ALIGNMENT);
		setLineWrap(true);
		setWrapStyleWord(true);
		setOpaque(false);
	}
	@Override
	protected void paintComponent(Graphics g) {        
        graphics = (Graphics2D) g;
        graphics.setColor(getBackground());
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
    			RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 8, 8);
        super.paintComponent(graphics);
   }
	
   @Override
   protected void paintBorder(Graphics g) {
	   graphics = (Graphics2D) g;
	   graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
   			RenderingHints.VALUE_ANTIALIAS_ON);
	   graphics.setColor(new Color(187,187,187));
       graphics.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 8, 8);
   }
   
   @Override
   public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 8, 8);
        }
        return shape.contains(x, y);
   }
   private Shape shape;
   private Graphics2D graphics;
}
