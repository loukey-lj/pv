package com.gr158.storm.topology;

import redis.clients.jedis.JedisPool;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;

import com.gr158.storm.bolt.pagecategory.PageCategory;
import com.gr158.storm.spout.MakePV;

public class PVCountTopo {
	public static void main(String[] args) {
		if(args == null){
		}
		args = new String[11];
		args[0] = "1";
		args[1] = "1";
		args[2] = "1";
		args[3] = "1";
		args[4] = "1";
		args[5] = "1";
		args[6] = "1";
		args[7] = "1";
		args[8] = "1";
		args[9] = "1";
		args[10] = "1";
		
		    
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("pv-make", new MakePV(),Integer.valueOf(args[0]));
		builder.setBolt("category", new PageCategory(),Integer.valueOf(args[1])).shuffleGrouping("pv-make");
		
	/*	builder.setBolt("jiaoyi", new JiaoYi(),Integer.valueOf(args[2])).shuffleGrouping("category","jiaoyi_stream");
		builder.setBolt("jiaoyi_detail", new JiaoYiDetail(),Integer.valueOf(args[3])).fieldsGrouping("jiaoyi",new Fields("cate"));
		
		builder.setBolt("zixun", new ZiXun(),Integer.valueOf(args[4])).shuffleGrouping("category","zixun_stream");
		builder.setBolt("zixun_detail", new ZiXunDetail(),Integer.valueOf(args[5])).fieldsGrouping("zixun",new Fields("cate"));
		
		builder.setBolt("ip-counter", new IPCounter(),Integer.valueOf(args[6])).shuffleGrouping("category","ip_stream");
		
		builder.setBolt("session", new FilterSession(),Integer.valueOf(args[7])).fieldsGrouping("category",new Fields("sessionId"));
		builder.setBolt("user-counter", new UserCounter(),Integer.valueOf(args[7])).fieldsGrouping("session",new Fields("loginId"));*/
		
		
		
		 //Topology definition
//        builder.setBolt("word-counter",new WordCounter(),Integer.valueOf(args[0])).fieldsGrouping("fristCategory",new Fields("web","page"));
 
        Config conf =new Config();
        conf.setDebug(true);
 
        conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING,1);
        LocalCluster cluster =new LocalCluster();
        cluster.submitTopology("Getting-Started-Toplogie",conf,builder.createTopology());
        /*try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        cluster.shutdown();*/
	}
}
