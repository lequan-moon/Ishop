package com.nn.ishop.client.gui.components;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;

public    class JVertLabel extends JLabel{

	public JVertLabel() {
		super();
		jbInit();
	}

	public JVertLabel(Icon image, int horizontalAlignment) {
		super(image, horizontalAlignment);
		jbInit();
	}

	public JVertLabel(Icon image) {
		super(image);
		jbInit();
	}

	public JVertLabel(String text, Icon icon, int horizontalAlignment) {
		super(text, icon, horizontalAlignment);
		jbInit();
	}

	public JVertLabel(String text, int horizontalAlignment) {
		super(text, horizontalAlignment);
		jbInit();
	}

	public JVertLabel(String text) {
		super(text);
		jbInit();
	}
	public void jbInit(){
		setUI(new VerticalLabelUI(false));			
		setVerticalTextPosition(JLabel.CENTER);
		setHorizontalAlignment(JLabel.LEFT);
		setVerticalTextPosition(JLabel.CENTER);
		setAlignmentX(JComponent.LEFT_ALIGNMENT);
		setIconTextGap(0);
		setIcon(null);
		
	}
	
}