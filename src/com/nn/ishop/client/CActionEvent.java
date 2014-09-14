package com.nn.ishop.client;

import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;

import com.nn.ishop.client.gui.Language;

public class CActionEvent extends EventObject {
	private static final long serialVersionUID = -4732402256824381441L;
	public static final int UPDATE = 0;
	public static final int REMOVE = 1;
	
	public static final int LIST_SELECT_ITEM = 4;
	
	public static final int ADD = 5;	
	
	public static final int SAVE = 6;
	
	public static final int SWITCH_LANG = 7;
	public static final int MINIMIZE_WIN = 8;
	public static final int MAXIMIZE_WIN = 9;
	public static final int UPDATE_COMBO_BOX = 10;
	
//	/** Initialize control data at startup **/
	public static final int INIT_CTRL_DATA = 11;
	
	public static final int LOAD_PRODUCT_BY_CAT_TREE = 12;
	public static final int PRODUCT_CHANGE = 13;	
	public static final int INIT_ADD_PRODUCT = 14;
	public static final int INIT_UPDATE_PRODUCT = 15;
	public static final int DELETE_PRODUCT = 16;
	
	public static final int SEARCH_PRODUCT_LIST = 17;
	public static final int INCOMPLETE_ACTION = 18;
	
	public static final int NOTIFY_ACTION = 19;
	
	public static final int UPDATE_USER = 20;
	public static final int NEW_USER = 21;
	public static final int SAVE_USER = 22;
	public static final int ENABLE_EDIT_USER = 23;
	
	public static final int NOTIFY_UPDATE_USER = 24;
	public static final int NOTIFY_ADD_USER = 25;
	
	public static final int NOTIFY_MODIFY_CUSTOMER = 26;
	
	public static final int COMPANY_NODE_SELECT = 27;
	
	public static final int LOAD_PRICELIST_BY_CAT = 28;
	
	public static final int SEARCH_PRICELIST = 29;
	
	public static final int LOST_FOCUS = 30;
	
	public static final int CANCEL = 31;
	
	public static final int NEW_TRANX = 32;
	
	public static final int RELOAD_TRANX_LIST = 33;
	
	public static final int UPDATE_PP = 34;
	public static final int UPDATE_GRN = 35;
	public static final int UPDATE_STOCK_CARD = 36;
	public static final int EXPORT_DATA = 37;
	public static final int IMPORT_DATA = 38;
	public static final int LOG_OUT = 39;
	public static final int EXPORT_REPORT = 40;
	
	public static final int LOAD_WARE_HOUSE=41;
	public static final int DELETE_LOT=42;
	public static final int DELETE_WH=43;
	public static final int LOAD_WARE_HOUSE_LOT=44;
	
	public static final int NOTIFY_LOADED_UPDATE_USER = 45;
	//Paging button command
	public static final int PAGING_SAVE_EVENT = 46;
	public static final int PAGING_ADD_EVENT =47;
	public static final int PAGING_EDIT_EVENT = 48;
	public static final int PAGING_DELETE_EVENT = 49;
	public static final int PAGING_REFRESH_EVENT = 50;
	public static final int PAGING_SEARCH_EVENT = 51;
	
	public static final int INIT_UPDATE_GRN = 52;
	public static final int  	EXPORT_REPORT_FINISH=53;
	/** Map event with description */
	private Map<Object,String> eventDescMap = new HashMap<Object, String>();  
	public static enum CAction {
		ADD,UPDATE,DELETE,SAVE,NONE,
		INIT_CTRL_DATA,LOAD_PRODUCT_BY_CAT_TREE
		,LIST_SELECT_ITEM,REMOVE,UPDATE_COMBO_BOX, 
		MAXIMIZE_WIN,MINIMIZE_WIN,SWITCH_LANG,RELOAD_TRANX,
		LOAD_WARE_HOUSE
	}
	
	private int action;
	private Object data;

	public CActionEvent(Object source, int action, Object data) {
		super(source);
		this.source = source;
		this.action = action;
		this.data = data;
		//-- put action description
		eventDescMap.put(UPDATE, Language.getInstance().getString("action.event.update"));
		eventDescMap.put(REMOVE, Language.getInstance().getString("action.event.remove"));
		eventDescMap.put(DELETE_PRODUCT, Language.getInstance().getString("action.event.remove"));		
		eventDescMap.put(LIST_SELECT_ITEM, Language.getInstance().getString("action.event.list.select.item"));
		eventDescMap.put(ADD, Language.getInstance().getString("action.event.add"));		
		
		eventDescMap.put(SAVE, Language.getInstance().getString("action.event.save"));
		eventDescMap.put(SWITCH_LANG, Language.getInstance().getString("action.event.switch.language"));		
		eventDescMap.put(UPDATE_COMBO_BOX, Language.getInstance().getString("action.event.update.control.data"));
		eventDescMap.put(INIT_CTRL_DATA, Language.getInstance().getString("action.event.init.control.data"));
		
		eventDescMap.put(LOAD_PRODUCT_BY_CAT_TREE, Language.getInstance().getString("action.event.load.product.list.by.cat"));
		eventDescMap.put(PRODUCT_CHANGE, Language.getInstance().getString("action.event.notify.product.change"));
		eventDescMap.put(INIT_ADD_PRODUCT, Language.getInstance().getString("action.event.init.add.product"));
		eventDescMap.put(INIT_UPDATE_PRODUCT, Language.getInstance().getString("action.event.init.update.product"));
		
		eventDescMap.put(CAction.ADD, Language.getInstance().getString("action.event.add"));
		eventDescMap.put(CAction.UPDATE, Language.getInstance().getString("action.event.update"));
		eventDescMap.put(CAction.DELETE, Language.getInstance().getString("action.event.remove"));
		eventDescMap.put(CAction.SAVE, Language.getInstance().getString("action.event.save"));
		eventDescMap.put(CAction.NONE, Language.getInstance().getString("action.event.none"));		
		eventDescMap.put(CAction.LIST_SELECT_ITEM, Language.getInstance().getString("action.event.list.select.item"));
		eventDescMap.put(CAction.REMOVE, Language.getInstance().getString("action.event.remove"));
		eventDescMap.put(CAction.UPDATE_COMBO_BOX, Language.getInstance().getString("action.event.update.control.data"));
		
		eventDescMap.put(SEARCH_PRODUCT_LIST, Language.getInstance().getString("search.product"));
		eventDescMap.put(INCOMPLETE_ACTION, Language.getInstance().getString("incomplete.action"));
		
		eventDescMap.put(NOTIFY_ACTION, Language.getInstance().getString("notify.action"));
		eventDescMap.put(RELOAD_TRANX_LIST, Language.getInstance().getString("reload.transaction.list"));
		eventDescMap.put(UPDATE_PP, Language.getInstance().getString("action.event.update.pp"));
	}
	public String actionDesc(){
		return eventDescMap.get(getAction());
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
