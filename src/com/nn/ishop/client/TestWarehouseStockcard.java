package com.nn.ishop.client;

import com.nn.ishop.client.logic.admin.WarehouseStockcardLogic;

public class TestWarehouseStockcard {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		WarehouseStockcardLogic instance = WarehouseStockcardLogic.getInstance();
		System.out.println("--warehousestockcard detail:\n" + instance.getWarehouseStockcardDetail(1));
	}

}
