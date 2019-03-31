package wechat.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wechat.util.baidu.SnCal;

import java.io.IOException;
import java.util.*;

/**
 * @author TangXu
 * @create 2019-02-12 10:35
 * @description:
 */
public class IPAddressUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(IPAddressUtil.class);
    private static final String AK = "DE0e0d0e9796e54756dff1fecd7ca766";
    private static final String BAIDU_URL = "http://api.map.baidu.com/location/ip";


    public static void main(String[] args) throws IOException {

        String ip = "47.102.155.222, 10.42.1.0";
        String addressWithBaidu = getAddressWithBaidu(ip);
        System.out.println(addressWithBaidu);
    }

    public static String extractString(String s, String charAt, int offset) {
        String returnString = "";
        for (int i = 0; i < offset; i++) {
            returnString = s.substring(0, s.indexOf(charAt) + 1);
            System.out.println(returnString);
        }
        return s;
    }

    /**
     * @author: TangXu
     * @date: 2019/2/12 11:25
     * @description: 通过百度地图API查询地址
     * @param: [ip]
     */
    public static String getAddressWithBaidu(String ip) {
        if (ip == null || ip.length() == 0) {
            return "";
        }
        String[] ips = null;
        try {
            ips = ip.split(", ");
        } catch (Exception e) {
            LOGGER.error("ip转换异常", e);
            return "";
        }
        Map params = new HashMap();
        params.put("ip", ips[0]);
        params.put("ak", AK);
        params.put("coor", "bd09ll");
        LinkedHashMap paramMap = new LinkedHashMap();
        paramMap.put("ip", ips[0]);
        paramMap.put("ak", "DE0e0d0e9796e54756dff1fecd7ca766");
        String sn = SnCal.sn(paramMap);
        params.put("sn", sn);
        JSONObject jsonObject = request(ips[0], sn);
        if (null != jsonObject && jsonObject.containsKey("address")) {
            return jsonObject.get("address").toString();
        } else {
            return "";
        }
    }

    public static JSONObject request(String ip, String sn) {
        try {
            HttpClient client = HttpClients.createDefault();// 创建默认http连接
            HttpPost post = new HttpPost(BAIDU_URL);// 创建一个post请求
            List<NameValuePair> paramList = new ArrayList<NameValuePair>();
            paramList.add(new BasicNameValuePair("ip", ip));//传递的参数
            paramList.add(new BasicNameValuePair("ak", AK));//传递的参数
            paramList.add(new BasicNameValuePair("sn", sn));//传递的参数
            paramList.add(new BasicNameValuePair("coor", "bd09ll"));//传递的参数
            // 把参转码后放入请求实体中
            HttpEntity httpEntity = new UrlEncodedFormEntity(paramList, "utf-8");
            post.setEntity(httpEntity);// 把请求实体放post请求中
            HttpResponse response = client.execute(post);// 用http连接去执行get请求并且获得http响应
            HttpEntity responseEntity = response.getEntity();// 从response中取到响实体
            String responseBody = EntityUtils.toString(responseEntity);// 把响应实体转成文本
            responseBody = responseBody.replace("CN", "中国");
            return JSONObject.parseObject(responseBody);
        } catch (Exception e) {
            LOGGER.error("查询异常{}", e.getMessage());
            return null;
        }
    }
}