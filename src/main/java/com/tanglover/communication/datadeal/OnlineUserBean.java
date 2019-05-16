package com.tanglover.communication.datadeal;

import java.io.Serializable;

public class OnlineUserBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String loginName;
	private String loginIP;
	public OnlineUserBean(String loginName, String loginIP) {
		super();
		this.loginName = loginName;
		this.loginIP = loginIP;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getLoginIP() {
		return loginIP;
	}
	public void setLoginIP(String loginIP) {
		this.loginIP = loginIP;
	}
	public OnlineUserBean() {
		super();
	}
}
