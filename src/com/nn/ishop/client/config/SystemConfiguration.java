package com.nn.ishop.client.config;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.axis2.addressing.EndpointReference;

/**
 * This class load constants for the whole system in development phase. 
 * In production we change to resource configuration
 * @author NguyenNghia
 *
 */
public class SystemConfiguration {

	public static final DecimalFormat decfm = new DecimalFormat("#,###,##0.00");	
	public static Color DEFAULT_REQUIRED_TEXT = new Color(251,229,229);
	public static Color DEFAULT_DISABLED_TEXT = new Color(0xEC,0xE9,0xD8);
	public static Color DEFAULT_ERROR_TEXT = new Color(255,140,150);
	public static Color DEFAULT_TEXT = new Color(255,255,255);
	/** Keep the system variables */
	private static Map<Object,Object> sysContext = null;
	
	public static Map<Object,Object> getSysContext(){
		if (sysContext == null)
			sysContext = new HashMap<Object, Object>();
		return sysContext;
	}
	public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	public static SimpleDateFormat shortDateFm = new SimpleDateFormat("dd/MM/yyyy");
	/**
	 * Axis2 Version Service
	 */
	public static final String LOCALPART_ECHO = "echoService";
	
	/**
	 * From 2013.05.22 we use standard method naming such as insert, get,
	 *  update, load, search for all interface
	 */
	public static final String LOCALPART_INSERT = "insert";
	public static final String LOCALPART_UPDATE = "update";
	public static final String LOCALPART_GET = "get";
	public static final String LOCALPART_LOAD = "load";
	public static final String LOCALPART_SEARCH = "search";
	
	public static final EndpointReference WS_VERSION_SERVICE_TARGET_EPR 
	//= new EndpointReference("http://192.168.1.3:8080/ishop_server_v1.1/services/Version");
	= new EndpointReference("http://127.0.0.1:8080/ishop_server_v1.1/services/Version");
	public static final String WS_VERSION_NAMESPACE_URI ="http://axisversion.sample";		
	public static final String LOCALPART_WS_VERSION_GET_VERSION = "getVersion";
	/**
	 * User Service configuration
	 */	
	public static final String NAMESPACE_URI ="http://webservice.server.ishop.nn.com";
	public static final EndpointReference EMPLOYEE_SERVICE_TARGET_EPR 
		= new EndpointReference("http://127.0.0.1:8080/ishop_server_v1.1/services/EmployeeService");
	
//	public static final EndpointReference EMPLOYEE_SERVICE_TARGET_EPR 
//		= new EndpointReference("http://192.168.1.3:8080/ishop_server_v1.1/services/EmployeeService");
	
	// Localpart for Employee
	public static final String LOCALPART_EMPLOYEE_GET_USER_ROLE = "getEmployeeRolesByEmployeeId";
	public static final String LOCALPART_EMPLOYEE_GET_ROLE_FUNTION = "getRoleFunctionByRoleId";
	public static final String LOCALPART_EMPLOYEE_GET_USER = "getUser";
	public static final String LOCALPART_EMPLOYEE_GET_USER_LIST = "getUserList";
	public static final String LOCALPART_EMPLOYEE_GET_USER_LIST_FOR_COMPANY = "getUserListForCompany";
	public static final String LOCALPART_EMPLOYEE_SET_USER_BY_FULL_PARAM = "setUser";	
	public static final String LOCALPART_EMPLOYEE_GET_USER_BY_LOGIN_ID = "getUserByLoginId";
	public static final String LOCALPART_EMPLOYEE_GET_ROLE_LIST = "getRoleList";
	public static final String LOCALPART_EMPLOYEE_INSERT_EMPLOYEE = "insertEmployee";
	public static final String LOCALPART_EMPLOYEE_UPDATE_EMPLOYEE = "updateEmployee";
	public static final String LOCALPART_EMPLOYEE_UPDATE_EROLE = "updateEmployeeRole";	
	
