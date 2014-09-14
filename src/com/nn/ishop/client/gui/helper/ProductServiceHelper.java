package com.nn.ishop.client.gui.helper;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

//import com.nn.ishop.client.business.util.BusinessUtil;
import com.nn.ishop.client.config.SystemConfiguration;
import com.nn.ishop.server.dto.product.ProductCategory;


public class ProductServiceHelper {
	public static final Logger logger = Logger.getLogger(ProductServiceHelper.class);
	public static List<ProductCategory> getCategoryList(){		
		List<ProductCategory> catList = new ArrayList<ProductCategory>();
		try {
			/*BusinessUtil<ProductCategory> bs = new BusinessUtil<ProductCategory>();
			catList = bs.webserviceGetDtoList(new Integer(1),
					SystemConfiguration.LP_PD_GET_ALL_CAT,
					new Class[] { ProductCategory[].class }, 
					new Object[] { new Integer(1) },
					SystemConfiguration.PD_EPR);*/
			
			
		} catch (Exception e) {
			logger.info(" updateCatList: "+e.getMessage());
		}
		return catList;
	}
}
