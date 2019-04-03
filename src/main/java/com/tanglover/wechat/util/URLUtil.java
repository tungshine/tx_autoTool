package com.tanglover.wechat.util;

/**
 * @author: TangXu
 * @date: 2018/10/29 19:41
 * @description: 微信基础接口URL生成工具
 */
public class URLUtil {

    public static final String WX_GET_ACCOSS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
    public static final String WX_GET_THIRD_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
    public static final String WX_GET_WECHAT_USER_URL = "https://api.weixin.qq.com/sns/userinfo";

    /**
     * 获取oauth2-accoss token接口(根据code换取token)
     * getWxOauth2AccossTokenUrl(这里用一句话描述这个方法的作用)
     *
     * @param appid  wxappid
     * @param secret AppSecret
     * @param code   微信返回的code
     * @return String
     * @throws @since 1.0.0
     */
    public static String getWxOauth2AccossTokenUrl(String appid, String secret, String code) {
        StringBuffer buff = new StringBuffer();
        buff.append(WX_GET_ACCOSS_TOKEN_URL + "?");
        buff.append("appid=" + appid);
        buff.append("&secret=" + secret);
        buff.append("&code=" + code);
        buff.append("&grant_type=authorization_code");
        return buff.toString();
    }

    /**
     * 拼接授权第三步url地址
     *
     * @param appid
     * @param refresh_token
     * @return
     */
    public static String getWxThirdUrl(String appid, String refresh_token) {
        StringBuilder buff = new StringBuilder();
        buff.append(WX_GET_THIRD_URL + "?");
        buff.append("appid=" + appid);
        buff.append("&grant_type=refresh_token");
        buff.append("&refresh_token=" + refresh_token);
        return buff.toString();
    }

    /**
     * 拼接授权第四步url地址
     *
     * @param access_token
     * @param openId
     * @param lang
     * @return
     */
    public static String getWxUserInfoUrl(String access_token, String openId, String lang) {
        StringBuilder buff = new StringBuilder();
        buff.append(WX_GET_WECHAT_USER_URL + "?");
        buff.append("access_token=" + access_token);
        buff.append("&openid=" + openId);
        buff.append("&lang=" + lang);
        return buff.toString();
    }

    /**
     * 授权后代用户获取oauth2-accoss token接口(根据code换取token)
     *
     * @param appid                公众号的appid
     * @param code                 填写第一步获取的code参数
     * @param componentAppid
     * @param componentAccessToken
     * @return String
     * @throws @since 1.0.0
     */
    public static String getWxOauth2AccossTokenUrl(String appid, String code, String componentAppid,
                                                   String componentAccessToken) {
        StringBuffer buff = new StringBuffer();
        buff.append("https://api.weixin.qq.com/sns/oauth2/component/access_token?");
        buff.append("appid=" + appid);
        buff.append("&code=" + code);
        buff.append("&grant_type=authorization_code");
        buff.append("&component_appid=" + componentAppid);
        buff.append("&component_access_token=" + componentAccessToken);
        return buff.toString();
    }

    /**
     * 带授权获取用户信息连接
     *
     * @param appid          公众号的appid
     * @param redirectUri    重定向地址，需要urlencode，这里填写的应是服务开发方的回调地址
     * @param state          回传的字符串
     * @param componentAppid 服务方的appid
     * @return String
     * @throws @since 1.0.0
     */
    public static String getActivityUrl(String appid, String redirectUri, String state, String componentAppid) {
        StringBuffer buff = new StringBuffer();
        buff.append("https://open.weixin.qq.com/connect/oauth2/authorize?");
        buff.append("appid=" + appid);
        buff.append("&redirect_uri=" + redirectUri);
        buff.append("&response_type=code");
        buff.append("&scope=snsapi_base");
        buff.append("&state=" + state);
        buff.append("&component_appid=" + componentAppid + "#wechat_redirect");
        return buff.toString();
    }

}
