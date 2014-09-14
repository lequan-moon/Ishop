package com.nn.ishop.client.gui;

import java.awt.event.ActionEvent;

public class CGUIActionEvent extends ActionEvent {
	private static final long serialVersionUID = 5822163341158472537L;
	@SuppressWarnings("unused")
	private Object data;
	@SuppressWarnings("unused")
	private int action;
	public int VIEW = 0;
	public int EDIT = 1;
	public int SAVE = 2;

	public CGUIActionEvent(int action, Object data) {
		super(data, action, "");
	}
}