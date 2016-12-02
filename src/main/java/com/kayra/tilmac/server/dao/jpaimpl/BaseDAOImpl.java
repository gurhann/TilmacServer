package com.kayra.tilmac.server.dao.jpaimpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;

import com.kayra.tilmac.server.dao.BaseDAO;

public class BaseDAOImpl<T> implements BaseDAO<T> {

	private Class<T> clazz;

	@Value("${jdbc.batch_size}")
	private int batchSize;

	@PersistenceContext
	private EntityManager em;

	public final void setClazz(Class<T> clazzToSet) {
		this.clazz = clazzToSet;
	}

	@Transactional
	@Override
	public void add(T t) {
		em.persist(t);
	}

	@Override
	public T findById(Object id) {
		return em.find(clazz, id);
	}

	@Transactional
	@Override
	public T update(T t) {
		return em.merge(t);
	}

	@Transactional
	@Override
	public void delete(T t) {
		em.remove(t);
	}

	@Transactional
	@Override
	public void batchAdd(List<T> list) {
		int i = 0;
		for (T t : list) {
			add(t);
			i++;
			if (i % batchSize == 0) {
				em.flush();
				em.clear();
			}
		}
	}

}
