package com.gr158.storm.spout;

import java.util.Date;
import java.util.Map;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import com.gr158.storm.entity.Page;
import com.gr158.storm.util.PageUtils;

/**
 * 模拟pv数据
 * @author liujie<2016年3月23日>
 */
public class MakePV extends BaseRichSpout {
	private static final long serialVersionUID = 2197521792014017918L;
	private SpoutOutputCollector collector;
	int i = 20 ;
	
	//父类方法
	@Override
	public void open(@SuppressWarnings("rawtypes") Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		this.collector = collector;
	}

	public void nextTuple() {
		
		while(true){
			//获取PV
			Page pageUrl = PageUtils.makUrl();
			pageUrl.setDate(new Date());
			this.collector.emit(new Values(pageUrl));
			try {
				Thread.sleep(((int)(Math.random()*10)*100));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields(new String[] { "page" }));
	}
}
