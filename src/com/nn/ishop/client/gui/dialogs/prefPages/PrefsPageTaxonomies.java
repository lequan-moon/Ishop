package com.nn.ishop.client.gui.dialogs.prefPages;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.nn.ishop.client.gui.components.CLineBorder;
import com.nn.ishop.client.gui.dialogs.CConstants;

public class PrefsPageTaxonomies extends JPanel {
	
	/** serial version UID*/
	private static final long serialVersionUID = -4464433403433160067L;
	JLabel category = new JLabel("Categories:");
	JLabel relations = new JLabel("Categories:");
	
	
	public PrefsPageTaxonomies() {
		setLayout(new GridLayout(2,1));
		setBackground(CConstants.BG_COLOR);
		setOpaque(true);
		
		JLabel test = new JLabel("GENERAL TEST");
		
		// Below should be last actions for this panel
		setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createCompoundBorder(
				new CLineBorder(new Color(171,171,175),3)
				, BorderFactory.createEmptyBorder(3, 3, 3, 3))
				, " Taxonomies page "
				, TitledBorder.DEFAULT_JUSTIFICATION
				, TitledBorder.DEFAULT_POSITION
				, test.getFont()));
		Dimension d = this.getPreferredSize();
		d.width = 328;
		setPreferredSize(d);			
	}
}

