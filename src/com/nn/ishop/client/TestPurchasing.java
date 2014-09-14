package com.nn.ishop.client;

import java.util.List;

import com.nn.ishop.client.logic.admin.PurchaseLogic;
import com.nn.ishop.server.dto.purchase.PurchasingPlan;

public class TestPurchasing {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PurchaseLogic plogic = PurchaseLogic.getInstance();
		System.out.println("--list provider:\n" + plogic.getListProvider());
		System.out.println("--list purchasingPlanType:\n" + plogic.getListPurchasingPlanType());
		List<PurchasingPlan> lstpp = plogic.getListPurchasingPlan();
		System.out.println("--list purchasingPlan\n" + lstpp);
		System.out.println("--purchasing detail for " + lstpp.get(0).getPpId() + ":\n" + plogic.getListPurchasingPlanDetail(lstpp.get(0).getPpId()));
	}
}
