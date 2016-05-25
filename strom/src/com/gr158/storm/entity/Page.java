/*
 *Project: strom
 *File: cn.itcast.storm.entity.Page.java <2016年3月8日>
 ****************************************************************
 * 版权所有@2016 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.gr158.storm.entity;

import java.util.Date;

/**
 *
 * @author liujie 
 * @Date 2016年3月8日 下午8:53:15
 * @version 1.0
 */
public class Page {
	
	//网站地址
	private String url;
	//访问时间
	private Date date;
	//网站类型
	private Integer type;
	//访问IP
	private String ip;
	//用户名
	private String user;
	//sessionId
	private String seesionId;
	
	
	private String category;
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getSeesionId() {
		return seesionId;
	}
	public void setSeesionId(String seesionId) {
		this.seesionId = seesionId;
	}
}
