package com.gr158.storm.bolt.user;

import java.util.HashMap;
import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;

import com.gr158.storm.base.MyBaseRichBolt;

/**
 *1.统计一天用户的活跃数
 *  根据sessionID去重
 *	根据用户去重
 *	不同sessionId的游客算一个用户
 *2.统计一个用户一天登陆了多少次
 *  根据sesionId 去重
 *  根据用户去重
 *  游客不加入统计
 * @author liujie<2016年3月23日>
 */
public class UserCounter extends MyBaseRichBolt{
	Map<String, Integer> userMap;
	private int size = 0;
	private static final long serialVersionUID = -7675260248964394972L;

	@Override
	public void cleanup() {
	}

	@Override
	public void execute(Tuple arg0) {
		
		//获取登陆用户名
		String loginId = (String) arg0.getValue(0);
		//如果这个用户是游客
		if (loginId.equals("游客")){
			size ++;
		}else{
			//不是游客 并且并未统计过此游客
			if ( !userMap.containsKey(loginId)) {
				userMap.put(loginId, 1);
				size ++;
			}
			//不是游客并且存在过这个用户
			else {
				Integer times = userMap.get(loginId) + 1;
				userMap.put(loginId, times);
			}
		}
		System.out.println("用户{"+size+"}："+ arg0.getValue(0));
	}

	@Override
	public void myPrepare(@SuppressWarnings("rawtypes") Map arg0, TopologyContext arg1, OutputCollector arg2) {
		this.userMap = new HashMap<String, Integer>();
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer arg0) {
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}
}
