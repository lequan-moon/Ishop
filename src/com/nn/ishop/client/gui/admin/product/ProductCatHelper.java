package com.nn.ishop.client.gui.admin.product;

import java.awt.Toolkit;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

import com.nn.ishop.client.CActionEvent.CAction;
import com.nn.ishop.client.config.SystemConfiguration;
import com.nn.ishop.client.gui.components.CAbstractProductCategoryTreeNode;
import com.nn.ishop.client.gui.components.CProductCategoryGroupTreeNode;
import com.nn.ishop.client.gui.components.CProductCategoryTreeNode;
import com.nn.ishop.client.gui.components.CProductSectionTreeNode;
import com.nn.ishop.client.gui.components.CTreeRootNode;
import com.nn.ishop.client.gui.dialogs.ProductCategoryDialog;
import com.nn.ishop.client.gui.dialogs.ProductCategoryGroupDialog;
import com.nn.ishop.client.gui.dialogs.ProductSectionDialog;
import com.nn.ishop.client.logic.admin.ProductCatLogic;
import com.nn.ishop.server.dto.AbstractIshopEntity;
import com.nn.ishop.server.dto.product.ProductCategory;
import com.nn.ishop.server.dto.product.ProductCategoryGroup;
import com.nn.ishop.server.dto.product.ProductSection;

/**
 * This class help solving client side operation related to product category, section, category group 
 * It may contains some method interact with Logic object
 * @author nguyennghia
 *
 */
public class ProductCatHelper {
	
	/**
	 * Perform delte action from tree to storage
	 * @param productCategoryTree
	 * @param treeModel
	 * @param node
	 */
	public static void performTreeDeleteAction(
			JTree productCategoryTree, 
			DefaultTreeModel treeModel, 
			CAbstractProductCategoryTreeNode node){
		TreePath currentSelection = productCategoryTree.getSelectionPath();
        if (currentSelection != null) {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)
                         (currentSelection.getLastPathComponent());
            AbstractIshopEntity userObject = (AbstractIshopEntity)currentNode.getUserObject();
            
            MutableTreeNode parent = (MutableTreeNode)(currentNode.getParent());
            if (parent != null) {
                try {
                	//-- remove from database
                	ProductCatLogic.getInstance().deleteCategory(userObject);
                	//-- remove node from tree
					treeModel.removeNodeFromParent(currentNode);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return;
            }
        } 
        // Either there was no selection, or the root was selected.
        Toolkit.getDefaultToolkit().beep();
	}
	
