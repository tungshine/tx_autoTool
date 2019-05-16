package com.tanglover.communication.client;

import com.tanglover.communication.datadeal.PartInfoBean;

import java.io.Serializable;
import java.util.ArrayList;

public class ServerToClient implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AllUserArrayList allUserList;
	private OnlineUserArrayList onlineList;
	private ArrayList<PartInfoBean> allPartList;
	
	public ServerToClient() {
		super();
	}
	public ServerToClient(AllUserArrayList allUserList,
			OnlineUserArrayList onlineList, ArrayList<PartInfoBean> allPartList) {
		super();
		this.allUserList = allUserList;
		this.onlineList = onlineList;
		this.allPartList = allPartList;
	}
	public ArrayList<PartInfoBean> getAllPartList() {
		return allPartList;
	}
	public void setAllPartList(ArrayList<PartInfoBean> allPartList) {
		this.allPartList = allPartList;
	}
	public AllUserArrayList getAllUserList() {
		return allUserList;
	}
	public void setAllUserList(AllUserArrayList allUserList) {
		this.allUserList = allUserList;
	}
	public OnlineUserArrayList getOnlineList() {
		return onlineList;
	}
	public void setOnlineList(OnlineUserArrayList onlineList) {
		this.onlineList = onlineList;
	}
}
