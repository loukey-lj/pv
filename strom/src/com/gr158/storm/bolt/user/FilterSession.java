/*
 *Project: strom
 *File: com.gr158.storm.bolt.user.FilterSession.java <2016年4月10日>
 ****************************************************************
 * 版权所有@2016 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.gr158.storm.bolt.user;

import java.util.HashMap;
import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import com.gr158.storm.base.MyBaseRichBolt;

/**
 * 过滤session每次pv都要经过此处
 * 进行session去重过滤，同一个会话
 * 不会往下流走
 * 
 * @author liujie 
 * @Date 2016年4月10日 下午4:30:45
 * @version 1.0
 */
public class FilterSession extends MyBaseRichBolt {

	private static final long serialVersionUID = -3363278410200238745L;
	Map<String, Integer> counters;
	private int pvSize = 0;
	private OutputCollector collector;

	@Override
	public void cleanup() {
	}

	@Override
	public void execute(Tuple arg0) {
		pvSize ++ ;
		System.out.println("FilterSession :{"+pvSize+"}" + arg0.getString(0));
		String sessionId =  arg0.getString(0);
		String loginId =  arg0.getString(1);
		//如果不存在会话
		if(!counters.containsKey(sessionId)){
			if(loginId == null || "".equals(loginId.trim()) || "null".equals((loginId.trim()).toLowerCase())){
				loginId = "游客";
			}
			//这个整形无意义 随便赋值 这里的map只做记录 不做统计
			counters.put(sessionId, 0);
			//将登陆用户流转到下一个tuple
			collector.emit(new Values(loginId));
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer arg0) {
		arg0.declare(new Fields("loginId"));
	}
	
	@Override
	public void myPrepare(@SuppressWarnings("rawtypes") Map arg0, TopologyContext arg1, OutputCollector arg2) {
		this.collector = arg2;
		this.counters = new HashMap<String, Integer>();
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}


}