	public static final String LOCALPART_EMPLOYEE_INSERT_ROLE = "insertRole";
	public static final String LOCALPART_EMPLOYEE_UPDATE_FROLE = "updateRole";
	
	
	
	public static final String LOCALPART_EMPLOYEE_LOAD_FUNCTION = "loadFunction";
		
	// Local part for Product
//	public static final EndpointReference PD_EPR 
//	= new EndpointReference("http://192.168.1.3:8080/ishop_server_v1.1/services/ProductService");
	public static final EndpointReference PD_EPR 
		= new EndpointReference("http://127.0.0.1:8080/ishop_server_v1.1/services/ProductService");
	
	public static final String LP_PD_INSERT_CAT = "insertProductCategory";
	public static final String LP_PD_UPDATE_CAT = "updateProductCategory";	
	
	public static final String LP_PD_SAVE_CAT = "createProductCategoryGroup";
	public static final String LP_PD_SAVE_ITEM = "updateProductItem";
	public static final String LP_PD_INSERT_SECTION = "insertProductSection";
	public static final String LP_PD_UPDATE_SECTION = "updateProductSection";	
	public static final String LP_PD_SAVE_SECTION = "updateProductSection";	
	
	public static final String LP_PD_INSERT_CAT_GROUP = "insertProductCategoryGroup";	
	public static final String LP_PD_UPDATE_CAT_GROUP = "updateProductCategoryGroup";
	public static final String LP_PD_SAVE_CAT_GROUP = "updateProductCategoryGroup";	
	
	public static final String LP_PD_INSERT_ITEM = "insertProductItem";
	public static final String LP_PD_UPDATE_ITEM = "updateProductItem";
	
	public static final String LP_PD_SEARCH_ITEM = "searchProductItem";
	
	public static final String LP_PD_SAVE_UOM = "updateProductUOM";
	public static final String LP_PD_SAVE_BOM = "updateProductBOM";	
	public static final String LP_PD_SAVE_PACKING_TYPE = "updatePackingType";
	
	//-- GET/LOAD services
	public static final String LP_PD_GET_UOM = "getUOM";//"getProductUOM";
	public static final String LP_PD_GET_SECTION = "getProductSection";
	public static final String LP_PD_GET_PACKING_TYPE = "getProductPackingType";
	public static final String LP_PD_GET_ITEM = "getProductItem";
	public static final String LP_PD_GET_CAT = "getCategory";

	public static final String LP_PD_GET_BOM = "getProductBOM";
	
	//-- TODO change to load
	
	public static final String LP_PD_LOAD_SECTION = "loadSection";
	
	public static final String LP_PD_LOAD_CAT_GROUP = "loadCategoryGroup";
	public static final String LP_PD_LOAD_CAT_GROUP_BY_SECTION = "loadCategoryGroupBySection";
	
	public static final String LP_PD_LOAD_CAT = "loadCategory";
	public static final String LP_PD_LOAD_CAT_BY_CAT_GROUP = "loadCategoryByCategoryGroup";
	public static final String LP_PD_LOAD_SUB_CAT = "loadSubCategory";
	public static final String LP_PD_LOAD_LEAF_CAT = "loadLeafCategory";
	
	public static final String LP_PD_LOAD_PRODUCT = "loadProduct";
	public static final String LP_PD_LOAD_PRODUCT_BY_CAT = "loadProductByCategory";	
	public static final String LP_PD_COUNT_PRODUCT_BY_CAT = "countProductByCategory";
	
	
	public static final String LP_PD_LOAD_UOM = "loadProductUOM";
	public static final String LP_PD_LOAD_PACKING_TYPE = "loadProductPackingType";	
	public static final String LP_PD_LOAD_BOM = "loadProductBOM";
	
	public static final EndpointReference CUSTOMER_SERVICE_TARGET_EPR 
		= new EndpointReference("http://127.0.0.1:8080/ishop_server_v1.1/services/CustomerService");
	
