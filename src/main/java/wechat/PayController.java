package wechat;

import com.shifenkafei.sflc.business.service.OrderService;
import com.shifenkafei.sflc.wechat.service.PayServiceImpl;
import org.jdom.JDOMException;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.shifenkafei.sflc.util.LogUtil.CONTROLLERLOG;

/**
 * 微信接口
 *
 * @author TungShine 2016年9月20日
 */
@Controller
@RequestMapping(value = "pay")
public class PayController {

    protected static Logger logger = CONTROLLERLOG;

    @Resource
    private PayServiceImpl payService;

    @Resource
    private OrderService orderService;

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
            String weChatUserInfo = payService.getAccessToken(code, state, req);
            req.getSession().setAttribute("weChatUserInfo", weChatUserInfo);
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
    @RequestMapping(params = "uniformOrder")
    public Map<String, Object> uniformOrder(@RequestParam(value = "orderNo") String orderNo,
                                            @RequestParam(value = "openid") String openid, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map = payService.uniformOrder(orderNo, openid, request);
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        payService.payNotify(req, resp);
    }

    /**
     * 微信查询订单支付情况
     *
     * @param orderNo
     */
    @ResponseBody
    @RequestMapping(params = "orderQuery")
    public void orderQuery(@RequestParam(value = "orderNo") String orderNo) {
        try {
            @SuppressWarnings("unused")
            Map<String, Object> map = payService.orderQuery(orderNo);
            // TODO
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        return orderService.updateOrderPayStatus(orderNo, payStatus);
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
        return orderService.orderDetail(orderNo);
    }


}
