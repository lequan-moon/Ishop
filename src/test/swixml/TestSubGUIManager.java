package test.swixml;

import com.nn.ishop.client.gui.AbstractGUIManager;

public class TestSubGUIManager extends AbstractGUIManager {

	public TestSubGUIManager( String locale) {
		init(locale);
	}
	public TestSubGUIManager() {
		init("vn");
	}
	@Override
	protected void applyStyles() {}

	@Override
	protected void bindEventHandlers() {}

	@Override
	protected void checkPermission() {}

	@Override
	public Object getData(String var) {return null;}

	@Override
	public void update() {}

	@Override
	public void updateList() {}
	private void init(String locale) {		
		//-- Change template here to test
		initTemplate(this, "sale/saleOrderPage.xml", locale);
		render();		
		bindEventHandlers();
	}

}
