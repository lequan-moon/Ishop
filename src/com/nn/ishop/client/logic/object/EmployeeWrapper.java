package com.nn.ishop.client.logic.object;

import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;

import com.nn.ishop.client.config.SystemConfiguration;
import com.nn.ishop.client.logic.util.BusinessUtil;
import com.nn.ishop.server.dto.user.Employee;
import com.nn.ishop.server.dto.user.Function;
import com.nn.ishop.server.dto.user.UserRole;
import com.nn.ishop.server.types.FunctionType;
import com.nn.ishop.server.types.RoleType;

/**
 * Generate wrapper object for Employee Entity
 * each time when you initializing this class. It will self-construct the remain objects.
 * Such as Role, Function
 * @author NguyenNghia
 *
 */
public class EmployeeWrapper {
	private static Logger logger = Logger.getLogger(EmployeeWrapper.class);
	Employee em;
	Hashtable<UserRole, List<Function>> roleFunctions;
	List<UserRole> role;
	
	public EmployeeWrapper(){
		
	}
	
	public EmployeeWrapper(Employee em) {
		super();
		try{
			this.em = em;			
			loadEmployeeRole();
		}catch(Exception e){
			logger.error(e);
		}
	}
	public Employee getEm() {
		return em;
	}	

	@Override
	public String toString() {
		return "[" + em.toString() + "]";
	}
	protected void loadEmployeeRole()throws Exception{
		BusinessUtil<UserRole> bu = new BusinessUtil<UserRole>();
		this.role = bu.webserviceGetDtoList(
				em.getId()
				, SystemConfiguration.LOCALPART_EMPLOYEE_GET_USER_ROLE
				, new Class[]{UserRole[].class}
				, new Object[]{em.getId()}
				, SystemConfiguration.EMPLOYEE_SERVICE_TARGET_EPR);
		roleFunctions = new Hashtable<UserRole, List<Function>>();
		for(UserRole ur: this.role){
			roleFunctions.put(ur, loadRoleFunction(ur.getRoleId()));
		}
		
	}
	protected List<Function> loadRoleFunction(Integer roleId)throws Exception{
		BusinessUtil<Function> bs = new BusinessUtil<Function>();
	    List<Function> functions = bs.webserviceGetDtoList(roleId
	    		, "getRoleFunctionByRoleId"
				, new Class[] { Function[].class }
				,  new Object[] { roleId }
				, SystemConfiguration.EMPLOYEE_SERVICE_TARGET_EPR);		
	    return functions;
	}
	public boolean canPerformFunction(FunctionType function){
		boolean ret = false;
		if(getUserRoleName().equalsIgnoreCase(RoleType.ADMIN.toString())) return true;
		for(UserRole ur:role){
			List<Function> funcs = roleFunctions.get(ur);
			for(Function f: funcs){
				ret = f.getFunctionName().equalsIgnoreCase(function.toString());
				if(ret)break;
			}			
			if(ret)break;
		}
		return ret;		
	}
	public boolean canPerformFunction(String function){
		boolean ret = false;
		if(getUserRoleName().equalsIgnoreCase(RoleType.ADMIN.toString())) return true;
		for(UserRole ur:role){
			List<Function> funcs = roleFunctions.get(ur);
			for(Function f: funcs){
				ret = f.getFunctionName().equalsIgnoreCase(function.toString());
				if(ret)break;
			}
			if(ret)break;
		}  
		return ret;		
	}
	/**
	 * Check if user can perform product maintenance
	 * User just can maintenance product if he got at least one function related to
	 * @return
	 */
	public boolean canMaintainPROD(){
		if(getUserRoleName().equalsIgnoreCase(RoleType.ADMIN.toString())) return true;
		return (
				canPerformFunction(FunctionType.PROD_CREATE_CMDFunc)
				|| canPerformFunction(FunctionType.PROD_EDIT_CMDFunc)
				|| canPerformFunction(FunctionType.PROD_DELETE_CMDFunc)
				|| canPerformFunction(FunctionType.PROD_IMPORT_CMDFunc)
				|| canPerformFunction(FunctionType.PROD_EXPORT_CMDFunc)
				|| canPerformFunction(FunctionType.PROD_SEARCH_CMDFunc)
				|| canPerformFunction(FunctionType.PROD_CREATEITEM_CMDFunc)
				|| canPerformFunction(FunctionType.PROD_EDITITEM_CMDFunc)
				|| canPerformFunction(FunctionType.PROD_DELETEITEM_CMDFunc)
				|| canPerformFunction(FunctionType.PROD_SEARCHITEM_CMDFunc)
				|| canPerformFunction(FunctionType.PROD_CREATECHIL_CMDFunc)
				|| canPerformFunction(FunctionType.PROD_EDITCHIL_CMDFunc)
				|| canPerformFunction(FunctionType.PROD_DELETECHIL_CMDFunc)
				|| canPerformFunction(FunctionType.PROD_CREATEINFO_CMDFunc)
				|| canPerformFunction(FunctionType.PROD_EDITINFO_CMDFunc)
				|| canPerformFunction(FunctionType.PROD_DELETEINFO_CMDFunc)
				|| canPerformFunction(FunctionType.PROD_DELETEPRIN_CMDFunc)
				);
	}
	/**
	 * Check if user can maintain user
	 * @return
	 */	
	public boolean canMaintainUSER(){
		if(getUserRoleName().equalsIgnoreCase(RoleType.ADMIN.toString())) return true;
		return (
				canPerformFunction(FunctionType.USER_CREATE_CMDFunc)
				|| canPerformFunction(FunctionType.USER_EDIT_CMDFunc)
				|| canPerformFunction(FunctionType.USER_WRITE_CMDFunc)
				|| canPerformFunction(FunctionType.USER_ADDPMS_CMDFunc)
				|| canPerformFunction(FunctionType.USER_REMOVEPMS_CMDFunc)
				|| canPerformFunction(FunctionType.USER_CREATEROLE_CMDFunc)
				|| canPerformFunction(FunctionType.USER_EDITROLE_CMDFunc)
				|| canPerformFunction(FunctionType.USER_SEARCH_CMDFunc)	
				);
	}
	
