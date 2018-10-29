package wechat.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author: TangXu
 * @date: 2018/10/29 19:39
 * @description: 发起HTTP请求
 */
public class HTTPUtil {

    public static void main(String[] args) {

    }

    /**
     * 发起一次http连接 method = get http(这里用一句话描述这个方法的作用)
     *
     * @param url void
     * @throws IOException
     * @throws @since      1.0.0
     */
    public static String httpByGet(String url) throws IOException {
        // log.debug("访问地址：" + url);
        URL _u = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) _u.openConnection();
        connection.connect();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
        String lines;
        String data = "";
        while ((lines = reader.readLine()) != null) {
            data += lines;
        }

        // log.debug("收到访问返回数据：" + data);

        reader.close();
        connection.disconnect();
        return data;
    }

}
