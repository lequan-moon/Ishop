package com.nn.ishop.client.cache;

public class CacheElement<T> {
	T value;
	boolean isDBEntity = true;

	public CacheElement(T value, boolean isDBEntity) {
		super();
		this.value = value;
		this.isDBEntity = isDBEntity;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public boolean isDBEntity() {
		return isDBEntity;
	}

	public void setDBEntity(boolean isDBEntity) {
		this.isDBEntity = isDBEntity;
	}
	
}
