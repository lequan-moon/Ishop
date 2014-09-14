package com.nn.ishop.client.gui.dialogs.prefPages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.nn.ishop.client.gui.components.CLineBorder;
import com.nn.ishop.client.gui.dialogs.CConstants;
import com.nn.ishop.client.util.Util;

public class PrefsPageNetworkVisualization extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7206613806455549632L;
	JCheckBox defaultZoom;
	JCheckBox smartZoomBelow;
	JCheckBox alwaysShowEgoNode;
	JCheckBox alwaysCenterEgoNode;
	JCheckBox alwaysShowNodeLabel;
	JCheckBox alwaysShowEdgeLabel;
	JCheckBox alwaysShowInforWindow;
	JCheckBox alwaysAntiAliasGraph;
	
	JTextField txtDefaultZoom;
	JTextField txtSmartZoom;
	
	
	
	public PrefsPageNetworkVisualization() {
		setLayout(new BorderLayout());
		setBackground(CConstants.BG_COLOR);
		defaultZoom = new JCheckBox("Default Zoom: ");
		smartZoomBelow = new JCheckBox("Smart Zoom below: ");
		alwaysShowEgoNode = new JCheckBox("Always show Ego Node");
		alwaysCenterEgoNode = new JCheckBox("Always center Ego Node");
		alwaysShowNodeLabel = new JCheckBox("Always show Node labels");
		alwaysShowEdgeLabel = new JCheckBox("Always show Egde labels");
		alwaysShowInforWindow = new JCheckBox("Always show infor windows");
		alwaysAntiAliasGraph = new JCheckBox("Always antialias graph");
		
		alwaysShowEgoNode.setSelected(false);
		alwaysCenterEgoNode.setSelected(false);
		alwaysCenterEgoNode.setEnabled(false);
		alwaysShowEgoNode.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				if (alwaysShowEgoNode.isSelected())
				{
					alwaysCenterEgoNode.setEnabled(true);
				}
				else
				{
					alwaysCenterEgoNode.setSelected(false);
					alwaysCenterEgoNode.setEnabled(false);
				}
			}
		});

		// Set background
		defaultZoom.setBackground(CConstants.BG_COLOR);
		smartZoomBelow.setBackground(CConstants.BG_COLOR);
		alwaysShowEgoNode.setBackground(CConstants.BG_COLOR);
		alwaysCenterEgoNode.setBackground(CConstants.BG_COLOR);
		alwaysShowNodeLabel.setBackground(CConstants.BG_COLOR);
		alwaysShowEdgeLabel.setBackground(CConstants.BG_COLOR);
		alwaysShowInforWindow.setBackground(CConstants.BG_COLOR);
		alwaysAntiAliasGraph.setBackground(CConstants.BG_COLOR);
		
		// set state for check box
		/** TODO next time should get values from connect.cfg first */
		defaultZoom.setSelected(true);
		smartZoomBelow.setSelected(true);
		alwaysCenterEgoNode.setSelected(true);
		alwaysShowNodeLabel.setSelected(true);
		alwaysAntiAliasGraph.setSelected(true);
		
		txtDefaultZoom = new JTextField("100");
		txtDefaultZoom.setBackground(CConstants.BG_COLOR);
		txtSmartZoom = new JTextField("100");
		txtSmartZoom.setBackground(CConstants.BG_COLOR);
		
