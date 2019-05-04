package com.tanglover.barrage;

import com.yycdev.douyu.sdk.DouYuClient;
import com.yycdev.douyu.sdk.MessageListener;
import com.yycdev.douyu.sdk.entity.ChatMsg;

/**
 * @author TangXu
 * @create 2019-05-04 19:09
 * @description:
 */
public class BarrageTest {


    public static void main(String[] args) throws InterruptedException {
        DouYuClient client = new DouYuClient("openbarrage.douyutv.com", 8601, "1693612");
        client.registerMessageListener(new MessageListener<ChatMsg>() {
            @Override
            public void read(ChatMsg message) {
                System.out.println(message.toChatStr());
            }
        });
        client.registerMessageListener(new MessageListener<String>() {
            @Override
            public void read(String message) {
                System.out.println(message);
            }
        });
        DouYuClient login = client.login();
        DouYuClient sync = client.sync();
        Thread.sleep(5000);
        client.exit();
    }
}