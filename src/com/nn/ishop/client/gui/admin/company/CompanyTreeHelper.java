package com.nn.ishop.client.gui.admin.company;

import java.util.Date;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.nn.ishop.client.CActionEvent.CAction;
import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.components.CCompanyTreeNode;
import com.nn.ishop.client.gui.dialogs.CompanyDialog;
import com.nn.ishop.client.gui.dialogs.ProductSectionDialog;
import com.nn.ishop.client.gui.dialogs.SystemMessageDialog;
import com.nn.ishop.client.logic.admin.CompanyLogic;
import com.nn.ishop.server.dto.company.Company;
import com.nn.ishop.server.dto.product.ProductSection;

public class CompanyTreeHelper {

	public static void performTreeAddAction(JTree companyTreeList, DefaultTreeModel treeModel, CCompanyTreeNode node) {
		boolean isParentRootNode = false;
		try {
			CCompanyTreeNode parentNode = (CCompanyTreeNode) node.getParent();
		} catch (ClassCastException ex) {
			isParentRootNode = true;
		}

		CCompanyTreeNode childNode = null;
		Company parentCompany = (Company) node.getUserObject();
//		int parentType = parentCompany.getType();
//		int childType = 1;
//		if (parentType < 3 && parentCompany.getParent_id() != 0) {
//			childType = parentType + 1;
//		} else {
//			childType = 3;
//		}
		int parentId = parentCompany.getId();
		Company newCompany = createOrModifyCompany(null);
		if (newCompany != null) {
			newCompany.setParent_id(parentId);
			//newCompany.setType(childType);
		} else {
			return;
		}

		CompanyLogic companyLogic = CompanyLogic.getInstance();
		newCompany = companyLogic.insertCompany(newCompany);

		childNode = new CCompanyTreeNode(newCompany);
		if (childNode != null) {
			node.add(childNode);
			treeModel.insertNodeInto(childNode, node, node.getChildCount() - 1);
			companyTreeList.scrollPathToVisible(new TreePath(childNode.getPath()));
		}
	}

	public static void performTreeDeleteAction(JTree companyTreeList, DefaultTreeModel treeModel, CCompanyTreeNode node) {
		Company currentCompany = ((Company) node.getUserObject());
		//-- Avoid delete company with id = '0' (Root company)
		if(currentCompany.getId() == 0){
			SystemMessageDialog.showMessageDialog(null, 
					Language.getInstance().getString("cannot.delete.root.company"), 
					SystemMessageDialog.SHOW_OK_OPTION);
			return;
		}
		
		CompanyLogic companyLogic = CompanyLogic.getInstance();
		currentCompany = companyLogic.updateCompany(currentCompany, CAction.DELETE);
		treeModel.removeNodeFromParent(node);
	}

	public static void performTreeUpdateAction(JTree companyTreeList, DefaultTreeModel treeModel, CCompanyTreeNode node) {
		Company currentCompany = (Company) node.getUserObject();
		currentCompany = createOrModifyCompany(currentCompany);
		if (currentCompany != null) {
			CompanyLogic companyLogic = CompanyLogic.getInstance();
			currentCompany = companyLogic.updateCompany(currentCompany, CAction.UPDATE);

			node.setUserObject(currentCompany);
			treeModel.nodeChanged(node);
		}
	}

	public static Company createOrModifyCompany(Company company) {
		CompanyDialog d = new CompanyDialog(null, true, "", "", "", company);
		d.setVisible(true);
		company = d.getReturnValue();
		return company;
	}
}
