package com.nn.ishop.client.gui.components;

import javax.swing.ImageIcon;

import com.nn.ishop.client.util.Util;

public class CProductSectionTreeNode extends CAbstractProductCategoryTreeNode {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ImageIcon icon = Util.getIcon("tree/nodes.png");

	public CProductSectionTreeNode() {
		super();
	}

	public CProductSectionTreeNode(Object userObject, boolean allowsChildren) {
		super(userObject, allowsChildren);
	}

	public CProductSectionTreeNode(Object userObject) {
		super(userObject);
	}

	public ImageIcon getIcon() {
		return icon;
	}

	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}	
}
