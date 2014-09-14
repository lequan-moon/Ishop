package com.nn.ishop.client.logic.sale;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.namespace.QName;

import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.nn.ishop.client.CActionEvent.CAction;
import com.nn.ishop.client.config.SystemConfiguration;
import com.nn.ishop.client.logic.CommonLogic;
import com.nn.ishop.client.logic.IShopLogic;
import com.nn.ishop.client.logic.util.BusinessUtil;
import com.nn.ishop.client.util.Util;
import com.nn.ishop.server.dto.sale.ItemStock;
import com.nn.ishop.server.dto.sale.SaleIssueSlip;
import com.nn.ishop.server.dto.sale.SaleOrder;
import com.nn.ishop.server.dto.sale.SaleOrderType;

public class SaleOrderLogic extends CommonLogic implements IShopLogic<SaleOrder,String>{
	
	public static final String INSERT_ISL_METHOD = "insertIsl";
	public static final String UPDATE_ISL_METHOD = "updateIsl";
	public static final String GET_ISL_METHOD = "getIsl";
	public static final String LOAD_ISL_METHOD = "loadIsl";
	public static final String SEARCH_ISL_METHOD = "searchIsl";
	
	public static final String GET_ITEM_STOCK_METHOD = "getItemStock";
	public static final String LOAD_ITEM_STOCK_METHOD = "loadItemStock";
	public static final String SEARCH_ITEM_STOCK_METHOD = "searchItemStock";
	public static final String LOAD_ISL_BY_ORDER_METHOD = "loadIssueSlipByOrder";
	
	private static SaleOrderLogic instance;	
	public static SaleOrderLogic getInstance(){
		if(instance == null)
			instance = new SaleOrderLogic();
		return instance;
	}
	
	/**
 	 * Load SaleOrderType from sale.cfg (contain JSON string describe SaleOrderType)
 	 * @return
 	 */
	public static List<SaleOrderType> loadSaleOrderType(){
		Properties props = Util.getProperties("cfg/sale.cfg");
		String jString =  props.getProperty("order.type");
		
		Gson gson = new Gson();
		
		/*
		// Quickest way to convert
		Type collectionType = new TypeToken<Collection<SaleOrderType>>(){}.getType();
		Collection<SaleOrderType> sotCols = gson.fromJson(jString, collectionType);
		*/			
		
		//The other way to return ArrayList of SaleOrderType
		List<SaleOrderType> sotList = new ArrayList<SaleOrderType>();
		JsonParser parser = new JsonParser();
	    JsonArray Jarray = parser.parse(jString).getAsJsonArray();
	    
	    for(JsonElement obj : Jarray )
	    {
	    	SaleOrderType sot = gson.fromJson( obj , SaleOrderType.class);
	    	sotList.add(sot);
	    }
	    //return sotCols;
	    return sotList;
	}
	
	public SaleOrder get(String id) throws Throwable {
		BusinessUtil<SaleOrder> bs = new BusinessUtil<SaleOrder>();
		SaleOrder ret = bs.webserviceGetDto(1,
				SystemConfiguration.LOCALPART_GET,
				new Class[] { SaleOrder.class }, 
				new Object[] {id},
				SystemConfiguration.SALE_ORDER_SERVICE_TARGET_EPR);
		return ret;		
	}
	
	public SaleOrder getSOByTranxId(String param)throws Throwable {
		BusinessUtil<SaleOrder> bs = new BusinessUtil<SaleOrder>();
		SaleOrder ret = bs.webserviceGetDto(1,
				SystemConfiguration.LOCALPART_GET_SO_BY_TX_ID,
				new Class[] { SaleOrder.class }, 
				new Object[] {param},
				SystemConfiguration.SALE_ORDER_SERVICE_TARGET_EPR);
		return ret;		
	}

	@SuppressWarnings("unchecked")
	public SaleOrder insert(SaleOrder param) throws Throwable {
		setExtraEntityInfor(param, CAction.ADD);
		RPCServiceClient serviceClient = new RPCServiceClient();
		Options options = serviceClient.getOptions();
		options.setTo(SystemConfiguration.SALE_ORDER_SERVICE_TARGET_EPR);
		QName qname = new QName(SystemConfiguration.NAMESPACE_URI,
				SystemConfiguration.LOCALPART_INSERT);
		Object[] params = new Object[] { param };
		Class[] types = new Class[] { SaleOrder.class };
		Object[] res = serviceClient.invokeBlocking(qname, params, types);
		return (SaleOrder) res[0];
	}

