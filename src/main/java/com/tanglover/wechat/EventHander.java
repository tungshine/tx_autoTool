package com.tanglover.wechat;

import com.tanglover.wechat.resp.PicUrlInfo;
import com.tanglover.wechat.resp.Article;
import com.tanglover.wechat.resp.NewsRespMessage;
import com.tanglover.wechat.resp.RespMessageEnum;
import com.tanglover.wechat.util.Constants;
import com.tanglover.wechat.util.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: TangXu
 * @date: 2018/10/29 19:46
 * @description: 微信服务号 菜单处理类
 */
public class EventHander {

    protected static Logger logger = LoggerFactory.getLogger("service");

    public static String processSinglePicText(String toUserName, String fromUserName, String eventKey) {

        // TODO 根据key获取不同的信息
        logger.info("processSinglePicText({},{}) begin:", toUserName, fromUserName);
        NewsRespMessage newsMessage = new NewsRespMessage();
        newsMessage.setToUserName(toUserName);
        newsMessage.setFromUserName(fromUserName);
        newsMessage.setCreateTime(new Date().getTime());
        newsMessage.setMsgType(RespMessageEnum.NEWS.getValue());
        newsMessage.setFuncFlag(0);

        List<Article> articleList = new ArrayList<Article>();
        Article article = new Article();
        PicUrlInfo info = getPicUrlInfoByKey(eventKey);
        article.setTitle(info.getTitle());
        article.setDescription(info.getInfo());
        article.setPicUrl(info.getPic());
        article.setUrl(info.getUrl());
        articleList.add(article);
        // 设置图文消息个数
        newsMessage.setArticleCount(articleList.size());
        // 设置图文消息包含的图文集合
        newsMessage.setArticles(articleList);
        // 将图文消息对象转换成xml字符串
        String result = MessageUtil.newsMessageToXml(newsMessage);
        logger.info("processSinglePicText({},{}) end\n{}:", toUserName, fromUserName, result);
        return result;
    }

    private static PicUrlInfo getPicUrlInfoByKey(String key) {

        PicUrlInfo tmp = new PicUrlInfo();
        if (key.equalsIgnoreCase("12")) { // 合同下载
            tmp.setTitle("合同下载 ");
            tmp.setPic(Constants.STATIC_WECHAT + "/imgs/download.png");
            tmp.setUrl(Constants.SERVER_WECHAT + "/wechatFileTypeList.do");
            tmp.setInfo("买卖合同范本\n担保合同范本\n投资融资合同\n招标投标合同\n劳动合同范本\n经营合同范本\n建设工程合同\n");
            return tmp;
        } else if (key.equalsIgnoreCase("13")) {// 合同代写
            tmp.setTitle("合同代写 ");
            tmp.setPic(Constants.STATIC_WECHAT + "/imgs/ghostWriting.png");
            tmp.setUrl(Constants.STATIC_WECHAT + "/htdaixie.html");
            tmp.setInfo(
                    "红帽合同代写优势\n红帽法律卫士提供优质高效的合同代写服务,由专业合同律师团队服务,经过严谨规范的服务标准,保证最终的合同质量;完整的服务记录。量身定制化合同代写，充分保障您的合法权益。");
            return tmp;
        } else if (key.equalsIgnoreCase("14")) {// 合同审查
            tmp.setTitle("合同审查 ");
            tmp.setPic(Constants.STATIC_WECHAT + "/imgs/review.png");
            tmp.setUrl(Constants.STATIC_WECHAT + "/htshencha.html");
            tmp.setInfo("红帽合同审查优势\n\n红帽法律卫士提供优质高效的合同审查服务,由专业合同律师团队服务,经过严谨规范的服务标准,保证合同审核准确无误。免费为您保驾护航，排除法律隐患");
            return tmp;
        } else if (key.equalsIgnoreCase("31")) {// 创意活动
            tmp.setTitle("创意活动 ");
            tmp.setPic(Constants.STATIC_WECHAT + "/imgs/creation.png");
            tmp.setUrl(Constants.STATIC_WECHAT + "/chuanyihuodong.html");
            tmp.setInfo("红帽感恩季活动现已启动\n现注册账号，即可免费获送限量红帽法律卫士终身VIP金卡一张！");
            return tmp;
        } else if (key.equalsIgnoreCase("32")) {// 服务介绍
            tmp.setTitle("服务介绍");
            tmp.setPic(Constants.STATIC_WECHAT + "/imgs/introduction.png");
            tmp.setUrl(Constants.STATIC_WECHAT + "/fuwujieshao.html");
            tmp.setInfo(
                    "免费\n革命性法律服务模式，致力于让每位用户和企业都免费获得贴身的法律保护\n\n生态圈\n以革命性的免费优质服务接入大量用户需求，再通过免费为律师提供案源及用户关系管理工具吸引大量律师入驻平台并免费向用户提供法律基础服务，从而形成法律服务生态循环");
            return tmp;
        }
        return null;

    }
}