package com.base;

import java.util.Collection;
import java.util.List;

public abstract class BaseServiceImpl<T extends Modelable> implements BaseService<T> {
	protected abstract BaseManager<T> getBaseManager();
	public T save(T entity) {
		return getBaseManager().save(entity);
	}
	public void update(T entity) {
		getBaseManager().update(entity);
	}
	public List<T> findList() {
		return getBaseManager().findList();
	}
	public void deleteById(String id) {
		getBaseManager().deleteByIdStr(id);
	}
	public void deleteByIdList(Collection<String> idList) {
		getBaseManager().deleteByIdList(idList);
	}
	public T findById(String idStr) {
		return getBaseManager().findById(idStr);
	}
	public T loadById(String idStr) {
		return getBaseManager().loadById(idStr);
	}
}
