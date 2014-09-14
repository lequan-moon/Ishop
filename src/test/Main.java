package test;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

public class Main extends JFrame {
	  private JTree m_simpleTree;

	  private DefaultMutableTreeNode m_rootNode;

	  public Main() {
	    m_rootNode = new DefaultMutableTreeNode("A");

	    DefaultMutableTreeNode enggNode = new DefaultMutableTreeNode("B");
	    DefaultMutableTreeNode markNode = new DefaultMutableTreeNode("C");
	    DefaultMutableTreeNode hrNode = new DefaultMutableTreeNode("D");

	    m_rootNode.add(enggNode);
	    m_rootNode.add(markNode);
	    m_rootNode.add(hrNode);
	    m_simpleTree = new JTree(m_rootNode);

	    DefaultTreeCellRenderer cellRenderer = (DefaultTreeCellRenderer) m_simpleTree.getCellRenderer();

	    cellRenderer.setOpenIcon(new ImageIcon("nodes.gif"));
	    cellRenderer.setClosedIcon(new ImageIcon("nodeg.gif"));
	    cellRenderer.setLeafIcon(new ImageIcon("nodec.gif"));

	    cellRenderer.setBackgroundNonSelectionColor(new Color(255, 255, 221));
	    cellRenderer.setBackgroundSelectionColor(new Color(0, 0, 128));
	    cellRenderer.setBorderSelectionColor(Color.black);
	    cellRenderer.setTextSelectionColor(Color.white);
	    cellRenderer.setTextNonSelectionColor(Color.blue);

	    JScrollPane scrollPane = new JScrollPane(m_simpleTree);

	    add(scrollPane);
	  }
	  public static void main(String[] arg) {
	    Main m = new Main();

	    m.setVisible(true);
	    m.setSize(new Dimension(450, 300));
	    m.validate();
	  }
	}
