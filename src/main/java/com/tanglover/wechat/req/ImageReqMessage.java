package com.tanglover.wechat.req;

/**
 * 图片消息
 *
 * @author TangXu
 * @date 2014-05-19
 */
public class ImageReqMessage extends BaseReqMessage {
    // 图片链接
    private String PicUrl;

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }
}