	public static final String LP_CUS_INSERTCUSTOMER ="insertCustomer";
	public static final String LP_CUS_UPDATECUSTOMER ="updateCustomer";
	public static final String LP_CUS_LOADCUSTOMER ="loadCustomer";
	public static final String LP_CUS_GETCUSTOMER ="getCustomer";
	
	public static final String LP_CUS_INSERTCUSTOMERPRICELIST ="insertCustomerPriceList";
	public static final String LP_CUS_UPDATECUSTOMERPRICELIST ="updateCustomerPriceList";	
	public static final String LP_CUS_LOADCUSTOMERPRICELIST ="loadCustomerPriceList";
	public static final String LP_CUS_GETCUSTOMERPRICELIST ="getCustomerPriceList";
	public static final String LP_CUS_INSERTPRICELIST ="insertPriceList";
	public static final String LP_CUS_UPDATEPRICELIST ="updatePriceList";
	public static final String LP_CUS_LOADPRICELIST ="loadPriceList";
	public static final String LP_GETPRICELIST ="getPriceList";
	
	public static final EndpointReference COMMON_SERVICE_TARGET_EPR 
	= new EndpointReference("http://127.0.0.1:8080/ishop_server_v1.1/services/CommonService");
	public static final String LP_COMMON_INSERT_BANK ="insertBank";
	public static final String LP_COMMON_UPDATE_BANK ="updateBank";
	public static final String LP_COMMON_LOAD_BANK ="loadBank";	
	public static final String LP_COMMON_GET_BANK ="getBank";

	public static final String LP_COMMON_INSERT_CURRENCY ="insertCurrency";
	public static final String LP_COMMON_UPDATE_CURRENCY ="updateCurrency";
	public static final String LP_COMMON_LOAD_CURRENCY ="loadCurrency";
	public static final String LP_COMMON_GET_CURRENCY ="getCurrency";
	
	public static final String LP_COMMON_INSERT_EXRATE ="insertExRate";
	public static final String LP_COMMON_UPDATE_EXRATE ="updateExRate";
	public static final String LP_COMMON_LOAD_EXRATE ="loadExRate";
	public static final String LP_COMMON_GET_EXRATE ="getExRate";
	
	public static final String LP_COMMON_INSERT_LOCATION ="insertLocation";
	public static final String LP_COMMON_UPDATE_LOCATION ="updateLocation";
	public static final String LP_COMMON_LOAD_COUNTRY ="loadCountry";
	public static final String LP_COMMON_LOAD_LOCATION_BY_PARENT_ID ="loadLocationByParentId";	
	public static final String LP_COMMON_GET_LOCATION ="getLocation";
	
	public static final String LP_COMMON_INSERT_PRICELIST ="insertPriceList";
	public static final String LP_COMMON_UPDATE_PRICELIST ="updatePriceList";
	public static final String LP_COMMON_LOAD_PRICELIST ="loadPriceList";
	public static final String LP_COMMON_GET_PRICELIST ="getPriceList";
	public static final String LP_COMMON_LOAD_PRICELIST_BY_CAT ="loadPriceListByPdCat";
	
	public static final String LP_COMMON_SEARCH_PRICELIST ="searchPriceList";
	public static final String LP_COMMON_LOAD_GRN_HISTORY ="loadGrnHistory";
	
	
	public static final EndpointReference COMPANY_SERVICE_TARGET_EPR = new EndpointReference(
			"http://127.0.0.1:8080/ishop_server_v1.1/services/CompanyService");

	public static final String LOCALPART_COMPANY_GET_LIST_COMPANY = "getListCompany";
	public static final String LOCALPART_COMPANY_UPDATE_COMPANY = "updateCompany";
	public static final String LOCALPART_COMPANY_INSERT_COMPANY = "insertCompany";
	public static final String LOCALPART_COMPANY_GET_LIST_CHILDREN_COMPANY = "getListChildrenCompany";
	public static final String LOCALPART_COMPANY_GET_COMPANY_BY_ID = "getCompanyById";
	