	public List<SaleOrder> load() throws Throwable {
		BusinessUtil<SaleOrder> bs = new BusinessUtil<SaleOrder>();
		List<SaleOrder> ret = bs.webserviceGetDtoList(1,
				SystemConfiguration.LOCALPART_LOAD,
				new Class[] { SaleOrder[].class }, 
				new Object[] { new Integer(1) },
				SystemConfiguration.SALE_ORDER_SERVICE_TARGET_EPR);
		return ret;
	}

	public List<SaleOrder> search(String condition) throws Throwable {
		// TODO implement
		return null;
	}

	@SuppressWarnings("unchecked")
	public SaleOrder update(SaleOrder param) throws Throwable {
		setExtraEntityInfor(param, CAction.UPDATE);
		RPCServiceClient serviceClient = new RPCServiceClient();
		Options options = serviceClient.getOptions();
		options.setTo(SystemConfiguration.SALE_ORDER_SERVICE_TARGET_EPR);
		QName qname = new QName(SystemConfiguration.NAMESPACE_URI,
				SystemConfiguration.LOCALPART_UPDATE);
		Object[] params = new Object[] { param };
		Class[] types = new Class[] { SaleOrder.class };
		Object[] res = serviceClient.invokeBlocking(qname, params, types);
		return (SaleOrder) res[0];
	}
	
	
	
	@SuppressWarnings("unchecked")
	public SaleIssueSlip insertIsl(SaleIssueSlip param)throws Throwable {
		setExtraEntityInfor(param, CAction.ADD);
		RPCServiceClient serviceClient = new RPCServiceClient();
		Options options = serviceClient.getOptions();
		options.setTo(SystemConfiguration.SALE_ORDER_SERVICE_TARGET_EPR);
		QName qname = new QName(SystemConfiguration.NAMESPACE_URI,
				INSERT_ISL_METHOD);
		Object[] params = new Object[] { param };
		Class[] types = new Class[] { SaleIssueSlip.class };
		Object[] res = serviceClient.invokeBlocking(qname, params, types);
		return (SaleIssueSlip) res[0];
	};
	
	@SuppressWarnings("unchecked")
	public SaleIssueSlip updateIsl(SaleIssueSlip param)throws Throwable {
		setExtraEntityInfor(param, CAction.UPDATE);
		RPCServiceClient serviceClient = new RPCServiceClient();
		Options options = serviceClient.getOptions();
		options.setTo(SystemConfiguration.SALE_ORDER_SERVICE_TARGET_EPR);
		QName qname = new QName(SystemConfiguration.NAMESPACE_URI,
				UPDATE_ISL_METHOD);
		Object[] params = new Object[] { param };
		Class[] types = new Class[] { SaleIssueSlip.class };
		Object[] res = serviceClient.invokeBlocking(qname, params, types);
		return (SaleIssueSlip) res[0];
	};
	
	public SaleIssueSlip getIsl(Integer id)throws Throwable {
		BusinessUtil<SaleIssueSlip> bs = new BusinessUtil<SaleIssueSlip>();
		SaleIssueSlip ret = bs.webserviceGetDto(1,
				GET_ISL_METHOD,
				new Class[] { SaleIssueSlip.class }, 
				new Object[] {id},
				SystemConfiguration.SALE_ORDER_SERVICE_TARGET_EPR);
		return ret;	
	};
	
	public List<SaleIssueSlip> loadIsl()throws Throwable {
		BusinessUtil<SaleIssueSlip> bs = new BusinessUtil<SaleIssueSlip>();
		List<SaleIssueSlip> ret = bs.webserviceGetDtoList(1,
				LOAD_ISL_METHOD,
				new Class[] { SaleIssueSlip[].class }, 
				new Object[] { new Integer(1) },
				SystemConfiguration.SALE_ORDER_SERVICE_TARGET_EPR);
		return ret;
	};
	
	public List<SaleIssueSlip> searchIsl()throws Throwable {
		// TODO implement
		return null;
	};
	
