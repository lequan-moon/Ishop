package com.nn.ishop.client.gui.components;

import javax.swing.ImageIcon;

import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.util.Util;

public class CTreeRootNode extends CAbstractProductCategoryTreeNode {
	
	public CTreeRootNode() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CTreeRootNode(Object userObject, boolean allowsChildren) {
		super(userObject, allowsChildren);
		// TODO Auto-generated constructor stub
	}

	public CTreeRootNode(Object userObject) {
		super(userObject);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ImageIcon icon = Util.getIcon("tree/noderoot.png");

	public ImageIcon getIcon() {
		return icon;
	}

	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}
	@Override
	public String toString() {
		return Language.getInstance().getString("tree.root.node");
	}
}
