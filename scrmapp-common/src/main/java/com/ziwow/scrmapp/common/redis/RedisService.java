package com.ziwow.scrmapp.common.redis;

import java.util.List;
import java.util.Set;

import java.util.concurrent.TimeUnit;

/**
 * 
 * ClassName: RedisService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014-11-11 下午5:19:06 <br/>
 *
 * @author hogen
 * @version 
 * @since JDK 1.6
 */
public interface RedisService {
	
	/** CommonOperations */
	public Boolean hasKey(Object key);
	
	public void delete(Object key);

	/** ValueOperations */
	public void set(Object key, Object value);
	
	/** timeout: 单位：秒  */
	public void set(Object key, Object value, Long timeout);
	
	public Object get(Object key);
	
	/** ListOperations */
	public Long leftPush(Object key, Object value);
	
	public Long leftPushAll(Object key, Object... values);
	
	public Object leftPop(Object key);
	
	public Long rightPush(Object key, Object value);
	
	public Long rightPushAll(Object key, Object... values);
	
	public Object rightPop(Object key);
	
	public Long listSize(Object key);
	
	//TODO
	public Long incr(Object key);
	public Long getIncr(Object key);
	
	/**
	 * 取出范围内数据
	 * range:(这里用一句话描述这个方法的作用). <br/>
	 *
	 * @author hogen
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 * @since JDK 1.6
	 */
	public List<Object> range(Object key, long start, long end);
	
	public Long getExpire(Object key,TimeUnit unit);
	public void setKeyExpire(Object key,Object value,Long expireTime,TimeUnit unit);
	
	//Set operations
    public Long addSet(Object key,Object ... values);
    
    public boolean isSetMember(Object key, Object value);
    
    public Set <Object> getSet(Object key);
    
    public Object popSet(Object key);
    
    public Long getSetSize(Object key);
    
    //Hash operations
    public void addHash(Object key,Object field,Object value);
    
    public List<Object> getHashValues(Object key);
    
    public Object getHash(Object key,Object field);
    
    public Set <Object> getHashKeys(Object key);
	
    /**
     * 
     * getKeys:(模糊查询所有key). <br/>
     *
     * @author hogen
     * @param aa
     * @return
     * @since JDK 1.6
     */
    public Set<Object> getKeys(Object aa);
    
    public void hashDel(Object key,Object field);
    
    public boolean hashHasKey(Object key,Object field);
    
    public void ZADD(String key, double score, Object member);
    
    public Set<Object> ZRANGE(String key, long start, long stop);
    
    public Long increment(Object key,Long value);
    
    public void setKeyExpire(Object key, long timeout, TimeUnit unit);
    
    public void convertAndSend(String channel,Object message);
}
