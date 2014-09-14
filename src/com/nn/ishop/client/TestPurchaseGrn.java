package com.nn.ishop.client;

import com.nn.ishop.client.logic.admin.PurchaseGrnLogic;

public class TestPurchaseGrn {
	public static void main(String[] args) {
		PurchaseGrnLogic instance = PurchaseGrnLogic.getInstance();
		System.out.println("----list PurchaseGrn:\n" + instance.getPurchaseGrnList());
		System.out.println("--Purchase grn:\n" + instance.getPurchaseGrn("1"));
		System.out.println("--Purchase grn detail:\n" + instance.getPurchaseGrnDetail("1"));
	}

}
