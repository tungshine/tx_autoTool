package wechat.util;


import encryption.MD5;
import org.dom4j.io.SAXReader;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tool.DateUtils;
import wechat.PayInfo;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.*;
import java.util.*;

public class CommonUtil {

    static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

    /**
     * 获取签名
     *
     * @param p
     * @return
     * @throws Exception
     */
    public static String getSignature(PayInfo p) {

        StringBuilder sb = new StringBuilder();
        sb.append("appid=" + p.getAppid());
        // sb.append("&attch=" + p.getAttch());
        sb.append("&body=" + p.getBody());
        sb.append("&device_info=" + p.getDevice_info());
        sb.append("&mch_id=" + p.getMch_id());
        sb.append("&nonce_str=" + p.getNonce_str());
        sb.append("&notify_url=" + p.getNotify_url());
        sb.append("&openid=" + p.getOpenid());
        sb.append("&out_trade_no=" + p.getOut_trade_no());
        sb.append("&spbill_create_ip=" + p.getSpbill_create_ip());
        sb.append("&total_fee=" + p.getTotal_fee());
        sb.append("&trade_type=" + p.getTrade_type());
        String signTemp = sb.toString() + "&key=" + Constants.api_key;
        return MD5.md5(signTemp);

    }

    public static String signature(SortedMap<String, Object> signMap) {
        StringBuilder sb = new StringBuilder();
        sb.append("appid=" + signMap.get("appid"));
        if (null != signMap.get("attch")) {
            sb.append("&attach=").append(signMap.get("attch"));
        }
        sb.append("&body=" + signMap.get("body"));
        sb.append("&device_info=" + signMap.get("device_info"));
        sb.append("&mch_id=" + signMap.get("mch_id"));
        sb.append("&nonce_str=" + signMap.get("nonce_str"));
        sb.append("&notify_url=" + signMap.get("notify_url"));
        sb.append("&openid=" + signMap.get("openid"));
        sb.append("&out_trade_no=" + signMap.get("out_trade_no"));
        sb.append("&spbill_create_ip=" + signMap.get("spbill_create_ip"));
        sb.append("&total_fee=" + (int) signMap.get("total_fee"));
        sb.append("&trade_type=" + signMap.get("trade_type"));
        String signTemp = sb.toString() + "&key=" + Constants.api_key;
        return MD5.md5(signTemp);
    }

    /**
     * 获取IP地址
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            return request.getRemoteAddr();
        }
        byte[] ipAddr = addr.getAddress();
        String ipAddrStr = "";
        for (int i = 0; i < ipAddr.length; i++) {
            if (i > 0) {
                ipAddrStr += ".";
            }
            ipAddrStr += ipAddr[i] & 0xFF;
        }
        return ipAddrStr;
    }

    /**
     * 生成随机字符串
     *
     * @param digit:生成多少位
     * @return
     */
    public static String create_nonce_str(int digit) {
        String base = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < digit; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString().toUpperCase();
    }

    // 请求xml组装
    public static String getRequestXml(SortedMap<String, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String) entry.getKey();
            Object value = entry.getValue();
            if ("attach".equalsIgnoreCase(key) || "body".equalsIgnoreCase(key) || "sign".equalsIgnoreCase(key)) {
                sb.append("<" + key + ">" + "<![CDATA[" + value + "]]></" + key + ">");
            } else {
                sb.append("<" + key + ">" + value + "</" + key + ">");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }

    public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        try {

            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            return buffer.toString();
        } catch (ConnectException ce) {
            System.out.println("连接超时：{}" + ce);
        } catch (Exception e) {
            System.out.println("https请求异常：{}" + e);
        }
        return null;
    }

    /**
     * xml解析
     *
     * @param strxml
     * @return
     * @throws JDOMException
     * @throws IOException
     */
    public static Map doXMLParse(String strxml) throws JDOMException, IOException {
        strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");

        if (null == strxml || "".equals(strxml)) {
            return null;
        }

        Map m = new HashMap();

        InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(in);
        Element root = doc.getRootElement();
        List list = root.getChildren();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Element e = (Element) it.next();
            String k = e.getName();
            String v = "";
            List children = e.getChildren();
            if (children.isEmpty()) {
                v = e.getTextNormalize();
            } else {
                v = getChildrenText(children);
            }

            m.put(k, v);
        }

        // 关闭流
        in.close();

        return m;
    }

    public static String getChildrenText(List children) {
        StringBuffer sb = new StringBuffer();
        if (!children.isEmpty()) {
            Iterator it = children.iterator();
            while (it.hasNext()) {
                Element e = (Element) it.next();
                String name = e.getName();
                String value = e.getTextNormalize();
                List list = e.getChildren();
                sb.append("<" + name + ">");
                if (!list.isEmpty()) {
                    sb.append(getChildrenText(list));
                }
                sb.append(value);
                sb.append("</" + name + ">");
            }
        }

        return sb.toString();
    }

    /**
     * 通过dom4j解析request
     *
     * @param request
     * @return
     * @throws Exception
     */
    public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<String, String>();
        // 从request中取得输入流
        InputStream inputStream = request.getInputStream();
        // 读取输入流
        SAXReader reader = new SAXReader();
        org.dom4j.Document document = reader.read(inputStream);
        // 得到xml根元素
        org.dom4j.Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<org.dom4j.Element> elementList = root.elements();
        // 遍历所有子节点
        for (org.dom4j.Element e : elementList)
            map.put(e.getName(), e.getText());
        // 释放资源
        inputStream.close();
        inputStream = null;
        return map;
    }

    public static void main(String[] args) {
        PayInfo payInfo = new PayInfo();
        payInfo.setAppid(Constants.appid);
        payInfo.setDevice_info("WEB");
        payInfo.setMch_id(Constants.mch_id);
        payInfo.setNonce_str(create_nonce_str(32).replace("-", ""));
        payInfo.setBody("这里是某某白米饭的body");
        payInfo.setAttch("adddd2512");
        payInfo.setOut_trade_no("ssdss".concat(DateUtils.formatShortDateTime(new Date())));
        payInfo.setTotal_fee(1000);
        payInfo.setSpbill_create_ip("192.168.1.1");
        payInfo.setNotify_url(Constants.notify_url);
        payInfo.setTrade_type("JSAPI");
        payInfo.setOpenid("sdfsdfsd");
        System.out.println("nonce : " + create_nonce_str(32));
    }
}
