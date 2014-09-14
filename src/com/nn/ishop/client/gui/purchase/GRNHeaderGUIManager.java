package com.nn.ishop.client.gui.purchase;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Date;
import java.util.Vector;

import com.nn.ishop.client.CActionEvent;
import com.nn.ishop.client.CActionListener;
import com.nn.ishop.client.config.SystemConfiguration;
import com.nn.ishop.client.gui.AbstractGUIManager;
import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.components.CTextField;
import com.nn.ishop.client.logic.admin.CustomerLogic;
import com.nn.ishop.client.logic.admin.PurchaseGrnLogic;
import com.nn.ishop.client.logic.admin.PurchaseLogic;
import com.nn.ishop.client.logic.transaction.DailyTranxLogic;
import com.nn.ishop.client.validator.Validator;
import com.nn.ishop.server.dto.bstranx.DailyTransaction;
import com.nn.ishop.server.dto.customer.Customer;
import com.nn.ishop.server.dto.grn.PurchaseGrn;
import com.nn.ishop.server.dto.purchase.PurchasingPlan;
import com.nn.ishop.server.util.Formatter;
import com.toedter.calendar.JDateChooser;

public class GRNHeaderGUIManager extends AbstractGUIManager implements CActionListener, ItemListener{
	CTextField txtGRNId;
	CTextField txtPPId;
	CTextField txtPurchaseNote;
	JDateChooser dcReceiveDate;	
	CTextField txtNote;
	CTextField  txtProvider;
	boolean isActived = false;
	
	PurchaseGrnLogic logicPurchaseGrn = PurchaseGrnLogic.getInstance(); 
	PurchaseGrn beanPgrn;
	Vector<CActionListener> vctListener = new Vector<CActionListener>();
	
	PurchaseLogic logicPurchase = PurchaseLogic.getInstance();
	DailyTransaction beanCurrentTranx;
	DailyTranxLogic logicDailyTranxLogic = DailyTranxLogic.getInstance();
	
	public GRNHeaderGUIManager(String locale){
		setLocale(locale);
		init();
	}

	void init(){
		if (getLocale() != null && !getLocale().equals("")){
			initTemplate(this, "purchase/po/grnHeaderPage.xml", getLocale());
		}else{
			initTemplate(this, "purchase/po/grnHeaderPage.xml");		
		}
		render();
		prepareData();
		bindEventHandlers();
	}
	
	private void prepareData(){
		dcReceiveDate.setDate(new Date());
	}
	
	void resetData(){
		this.txtGRNId.setText("");
		this.txtPPId.setText("");
		this.txtPurchaseNote.setText("");
		this.dcReceiveDate.setDate(new Date());
		this.txtNote.setText("");
		this.txtProvider.setText("");
	}
	
	private void generateGRNId() {
		String grnId = "GRN_" +Formatter.sdfTimeId.format((new Date()).getTime());	
		this.txtGRNId.setText(grnId);
	}
	
	public PurchaseGrn saveData(){
		beanPgrn = new PurchaseGrn();
		beanPgrn.setGrn_id(txtGRNId.getText());
		beanPgrn.setPp_id(txtPPId.getText());
		beanPgrn.setReceive_date(dcReceiveDate.getDate().toString());
		beanPgrn.setNote(txtNote.getText());
		beanPgrn = logicPurchaseGrn.insertPurchaseGrn(beanPgrn);
		beanCurrentTranx.setCurrent_step("STOCK_CARD");
		beanCurrentTranx.setVoucher_id(txtGRNId.getText());
		try {
			logicDailyTranxLogic.update(beanCurrentTranx);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return beanPgrn;
	}
	
	@Override
	protected void bindEventHandlers() {
	}

	@Override
	public Object getData(String var) {
		return null;
	}

	@Override
	protected void applyStyles() {
	}

	@Override
	public void update() {
	}

	@Override
	public void updateList() {
	}

	@Override
	protected void checkPermission() {
	}

	public void addCActionListener(CActionListener al) {
		vctListener.add(al);
	}

	public void removeCActionListener(CActionListener al) {
	}

	public void cActionPerformed(CActionEvent action) {
		if(action.getAction() == CActionEvent.SAVE){
			// Validate
			if(Validator.validateEmpty(txtGRNId, SystemConfiguration.DEFAULT_DISABLED_TEXT, Language.getInstance().get("GRNId")) 
					&& Validator.validateEmpty(txtPPId, SystemConfiguration.DEFAULT_DISABLED_TEXT, Language.getInstance().get("Purchasing plan id"))){
				// write to database
				saveData();
				action.setData(beanPgrn);
				fireCActionEvent(action);
				isActived = false;
				resetData();
			}
		}
		if(action.getAction() == CActionEvent.CANCEL){
			fireCActionEvent(action);
			isActived = false;
			resetData();
		}
		//ADD Action fired by New button on Main GRN GUI.
		//UPDATE_GRN Action fired from master GUI (list transaction + button)
		if(action.getAction() == CActionEvent.UPDATE_GRN){
			prepareData();
			isActived = true;
			generateGRNId();
			fireCActionEvent(action);			
			beanCurrentTranx = (DailyTransaction)action.getData();
			txtPPId.setText(beanCurrentTranx.getVoucher_id());
			PurchasingPlan tmpPP = logicPurchase.getPurchasingPlan(beanCurrentTranx.getVoucher_id());
			if(tmpPP != null){
				txtPurchaseNote.setText(tmpPP.getNote());
				//Get provider info
				int cusId = tmpPP.getProviderId();
				try {					
					Customer c = CustomerLogic.getInstance().getCustomer(cusId);
					txtProvider.setText(c.getName());
				} catch (Exception e) {
					txtProvider.setText(String.valueOf(cusId));
				}
			}
		}
	}
	
	private void fireCActionEvent(CActionEvent action){
		for (CActionListener listener : vctListener) {
			listener.cActionPerformed(action);
		}
	}

	public PurchaseGrn getPgrn() {
		return beanPgrn;
	}

	public void setPgrn(PurchaseGrn pgrn) {
		this.beanPgrn = pgrn;
	}

	public void itemStateChanged(ItemEvent e) {
//		if (e.getStateChange() == ItemEvent.SELECTED) {
//			ItemWrapper item = (ItemWrapper)e.getItem();
//			txtPurchaseNote.setText(item.toString());
//		}
	}

}
