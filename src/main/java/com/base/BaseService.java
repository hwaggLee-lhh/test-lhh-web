package com.base;

import java.util.Collection;
import java.util.List;

public interface BaseService<T> {
	T save(T t);
	void update(T t);
	void deleteById(String id);
	List<T> findList();
	void deleteByIdList(Collection<String> idList);
	T findById(String idStr);
	T loadById(String idStr);
}
