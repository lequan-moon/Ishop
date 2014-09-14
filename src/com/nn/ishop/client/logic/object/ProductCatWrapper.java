package com.nn.ishop.client.logic.object;

import java.util.ArrayList;
import java.util.List;

import com.nn.ishop.server.dto.product.ProductCategory;
import com.nn.ishop.server.dto.product.ProductCategoryGroup;
import com.nn.ishop.server.dto.product.ProductSection;

public class ProductCatWrapper {	
	List<ProductSection> productSessionList = new ArrayList<ProductSection>();

	public List<ProductSection> getProductSessionList() {
		return productSessionList;
	}

	public void setProductSessionList(List<ProductSection> productSessionList) {
		this.productSessionList = productSessionList;
	}
	@Override
	public String toString() {		
		return "2N Product Category";
	}
	public static List<ProductSection> testBuildProductCategoryTree(){
		List<ProductSection> prodSecList = new ArrayList<ProductSection>();
//		for (int i=0;i<5;i++){
//			ProductSection pds = new ProductSection("Section-" + i, "Test");
//				pds.setSectionId(i);				
//				for(int j=0;j<3;j++){
//					ProductCategoryGroup pcg = new ProductCategoryGroup("Cat Group-"+i+"-"+j, "Test", i);
//					pcg.setGoupId(i*j);
//					pds.getCatGroupList().add(pcg);					
//					for(int k =0;k<3;k++){
//						ProductCategory pc = new ProductCategory("Category-"+i+"-"+j+"-"+k, 0, "ABABAABA", pcg.getGoupId());
//						pc.setCategoryId(i*j*k);
//						pcg.getListCat().add(pc);
//						for (int n=0;n<3;n++){
//							ProductCategory pcSub = new ProductCategory("Sub category-"+i+"-"+j+"-"+k+"-"+n, 0, "ABABAABA", pcg.getGoupId());
//							pcSub.setCategoryId(i*j*k*n);
//							pc.getCatList().add(pcSub);
//							for(int m=0;m<3;m++){
//								ProductCategory pcSub2 = new ProductCategory("Sub category-"+i+"-"+j+"-"+k+"-"+n+"-"+m,
//										0, "ABABAABA", pcg.getGoupId());
//								pcSub2.setCategoryId(i*j*k*n*m);
//								pcSub.getCatList().add(pcSub2);
//							}
//						}
//					}
//				}
//				prodSecList.add(pds);
//		}
		return prodSecList;
	}
}
