package com.nn.ishop.client.logic.admin;

import java.util.List;
import java.util.Vector;

import javax.xml.namespace.QName;

import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

import com.nn.ishop.client.CActionEvent.CAction;
import com.nn.ishop.client.config.SystemConfiguration;
import com.nn.ishop.client.logic.CommonLogic;
import com.nn.ishop.client.logic.util.BusinessUtil;
import com.nn.ishop.server.dto.customer.Customer;
import com.nn.ishop.server.dto.customer.CustomerPriceList;
import com.nn.ishop.server.dto.pricelist.PriceList;

public class CustomerLogic extends CommonLogic{
	private static CustomerLogic instance = null;
	public static CustomerLogic getInstance(){
		if (instance == null)
			instance = new CustomerLogic();
		return instance; 
	}
	//================ Customer ============================//
	/**
	 * INSERT or UPDATE a Customer
	 * @param customer a Customer instance to insert or update to database	 
	 * @return Customer with new information
	 */
	@SuppressWarnings("unchecked")
	public Customer insertCustomer(Customer customer) throws Exception{
		setExtraEntityInfor(customer, CAction.ADD);
		RPCServiceClient serviceClient = new RPCServiceClient();
		Options options = serviceClient.getOptions();
		options.setTo(SystemConfiguration.CUSTOMER_SERVICE_TARGET_EPR);
		QName qname = new QName(SystemConfiguration.NAMESPACE_URI,
				SystemConfiguration.LP_CUS_INSERTCUSTOMER);
		Object[] params = new Object[] { customer };
		Class[] types = new Class[] { Customer.class };
		Object[] res = serviceClient.invokeBlocking(qname, params, types);
		return (Customer) res[0];
		
	}
	@SuppressWarnings("unchecked")
	public Customer updateCustomer(Customer customer) throws Exception{
		setExtraEntityInfor(customer, CAction.UPDATE);
		RPCServiceClient serviceClient = new RPCServiceClient();
		Options options = serviceClient.getOptions();
		options.setTo(SystemConfiguration.CUSTOMER_SERVICE_TARGET_EPR);
		QName qname = new QName(SystemConfiguration.NAMESPACE_URI,
				SystemConfiguration.LP_CUS_UPDATECUSTOMER);
		Object[] params = new Object[] { customer };
		Class[] types = new Class[] { Customer.class };
		Object[] res = serviceClient.invokeBlocking(qname, params, types);
		return (Customer) res[0];		
	}
	@SuppressWarnings("unchecked")
	public Customer deleteCustomer(Customer customer) throws Exception{
		setExtraEntityInfor(customer, CAction.DELETE);
		RPCServiceClient serviceClient = new RPCServiceClient();
		Options options = serviceClient.getOptions();
		options.setTo(SystemConfiguration.CUSTOMER_SERVICE_TARGET_EPR);
		QName qname = new QName(SystemConfiguration.NAMESPACE_URI,
				SystemConfiguration.LP_CUS_UPDATECUSTOMER);
		Object[] params = new Object[] { customer };
		Class[] types = new Class[] { Customer.class };
		Object[] res = serviceClient.invokeBlocking(qname, params, types);
		return (Customer) res[0];		
	}
	
	public List<Customer> loadCustomer() throws Exception{		
		BusinessUtil<Customer> bs = new BusinessUtil<Customer>();
		List<Customer> customerList = bs.webserviceGetDtoList(1,
				SystemConfiguration.LP_CUS_LOADCUSTOMER,
				new Class[] { Customer[].class }, 
				new Object[] { new Integer(1) },
				SystemConfiguration.CUSTOMER_SERVICE_TARGET_EPR);
		return customerList;	
	}
	public Vector<Customer> loadCustomerVector() throws Exception{		
		BusinessUtil<Customer> bs = new BusinessUtil<Customer>();
		List<Customer> customerList = bs.webserviceGetDtoList(1,
				SystemConfiguration.LP_CUS_LOADCUSTOMER,
				new Class[] { Customer[].class }, 
				new Object[] { new Integer(1) },
				SystemConfiguration.CUSTOMER_SERVICE_TARGET_EPR);
		Vector<Customer> vctCustomer = new Vector<Customer>();
		for(Customer c:customerList)
			vctCustomer.add(c);
		return vctCustomer;	
	}
	public Customer getCustomer(int cusId) throws Exception{
		BusinessUtil<Customer> bs = new BusinessUtil<Customer>();
		Customer customer = bs.webserviceGetDto(1,
				SystemConfiguration.LP_CUS_GETCUSTOMER,
				new Class[] { Customer.class }, 
				new Object[] { new Integer(cusId) },
				SystemConfiguration.CUSTOMER_SERVICE_TARGET_EPR);
		return customer;
	}
	//================ CustomerPriceList ============================//
	@SuppressWarnings("unchecked")
	public CustomerPriceList insertCustomerPriceList(CustomerPriceList customerPriceList) throws Exception{
		setExtraEntityInfor(customerPriceList, CAction.ADD);
		RPCServiceClient serviceClient = new RPCServiceClient();
		Options options = serviceClient.getOptions();
		options.setTo(SystemConfiguration.CUSTOMER_SERVICE_TARGET_EPR);
		QName qname = new QName(SystemConfiguration.NAMESPACE_URI,
				SystemConfiguration.LP_CUS_INSERTCUSTOMERPRICELIST);
		Object[] params = new Object[] { customerPriceList };
		Class[] types = new Class[] { CustomerPriceList.class };
		Object[] res = serviceClient.invokeBlocking(qname, params, types);
		return (CustomerPriceList) res[0];		
	}
	
	@SuppressWarnings("unchecked")
	public CustomerPriceList updateCustomerPriceList(CustomerPriceList customerPriceList) throws Exception{
		setExtraEntityInfor(customerPriceList, CAction.UPDATE);
		RPCServiceClient serviceClient = new RPCServiceClient();
		Options options = serviceClient.getOptions();
		options.setTo(SystemConfiguration.CUSTOMER_SERVICE_TARGET_EPR);
		QName qname = new QName(SystemConfiguration.NAMESPACE_URI,
				SystemConfiguration.LP_CUS_UPDATECUSTOMERPRICELIST);
		Object[] params = new Object[] { customerPriceList };
		Class[] types = new Class[] { CustomerPriceList.class };
		Object[] res = serviceClient.invokeBlocking(qname, params, types);
		return (CustomerPriceList) res[0];		
	}
	public List<CustomerPriceList> loadCustomerPriceList() throws Exception{		
		BusinessUtil<CustomerPriceList> bs = new BusinessUtil<CustomerPriceList>();
		List<CustomerPriceList> retList = bs.webserviceGetDtoList(1,
				SystemConfiguration.LP_CUS_LOADCUSTOMERPRICELIST,
				new Class[] { CustomerPriceList[].class }, 
				new Object[] { new Integer(1) },
				SystemConfiguration.CUSTOMER_SERVICE_TARGET_EPR);
		return retList;	
	}
	public CustomerPriceList getCustomerPriceList(int cusPriceListId) throws Exception{
		BusinessUtil<CustomerPriceList> bs = new BusinessUtil<CustomerPriceList>();
		CustomerPriceList ret = bs.webserviceGetDto(1,
				SystemConfiguration.LP_CUS_GETCUSTOMERPRICELIST,
				new Class[] { CustomerPriceList.class }, 
				new Object[] { new Integer(cusPriceListId) },
				SystemConfiguration.CUSTOMER_SERVICE_TARGET_EPR);
		return ret;
	}
}