	/**
	 * Check if user can maintain customer
	 * @return
	 */	
	public boolean canMaintainCUST(){
		if(getUserRoleName().equalsIgnoreCase(RoleType.ADMIN.toString())) return true;
		return (
				canPerformFunction(FunctionType.CUST_CREATE_CMDFunc)
				|| canPerformFunction(FunctionType.CUST_UPDATE_CMDFunc)
				|| canPerformFunction(FunctionType.CUST_DELETE_CMDFunc)
				|| canPerformFunction(FunctionType.CUST_SORT_CMDFunc)
				|| canPerformFunction(FunctionType.CUST_EDITPROF_CMDFunc)
				|| canPerformFunction(FunctionType.CUST_EDITCONT_CMDFunc)
				|| canPerformFunction(FunctionType.CUST_EDITDELI_CMDFunc)
				|| canPerformFunction(FunctionType.CUST_EDITBILL_CMDFunc)
				|| canPerformFunction(FunctionType.CUST_SEARCH_CMDFunc)
				|| canPerformFunction(FunctionType.CUST_EDITNETW_CMDFunc)			
				);
	}
	/**
	 * Check if user can maintain warehouse
	 * @return
	 */	
	public boolean canMaintainWAHS(){
		if(getUserRoleName().equalsIgnoreCase(RoleType.ADMIN.toString())) return true;
		return (
				canPerformFunction(FunctionType.WAHS_CREATEWH_CMDFunc)
				|| canPerformFunction(FunctionType.WAHS_EDITWH_CMDFunc)
				|| canPerformFunction(FunctionType.WAHS_DELETEWH_CMDFunc)
				|| canPerformFunction(FunctionType.WAHS_SORTWH_CMDFunc)
				|| canPerformFunction(FunctionType.WAHS_SEARCHWH_CMDFunc)
				|| canPerformFunction(FunctionType.WAHS_CREATELOT_CMDFunc)
				|| canPerformFunction(FunctionType.WAHS_EDITLOT_CMDFunc)
				|| canPerformFunction(FunctionType.WAHS_DELETELOT_CMDFunc)
				|| canPerformFunction(FunctionType.WAHS_SORTLOT_CMDFunc)
				|| canPerformFunction(FunctionType.WAHS_SEARCHLOT_CMDFunc)
				|| canPerformFunction(FunctionType.WAHS_IMPORTLOT_CMDFunc)
				|| canPerformFunction(FunctionType.WAHS_EXPORTLOT_CMDFunc)			
				);
	}
	
