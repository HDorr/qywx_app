package com.ziwow.scrmapp.common.bean;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author frank
 * 
 * @param <T>
 * @param <PK>
 */
public abstract class GenericServiceImpl<T extends Object, PK> {

	// protected abstract GenericMapper<T, PK> genericMapper;
	@Autowired
	protected GenericMapper<T, PK> genericMapper;

	@Transactional(propagation = Propagation.REQUIRED)
	public int insert(T entity) {
		return this.genericMapper.insert(entity);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public int insertSelective(T entity) {
		return this.genericMapper.insertSelective(entity);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public int deleteByPrimaryKey(PK id) {
		return this.genericMapper.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public T selectByPrimaryKey(PK id) {
		return this.genericMapper.selectByPrimaryKey(id);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<T> selectByExample(Criteria c) {
		return this.genericMapper.selectByExample(c);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public int updateByPrimaryKeySelective(T entity) {
		return this.genericMapper.updateByPrimaryKeySelective(entity);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public int updateByPrimaryKey(T entity) {
		return this.genericMapper.updateByPrimaryKey(entity);
	}

}
