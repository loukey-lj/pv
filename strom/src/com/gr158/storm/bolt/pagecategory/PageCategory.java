package com.gr158.storm.bolt.pagecategory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import com.gr158.storm.base.MyBaseRichBolt;
import com.gr158.storm.entity.Page;
import com.gr158.storm.util.RedisPool;

/**
 * 1.统计整个网站的点击量
 * 2.解析pv数据将pv进行分类
 * 3.解析pv数据将IP进行分类
 * 4.解析pv数据的用户信息
 * @author liujie<2016年3月23日>
 */
public class PageCategory extends MyBaseRichBolt {
	private static final long serialVersionUID = 7222111255161190945L;
	private int pvSize = 0;
	private OutputCollector collector;
	Jedis jedis;
	private SimpleDateFormat format;
	//一分钟统计一次
	private long times = 60 * 1000;
	private long upTime = 0;
	private int upSize = 0;
	@Override
	public void cleanup() {
	}

	@Override
	public void execute(Tuple arg0) {
		List<Object> line = arg0.getValues();
		Page page = (Page) line.get(0);
		if(page.getType() == 2){
			collector.emit("jiaoyi_stream",new Values(page.getCategory()));
		}else{
			collector.emit("zixun_stream",new Values(page.getCategory()));
		}
		String ip = page.getIp();
		collector.emit("ip_stream",new Values(ip));
		
		String sessionId = page.getSeesionId();
		String loginId = page.getUser();
		//注意这个会不会流程其他不相关的 bolt 比如 Ipcounter JIAoyi Zixun...
		// 试验表明 这里发射出去的tuple只会流向FilterSession Bolt
		collector.emit(new Values(sessionId,loginId));
		
		pvSize ++ ;
		
		Date date = new Date();
		long now = date.getTime();
		//upTime ==0 说明是第一次进入
		if(upTime == 0){
			//某一分钟的
			String yyMMddHHmm = format.format(date);
			jedis.set(yyMMddHHmm,pvSize + "_" + (pvSize - upSize));
			upTime = now;
			upSize = pvSize;
		}else{
			
			//下一次统计时间
			if(now -upTime >= times){
				//某一分钟的
				String yyMMddHHmm = format.format(date);
				jedis.set(yyMMddHHmm,pvSize + "_" + (pvSize - upSize));
				upTime = now;
				upSize = pvSize;
			}
		}
		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer arg0) {
		arg0.declareStream("jiaoyi_stream", new Fields("page_jiaoyi"));
		arg0.declareStream("zixun_stream", new Fields("page_zixun"));
		arg0.declareStream("ip_stream", new Fields("ip"));
		arg0.declare(new Fields("sessionId","loginId"));
	}
	
	@Override
	public void myPrepare(@SuppressWarnings("rawtypes") Map arg0, TopologyContext arg1, OutputCollector arg2) {
		this.collector = arg2;
		jedis = RedisPool.getJedis();
		format = new SimpleDateFormat("yyMMddHHmm");
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}


}
