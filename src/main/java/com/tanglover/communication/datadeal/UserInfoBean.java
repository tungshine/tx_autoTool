package com.tanglover.communication.datadeal;

import java.io.Serializable;

public class UserInfoBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String touXiang;
	private String nichen;
	private String password;
	private String name;
	private String sex;
	private String part;
	private String phoneNum;
	private String email;
	private String jianjie;
	public String getTouXiang() {
		return touXiang;
	}
	public void setTouXiang(String touXiang) {
		this.touXiang = touXiang;
	}
	public String getNichen() {
		return nichen;
	}
	public void setNichen(String nichen) {
		this.nichen = nichen;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getPart() {
		return part;
	}
	public void setPart(String part) {
		this.part = part;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getJianjie() {
		return jianjie;
	}
	public void setJianjie(String jianjie) {
		this.jianjie = jianjie;
	}
	
	public UserInfoBean(String touXiang, String nichen, String password,
			String name, String sex, String part, String phoneNum,
			String email, String jianjie) {
		super();
		this.touXiang = touXiang;
		this.nichen = nichen;
		this.password = password;
		this.name = name;
		this.sex = sex;
		this.part = part;
		this.phoneNum = phoneNum;
		this.email = email;
		this.jianjie = jianjie;
	}
	public UserInfoBean() {
		super();
	}
	
	

}
