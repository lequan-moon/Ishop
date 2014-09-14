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
import com.nn.ishop.server.dto.customer.Customer;
import com.nn.ishop.server.dto.grn.PurchaseGrn;
import com.nn.ishop.server.dto.grn.PurchaseGrnDetail;
import com.nn.ishop.server.dto.purchase.PurchasingPlan;
import com.nn.ishop.server.dto.purchase.PurchasingPlanDetail;

public class PurchaseGrnLogic extends CommonLogic {
	private static PurchaseGrnLogic instance;

	public List<PurchaseGrn> getPurchaseGrnList() {
		List<PurchaseGrn> ret = new ArrayList<PurchaseGrn>();
		BusinessUtil<PurchaseGrn> bu = new BusinessUtil<PurchaseGrn>();
		try {
			ret = bu.webserviceGetDtoList(new Integer(1), SystemConfiguration.LOCALPART_GET_PURCHASE_GRN_LIST, new Class[] { PurchaseGrn[].class },
					new Integer[] { 1 }, SystemConfiguration.PURCHASE_GRN_SERVICE_TARGET_EPR);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	public PurchaseGrn getPurchaseGrn(String purchaseGrnId) {
		PurchaseGrn ret = new PurchaseGrn();
		BusinessUtil<PurchaseGrn> bu = new BusinessUtil<PurchaseGrn>();
		try {
			ret = bu.webserviceGetDto(new Integer(1), SystemConfiguration.LOCALPART_GET_PURCHASE_GRN, new Class[] { PurchaseGrn.class },
					new String[] { purchaseGrnId }, SystemConfiguration.PURCHASE_GRN_SERVICE_TARGET_EPR);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	
	public PurchaseGrn getPurchaseGrnByPPId(String pp_id) {
		PurchaseGrn ret = new PurchaseGrn();
		BusinessUtil<PurchaseGrn> bu = new BusinessUtil<PurchaseGrn>();
		try {
			ret = bu.webserviceGetDto(new Integer(1), SystemConfiguration.LOCALPART_GET_PURCHASE_GRN_BY_PPID, new Class[] { PurchaseGrn.class },
					new String[] { pp_id }, SystemConfiguration.PURCHASE_GRN_SERVICE_TARGET_EPR);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	public List<PurchaseGrnDetail> getPurchaseGrnDetail(String purchaseGrnId) {
		List<PurchaseGrnDetail> ret = new ArrayList<PurchaseGrnDetail>();
		BusinessUtil<PurchaseGrnDetail> bu = new BusinessUtil<PurchaseGrnDetail>();
		try {
			ret = bu.webserviceGetDtoList(new Integer(1), SystemConfiguration.LOCALPART_GET_PURCHASE_GRN_DETAIL, new Class[] { PurchaseGrnDetail[].class },
					new String[] { purchaseGrnId }, SystemConfiguration.PURCHASE_GRN_SERVICE_TARGET_EPR);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		return ret;
	}
	
	public PurchaseGrn insertPurchaseGrn(PurchaseGrn pgrn){
		RPCServiceClient serviceClient;
		PurchaseGrn e = null;
		try {
			serviceClient = new RPCServiceClient();
			Options options = serviceClient.getOptions();
			options.setTo(SystemConfiguration.PURCHASE_GRN_SERVICE_TARGET_EPR);
			QName qname = new QName(SystemConfiguration.NAMESPACE_URI,
					SystemConfiguration.LOCALPART_INSERT_PURCHASE_GRN);
			setExtraEntityInfor(pgrn, CAction.ADD);
			Object[] params = new Object[] { pgrn };
			Class[] types = new Class[] { PurchaseGrn.class };
			Object[] response1 = serviceClient.invokeBlocking(qname, params, types);
			e = (PurchaseGrn)response1[0];
		} catch (AxisFault ex) {
			ex.printStackTrace();
		}
		return e;
	}
	
	/**
	 * Marked as deprecated
	 * @param grn_detail
	 * @return
	 * @deprecated  insert together with PurchaseGrn
	 */
	public PurchaseGrnDetail insertPurchaseGrnDetail(PurchaseGrnDetail grn_detail){
		RPCServiceClient serviceClient;
		PurchaseGrnDetail e = null;
		try {
			serviceClient = new RPCServiceClient();
			Options options = serviceClient.getOptions();
			options.setTo(SystemConfiguration.PURCHASE_GRN_SERVICE_TARGET_EPR);
			QName qname = new QName(SystemConfiguration.NAMESPACE_URI,
					SystemConfiguration.LOCALPART_INSERT_PURCHASE_GRN_DETAIL);
			setExtraEntityInfor(grn_detail, CAction.ADD);
			Object[] params = new Object[] { grn_detail };
			Class[] types = new Class[] { PurchaseGrnDetail.class };
			Object[] response1 = serviceClient.invokeBlocking(qname, params, types);
			e = (PurchaseGrnDetail)response1[0];
		} catch (AxisFault ex) {
			ex.printStackTrace();
		}
		return e;
	}
	
	public static PurchaseGrnLogic getInstance() {
		if (instance == null) {
			instance = new PurchaseGrnLogic();
		}
		return instance;
	}
}
