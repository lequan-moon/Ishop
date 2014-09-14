package com.nn.ishop.client.gui.common;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.nn.ishop.client.gui.AbstractGUIManager;
import com.nn.ishop.client.gui.components.CPaging;
import com.nn.ishop.client.gui.helper.TableUtil;
import com.nn.ishop.client.logic.sale.SaleOrderLogic;
import com.nn.ishop.server.dto.sale.ItemStock;

public class ShowItemStockDialogManager extends AbstractGUIManager {
	public static Logger logger = Logger.getLogger(ShowItemStockDialogManager.class);
	CPaging itemStockPage;
	
	void init() {
		initTemplate(this, "common/showItemStockPage.xml");
		render();
		bindEventHandlers();
		
	}
	
	public ShowItemStockDialogManager(String locale){
		setLocale(locale);
		init();
	}
	
	@Override
	protected void applyStyles() {
	}

	@Override
	protected void bindEventHandlers() {
		initializeData();
	}

	@Override
	protected void checkPermission() {
	}

	@Override
	public Object getData(String var) {
		return null;
	}

	@Override
	public void update() {
	}

	@Override
	public void updateList() {
	}
	private void prepareData(){
		try {
			List<ItemStock> data = SaleOrderLogic.getInstance().loadItemStock();
			if(data != null && data.size()>0)
				TableUtil.addListToTable(itemStockPage, itemStockPage.getTable(), data, Arrays.asList(new Integer(0)));
		} catch (Throwable e) {			
			logger.info(" updateCatGroupInfor: "+e.getMessage());			
		}
	}
	
	private void initializeData(){
		try {
			prepareData();			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		
	}

}
