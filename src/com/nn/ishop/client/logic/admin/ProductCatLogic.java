package com.nn.ishop.client.logic.admin;

import java.math.BigInteger;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

import com.nn.ishop.client.CActionEvent.CAction;
import com.nn.ishop.client.config.SystemConfiguration;
import com.nn.ishop.client.gui.components.CProductCategoryGroupTreeNode;
import com.nn.ishop.client.gui.components.CProductCategoryTreeNode;
import com.nn.ishop.client.gui.components.CProductSectionTreeNode;
import com.nn.ishop.client.gui.components.CTreeRootNode;
import com.nn.ishop.client.logic.CommonLogic;
import com.nn.ishop.client.logic.util.BusinessUtil;
import com.nn.ishop.client.util.Util;
import com.nn.ishop.server.dto.AbstractIshopEntity;
import com.nn.ishop.server.dto.product.ProductCategory;
import com.nn.ishop.server.dto.product.ProductCategoryGroup;
import com.nn.ishop.server.dto.product.ProductSection;
import com.nn.ishop.server.dto.product.ProductUOM;

public class ProductCatLogic extends CommonLogic {
	public static ProductCatLogic instance = null;
	
	/**
	 * 
	 */
	public ProductCatLogic(){
		
	}
	
	/**
	 * @return
	 */
	public static ProductCatLogic getInstance(){
		if(instance == null)
			instance = new ProductCatLogic();
		return instance;
	}
	
	/**
	 * @param catSectionObj
	 * @param localPart ADD or UPDATE local part
	 * @return
	 * @throws Exception
	 */
	public ProductSection saveSectionInfor(ProductSection catSectionObj, 
			String localPart
			)throws Exception{
		RPCServiceClient serviceClient = new RPCServiceClient();
		Options options = serviceClient.getOptions();
		options.setTo(SystemConfiguration.PD_EPR);
		QName qname = new QName(SystemConfiguration.NAMESPACE_URI,
				localPart);
		Object[] params = new Object[] {catSectionObj};
		Class[] types = new Class[] { ProductSection.class };
		Object[] res = serviceClient.invokeBlocking(qname, params,
				types);
		return (ProductSection)res[0];
		
	}
	
	/**
	 * @param catObj
	 * @param localPart
	 * @return
	 * @throws Exception
	 */
	public ProductCategory saveCatInfor(ProductCategory catObj, String localPart
			)throws Exception{
		RPCServiceClient serviceClient = new RPCServiceClient();
		Options options = serviceClient.getOptions();
		options.setTo(SystemConfiguration.PD_EPR);
		QName qname = new QName(SystemConfiguration.NAMESPACE_URI,
				localPart);
		Object[] params = new Object[] {catObj};
		Class[] types = new Class[] { ProductCategory.class };
		Object[] res = serviceClient.invokeBlocking(qname, params,
				types);
		return (ProductCategory)res[0];
		
	}
	
	/**
	 * @param catGroup
	 * @param localPart
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ProductCategoryGroup saveCatGroupInfor(ProductCategoryGroup catGroup,
			String localPart
			) 
		throws Exception{		
			
			RPCServiceClient serviceClient = new RPCServiceClient();
			Options options = serviceClient.getOptions();
			options.setTo(SystemConfiguration.PD_EPR);
			QName qname = new QName(SystemConfiguration.NAMESPACE_URI,
					localPart);			
			
			Object[] params = new Object[] {catGroup};
			Class[] types = new Class[] { ProductCategoryGroup.class };
			Object[] res = serviceClient.invokeBlocking(qname, params,
					types);
			return (ProductCategoryGroup)res[0];
	}
		
	/**
	 * @return
	 * @throws Exception
	 */
	public List<ProductCategoryGroup> loadCatGroupData() throws Exception{
		BusinessUtil<ProductCategoryGroup> bs = new BusinessUtil<ProductCategoryGroup>();
		List<ProductCategoryGroup> catGroupList = bs.webserviceGetDtoList(new Integer(1),
				SystemConfiguration.LP_PD_LOAD_CAT_GROUP,
				new Class[] { ProductCategoryGroup[].class }, 
				new Object[] { new Integer(1) },
				SystemConfiguration.PD_EPR);
		return catGroupList;
	}
	
	/**
	 * Get Product category list by section
	 * @param sectionId
	 * @return
	 * @throws Exception
	 */
	public List<ProductCategoryGroup> loadCatGroupBySection(int sectionId) throws Exception{
		BusinessUtil<ProductCategoryGroup> bs = new BusinessUtil<ProductCategoryGroup>();
		List<ProductCategoryGroup> catGroupList = bs.webserviceGetDtoList(new Integer(1),
				SystemConfiguration.LP_PD_LOAD_CAT_GROUP_BY_SECTION,
				new Class[] { ProductCategoryGroup[].class }, 
				new Object[] {sectionId },
				SystemConfiguration.PD_EPR);
		return catGroupList;
	}
	
