package com.nn.ishop.client.gui.admin.company;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import com.nn.ishop.client.CActionEvent;
import com.nn.ishop.client.CActionListener;
import com.nn.ishop.client.gui.AbstractGUIManager;
import com.nn.ishop.client.gui.GUIActionEvent;
import com.nn.ishop.client.gui.GUIActionListener;
import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.components.CAbstractCompanyTreeNode;
import com.nn.ishop.client.gui.components.CCompanyTreeNode;
import com.nn.ishop.client.gui.components.CLineBorder;
import com.nn.ishop.client.gui.components.CTextField;
import com.nn.ishop.client.gui.components.CTreeCellRenderer;
import com.nn.ishop.client.gui.components.CTreeRootNode;
import com.nn.ishop.client.gui.dialogs.SystemMessageDialog;
import com.nn.ishop.client.logic.admin.CompanyLogic;
import com.nn.ishop.server.dto.company.Company;

public class CompanyListManager extends AbstractGUIManager implements CActionListener, GUIActionListener, ListSelectionListener, TableModelListener,
		TreeModelListener {

	JTree companyListTree;
	CTreeRootNode rootNode = new CTreeRootNode(new String("root"));
	DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);

	CompanyLogic companyLogic;
	List<Company> lstCompanies;
	Vector<CActionListener> lstCActionListener = new Vector<CActionListener>();
	JToolBar whListToolbar;
	CTextField txtSearch;
	public CompanyListManager(String locale) {
		setLocale(locale);
		init();
	}

	void init() {
		if (getLocale() != null && !getLocale().equals("")) {
			initTemplate(this, "admin/congty/companyListPage.xml", getLocale());
		} else {
			initTemplate(this, "admin/congty/companyListPage.xml");
		}
		render();
		prepareData();
		bindEventHandlers();

		// -- Implement content here

	}

	private void prepareData() {
		if (companyLogic == null) {
			companyLogic = CompanyLogic.getInstance();
		}
		Company headCompany = companyLogic.getCompanyById(1);
		CCompanyTreeNode headNode = new CCompanyTreeNode(headCompany);
		List<Company> lstChildren = CompanyLogic.getListChildrenCompany(headCompany.getId());
		headNode = CompanyLogic.loadTreeListCompany(headNode, lstChildren);
		rootNode.add(headNode);

	}

	@Override
	protected void applyStyles() {
		whListToolbar.setBorder(BorderFactory.createCompoundBorder(new CLineBorder(null, CLineBorder.DRAW_BOTTOM_BORDER), new EmptyBorder(1, 1, 1, 1)));
	}

	@Override
	protected void bindEventHandlers() {		
		treeModel.addTreeModelListener(this);
		companyListTree.addKeyListener((new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				TreePath path = companyListTree.getSelectionPath();
				if (path == null) {
					return;
				}
				if (e.getKeyCode() == KeyEvent.VK_CONTEXT_MENU) {
					CAbstractCompanyTreeNode node = (CAbstractCompanyTreeNode) companyListTree.getLastSelectedPathComponent();
					Object userObject = node.getUserObject();
					Rectangle rec = companyListTree.getPathBounds(path);
					showTreePopup(rec.x + 20, rec.y + 10, node, userObject);
				} else if (e.getKeyCode() == KeyEvent.VK_F2) {
					addNode();
				} else if (e.getKeyCode() == KeyEvent.VK_DELETE) {
					deleteNode();
				} else if (e.getKeyCode() == KeyEvent.VK_F3) {
					updateNode();
				}
				super.keyReleased(e);
			}
		}));
		companyListTree.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int row = companyListTree.getClosestRowForLocation(e.getX(), e.getY());
				companyListTree.setSelectionRow(row);
				TreePath path = companyListTree.getSelectionPath();
				if(path == null || path.getLastPathComponent() == null)
					return;
				CCompanyTreeNode node = (CCompanyTreeNode) path.getLastPathComponent();
				Company obj = (Company) node.getUserObject();
				if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 1) {
					CActionEvent event = new CActionEvent(this, CActionEvent.COMPANY_NODE_SELECT, obj);
					fireCActionEvent(event);
				} else if (e.getButton() == MouseEvent.BUTTON3 && e.getClickCount() == 1) {
					CAbstractCompanyTreeNode node2 = (CAbstractCompanyTreeNode) companyListTree.getLastSelectedPathComponent();
					Object userObject = node2.getUserObject();
					Rectangle rec = companyListTree.getPathBounds(path);
					showTreePopup(rec.x + 20, rec.y + 10, node2, userObject);
				}
			}
		});
		companyListTree.setModel(treeModel);
		companyListTree.setEditable(false);
		companyListTree.setCellRenderer(new CTreeCellRenderer());
		companyListTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		companyListTree.setShowsRootHandles(true);
		companyListTree.setRootVisible(true);
		companyListTree.requestFocus();
		
		txtSearch.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtSearch.setText("");
				super.focusGained(e);
			}
			@Override
			public void focusLost(FocusEvent e) {
				txtSearch.setText(Language.getInstance().getString("defaultSearchText"));
				super.focusLost(e);
			}
		});
	}
	void showTreePopup(int X, int Y, final CAbstractCompanyTreeNode node, final Object userObject) {

		JPopupMenu popup = new JPopupMenu();
		JMenuItem addNode = new JMenuItem(Language.getInstance().getString("company.tree.add.command"));
		JMenuItem changeNode = new JMenuItem(Language.getInstance().getString("company.tree.modify.command"));
		JMenuItem deleteNode = new JMenuItem(Language.getInstance().getString("company.tree.delete.command"));

		addNode.setBackground(Color.WHITE);
		changeNode.setBackground(Color.WHITE);
		deleteNode.setBackground(Color.WHITE);

		addNode.addActionListener(new AbstractAction() {
			private static final long serialVersionUID = -1560157956650400918L;

			public void actionPerformed(ActionEvent e) {
				addNode();
			}
		});
		changeNode.addActionListener(new AbstractAction() {
			private static final long serialVersionUID = -1560157956650400918L;

			public void actionPerformed(ActionEvent e) {
				updateNode();
			}
		});
		deleteNode.addActionListener(new AbstractAction() {
			private static final long serialVersionUID = -1560157956650400918L;

			public void actionPerformed(ActionEvent e) {
				deleteNode();
			}
		});

		popup.add(addNode);
		// -- avoid chaging root node
		if (node.getParent() != null)
			popup.add(changeNode);
		// -- not delete root node
		if (node.getParent() != null)
			popup.add(deleteNode);

		popup.show(companyListTree, X, Y);
	}

	@Override
	public Object getData(String var) {
		return null;
	}

	@Override
	public void update() {
	}

	@Override
	public void updateList() {
	}

	public void cActionPerformed(CActionEvent action) {
	}

	public void fireCActionEvent(CActionEvent event) {
		for (CActionListener listener : lstCActionListener) {
			listener.cActionPerformed(event);
		}
	}

	public void guiActionPerformed(GUIActionEvent action) {
	}

	public void valueChanged(ListSelectionEvent e) {
	}

	public void tableChanged(TableModelEvent e) {
	}

	protected void checkPermission() {
	}

	public void addCActionListener(CActionListener al) {
		lstCActionListener.add(al);
	}

	public void removeCActionListener(CActionListener al) {
		lstCActionListener.remove(al);
	}

	void addNode() {
		TreePath path = companyListTree.getSelectionPath();
		if (path == null)
			return;
		CCompanyTreeNode node = (CCompanyTreeNode) path.getLastPathComponent();
		CompanyTreeHelper.performTreeAddAction(companyListTree, treeModel, node);
	}
    
	void deleteNode() {
		TreePath path = companyListTree.getSelectionPath();
		if (path == null) {
			System.out.println("Not select a node!");
			return;
		}
		CCompanyTreeNode node = (CCompanyTreeNode) path.getLastPathComponent();
		// - check if current node has child node
		if (node.getChildCount() > 0) {
			int response = SystemMessageDialog.showConfirmDialog(null, Language.getInstance().getString("confirm.delete.multi.node"),
					SystemMessageDialog.SHOW_OK_CANCEL_OPTION);
			// -- user click OK
			if (response == 0)
				CompanyTreeHelper.performTreeDeleteAction(companyListTree, treeModel, node);
		} else {
			int response = SystemMessageDialog.showConfirmDialog(null, Language.getInstance().getString("confirm.delete.node"),
					SystemMessageDialog.SHOW_OK_CANCEL_OPTION);
			if (response == 0)
				CompanyTreeHelper.performTreeDeleteAction(companyListTree, treeModel, node);
		}
	}

	/**
	 * Update data
	 */
	void updateNode() {
		TreePath path = companyListTree.getSelectionPath();
		if (path == null)
			return;
		CCompanyTreeNode node = (CCompanyTreeNode) path.getLastPathComponent();
		CompanyTreeHelper.performTreeUpdateAction(companyListTree, treeModel, node);
	}

	public void treeNodesChanged(TreeModelEvent e) {
	}

	public void treeNodesInserted(TreeModelEvent e) {
	}

	public void treeNodesRemoved(TreeModelEvent e) {
	}

	public void treeStructureChanged(TreeModelEvent e) {
	}
	
	Map<String, List<String>> lastSearchResult = new HashMap<String, List<String>>();
	String lastSearchStr = "NONE";
	public DefaultMutableTreeNode searchNode(String nodeStr) {		
		if(!lastSearchStr.equals(nodeStr) && lastSearchResult.containsKey(lastSearchStr))
			lastSearchResult.remove(lastSearchStr);
		List<String> lastSearchValue = lastSearchResult.get(nodeStr) != null?
				lastSearchResult.get(nodeStr):new ArrayList<String>();
	    DefaultMutableTreeNode node = null;
	    Enumeration e = rootNode.breadthFirstEnumeration();
	    while (e.hasMoreElements()) {
	      node = (DefaultMutableTreeNode) e.nextElement();
	      if (node.getUserObject().toString().indexOf(nodeStr) != -1 
	    		  && !lastSearchValue.contains(node.getUserObject().toString())) {
	    	  lastSearchValue.add(node.getUserObject().toString());
	    	  lastSearchResult.put(nodeStr, lastSearchValue);
	    	  lastSearchStr= nodeStr;
	        return node;
	      }
	    }
	    lastSearchStr= nodeStr;
	    return null;
	}
	public Action ACT_SEARCH = new AbstractAction(){		
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			DefaultMutableTreeNode node = searchNode(txtSearch.getText());
	        if (node != null) {
	          TreeNode[] nodes = treeModel.getPathToRoot(node);
	          TreePath path = new TreePath(nodes);
	          companyListTree.scrollPathToVisible(path);
	          companyListTree.setSelectionPath(path);
	        } else {
	        	if(lastSearchResult.size() == 0)
		        	SystemMessageDialog.showMessageDialog(null,
		        			 Language.getInstance().getString("search.company.not.found") + txtSearch.getText() ,  
		        			 SystemMessageDialog.SHOW_OK_OPTION);
	        	else{
	        		SystemMessageDialog.showMessageDialog(null,
		        			 Language.getInstance().getString("search.company.reach.end") + lastSearchResult.toString() ,  
		        			 SystemMessageDialog.SHOW_OK_OPTION);
	        	}
	        }
		}
	};
	public Action ACT_DELETE = new AbstractAction(){		
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			deleteNode();
		}
	};
	
	public Action ACT_REFRESH = new AbstractAction(){		
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			updateNode();
		}
	};
	public Action ACT_ADD = new AbstractAction(){		
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			addNode();
		}
	};
}
