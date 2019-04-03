package com.tanglover.wechat.po;

/**
 * @author 不言
 * @create 2018-10-30 11:40
 * @description:
 */
public class Unifiedorder {
    private String appid; // 公众账号ID
    private String mch_id; // 商户号
    private String device_info; // 设备号
    private String nonce_str;// 随机字符串
    private String sign; // 签名
    private String sign_type; // 签名类型
    private String body; // 商品描述
    private String detail; // 商品详情
    private String attach; // 附加数据
}