	/**
	 * Load product category information
	 * @return
	 * @throws Exception
	 */
	public List<ProductCategory> loadCat() throws Exception{
		BusinessUtil<ProductCategory> bs = new BusinessUtil<ProductCategory>();
		List<ProductCategory> catGroupList = bs.webserviceGetDtoList(1,
				SystemConfiguration.LP_PD_LOAD_CAT,
				new Class[] { ProductCategory[].class }, 
				new Object[] { new Integer(1) },
				SystemConfiguration.PD_EPR);
		return catGroupList;
	}
	
	public List<ProductCategory> loadLeafCat() throws Exception{
		BusinessUtil<ProductCategory> bs = new BusinessUtil<ProductCategory>();
		List<ProductCategory> catGroupList = bs.webserviceGetDtoList(1,
				SystemConfiguration.LP_PD_LOAD_LEAF_CAT,
				new Class[] { ProductCategory[].class }, 
				new Object[] { new Integer(1) },
				SystemConfiguration.PD_EPR);
		return catGroupList;
	}
	
	public ProductCategory getCatById(int catId) throws Exception{
		BusinessUtil<ProductCategory> bs = new BusinessUtil<ProductCategory>();
		ProductCategory cat = bs.webserviceGetDto(1,
				SystemConfiguration.LP_PD_GET_CAT,
				new Class[] { ProductCategory.class }, 
				new Object[] { new Integer(catId) },
				SystemConfiguration.PD_EPR);
		return cat;
	}
	
	public List<ProductUOM> loadUOM() throws Exception{
		BusinessUtil<ProductUOM> bs = new BusinessUtil<ProductUOM>();
		List<ProductUOM> catUOMList = bs.webserviceGetDtoList(1,
				SystemConfiguration.LP_PD_LOAD_UOM,
				new Class[] { ProductUOM[].class }, 
				new Object[] { new Integer(1) },
				SystemConfiguration.PD_EPR);
		return catUOMList;
	}
	
	public ProductUOM getUOM(int uomId) throws Exception{
		BusinessUtil<ProductUOM> bs = new BusinessUtil<ProductUOM>();
		ProductUOM catUOMList = bs.webserviceGetDto(1,
				SystemConfiguration.LP_PD_GET_UOM,
				new Class[] { ProductUOM.class }, 
				new Object[] { new Integer(uomId) },
				SystemConfiguration.PD_EPR);
		return catUOMList;
	}
	
	/**
	 * Load category data of the specify category group
	 * @param catGroupId
	 * @return
	 * @throws Exception
	 */
	public List<ProductCategory> loadCatByCatGroup(int catGroupId) throws Exception{
		BusinessUtil<ProductCategory> bs = new BusinessUtil<ProductCategory>();
		List<ProductCategory> catGroupList = bs.webserviceGetDtoList(1,
				SystemConfiguration.LP_PD_LOAD_CAT_BY_CAT_GROUP,
				new Class[] { ProductCategory[].class }, 
				new Object[] { catGroupId },
				SystemConfiguration.PD_EPR);
		return catGroupList;
	}
	
	public List<ProductCategory> loadSubCat(int parentCatId) throws Exception{
		BusinessUtil<ProductCategory> bs = new BusinessUtil<ProductCategory>();		
		List<ProductCategory> catGroupList = bs.webserviceGetDtoList(1,
				SystemConfiguration.LP_PD_LOAD_SUB_CAT,
				new Class[] { ProductCategory[].class }, 
				new Object[] { parentCatId },
				SystemConfiguration.PD_EPR);
		return catGroupList;
	}
	
