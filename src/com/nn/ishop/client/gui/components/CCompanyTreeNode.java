package com.nn.ishop.client.gui.components;

import javax.swing.ImageIcon;

import com.nn.ishop.client.util.Util;

public class CCompanyTreeNode extends CAbstractCompanyTreeNode {

	private static final long serialVersionUID = 1L;
	//private ImageIcon companyIcon = Util.getIcon("tree/company.png");
	private ImageIcon icon = Util.getIcon("tree/company.png");
	//private ImageIcon groupIcon = Util.getIcon("tree/group-icon.png");
	
	public ImageIcon getIcon() {
		return icon;
	}

	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}

	public CCompanyTreeNode() {
		super();
	}

	public CCompanyTreeNode(Object userObject, boolean allowsChildren) {
		super(userObject, allowsChildren);
	}

	public CCompanyTreeNode(Object userObject) {
		super(userObject);
	}

}
