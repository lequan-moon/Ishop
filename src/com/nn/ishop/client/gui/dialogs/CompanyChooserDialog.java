package com.nn.ishop.client.gui.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.components.CAbstractCompanyTreeNode;
import com.nn.ishop.client.gui.components.CAbstractProductCategoryTreeNode;
import com.nn.ishop.client.gui.components.CCompanyTreeNode;
import com.nn.ishop.client.gui.components.CDialogsLabelButton;
import com.nn.ishop.client.gui.components.CLineBorder;
import com.nn.ishop.client.gui.components.CProductCategoryTreeNode;
import com.nn.ishop.client.gui.components.CTreeCellRenderer;
import com.nn.ishop.client.gui.components.CTreeRootNode;
import com.nn.ishop.client.logic.admin.CompanyLogic;
import com.nn.ishop.client.util.Util;
import com.nn.ishop.server.dto.company.Company;
import com.nn.ishop.server.dto.product.ProductCategory;

public class CompanyChooserDialog extends CWizardDialog implements TreeModelListener {
	private static final long serialVersionUID = 1L;
	boolean isCancel = false;
	JTree companyListTree = new JTree();
	CTreeRootNode rootNode = new CTreeRootNode(new String("root"));
	DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
	CompanyLogic companyLogic;
	Company choosenCompany = null;

	public CompanyChooserDialog(JFrame frame, boolean modal, String dialogMessage, String header1, String header2) {
		super(frame, modal, dialogMessage, header1, header2);
		setIconImage(Util.getImage("logo32.png"));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		createDialogArea();
		updateNorthPanel(new JPanel());
		this.setPreferredSize(new Dimension(400, 250));
		pack();
		this.setResizable(true);
		this.setAlwaysOnTop(true);
		centerDialog(this);
		setFocusable(true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				windowClosedHandler();
				super.windowClosing(e);
			}
		});
	}

	private void createDialogArea() {
		companyListTree.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(1, 0, 0, 0), new CLineBorder(null,
				CLineBorder.DRAW_ALL_BORDER)));
		treeModel.addTreeModelListener(this);
		if (companyLogic == null) {
			companyLogic = CompanyLogic.getInstance();
		}
		Company headCompany = companyLogic.getCompanyById(0);
		CCompanyTreeNode headNode = new CCompanyTreeNode(headCompany);
		List<Company> lstChildren = CompanyLogic.getListChildrenCompany(headCompany.getId());
		headNode = CompanyLogic.loadTreeListCompany(headNode, lstChildren);

		rootNode.add(headNode);

		companyListTree.setModel(treeModel);
		companyListTree.setEditable(false);
		companyListTree.setCellRenderer(new CTreeCellRenderer());
		companyListTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		companyListTree.setShowsRootHandles(true);
		companyListTree.setRootVisible(true);
		companyListTree.requestFocus();

		companyListTree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = companyListTree.getClosestRowForLocation(e.getX(), e.getY());
				companyListTree.setSelectionRow(row);
				TreePath path = companyListTree.getSelectionPath();
				if (path.getLastPathComponent() instanceof CAbstractCompanyTreeNode) {
					CAbstractCompanyTreeNode node = (CAbstractCompanyTreeNode) path.getLastPathComponent();
					Object userObject = node.getUserObject();

					// -- Double click event by left button
					if (e.getClickCount() == 2 && e.getButton() == 1) {
						if (userObject instanceof Company) {
							setSelectCompany();
						}
					}
				}
			}
		});

		getContentPane().add(companyListTree, BorderLayout.CENTER);
	}

	public void setSelectCompany() {
		TreePath path = companyListTree.getSelectionPath();
		CAbstractCompanyTreeNode node = (CAbstractCompanyTreeNode) path.getLastPathComponent();
		if (node instanceof CAbstractCompanyTreeNode) {
			choosenCompany = (Company) node.getUserObject();
			setVisible(false);
		}
	}

	public Company getReturnValue() {
		return choosenCompany;
	}

	protected void windowClosedHandler() {
		isCancel = true;
	}
	public void treeNodesChanged(TreeModelEvent arg0) {
		// TODO Auto-generated method stub

	}
	public void treeNodesInserted(TreeModelEvent arg0) {
		// TODO Auto-generated method stub

	}
	public void treeNodesRemoved(TreeModelEvent arg0) {
		// TODO Auto-generated method stub

	}
	public void treeStructureChanged(TreeModelEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void performFinish() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doCancelAction() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isValidData() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void panelActive() {
		// TODO Auto-generated method stub

	}

	@Override
	public void panelDeactive() {
		// TODO Auto-generated method stub

	}

}