	/**
	 * Check if user can maintain COMPANY
	 * @return
	 */	
	public boolean canMaintainCOMP(){
		if(getUserRoleName().equalsIgnoreCase(RoleType.ADMIN.toString())) return true;
		return (
				canPerformFunction(FunctionType.COMP_CREATE_CMDFunc)
				|| canPerformFunction(FunctionType.COMP_EDIT_CMDFunc)
				|| canPerformFunction(FunctionType.COMP_DELETE_CMDFunc)				
				);
	}
	
	public boolean canMaintainPOMA(){
		if(getUserRoleName().equalsIgnoreCase(RoleType.ADMIN.toString())) return true;
		return (
				canPerformFunction(FunctionType.POMA_PURCHASE_CMDFunc)
				|| canPerformFunction(FunctionType.POMA_GRN_CMDFunc)
				|| canPerformFunction(FunctionType.POMA_STOCARD_CMDFunc)
				|| canPerformFunction(FunctionType.POMA_CREATE_CMDFunc)
				|| canPerformFunction(FunctionType.POMA_EDIT_CMDFunc)
				|| canPerformFunction(FunctionType.POMA_DELETE_CMDFunc)
				|| canPerformFunction(FunctionType.POMA_IMPORT_CMDFunc)
				|| canPerformFunction(FunctionType.POMA_EXPORT_CMDFunc)
				|| canPerformFunction(FunctionType.POMA_REFRESH_CMDFunc)
				|| canPerformFunction(FunctionType.POMA_SEARCH_CMDFunc)				
				);
	}
	
