package com.nn.ishop.client.cache;

import com.nn.ishop.client.cache.Constants.CacheEventEnum;

public interface CacheListener {
	
	public String getName();
	
	public void cacheAction(CacheEventEnum event, Object data);
	
}
