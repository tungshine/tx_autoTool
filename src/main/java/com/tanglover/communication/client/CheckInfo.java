package com.tanglover.communication.client;

import java.io.Serializable;

public class CheckInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String pwd;
	private String myIp;

	public String getMyIp() {
		return myIp;
	}
	public void setMyIp(String myIp) {
		this.myIp = myIp;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	
}
