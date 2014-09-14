package com.nn.ishop.client.gui.components;

import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;

import com.nn.ishop.client.util.Util;

public abstract class CAbstractCompanyTreeNode extends DefaultMutableTreeNode{
	private ImageIcon icon = Util.getIcon("tree/nodeg.png");

	public CAbstractCompanyTreeNode() {
		super();
	}

	public CAbstractCompanyTreeNode(Object userObject, boolean allowsChildren) {
		super(userObject, allowsChildren);
	}

	public CAbstractCompanyTreeNode(Object userObject) {
		super(userObject);
	}

	public ImageIcon getIcon() {
		return icon;
	}

	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}

}
