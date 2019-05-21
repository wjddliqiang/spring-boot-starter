package com.lqq.bookbar.service;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

@Service
public class RedisService {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    
    /**
     * 写入数据
     * @param key
     * @param value
     */
    public void set(String key, Object value) {
        //更改在redis里面查看key编码问题
        //RedisSerializer redisSerializer = new  StringRedisSerializer();
        //redisTemplate.setKeySerializer(redisSerializer);
        ValueOperations<String, Object> vo =  redisTemplate.opsForValue();
        vo.set(key, value);
    }
    
    /**
     * 判断redis连接是否OK
     * @return
     */
    public boolean isConnOk() {
		try {
			redisTemplate.opsForValue().get("test");
		} catch (Exception e) {
			System.out.println("出异常了："+e.getMessage());
			return false;
		}
		return true;
	}
    
    public RedisTemplate<String, Object> geTemplate() {
		return redisTemplate;
	}

    /**
     * 读取数据
     * @param key
     * @return
     */
    public Object get(String key) {
        ValueOperations<String, Object> vo =  redisTemplate.opsForValue();
        return vo.get(key);
    }
    
    /**
     * 判定key是否存在于redis中
     * @param key
     * @return
     */
    public boolean hasKey(final String key) {
		return redisTemplate.hasKey(key);
	}
}