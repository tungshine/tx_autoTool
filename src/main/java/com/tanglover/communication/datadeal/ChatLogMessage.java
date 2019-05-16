package com.tanglover.communication.datadeal;

import java.io.Serializable;
import java.util.Map;

public class ChatLogMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Map<String, String> map;

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> mapChatLog) {
		this.map = mapChatLog;
	}

}
