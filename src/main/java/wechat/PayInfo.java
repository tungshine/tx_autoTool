package wechat;

import wechat.util.Constants;

public class PayInfo {

    /*
     * 公众号ID
     */
    private String appid;
    /*
     * 商户ID
     */
    private String mch_id;
    /*
     * 设备号 PC网页或公众号内支付请传"WEB"
     */
    private String device_info;
    /*
     * 随机字符串
     */
    private String nonce_str;
    /*
     * 签名
     */
    private String sign;
    /*
     * 商品描述
     */
    private String body;
    /*
     * 附件数据
     */
    private String attch;
    /*
     * 商户订单号(内部订单编号)
     */
    private String out_trade_no;
    /*
     * 总金额
     */
    private int total_fee;
    /*
     * 终端IP
     */
    private String spbill_create_ip;
    /*
     * 通知地址
     */
    private String notify_url;
    /*
     * 交易类型
     */
    private String trade_type;
    /*
     * 用户标识
     */
    private String openid;

    public PayInfo() {
    }

    public PayInfo(String body, String nonce_str, String out_trade_no, int total_fee, String spbill_create_ip,
                   String trade_type, String openid) {
        this.appid = Constants.appid;
        this.mch_id = Constants.mch_id;
        this.device_info = "WEB";
        this.nonce_str = nonce_str;
        this.body = body;
        this.out_trade_no = out_trade_no;
        this.total_fee = total_fee;
        this.spbill_create_ip = spbill_create_ip;
        this.notify_url = Constants.notify_url;
        this.trade_type = trade_type;
        this.openid = openid;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAttch() {
        return attch;
    }

    public void setAttch(String attch) {
        this.attch = attch;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public int getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(int total_fee) {
        this.total_fee = total_fee;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

}
