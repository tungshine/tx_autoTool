package com.tanglover.wechat.req;

/**
 * @author: TangXu
 * @date: 2018/10/29 19:09
 * @description: 事件枚举类型
 */
public enum ReqEventEnum {

    // 返回消息类型: 订阅 取消订阅 自定义菜单点击事件
    SUBSCRIBE("subscribe"), UNSUBSCRIBE("unsubscribe"), CLICK("click");

    private ReqEventEnum(String name) {
        this.name = name;
    }

    public String toString() {
        return "ReqEventEnum:" + this.name;
    }

    public String getValue() {
        return name;
    }

    public static ReqEventEnum getReqEventEnum(String type) {
        for (ReqEventEnum tmp : ReqEventEnum.values()) {
            if (type.equalsIgnoreCase(tmp.name)) {
                return tmp;
            }
        }

        throw new IllegalArgumentException("have not get match ReqEventEnum for input:" + type);
    }

    private String name;

}
