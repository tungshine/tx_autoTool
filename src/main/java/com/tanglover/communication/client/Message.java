package com.tanglover.communication.client;

import java.io.Serializable;

public class Message implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sendUserName;
	private String reciveUserName;
	private String messageConten;
	private boolean flag;
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public String getSendUserName() {
		return sendUserName;
	}
	public void setSendUserName(String sendUserName) {
		this.sendUserName = sendUserName;
	}
	public String getReciveUserName() {
		return reciveUserName;
	}
	public void setReciveUserName(String reciveUserName) {
		this.reciveUserName = reciveUserName;
	}
	public String getMessageConten() {
		return messageConten;
	}
	public void setMessageConten(String messageConten) {
		this.messageConten = messageConten;
	}
	public Message(String sendUserName, String reciveUserName,
			String messageConten,boolean flag) {
		super();
		this.sendUserName = sendUserName;
		this.reciveUserName = reciveUserName;
		this.messageConten = messageConten;
		this.flag = flag;
	}
	public Message() {
		super();
	}
}