	public boolean canMaintainPOOR(){
		if(getUserRoleName().equalsIgnoreCase(RoleType.ADMIN.toString())) return true;
		return (
				canPerformFunction(FunctionType.POOR_CREATETSA_CMDFunc)
				|| canPerformFunction(FunctionType.POOR_EDITTSA_CMDFunc)
				|| canPerformFunction(FunctionType.POOR_DELETETSA_CMDFunc)
				|| canPerformFunction(FunctionType.POOR_REFRESHTSA_CMDFunc)
				|| canPerformFunction(FunctionType.POOR_IMPORTTSA_CMDFunc)
				|| canPerformFunction(FunctionType.POOR_EXPORTTSA_CMDFunc)
				|| canPerformFunction(FunctionType.POOR_SEARCHTSA_CMDFunc)
				|| canPerformFunction(FunctionType.POOR_CREATESTO_CMDFunc)
				|| canPerformFunction(FunctionType.POOR_EDITSTO_CMDFunc)
				|| canPerformFunction(FunctionType.POOR_DELETESTO_CMDFunc)
				|| canPerformFunction(FunctionType.POOR_REFRESHSTO_CMDFunc)
				|| canPerformFunction(FunctionType.POOR_IMPORTSTO_CMDFunc)
				|| canPerformFunction(FunctionType.POOR_EXPORTSTO_CMDFunc)
				|| canPerformFunction(FunctionType.POOR_SEARCHSTO_CMDFunc)
				|| canPerformFunction(FunctionType.POOR_SELECTPROD_CMDFunc)
				|| canPerformFunction(FunctionType.POOR_CREATEPROD_CMDFunc)
				|| canPerformFunction(FunctionType.POOR_EDITPROD_CMDFunc)
				|| canPerformFunction(FunctionType.POOR_DELETEPROD_CMDFunc)
				|| canPerformFunction(FunctionType.POOR_REFRESHPROD_CMDFunc)
				|| canPerformFunction(FunctionType.POOR_IMPORTPROD_CMDFunc)
				|| canPerformFunction(FunctionType.POOR_EXPORTPROD_CMDFunc)
				|| canPerformFunction(FunctionType.POOR_SEARCHPROD_CMDFunc)
				|| canPerformFunction(FunctionType.POOR_NEW_CMDFunc)
				|| canPerformFunction(FunctionType.POOR_SAVE_CMDFunc)
				|| canPerformFunction(FunctionType.POOR_CANCEL_CMDFunc)				
				);
	}
	public boolean canMaintainPOSC(){
		if(getUserRoleName().equalsIgnoreCase(RoleType.ADMIN.toString())) return true;
		return (
				canPerformFunction(FunctionType.POSC_CREATECARD_CMDFunc)
				|| canPerformFunction(FunctionType.POSC_EDITCARD_CMDFunc)
				|| canPerformFunction(FunctionType.POSC_DELETECARD_CMDFunc)
				|| canPerformFunction(FunctionType.POSC_REFRESHCARD_CMDFunc)
				|| canPerformFunction(FunctionType.POSC_IMPORTCARD_CMDFunc)
				|| canPerformFunction(FunctionType.POSC_EXPORTCARD_CMDFunc)
				|| canPerformFunction(FunctionType.POSC_SEARCHCARD_CMDFunc)
				|| canPerformFunction(FunctionType.POSC_SELECTPROD_CMDFunc)
				|| canPerformFunction(FunctionType.POSC_CREATEPROD_CMDFunc)
				|| canPerformFunction(FunctionType.POSC_EDITPROD_CMDFunc)
				|| canPerformFunction(FunctionType.POSC_DELETEPROD_CMDFunc)
				|| canPerformFunction(FunctionType.POSC_REFRESHPROD_CMDFunc)
				|| canPerformFunction(FunctionType.POSC_IMPORTPROD_CMDFunc)
				|| canPerformFunction(FunctionType.POSC_EXPORTPROD_CMDFunc)
				|| canPerformFunction(FunctionType.POSC_SEARCHPROD_CMDFunc)
				|| canPerformFunction(FunctionType.POSC_CREATESTO_CMDFunc)
				|| canPerformFunction(FunctionType.POSC_EDITSTO_CMDFunc)
				|| canPerformFunction(FunctionType.POSC_DELETESTO_CMDFunc)
				|| canPerformFunction(FunctionType.POSC_REFRESHSTO_CMDFunc)
				|| canPerformFunction(FunctionType.POSC_IMPORTSTO_CMDFunc)
				|| canPerformFunction(FunctionType.POSC_EXPORTSTO_CMDFunc)
				|| canPerformFunction(FunctionType.POSC_SEARCHSTO_CMDFunc)
				|| canPerformFunction(FunctionType.POSC_NEW_CMDFunc)
				|| canPerformFunction(FunctionType.POSC_SAVE_CMDFunc)
				|| canPerformFunction(FunctionType.POSC_CANCEL_CMDFunc)
				);
	}
	public boolean canMaintainPOGRN(){
		if(getUserRoleName().equalsIgnoreCase(RoleType.ADMIN.toString())) return true;
		return (
				canPerformFunction(FunctionType.POGRN_CREATESTO_CMDFunc)
				|| canPerformFunction(FunctionType.POGRN_EDITSTO_CMDFunc)
				|| canPerformFunction(FunctionType.POGRN_DELETESTO_CMDFunc)
				|| canPerformFunction(FunctionType.POGRN_REFRESHSTO_CMDFunc)
				|| canPerformFunction(FunctionType.POGRN_IMPORTSTO_CMDFunc)
				|| canPerformFunction(FunctionType.POGRN_EXPORTSTO_CMDFunc)
				|| canPerformFunction(FunctionType.POGRN_SEARCHSTO_CMDFunc)
				|| canPerformFunction(FunctionType.POGRN_CREATEIMP_CMDFunc)
				|| canPerformFunction(FunctionType.POGRN_EDITIMP_CMDFunc)
				|| canPerformFunction(FunctionType.POGRN_DELETEIMP_CMDFunc)
				|| canPerformFunction(FunctionType.POGRN_REFRESHIMP_CMDFunc)
				|| canPerformFunction(FunctionType.POGRN_IMPORTIMP_CMDFunc)
				|| canPerformFunction(FunctionType.POGRN_EXPORTIMP_CMDFunc)
				|| canPerformFunction(FunctionType.POGRN_SEARCHIMP_CMDFunc)
				|| canPerformFunction(FunctionType.POGRN_SAVE_CMDFunc)
				|| canPerformFunction(FunctionType.POGRN_CANCEL_CMDFunc)
				);
	}
	public boolean canMaintainSALEMA(){
		if(getUserRoleName().equalsIgnoreCase(RoleType.ADMIN.toString())) return true;
		return (
				canPerformFunction(FunctionType.SALEMA_CREATE_CMDFunc)
				|| canPerformFunction(FunctionType.SALEMA_EDIT_CMDFunc)
				|| canPerformFunction(FunctionType.SALEMA_DELETE_CMDFunc)
				|| canPerformFunction(FunctionType.SALEMA_IMPORT_CMDFunc)
				|| canPerformFunction(FunctionType.SALEMA_EXPORT_CMDFunc)
				|| canPerformFunction(FunctionType.SALEMA_REFRESH_CMDFunc)
				|| canPerformFunction(FunctionType.SALEMA_SEARCH_CMDFunc)
				|| canPerformFunction(FunctionType.SALEMA_CREATEPRI_CMDFunc)
				|| canPerformFunction(FunctionType.SALEMA_ORDER_CMDFunc)
				|| canPerformFunction(FunctionType.SALEMA_CREATEFORM_CMDFunc)
				|| canPerformFunction(FunctionType.SALEMA_LIAB_CMDFunc)
				|| canPerformFunction(FunctionType.SALEMA_EXPORTSTO_CMDFunc)
				);
	}
	public boolean canMaintainSALESO(){
		if(getUserRoleName().equalsIgnoreCase(RoleType.ADMIN.toString())) return true;
		return (
				canPerformFunction(FunctionType.SALESO_CREATEST_CMDFunc)
				|| canPerformFunction(FunctionType.SALESO_EDIT_CMDFunc)
				|| canPerformFunction(FunctionType.SALESO_DELETE_CMDFunc)
				|| canPerformFunction(FunctionType.SALESO_IMPORT_CMDFunc)
				|| canPerformFunction(FunctionType.SALESO_EXPORTST_CMDFunc)
				|| canPerformFunction(FunctionType.SALESO_REFRESHST_CMDFunc)
				|| canPerformFunction(FunctionType.SALESO_SEARCH_CMDFunc)
				|| canPerformFunction(FunctionType.SALESO_CREATE_CMDFunc)
				|| canPerformFunction(FunctionType.SALESO_WRITE_CMDFunc)
				|| canPerformFunction(FunctionType.SALESO_GOTO_CMDFunc)
				|| canPerformFunction(FunctionType.SALESO_PRINTBILL_CMDFunc)
				|| canPerformFunction(FunctionType.SALESO_REFRESH_CMDFunc)
				|| canPerformFunction(FunctionType.SALESO_REFRESHALL_CMDFunc)
				|| canPerformFunction(FunctionType.SALESO_EXPORT_CMDFunc)
				|| canPerformFunction(FunctionType.SALESO_EXPORTALL_CMDFunc)
				|| canPerformFunction(FunctionType.SALESO_CREATEDS_CMDFunc)
				|| canPerformFunction(FunctionType.SALESO_EDITDS_CMDFunc)
				|| canPerformFunction(FunctionType.SALESO_DELETEDS_CMDFunc)
				|| canPerformFunction(FunctionType.SALESO_IMPORTDS_CMDFunc)
				|| canPerformFunction(FunctionType.SALESO_EXPORTDS_CMDFunc)
				|| canPerformFunction(FunctionType.SALESO_REFRESHDS_CMDFunc)
				|| canPerformFunction(FunctionType.SALESO_SEARCHDS_CMDFunc)
				|| canPerformFunction(FunctionType.SALESO_ADDDS_CMDFunc)
				|| canPerformFunction(FunctionType.SALESO_INVENTORYDS_CMDFunc)
				|| canPerformFunction(FunctionType.SALESO_SELECTPROD_CMDFunc)
				|| canPerformFunction(FunctionType.SALESO_CREATEPROD_CMDFunc)
				|| canPerformFunction(FunctionType.SALESO_EDITPROD_CMDFunc)
				|| canPerformFunction(FunctionType.SALESO_DELETEPROD_CMDFunc)
				|| canPerformFunction(FunctionType.SALESO_IMPORTPROD_CMDFunc)
				|| canPerformFunction(FunctionType.SALESO_EXPORTPROD_CMDFunc)
				|| canPerformFunction(FunctionType.SALESO_REFRESHPROD_CMDFunc)
				|| canPerformFunction(FunctionType.SALESO_SEARCHPROD_CMDFunc)
				|| canPerformFunction(FunctionType.SALESO_CREATEDIS_CMDFunc)
				|| canPerformFunction(FunctionType.SALESO_EDITDIS_CMDFunc)
				|| canPerformFunction(FunctionType.SALESO_DELETEDIS_CMDFunc)
				|| canPerformFunction(FunctionType.SALESO_IMPORTDIS_CMDFunc)
				|| canPerformFunction(FunctionType.SALESO_EXPORTDIS_CMDFunc)
				|| canPerformFunction(FunctionType.SALESO_REFRESHDIS_CMDFunc)
				|| canPerformFunction(FunctionType.SALESO_SEARCHDIS_CMDFunc)
				|| canPerformFunction(FunctionType.SALESO_ALLOCATEDIS_CMDFunc)
				|| canPerformFunction(FunctionType.SALESO_CREATEAC_CMDFunc)
				|| canPerformFunction(FunctionType.SALESO_EDITAC_CMDFunc)
				|| canPerformFunction(FunctionType.SALESO_DELETEAC_CMDFunc)
				|| canPerformFunction(FunctionType.SALESO_IMPORTAC_CMDFunc)
				|| canPerformFunction(FunctionType.SALESO_EXPORTAC_CMDFunc)
				|| canPerformFunction(FunctionType.SALESO_REFRESHAC_CMDFunc)
				|| canPerformFunction(FunctionType.SALESO_SEARCHAC_CMDFunc)
				);
	}
	public boolean canMaintainSALEIS(){
		if(getUserRoleName().equalsIgnoreCase(RoleType.ADMIN.toString())) return true;
		return (
				canPerformFunction(FunctionType.SALEIS_CREATE_CMDFunc)
				|| canPerformFunction(FunctionType.SALEIS_EDIT_CMDFunc)
				|| canPerformFunction(FunctionType.SALEIS_DELETE_CMDFunc)
				|| canPerformFunction(FunctionType.SALEIS_IMPORT_CMDFunc)
				|| canPerformFunction(FunctionType.SALEIS_EXPORT_CMDFunc)
				|| canPerformFunction(FunctionType.SALEIS_REFRESH_CMDFunc)
				|| canPerformFunction(FunctionType.SALEIS_SEARCH_CMDFunc)
				|| canPerformFunction(FunctionType.SALEIS_CREATEDET_CMDFunc)
				|| canPerformFunction(FunctionType.SALEIS_EDITDET_CMDFunc)
				|| canPerformFunction(FunctionType.SALEIS_DELETEDET_CMDFunc)
				|| canPerformFunction(FunctionType.SALEIS_IMPORTDET_CMDFunc)
				|| canPerformFunction(FunctionType.SALEIS_EXPORTDET_CMDFunc)
				|| canPerformFunction(FunctionType.SALEIS_REFRESHDET_CMDFunc)
				|| canPerformFunction(FunctionType.SALEIS_SEARCHDET_CMDFunc)
				|| canPerformFunction(FunctionType.SALEIS_CREATEINV_CMDFunc)
				|| canPerformFunction(FunctionType.SALEIS_EDITINV_CMDFunc)
				|| canPerformFunction(FunctionType.SALEIS_DELETEINV_CMDFunc)
				|| canPerformFunction(FunctionType.SALEIS_IMPORTINV_CMDFunc)
				|| canPerformFunction(FunctionType.SALEIS_EXPORTINV_CMDFunc)
				|| canPerformFunction(FunctionType.SALEIS_REFRESHINV_CMDFunc)
				|| canPerformFunction(FunctionType.SALEIS_SEARCHINV_CMDFunc)
				|| canPerformFunction(FunctionType.SALEIS_CREATELOG_CMDFunc)
				|| canPerformFunction(FunctionType.SALEIS_EDITLOG_CMDFunc)
				|| canPerformFunction(FunctionType.SALEIS_DELETELOG_CMDFunc)
				|| canPerformFunction(FunctionType.SALEIS_IMPORTLLOG_CMDFunc)
				|| canPerformFunction(FunctionType.SALEIS_EXPORTLOG_CMDFunc)
				|| canPerformFunction(FunctionType.SALEIS_REFRESHLOG_CMDFunc)
				|| canPerformFunction(FunctionType.SALEIS_SEARCHLOG_CMDFunc)
				|| canPerformFunction(FunctionType.SALEIS_WRITE_CMDFunc)
				|| canPerformFunction(FunctionType.SALEIS_CLOSE_CMDFunc)
				|| canPerformFunction(FunctionType.SALEIS_PRINT_CMDFunc)
				);
	}
	public boolean canMaintainSALEST(){
		if(getUserRoleName().equalsIgnoreCase(RoleType.ADMIN.toString())) return true;
		return (
				canPerformFunction(FunctionType.SALEST_CREATE_CMDFunc)
				|| canPerformFunction(FunctionType.SALEST_EDIT_CMDFunc)
				|| canPerformFunction(FunctionType.SALEST_DELETE_CMDFunc)
				|| canPerformFunction(FunctionType.SALEST_IMPORT_CMDFunc)
				|| canPerformFunction(FunctionType.SALEST_EXPORT_CMDFunc)
				|| canPerformFunction(FunctionType.SALEST_SEARCH_CMDFunc)
				);
	}
	public boolean canMaintainARAPMA(){
		if(getUserRoleName().equalsIgnoreCase(RoleType.ADMIN.toString())) return true;
		return (
				canPerformFunction(FunctionType.ARAPMA_SELECTCUST_CMDFunc)
				|| canPerformFunction(FunctionType.ARAPMA_CREATECUST_CMDFunc)
				|| canPerformFunction(FunctionType.ARAPMA_EDITCUST_CMDFunc)
				|| canPerformFunction(FunctionType.ARAPMA_DELETECUST_CMDFunc)
				|| canPerformFunction(FunctionType.ARAPMA_IMPORTCUST_CMDFunc)
				|| canPerformFunction(FunctionType.ARAPMA_EXPORTCUST_CMDFunc)
				|| canPerformFunction(FunctionType.ARAPMA_REFRESHCUST_CMDFunc)
				|| canPerformFunction(FunctionType.ARAPMA_SEARCHCUST_CMDFunc)
				|| canPerformFunction(FunctionType.ARAPMA_CREATEVOU_CMDFunc)
				|| canPerformFunction(FunctionType.ARAPMA_EDITVOU_CMDFunc)
				|| canPerformFunction(FunctionType.ARAPMA_DELETEVOU_CMDFunc)
				|| canPerformFunction(FunctionType.ARAPMA_IMPORTVOU_CMDFunc)
				|| canPerformFunction(FunctionType.ARAPMA_EXPORTVOU_CMDFunc)
				|| canPerformFunction(FunctionType.ARAPMA_REFRESHVOU_CMDFunc)
				|| canPerformFunction(FunctionType.ARAPMA_SEARCHVOU_CMDFunc)
				|| canPerformFunction(FunctionType.ARAPMA_WRITE_CMDFunc)
				|| canPerformFunction(FunctionType.ARAPMA_PRINT_CMDFunc)
				);
	}
	public boolean canMaintainARAPST(){
		if(getUserRoleName().equalsIgnoreCase(RoleType.ADMIN.toString())) return true;
		return (
				canPerformFunction(FunctionType.ARAPST_SELECTCUST_CMDFunc)
				|| canPerformFunction(FunctionType.ARAPST_CREATECUST_CMDFunc)
				|| canPerformFunction(FunctionType.ARAPST_EDITCUST_CMDFunc)
				|| canPerformFunction(FunctionType.ARAPST_DELETECUST_CMDFunc)
				|| canPerformFunction(FunctionType.ARAPST_IMPORTCUST_CMDFunc)
				|| canPerformFunction(FunctionType.ARAPST_EXPORTCUST_CMDFunc)
				|| canPerformFunction(FunctionType.ARAPST_REFRESHCUST_CMDFunc)
				|| canPerformFunction(FunctionType.ARAPST_SEARCHCUST_CMDFunc)
				|| canPerformFunction(FunctionType.ARAPST_CREATEVOU_CMDFunc)
				|| canPerformFunction(FunctionType.ARAPST_EDITVOU_CMDFunc)
				|| canPerformFunction(FunctionType.ARAPST_DELETEVOU_CMDFunc)
				|| canPerformFunction(FunctionType.ARAPST_IMPORTVOU_CMDFunc)
				|| canPerformFunction(FunctionType.ARAPST_EXPORTVOU_CMDFunc)
				|| canPerformFunction(FunctionType.ARAPST_REFRESHVOU_CMDFunc)
				|| canPerformFunction(FunctionType.ARAPST_SEARCHVOU_CMDFunc)
				);
	}
	public boolean canMaintainRPT(){
		if(getUserRoleName().equalsIgnoreCase(RoleType.ADMIN.toString())) return true;
		return (
				canPerformFunction(FunctionType.RPT_EXECUTE_CMDFunc)
				|| canPerformFunction(FunctionType.RPT_REFRESH_CMDFunc)
				|| canPerformFunction(FunctionType.RPT_REFRESHALL_CMDFunc)
				|| canPerformFunction(FunctionType.RPT_EXPORT_CMDFunc)
				|| canPerformFunction(FunctionType.RPT_EXPORTALL_CMDFunc)
				);
	}
	public String getUserId(){
		return em.getLogin_id();
	}
	public String getUserName(){
		return em.getName();
	}
	/**
	 * Get first role of this user
	 * @return
	 */
	public String getUserRoleName(){
		String ret = null;
		ret = role.get(0).getRoleName();
		return ret;
	}
	public String getLoginInfor(){
		String ret = getUserId() + ":" + getUserName();
		try {
			ret = ret + "(" + getUserRoleName() + ")";
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return ret;
	}
	public boolean isAdminRole(){
		boolean ret = false;
		try {
			ret = getUserRoleName().equalsIgnoreCase(
					RoleType.ADMIN.toString());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return ret;
	}
	public boolean isSaleRole(){
		boolean ret = false;
		try {
			ret = getUserRoleName().equalsIgnoreCase(
					RoleType.SALE.toString());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return ret;
	}
	public boolean isPurchaseRole(){
		boolean ret = false;
		try {
			ret = getUserRoleName().equalsIgnoreCase(
					RoleType.PURCHASE.toString());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return ret;
	}
	public boolean isAccountantRole(){
		boolean ret = false;
		try {
			ret = getUserRoleName().equalsIgnoreCase(
					RoleType.ACCOUNTANT.toString());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return ret;
	}
	public boolean isMarketingRole(){
		boolean ret = false;
		try {
			ret = getUserRoleName().equalsIgnoreCase(
					RoleType.ACCOUNTANT.toString());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return ret;
	}
	public boolean isReportRole(){
		boolean ret = false;
		try {
			ret = getUserRoleName().equalsIgnoreCase(
					RoleType.ACCOUNTANT.toString());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return ret;
	}

	public void setEm(Employee em) {
		this.em = em;
	}

	public List<UserRole> getRole() {
		return role;
	}

	public void setRole(List<UserRole> role) {
		this.role = role;
	}

	public Hashtable<UserRole, List<Function>> getRoleFunctions() {
		return roleFunctions;
	}

	public void setRoleFunctions(Hashtable<UserRole, List<Function>> roleFunctions) {
		this.roleFunctions = roleFunctions;
	}
	
}
