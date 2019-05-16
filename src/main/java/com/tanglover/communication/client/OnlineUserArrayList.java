package com.tanglover.communication.client;

import com.tanglover.communication.datadeal.OnlineUserBean;

import java.io.Serializable;
import java.util.ArrayList;

public class OnlineUserArrayList implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<OnlineUserBean> onlineUserList;

	public ArrayList<OnlineUserBean> getOnlineUserList() {
		return onlineUserList;
	}

	public void setOnlineUserList(ArrayList<OnlineUserBean> onlineUserList) {
		this.onlineUserList = onlineUserList;
	}

	public OnlineUserArrayList(ArrayList<OnlineUserBean> onlineUserList) {
		super();
		this.onlineUserList = onlineUserList;
	}

	public OnlineUserArrayList() {
		super();
	}
}
