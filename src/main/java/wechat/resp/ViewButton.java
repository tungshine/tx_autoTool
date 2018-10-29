package wechat.resp;

/**
 * view类型的菜单
 * 
 * @author TungShine 2016年9月26日
 */
public class ViewButton extends Button {
	private String type = "view";
	private String url;

	public String getType() {
		return type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}