	public static final EndpointReference COMPANY_BUSINESS_FIELD_SERVICE_TARGET_EPR = new EndpointReference(
			"http://127.0.0.1:8080/ishop_server_v1.1/services/CompanyBusinessFieldService");
	public static final String LOCALPART_COMPANY_COUNT_USER = "countUser";
	
	public static final String LOCALPART_BUSINESS_FIELD_GET_LIST_BUSINESS_FIELD = "getListCompanyBussinessField";
	public static final String LOCALPART_BUSINESS_FIELD_GET_BUSINESS_FIELD = "getCompanyBusinesssFIeldById";
	public static final String LOCALPART_BUSINESS_FIELD_INSERT_BUSINESS_FIELD = "insertCompanyBussinessField";
	public static final String LOCALPART_BUSINESS_FIELD_UPDATE_BUSINESS_FIELD = "updateCompanyBussinessField";
	
	public static final EndpointReference WAREHOUSE_SERVICE_TARGET_EPR 
	= new EndpointReference("http://127.0.0.1:8080/ishop_server_v1.1/services/WarehouseService");
	
	public static final String LOCALPART_GET_WAREHOUSE = "getWarehouse";
	public static final String LOCALPART_UPDATE_WAREHOUSE = "updateWarehouse";
	public static final String LOCALPART_INSERT_WAREHOUSE = "insertWarehouse";
	public static final String LOCALPART_LOAD_WAREHOUSE = "loadWarehouse";
	
//	public static final String LOCALPART_GET_LOT = "getLot";
//	public static final String LOCALPART_UPDATE_LOT = "updateLot";
//	public static final String LOCALPART_INSERT_LOT = "insertLot";
//	public static final String LOCALPART_LOAD_LOT = "loadLot";
	public static final String LOCALPART_LOAD_LOT_BY_WAREHOUSE = "loadLotByWarehouse";
	
	public static final EndpointReference PURCHASE_SERVICE_TARGET_EPR = new EndpointReference(
			"http://127.0.0.1:8080/ishop_server_v1.1/services/PurchaseService");
	
	public static final String LOCALPART_GET_PROVIDER_LIST = "getListProvider";
	public static final String LOCALPART_GET_PURCHASING_PLAN = "getPurchasingPlan";
	public static final String LOCALPART_GET_PURCHASING_PLAN_LIST = "getListPurchasingPlan";
	public static final String LOCALPART_GET_PURCHASING_PLAN_TYPE_LIST = "getListPurchasingPlanType";
	public static final String LOCALPART_GET_PURCHASING_DETAIL_LIST = "getPurchasingDetail";
	public static final String LOCALPART_INSERT_PURCHASING_PLAN = "insertPurchasingPlan";
	public static final String LOCALPART_UPDATE_PURCHASING_PLAN = "updatePurchasingPlan";
	public static final String LOCALPART_INSERT_PURCHASING_PLAN_TYPE = "insertPurchasingPlanType";
	public static final String LOCALPART_UPDATE_PURCHASING_PLAN_TYPE = "updatePurchasingPlanType";
	public static final String LOCALPART_INSERT_PURCHASING_PLAN_DETAIL = "insertPurchasingPlanDetail";
	public static final String LOCALPART_UPDATE_PURCHASING_PLAN_DETAIL = "updatePurchasingPlanDetail";
	public static final String LOCALPART_INSERT_PURCHASING_PLAN_DETAIL_LIST = "insertPurchasingPlanDetailList";
	public static final String LOCALPART_UPDATE_PURCHASING_PLAN_DETAIL_LIST = "updatePurchasingPlanDetailList";
	public static final String LOCALPART_GET_PURCHASING_PLAN_BY_TRANX_ID = "getPurchasingPlanByTranxId";
	public static final EndpointReference WAREHOUSE_STOCKCARD_SERVICE_TARGET_EPR = new EndpointReference(
			"http://127.0.0.1:8080/ishop_server_v1.1/services/WarehouseStockcardService");
	
