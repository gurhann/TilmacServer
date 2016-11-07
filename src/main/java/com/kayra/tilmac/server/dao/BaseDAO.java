package com.kayra.tilmac.server.dao;

import java.util.List;

public interface BaseDAO<T> {
	
	public void add(T t);

	public Class<T> findById(Object Id);

	public void update(T t);

	public void delete(T t);

	public Class<T> findByName(String text);

	public void batchAdd(List<Class<T>> list);
}
