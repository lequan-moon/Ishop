package com.nn.ishop.client.gui.admin.product;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import com.nn.ishop.client.CActionEvent;
import com.nn.ishop.client.CActionListener;
import com.nn.ishop.client.ProgressEvent;
import com.nn.ishop.client.ProgressListener;
import com.nn.ishop.client.gui.AbstractGUIManager;
import com.nn.ishop.client.gui.GUIActionEvent;
import com.nn.ishop.client.gui.GUIActionListener;
import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.components.CAbstractProductCategoryTreeNode;
import com.nn.ishop.client.gui.components.CLineBorder;
import com.nn.ishop.client.gui.components.CTextField;
import com.nn.ishop.client.gui.components.CTreeCellRenderer;
import com.nn.ishop.client.gui.components.CTreeRootNode;
import com.nn.ishop.client.gui.components.CWhitePanel;
import com.nn.ishop.client.gui.dialogs.SystemMessageDialog;
import com.nn.ishop.client.logic.admin.ProductCatLogic;
import com.nn.ishop.server.dto.product.ProductCategory;

public class ProductCatManager extends AbstractGUIManager implements
		GUIActionListener, ProgressListener, CActionListener, TreeModelListener {
	JTree productCategoryTree;
	CTreeRootNode rootNode = new CTreeRootNode(new String("root"));
	DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
	CWhitePanel treePanel;
	JToolBar treeToolbar;
	CWhitePanel catTreeContainer1;
	void init() {
		initTemplate(this, "admin/sanpham/productCatPage.xml");
		render();
		bindEventHandlers();		
	}
	public ProductCatManager(String locale){
		setLocale(locale);
		init();
	}
	
	@Override
	protected void applyStyles() {		
		catTreeContainer1.setBorder(new EmptyBorder(1,0,0,0));
		productCategoryTree.setBorder(BorderFactory.createCompoundBorder(
				new CLineBorder(null, CLineBorder.DRAW_TOP_BORDER),
				new EmptyBorder(1,1,1,1)
				));		
	}
	
	@Override
	protected void bindEventHandlers() {
		treeModel.addTreeModelListener(this);
		productCategoryTree.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				TreePath path = productCategoryTree.getSelectionPath();
				if(path == null) return;
				if(e.getKeyCode() == KeyEvent.VK_CONTEXT_MENU){					
					CAbstractProductCategoryTreeNode node = (CAbstractProductCategoryTreeNode)path.getLastPathComponent();
					Object userObject = node.getUserObject();
					Rectangle rec = productCategoryTree.getPathBounds(path);
					showTreePopup(rec.x + 20, rec.y + 10, node, userObject);
				}else if(e.getKeyCode() == KeyEvent.VK_F2){
					addNode();
				}else if(e.getKeyCode() == KeyEvent.VK_F3){
					updateNode();
				}else if(e.getKeyCode() == KeyEvent.VK_DELETE){					
					deleteNode();
				}
				super.keyReleased(e);
			}
		});
		productCategoryTree.addMouseListener(new MouseAdapter() {			
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = productCategoryTree.getClosestRowForLocation(e.getX(), e.getY());  
				productCategoryTree.setSelectionRow(row);
				TreePath path = productCategoryTree.getSelectionPath();
				CAbstractProductCategoryTreeNode node = (CAbstractProductCategoryTreeNode)path.getLastPathComponent();
				Object userObject = node.getUserObject();
				
				//-- Double click event by left button
				if(e.getClickCount()==2 && e.getButton() == 1){
					if(userObject instanceof ProductCategory){
						ProductCategory pCat = (ProductCategory)userObject;
						if(pCat.getCatList().size() == 0){
							CActionEvent evt = new CActionEvent(this, 
									CActionEvent.LOAD_PRODUCT_BY_CAT_TREE, pCat);
							cActionPerformed(evt);
						}
					}
				}else if(SwingUtilities.isRightMouseButton(e)){//-- Right click
					showTreePopup(e.getX(), e.getY(), node, userObject);
				}				
			}
		});
		
		ProductCatLogic.getInstance().loadTreeData(rootNode);
		productCategoryTree.setModel(treeModel);
		productCategoryTree.setEditable(false);		
		productCategoryTree.setCellRenderer(new CTreeCellRenderer());
		productCategoryTree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		productCategoryTree.setShowsRootHandles(true);
		productCategoryTree.setRootVisible(true);
		productCategoryTree.requestFocus();
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
	private Vector<CActionListener> cActionListenerVector = new Vector<CActionListener>();
	
	public void addCActionListener(CActionListener cActionListener)
	{
		cActionListenerVector.add(cActionListener);
	}
	public void removeCActionListener(CActionListener cActionListener)
	{
		cActionListenerVector.remove(cActionListener);
	}
	
	@SuppressWarnings("unchecked")
	public void guiActionPerformed(GUIActionEvent action) {		
	}

	@Override
	protected void checkPermission() {		
	}	
	
	protected void fireCAction(CActionEvent action){
		for(CActionListener cActionListener:cActionListenerVector)
			cActionListener.cActionPerformed(action);
	}
	
	/* (non-Javadoc)
	 * @see com.nn.ishop.client.CActionListener#cActionPerformed(com.nn.ishop.client.CActionEvent)
	 */
	public void cActionPerformed(CActionEvent action) {		
		fireCAction(action);
	}	
	//-- PROGRESS LISTENER--//
	private Vector<ProgressListener> progressListenerVector = new Vector<ProgressListener>();
	
	public void addProgressListener(ProgressListener progressListener){
		progressListenerVector.add(progressListener);
	}
	public void removeProgressListener(ProgressListener progressListener){
		progressListenerVector.remove(progressListener);
	}
	protected void callProgressListener(ProgressEvent e){
		for(ProgressListener progressListener:progressListenerVector)
			progressListener.progressStatusChanged(e);
	}
	public void progressStatusChanged(ProgressEvent evt) {
		callProgressListener(evt);
		
	}
	
	void addNode(){		
		TreePath path = productCategoryTree.getSelectionPath();
		if(path==null) return;
		CAbstractProductCategoryTreeNode node = (CAbstractProductCategoryTreeNode)path.getLastPathComponent();		
		ProductCatHelper.performTreeAddAction(productCategoryTree, treeModel, node);
	}
	void deleteNode(){
		TreePath path = productCategoryTree.getSelectionPath();
		if(path == null) {
			return;
		}
		CAbstractProductCategoryTreeNode node = (CAbstractProductCategoryTreeNode)path.getLastPathComponent();
		//- check if current node has child node 
		if(node.getChildCount()>0){
			int response = SystemMessageDialog.showConfirmDialog(null, 
					Language.getInstance().getString("confirm.delete.multi.node"),
					SystemMessageDialog.SHOW_OK_CANCEL_OPTION);
			//-- user click OK
			if(response == 0)
				ProductCatHelper.performTreeDeleteAction(productCategoryTree, treeModel, node);
		}else{
			int response = SystemMessageDialog.showConfirmDialog(null, 
					Language.getInstance().getString("confirm.delete.node"),
					SystemMessageDialog.SHOW_OK_CANCEL_OPTION);
			if(response == 0)
			ProductCatHelper.performTreeDeleteAction(productCategoryTree, treeModel, node);
		}
	}
	/**
	 * Update data 
	 */
	void updateNode(){
		TreePath path = productCategoryTree.getSelectionPath();
		if(path==null) return;
		CAbstractProductCategoryTreeNode node = (CAbstractProductCategoryTreeNode)path.getLastPathComponent();
		ProductCatHelper.performTreeUpdateAction(productCategoryTree, treeModel, node);
	}
	void showTreePopup( int X, int Y,			
			final CAbstractProductCategoryTreeNode node,
			final Object userObject){
		
		JPopupMenu popup = new JPopupMenu();
        JMenuItem addNode = new JMenuItem(
        		Language.getInstance().getString("category.tree.add.command"));
        JMenuItem changeNode = new JMenuItem(
        		Language.getInstance().getString("category.tree.modify.command"));
        JMenuItem deleteNode = new JMenuItem(
        		Language.getInstance().getString("category.tree.delete.command"));
        JMenuItem listCatProduct = new JMenuItem(
        		Language.getInstance().getString("category.tree.list.item.command"));
        
        addNode.setBackground(Color.WHITE);
        changeNode.setBackground(Color.WHITE);
        deleteNode.setBackground(Color.WHITE);
        listCatProduct.setBackground(Color.WHITE);
        //================================= ADD NEW NODE ====================================// 
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
        listCatProduct.addActionListener(new AbstractAction() {
			private static final long serialVersionUID = -1560157956650400918L;
			public void actionPerformed(ActionEvent e) {
				if(userObject instanceof ProductCategory){
		        	ProductCategory pc = (ProductCategory)userObject;
		        	if(pc.getCatList().size() == 0){
		        		CActionEvent evt = new CActionEvent(this, 
								CActionEvent.LOAD_PRODUCT_BY_CAT_TREE, pc);
						cActionPerformed(evt);
		        	}
		        }
            }
        });
        popup.add(addNode);
        //-- avoid chaging root node
        if (node.getParent() != null)
        	popup.add(changeNode);
        //-- not delete root node
        if (node.getParent() != null)
        	popup.add(deleteNode);
        if(userObject instanceof ProductCategory){
        	ProductCategory pc = (ProductCategory)userObject;
        	if(pc.getCatList() == null || pc.getCatList().size() == 0){
        		popup.add(listCatProduct);
        	}
        }        
        popup.show(productCategoryTree, X, Y);
	}
	public void treeNodesChanged(TreeModelEvent e) {
		
	}
	public void treeNodesInserted(TreeModelEvent e) {
		
	}
	public void treeNodesRemoved(TreeModelEvent e) {
		
	}
	public void treeStructureChanged(TreeModelEvent e) {
		
	}
	public Action AC_PCATTREE_ADD_NODE = new AbstractAction(){
		private static final long serialVersionUID = -6798328647251737478L;

		public void actionPerformed(ActionEvent e) {
			addNode();
		}
	};
	public Action AC_PCATTREE_UPDATE_NODE = new AbstractAction(){
		private static final long serialVersionUID = -6798328647251737478L;

		public void actionPerformed(ActionEvent e) {
			updateNode();
		}
	};
	public Action AC_PCATTREE_DEL_NODE = new AbstractAction(){
		private static final long serialVersionUID = -6798328647251737478L;

		public void actionPerformed(ActionEvent e) {
			deleteNode();
		}
	};
	CTextField txtTreeSearch;
	public Action AC_PCATTREE_SEARCH_NODE = new AbstractAction(){
		private static final long serialVersionUID = -6798328647251737478L;

		public void actionPerformed(ActionEvent e) {		
			/*CTextField c = (CTextField)getComponentById(getRootComponent(), "txtTreeSearch");*/
			String searchString = txtTreeSearch.getText();
			if(searchString != null && !searchString.equals("")){
				TreePath startPath = productCategoryTree.getSelectionPath();
				CAbstractProductCategoryTreeNode startNode = null;
				if(startPath !=null){
					startNode = (CAbstractProductCategoryTreeNode)startPath.getLastPathComponent();
				}else{
					startNode = rootNode;
				}
				  
				DefaultMutableTreeNode node = searchNode(searchString, startNode);
				if (node != null) {
		          TreeNode[] nodes = treeModel.getPathToRoot(node);
		          TreePath path = new TreePath(nodes);
		          productCategoryTree.scrollPathToVisible(path);
		          productCategoryTree.setSelectionPath(path);
		        } else {
		        	SystemMessageDialog.showMessageDialog(null, 
		        			Language.getInstance().getString("node.not.found"), 
		        			SystemMessageDialog.SHOW_OK_OPTION);
		        }
			}
		}
	};
	public Action AC_PCATTREE_REFRESH_TREE = new AbstractAction(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {	
			rootNode.removeAllChildren();
		    treeModel.reload();
			ProductCatLogic.getInstance().loadTreeData(rootNode);
		}
	};
	
	public DefaultMutableTreeNode searchNode(String nodeStr, 
			DefaultMutableTreeNode startNode ) {
	    DefaultMutableTreeNode node = null;
	    Enumeration e = startNode.breadthFirstEnumeration();
	    while (e.hasMoreElements()) {
	      node = (DefaultMutableTreeNode) e.nextElement();
	      if (nodeStr.equals(node.getUserObject().toString())) {
	        return node;
	      }
	    }
	    return null;
	}
}
