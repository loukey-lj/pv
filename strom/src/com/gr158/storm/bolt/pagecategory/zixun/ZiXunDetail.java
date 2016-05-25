package com.gr158.storm.bolt.pagecategory.zixun;

import java.util.HashMap;
import java.util.Map;

import com.gr158.storm.base.MyBaseRichBolt;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;

/**
 * 1.统计一个具体主题被点击了多少次
 * @author liujie<2016年3月23日>
 */
public class ZiXunDetail extends MyBaseRichBolt{
	Map<String, Map<String, Integer>> deatilMap;
	Map<String, Integer> pageMap;
//	private OutputCollector collector;
	private static final long serialVersionUID = -7675260248964394972L;

	@Override
	public void cleanup() {
		System.out.println("ZiXunDetail..over");
	}

	@Override
	public void execute(Tuple arg0) {
		
		//获取pv的分类
		String category = (String) arg0.getValue(0);
		
		//如果包含_代表是需要统计明细的需要流转到下一个bolt进行页面参数统计比如某个商品被点击了多少次，某个店铺被点击了多少次
		String page = "";
		if(category.contains("_")){
			String [] cateAndParam = category.split("_");
			String cate = cateAndParam[0];
			String param = cateAndParam[1];
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
		//如果是主页或者是不需要统计的页面
		else{
			page = category;
		}
		if (!pageMap.containsKey(page)) {
			pageMap.put(page, 1);
		} else {
			Integer c = pageMap.get(page) + 1;
			pageMap.put(page, c);
		}
	}

	@Override
	public void myPrepare(@SuppressWarnings("rawtypes") Map arg0, TopologyContext arg1, OutputCollector arg2) {
		this.pageMap = new HashMap<String, Integer>();
		this.deatilMap =  new HashMap<String, Map<String,Integer>>();
//		this.collector = arg2;
	
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer arg0) {
		
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}

}
