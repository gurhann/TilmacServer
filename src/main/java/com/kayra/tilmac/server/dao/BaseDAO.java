package com.kayra.tilmac.server.dao;

import java.util.List;

public interface BaseDAO<T> {
	
	public void add(T t);

	public T findById(Object Id);

	public T update(T t);

	public void delete(T t);

	public void batchAdd(List<T> list);
}
