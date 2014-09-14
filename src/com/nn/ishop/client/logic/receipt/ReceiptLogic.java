package com.nn.ishop.client.logic.receipt;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

import com.nn.ishop.client.CActionEvent.CAction;
import com.nn.ishop.client.config.SystemConfiguration;
import com.nn.ishop.client.logic.CommonLogic;
import com.nn.ishop.client.logic.IShopLogic;
import com.nn.ishop.client.logic.util.BusinessUtil;
import com.nn.ishop.server.dto.receipt.AccountReceivable;
import com.nn.ishop.server.dto.receipt.Receipt;

public class ReceiptLogic extends CommonLogic implements IShopLogic<Receipt,String>{
	private static ReceiptLogic instance = null;
	
	public static ReceiptLogic getInstance(){
		if(instance == null)
			instance = new ReceiptLogic();
		return instance;
	}
	public Receipt get(String id) throws Throwable {
		BusinessUtil<Receipt> bs = new BusinessUtil<Receipt>();
		Receipt ret = bs.webserviceGetDto(1,
				SystemConfiguration.LOCALPART_GET,
				new Class[] { Receipt.class }, 
				new Object[] {id},
				SystemConfiguration.RECEIPT_SERVICE_TARGET_EPR);
		return ret;		
	}

	@SuppressWarnings("unchecked")
	public Receipt insert(Receipt param) throws Throwable {
		setExtraEntityInfor(param, CAction.ADD);
		RPCServiceClient serviceClient = new RPCServiceClient();
		Options options = serviceClient.getOptions();
		options.setTo(SystemConfiguration.RECEIPT_SERVICE_TARGET_EPR);
		QName qname = new QName(SystemConfiguration.NAMESPACE_URI,
				SystemConfiguration.LOCALPART_INSERT);
		Object[] params = new Object[] { param };
		Class[] types = new Class[] { Receipt.class };
		Object[] res = serviceClient.invokeBlocking(qname, params, types);
		return (Receipt) res[0];
	}
	public Receipt insertWithAr(Receipt param, AccountReceivable[] arList) throws Throwable {
		setExtraEntityInfor(param, CAction.ADD);
		RPCServiceClient serviceClient = new RPCServiceClient();
		Options options = serviceClient.getOptions();
		options.setTo(SystemConfiguration.RECEIPT_SERVICE_TARGET_EPR);
		QName qname = new QName(SystemConfiguration.NAMESPACE_URI,
				SystemConfiguration.LOCALPART_INSERT +"WithAr");
		Object[] params = new Object[] { param, arList };
		Class<?>[] types = new Class[] { Receipt.class };
		Object[] res = serviceClient.invokeBlocking(qname, params, types);
		return (Receipt) res[0];
	}
	public List<Receipt> load() throws Throwable {
		BusinessUtil<Receipt> bs = new BusinessUtil<Receipt>();
		List<Receipt> ret = bs.webserviceGetDtoList(1,
				SystemConfiguration.LOCALPART_LOAD,
				new Class[] { Receipt[].class }, 
				new Object[] { new Integer(1) },
				SystemConfiguration.RECEIPT_SERVICE_TARGET_EPR);
		return ret;
	}

	@SuppressWarnings("unchecked")
	public Receipt update(Receipt param) throws Throwable {
		setExtraEntityInfor(param, CAction.UPDATE);
		RPCServiceClient serviceClient = new RPCServiceClient();
		Options options = serviceClient.getOptions();
		options.setTo(SystemConfiguration.RECEIPT_SERVICE_TARGET_EPR);
		QName qname = new QName(SystemConfiguration.NAMESPACE_URI,
				SystemConfiguration.LOCALPART_UPDATE);
		Object[] params = new Object[] { param };
		Class[] types = new Class[] { Receipt.class };
		Object[] res = serviceClient.invokeBlocking(qname, params, types);
		return (Receipt) res[0];
	}
	
