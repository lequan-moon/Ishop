package com.nn.ishop.client.logic.admin;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.axis2.AxisFault;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

import com.nn.ishop.client.CActionEvent.CAction;
import com.nn.ishop.client.config.SystemConfiguration;
import com.nn.ishop.client.logic.CommonLogic;
import com.nn.ishop.client.logic.util.BusinessUtil;
import com.nn.ishop.server.dto.company.Company;
import com.nn.ishop.server.dto.customer.Customer;
import com.nn.ishop.server.dto.grn.PurchaseGrn;
import com.nn.ishop.server.dto.purchase.Provider;
import com.nn.ishop.server.dto.purchase.PurchasingPlan;
import com.nn.ishop.server.dto.purchase.PurchasingPlanDetail;
import com.nn.ishop.server.dto.purchase.PurchasingPlanType;

public class PurchaseLogic extends CommonLogic {
	private static PurchaseLogic instance;

	public List<Customer> getListProvider() {
		List<Customer> ret = new ArrayList<Customer>();
		BusinessUtil<Customer> bu = new BusinessUtil<Customer>();
		try {
			ret = bu.webserviceGetDtoList(new Integer(1), 
					SystemConfiguration.LOCALPART_GET_PROVIDER_LIST, 
					new Class[] { Customer[].class }, 
					new Integer[]{1},
					SystemConfiguration.PURCHASE_SERVICE_TARGET_EPR);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	
	public List<PurchasingPlanType> getListPurchasingPlanType(){
		List<PurchasingPlanType> ret = new ArrayList<PurchasingPlanType>();
		BusinessUtil<PurchasingPlanType> bu = new BusinessUtil<PurchasingPlanType>();
		try {
			ret = bu.webserviceGetDtoList(new Integer(1), 
					SystemConfiguration.LOCALPART_GET_PURCHASING_PLAN_TYPE_LIST, 
					new Class[] { PurchasingPlanType[].class }, 
					new Integer[]{1},
					SystemConfiguration.PURCHASE_SERVICE_TARGET_EPR);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	
	public PurchasingPlan getPurchasingPlan(String pp_id) {
		PurchasingPlan ret = new PurchasingPlan();
		BusinessUtil<PurchasingPlan> bu = new BusinessUtil<PurchasingPlan>();
		try {
			ret = bu.webserviceGetDto(new Integer(1), SystemConfiguration.LOCALPART_GET_PURCHASING_PLAN, new Class[] { PurchasingPlan.class },
					new String[] { pp_id }, SystemConfiguration.PURCHASE_SERVICE_TARGET_EPR);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	
	public PurchasingPlan getPurchasingPlanByTranxId(String tranx_id) {
		PurchasingPlan ret = new PurchasingPlan();
		BusinessUtil<PurchasingPlan> bu = new BusinessUtil<PurchasingPlan>();
		try {
			ret = bu.webserviceGetDto(new Integer(1), SystemConfiguration.LOCALPART_GET_PURCHASING_PLAN_BY_TRANX_ID, new Class[] { PurchasingPlan.class },
					new String[] { tranx_id }, SystemConfiguration.PURCHASE_SERVICE_TARGET_EPR);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	
	public List<PurchasingPlan> getListPurchasingPlan(){
		List<PurchasingPlan> ret = new ArrayList<PurchasingPlan>();
		BusinessUtil<PurchasingPlan> bu = new BusinessUtil<PurchasingPlan>();
		try {
			ret = bu.webserviceGetDtoList(new Integer(1), 
					SystemConfiguration.LOCALPART_GET_PURCHASING_PLAN_LIST, 
					new Class[] { PurchasingPlan[].class }, 
					new Integer[]{1},
					SystemConfiguration.PURCHASE_SERVICE_TARGET_EPR);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	
	public List<PurchasingPlanDetail> getListPurchasingPlanDetail(String ppId){
		List<PurchasingPlanDetail> ret = new ArrayList<PurchasingPlanDetail>();
		BusinessUtil<PurchasingPlanDetail> bu = new BusinessUtil<PurchasingPlanDetail>();
		try {
			ret = bu.webserviceGetDtoList(new Integer(1), 
					SystemConfiguration.LOCALPART_GET_PURCHASING_DETAIL_LIST, 
					new Class[] { PurchasingPlanDetail[].class }, 
					new String[]{ppId},
					SystemConfiguration.PURCHASE_SERVICE_TARGET_EPR);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	public PurchasingPlan insertPurchasingPlan(PurchasingPlan pp){
		RPCServiceClient serviceClient;
		PurchasingPlan e = null;
		try {
			serviceClient = new RPCServiceClient();
			Options options = serviceClient.getOptions();
			options.setTo(SystemConfiguration.PURCHASE_SERVICE_TARGET_EPR);
			QName qname = new QName(SystemConfiguration.NAMESPACE_URI,
					SystemConfiguration.LOCALPART_INSERT_PURCHASING_PLAN);
			setExtraEntityInfor(pp, CAction.ADD);
			Object[] params = new Object[] { pp };
			Class[] types = new Class[] { PurchasingPlan.class };
			Object[] response1 = serviceClient.invokeBlocking(qname, params, types);
			e = (PurchasingPlan)response1[0];
		} catch (AxisFault ex) {
			ex.printStackTrace();
		}
		return e;
	}
	
	public PurchasingPlanDetail insertPurchasingPlanDetail(PurchasingPlanDetail pp_detail){
		RPCServiceClient serviceClient;
		PurchasingPlanDetail e = null;
		try {
			serviceClient = new RPCServiceClient();
			Options options = serviceClient.getOptions();
			options.setTo(SystemConfiguration.PURCHASE_SERVICE_TARGET_EPR);
			QName qname = new QName(SystemConfiguration.NAMESPACE_URI,
					SystemConfiguration.LOCALPART_INSERT_PURCHASING_PLAN_DETAIL);
			setExtraEntityInfor(pp_detail, CAction.ADD);
			Object[] params = new Object[] { pp_detail };
			Class[] types = new Class[] { PurchasingPlanDetail.class };
			Object[] response1 = serviceClient.invokeBlocking(qname, params, types);
			e = (PurchasingPlanDetail)response1[0];
		} catch (AxisFault ex) {
			ex.printStackTrace();
		}
		return e;
	}
	
	public static PurchaseLogic getInstance() {
		if (instance == null) {
			instance = new PurchaseLogic();
		}
		return instance;
	}
}