	public List<ProductSection> loadSectionData() throws Exception{
		BusinessUtil<ProductSection> bs = new BusinessUtil<ProductSection>();
		List<ProductSection> catGroupList = bs.webserviceGetDtoList(1,
				SystemConfiguration.LP_PD_LOAD_SECTION,
				new Class[] { ProductSection[].class }, 
				new Object[] { new Integer(1) },
				SystemConfiguration.PD_EPR);
		return catGroupList;
	}
	/**
	 * Convert List of ProductCategoryGroup to two dimension Object[][] array
	 * @param catGroupList
	 * @return
	 * @throws Exception
	 */
	public Object[][] convertCatList2CatData(List<ProductCategoryGroup> catGroupList)
	 	throws Exception{
		Object[][] catGroupData = new Object[catGroupList.size()][];
		int i =0;
		for(ProductCategoryGroup obj:catGroupList){
			catGroupData[i] = new Object[]{
					Boolean.FALSE,
					obj.getGroupName(),
					Util.parseString(obj.getSectionId()),
					obj.getGroupDesc(),
					obj.getGoupId()
			};
			i++;
		}
		return catGroupData;
	}
	public void loadTreeData(CTreeRootNode rootNode){
		try {
			List<ProductSection> prodList = ProductCatLogic.getInstance()
					.loadSectionData();
			for (ProductSection sec : prodList) {
				List<ProductCategoryGroup> catGroupLst 
					= ProductCatLogic.getInstance().loadCatGroupBySection(sec.getSectionId());
				sec.setCatGroupList(catGroupLst);
				CProductSectionTreeNode sectionNode = new CProductSectionTreeNode(sec);
				
				for (ProductCategoryGroup pcg : catGroupLst) {
					List<ProductCategory> catLst =  ProductCatLogic.getInstance().loadCatByCatGroup(pcg.getGoupId());					
					pcg.setCatList(catLst);
					CProductCategoryGroupTreeNode pcgNode = new CProductCategoryGroupTreeNode(
							pcg);
					for (ProductCategory pc : catLst) {
						//-- load sub cat
						List<ProductCategory> subCatLst =  ProductCatLogic.getInstance().loadSubCat(pc.getCategoryId());
						pc.setCatList(subCatLst);
						CProductCategoryTreeNode pcNode = new CProductCategoryTreeNode(pc);
						
						if (subCatLst.size() > 0)
							listSubCategory(subCatLst, pcNode);
						else
							updateCatNodeLabel(pcNode);
						
						pcgNode.add(pcNode);
					}
					sectionNode.add(pcgNode);
				}
				rootNode.add(sectionNode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void updateCatNodeLabel(CProductCategoryTreeNode node) {		
		ProductCategory pc = (ProductCategory) node.getUserObject();	
		try {
			if(pc.getCatList() == null || pc.getCatList().size() == 0 ){
				BigInteger itemCount = ProductLogic.getInstance().countItemByCat(
						pc.getCategoryId());			
				if(itemCount != null && itemCount.intValue()>0)
					pc.setCategoryName(pc.getCategoryName() + " [" + itemCount.intValue() + "]");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	/**
	 * Recursive method to get sub category list
	 * @param pc
	 * @param parentNode
	 */
	protected void listSubCategory(List<ProductCategory> subCatLst, 
			CProductCategoryTreeNode parentNode) throws Exception{		
		for(ProductCategory subPc: subCatLst){
			List<ProductCategory> subCatLst2 =  ProductCatLogic.getInstance().loadSubCat(subPc.getCategoryId());
			subPc.setCatList(subCatLst2);
			CProductCategoryTreeNode subNode = new CProductCategoryTreeNode(subPc);
			
			if(subCatLst2.size()>0)
				listSubCategory(subCatLst2, subNode);
			updateCatNodeLabel(subNode);
			parentNode.add(subNode);
		}
	}
	/**
	 * Recursive method to delete category tree
	 * @param userObject can be <code>ProductSection</code>, <code>ProductCategoryGroup</code> or <code>ProductCategory</code>
	 * @throws Exception
	 */
	public void deleteCategory(AbstractIshopEntity userObject) throws Exception{
		if(userObject instanceof ProductSection){
			ProductSection sec = (ProductSection)userObject;
			List<ProductCategoryGroup> catGroupLst
			= ProductCatLogic.getInstance().loadCatGroupBySection(sec.getSectionId());	
			if(catGroupLst != null && catGroupLst.size()>0){
				for(ProductCategoryGroup pcg:catGroupLst){
					deleteCategory(pcg);
				}
			}
			setExtraEntityInfor(userObject, CAction.DELETE);			
			saveSectionInfor(sec, SystemConfiguration.LP_PD_UPDATE_SECTION);			
    	}else if(userObject instanceof ProductCategoryGroup){
    		ProductCategoryGroup pcg = (ProductCategoryGroup)userObject;
    		List<ProductCategory> catLst =  ProductCatLogic.getInstance().loadCatByCatGroup(pcg.getGoupId());
    		if(catLst != null && catLst.size()>0){
    			for(ProductCategory pc:catLst){
    				deleteCategory(pc);
    			}
    		}
    		setExtraEntityInfor(userObject, CAction.DELETE);
    		saveCatGroupInfor(pcg, SystemConfiguration.LP_PD_UPDATE_CAT_GROUP);
    	}else if(userObject instanceof ProductCategory){
    		ProductCategory pc = (ProductCategory)userObject;
    		List<ProductCategory> subCatLst =  ProductCatLogic.getInstance().loadSubCat(pc.getCategoryId());
    		if(subCatLst != null && subCatLst.size()>0){
    			for(ProductCategory subPc:subCatLst){
    				deleteCategory(subPc);
    			}
    		}
    		setExtraEntityInfor(userObject, CAction.DELETE);
    		saveCatInfor(pc, SystemConfiguration.LP_PD_UPDATE_CAT);
    	}
	}
}
