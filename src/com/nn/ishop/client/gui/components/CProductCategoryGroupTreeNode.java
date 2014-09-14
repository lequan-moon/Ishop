package com.nn.ishop.client.gui.components;

import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;

import com.nn.ishop.client.util.Util;

public class CProductCategoryGroupTreeNode extends CAbstractProductCategoryTreeNode {
	private ImageIcon icon = Util.getIcon("tree/nodeg.png");

	public ImageIcon getIcon() {
		return icon;
	}

	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}

	public CProductCategoryGroupTreeNode() {
		super();
	}

	public CProductCategoryGroupTreeNode(Object userObject, boolean allowsChildren) {
		super(userObject, allowsChildren);
	}

	public CProductCategoryGroupTreeNode(Object userObject) {
		super(userObject);
	}
}