//		Util.setLookAndFeelForComponent(txtDefaultZoom);
//		Util.setLookAndFeelForComponent(txtSmartZoom);
		
		JPanel northPnl = new JPanel(new GridBagLayout());
		JLabel test = new JLabel();
		northPnl.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createCompoundBorder(new CLineBorder(new Color(171,171,175),3)
				, BorderFactory.createEmptyBorder(3, 0, 3, 3))
				, " Zooming "
				, TitledBorder.DEFAULT_JUSTIFICATION
				, TitledBorder.DEFAULT_POSITION
				, test.getFont()));
		add(northPnl,BorderLayout.NORTH);
		
		//row 1
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		
		northPnl.add(defaultZoom, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		
		northPnl.add(txtDefaultZoom, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		
		northPnl.add(new JLabel("%"), gbc);		
		//row 2
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		
		northPnl.add(smartZoomBelow, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.weightx = 1f;
		
		northPnl.add(txtSmartZoom, gbc);
		northPnl.setBackground(CConstants.BG_COLOR);
		
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		
		northPnl.add(new JLabel("%"), gbc);		

		// Another panel
		JPanel southPnl = new JPanel(new GridBagLayout());
		southPnl.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createCompoundBorder(new CLineBorder(new Color(171,171,175),3)
				, BorderFactory.createEmptyBorder(3, 0, 3, 3))
				, " General Visualization "
				, TitledBorder.DEFAULT_JUSTIFICATION
				, TitledBorder.DEFAULT_POSITION
				, test.getFont()));
		southPnl.setBackground(CConstants.BG_COLOR);		
		add(southPnl, BorderLayout.CENTER);
		
		GridBagConstraints gbc2 = new GridBagConstraints();

		gbc2.anchor = GridBagConstraints.LINE_START;
		gbc2.fill = GridBagConstraints.HORIZONTAL;
		gbc2.gridx = 0;
		gbc2.gridy = 0;
		gbc2.gridwidth = 1;
		southPnl.add(alwaysShowEgoNode,gbc2);

		gbc2.insets = new Insets(0,25,0,0);
		gbc2.gridx = 0;
		gbc2.gridy = 1;
		southPnl.add(alwaysCenterEgoNode,gbc2);
		
		gbc2.gridx = 0;
		gbc2.gridy = 2;
		gbc2.insets = new Insets(0,0,0,0);
		southPnl.add(alwaysShowNodeLabel,gbc2);
		
		gbc2.gridx = 0;
		gbc2.gridy = 3;
		southPnl.add(alwaysShowEdgeLabel,gbc2);
		
		gbc2.gridx = 0;
		gbc2.gridy = 4;
		southPnl.add(alwaysShowInforWindow,gbc2);
		
		gbc2.gridx = 0;
		gbc2.gridy = 5;
		gbc2.weightx = 1f;
		southPnl.add(alwaysAntiAliasGraph,gbc2);		
		
		Dimension d = this.getPreferredSize();
		d.width = 328;
		setPreferredSize(d);			
	}
	//
	public String getdefaultZoom(){
		return txtDefaultZoom.getText();
	}
	public String getSmartZoom(){
		return txtSmartZoom.getText();
	}
	
	public boolean isDefaultZoom(){
		return defaultZoom.isSelected();		
	}
	public boolean isSmartZoomBelow(){
		return smartZoomBelow.isSelected();		
	}
	public boolean isAlwaysShowEgoNode(){
		return alwaysShowEgoNode.isSelected();		
	}
	public boolean isAlwaysCenterEgoNode(){
		return alwaysCenterEgoNode.isSelected();		
	}
	public boolean isAlwaysShowNodeLabel(){
		return alwaysShowNodeLabel.isSelected();		
	}
	public boolean isAlwaysShowEdgeLabel(){
		return alwaysShowEdgeLabel.isSelected();		
	}
	public boolean isAlwaysShowInforWindow(){
		return alwaysShowInforWindow.isSelected();		
	}
	public boolean isAlwaysAntiAliasGraph(){
		return alwaysAntiAliasGraph.isSelected();		
	}	
	
//	JCheckBox defaultZoom;
	public void setDefaultZoom(boolean b){
		defaultZoom.setSelected(b);
	}
//	JCheckBox smartZoomBelow;
	public void setSmartZoomBelow(boolean b){
		smartZoomBelow.setSelected(b);
	}
//	JCheckBox alwaysShowEgoNode;
	public void setAlwaysShowEgoNode(boolean b){
		alwaysShowEgoNode.setSelected(b);
	}
//	JCheckBox alwaysCenterEgoNode;
	public void setAlwaysCenterEgoNode(boolean b){
		alwaysCenterEgoNode.setSelected(b);
	}
//	JCheckBox alwaysShowNodeLabel;
	public void setAlwaysShowNodeLabel(boolean b){
		alwaysShowNodeLabel.setSelected(b);
	}
//	JCheckBox alwaysShowEdgeLabel;
	public void setAlwaysShowEdgeLabel(boolean b){
		alwaysShowEdgeLabel.setSelected(b);
	}
//	JCheckBox alwaysShowInforWindow;
	public void setAlwaysShowInforWindow(boolean b){
		alwaysShowInforWindow.setSelected(b);
	}
//	JCheckBox alwaysAntiAliasGraph;
	public void setAlwaysAntiAliasGraph(boolean b){
		alwaysAntiAliasGraph.setSelected(b);
	}
//	
//	JTextField txtDefaultZoom;
	public void setTxtDefaultZoom(String defaultZoom){
		txtDefaultZoom.setText(defaultZoom);
	}
//	JTextField txtSmartZoom;
	public void setTxtSmartZoom(String smartZoom){
		txtSmartZoom.setText(smartZoom);
		
	}
}
