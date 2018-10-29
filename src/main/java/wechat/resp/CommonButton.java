package wechat.resp;

/**
 * @author: TangXu
 * @date: 2018/10/29 19:11
 * @description: 普通按钮（子按钮）
 */
public class CommonButton extends Button {
	private String type = "click"; // 按钮类型
	private String key; // 关键字

	public String getType() {
		return type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}