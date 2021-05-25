package com.blocadmin.core.dao;

import java.util.List;

public interface CommonDAO<T> {
	public abstract T create(T t);

	public abstract void delete(Object id);

	public abstract T find(Object id);

	public abstract T update(T t);

	public abstract void flush();

	public abstract void clear();

	public abstract T getReference(Class<T> entityClass, Object primaryKey);

	public abstract List<T> findAll();
}
