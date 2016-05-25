/*
 *Project: strom
 *File: cn.itcast.storm.util.PageUtils.java <2016年3月8日>
 ****************************************************************
 * 版权所有@2016 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.gr158.storm.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.gr158.storm.entity.Page;

/**
 *
 * @author liujie 
 * @Date 2016年3月8日 下午8:55:48
 * @version 1.0
 */
public class PageUtils {
	
	public static final String www = "http://www.gr158.com/";
	public static final String jiaoyi = "http://jiaoyi.gr158.com/";
	public static final String jiaoyi_spu = "http://jiaoyi.gr158.com/seller";
	public static final String jiaoyi_product = "http://jiaoyi.gr158.com/product";
	public static final String jiaoyi_shop = "http://jiaoyi.gr158.com/shop";
	public static final String jiaoyi_productlist = "http://jiaoyi.gr158.com/productlist";
	public static final String jiaoyi_kw = "http://jiaoyi.gr158.com/search?kw=";
	public static final String zixun = "http://zixun.gr158.com/";
	public static final String shangxun = "http://shangxun.gr158.com/";
	public static final String shangxun_serachList = "http://shangxun.gr158.com/businessInfo/serachList.shtml?type=";
	public static final String shangxun_detail = "http://shangxun.gr158.com/businessInfo/infoDetail.shtml?id=";
	/**
	 * Map<Integer,String>
	 * Integer：偶数为可变url(模拟点击不同的商品等)，奇数为不可变 
	 */
	private static Map<Integer,String> urlMap = new HashMap<Integer,String>();
	private static List<String> zixunList = new ArrayList<String>();
	private static List<String> ipAddr = new ArrayList<String>();
	private static List<String> session = new ArrayList<String>();
	private static List<String> user = new ArrayList<String>();
    static {
    	urlMap.put(1,www);
    	urlMap.put(3,jiaoyi);
    	urlMap.put(2,"http://jiaoyi.gr158.com/seller-{0}.html");
    	urlMap.put(4,"http://jiaoyi.gr158.com/product-{0}.html");
    	urlMap.put(6,"http://jiaoyi.gr158.com/shop-{0}.html");
    	urlMap.put(8,"http://jiaoyi.gr158.com/search?kw={str}");
    	urlMap.put(10,"http://jiaoyi.gr158.com/productlist-{0}.html");
    	urlMap.put(5,zixun);
    	urlMap.put(9,"http://zixun.gr158.com/hyzx.html");
    	urlMap.put(11,"http://zixun.gr158.com/mtbd.html");
    	urlMap.put(13,"http://zixun.gr158.com/zcfg.html");
    	urlMap.put(15,"http://zixun.gr158.com/yybg.html");
    	urlMap.put(17,"http://zixun.gr158.com/dcbg.html");
    	urlMap.put(19,"http://zixun.gr158.com/jyft.html");
    	urlMap.put(21,"http://zixun.gr158.com/sqfx.html");
    	urlMap.put(7,shangxun);
    	urlMap.put(12,"http://shangxun.gr158.com/businessInfo/serachList.shtml?type={0}");
    	
    	urlMap.put(14,"http://shangxun.gr158.com/businessInfo/infoDetail.shtml?id={0}");
    	zixunList.add("hyzx");
    	zixunList.add("mtbd");
    	zixunList.add("zcfg");
    	zixunList.add("yybg");
    	zixunList.add("dcbg");
    	zixunList.add("jyft");
    	zixunList.add("sqfx");
    	ipAddr.add("HN");ipAddr.add("HB");ipAddr.add("BJ");ipAddr.add("TJ");ipAddr.add("SH");ipAddr.add("GZ");ipAddr.add("JN");
    	ipAddr.add("SD");ipAddr.add("HN");ipAddr.add("XA");ipAddr.add("AH");ipAddr.add("JX");ipAddr.add("LD");ipAddr.add("YY");
    	ipAddr.add("XT");ipAddr.add("CS");ipAddr.add("CD");ipAddr.add("ZZ");ipAddr.add("HY");ipAddr.add("SZ");ipAddr.add("GZ");
    	ipAddr.add("DG");ipAddr.add("CP");ipAddr.add("ZJJ");ipAddr.add("ST");ipAddr.add("HM");ipAddr.add("HH");ipAddr.add("XZ");
    	
    	for(int i = 0; i< 50; i ++){
    		user.add("user"+i);
    		String s = UUID.randomUUID().toString();
    		session.add(s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24));
    	}
    	
	}

	/**
	 * 判断url是那个网站的
	 */
	public static String  getWebsite(String url){
		if(url == null){
			return null;
		}
		
		if(url.startsWith(jiaoyi_kw)){
			String seller = url.substring(url.indexOf("=") + 1);
			return "2/4_"+seller;
		}
		
		if(url.startsWith(jiaoyi_spu)){
			String seller = url.substring(url.indexOf("-") + 1, url.length()-5);
			return "2/4_"+seller;
		}
		
		if(url.startsWith(jiaoyi_product)){
			String seller = url.substring(url.indexOf("-") + 1, url.length()-5);
			return "2/1_"+seller;
		}
		if(url.startsWith(jiaoyi_shop)){
			String seller = url.substring(url.indexOf("-") + 1, url.length()-5);
			return "2/2_"+seller;
		}
		if(url.startsWith(jiaoyi_productlist)){
			String seller = url.substring(url.indexOf("-") + 1, url.length()-5);
			return "2/3_"+seller;
		}
		if(url.startsWith(shangxun_serachList)){
			String seller = url.substring(url.indexOf("=") + 1);
			return "4/1_"+seller;
		}
		
		if(url.startsWith(shangxun_detail)){
			String seller = url.substring(url.indexOf("=") + 1);
			return "4/2_"+seller;
		}
		
		if(url.startsWith(zixun) && url.length() != zixun.length() ){
			String type = url.substring(zixun.length(), url.length() -5);
			return "5/1_"+type;
		}
		if(url.startsWith(www)){
			return "1/0";
		}
		if(url.startsWith(jiaoyi)){
			return "2/0";
		}
		if(url.startsWith(zixun)){
			return "3/0";
		}
		if(url.startsWith(shangxun)){
			return "4/0";
		}
		return null;
	}
	
	/**
	 * 随机产生URl
	 */
	
	public static Page makUrl(){
		Integer next = 1+(int)(Math.random()*30);
		if(urlMap.containsKey(next)){
			String url = urlMap.get(next);
			if(url.contains("{0}")){
				url = url.replace("{0}", 1+(int)(Math.random()*100)+"");
			}else if(url.contains("{str}")){
				url = url.replace("{str}", zixunList.get((int)(Math.random()*7)));
			}
			Page p = new Page();
			p.setType(Integer.valueOf(getWebsite(url).split("/")[0]));
			p.setCategory(getWebsite(url));
			p.setUrl(url);
			p.setIp(ipAddr.get( 0+(int)(Math.random()*(ipAddr.size()))));
			p.setSeesionId(session.get( 0+(int)(Math.random()*(session.size()))));
			p.setUser(user.get( 0+(int)(Math.random()*(user.size()))));
			
			return p;
		}else{
			return makUrl();
		}
		
	}
	
	
	public static void main(String[] args) {
		for(int i = 0; i < 2000; i++){
//			System.out.println(i+"：" + makUrl());
			System.out.println();
		}
		
	}
}
