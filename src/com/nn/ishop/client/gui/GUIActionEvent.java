package com.nn.ishop.client.gui;

import java.util.EventObject;

public class GUIActionEvent extends EventObject {
	private static final long serialVersionUID = 9067701840652095497L;
	
	public enum GUIType {
		PRODUCT_CAT, 
		PRODUCT_CAT_GROUP, 
		PRODUCT_CAT_PKG, 
		PRODUCT_CAT_SESION, 
		PRODUCT_CAT_UOM, 
		PRODUCT,
		PRICELIST,
		RELATION,
		WH_LOT_LIST
	}
	public enum GUIActionType {

		// application navigation
		NAVIGATE,
		RESTART_GUI,
		EXIT_APP,

		// entities
		SELECT,
		CANCEL,
		NEW,
		EDIT,
		SAVE,	
		PRINT,
		DELETE,

		// panels
		UPDATE_PANEL,
		MODIFY_PANEL,

		// sorting
		SORT_ASD,
		SORT_DES,
		MINIMIZE_WINDOW,
		MAXIMIZE_WINDOW
		
	}
	
	private Object data;
	private GUIActionType action;
	private GUIType guiType;
	
	public GUIActionEvent(Object source, GUIActionType action, Object data) {
		super(source);
		this.action = action;
		this.data = data;
	}

	public GUIActionEvent(Object source, GUIActionType action, GUIType guiType, Object data) {
		super(source);
		this.guiType = guiType;
		this.action = action;
		this.data = data;
	}

	public Object getData() {
		return data;
	}

	public GUIActionType getAction() {
		return action;
	}

	public GUIType getGuiType(){
		return guiType;
	}
}
