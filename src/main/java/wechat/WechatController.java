package wechat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wechat.service.CoreService;
import wechat.util.SignUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@Controller
@RequestMapping("wechat")
public class WechatController {

    protected static Logger logger = LoggerFactory.getLogger("controller");
    @Autowired
    private CoreService cs;

    //	@RequestMapping(method = RequestMethod.GET)
    @RequestMapping(value = "test")
    public void doGet(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");

        logger.info("input parameter sig tim nonce echo:{}|{}|{}|{}", signature, timestamp, nonce, echostr);

        PrintWriter out = response.getWriter();
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            out.print(echostr);
            logger.info("validate success !!!!!");
        }
        out.close();
        out = null;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）  
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        logger.info("doPost begin invoke the processRequest>>>>>>");
        // 调用核心业务类接收消息、处理消息  
        String respMessage = cs.processRequest(request);
        logger.info("doPost end invoke the processRequest {}<<<<<<<<", respMessage);
        // 响应消息  
        PrintWriter out = response.getWriter();
        out.print(respMessage);
        out.close();
        out = null;
    }

}
