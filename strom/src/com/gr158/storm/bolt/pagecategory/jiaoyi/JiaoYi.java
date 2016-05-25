package com.gr158.storm.bolt.pagecategory.jiaoyi;

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
 * 1.统计交易网站的访问次数
 * 2.将网站各个明细页进行统计比如交易的搜索页被点击了多少次
 * 交易的主页被点击了多少次
 * 3.统计一个商品或一个店铺被点击了多少次
 * @author liujie<2016年3月23日>
 */
public class JiaoYi extends MyBaseRichBolt{
	Map<String, Integer> pageMap;
	private OutputCollector collector;
	private int size = 0;
	private static final long serialVersionUID = -7675260248964394972L;

	@Override
	public void cleanup() {
		System.out.println("JiaoYi..over");
	}

	@Override
	public void execute(Tuple arg0) {
		size ++;
		System.out.println("交易网站点击量{"+size+"}："+ arg0.getValue(0));
		
		//获取pv的分类
		String category = (String) arg0.getValue(0);
		
		//如果包含_代表是需要统计明细的需要流转到下一个bolt进行页面参数统计比如某个商品被点击了多少次，某个店铺被点击了多少次
		String page = "";
		if(category.contains("_")){
			String [] cateAndParam = category.split("_");
			String cate = cateAndParam[0];
			String param = cateAndParam[1];
			//将统计数据进行分流下一步
			collector.emit(new Values(cate,param));
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
		this.collector = arg2;
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer arg0) {
		arg0.declare(new Fields("cate", "param"));
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}
}
