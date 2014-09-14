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
import com.nn.ishop.client.gui.components.CAbstractProductCategoryTreeNode;
import com.nn.ishop.client.gui.components.CDialogsLabelButton;
import com.nn.ishop.client.gui.components.CLineBorder;
import com.nn.ishop.client.gui.components.CTreeCellRenderer;
import com.nn.ishop.client.gui.components.CProductCategoryTreeNode;
import com.nn.ishop.client.gui.components.CTreeRootNode;
import com.nn.ishop.client.logic.admin.ProductCatLogic;
import com.nn.ishop.client.util.Util;
import com.nn.ishop.server.dto.product.ProductCategory;

public class CategoryChooserDialog extends CWizardDialog implements TreeModelListener{
	private static final long serialVersionUID = 1L;
	/** TRUE if user click cancel*/
	boolean isCancel = false;
	JTree productCategoryTree = new JTree();
	CTreeRootNode rootNode = new CTreeRootNode(new String("root"));
	DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
	ProductCategory choosenCat = null;	
	public CategoryChooserDialog(JFrame frame, boolean modal,
			String dialogMessage, String header1, String header2) {
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
	void createDialogArea(){
		 //-- Load treee
        productCategoryTree.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(1,0,0,0),
				new CLineBorder(null, CLineBorder.DRAW_ALL_BORDER)
				)
				);
		treeModel.addTreeModelListener(this);
		
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
						setSelectCat();
					}
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
		
		
		getContentPane().add(productCategoryTree, BorderLayout.CENTER);
		
		CNavigatorPanel nav = new CNavigatorPanel();
		nav.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
		nav.setBorder(null);
		CDialogsLabelButton chooseButton
		= new CDialogsLabelButton(Language.getInstance().getString("buttonSelect")/*
				,Util.getIcon("dialog/btn_normal.png")
				,Util.getIcon("dialog/btn_over.png")*/
		);
		chooseButton.addActionListener(new AbstractAction(){
			public void actionPerformed(ActionEvent e) {	
				setSelectCat();
			}
		});
		CDialogsLabelButton cancelButton
		= new CDialogsLabelButton(Language.getInstance().getString("buttonCancel")/*
				,Util.getIcon("dialog/btn_normal.png")
				,Util.getIcon("dialog/btn_over.png")*/
		);
		cancelButton.addActionListener(new AbstractAction(){
			public void actionPerformed(ActionEvent e) {
				// set null to avoid add new or update
				doCancelAction() ;
				dispose();
			}
		});
		nav.addButton(chooseButton);nav.addButton(cancelButton);
		nav.setBackground(Color.WHITE);
		updateNavigatorPanel(nav);
	}
	void setSelectCat(){
		TreePath path = productCategoryTree.getSelectionPath();
		CAbstractProductCategoryTreeNode node = (CAbstractProductCategoryTreeNode)path.getLastPathComponent();
		if(node instanceof CProductCategoryTreeNode && node.isLeaf()){
			choosenCat = (ProductCategory)node.getUserObject();
			setVisible(false);
		}else{
			SystemMessageDialog.showMessageDialog(null, 
					Language.getInstance().getString("require.choose.category"), 
					SystemMessageDialog.SHOW_OK_OPTION);
		}
	}
	protected void windowClosedHandler(){
		isCancel = true;
	}
	@Override
	public void doCancelAction() {
		isCancel = true;
	}

	@Override
	public boolean isValidData() {
		return false;
	}

	@Override
	public void panelActive() {
		
	}

	@Override
	public void panelDeactive() {
		
	}

	@Override
	public void performFinish() {
		
	}
	
	public ProductCategory getReturnValue(){
		return choosenCat;
	}
	
	public void treeNodesChanged(TreeModelEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void treeNodesInserted(TreeModelEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void treeNodesRemoved(TreeModelEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void treeStructureChanged(TreeModelEvent e) {
		// TODO Auto-generated method stub
		
	}

}