	public List<Receipt> search(String condition) throws Throwable {
		// TODO Auto-generated method stub
		return null;
	}
	public static List<Receipt> search(Integer customerId, Calendar fromDate, Calendar toDate ) throws Exception{
		BusinessUtil<Receipt> bs = new BusinessUtil<Receipt>();
		List<Receipt> ret = bs.webserviceGetDtoList(1,
				SystemConfiguration.LOCALPART_SEARCH+"CDD",
				new Class[] { Receipt[].class }, 
				new Object[] { new Integer(customerId), fromDate, toDate },
				SystemConfiguration.RECEIPT_SERVICE_TARGET_EPR);
		return ret;
	}
	public static List<Receipt> search(int customerId, Calendar createDate) throws Exception{
		BusinessUtil<Receipt> bs = new BusinessUtil<Receipt>();
		List<Receipt> ret = bs.webserviceGetDtoList(1,
				SystemConfiguration.LOCALPART_SEARCH+"CD",
				new Class[] { Receipt[].class }, 
				new Object[] { new Integer(customerId), createDate},
				SystemConfiguration.RECEIPT_SERVICE_TARGET_EPR);
		return ret;
	}
	public static List<Receipt> search(int customerId, String paymentType)throws Exception{
		BusinessUtil<Receipt> bs = new BusinessUtil<Receipt>();
		List<Receipt> ret = bs.webserviceGetDtoList(1,
				SystemConfiguration.LOCALPART_SEARCH+"CP",
				new Class[] { Receipt[].class }, 
				new Object[] { new Integer(customerId), paymentType},
				SystemConfiguration.RECEIPT_SERVICE_TARGET_EPR);
		return ret;
	}
	public static List<Receipt> search(int customerId, String paymentType, String txId) throws Exception{
		BusinessUtil<Receipt> bs = new BusinessUtil<Receipt>();
		List<Receipt> ret = bs.webserviceGetDtoList(1,
				SystemConfiguration.LOCALPART_SEARCH+"CPTX",
				new Class[] { Receipt[].class }, 
				new Object[] { new Integer(customerId), paymentType, txId},
				SystemConfiguration.RECEIPT_SERVICE_TARGET_EPR);
		return ret;
	}
	public static Receipt getBalanceForward(int customerId) throws Exception{
		BusinessUtil<Receipt> bs = new BusinessUtil<Receipt>();		
		Receipt ret = bs.webserviceGetDto(1,
				"getBalanceForward",
				new Class[] { Receipt.class }, 
				new Object[] {customerId},
				SystemConfiguration.RECEIPT_SERVICE_TARGET_EPR);
		return ret;		
	}
	
	/*public static void main(String[] args) throws Exception{		
		Receipt pm = new Receipt("pm1", 1, "voucherId",
				"currencyId", 0.5454d, "tranxId",
				"paymentType", 32, 999,
				new Date(), "paymentMethod", "customerBankAccount",
				"refVoucherNumber","payerName", 9999,
				"voucherType", "accountNumber","payment_desc");
		Receipt pm2 =  new Receipt("pm2", 1, "voucherId",
				"currencyId", 0.5454d, "tranxId",
				"paymentType", 32, 999,
				new Date(), "paymentMethod", "customerBankAccount",
				"refVoucherNumber","payerName", 9999,
				"voucherType", "accountNumber","payment_desc");
		try {
			//PaymentLogic.getInstance().insert(pm);
			//PaymentLogic.getInstance().insert(pm2);
			List<Receipt> ret = ReceiptLogic.search(1,"paymentType");
			List<Receipt> ret2 = ReceiptLogic.search(1,"paymentType","tranxId");
			List<Receipt> ret3 = ReceiptLogic.search(1,SystemConfiguration.sdf.parse("20/08/2013 00:00:00"));
			List<Receipt> ret4 = ReceiptLogic.search(1,SystemConfiguration.sdf.parse("01/08/2013 00:00:00")
					, SystemConfiguration.sdf.parse("31/08/2013 00:00:00"));
			
			System.out.println("ret " + ret.size() + " --- ret2 " 
					+ ret2.size()+"--- ret3 " + ret3.size() + "------" + ret4.size());
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}*/
}
