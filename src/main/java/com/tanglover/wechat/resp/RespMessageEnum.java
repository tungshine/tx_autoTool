package com.tanglover.wechat.resp;

public enum RespMessageEnum {
	
	//返回消息类型：文本 音乐 图文
	TEXT("text"), MUSIC("music"), NEWS("news"),LinK("link");


	private RespMessageEnum(String name) {
		this.name = name;
	}

	public String toString() {
		return "RespMessageEnum:"+this.name;
	}
	
	public String getValue(){
		return name;
	}

	private String name;
}