	/**
	 * Perform update action from tree to storage
	 * @param productCategoryTree
	 * @param treeModel
	 * @param node
	 */
	public static void performTreeUpdateAction(
			JTree productCategoryTree, 
			DefaultTreeModel treeModel, 
			CAbstractProductCategoryTreeNode node
			){	
		if(node instanceof CProductSectionTreeNode){
			
			ProductSection section =(ProductSection)node.getUserObject();  
				section = ProductCatHelper.createOrModifyProductSection(
						section);			
			if(section != null){
				try {
					ProductCatLogic.setExtraEntityInfor(section, CAction.UPDATE);
					section = ProductCatLogic.getInstance().saveSectionInfor(section, 
							SystemConfiguration.LP_PD_UPDATE_SECTION);
					node.setUserObject(section);	
					//- notify the model for the node changed
					treeModel.nodeChanged(node);
				} catch (Exception e) {
					e.printStackTrace();
				}										
				
			}				
        }else if(node instanceof CProductCategoryGroupTreeNode){
			ProductCategoryGroup catGroup = (ProductCategoryGroup)node.getUserObject(); 
			catGroup = ProductCatHelper.createOrModifyProductCategoryGroup(
					catGroup, catGroup.getSectionId(), false);			
			if(catGroup != null){									
				try {
					ProductCatLogic.setExtraEntityInfor(catGroup, CAction.UPDATE);
					catGroup = ProductCatLogic.getInstance().saveCatGroupInfor(catGroup, 
							SystemConfiguration.LP_PD_UPDATE_CAT_GROUP);
					node.setUserObject(catGroup);
					treeModel.nodeChanged(node);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
        }else if(node instanceof CProductCategoryTreeNode){		        	
        	//-- parent cat = -1
        	ProductCategory pCat = (ProductCategory)node.getUserObject();
        	int parentCatId = pCat.getCategoryId();
        	int catGroupId = pCat.getCategoryGroup();
        	
        	pCat =  ProductCatHelper.createOrModifyProductCategory(
        			pCat, parentCatId, catGroupId, false);
        	if(pCat != null){
        		try {
        			ProductCatLogic.setExtraEntityInfor(pCat, CAction.UPDATE);
        			pCat = ProductCatLogic.getInstance().saveCatInfor(pCat, 
        					SystemConfiguration.LP_PD_UPDATE_CAT);
					node.setUserObject(pCat);
					treeModel.nodeChanged(node);
				} catch (Exception e) {
					e.printStackTrace();
				}
        	}
        }				
		Toolkit.getDefaultToolkit().beep();
	}	
	
	/**
	 * Perform add action from tree to storage
	 * @param productCategoryTree Tree
	 * @param treeModel Tree model
	 * @param node current selected node
	 */
	public static void performTreeAddAction(
			JTree productCategoryTree, 
			DefaultTreeModel treeModel, 
			CAbstractProductCategoryTreeNode node
			){
		
		CAbstractProductCategoryTreeNode childNode = null;
		if(node instanceof CTreeRootNode){
			ProductSection newSection = ProductCatHelper.createOrModifyProductSection(
					null);			
			if(newSection != null){
				try {
					ProductCatLogic.setExtraEntityInfor(newSection, CAction.ADD);										
					newSection = ProductCatLogic.getInstance().saveSectionInfor(newSection, SystemConfiguration.LP_PD_INSERT_SECTION);					
				} catch (Exception e) {
					e.printStackTrace();
				}					
				childNode = new CProductSectionTreeNode(newSection);						
			}					
		}else if(node instanceof CProductSectionTreeNode){
			//-- get section id
			int sectionId = ((ProductSection)node.getUserObject()).getSectionId();					
			ProductCategoryGroup newCatGroup = ProductCatHelper.createOrModifyProductCategoryGroup(
					null, sectionId, false);
			
			try {
				if(newCatGroup != null){
					ProductCatLogic.setExtraEntityInfor(newCatGroup, CAction.ADD);					
					newCatGroup = ProductCatLogic.getInstance().saveCatGroupInfor(
							newCatGroup, SystemConfiguration.LP_PD_INSERT_CAT_GROUP
							);
					
					childNode = new CProductCategoryGroupTreeNode(newCatGroup);						
				}	
			} catch (Exception e) {
				e.printStackTrace();
			}
        }else if(node instanceof CProductCategoryGroupTreeNode){
        	//-- parent cat = -1
        	int catGroupId = ((ProductCategoryGroup)node.getUserObject()).getGoupId();		        	
        	ProductCategory productCat =  ProductCatHelper.createOrModifyProductCategory(
        			null, -1, catGroupId, false);        	
        	try {
				if(productCat != null){
					ProductCatLogic.setExtraEntityInfor(productCat, CAction.ADD);
					productCat = ProductCatLogic.getInstance().saveCatInfor(
							productCat, SystemConfiguration.LP_PD_INSERT_CAT
							);        		
					childNode = new CProductCategoryTreeNode(productCat);
				}
			} catch (Exception e) {				
				e.printStackTrace();
			}
        }else if(node instanceof CProductCategoryTreeNode){		        	
        	//-- parent cat = -1
        	ProductCategory pCat = (ProductCategory)node.getUserObject();
        	int parentCatId = pCat.getCategoryId();
        	int catGroupId = pCat.getCategoryGroup();
        	
        	ProductCategory productCat =  ProductCatHelper.createOrModifyProductCategory(
        			null, parentCatId, catGroupId, false);        	
        	try {
				if (productCat != null) {
					//-- Set user, time insert to new category
					ProductCatLogic.setExtraEntityInfor(productCat, CAction.ADD);
					productCat = ProductCatLogic.getInstance().saveCatInfor(
							productCat, SystemConfiguration.LP_PD_INSERT_CAT);
					childNode = new CProductCategoryTreeNode(productCat);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
		if(childNode != null){
			//-- The last most important action
			node.add(childNode);
			treeModel.insertNodeInto(childNode, node, node.getChildCount()-1);
			productCategoryTree.scrollPathToVisible(new TreePath(childNode.getPath()));
			Toolkit.getDefaultToolkit().beep();
		}		
	}
	
	/**
	 * Show dialog to add product section
	 * @param section, instance of ProductSection. if null that means create new and not null mean update
	 * @param actionEvent
	 * @return
	 */
	public static ProductSection createOrModifyProductSection(
			ProductSection section)
	{		
		ProductSectionDialog d = new ProductSectionDialog(null, true, "", 
				"", "", section);
		d.setVisible(true);
		section = d.getReturnValue();
		return section;
	}
	
	
	/**
	 * Show dialog to help user adding product category group
	 * @param catGroup instance of ProductCategoryGroup. if null, it means create new and not null mean update
	 * @param sectionId
	 * @param isShowSectionId
	 * @param actionEvent
	 * @return
	 */
	public static ProductCategoryGroup createOrModifyProductCategoryGroup(
			ProductCategoryGroup catGroup, 
			int sectionId,
			boolean isShowSectionId)
	{		
		ProductCategoryGroupDialog d = new ProductCategoryGroupDialog(null, true, "", 
				"", "", catGroup,sectionId,isShowSectionId);
		d.setVisible(true);
		catGroup = d.getReturnValue();
		return catGroup;
	}
	
	/**
	 * Show dialog to help user adding product category
	 * @param productCat instance of ProductCategory. if null that means create new and not null mean update
	 * @param parentCatId integer value for category id 
	 * @param catGroupId
	 * @param isShowAllInfor
	 * @param action
	 * @return
	 */
	public static ProductCategory createOrModifyProductCategory(
			ProductCategory productCat, int parentCatId, int catGroupId,
			boolean isShowAllInfor)
	{		
		ProductCategoryDialog d = new ProductCategoryDialog(null, true, "", 
				"", "", productCat,parentCatId,catGroupId,isShowAllInfor);
		d.setVisible(true);
		productCat = d.getReturnValue();
		return productCat;
	}
}
