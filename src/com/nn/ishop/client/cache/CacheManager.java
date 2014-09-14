package com.nn.ishop.client.cache;

import java.util.LinkedHashMap;
import java.util.Map;

import javafx.collections.MapChangeListener;

public class CacheManager {
	private static Map<String, Cache> mapCache;
	private static Cache SINGLETON_CACHE;
	private static final String SINGLETON_CACHE_NAME = "SINGLETON_CACHE";
	
	public static Cache getCacheInstance(){
		if(SINGLETON_CACHE == null){
			SINGLETON_CACHE = new Cache(SINGLETON_CACHE_NAME);
		}
		return SINGLETON_CACHE;
	}
	
	public Cache getCacheInstance(String instanceName){
		if(mapCache == null){
			mapCache = new LinkedHashMap<String, Cache>();
		}else{
			if(mapCache.containsKey(instanceName)){
				return mapCache.get(instanceName);
			}
		}
		Cache cache = new Cache(instanceName);
		mapCache.put(instanceName, cache);
		return cache;
	}

	public Map<String, Cache> getCaches() {
		return mapCache;
	}
}