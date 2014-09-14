package com.nn.ishop.client.logic;

import java.util.List;

public interface IShopLogic<T, S> {
	public T insert(T param)throws Throwable;
	public T update(T param)throws Throwable;
	public T get(S id)throws Throwable;	
	public List<T> load()throws Throwable;
	public List<T> search(S condition)throws Throwable;
}