	public List<SaleIssueSlip> loadIssueSlipByOrder(String orderId)throws Throwable{ 
		BusinessUtil<SaleIssueSlip> bs = new BusinessUtil<SaleIssueSlip>();
		List<SaleIssueSlip> ret = bs.webserviceGetDtoList(1,
				LOAD_ISL_BY_ORDER_METHOD,
				new Class[] { SaleIssueSlip[].class }, 
				new Object[] { orderId },
				SystemConfiguration.SALE_ORDER_SERVICE_TARGET_EPR);
		return ret;
	}
	
	public ItemStock getItemStock(String itemId)throws Throwable {
		BusinessUtil<ItemStock> bs = new BusinessUtil<ItemStock>();
		ItemStock ret = bs.webserviceGetDto(1,
				GET_ITEM_STOCK_METHOD,
				new Class[] { ItemStock.class }, 
				new Object[] {itemId},
				SystemConfiguration.SALE_ORDER_SERVICE_TARGET_EPR);
		return ret;	
	};
	
	public List<ItemStock> loadItemStock()throws Throwable {
		BusinessUtil<ItemStock> bs = new BusinessUtil<ItemStock>();
		List<ItemStock> ret = bs.webserviceGetDtoList(1,
				LOAD_ITEM_STOCK_METHOD,
				new Class[] { ItemStock[].class }, 
				new Object[] { new String() },
				SystemConfiguration.SALE_ORDER_SERVICE_TARGET_EPR);
		return ret;
	};
	public static void main(String[] args) throws Exception{		
		try {
			/*SaleOrder so2 = SaleOrderLogic.getInstance().get("SO_2013.05.25_1");
			System.out.println(so2.getOrder_id() + "--" + so2.getOrder_type() + "--- " + so2.getObjectHeader());*/
			
			//-- UPDATE with details 
			/*so2.setDescription("Desc after update ");		
			
			SaleOrderDetail[] soDetail = new SaleOrderDetail[] {
					new SaleOrderDetail("SO_DT_1", "SO_2013.05.25_1",
							"4902505408083", 100, "Cai"),
					new SaleOrderDetail("SO_DT_2", "SO_2013.05.25_1",
							"4902505408083", 100, "Cai"),
					new SaleOrderDetail("SO_DT_3", "SO_2013.05.25_1",
							"4902505408083", 100, "Cai"),
					new SaleOrderDetail("SO_DT_4", "SO_2013.05.25_1",
							"4902505408083", 100, "Cai")
					};
			so2.setDetails(soDetail);
			
			SaleOrder so3 = SaleOrderLogic.getInstance().update(so2);
			
			System.out.println(so3.getDescription());
			System.out.println(so3.getDetails().length);*/
			
			/*SaleIssueSlip isl = new SaleIssueSlip(1, "1369539124312",
					"SO_2013.05.25_1", 
					1, "WH_5b732891-d617-4c22-80c0-43da96b9eb2c", "LOT_0765807f-7077-4e73-8318-c68e5294cb60",
					"4902505408083", 120, new Date(), 1);			
			SaleOrderLogic.getInstance().insertIsl(isl);*/
			
			/*SaleIssueSlip isl2 = new SaleIssueSlip(2, "1369539124312",
					"SO_2013.05.25_1", 
					1, "WH_5b732891-d617-4c22-80c0-43da96b9eb2c", "LOT_0765807f-7077-4e73-8318-c68e5294cb60",
					"4902505408083", 10, new Date(), 1);
			
			SaleOrderLogic.getInstance().insertIsl(isl2);
			
			SaleIssueSlip isl3 = new SaleIssueSlip(3, "1369539124312",
					"SO_2013.05.25_1", 
					1, "WH_5b732891-d617-4c22-80c0-43da96b9eb2c", "LOT_0765807f-7077-4e73-8318-c68e5294cb60",
					"4902505408083", 12, new Date(), 1);
			
			SaleOrderLogic.getInstance().insertIsl(isl3);*/
			
			/*List<SaleIssueSlip> ret = SaleOrderLogic.getInstance().loadIsl();
			System.out.println("length = " + ret.size());*/
			List<ItemStock> stocks = SaleOrderLogic.getInstance().loadItemStock();
			for(ItemStock s:stocks)
				System.out.println(s.getItem_id() +"--"+ s.getStore_qty());
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}
