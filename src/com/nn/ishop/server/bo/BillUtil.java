package com.nn.ishop.server.bo;

import java.io.Serializable;
import java.util.ArrayList;

public class BillUtil implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4300276672448812414L;
	
	/**
	 * Load bill by Cat
	 */
	public static String getBillByCategory(String catId) throws Exception
	{
		StringBuffer a = new StringBuffer(128);
		a.append(" SELECT bill_id FROM ")
		.append(" ( ")
		.append(" SELECT t1.id AS bill_id, " )
		.append(" t2.id AS assoc_id, ")
		.append(" t2.order_id, ")
		.append(" t2.item_id, ")
		.append(" t2.item_quantity, ")
		.append(" t2.item_unit ")
		.append(" FROM " )
		.append(" sic_retailer.item_order AS t1, ")
		.append(" sic_retailer.item_order_association AS t2 ")
		.append(" WHERE t1.id = t2.order_id ")
		.append(" ) AS t3, ")
		.append(" sic_retailer.category AS t4, ")
		.append(" sic_retailer.item AS t5 ")
		.append(" WHERE ")
		.append(" (t4.id = ?) AND ")
		.append(" (t3.item_id = t5.id) AND ") 
		.append(" (t5.cat_id = t4.id)");
		return null;
	}
	/**
	 * Load bill by item
	 */
	public static String getBillByItem(String itemId) throws Exception
	{
		StringBuffer a = new StringBuffer(128);
		a.append(" SELECT bill_id FROM ")
		.append(" ( ")
		.append(" SELECT t1.id AS bill_id, " )
		.append(" t2.id AS assoc_id, ")
		.append(" t2.order_id, ")
		.append(" t2.item_id, ")
		.append(" t2.item_quantity, ")
		.append(" t2.item_unit ")
		.append(" FROM " )
		.append(" sic_retailer.item_order AS t1, ")
		.append(" sic_retailer.item_order_association AS t2 ")
		.append(" WHERE t1.id = t2.order_id ")
		.append(" ) AS t3, ")
		.append(" sic_retailer.item AS t5 ")
		.append(" WHERE ")
		.append(" (t3.item_id = t5.id) AND ") 
		.append(" (t5.id = ?) ");
		
		return null;
	}

}
