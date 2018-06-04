package com.ziwow.scrmapp.common.redis;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 
 * ClassName: SpringRedisServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014-11-11 下午5:19:12 <br/>
 *
 * @author hogen
 * @version 
 * @since JDK 1.6
 */

@Component("redisService")
public class SpringRedisServiceImpl implements RedisService {
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory
			.getLogger(SpringRedisServiceImpl.class);

	// inject the actual template
	@Autowired
	private RedisTemplate<Object, Object> template;
	
	public Boolean hasKey(Object key) {
		logger.debug("Redis hasKey, key [{}]", key);
		return template.hasKey(key);
	}
	
	public void delete(Object key) {
		logger.debug("Redis delete, key [{}]", key);
		template.delete(key);
	}
	
	public void set(Object key, Object value) {
		logger.debug("Redis set, key [{}] value [{}]", key, value);
		template.opsForValue().set(key, value);
	}
	
	public void set(Object key, Object value, Long timeout) {
		logger.debug("Redis set, key [{}] value [{}] expire [{}]", key, value, timeout);
		template.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
	}

	public Object get(Object key) {
		logger.debug("Redis get, key [{}]", key);
		return template.opsForValue().get(key);
	}

	public Long leftPush(Object key, Object value) {
		logger.debug("Redis leftPush, key [{}] value [{}]", key, value);
		return template.opsForList().leftPush(key, value);
	}

	public Long leftPushAll(Object key, Object... values) {
		logger.debug("Redis leftPushAll, key [{}] values [{}]", key, values);
		return template.opsForList().leftPushAll(key, values);
	}

	public Object leftPop(Object key) {
		logger.debug("Redis leftPop, key [{}]", key);
		return template.opsForList().leftPop(key);
	}

	public Long rightPush(Object key, Object value) {
		logger.debug("Redis rightPush, key [{}] value [{}]", key, value);
		return template.opsForList().rightPush(key, value);
	}

	public Long rightPushAll(Object key, Object... values) {
		logger.debug("Redis rightPushAll, key [{}] values [{}]", key, values);
		return template.opsForList().rightPushAll(key, values);
	}

	public Object rightPop(Object key) {
		logger.debug("Redis rightPop, key [{}]", key);
		return template.opsForList().rightPop(key);
	}
	
	public Long listSize(Object key) {
		logger.debug("Redis listSize, key [{}]", key);
		return template.opsForList().size(key);
	}
	
	public Long incr(Object key) {
		return template.opsForValue().increment(key, 1);
	}
	
	public Long getIncr(Object key) {
		String val = template.boundValueOps(key).get(0, -1);
		if(StringUtils.isNotBlank(val)) {
			return Long.parseLong(val);
		}
		return null;
	}

	public List<Object> range(Object key, long start, long end) {
		logger.debug("Redis range, key [{}] start [{}] end [{}]", key, start , end);
		return template.opsForList().range(key, start, end);
	}

	@Override
	public Long getExpire(Object key, TimeUnit unit) {
		return template.getExpire(key, unit);
	}
	
	@Override
	public void setKeyExpire(Object key,Object value,Long expireTime,TimeUnit unit){
		template.opsForValue().set(key, value, expireTime, unit);
	}
	
	public Long addSet(Object key,Object ... values){
	    logger.debug("Redis set, key [{}] values [{}]", key, values);
	       return template.opsForSet().add(key, values);
	   }
	    
	public Set <Object> getSet(Object key){
	    logger.debug("Redis get set, key [{}]", key);
	    return template.opsForSet().members(key);
	}
	
	public boolean isSetMember(Object key, Object value) {
		logger.debug("Redis set, value [{}], is key [{}]'s member", value, key);
	    return template.opsForSet().isMember(key, value);
	}
	
	public Object popSet(Object key) {
	    logger.debug("Redis pop key, key [{}]", key);
	    return template.opsForSet().pop(key);
	}
	    
	public Long getSetSize(Object key){
	    logger.debug("Redis Set Size, key [{}]", key);
	    return template.opsForSet().size(key);
	}
    
	/**
	 * TODO 简单描述该方法的实现功能.
	 * @see com.ziwow.api.core.cache.RedisService#addHash(java.lang.Object, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void addHash(Object key, Object field, Object value) {
		template.opsForHash().put(key, field, value);
	}

	/**
	 * TODO 简单描述该方法的实现功能.
	 * @see com.ziwow.api.core.cache.RedisService#getHash(java.lang.Object, java.lang.Object)
	 */
	@Override
	public Object getHash(Object key, Object field) {
		return template.opsForHash().get(key, field);
	}
	
	
	@Override
	public Set <Object> getHashKeys(Object key){
		return template.opsForHash().keys(key);
	}
	
	/**
	 * TODO 简单描述该方法的实现功能.
	 * @see com.ziwow.api.core.cache.RedisService#getHashValues(java.lang.Object)
	 */
	@Override
	public List<Object> getHashValues(Object key) {
		return template.opsForHash().values(key);
	}

	/**
	 * TODO 简单描述该方法的实现功能.
	 * @see com.ziwow.activity.core.cache.RedisService#getKey(java.lang.Object)
	 */
	@Override
	public Set<Object> getKeys(Object aa) {
		return template.keys(aa);
	}

	@Override
	public void hashDel(Object key, Object field) {
		template.opsForHash().delete(key, field);
		
	}

	@Override
	public boolean hashHasKey(Object key, Object field) {
		return template.opsForHash().hasKey(key, field);
	}

	@Override
	public void ZADD(String key, double score, Object member) {
		template.opsForZSet().add(key, member, score);
	}

	@Override
	public Set<Object> ZRANGE(String key, long start, long stop) {
		return template.opsForZSet().range(key, start, stop);
	}

	@Override
	public Long increment(Object key, Long value) {
		return template.opsForValue().increment(key,value);
	}

	@Override
	public void setKeyExpire(Object key, long timeout, TimeUnit unit) {
		template.expire(key, timeout, unit);
		
	}

	@Override
	public void convertAndSend(String channel,Object message) {
		template.convertAndSend(channel, message);
	}

}
