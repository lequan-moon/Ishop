package com.nn.ishop.client.logic.admin;

import java.math.BigInteger;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

import com.nn.ishop.client.CActionEvent.CAction;
import com.nn.ishop.client.config.SystemConfiguration;
import com.nn.ishop.client.logic.CommonLogic;
import com.nn.ishop.client.logic.util.BusinessUtil;
import com.nn.ishop.server.dto.product.ProductItem;
import com.nn.ishop.server.dto.product.ProductUOM;

public class ProductLogic  extends CommonLogic{
	static ProductLogic instance = null;
	
	public static ProductLogic getInstance() {
		if(instance == null)
			instance = new ProductLogic();
		return instance;
	}
	public ProductItem saveProduct(ProductItem pcg, CAction act){		
		
		try {
			RPCServiceClient serviceClient = new RPCServiceClient();
			Options options = serviceClient.getOptions();
			options.setTo(SystemConfiguration.PD_EPR);
			QName qname = null;
			if(act.equals(CAction.ADD))
				qname = new QName(SystemConfiguration.NAMESPACE_URI,
					SystemConfiguration.LP_PD_INSERT_ITEM);
			else
				qname = new QName(SystemConfiguration.NAMESPACE_URI,
						SystemConfiguration.LP_PD_UPDATE_ITEM);
			
			Object[] params = new Object[] { pcg };
			Class[] types = new Class[] { ProductItem.class };
			Object[] response1 = serviceClient.invokeBlocking(qname, params,
					types);
			pcg = (ProductItem)response1[0];
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pcg;
	}
	public List<ProductItem> loadItemByCat(int catId){
		List<ProductItem> lst = null;
		try {
			BusinessUtil<ProductItem> bs = new BusinessUtil<ProductItem>();
			lst = bs.webserviceGetDtoList(1,
					SystemConfiguration.LP_PD_LOAD_PRODUCT_BY_CAT,
					new Class[] { ProductItem[].class },
					new Object[] { new Integer(catId) },
					SystemConfiguration.PD_EPR);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lst;
	}
	
	public BigInteger countItemByCat(
			int catId		
			)throws Exception{
		RPCServiceClient serviceClient = new RPCServiceClient();
	    Options options = serviceClient.getOptions();
	    options.setTo(SystemConfiguration.PD_EPR);
	    QName qname = new QName(SystemConfiguration.NAMESPACE_URI, SystemConfiguration.LP_PD_COUNT_PRODUCT_BY_CAT);	     
	    Object response[] = serviceClient.invokeBlocking(qname, 
	    		new Object[] { new Integer(catId) },
	    		new Class[] { BigInteger.class });
		return (BigInteger)response[0];
	}
	
	public ProductItem getItem(String itemId){
		ProductItem ret = null;
		try {
			BusinessUtil<ProductItem> bs = new BusinessUtil<ProductItem>();
			ret = bs.webserviceGetDto(new Integer("1"), SystemConfiguration.LP_PD_GET_ITEM,
					new Class[] { ProductItem.class }, 
					new Object[] { itemId }, SystemConfiguration.PD_EPR);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public List<ProductItem> searchItemByName(String itemName){
		List<ProductItem> lst = null;
		try {
			BusinessUtil<ProductItem> bs = new BusinessUtil<ProductItem>();
			lst = bs.webserviceGetDtoList(1,
					SystemConfiguration.LP_PD_SEARCH_ITEM,
					new Class[] { ProductItem[].class },
					new Object[] { itemName },
					SystemConfiguration.PD_EPR);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lst;
	}
	
	public ProductUOM getUOM(int id){
		ProductUOM ret = null;
		try {
			BusinessUtil<ProductUOM> bs = new BusinessUtil<ProductUOM>();
			ret = bs.webserviceGetDto(1,
					SystemConfiguration.LP_PD_GET_UOM,
					new Class[] { ProductUOM.class },
					new Object[] { new Integer(id) },
					SystemConfiguration.PD_EPR);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
//	public static getProductUom(int)
}
