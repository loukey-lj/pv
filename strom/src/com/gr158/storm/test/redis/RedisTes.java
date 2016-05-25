/*
 *Project: strom
 *File: com.gr158.storm.test.redis.RedisTes.java <2016年4月16日>
 ****************************************************************
 * 版权所有@2016 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.gr158.storm.test.redis;

import java.text.SimpleDateFormat;
import java.util.Date;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 *
 * @author liujie 
 * @Date 2016年4月16日 上午11:16:01
 * @version 1.0
 */
public class RedisTes {
	private static JedisPool pool;
	public static void main(String[] args) {
		 JedisPoolConfig config = new JedisPoolConfig();
		    config.setMaxActive(Integer.valueOf(1024));
		    config.setMaxIdle(Integer.valueOf(200));
		    config.setMaxWait(Long.valueOf(1000));
		    config.setTestOnBorrow(Boolean.valueOf(true));
		    config.setTestOnReturn(Boolean.valueOf(true));
		    pool = new JedisPool(config, "localhost",6379);	
		    
		    Jedis jedis = pool.getResource();
		   SimpleDateFormat formatMm = new SimpleDateFormat("yyMMddHHmm");
		   SimpleDateFormat formatDd = new SimpleDateFormat("yyMMdd");
		  
		    while(true){
		    	 Date now = new Date();
				 String mm = formatMm.format(now);
				 String dd = formatDd.format(now);
				 String mmvalue = jedis.get(mm);
				 String ddvalue = jedis.get(dd);
				 System.out.println(mm +" ==> tolal:" +ddvalue + ";increat:"+mmvalue);
				 try {
					Thread.sleep(30*1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		    // 取数据
	}
}
