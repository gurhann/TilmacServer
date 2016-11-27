package com.kayra.tilmac.server.dao;

import java.util.List;

public interface BaseDAO<T> {
	
	public void add(T t);

	public Class<T> findById(Object Id);

	public void update(T t);

	public void delete(T t);

	public void batchAdd(List<T> list);
}
