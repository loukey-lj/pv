/*
 *Project: strom
 *File: com.gr158.storm.base.StromBase.java <2016年4月10日>
 ****************************************************************
 * 版权所有@2016 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.gr158.storm.base;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;

/**
 *
 * @author liujie 
 * @Date 2016年4月10日 下午5:29:36
 * @version 1.0
 */
public abstract  class MyBaseRichBolt implements IRichBolt{
	private static final long serialVersionUID = 5394110606758839527L;
	protected Integer id;
	protected String name;
	
	
	@SuppressWarnings("rawtypes")
	@Override
	public void prepare(Map arg0, TopologyContext arg1, OutputCollector arg2) {
		this.name = arg1.getThisComponentId();
		this.id = arg1.getThisTaskId();
		myPrepare(arg0, arg1, arg2);
		
	}
	@SuppressWarnings("rawtypes")
	public abstract void myPrepare(Map arg0, TopologyContext arg1, OutputCollector arg2);
}
