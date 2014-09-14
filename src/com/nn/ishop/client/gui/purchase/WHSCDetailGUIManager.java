package com.nn.ishop.client.gui.purchase;

import com.nn.ishop.client.gui.AbstractGUIManager;

public class WHSCDetailGUIManager extends AbstractGUIManager{

	public WHSCDetailGUIManager(String locale){
		setLocale(locale);
		init();
	}
	
	void init(){
		if (getLocale() != null && !getLocale().equals("")){
			initTemplate(this, "purchase/po/whscDetailPage.xml", getLocale());
		}else{
			initTemplate(this, "purchase/po/whscDetailPage.xml");		
			}
		render();
		bindEventHandlers();
	}
	
	@Override
	protected void bindEventHandlers() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getData(String var) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void applyStyles() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateList() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void checkPermission() {
		// TODO Auto-generated method stub
		
	}

}
