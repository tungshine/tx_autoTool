package wechat.resp;

/**
 * @author: TangXu
 * @date: 2018/10/29 19:10
 * @description: 微信通用接口凭证
 */
public class AccessToken {
    // 获取到的凭证
    private String access_token;
    // 凭证有效时间，单位：秒
    private int expires_in;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("token:[").append(this.access_token);
        sb.append("expire:[").append(this.expires_in);
        return sb.toString();
    }

}