	public static final String LOCALPART_GET_WAREHOUSE_STOCKCARD = "getWarehouseStockcard";
	public static final String LOCALPART_GET_WAREHOUSE_STOCKCARD_DETAIL = "getWarehouseStockcardDetail";
	public static final String LOCALPART_GET_WAREHOUSE_STOCKCARD_LIST = "getWarehouseStockcardList";
	public static final String LOCALPART_INSERT_WAREHOUSE_STOCKCARD = "insertWarehouseStockcard";
	public static final String LOCALPART_INSERT_WAREHOUSE_STOCKCARD_DETAIL = "insertWarehouseStockcardDetail";
	public static final String LOCALPART_INSERT_WAREHOUSE_STOCKCARD_LIST = "insertWarehouseStockcardDetailList";
	public static final String LOCALPART_UPDATE_WAREHOUSE_STOCKCARD = "updateWarehouseStockcard";
	public static final String LOCALPART_UPDATE_WAREHOUSE_STOCKCARD_DETAIL = "updateWarehouseStockcardDetail";
	public static final String LOCALPART_UPDATE_WAREHOSUE_STOCKCARD_DETAIL_LIST = "updateWarehouseStockcardDetailList";
	
	public static final EndpointReference PURCHASE_GRN_SERVICE_TARGET_EPR = new EndpointReference(
			"http://127.0.0.1:8080/ishop_server_v1.1/services/PurchaseGrnService");
	
	public static final String LOCALPART_GET_PURCHASE_GRN = "getPurchaseGrn";
	public static final String LOCALPART_GET_PURCHASE_GRN_DETAIL = "getPurchaseGrnDetail";
	public static final String LOCALPART_GET_PURCHASE_GRN_LIST = "getPurchaseGrnList";
	public static final String LOCALPART_INSERT_PURCHASE_GRN = "insertPurchaseGrn";
	public static final String LOCALPART_INSERT_PURCHASE_GRN_DETAIL = "insertPurchaseGrnDetail";
	public static final String LOCALPART_INSERT_PURCHASE_GRN_DETAIL_LIST = "insertPurchaseGrnDetailList";
	public static final String LOCALPART_UPDATE_PURCHASE_GRN = "updatePurchaseGrn";
	public static final String LOCALPART_UPDATE_PURCHASE_GRN_DETAIL = "updatePurchaseGrnDetail";
	public static final String LOCALPART_UPDATE_PURCHASE_GRN_DETAIL_LIST = "updatePurchaseGrnDetailList";
	public static final String LOCALPART_GET_PURCHASE_GRN_BY_PPID = "getPurchaseGrnByPPId";
	public static final String LOCALPART_SEARCH_TRANSACTION_BY_STEP = "searchByStep";
	public static final String LOCALPART_SEARCH_TRANSACTION_BY_TX_TYPE = "searchByType";
	public static final String LOCALPART_GET_SO_BY_TX_ID = "getSOByTranxId";
	
	public static final EndpointReference DAILY_TRANX_SERVICE_TARGET_EPR 
	= new EndpointReference("http://127.0.0.1:8080/ishop_server_v1.1/services/DailyTranxService");
	
	public static final EndpointReference SALE_ORDER_SERVICE_TARGET_EPR 
	= new EndpointReference("http://127.0.0.1:8080/ishop_server_v1.1/services/SaleOrderService");
	public static final EndpointReference RECEIPT_SERVICE_TARGET_EPR 
	= new EndpointReference("http://127.0.0.1:8080/ishop_server_v1.1/services/ReceiptService");
	
	public static final EndpointReference AR_SERVICE_TARGET_EPR 
	= new EndpointReference("http://127.0.0.1:8080/ishop_server_v1.1/services/AccountReceivableService");
	
	
	public static final List<String> PAYMENT_METHOD = Arrays.asList("by.cash","by.bank.transfer");
}
