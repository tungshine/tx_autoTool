package com.tanglover.communication.datadeal;

import java.io.Serializable;
import java.util.ArrayList;

public class ServerToClientInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<OnlineUserBean> onlineList ;
	private ArrayList<UserInfoBean> allUserList;
	public ServerToClientInfo() {
		super();
	}
	public ArrayList<OnlineUserBean> getOnlineList() {
		return onlineList;
	}
	public void setOnlineList(ArrayList<OnlineUserBean> onlineList) {
		this.onlineList = onlineList;
	}
	public ArrayList<UserInfoBean> getAllUserList() {
		return allUserList;
	}
	public void setAllUserList(ArrayList<UserInfoBean> allUserList) {
		this.allUserList = allUserList;
	}
}
