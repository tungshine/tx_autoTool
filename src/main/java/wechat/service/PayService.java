package wechat.service;

import encryption.MD5;
import org.jdom.JDOMException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tool.ReturnUtil;
import wechat.po.OAuth2AccessToken;
import wechat.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@Service
public class PayService {

    static final Logger logger = LoggerFactory.getLogger(PayService.class);

    public String getAccessToken(String code, String state, HttpServletRequest request) {
        OAuth2AccessToken ot = new OAuth2AccessToken();
        // 生成访问接口数据
        String url = URLUtil.getWxOauth2AccossTokenUrl(Constants.appid, Constants.appSercet, code);
        // 发起请求，获取数据
        String json;
        try {
            json = HTTPUtil.httpByGet(url);
            logger.info("拉取第二步access_token信息 ------{}", json);
            ot = JsonUtil.getOA2AccessToken(json);

            String thirdUrl = URLUtil.getWxThirdUrl(Constants.appid, ot.getRefreshToken());
            logger.info("拉取第三步url地址 ------{}", thirdUrl);
            String RefreshJson = HTTPUtil.httpByGet(thirdUrl);
            logger.info("拉取第三步RefreshJson ------{}", RefreshJson);
            String access_token = null;

            JSONObject jo = new JSONObject(RefreshJson);
            access_token = jo.getString("access_token");
            logger.info("拉取第三步Refresh返回的access_token ------{}", access_token);

            logger.info("开始获取微信的用户信息");
            String lang = "zh_CN";
            String wechatUserUrl = URLUtil.getWxUserInfoUrl(access_token, ot.getOpenid(), lang);
            logger.info("wechatUserUrl ------{}", wechatUserUrl);
            String weChatUserInfo = HTTPUtil.httpByGet(wechatUserUrl);
            logger.info("wechatuserinfo ------{}", weChatUserInfo);
            return weChatUserInfo;
        } catch (Exception e) {
            logger.info("exception is ------{}", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public Map<String, Object> uniformOrder(String orderNo, String openid, HttpServletRequest req) throws JDOMException, IOException {

        long orderTotalPrice = 0L;
        SortedMap<String, Object> paramsMap = new TreeMap<String, Object>();
        /* 统一下单生成签名 */
        String nonce_str = CommonUtil.create_nonce_str(32);
        paramsMap.put("appid", Constants.appid);
        paramsMap.put("body", "十分优品");
        paramsMap.put("device_info", "WEB");
        paramsMap.put("mch_id", Constants.mch_id);
        paramsMap.put("nonce_str", nonce_str);
        paramsMap.put("notify_url", Constants.notify_url);
        paramsMap.put("openid", openid);
        paramsMap.put("out_trade_no", orderNo);
        paramsMap.put("spbill_create_ip", CommonUtil.getIpAddr(req));
        paramsMap.put("total_fee", (int) (orderTotalPrice * 100));
        paramsMap.put("trade_type", "JSAPI");
        String signature = CommonUtil.signature(paramsMap);
        paramsMap.put("sign", signature);
        String requestXML = CommonUtil.getRequestXml(paramsMap);
        logger.info("====================获取签名结果：" + requestXML);

        logger.debug("====================11111获取签名结果：" + requestXML);
        String result = CommonUtil.httpsRequest(Constants.unified_order_url, "POST", requestXML);
        logger.info("====================统一下单结果：" + result);
        /* 解析统一下单结果 */
        Map<String, String> unifiedorderMap = CommonUtil.doXMLParse(result);
        String return_code = unifiedorderMap.get("return_code");

        if ("SUCCESS".equals(return_code)) {
            Map<String, Object> map = ReturnUtil.returnSuccess(null);
            Map<String, Object> payMap = new HashMap<String, Object>();

            /* H5调用支付API需要重新生成签名 */
            String nonceStr2 = CommonUtil.create_nonce_str(32);
            StringBuilder signs = new StringBuilder();
            signs.append("appId=").append(Constants.appid);
            signs.append("&nonceStr=").append(nonceStr2);
            signs.append("&package=").append("prepay_id=").append((String) unifiedorderMap.get("prepay_id"));
            signs.append("&signType=").append("MD5");
            long timeStamp = System.currentTimeMillis() / 1000;
            signs.append("&timeStamp=").append(timeStamp).append("&key=").append(Constants.api_key);
            String sign = MD5.md5(signs.toString());

            payMap.put("appId", Constants.appid);
            payMap.put("timeStamp", timeStamp);
            payMap.put("nonceStr", nonceStr2);
            payMap.put("prepay_id", (String) unifiedorderMap.get("prepay_id"));
            payMap.put("signType", "MD5");
            payMap.put("paySign", sign);
            payMap.put("orderNo", orderNo);
            logger.info("====================返回支付信息到前端");

            map.put("data", payMap);
            return map;
        } else {
            return ReturnUtil.returnError("", 60000);
        }
    }

    public void payNotify(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Map<String, String> payNotify = CommonUtil.parseXml(req);
            String return_code = payNotify.get("return_code").toString();
            logger.info("====================支付通知返回状态码：{}", return_code);
            logger.info("====================支付通知返回信息：{}", payNotify.get("return_msg"));
            if ("SUCCESS".equals(return_code)) {
                logger.info("====================支付通知返回业务结果--支付是否成功--result_code：{}", payNotify.get("result_code"));
                String orderNo = payNotify.get("out_trade_no");
                logger.info("====================支付通知返回业务结果--商户订单编号--orderNo：{}", orderNo);
//                Order order = orderDao.getOrder(orderNo);
//                order.setPayStatus(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, Object> orderQuery(String orderNo) throws JDOMException, IOException {
        String nonce_str = CommonUtil.create_nonce_str(32);
        String sign = getOrderQuerySign(orderNo, nonce_str);
        String orderDetail = getOrderDetail(orderNo, nonce_str, sign);
        String result = CommonUtil.httpsRequest(Constants.query_order_url, "POST", orderDetail);
        // TODO
        Map<String, String> queryOrderMap = CommonUtil.doXMLParse(result);
        System.out.println(result);

//        Order order = orderDao.getOrder(orderNo);

        return null;

    }

    private String getOrderQuerySign(String orderNo, String nonce_str) {
        StringBuilder signs = new StringBuilder();
        signs.append("appid=").append(Constants.appid);
        signs.append("&mch_id=").append(Constants.mch_id);
        signs.append("&nonce_str=").append(nonce_str);
        signs.append("&out_trade_no=").append(orderNo);
        signs.append("&key=").append(Constants.api_key);
        String sign = MD5.md5(signs.toString());
        return sign;
    }

    private String getOrderDetail(String orderNo, String nonce_str, String sign) {
        SortedMap<String, Object> paramsMap = new TreeMap<String, Object>();
        paramsMap.put("appid", Constants.appid);
        paramsMap.put("mch_id", Constants.mch_id);
        paramsMap.put("nonce_str", nonce_str);
        paramsMap.put("out_trade_no", orderNo);
        paramsMap.put("sign", sign);
        String requestXML = CommonUtil.getRequestXml(paramsMap);
        return requestXML;
    }

}
