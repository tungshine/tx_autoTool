package com.tanglover.wechat.req;

/**
 * @author: TangXu
 * @date: 2018/10/29 19:10
 * @description: 文本消息
 */
public class TextReqMessage extends BaseReqMessage {
	// 消息内容
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
}