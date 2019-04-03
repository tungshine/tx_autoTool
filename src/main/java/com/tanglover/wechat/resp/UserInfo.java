package com.tanglover.wechat.resp;

/**
 * 获得关注用户的信息
 * @author TungShine
 *{
    "subscribe": 1, 
    "openid": "o6_bmjrPTlm6_2sgVt7hMZOPfL2M", 
    "nickname": "Band", 
    "sex": 1, 
    "language": "zh_CN", 
    "city": "广州", 
    "province": "广东", 
    "country": "中国", 
    "headimgurl":    "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0", 
   "subscribe_time": 1382694957
}
 */
public class UserInfo {
	private int subScribe;
	private String openid;
	private String nickname;
	private int sex;
	private String language;
	private String city;
	private String province;
	private String country;
	private String headimgurl;
	private long subscribe_time;
	
	
   public int getSubScribe(){
	   return subScribe;
   }


	public void setSubScribe(int subScribe) {
		this.subScribe = subScribe;
	}



	public String getOpenid() {
		return openid;
	}



	public void setOpenid(String openid) {
		this.openid = openid;
	}



	public String getNickname() {
		return nickname;
	}



	public void setNickname(String nickname) {
		this.nickname = nickname;
	}



	public int getSex() {
		return sex;
	}



	public void setSex(int sex) {
		this.sex = sex;
	}



	public String getLanguage() {
		return language;
	}



	public void setLanguage(String language) {
		this.language = language;
	}



	public String getCity() {
		return city;
	}



	public void setCity(String city) {
		this.city = city;
	}



	public String getProvince() {
		return province;
	}



	public void setProvince(String province) {
		this.province = province;
	}



	public String getCountry() {
		return country;
	}



	public void setCountry(String country) {
		this.country = country;
	}



	public String getHeadimgurl() {
		return headimgurl;
	}



	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}



	public long getSubscribe_time() {
		return subscribe_time;
	}



	public void setSubscribe_time(long  subscribe_time) {
		this.subscribe_time = subscribe_time;
	}



	public static void main(String []args){
		System.out.println(Integer.MAX_VALUE);
	}
}
