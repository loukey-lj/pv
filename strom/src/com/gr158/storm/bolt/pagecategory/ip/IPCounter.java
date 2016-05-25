package com.gr158.storm.bolt.pagecategory.ip;

import java.util.HashMap;
import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;

import com.gr158.storm.base.MyBaseRichBolt;

/**
 * 1.根据IP统计地区访问人数
 * 比如湖南访问有多少人，湖北地区访问的有多少人
 * 每点击一次 统计一次
 * @author liujie<2016年3月23日>
 */
public class IPCounter extends MyBaseRichBolt{
	Map<String, Integer> counters;
	private OutputCollector collector;
	int size =0;
	
	private static final long serialVersionUID = -7675260248964394972L;

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
	}
	

	@Override
	public void cleanup() {
		
	}

	@Override
	public void execute(Tuple input) {
		size ++ ;
		System.out.println("IP {"+size+"}："+ input.getValue(0));

		String str = input.getString(0);
		if (!counters.containsKey(str)) {
			counters.put(str, 1);
		} else {
			Integer c = counters.get(str) + 1;
			counters.put(str, c);
		}
		System.out.println("------------------------------------ ip Counter [" + name + "-" + id + "]-------------------------");
		for (Map.Entry<String, Integer> entry : counters.entrySet()) {
			System.out.println("地区：" + entry.getKey() + "访问次数: " + entry.getValue());
		}
		collector.ack(input);
	}

	@Override
	public void myPrepare(@SuppressWarnings("rawtypes") Map arg0, TopologyContext arg1, OutputCollector arg2) {
		this.counters = new HashMap<String, Integer>();
		this.collector = arg2;
	}


	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}

	
}
