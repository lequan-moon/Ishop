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
import com.nn.ishop.server.dto.warehouse.Lot;
import com.nn.ishop.server.dto.warehouse.Warehouse;

public class WarehouseLogic extends CommonLogic {
	//===================== WAREHOUSE ===========================//
	@SuppressWarnings("unchecked")
	public static Warehouse insertWarehouse(Warehouse ent) throws Exception{
		//setExtraEntityInfor(ent, CAction.ADD);
		RPCServiceClient serviceClient = new RPCServiceClient();
		Options options = serviceClient.getOptions();
		options.setTo(SystemConfiguration.WAREHOUSE_SERVICE_TARGET_EPR);
		QName qname = new QName(SystemConfiguration.NAMESPACE_URI,
				SystemConfiguration.LOCALPART_INSERT_WAREHOUSE);
		Object[] params = new Object[] { ent};
		Class[] types = new Class[] { Warehouse.class };
		Object[] res = serviceClient.invokeBlocking(qname, params, types);
		return (Warehouse) res[0];		
	}
	
	@SuppressWarnings("unchecked")
	public static Warehouse updateWarehouse(Warehouse ent) throws Exception{
//		setExtraEntityInfor(ent, CAction.UPDATE);
		RPCServiceClient serviceClient = new RPCServiceClient();
		Options options = serviceClient.getOptions();
		options.setTo(SystemConfiguration.WAREHOUSE_SERVICE_TARGET_EPR);
		QName qname = new QName(SystemConfiguration.NAMESPACE_URI,
				SystemConfiguration.LOCALPART_UPDATE_WAREHOUSE);
		Object[] params = new Object[] { ent };
		Class[] types = new Class[] { Warehouse.class };
		Object[] res = serviceClient.invokeBlocking(qname, params, types);
		return (Warehouse) res[0];		
	}
	
	public static List<Warehouse> loadWarehouse() throws Exception{		
		BusinessUtil<Warehouse> bs = new BusinessUtil<Warehouse>();
		List<Warehouse> whList = bs.webserviceGetDtoList(1,
				SystemConfiguration.LOCALPART_LOAD_WAREHOUSE,
				new Class[] { Warehouse[].class }, 
				new Object[] { new Integer(1) },
				SystemConfiguration.WAREHOUSE_SERVICE_TARGET_EPR);
		return whList;	
	}
	public static List<Lot> loadLotByWarehouse(String whId) throws Exception{		
		BusinessUtil<Lot> bs = new BusinessUtil<Lot>();
		List<Lot> whList = bs.webserviceGetDtoList(1,
				SystemConfiguration.LOCALPART_LOAD_LOT_BY_WAREHOUSE,
				new Class[] { Lot[].class }, 
				new Object[] { whId },
				SystemConfiguration.WAREHOUSE_SERVICE_TARGET_EPR);
		return whList;	
	}
	public static Vector<Warehouse> loadWarehouseVector() throws Exception{
		List<Warehouse> whList = loadWarehouse();
		Vector<Warehouse> vctWarehouse = new Vector<Warehouse>();
		for(Warehouse c:whList)
			vctWarehouse.add(c);
		return vctWarehouse;	
	}
	
	public static Warehouse getWarehouse(String whId) throws Exception{
		BusinessUtil<Warehouse> bs = new BusinessUtil<Warehouse>();
		Warehouse w = bs.webserviceGetDto(1,
				SystemConfiguration.LOCALPART_GET_WAREHOUSE,
				new Class[] { Warehouse.class }, 
				new Object[] {whId},
				SystemConfiguration.WAREHOUSE_SERVICE_TARGET_EPR);
		return w;
	}
	
	public static void main(String[] args) throws Exception{
		
//		Warehouse h = new Warehouse("testwh1", "Warehouse Test======= 1 ", 1, "ABC XYZ", 1, 1, 1);
//		Lot l1 = new Lot("Lot1", "testwh1", "Test --- Lot 1");
//		Lot l2 = new Lot("Lot2", "testwh1", "Test --- Lot 2");
//		Lot[] lots = new Lot[]{l1, l2};
//		h.setLots(lots);
//		//WarehouseLogic.insertWarehouse(h);
//		//WarehouseLogic.updateWarehouse(h);
//		long from = System.currentTimeMillis();
		List<Warehouse> w = WarehouseLogic.loadWarehouse();
		System.out.println(w);
//		long to = System.currentTimeMillis();
//		System.out.println((to -from));
		
	}
}
