package com.nn.ishop.client.cache;

import com.nn.ishop.client.cache.Constants.CacheCommandEnum;

public class CacheCommand {
	private CacheCommandEnum command;
	private Object data;
	
	public CacheCommand(CacheCommandEnum command, Object data) {
		super();
		this.command = command;
		this.data = data;
	}
	public CacheCommandEnum getCommand() {
		return command;
	}
	public void setCommand(CacheCommandEnum command) {
		this.command = command;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
}
