package com.tanglover.wechat.req;

/**
 * @author: TangXu
 * @date: 2018/10/29 19:10
 * @description: 请求消息枚举类型
 */
public enum ReqMessageEnum {

    // 返回消息类型：文本 图片 链接 地理 音频 推送
    TEXT("text"), IMAGE("image"), LINK("link"), LOCATION("location"), VOICE(
            "voice"), EVENT("event");

    private ReqMessageEnum(String name) {
        this.name = name;
    }

    public String toString() {
        return "ReqMessageEnum:" + this.name;
    }

    public String getValue() {
        return name;
    }

    private String name;

}
