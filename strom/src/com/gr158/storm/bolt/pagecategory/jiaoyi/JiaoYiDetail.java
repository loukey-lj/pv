package com.gr158.storm.bolt.pagecategory.jiaoyi;

import java.util.HashMap;
import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;

import com.gr158.storm.base.MyBaseRichBolt;

/**
 * 1.统计一个商品或一个店铺被点击了多少次
 * @author liujie<2016年3月23日>
 */
public class JiaoYiDetail extends MyBaseRichBolt{
	Map<String, Map<String, Integer>> deatilMap;
	private static final long serialVersionUID = -7675260248964394972L;

	@Override
	public void cleanup() {
		System.out.println("JiaoYiDetail..over");
	}

	@Override
	public void execute(Tuple arg0) {
		String cate = arg0.getString(0);
		String param = arg0.getString(1);
		if (!deatilMap.containsKey(cate)) {
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			map.put(param, 1);
			deatilMap.put(cate, map);
		} else {
			HashMap<String, Integer> map = (HashMap<String, Integer>) deatilMap.get(cate);
			if(map.containsKey(param)){
				map.put(param, map.get(param) + 1);
			}else{
				map.put(param,  1);
			}
			deatilMap.put(cate, map);
		}
	}

	@Override
	public void myPrepare(@SuppressWarnings("rawtypes") Map arg0, TopologyContext arg1, OutputCollector arg2) {
		this.deatilMap =  new HashMap<String, Map<String,Integer>>();
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer arg0) {
		
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}
}
