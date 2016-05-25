/*
 *Project: strom
 *File: com.gr158.storm.util.StringUtil.java <2016年4月17日>
 ****************************************************************
 * 版权所有@2016 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.gr158.storm.util;

/**
 *
 * @author liujie 
 * @Date 2016年4月17日 下午9:37:54
 * @version 1.0
 */
public class StringUtil {
	
	public static int getIndexFormArr(String [] arr,String look){
		
		if(arr == null || arr.length < 1 || !isEmpty(look)){
			return -1;
		}
		for(int i = 0; i < arr.length; i ++){
			if(look.equals(arr[i])){
				return i;
			}
		}
		return -1;
	}
	
	public static boolean isEmpty(String look){
		if(look == null){
			return true;
		}
		look = (look.trim()).toLowerCase();
		if(look.length() < 1 || "null".equals(look)){
			return true;
		}
		return false;
	}
}
