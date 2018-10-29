package wechat;//package com.shifenkafei.sflc.wechat;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.jdom.Document;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//public class Pay {
//	@ResponseBody
//	@RequestMapping("/weixin")
//	public Map<String, Object> weixin(HttpServletRequest request, HttpServletResponse response,Long id,String appid,String openid) throws IOException {
//
//	    Map<String, Object> r = new HashMap<>();
//
//	    String key = PropertyConfigurer.getValue(Constants.ENCRYPTION_KEY);
////	    String appid = (String) request.getSession().getAttribute("appid");
////	    String openid = (String) request.getSession().getAttribute("openid");
//	    appid = AESCoder.decrypt(appid, key);
//	    openid = AESCoder.decrypt(openid,key);
//
//	    //获取订单信息
////	    Long id = (Long) request.getSession().getAttribute("id");
//	    MallOrder mo = pService.selectOrderById(id);
//	    double money = ((mo.getMoney()+mo.getPostage())*100);
//	    int moneys = (int)money;
//	    //获取公众号绑定的支付信息
//	    WeixinPay wp = WeixinPayServers.getParam(appid, 1);
//	    if(wp == null){
//	        r.put("code", 0);
//	        r.put("msg", "未获取到支付信息！");
//	        return r;
//	    }
//
//	    //获取支付通知地址
//	    String notifyUrl = PropertyConfigurer.getValue("notify_url");
//	    //微信支付统一预下单
//	    Map<String, Object> preResult = PayUtil.preOrder(wp, "JIOEWGEW", mo.getProduct(), notifyUrl, openid,moneys,mo.getOrderNum());
//
//	    //生成微信支付签名
//	    if((Integer)preResult.get("code") != 1){
//	        log.error("==================预下单失败=============preResult："+preResult);
//	        return preResult;
//	    }
//
//	    String timeStamp = String.valueOf(calLastedTime(new Date().getTime()));
//	    String nonceStr = getNonceStr();
//
//	    StringBuffer signs = new StringBuffer();
//	    signs.append("appId=").append(appid);
//	    signs.append("&nonceStr=").append(nonceStr);
//	    signs.append("&package=").append("prepay_id=").append((String)preResult.get("prepay_id"));
//	    signs.append("&signType=").append("MD5");
//	    signs.append("&timeStamp=").append(timeStamp).append("&key=").append(wp.getCertificateKey());
//
//	    log.debug("================Pre success signs:"+signs);
//
//	    String sign = null;
//	    try {
//	        sign = PayUtil.md5(signs.toString());
//	    } catch (Exception e) {
//	        log.error("===============================系统错误，签名失败！", e);
//	        r.put("code", -1);
//	        r.put("msg", "系统错误，签名失败！");
//	        return r;
//	    }
//
//	    if(sign == null){
//	        r.put("code", -2);
//	        r.put("msg", "系统错误，签名失败！");
//	        return r;
//	    }
//
//	    log.debug("================Pre success sign:"+sign);
//
//	    r.put("appId", appid);
//	    r.put("timeStamp", timeStamp);
//	    r.put("nonceStr", nonceStr);
//	    r.put("packages", "prepay_id="+((String)preResult.get("prepay_id")));
//	    r.put("signType", "MD5");
//	    r.put("paySign", sign);
//	    r.put("code", 1);
//
//	    log.debug("================Pre success result:"+r);
//
//	    return r;
//	}
//
//
//
//	/**
//	 * 统一下单部分：
//	 * @param wp
//	 * @param attach
//	 * @param body
//	 * @param notify_url
//	 * @param openid
//	 * @param total_fee
//	 * @param out_trade_no
//	 * @return
//	 */
//	public static Map<String, Object> preOrder(WeixinPay wp, String attach, String body, String notify_url, String openid, Integer total_fee, String out_trade_no) {
//	new HashMap();
//	Map r = prePay(wp, attach, body, notify_url, openid, total_fee, out_trade_no);
//	if(r == null) {
//	    log.error("-2201 系统错误，预下单失败！");
//	    HashMap r1 = new HashMap();
//	    r1.put("code", Integer.valueOf(-1));
//	    r1.put("msg", "系统错误，预下单失败!");
//	    return r1;
//	} else if(((Integer)r.get("code")).intValue() != 1) {
//	    return r;
//	} else {
//	    Document _d = (Document)r.get("data");
//
//	    try {
//	        return analysisWXXml(_d);
//	    } catch (Exception var10) {
//	        log.error("解析XML失败！", var10);
//	        r.put("code", Integer.valueOf(-10));
//	        r.put("msg", "系统错误，预下单失败!");
//	        return r;
//	    }
//	}
//	}
//
//	private static Map<String, Object> prePay(WeixinPay wp, String attach, String body, String notify_url, String openid, Integer total_fee, String out_trade_no) {
//	HashMap r = new HashMap();
//	String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
//	String nonce_str = getNonceStr();
//	StringBuffer signs = new StringBuffer();
//	signs.append("appid=").append(wp.getAppid());
//	if(!StringUtil.isNull(attach)) {
//	    signs.append("&attach=").append(attach);
//	}
//
//	signs.append("&body=").append(body).append("&mch_id=").append(wp.getMerchantNumber());
//	signs.append("&nonce_str=").append(nonce_str).append("&notify_url=").append(notify_url).append("&openid=").append(openid);
//	signs.append("&out_trade_no=").append(out_trade_no).append("&spbill_create_ip=").append(Util.spbill_create_ip);
//	signs.append("&total_fee=").append(total_fee).append("&trade_type=").append("JSAPI");
//	signs.append("&key=").append(wp.getCertificateKey());
//	log.debug("===============================signs:" + signs);
//	String sign = null;
//
//	try {
//	    sign = md5(signs.toString());
//	} catch (Exception var18) {
//	    log.error("===============================系统错误，签名失败！", var18);
//	    r.put("code", Integer.valueOf(-1));
//	    r.put("msg", "系统错误，签名失败！");
//	    return r;
//	}
//
//	if(sign == null) {
//	    r.put("code", Integer.valueOf(-2));
//	    r.put("msg", "系统错误，签名失败！");
//	    return r;
//	} else {
//	    log.debug("===============================sign:" + sign);
//	    StringBuffer xml = new StringBuffer();
//	    xml.append("<xml>");
//	    xml.append("<appid>").append(wp.getAppid()).append("</appid>");
//	    if(!StringUtil.isNull(attach)) {
//	        xml.append("<attach>").append(attach).append("</attach>");
//	    }
//
//	    xml.append("<body>").append(body).append("</body>");
//	    xml.append("<mch_id>").append(wp.getMerchantNumber()).append("</mch_id>");
//	    xml.append("<nonce_str>").append(nonce_str).append("</nonce_str>");
//	    xml.append("<notify_url>").append(notify_url).append("</notify_url>");
//	    xml.append("<openid>").append(openid).append("</openid>");
//	    xml.append("<out_trade_no>").append(out_trade_no).append("</out_trade_no>");
//	    xml.append("<spbill_create_ip>").append(Util.spbill_create_ip).append("</spbill_create_ip>");
//	    xml.append("<total_fee>").append(total_fee).append("</total_fee>");
//	    xml.append("<trade_type>").append("JSAPI").append("</trade_type>");
//	    xml.append("<sign>").append(sign).append("</sign>");
//	    xml.append("</xml>");
//	    log.debug("===============================xml:" + xml.toString());
//	    if(Util.pay_interface_type.equals(WeiXInInterfaceTypeEnum.RMI.value)) {
//	        WeiXinService weixinPayResult1 = (WeiXinService)WeixinApplicationHelper.getBean("weiXinServicellper");
//	        String e1 = (String)weixinPayResult1.weixinPay(url, xml.toString());
//	        if(StringUtil.isNull(e1)) {
//	            r.put("code", Integer.valueOf(-2));
//	            r.put("msg", "系统错误，调取微信接口失败！");
//	            return r;
//	        } else {
//	            log.debug("================= weixin pay by rmi brtbeacon interface result:" + e1);
//
//	            try {
//	                r.put("code", Integer.valueOf(1));
//	                r.put("data", XmlUtil.createDocumentByString(e1));
//	                return r;
//	            } catch (Exception var16) {
//	                log.error("===============微信支付预下单失败：weixinResult:" + e1, var16);
//	                r.put("code", Integer.valueOf(-8));
//	                r.put("msg", "系统错误，调取微信接口失败！");
//	                return r;
//	            }
//	        }
//	    } else {
//	        String weixinPayResult = null;
//
//	        try {
//	            weixinPayResult = HttpSendHellper.sendPost(Util.pay_interface_url, "url=" + URLEncoder.encode(url, "utf-8") + "&param=" + URLEncoder.encode(xml.toString(), "utf-8"));
//	            log.debug("================= weixin pay by http brtbeacon interface result:" + weixinPayResult);
//	            if(StringUtil.isNull(weixinPayResult)) {
//	                r.put("code", Integer.valueOf(-2));
//	                r.put("msg", "系统错误，调取微信接口失败！");
//	                return r;
//	            } else {
//	                JSONObject e = new JSONObject(weixinPayResult);
//	                if(e.getInt("code") != 1) {
//	                    r.put("code", Integer.valueOf(e.getInt("code")));
//	                    r.put("msg", e.getString("msg"));
//	                    return r;
//	                } else {
//	                    r.put("code", Integer.valueOf(1));
//	                    r.put("data", XmlUtil.createDocumentByString(e.getString("data")));
//	                    return r;
//	                }
//	            }
//	        } catch (Exception var17) {
//	            log.error("===============微信支付预下单失败：weixinResult:" + weixinPayResult, var17);
//	            r.put("code", Integer.valueOf(-8));
//	            r.put("msg", "系统错误，调取微信接口失败！");
//	            return r;
//	        }
//	    }
//	}
//	}
//}
