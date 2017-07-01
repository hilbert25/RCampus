package org.sunhp.rcampus.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import org.sunhp.rcampus.components.Page;
import org.sunhp.rcampus.components.Pageable;
import org.sunhp.rcampus.dao.BaseDao;
import org.sunhp.rcampus.service.BaseService;

/** 
 * fuyufeng
 */


public class BaseServiceImpl<T> implements BaseService<T>{
	
	protected BaseDao<T> baseDao;
	
	public void setBaseDao(BaseDao<T> baseDao) {
		this.baseDao = baseDao;
	}


	/**
	 * <p>存储实体</p>
	 * @author txj  
	 * @date 2013-7-24 下午02:51:24 
	 * @param entity  实体
	 */
	@Transactional
	public void save(T entity){
		this.baseDao.add(entity);
	}

	
	/**
	 * <p>批量存储实体</p>
	 * @author txj  
	 * @date 2013-7-24 下午02:51:49 
	 * @param list  实体列表
	 */
	@Transactional
	public void saveList(List<T> list){
		for(T t:list){
			this.baseDao.add(t);
		}
	}

	
	/**
	 * <p>更新实体</p>
	 * @author txj  
	 * @date 2013-7-24 下午02:52:17 
	 * @param entity  实体
	 */
	@Transactional
	public void update(T entity){
		this.baseDao.update(entity);
	}

	
	/**
	 * <p>批量更新实体</p>
	 * @author txj  
	 * @date 2013-7-24 下午02:52:40 
	 * @param list  实体列表
	 */
	@Transactional
	public void updateList(List<T> list){
		for(T t: list) {
			this.baseDao.update(t);
		}
	}

	
	/**
	 * <p>根据ID查找实体</p>
	 * @author txj  
	 * @date 2013-7-24 下午02:53:28 
	 * @param id  实体编号
	 * @return  如果查到返回实体，否则返回null
	 */
	@Transactional(readOnly=true)
	public T get(Serializable id){
		return this.baseDao.find(id);
	}

	
	
	/**
	 * <p>查询所有的</p>
	 * @author txj  
	 * @date 2013-7-24 下午02:55:41 
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<T> getAll(T t){
		return this.baseDao.find(t);
	}

	/**
	 * <p>根据id删除实体</p>
	 * @author txj  
	 * @date 2013-7-24 下午04:06:20 
	 * @param id
	 */
	@Transactional
	public void delete(Serializable id){
		this.baseDao.delete(id);
	}

	
	/**
	 * <p>批量删除</p>
	 * @author txj  
	 * @date 2013-7-24 下午04:05:40 
	 * @param ids  编号数组
	 */
	@Transactional
	public void delete(Serializable...ids){
		this.baseDao.delete(ids);
	}
	
	/**
	 * <p>分页查询</p>
	 * @author txj  
	 * @date 2013-7-31 下午02:11:15 
	 * @param pager  分页信息
	 * @param queryUtil  查询条件信息
	 */
	@Transactional(readOnly=true)
	public Page<T> findByPager(Pageable<T> pageable){
		return this.baseDao.findPage(pageable);
	}
}
