package com.nn.ishop.client.logic.arap;


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import com.nn.ishop.server.util.Util;

public class ARAPLogic extends CommonLogic implements IShopLogic<AccountReceivable, String> {
	private static ARAPLogic instance;
	
	public static ARAPLogic getInstance(){
		if(instance == null)
			instance = new ARAPLogic();
		return instance;
	}
	public AccountReceivable get(String id) throws Throwable {
		BusinessUtil<AccountReceivable> bs = new BusinessUtil<AccountReceivable>();
		AccountReceivable ret = bs.webserviceGetDto(1,
				SystemConfiguration.LOCALPART_GET,
				new Class[] { AccountReceivable.class }, 
				new Object[] {id},
				SystemConfiguration.AR_SERVICE_TARGET_EPR);
		return ret;	
	}

	@SuppressWarnings("unchecked")
	public AccountReceivable insert(AccountReceivable param) throws Throwable {
		setExtraEntityInfor(param, CAction.ADD);
		RPCServiceClient serviceClient = new RPCServiceClient();
		Options options = serviceClient.getOptions();
		options.setTo(SystemConfiguration.AR_SERVICE_TARGET_EPR);
		QName qname = new QName(SystemConfiguration.NAMESPACE_URI,
				SystemConfiguration.LOCALPART_INSERT);
		Object[] params = new Object[] { param };
		Class[] types = new Class[] { AccountReceivable.class };
		Object[] res = serviceClient.invokeBlocking(qname, params, types);
		return (AccountReceivable) res[0];
	}

	public List<AccountReceivable> load() throws Throwable {
		BusinessUtil<AccountReceivable> bs = new BusinessUtil<AccountReceivable>();
		List<AccountReceivable> ret = bs.webserviceGetDtoList(1,
				SystemConfiguration.LOCALPART_LOAD,
				new Class[] { AccountReceivable[].class }, 
				new Object[] { new Integer(1) },
				SystemConfiguration.AR_SERVICE_TARGET_EPR);
		return ret;
	}

	public List<AccountReceivable> search(String condition) throws Throwable {
		return null;
	}

	@SuppressWarnings("unchecked")
	public AccountReceivable update(AccountReceivable param) throws Throwable {
		setExtraEntityInfor(param, CAction.UPDATE);
		RPCServiceClient serviceClient = new RPCServiceClient();
		Options options = serviceClient.getOptions();
		options.setTo(SystemConfiguration.AR_SERVICE_TARGET_EPR);
		QName qname = new QName(SystemConfiguration.NAMESPACE_URI,
				SystemConfiguration.LOCALPART_UPDATE);
		Object[] params = new Object[] { param };
		Class[] types = new Class[] { AccountReceivable.class };
		Object[] res = serviceClient.invokeBlocking(qname, params, types);
		return (AccountReceivable) res[0];
	}
	public static List<AccountReceivable> search(Integer customerId, Calendar fromDate, Calendar toDate ) throws Exception{
		BusinessUtil<AccountReceivable> bs = new BusinessUtil<AccountReceivable>();
		List<AccountReceivable> ret = bs.webserviceGetDtoList(1,
				SystemConfiguration.LOCALPART_SEARCH+"CDD",
				new Class[] { AccountReceivable[].class }, 
				new Object[] { new Integer(customerId), fromDate, toDate },
				SystemConfiguration.AR_SERVICE_TARGET_EPR);
		return ret;
	}
	public static List<AccountReceivable> search(int customerId, Calendar createDate) throws Exception{
		BusinessUtil<AccountReceivable> bs = new BusinessUtil<AccountReceivable>();
		List<AccountReceivable> ret = bs.webserviceGetDtoList(1,
				SystemConfiguration.LOCALPART_SEARCH+"CD",
				new Class[] { AccountReceivable[].class }, 
				new Object[] { new Integer(customerId), createDate},
				SystemConfiguration.AR_SERVICE_TARGET_EPR);
		return ret;
	}
	public static List<AccountReceivable> search(int customerId, String paymentType)throws Exception{
		BusinessUtil<AccountReceivable> bs = new BusinessUtil<AccountReceivable>();
		List<AccountReceivable> ret = bs.webserviceGetDtoList(1,
				SystemConfiguration.LOCALPART_SEARCH+"CP",
				new Class[] { AccountReceivable[].class }, 
				new Object[] { new Integer(customerId), paymentType},
				SystemConfiguration.AR_SERVICE_TARGET_EPR);
		return ret;
	}
	public static List<AccountReceivable> search(int customerId, String paymentType, String txId) throws Exception{
		BusinessUtil<AccountReceivable> bs = new BusinessUtil<AccountReceivable>();
		List<AccountReceivable> ret = bs.webserviceGetDtoList(1,
				SystemConfiguration.LOCALPART_SEARCH+"CPTX",
				new Class[] { AccountReceivable[].class }, 
				new Object[] { new Integer(customerId), paymentType, txId},
				SystemConfiguration.AR_SERVICE_TARGET_EPR);
		return ret;
	}
	public static void main(String args[]) throws Exception{
		try {
			Calendar d = new GregorianCalendar();
			AccountReceivable arDto = new AccountReceivable(Util
					.generateSerial("AR_"), "tranx_id", "order_id",
					"order_type", d, "order_status", "VND", 24,
					"pp-num", "ontract_number", d, "WH", "WH TO",
					9999, "DESC");
			ARAPLogic.getInstance().insert(arDto);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
