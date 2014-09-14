package com.nn.ishop.client.gui.components;

import javax.swing.ImageIcon;

import com.nn.ishop.client.util.Util;

public class CProductCategoryTreeNode extends CAbstractProductCategoryTreeNode {
	private static final long serialVersionUID = 1L;
	private ImageIcon icon = Util.getIcon("tree/nodec.png");

	public ImageIcon getIcon() {
		return icon;
	}

	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}

	public CProductCategoryTreeNode() {
		super();
	}

	public CProductCategoryTreeNode(Object userObject, boolean allowsChildren) {
		super(userObject, allowsChildren);
	}

	public CProductCategoryTreeNode(Object userObject) {
		super(userObject);
	}

}
