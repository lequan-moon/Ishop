package com.nn.ishop.client.gui.dialogs.prefPages;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.nn.ishop.client.gui.components.CLineBorder;
import com.nn.ishop.client.gui.dialogs.CConstants;

public class PrefsPageView extends JPanel {

	private static final long serialVersionUID = 1115128163145217645L;
	public PrefsPageView() {
		setLayout(new GridBagLayout());
		setOpaque(true);
		setBackground(CConstants.BG_COLOR);
		
		JLabel test = new JLabel("PrefsPageNetworkAnalysis TEST");
		
		// Below should be last actions for this panel
		setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createCompoundBorder(
					new CLineBorder(new Color(171,171,175),3)
				, BorderFactory.createEmptyBorder(3, 3, 3, 3))
				, " View "
				, TitledBorder.DEFAULT_JUSTIFICATION
				, TitledBorder.DEFAULT_POSITION
				, test.getFont()));
		Dimension d = this.getPreferredSize();
		d.width = 328;
		setPreferredSize(d);			
	}
}
