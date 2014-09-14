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
import com.nn.ishop.server.dto.grn.PurchaseGrn;
import com.nn.ishop.server.dto.grn.PurchaseGrnDetail;
import com.nn.ishop.server.dto.warehouse.Warehouse;
import com.nn.ishop.server.dto.warehouse.WarehouseStockcard;
import com.nn.ishop.server.dto.warehouse.WarehouseStockcardDetail;

public class WarehouseStockcardLogic extends CommonLogic{
	private static WarehouseStockcardLogic instance;
	public static final String loadScByItem = "loadScByItem";
	
	public List<WarehouseStockcardDetail> getWarehouseStockcardDetail(int lot_id){
		List<WarehouseStockcardDetail> ret = new ArrayList<WarehouseStockcardDetail>();
		BusinessUtil<WarehouseStockcardDetail> bu = new BusinessUtil<WarehouseStockcardDetail>();
		try {
			ret = bu.webserviceGetDtoList(new Integer(1), SystemConfiguration.LOCALPART_GET_WAREHOUSE_STOCKCARD_DETAIL, new Class[] { WarehouseStockcardDetail[].class },
					new Integer[] { lot_id }, SystemConfiguration.WAREHOUSE_STOCKCARD_SERVICE_TARGET_EPR);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	
	public static WarehouseStockcardLogic getInstance(){
		if(instance == null){
			instance = new WarehouseStockcardLogic();
		}
		return instance;
	}
	
	public static WarehouseStockcardDetail insertWarehouseStockcardDetail(WarehouseStockcardDetail whsc){
		RPCServiceClient serviceClient;
		WarehouseStockcardDetail ret = null;
		try {
			serviceClient = new RPCServiceClient();
			Options options = serviceClient.getOptions();
			options.setTo(SystemConfiguration.WAREHOUSE_STOCKCARD_SERVICE_TARGET_EPR);
			QName qname = new QName(SystemConfiguration.NAMESPACE_URI,
					SystemConfiguration.LOCALPART_INSERT_WAREHOUSE_STOCKCARD_DETAIL);
			setExtraEntityInfor(whsc, CAction.ADD);
			Object[] params = new Object[] { whsc };
			Class[] types = new Class[] { WarehouseStockcardDetail.class };
			Object[] response1 = serviceClient.invokeBlocking(qname, params, types);
			ret = (WarehouseStockcardDetail)response1[0];
		} catch (AxisFault ex) {
			ex.printStackTrace();
		}
		return ret;
	}
	
	public List<WarehouseStockcardDetail> loadSCByItem(String itemId){
		List<WarehouseStockcardDetail> ret = new ArrayList<WarehouseStockcardDetail>();
		BusinessUtil<WarehouseStockcardDetail> bu = new BusinessUtil<WarehouseStockcardDetail>();
		try {
			ret = bu.webserviceGetDtoList(new Integer(1)
			, loadScByItem
			, new Class[] { WarehouseStockcardDetail[].class }
			, new String[] { itemId }
			, SystemConfiguration.WAREHOUSE_STOCKCARD_SERVICE_TARGET_EPR);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	
}
