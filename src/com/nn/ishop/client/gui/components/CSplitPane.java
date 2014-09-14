package com.nn.ishop.client.gui.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JSplitPane;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

public class CSplitPane extends JSplitPane {
	private static final long serialVersionUID = 1L;
	public CSplitPane() {
		setUI(new CSplitPaneUI());
		setBackground(new Color(226,226,226));
		repaint();
	}

}
class CSplitPaneUI extends BasicSplitPaneUI{
	public CSplitPaneUI() {
	}
}
class CSplitPaneDevider extends BasicSplitPaneDivider{
	private static final long serialVersionUID = 1L;
	BasicSplitPaneUI delegateUI;
	public CSplitPaneDevider(BasicSplitPaneUI ui) {
		super(ui);
		delegateUI = ui;
		this.setBackground(new Color(226,226,226));
		this.setBorder(new CLineBorder(null,CLineBorder.DRAW_TOP_BOTTOM_BORDER));
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 1));
		final JButton btnSplit = new JButton("..."); 
		btnSplit.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnSplit.setPreferredSize(new Dimension(20,6));
		btnSplit.setBorder(new RoundedCornerBorder(3));
		btnSplit.setFocusPainted(false);
		add(btnSplit);
		setDividerSize(8);
		
		btnSplit.addActionListener(new AbstractAction(){
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent arg0) {				
				JButton btnHideBottomComp = (JButton) delegateUI.getDivider().getComponent(1);
				JButton btnHideTopComp = (JButton) delegateUI.getDivider().getComponent(0);
				/*if(receiptMainSplit.getDividerLocation() == 0.0d)
					btnHideTopComp.doClick();
				else{	
					receiptMainSplit.setDividerLocation(1.0d);
					btnHideBottomComp.doClick();
				}*/
				//-- Hide top component
				 if(delegateUI.getSplitPane().getDividerLocation() == 1.0d)
					btnHideBottomComp.doClick();
				else	
					btnHideTopComp.doClick();
				
			}
			
		});
		validate();
	}
	
}
