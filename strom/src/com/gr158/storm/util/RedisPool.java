package com.gr158.storm.util;


import java.util.ResourceBundle;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisPool {
	
	private static  RedisPool pool = new RedisPool();
	
	private static  JedisPool jPool = null;
	
	private  RedisPool(){
		init();
	}
	
	public static RedisPool getPoolInstance(){
		return pool;
	}
	
	
	private static void init(){
		JedisPoolConfig con = new JedisPoolConfig();
		ResourceBundle bundle = ResourceBundle.getBundle("redis");
	    if (bundle == null) {
	  throw new IllegalArgumentException(
	    "[redis.properties] is not found!");
	    }
	    JedisPoolConfig config = new JedisPoolConfig();
	    config.setMaxActive(Integer.valueOf(bundle.getString("redis.pool.maxActive")));
	    config.setMaxIdle(Integer.valueOf(bundle
	      .getString("redis.pool.maxIdle")));
	    config.setMaxWait(Long.valueOf(bundle.getString("redis.pool.maxWait")));
	    config.setTestOnBorrow(Boolean.valueOf(bundle
	      .getString("redis.pool.testOnBorrow")));
	    config.setTestOnReturn(Boolean.valueOf(bundle
	      .getString("redis.pool.testOnReturn")));
	    jPool = new JedisPool(config, bundle.getString("redis.ip"),
	      Integer.valueOf(bundle.getString("redis.port")));	
	}
	
	
	public static Jedis getJedis(){
		if(jPool == null){
			init();
			if(jPool == null){
				return null;
			}
		}
		
		return jPool.getResource();
	}
	
	public static void returnResource(Jedis resource){
		jPool.returnResource(resource);
	}
}
