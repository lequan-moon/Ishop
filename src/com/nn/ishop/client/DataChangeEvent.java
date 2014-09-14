package com.nn.ishop.client;

import java.util.EventObject;

public class DataChangeEvent extends EventObject {
	private static final long serialVersionUID = -6507417360934146577L;
	@SuppressWarnings("unused")
	private Object data;

	public DataChangeEvent(Object source, int data) {
		super(source);
	}
}