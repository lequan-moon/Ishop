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

public class PrefsPagePrint extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2920677967378716853L;
	
	public PrefsPagePrint() {
		setLayout(new GridBagLayout());
		setOpaque(true);
		setBackground(CConstants.BG_COLOR);
		JLabel test = new JLabel("GENERAL TEST");
		
		// Below should be last actions for this panel
		setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createCompoundBorder(
				new CLineBorder(new Color(171,171,175),3)
				, BorderFactory.createEmptyBorder(3, 3, 3, 3))
				, " Print page "
				, TitledBorder.DEFAULT_JUSTIFICATION
				, TitledBorder.DEFAULT_POSITION
				, test.getFont()));
		Dimension d = this.getPreferredSize();
		d.width = 320;
		setPreferredSize(d);			
	}
}

