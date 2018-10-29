/**
 * <p>项目名bb-rp</p>
 * <p>包名：com.brtbeacon.wx.entity</p>
 * <p>文件名：OAuth2AccessToken.java</p>
 * <p>版本信息：</p>
 * <p>日期：2015年4月12日-下午5:46:55</p>
 * <p>创建人：程磊</p>
 * <p>Copyright (c) 2015重庆智石网络科技有限公司-版权所有</p>
 */
package wechat.po;

import java.util.Date;

/**
 * @author: TangXu
 * @date: 2018/10/29 19:08
 * @description: 保存获取的Oauth2.0token信息
 */
public class OAuth2AccessToken {

    /**
     * 网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
     */
    private String accessToken;

    /**
     * access_token接口调用凭证超时时间，单位（秒）
     */
    private Long expiresIn;

    /**
     * 用户刷新access_token
     */
    private String refreshToken;

    /**
     * 用户唯一标识，请注意，在未关注公众号时，用户访问公众号的网页，也会产生一个用户和公众号唯一的OpenID
     */
    private String openid;

    /**
     * 用户授权的作用域，使用逗号（,）分隔
     */
    private String scope;

    /**
     * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
     */
    private String unionid;

    /**
     * 上次请求时间
     */
    private Date requestTime;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    @Override
    public String toString() {
        return "OAuth2AccessToken [accessToken=" + accessToken + ", expiresIn="
                + expiresIn + ", refreshToken=" + refreshToken + ", openid="
                + openid + ", scope=" + scope + ", unionid=" + unionid
                + ", requestTime=" + requestTime + "]";
    }

}
