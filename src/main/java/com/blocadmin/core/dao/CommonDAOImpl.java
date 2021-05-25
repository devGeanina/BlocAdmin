package com.blocadmin.core.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.SynchronizationType;
import javax.transaction.Transactional;

@Transactional
public abstract class CommonDAOImpl<T> implements CommonDAO<T> {
	@PersistenceContext(type = PersistenceContextType.TRANSACTION, synchronization = SynchronizationType.SYNCHRONIZED, unitName = "BAPU")
	protected EntityManager em;

	private Class<T> type;

	public CommonDAOImpl() {
		Type t = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) t;
		type = (Class) pt.getActualTypeArguments()[0];
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public T create(final T t) {
		this.em.persist(t);
		return t;
	}

	@Override
	public void delete(final Object id) {
		this.em.remove(this.em.getReference(type, id));
	}

	@Override
	public T find(final Object id) {
		return this.em.find(type, id);
	}

	@Override
	public T update(final T t) {
		return this.em.merge(t);
	}

	@Override
	public void flush() {
		this.em.flush();
	}

	@Override
	public void clear() {
		this.em.clear();
	}

	@Override
	public T getReference(Class<T> clazz, Object pk) {
		return em.getReference(clazz, pk);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll() {
		return em.createQuery("Select t from " + type.getSimpleName() + " t").getResultList();
	}
}
