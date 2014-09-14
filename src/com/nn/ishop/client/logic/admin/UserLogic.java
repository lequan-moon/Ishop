/**
 * 
 */
package com.nn.ishop.client.logic.admin;

import java.util.List;
import java.util.Vector;

import javax.xml.namespace.QName;

import org.apache.axis2.AxisFault;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

import com.nn.ishop.client.Main;
import com.nn.ishop.client.CActionEvent.CAction;
import com.nn.ishop.client.config.SystemConfiguration;
import com.nn.ishop.client.logic.CommonLogic;
import com.nn.ishop.client.logic.object.EmployeeWrapper;
import com.nn.ishop.client.logic.util.BusinessUtil;
import com.nn.ishop.server.dto.company.Company;
import com.nn.ishop.server.dto.user.Employee;
import com.nn.ishop.server.dto.user.Function;
import com.nn.ishop.server.dto.user.UserRole;

/**
 * @author MiMoon
 * 
 */
public class UserLogic extends CommonLogic{
	public static UserLogic instance = null;

	public static UserLogic getInstance() {
		if (instance == null) {
			instance = new UserLogic();
		}
		return instance;
	}
	
	public List<Function> loadAvailableFunction(){
		List<Function> functions = null;
		try {
			BusinessUtil<Function> bs = new BusinessUtil<Function>();
			functions = bs.webserviceGetDtoList(new Integer(1),
					SystemConfiguration.LOCALPART_EMPLOYEE_LOAD_FUNCTION,
					new Class[] { Function[].class },
					new Object[] { new Integer(1) },
					SystemConfiguration.EMPLOYEE_SERVICE_TARGET_EPR);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	    return functions;
	}
	public List<Function> loadRoleFunction(Integer roleId)throws Exception{
		BusinessUtil<Function> bs = new BusinessUtil<Function>();
	    List<Function> functions = bs.webserviceGetDtoList(roleId
	    		, SystemConfiguration.LOCALPART_EMPLOYEE_GET_ROLE_FUNTION
				, new Class[] { Function[].class }
				,  new Object[] { roleId }
				, SystemConfiguration.EMPLOYEE_SERVICE_TARGET_EPR);		
	    return functions;
	}
	public List<UserRole> loadAvailableUserRoles(){
		List<UserRole> availRoleVector = null;
		try {
			BusinessUtil<UserRole> bu1 = new BusinessUtil<UserRole>();			
			availRoleVector = bu1.webserviceGetDtoList(new Integer(1),
					SystemConfiguration.LOCALPART_EMPLOYEE_GET_ROLE_LIST,
					new Class[] { UserRole[].class }, new Object[] { '1' },
					SystemConfiguration.EMPLOYEE_SERVICE_TARGET_EPR);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return availRoleVector;
	}
	public Vector<EmployeeWrapper> loadListEmployeeWrapper() {
		Vector<EmployeeWrapper> lstEmployeeWrappers = new Vector<EmployeeWrapper>();
		try {
			BusinessUtil<Employee> bu = new BusinessUtil<Employee>();
			List<Employee> lstEmployee = bu.webserviceGetDtoList(new Integer(1),
					SystemConfiguration.LOCALPART_EMPLOYEE_GET_USER_LIST,
					new Class[] { Employee[].class }, new Object[] { '1' },
					SystemConfiguration.EMPLOYEE_SERVICE_TARGET_EPR);

			// add list employee to list employeeWrapper
			for (Employee employee : lstEmployee) {
				EmployeeWrapper emWrapper = new EmployeeWrapper(employee);
				lstEmployeeWrappers.add(emWrapper);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lstEmployeeWrappers;
	}

	public Employee insertEmployee(Employee emp) {

		RPCServiceClient serviceClient;
		Employee e = null;
		try {
			serviceClient = new RPCServiceClient();
			Options options = serviceClient.getOptions();
			options.setTo(SystemConfiguration.EMPLOYEE_SERVICE_TARGET_EPR);
			QName qname = new QName(SystemConfiguration.NAMESPACE_URI,
					SystemConfiguration.LOCALPART_EMPLOYEE_INSERT_EMPLOYEE);
			setExtraEntityInfor(emp, CAction.ADD);
			Object[] params = new Object[] { emp };
			Class[] types = new Class[] { Employee.class };
			Object[] response1 = serviceClient.invokeBlocking(qname, params, types);
			e = (Employee)response1[0];
		} catch (AxisFault ex) {
			ex.printStackTrace();
		}
		return e;
	}

	/**
	 * Update employee without support DELETE (usage_flg = 1)
	 * @param emp
	 */
	public void updateEmployee(Employee emp) {
		RPCServiceClient serviceClient;
		try {
			serviceClient = new RPCServiceClient();
			Options options = serviceClient.getOptions();
			options.setTo(SystemConfiguration.EMPLOYEE_SERVICE_TARGET_EPR);
			QName qname = new QName(SystemConfiguration.NAMESPACE_URI,
					SystemConfiguration.LOCALPART_EMPLOYEE_UPDATE_EMPLOYEE);
			setExtraEntityInfor(emp, CAction.UPDATE);
			Object[] params = new Object[] { emp };
			Class[] types = new Class[] { Employee.class };
			Object[] response1 = serviceClient.invokeBlocking(qname, params, types);
//			System.out.println(ret.toString());
		} catch (AxisFault e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Update Employee support delete 
	 * @param emp
	 * @param caction
	 */
	public void updateEmployee(Employee emp, CAction caction) {

		RPCServiceClient serviceClient;
		try {
			serviceClient = new RPCServiceClient();
			Options options = serviceClient.getOptions();
			options.setTo(SystemConfiguration.EMPLOYEE_SERVICE_TARGET_EPR);
			QName qname = new QName(SystemConfiguration.NAMESPACE_URI,
					SystemConfiguration.LOCALPART_EMPLOYEE_UPDATE_EMPLOYEE);
			setExtraEntityInfor(emp, caction);
			Object[] params = new Object[] { emp };
			Class[] types = new Class[] { Employee.class };
			Object[] response1 = serviceClient.invokeBlocking(qname, params, types);
		} catch (AxisFault e) {
			e.printStackTrace();
		}
	}
	
	public void updateEmployeeRole(int userId, List<UserRole> lstUserRole) {

		RPCServiceClient serviceClient;
		try {
			serviceClient = new RPCServiceClient();
			Options options = serviceClient.getOptions();
			options.setTo(SystemConfiguration.EMPLOYEE_SERVICE_TARGET_EPR);
			QName qname = new QName(SystemConfiguration.NAMESPACE_URI,
					SystemConfiguration.LOCALPART_EMPLOYEE_UPDATE_EROLE);
			Object[] params = new Object[] { userId, lstUserRole.toArray() };
			Class[] types = new Class[] { boolean.class };
			Object[] response1 = serviceClient.invokeBlocking(qname, params, types);
		} catch (AxisFault e) {
			e.printStackTrace();
		}
	}
	
	public UserRole insertRole(UserRole role) {
		try {
			RPCServiceClient serviceClient = new RPCServiceClient();
			Options options = serviceClient.getOptions();
			options.setTo(SystemConfiguration.EMPLOYEE_SERVICE_TARGET_EPR);
			QName qname = new QName(SystemConfiguration.NAMESPACE_URI,
					SystemConfiguration.LOCALPART_EMPLOYEE_INSERT_ROLE);
			setExtraEntityInfor(role, CAction.ADD);
			Object[] params = new Object[] { role, role.getRoleFunctions().toArray()};
			Class[] types = new Class[] { UserRole.class };
			Object[] response1 = serviceClient.invokeBlocking(qname, params, types);
			role = (UserRole)response1[0];
		} catch (AxisFault ex) {
			ex.printStackTrace();
		}
		return role;
	}
	
	public UserRole updateRole(UserRole role) {
		try {
			RPCServiceClient serviceClient = new RPCServiceClient();
			Options options = serviceClient.getOptions();
			options.setTo(SystemConfiguration.EMPLOYEE_SERVICE_TARGET_EPR);
			QName qname = new QName(SystemConfiguration.NAMESPACE_URI,
					SystemConfiguration.LOCALPART_EMPLOYEE_UPDATE_FROLE);
			setExtraEntityInfor(role, CAction.UPDATE);
			Object[] params = new Object[] { role,role.getRoleFunctions().toArray() };
			Class[] types = new Class[] { UserRole.class };
			Object[] response1 = serviceClient.invokeBlocking(qname, params, types);
			role = (UserRole)response1[0];
		} catch (AxisFault ex) {
			ex.printStackTrace();
		}
		return role;
	}

	public Vector<EmployeeWrapper> loadListEmployeeWrapperWithSelectedCompany(Company selectedCompany) {
		Vector<EmployeeWrapper> lstEmployeeWrappers = new Vector<EmployeeWrapper>();

		try {
			BusinessUtil<Employee> bu = new BusinessUtil<Employee>();
			List<Employee> lstEmployee = bu.webserviceGetDtoList(new Integer(1),
					SystemConfiguration.LOCALPART_EMPLOYEE_GET_USER_LIST_FOR_COMPANY,
					new Class[] { Employee[].class }, new Object[] { String.valueOf(selectedCompany.getId()) },
					SystemConfiguration.EMPLOYEE_SERVICE_TARGET_EPR);

			// add list employee to list employeeWrapper
			for (Employee employee : lstEmployee) {
				EmployeeWrapper emWrapper = new EmployeeWrapper(employee);
				lstEmployeeWrappers.add(emWrapper);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lstEmployeeWrappers;
	}
	public Employee getUser(int id){
		Employee ret = null;
		try{
			  BusinessUtil<Employee> bu = new BusinessUtil<Employee>();
			  ret = bu.webserviceGetDto(new Integer(1)
			  	, SystemConfiguration.LOCALPART_EMPLOYEE_GET_USER
			  	, new Class[]{Employee.class}
			  	, new Object[]{new Integer(id)}
			  	, SystemConfiguration.EMPLOYEE_SERVICE_TARGET_EPR
			  );
			
		  }catch (Exception e)
		  {
			  e.printStackTrace();
		  }
		return ret;
	}

}
