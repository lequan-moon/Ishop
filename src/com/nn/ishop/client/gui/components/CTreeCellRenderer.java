package com.nn.ishop.client.gui.components;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

public class CTreeCellRenderer extends DefaultTreeCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		if (value instanceof CAbstractCompanyTreeNode) {
			CAbstractCompanyTreeNode nodeCompany = (CAbstractCompanyTreeNode) value;
			if (nodeCompany.getIcon() != null) {
				/* a set of open/close/leaf icon */
				setClosedIcon(nodeCompany.getIcon());
				setOpenIcon(nodeCompany.getIcon());
				setLeafIcon(nodeCompany.getIcon());
				setIcon(nodeCompany.getIcon());
			} else {
				setClosedIcon(getDefaultClosedIcon());
				setLeafIcon(getDefaultLeafIcon());
				setOpenIcon(getDefaultOpenIcon());
			}
		}
		if (value instanceof CAbstractProductCategoryTreeNode) {
			CAbstractProductCategoryTreeNode node = (CAbstractProductCategoryTreeNode) value;
			if (node.getIcon() != null) {
				/* a set of open/close/leaf icon */
				setClosedIcon(node.getIcon());
				setOpenIcon(node.getIcon());
				setLeafIcon(node.getIcon());
				setIcon(node.getIcon());
			} else {
				setClosedIcon(getDefaultClosedIcon());
				setLeafIcon(getDefaultLeafIcon());
				setOpenIcon(getDefaultOpenIcon());
			}
		}
		super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

		return this;
	}
}
