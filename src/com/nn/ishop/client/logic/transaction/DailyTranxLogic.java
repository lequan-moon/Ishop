package com.nn.ishop.client.logic.transaction;

import java.util.ArrayList;
import java.util.Date;
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
import com.nn.ishop.server.dto.bstranx.DailyTransaction;
import com.nn.ishop.server.dto.bstranx.TransactionType;
import com.nn.ishop.server.dto.company.Company;
import com.nn.ishop.server.dto.sale.SaleOrderType;

public class DailyTranxLogic extends CommonLogic implements IShopLogic<DailyTransaction,String> {

		private static DailyTranxLogic instance;
		public static final String METHOD_SEARCH_BY_TYPE_CUSTOMER_FRDATE_TODATE = "searchByTypeCustomerFrDateToDate";
 		public static DailyTranxLogic getInstance(){
			return instance == null? new DailyTranxLogic():instance;
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
		
		public static List<TransactionType> loadTransactionType(){
			Properties props = Util.getProperties("cfg/daily_tranx.cfg");
			String jString =  props.getProperty("tranx.type");
			
			Gson gson = new Gson();
			
			/*
			// Quickest way to convert
			Type collectionType = new TypeToken<Collection<SaleOrderType>>(){}.getType();
			Collection<SaleOrderType> sotCols = gson.fromJson(jString, collectionType);
			*/			
			
			//The other way to return ArrayList of SaleOrderType
			List<TransactionType> txTypeList = new ArrayList<TransactionType>();
			JsonParser parser = new JsonParser();
		    JsonArray Jarray = parser.parse(jString).getAsJsonArray();
		    
		    for(JsonElement obj : Jarray )
		    {
		    	TransactionType txType = gson.fromJson( obj , TransactionType.class);
		    	txTypeList.add(txType);
		    }
		    //return sotCols;
		    return txTypeList;
		}

		public DailyTransaction get(String id) throws Exception {
			BusinessUtil<DailyTransaction> bs = new BusinessUtil<DailyTransaction>();
			DailyTransaction ret = bs.webserviceGetDto(1,
					SystemConfiguration.LOCALPART_GET,
					new Class[] { DailyTransaction.class }, 
					new Object[] {id},
					SystemConfiguration.DAILY_TRANX_SERVICE_TARGET_EPR);
			return ret;
		}

		public DailyTransaction insert(DailyTransaction param) throws Exception {
			setExtraEntityInfor(param, CAction.ADD);
			RPCServiceClient serviceClient = new RPCServiceClient();
			Options options = serviceClient.getOptions();
			options.setTo(SystemConfiguration.DAILY_TRANX_SERVICE_TARGET_EPR);
			QName qname = new QName(SystemConfiguration.NAMESPACE_URI,
					SystemConfiguration.LOCALPART_INSERT);
			Object[] params = new Object[] { param };
			Class[] types = new Class[] { DailyTransaction.class };
			Object[] res = serviceClient.invokeBlocking(qname, params, types);
			return (DailyTransaction) res[0];
		}

		public List<DailyTransaction> load()  throws Exception{
			BusinessUtil<DailyTransaction> bs = new BusinessUtil<DailyTransaction>();
			List<DailyTransaction> ret = bs.webserviceGetDtoList(1,
					SystemConfiguration.LOCALPART_LOAD,
					new Class[] { DailyTransaction[].class }, 
					new Object[] { new Integer(1) },
					SystemConfiguration.DAILY_TRANX_SERVICE_TARGET_EPR);
			return ret;
		}

		public static List<DailyTransaction> searchByTypeCustomerFrDateToDate(
				String txType, int cusId, Date fromDate, Date toDate) throws Exception{
			BusinessUtil<DailyTransaction> bs = new BusinessUtil<DailyTransaction>();
			List<DailyTransaction> ret = bs.webserviceGetDtoList(1,
					METHOD_SEARCH_BY_TYPE_CUSTOMER_FRDATE_TODATE,
					new Class[] { DailyTransaction[].class }, 
					new Object[] {txType,cusId,fromDate,toDate},
					SystemConfiguration.DAILY_TRANX_SERVICE_TARGET_EPR);
			return ret;
		}
		public DailyTransaction update(DailyTransaction param)  throws Exception{
			setExtraEntityInfor(param, CAction.UPDATE);
			RPCServiceClient serviceClient = new RPCServiceClient();
			Options options = serviceClient.getOptions();
			options.setTo(SystemConfiguration.DAILY_TRANX_SERVICE_TARGET_EPR);
			QName qname = new QName(SystemConfiguration.NAMESPACE_URI,
					SystemConfiguration.LOCALPART_UPDATE);
			Object[] params = new Object[] { param };
			Class[] types = new Class[] { DailyTransaction.class };
			Object[] res = serviceClient.invokeBlocking(qname, params, types);
			return (DailyTransaction) res[0];
		}
		
		public List<DailyTransaction> search(String condition)  throws Exception{
			// TODO Auto-generated method stub
			return null;
		}
		
		public List<DailyTransaction> searchByStep(String condition)  throws Exception{
			List<DailyTransaction> result = null;
			BusinessUtil<DailyTransaction> bu = new BusinessUtil<DailyTransaction>();
			try {
				result = bu.webserviceGetDtoList(new Integer(1), SystemConfiguration.LOCALPART_SEARCH_TRANSACTION_BY_STEP, new Class[] { DailyTransaction[].class },
						new Object[] { condition }, SystemConfiguration.DAILY_TRANX_SERVICE_TARGET_EPR);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}
		
		public static void main(String[] args) throws Exception{
			DailyTranxLogic tx = DailyTranxLogic.getInstance();
			/*DailyTransaction dtx = new DailyTransaction("2013.05.22_001", "SALE_TRANSACTION",
					"INIT,SALE_ORDER,PAYMENT,ISSUE_SLIP", "INIT", "Active", null);
			dtx = tx.insert(dtx);
			System.out.println(dtx);*/
//			System.out.println("Transaction type =======================================");
//			tx.loadTransactionType();
			System.out.println("GET daily tranx =======================================");
			DailyTransaction dtx = tx.get("2013.05.22_001");
			System.out.println(dtx);
			dtx.setCurrent_step("INIT");
			dtx=tx.update(dtx);
			System.out.println(dtx);
		}
		
		public List<DailyTransaction> loadTranxByStep(String STEP)  throws Exception{
			BusinessUtil<DailyTransaction> bs = new BusinessUtil<DailyTransaction>();
			List<DailyTransaction> ret = bs.webserviceGetDtoList(1,
					SystemConfiguration.LOCALPART_SEARCH_TRANSACTION_BY_STEP,
					new Class[] { DailyTransaction[].class }, 
					new Object[] { STEP },
					SystemConfiguration.DAILY_TRANX_SERVICE_TARGET_EPR);
			return ret;
		}
		public List<DailyTransaction> loadTranxByTxType(String TYPE)  throws Exception{
			BusinessUtil<DailyTransaction> bs = new BusinessUtil<DailyTransaction>();
			List<DailyTransaction> ret = bs.webserviceGetDtoList(1,
					SystemConfiguration.LOCALPART_SEARCH_TRANSACTION_BY_TX_TYPE,
					new Class[] { DailyTransaction[].class }, 
					new Object[] { TYPE },
					SystemConfiguration.DAILY_TRANX_SERVICE_TARGET_EPR);
			return ret;
		}
		//searchByType
}
