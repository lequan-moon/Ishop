package com.nn.ishop.client.gui.components;

import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;

import com.nn.ishop.client.util.Util;

public abstract class CAbstractProductCategoryTreeNode extends DefaultMutableTreeNode {
	private ImageIcon icon = Util.getIcon("tree/nodeg.png");

	public CAbstractProductCategoryTreeNode() {
		super();
	}

	public CAbstractProductCategoryTreeNode(Object userObject, boolean allowsChildren) {
		super(userObject, allowsChildren);
	}

	public CAbstractProductCategoryTreeNode(Object userObject) {
		super(userObject);
	}

	public ImageIcon getIcon() {
		return icon;
	}

	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}
}
