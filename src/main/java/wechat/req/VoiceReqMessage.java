package wechat.req;

/**
 * @author: TangXu
 * @date: 2018/10/29 19:10
 * @description: 音频消息
 */
public class VoiceReqMessage extends BaseReqMessage {
	// 媒体ID
	private String MediaId;
	// 语音格式
	private String Format;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public String getFormat() {
		return Format;
	}

	public void setFormat(String format) {
		Format = format;
	}
}
