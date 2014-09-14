package com.nn.ishop.client.gui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.nn.ishop.client.util.Util;

public class NetworkSearchPanel extends JPanel {
	public NetworkSearchPanel(){
		init();
	}
	void init()
	{
		setLayout(new FlowLayout(FlowLayout.LEFT,2,10));
		JPanel networkSearchBar = new JPanel();
		networkSearchBar.setBackground(Color.WHITE);
		networkSearchBar.setBorder(new CLineBorder(null, CLineBorder.DRAW_ALL_BORDER));
		networkSearchBar.setPreferredSize(new Dimension(180,28));
		CButton networkSearchButton = new CButton();
		networkSearchButton.setIcon(Util.getIcon("network/icon_network_search.png"));
		networkSearchButton.setSelectedIcon(Util.getIcon("network/icon_network_search.png"));
		networkSearchButton.setPreferredSize(new Dimension(20,20));
		networkSearchButton.setBorder(null);networkSearchButton.setBorderPainted(false);
		CTextField mainNetworkSearchTxt = new CTextField();
	}
//	<panel id="networkSearchBarContainer" name="networkSearchBarContainer"
//        layout="FlowLayout(FlowLayout.LEFT,2,10)" 
//        constraints="BorderLayout.EAST">
//        <panel id="networkSearchBar" name="networkSearchBar"
//        	layout="FlowLayout(FlowLayout.LEFT,0,2)"
//        	Border="LineBorder"
//        	Background="255, 255, 255"
//        	preferredSize="180,28" visible="true">
//	        <cbutton id="networkSearchButton" BorderPainted="false" 
//	        	Icon="com/shiftthink/connect/client/img/network/icon_network_search.png"	        	
//				SelectedIcon="com/shiftthink/connect/client/img/network/icon_network_search.png"      
//	         	Border="null" Background="255, 255, 255"
//	         	preferredSize="20,20" Action="networkSearchAction"/>
//	        <label id="seachSpaceLabel" text=" " preferredSize="25,20"/>
//	        <ctextfield id="mainNetworkSearchTxt" preferredSize="120,20" Border="EmptyBorder" Background="255, 255, 255"  
//	        	text="$langPack.getString('defaultSearchText')"/>
//        </panel>
//      </panel> 
}
