package com.nn.ishop.client.gui.dialogs.prefPages;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.nn.ishop.client.gui.components.CLineBorder;
import com.nn.ishop.client.gui.dialogs.CConstants;

public class PrefsPageNetworkBehavior extends JPanel {
	/** Serial version UID*/
	private static final long serialVersionUID = -5251859643265293465L;
	private JCheckBox showNodeLabelOnMouseOver;
	private JCheckBox showNodeLabelOnsingleClick;
	
	private JCheckBox showEdgeLabelOnMouseOver;
	private JCheckBox showEdgeLabelOnSingleClick;	
	private JCheckBox highLightEdgeOnMouseOver;
	
	private JCheckBox showKNE1OnSingleClick;
	private JCheckBox showKNE1OfSelectedNode;
	
	private JCheckBox showInforWindowOnMouseOver;
	private JCheckBox showInforWindowOnSingleClick;
	private JCheckBox showInforWindowOnDoubleClick;
	
	
	
	public PrefsPageNetworkBehavior() {
		setLayout(new GridLayout(4,1));
		setBackground(CConstants.BG_COLOR);
		showNodeLabelOnMouseOver = new JCheckBox("Show node label on mouse over");
		showNodeLabelOnsingleClick = new JCheckBox("Show node label on single click");
		
		showEdgeLabelOnMouseOver = new JCheckBox("Show edge label on mouse over");
		showEdgeLabelOnSingleClick = new JCheckBox("Show edge label on single click");
		highLightEdgeOnMouseOver = new JCheckBox("Highlight edge on mouse over");
		
		showKNE1OnSingleClick = new JCheckBox("Show KNE 1 on single click");
		showKNE1OfSelectedNode = new JCheckBox("Show KNE 1 of selected node");

		showInforWindowOnMouseOver = new JCheckBox("Show infor window on mouse over");
		showInforWindowOnSingleClick = new JCheckBox("Show infor window on single click");
		showInforWindowOnDoubleClick = new JCheckBox("Show infor window on double click");

		// Set background
		showNodeLabelOnMouseOver.setBackground(CConstants.BG_COLOR);
		showNodeLabelOnsingleClick.setBackground(CConstants.BG_COLOR);
		showEdgeLabelOnMouseOver.setBackground(CConstants.BG_COLOR);
		showEdgeLabelOnSingleClick.setBackground(CConstants.BG_COLOR);
		highLightEdgeOnMouseOver.setBackground(CConstants.BG_COLOR);
		showKNE1OnSingleClick.setBackground(CConstants.BG_COLOR);
		showKNE1OfSelectedNode.setBackground(CConstants.BG_COLOR);
		showInforWindowOnMouseOver.setBackground(CConstants.BG_COLOR);
		showInforWindowOnSingleClick.setBackground(CConstants.BG_COLOR);
		showInforWindowOnDoubleClick.setBackground(CConstants.BG_COLOR);
		
		// set state for check box
		/** TODO next time should get values from connect.cfg first */
		showNodeLabelOnMouseOver.setSelected(true);
		showNodeLabelOnsingleClick.setSelected(true);
		showEdgeLabelOnSingleClick.setSelected(true);
		highLightEdgeOnMouseOver.setSelected(true);
		
		JPanel nodePnl = new JPanel(new GridBagLayout());
		nodePnl.setBackground(CConstants.BG_COLOR);
		JLabel test = new JLabel();
		nodePnl.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createCompoundBorder(new CLineBorder(new Color(171,171,175),3)
				, BorderFactory.createEmptyBorder(1, 0, 1, 1))
				, " Nodes "
				, TitledBorder.DEFAULT_JUSTIFICATION
				, TitledBorder.DEFAULT_POSITION
				, test.getFont()));
		add(nodePnl);
		
		//row 1
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0,0,0,0);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		
		nodePnl.add(showNodeLabelOnMouseOver, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.weightx = 1.0f;
		
		nodePnl.add(showNodeLabelOnsingleClick, gbc);
		
		// Edge panel 
		JPanel edgePnl = new JPanel(new GridBagLayout());
		edgePnl.setBackground(CConstants.BG_COLOR);
		edgePnl.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createCompoundBorder(new CLineBorder(new Color(171,171,175),3)
				, BorderFactory.createEmptyBorder(1, 0, 1, 1))
				, " Edges "
				, TitledBorder.DEFAULT_JUSTIFICATION
				, TitledBorder.DEFAULT_POSITION
				, test.getFont()));
		add(edgePnl);
		
		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.anchor = GridBagConstraints.LINE_START;
		gbc2.fill = GridBagConstraints.HORIZONTAL;
		gbc2.gridx = 0;
		gbc2.gridy = 0;
		gbc2.gridwidth = 1;
		gbc2.insets = new Insets(0,0,0,0);
		edgePnl.add(showEdgeLabelOnMouseOver, gbc2);
		
		gbc2.gridx = 0;
		gbc2.gridy = 1;
		edgePnl.add(showEdgeLabelOnSingleClick, gbc2);
		
		gbc2.gridx = 0;
		gbc2.gridy = 2;
		gbc2.weightx = 1.0f;
		edgePnl.add(highLightEdgeOnMouseOver, gbc2);
		
		// KNE
		JPanel knePnl = new JPanel(new GridBagLayout());
		knePnl.setBackground(CConstants.BG_COLOR);
		knePnl.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createCompoundBorder(new CLineBorder(new Color(171,171,175),3)
				, BorderFactory.createEmptyBorder(1, 0, 1, 1))
				, " KNE "
				, TitledBorder.DEFAULT_JUSTIFICATION
				, TitledBorder.DEFAULT_POSITION
				, test.getFont()));
		add(knePnl);
		
		GridBagConstraints gbc3 = new GridBagConstraints();
		gbc3.anchor = GridBagConstraints.LINE_START;
		gbc3.fill = GridBagConstraints.HORIZONTAL;
		gbc3.gridx = 0;
		gbc3.gridy = 0;
		gbc3.gridwidth = 1;
		gbc3.insets = new Insets(0,0,0,0);
		knePnl.add(showKNE1OnSingleClick, gbc3);
		
		gbc3.gridx = 0;
		gbc3.gridy = 1;
		gbc3.weightx = 1.0f;
		knePnl.add(showKNE1OfSelectedNode, gbc3);
		
		// Infor Window
		JPanel infoWinPnl = new JPanel(new GridBagLayout());
		infoWinPnl.setBackground(CConstants.BG_COLOR);
		infoWinPnl.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createCompoundBorder(new CLineBorder(new Color(171,171,175),3)
				, BorderFactory.createEmptyBorder(1, 0, 1, 1))
				, " Infor Window "
				, TitledBorder.DEFAULT_JUSTIFICATION
				, TitledBorder.DEFAULT_POSITION
				, test.getFont()));
		add(infoWinPnl);
		
		GridBagConstraints gbc4 = new GridBagConstraints();
		gbc4.anchor = GridBagConstraints.LINE_START;
		gbc4.fill = GridBagConstraints.HORIZONTAL;
		gbc4.gridx = 0;
		gbc4.gridy = 0;
		gbc4.gridwidth = 1;
		gbc4.insets = new Insets(0,0,0,0);
		infoWinPnl.add(showInforWindowOnMouseOver, gbc4);
		
		gbc4.gridx = 0;
		gbc4.gridy = 1;
		infoWinPnl.add(showInforWindowOnSingleClick, gbc4);		
		
		gbc4.gridx = 0;
		gbc4.gridy = 2;
		gbc4.weightx = 1.0f;
		infoWinPnl.add(showInforWindowOnDoubleClick, gbc4);		
		
		// Set preferred size
		Dimension d = this.getPreferredSize();
		d.width = 328;
		d.height = 320;
		setPreferredSize(d);
		
	}
	//
	public boolean isShowNodeLabelOnMouseOver(){
		return showNodeLabelOnMouseOver.isSelected();
	}
	public boolean isShowNodeLabelOnsingleClick(){
		return showNodeLabelOnsingleClick.isSelected();
	}
	
	public boolean isShowEdgeLabelOnMouseOver(){
		return showEdgeLabelOnMouseOver.isSelected();
	}public boolean isShowEdgeLabelOnSingleClick(){
		return showEdgeLabelOnSingleClick.isSelected();
	}public boolean isHighLightEdgeOnMouseOver(){
		return highLightEdgeOnMouseOver.isSelected();
	}
	
	public boolean isShowKNE1OnSingleClick(){
		return showKNE1OnSingleClick.isSelected();
	}public boolean isShowKNE1OfSelectedNode(){
		return showKNE1OfSelectedNode.isSelected();
	}
	
	public boolean isShowInforWindowOnMouseOver(){
		return showInforWindowOnMouseOver.isSelected();
	}public boolean isShowInforWindowOnSingleClick(){
		return showInforWindowOnSingleClick.isSelected();
	}public boolean isShowInforWindowOnDoubleClick(){
		return showInforWindowOnDoubleClick.isSelected();
	}
	//SET method	
	public void setShowNodeLabelOnMouseOver(boolean b){
		showNodeLabelOnMouseOver.setSelected(b);
	}
	public void setShowNodeLabelOnsingleClick(boolean b){
		showNodeLabelOnsingleClick.setSelected(b);
	}
	
	public void setShowEdgeLabelOnMouseOver(boolean b){
		showEdgeLabelOnMouseOver.setSelected(b);
	}
	public void setShowEdgeLabelOnSingleClick(boolean b){
		showEdgeLabelOnSingleClick.setSelected(b);
	}	
	public void setHighLightEdgeOnMouseOver(boolean b){
		highLightEdgeOnMouseOver.setSelected(b);
	}
	
	public void setShowKNE1OnSingleClick(boolean b){
		showKNE1OnSingleClick.setSelected(b);
	}
	public void setShowKNE1OfSelectedNode(boolean b){
		showKNE1OfSelectedNode.setSelected(b);
	}
	
	public void setShowInforWindowOnMouseOver(boolean b){
		showInforWindowOnMouseOver.setSelected(b);
	}
	public void setShowInforWindowOnSingleClick(boolean b){
		showInforWindowOnSingleClick.setSelected(b);
	}
	public void setShowInforWindowOnDoubleClick(boolean b){
		showInforWindowOnDoubleClick.setSelected(b);
	}	
	
}
