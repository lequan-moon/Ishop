package test.tree;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import com.nn.ishop.client.gui.components.CAbstractProductCategoryTreeNode;

public class CustomeTreeCellRenderer extends DefaultTreeCellRenderer {

    public CustomeTreeCellRenderer() {
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, 
    		boolean leaf, int row, boolean hasFocus) {
        CAbstractProductCategoryTreeNode node = (CAbstractProductCategoryTreeNode) value;
        if (node.getIcon() != null) {
        	/* a set of open/close/leaf icon*/
            setClosedIcon(node.getIcon());
            setOpenIcon(node.getIcon());
            setLeafIcon(node.getIcon());   
            setIcon(node.getIcon());
        } else {
            //System.out.println(node + " - default");
            setClosedIcon(getDefaultClosedIcon());
            setLeafIcon(getDefaultLeafIcon());
            setOpenIcon(getDefaultOpenIcon());
        }

        super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

        return this;
    }
}