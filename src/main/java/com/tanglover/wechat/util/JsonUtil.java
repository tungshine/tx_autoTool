package com.tanglover.wechat.util;

import com.tanglover.wechat.po.OAuth2AccessToken;
import org.json.JSONObject;

/**
 * @author: TangXu
 * @date: 2018/10/29 19:38
 * @description: 解析微信返回json格式数据
 */
public class JsonUtil {

    /**
     * 解析获取到的带有OAuth2AccessToken的数据 getOA2AccessToken(这里用一句话描述这个方法的作用)
     *
     * @param json
     * @return OAuth2AccessToken
     * @throws @since 1.0.0
     */
    public static OAuth2AccessToken getOA2AccessToken(String json) {
        try {
            JSONObject jo = new JSONObject(json);
            OAuth2AccessToken ot = new OAuth2AccessToken();
            ot.setAccessToken(jo.getString("access_token"));
            ot.setExpiresIn(jo.getLong("expires_in"));
            ot.setOpenid(jo.getString("openid"));
            ot.setRefreshToken(jo.getString("refresh_token"));
            ot.setRequestTime(TimeUtil.getNowTimestamp());
            ot.setScope(jo.getString("scope"));
            return ot;
        } catch (Exception e) {
            e.getStackTrace();
            // log.debug(e.getStackTrace());
            return null;
        }
    }

}
