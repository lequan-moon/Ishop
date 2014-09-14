package com.nn.ishop.client.gui.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * This class use for KNE label 
 * @author connect.shift-think.com
 *
 */
public class CircleLabel extends JLabel {
	
	private static final long serialVersionUID = -374990295564571372L;
	private String entityId = "";

	public CircleLabel(String text, String entityId) {
		super();
		this.entityId = entityId;
		setText(text);		
		setHorizontalAlignment(JLabel.CENTER);
		setFont(new Font("Arial",Font.PLAIN,10));
		setForeground(Color.WHITE);
		setVerticalAlignment(JLabel.CENTER);
		addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "Next time this action will show the hide neighbors");
			}
		});
	}
	public String getEntityId() {
		return entityId;
	}
}
