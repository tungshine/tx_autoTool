package wechat.service;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom.JDOMException;

public interface PayService {
	Map<String, Object> uniformOrder(Integer userId, Integer orderId, String openid, Integer quantity,
                                     HttpServletRequest request) throws JDOMException, IOException;
	
	void payNotify(HttpServletRequest req, HttpServletResponse resp);
}
