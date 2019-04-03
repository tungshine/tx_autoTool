package com.tanglover.wechat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: TangXu
 * @date: 2018/10/29 20:25
 * @description: 微信接口
 */
@Controller
public class PayController {

    static final Logger logger = LoggerFactory.getLogger(PayController.class);

    @RequestMapping(value = "index")
    public String index() {
        return "index";
    }

    /**
     * 微信浏览器授权登录回掉地址
     *
     * @param code
     * @param state
     * @param req
     * @return
     */
    @RequestMapping(value = "getAuthCode")
    public String getCode(@RequestParam(value = "code", required = false) String code,
                          @RequestParam(value = "state", required = false) String state, HttpServletRequest req) {

        try {
            logger.info("code is : ------" + code);
            logger.info("state is : ------" + state);
//            String weChatUserInfo = payService.getAccessToken(code, state, req);
//            req.getSession().setAttribute("weChatUserInfo", weChatUserInfo);
            return "weChatInfo";
        } catch (Exception e) {
            logger.error(e.toString());
            return "error";
        }

    }

    /**
     * 统一下单
     *
     * @param openid
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "/com/tanglover/wechat/uniformOrder")
    public Map<String, Object> uniformOrder(@RequestParam(value = "orderNo") String orderNo,
                                            @RequestParam(value = "openid") String openid, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
//        try {
//            map = payService.uniformOrder(orderNo, openid, request);
//        } catch (JDOMException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return map;
    }

    /**
     * 微信支付回调
     *
     * @param req
     * @param resp
     */
    @RequestMapping(value = "payNotify")
    public void payNotify(HttpServletRequest req, HttpServletResponse resp) {
//        payService.payNotify(req, resp);
    }

    /**
     * 微信查询订单支付情况
     *
     * @param orderNo
     */
    @ResponseBody
    @RequestMapping(params = "orderQuery")
    public void orderQuery(@RequestParam(value = "orderNo") String orderNo) {
//        try {
//            Map<String, Object> map = payService.orderQuery(orderNo);
//            // TODO
//        } catch (JDOMException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * web前端调起支付接口之后，修改自己订单的支付状态
     *
     * @param orderNo
     * @param payStatus
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "updateOrderPayStatus")
    public Map<String, Object> updateOrderPayStatus(@RequestParam(value = "orderNo") String orderNo,
                                                    @RequestParam(value = "payStatus") Integer payStatus) {
//        return orderService.updateOrderPayStatus(orderNo, payStatus);
        return null;
    }

    /**
     * 微信端获取订单详情
     *
     * @param orderNo
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "orderDetail")
    public Map<String, Object> orderDetail(
            @RequestParam(value = "orderNo") String orderNo) {
//        return orderService.orderDetail(orderNo);
        return null;
    }


}
