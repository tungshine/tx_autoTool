package com.tanglover.wechat;

import org.jdom.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tanglover.wechat.util.CommonUtil;
import com.tanglover.wechat.util.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@Controller
@RequestMapping(value = "pay")
public class WeiXinPayController {
    protected static Logger logger = LoggerFactory.getLogger(WeiXinPayController.class);

    String timeMillis = String.valueOf(System.currentTimeMillis() / 1000);

    /**
     * @param sn          订单号
     * @param totalAmount 支付金额
     * @param description 产品描述
     * @param request
     * @return
     */
    @RequestMapping(value = "/weixin/weixinPay/{sn}/{totalAmount}/{description}/{openId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public SortedMap<String, Object> ToPay(@PathVariable String sn, @PathVariable Integer totalAmount,
                                           @PathVariable String description, @PathVariable String openId, HttpServletRequest request) {
        Map<String, String> map = weixinPrePay(sn, totalAmount, description, openId, request);
        SortedMap<String, Object> finalpackage = new TreeMap<String, Object>();
        finalpackage.put("appId", Constants.appid);
        finalpackage.put("timeStamp", timeMillis);
        finalpackage.put("nonceStr", CommonUtil.create_nonce_str(32));
        finalpackage.put("package", "prepay_id=" + map.get("prepay_id"));
        finalpackage.put("signType", "MD5");
        // String sign = CommonUtil.(finalpackage);
        // finalpackage.put("paySign", sign);
        return finalpackage;
    }

    @SuppressWarnings("unchecked")
    public Map<String, String> weixinPrePay(String sn, Integer totalAmount, String description, String openid,
                                            HttpServletRequest request) {
        SortedMap<String, Object> parameterMap = new TreeMap<String, Object>();
        String nonce_str = CommonUtil.create_nonce_str(32);
        parameterMap.put("appid", Constants.appid);
//		parameterMap.put("attch", "ss");
        parameterMap.put("body", description);
        parameterMap.put("device_info", "WEB");
        parameterMap.put("mch_id", Constants.mch_id);
        parameterMap.put("nonce_str", nonce_str);
        parameterMap.put("notify_url", "http://manager.hm-vc.com/sflc-manager/pay/getNotify.htm");
        parameterMap.put("openid", openid);
        parameterMap.put("out_trade_no", sn);
        parameterMap.put("spbill_create_ip", CommonUtil.getIpAddr(request));
        parameterMap.put("total_fee", totalAmount);
        parameterMap.put("trade_type", "JSAPI");

        PayInfo p = new PayInfo(description, nonce_str, sn, totalAmount, CommonUtil.getIpAddr(request), "JSAPI",
                openid);
//		p.setAttch("ss");
        p.setTrade_type("JSAPI");
        String sign = CommonUtil.getSignature(p);
        parameterMap.put("sign", sign);
        String requestXML = CommonUtil.getRequestXml(parameterMap);
        System.out.println(requestXML);
        String result = CommonUtil.httpsRequest("https://api.mch.weixin.qq.com/pay/unifiedorder", "POST", requestXML);
        System.out.println(result);
        Map<String, String> map = null;
        try {
            map = CommonUtil.doXMLParse(result);
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * @param request
     * @param response
     */
    @RequestMapping(value = "getNotify")
    public void getNotify(HttpServletRequest request, HttpServletResponse response) {

    }

}
