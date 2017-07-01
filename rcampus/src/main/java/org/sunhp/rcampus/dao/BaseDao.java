package org.sunhp.rcampus.dao;

import java.io.Serializable;
import java.util.List;

import org.sunhp.rcampus.components.Page;
import org.sunhp.rcampus.components.Pageable;


public interface BaseDao<T> {

	/**
	 * 查找实体对象
	 * 
	 * @param id
	 *            ID
	 * @return 实体对象，若不存在则返回null
	 */
	T find(Serializable id);

	/**
	 * 根据条件查询
	 * 
	 * @param t
	 * @return
	 */
	List<T> find(T t);

	/**
	 * 查找实体对象分页
	 * 
	 * @param pageable
	 *            分页信息
	 * @return 实体对象分页
	 */
	Page<T> findPage(Pageable<T> pageable);

	/**
	 * 查询实体对象数量
	 * 
	 * @param filters
	 *            筛选
	 * @return 实体对象数量
	 */
	long count(Pageable<T> pageable);

	/**
	 * 修改一条记录
	 * 
	 * @param t
	 * @return
	 */
	boolean update(T t);

	/**
	 * 新增一条记录
	 * 
	 * @param t
	 * @return
	 */
	boolean add(T t);

	/**
	 * 根据ID删除记录
	 * 
	 * @param id
	 * @return
	 */
	boolean delete(Serializable id);

}
