package com.tanglover.communication.client;

import com.tanglover.communication.datadeal.UserInfoBean;

import java.io.Serializable;
import java.util.ArrayList;

public class AllUserArrayList implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<UserInfoBean> allUserList;

	public ArrayList<UserInfoBean> getAllUserList() {
		return allUserList;
	}

	public void setAllUserList(ArrayList<UserInfoBean> allUserList) {
		this.allUserList = allUserList;
	}

	public AllUserArrayList(ArrayList<UserInfoBean> allUserList) {
		super();
		this.allUserList = allUserList;
	}

	public AllUserArrayList() {
		super();
	}
}
