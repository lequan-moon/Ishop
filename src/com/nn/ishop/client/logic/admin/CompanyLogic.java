package com.nn.ishop.client.logic.admin;

import java.math.BigInteger;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.axis2.AxisFault;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

import com.nn.ishop.client.CActionEvent.CAction;
import com.nn.ishop.client.config.SystemConfiguration;
import com.nn.ishop.client.gui.components.CCompanyTreeNode;
import com.nn.ishop.client.logic.CommonLogic;
import com.nn.ishop.client.logic.util.BusinessUtil;
import com.nn.ishop.server.dto.company.Company;
import com.nn.ishop.server.dto.company.CompanyBusinessField;

public class CompanyLogic extends CommonLogic {
	public static CompanyLogic instance;
	
	public Company getCompanyById(int companyId){
		Company ret = null;
		BusinessUtil<Company> bu = new BusinessUtil<Company>();
		try {
			ret = bu.webserviceGetDto(new Integer(1), SystemConfiguration.LOCALPART_COMPANY_GET_COMPANY_BY_ID, new Class[] { Company.class },
					new Object[] { companyId }, SystemConfiguration.COMPANY_SERVICE_TARGET_EPR);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	public List<Company> getListCompany() {
		List<Company> result = null;
		BusinessUtil<Company> bu = new BusinessUtil<Company>();
		try {
			result = bu.webserviceGetDtoList(new Integer(1), SystemConfiguration.LOCALPART_COMPANY_GET_LIST_COMPANY, new Class[] { Company[].class },
					new Object[] { '1' }, SystemConfiguration.COMPANY_SERVICE_TARGET_EPR);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public static List<Company> getListChildrenCompany(int companyId) {
		List<Company> result = null;
		BusinessUtil<Company> bu = new BusinessUtil<Company>();
		try {
			result = bu.webserviceGetDtoList(new Integer(1), SystemConfiguration.LOCALPART_COMPANY_GET_LIST_CHILDREN_COMPANY,
					new Class[] { Company[].class }, new Object[] { companyId }, SystemConfiguration.COMPANY_SERVICE_TARGET_EPR);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static CCompanyTreeNode loadTreeListCompany(CCompanyTreeNode node, List<Company> lstCompany) {
		for (Company company : lstCompany) {
			long totalUser = countUser(company.getId());
			String oldName = company.getName();
			company.setName(oldName + "(" +String.valueOf(totalUser)+")");
			CCompanyTreeNode newNode = new CCompanyTreeNode(company);			
			List<Company> result = getListChildrenCompany(company.getId());
			if (result.size() > 0) {
				loadTreeListCompany(newNode, result);
			}
			node.add(newNode);
		}
		return node;
	}
	
	public static Company insertCompany(Company comp){
		RPCServiceClient serviceClient;
		Company e = null;
		try {
			serviceClient = new RPCServiceClient();
			Options options = serviceClient.getOptions();
			options.setTo(SystemConfiguration.COMPANY_SERVICE_TARGET_EPR);
			QName qname = new QName(SystemConfiguration.NAMESPACE_URI,
					SystemConfiguration.LOCALPART_COMPANY_INSERT_COMPANY);
			setExtraEntityInfor(comp, CAction.ADD);
			Object[] params = new Object[] { comp };
			Class[] types = new Class[] { Company.class };
			Object[] response1 = serviceClient.invokeBlocking(qname, params, types);
			e = (Company)response1[0];
		} catch (AxisFault ex) {
			ex.printStackTrace();
		}
		return e;
	}
	
	public static Company updateCompany(Company comp, CAction caction){
		RPCServiceClient serviceClient;
		Company e = null;
		try {
			serviceClient = new RPCServiceClient();
			Options options = serviceClient.getOptions();
			options.setTo(SystemConfiguration.COMPANY_SERVICE_TARGET_EPR);
			QName qname = new QName(SystemConfiguration.NAMESPACE_URI,
					SystemConfiguration.LOCALPART_COMPANY_UPDATE_COMPANY);
			setExtraEntityInfor(comp, caction);
			Object[] params = new Object[] { comp };
			Class[] types = new Class[] { Company.class };
			Object[] response1 = serviceClient.invokeBlocking(qname, params, types);
			e = (Company)response1[0];
		} catch (AxisFault ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		return e;
	}
	
	public static CompanyBusinessField insertCompanyBusinessField(CompanyBusinessField bussinessField){
		RPCServiceClient serviceClient;
		CompanyBusinessField e = null;
		try {
			serviceClient = new RPCServiceClient();
			Options options = serviceClient.getOptions();
			options.setTo(SystemConfiguration.COMPANY_BUSINESS_FIELD_SERVICE_TARGET_EPR);
			QName qname = new QName(SystemConfiguration.NAMESPACE_URI,
					SystemConfiguration.LOCALPART_BUSINESS_FIELD_INSERT_BUSINESS_FIELD);
			setExtraEntityInfor(bussinessField, CAction.ADD);
			Object[] params = new Object[] { bussinessField };
			Class[] types = new Class[] { CompanyBusinessField.class };
			Object[] response1 = serviceClient.invokeBlocking(qname, params, types);
			e = (CompanyBusinessField)response1[0];
		} catch (AxisFault ex) {
			ex.printStackTrace();
		}
		return e;
	}
	
	public static CompanyBusinessField updateCompanyBusinessField(CompanyBusinessField comp, CAction caction){
		RPCServiceClient serviceClient;
		CompanyBusinessField e = null;
		try {
			serviceClient = new RPCServiceClient();
			Options options = serviceClient.getOptions();
			options.setTo(SystemConfiguration.COMPANY_BUSINESS_FIELD_SERVICE_TARGET_EPR);
			QName qname = new QName(SystemConfiguration.NAMESPACE_URI,
					SystemConfiguration.LOCALPART_BUSINESS_FIELD_UPDATE_BUSINESS_FIELD);
			setExtraEntityInfor(comp, caction);
			Object[] params = new Object[] { comp };
			Class[] types = new Class[] { CompanyBusinessField.class };
			Object[] response1 = serviceClient.invokeBlocking(qname, params, types);
			e = (CompanyBusinessField)response1[0];
		} catch (AxisFault ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		return e;
	}
	
	public List<CompanyBusinessField> getListCompanyBusinessField(int companyId) {
		List<CompanyBusinessField> result = null;
		BusinessUtil<CompanyBusinessField> bu = new BusinessUtil<CompanyBusinessField>();
		try {
			result = bu.webserviceGetDtoList(new Integer(1), SystemConfiguration.LOCALPART_BUSINESS_FIELD_GET_LIST_BUSINESS_FIELD, new Class[] { CompanyBusinessField[].class },
					new Object[] { companyId }, SystemConfiguration.COMPANY_BUSINESS_FIELD_SERVICE_TARGET_EPR);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	public static long countUser(Integer compId){
		RPCServiceClient serviceClient;
		Long ret = new Long("0");
		try {
			serviceClient = new RPCServiceClient();
			Options options = serviceClient.getOptions();
			
			options.setTo(SystemConfiguration.COMPANY_SERVICE_TARGET_EPR);
			QName qname = new QName(SystemConfiguration.NAMESPACE_URI,
					SystemConfiguration.LOCALPART_COMPANY_COUNT_USER);
			
			Object[] params = new Object[] { compId };
			Class<?>[] types = new Class[] { Long.class };
			Object[] response1 = serviceClient.invokeBlocking(qname, params, types);
			ret = (Long)response1[0];
		} catch (AxisFault ex) {
			ex.printStackTrace();
		}
		return ret.longValue();
	}	
	public static CompanyLogic getInstance() {
		if (instance == null) {
			instance = new CompanyLogic();
		}
		return instance;
	}
	

}
