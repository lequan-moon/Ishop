package com.nn.ishop.client.cache;

import java.util.LinkedHashMap;
import java.util.Queue;
import java.util.Vector;

import com.nn.ishop.client.cache.Constants.CacheCommandEnum;
import com.nn.ishop.client.cache.Constants.CacheEventEnum;

public class Cache {
	private String instance_name;
	private LinkedHashMap<String, CacheElement> data;
	private int cacheHit = 0;
	private int cacheMiss = 0;
	private Vector<CacheListener> lstListener;
	private Queue<CacheCommand> queueCommand;

	public Cache(String instance_name) {
		super();
		this.instance_name = instance_name;
	}

	public void addElement(String name, CacheElement<Object> element){
		//Check if existed then update
		//else add
		if(data.containsKey(name)){
			// Update
			actionProcedure(CacheCommandEnum.UPDATE, element);
		}else{
			// Add
			actionProcedure(CacheCommandEnum.INSERT, element);
		}
		data.put(name, element);
	}
	
	public CacheElement<Object> getElement(String name){
		// Check if cache contains Element then return the element
		// else go to database to get that
		if(data.containsKey(name)){
			return data.get(name);
		}
		// Get from database and add into Cache, return it too
		return null;
	}
	
	public void removeElement(CacheElement<Object> element){
		// Update flg_usage = 0
	}
	
	public void fireCacheAction(CacheEventEnum event, CacheElement<Object> data){
		for (CacheListener cacheListener : lstListener) {
			cacheListener.cacheAction(event, data);
		}
	}
	
	public void actionProcedure(CacheCommandEnum command, CacheElement<Object> element){
		queueCommand.add(new CacheCommand(command, element.getValue()));
		if(command == CacheCommandEnum.INSERT){
			fireCacheAction(CacheEventEnum.CREATE, element);
		}else if(command == CacheCommandEnum.UPDATE){
			fireCacheAction(CacheEventEnum.UPDATE, element);
		}
	}
	
	public int getCacheHitPercent(){
		if(cacheMiss != 0 && cacheHit != 0){
			return cacheHit/(cacheHit + cacheMiss);
		}
		return 0;
	}
	
	public Queue<CacheCommand> getQueueCommand() {
		return queueCommand;
	}

	public void setQueueCommand(Queue<CacheCommand> queueCommand) {
		this.queueCommand = queueCommand;
	}
	
	public LinkedHashMap<String, CacheElement> getData() {
		return data;
	}
	public void setData(LinkedHashMap<String, CacheElement> data) {
		this.data = data;
	}
	public int getCacheHit() {
		return cacheHit;
	}
	public void setCacheHit(int cacheHit) {
		this.cacheHit = cacheHit;
	}
	public int getCacheMiss() {
		return cacheMiss;
	}
	public void setCacheMiss(int cacheMiss) {
		this.cacheMiss = cacheMiss;
	}
	public Vector<CacheListener> getLstListener() {
		return lstListener;
	}
	public void setLstListener(Vector<CacheListener> lstListener) {
		this.lstListener = lstListener;
	}
	public String getInstance_name() {
		return instance_name;
	}

	public void setInstance_name(String instance_name) {
		this.instance_name = instance_name;
	}
}
