package wechat.resp;

/**
 * 文本消息
 * 
 * @author TungShine 2016年9月26日
 */
public class TextRespMessage extends BaseRespMessage {
	// 回复的消息内容
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
}