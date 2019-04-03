package com.tanglover.wechat.service;

import com.tanglover.wechat.EventHander;
import com.tanglover.wechat.req.ReqEventEnum;
import com.tanglover.wechat.req.ReqMessageEnum;
import com.tanglover.wechat.resp.*;
import com.tanglover.wechat.util.Constants;
import com.tanglover.wechat.util.MessageUtil;
import com.tanglover.wechat.util.WeixinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 核心服务类
 *
 * @author TungShine 2016年9月20日
 */
@Service
@SuppressWarnings("unused")
public class CoreService {

    protected static Logger logger = LoggerFactory.getLogger("service");

    Map<String, Object> userMap = new HashMap<String, Object>();
    private static String url = "http://ceshi.10novo.com/sflc-manager/wechat/";

    /**
     * 处理微信发来的请求
     *
     * @param request
     * @return
     */
    public String processRequest(HttpServletRequest request) {
        logger.info("CoreService.processRequest(request)>>>>>>>>>>>>>>");
        String respMessage = null;
        try {
            // 默认返回的文本消息内容
            String respContent = "请求处理异常，请稍候尝试！";

            // xml请求解析
            Map<String, String> requestMap = MessageUtil.parseXml(request);

            // 发送方帐号（open_id）
            String fromUserName = requestMap.get("FromUserName");
            // 公众帐号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");

            // 回复文本消息
            TextRespMessage textMessage = new TextRespMessage();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(RespMessageEnum.TEXT.getValue());
            textMessage.setFuncFlag(0);

            logger.info("from:{} to {} msgtype {}", fromUserName, toUserName, msgType);

            // 文本消息
            if (msgType.equals(ReqMessageEnum.TEXT.getValue())) {
                // String result = processTextRequest(requestMap); // process
                // Text
                // Type Message
                logger.info("processRequest() process Text result:{}", request);
                return "";
            }
            // 图片消息
            if (msgType.equals(ReqMessageEnum.IMAGE.getValue())) {
                respContent = "您发送的是图片消息！";
            }
            // 地理位置消息
            else if (msgType.equals(ReqMessageEnum.LOCATION.getValue())) {
                respContent = "您发送的是地理位置消息！";
            }
            // 链接消息
            else if (msgType.equals(ReqMessageEnum.LINK.getValue())) {
                respContent = "您发送的是链接消息！";
            }
            // 音频消息
            else if (msgType.equals(ReqMessageEnum.VOICE.getValue())) {
                respContent = "您发送的是音频消息！";
            }
            // 事件推送
            else if (msgType.equals(ReqMessageEnum.EVENT.getValue())) {
                String result = processEvent(requestMap); // process EVENT
                // Type Message
                logger.info("processEVENTRequest() process Text result:{}", request);
                return result;
            }

            textMessage.setContent(respContent);
            respMessage = MessageUtil.textMessageToXml(textMessage);
            logger.info("send msg:{}", respContent);
        } catch (Exception e) {
            logger.error("processRequest() errror for process Content ", e);
            e.printStackTrace();
        }

        return respMessage;
    }

    private String processTextRequest(Map<String, String> requestMap) {
        String respMessage = null;
        try {
            // 发送方帐号（open_id）
            String fromUserName = requestMap.get("FromUserName");
            // 公众帐号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");

            if (msgType.equals(ReqMessageEnum.TEXT.getValue())) {
                String content = requestMap.get("Content");// 获取用户发送的文本内存
                logger.info("content:{}", content);
                // if(content.contains("+")&&getStrNum(content,"([+])")==3&&content.contains("公司")&&content.contains("@")){
                //// WechatUser
                // wu1=wechatService.loadWechatUserByOpenId(fromUserName);
                // String
                // jsonStr=Http.doGet(url+"wechatUserExist.json","userOpenId="+fromUserName
                // , "UTF-8", true);
                // Map<String,String> map=GsonUtil.getKeyMapByjson(jsonStr);
                // String msg=map.get("msg").toString();
                // if(!msg.equals("0")){
                // WechatUser wu = new WechatUser();
                // String[] list =content.split("\\+");
                // for (int i = 0; i < list.length; i++) {
                // String s = list[i];
                // if(isEmail(s)){//email
                // wu.setEmail(s);
                // s=null;
                // }else if(s.contains("公司")){//company
                // wu.setCompanyName(s);
                // s=null;
                // }else if(isPhone(s)){//phone
                // wu.setPhone(s);
                // }else{
                // wu.setQudao(s);
                // }
                // }
                // wu.setUserOpenId(fromUserName);
                // String
                // queryString="qudao="+wu.getQudao()+"&companyName="+wu.getCompanyName()+"&phone="+wu.getPhone()+"&userOpenId="+wu.getUserOpenId()+"&email="+wu.getEmail();
                // String flag=Http.doGet(url+"addWechatUser.json", queryString,
                // "UTF-8", true);
                // }
                // TextRespMessage textMessage = new TextRespMessage();
                // textMessage.setContent("请发送数字编号选择文书类别：\n 1.公司设立文书 \n
                // 2.公司劳资内部管理文书 \n 3.公司对外合同\\协议");
                // textMessage.setCreateTime(new Date().getTime());
                // textMessage.setFromUserName(toUserName);
                // textMessage.setToUserName(fromUserName);
                // textMessage.setFuncFlag(0);
                // textMessage.setMsgType(RespMessageEnum.TEXT.getValue());
                // respMessage= MessageUtil.textMessageToXml(textMessage);
                // if(!"".equals(content)&&null!=content){
                // NewsRespMessage news = new NewsRespMessage();
                // Article link=new Article();
                // link.setDescription("红帽法律卫士服务目录");
                // link.setTitle("红帽法律卫士");
                // link.setUrl("http://114.215.236.27:8080/hmfl/wechat/weChatService.htm");
                // link.setPicUrl("http://server.hongmaofalv.com/wechat/imgs/ghostWriting.png");
                // List<Article> list = new ArrayList<Article>();
                // list.add(link);
                // news.setArticleCount(1);
                // news.setArticles(list);
                // news.setCreateTime(new Date().getTime());
                // news.setFromUserName(toUserName);
                // news.setToUserName(fromUserName);
                // news.setFuncFlag(0);
                // news.setMsgType(RespMessageEnum.NEWS.getValue());
                // respMessage=MessageUtil.newsMessageToXml(news);
                // }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respMessage;
    }

    private static int getStrNum(String str, String regx) {
        int i = 0;
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            i++;
        }
        return i;
    }

    public static boolean isPhone(String phone) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    public static boolean isEmail(String email) {
        if (null == email || "".equals(email))
            return false;
        // Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");// 复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * emoji表情转换(hex -> utf-16)
     *
     * @param hexEmoji
     * @return
     */
    public static String emoji(int hexEmoji) {
        return String.valueOf(Character.toChars(hexEmoji));
    }

    public static void main(String[] args) {
        // 调用接口获取access_token
        AccessToken at = WeixinUtil.getAccessToken(WeixinUtil.AppId, WeixinUtil.AppSecret);

        if (null != at) {
            // 调用接口创建菜单
            int result = WeixinUtil.createMenu(getMenu(), at.getAccess_token());


//			WeixinUtil.deleteMenu();
            // 判断菜单创建结果
            if (0 == result)
                logger.info("菜单创建成功！");
            else
                logger.error("菜单创建失败，错误码：{}", result);
        }
    }

    public static UserInfo getUserByOpenId(String openId) {
        return null;
    }

    /**
     * 组装菜单数据
     *
     * @return
     */
    private static Menu getMenu() {
        // ViewButton btn11 = new ViewButton();
        // btn11.setName("常见法律问题");
        // btn11.setUrl(ConstantUtil.SERVER_WECHAT +
        // "/wechatToQuestionPage.do"); // 常见问题
        //
        // CommonButton btn12 = new CommonButton();
        // btn12.setName("合同下载");
        // btn12.setKey("12");
        //
        // CommonButton btn13 = new CommonButton();
        // btn13.setName("合同代写");
        // btn13.setKey("13");
        //
        // CommonButton btn14 = new CommonButton();
        // btn14.setName("合同审查");
        // btn14.setKey("14");
        //
        // ViewButton btn21 = new ViewButton();
        // btn21.setName("咨询律师");
        // btn21.setUrl(ConstantUtil.SERVER_WECHAT + "/wechatFindlayer.do"); //
        // 律师列表
        //
        // CommonButton btn22 = new CommonButton();
        // btn22.setName("电话咨询");
        // btn22.setKey("22");
        //
        // CommonButton btn23 = new CommonButton();
        // btn23.setName("在线咨询");
        // btn23.setKey("23");
        //
        // CommonButton btn31 = new CommonButton();
        // btn31.setName("创意活动");
        // btn31.setKey("31");
        //
        // CommonButton btn32 = new CommonButton();
        // btn32.setName("服务介绍");
        // btn32.setKey("32");

        // CommonButton btn33 = new CommonButton();
        // btn33.setName("激活VIP");
        // btn33.setKey("33");

        /*
         * ViewButton btn34 = new ViewButton(); btn34.setName("APP");
         * //btn34.setUrl("http://www.hatsafe.com/"); // App下载页
         * btn34.setUrl("http://114.215.236.27:8080/wechat/appDownload.html");
         */
        // CommonButton btn35 = new CommonButton();
        // btn35.setName("ios下载");
        // btn35.setKey("35");
        // CommonButton btn36 = new CommonButton();
        // btn36.setName("安卓下载");
        // btn36.setKey("36");

        // ComplexButton mainBtn1 = new ComplexButton();
        // mainBtn1.setName("法律服务");
        // mainBtn1.addButton(btn11).addButton(btn12).addButton(btn13)
        // .addButton(btn14);

        // ComplexButton mainBtn2 = new ComplexButton();
        // mainBtn2.setName("法律咨询");
        // mainBtn2.addButton(btn21).addButton(btn22).addButton(btn23);
        ViewButton btn11 = new ViewButton();
        btn11.setName("巾帼云创");
        btn11.setUrl("http://ceshi.10novo.com/jgyc-manager/user/weChatInfo.htm"); // 常见问题

//		btn11.setName("通益投资");
//		btn11.setUrl("http://ceshi.10novo.com/mobile/index.html"); // 常见问题
        // ViewButton btn12 = new ViewButton();
        // btn12.setName("创业服务");
        // btn12.setUrl("http://www.hongmaofalv.com/hmflWechat/wechat/event.htm"); // 常见问题
        // CommonButton btn12 = new CommonButton();
        // btn12.setName("在线咨询");
        // btn12.setKey("12");
        // ViewButton btn13 = new ViewButton();
        // btn13.setName("红帽咨询");
        // btn13.setUrl("http://test.hongmaofalv.com/WebMobileRedhat3/html/");
        // // 常见问题

        /**
         * 这是公众号xiaoqrobot目前的菜单结构，每个一级菜单都有二级菜单项<br>
         *
         * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br>
         * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：<br>
         * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 });
         */
        Menu menu = new Menu();
        menu.addSubButton(btn11);
        return menu;
    }

    /**
     * @param requestMap
     * @return
     */

    private static String processEvent(Map<String, String> requestMap) {
        String respMessage = null;
        try {
            // 默认返回的文本消息内容
            String respContent = "请求处理异常，请稍候尝试！";

            // 发送方帐号（open_id）
            String fromUserName = requestMap.get("FromUserName");
            // 公众帐号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");

            // 回复文本消息
            TextRespMessage textMessage = new TextRespMessage();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(RespMessageEnum.TEXT.getValue());
            textMessage.setFuncFlag(0);

            if (msgType.equals(ReqMessageEnum.EVENT.getValue())) {
                // 事件类型
                String eventType = requestMap.get("Event");

                switch (ReqEventEnum.getReqEventEnum(eventType)) {

                    case SUBSCRIBE:

                        respContent = MessageUtil.RESP_MESSAGE_FIRST_SUBSCRIB;
                        break;

                    case UNSUBSCRIBE:

                        logger.warn("user:{} not subscribe you {}, please improve you ability!!!", fromUserName,
                                toUserName);

                        break;

                    case CLICK:

                        // 事件KEY值，与创建自定义菜单时指定的KEY值对应
                        String eventKey = requestMap.get("EventKey");

                        if (eventKey.equals("22")) { // 电话咨询
                            respContent = "红帽法律卫士全球服务专线，上千名资深律师全年无休随时待命，无论您何时何地遇到法律问题，一个电话随时连线律师，为您解答一切法律问题。红帽法律卫士提供多种电话咨询方式，打造定制化电话咨询服务。\n个人用户请拨打400-004-1220，\n企业用户请拨打028-83382532，\n同时也可下载\n<a href=\""
                                    + Constants.STATIC_WECHAT
                                    + "/appDownload.html\">红帽法律卫士官方APP</a>，\n一键呼叫您的私人律师和专属企业法律顾问。";
                        } else if (eventKey.equals("23")) {// 在线咨询
                            respContent = "红帽法律卫士在线咨询服务，24小时在线接受您的咨询，随时随地解决您的一切法律问题，还可通过发送照片、文档等形式，将材料发送给我们，专业律师团队将对您的疑问做出最贴心周到的专业建议和解答。\n现在就下载<a href=\""
                                    + Constants.STATIC_WECHAT + "/appDownload.html\">红帽法律卫士官方APP</a>进行在线咨询吧。";
                        } else if (eventKey.equals("35")) {
                            respContent = "红帽法律卫士ios版官方app现已登陆苹果商店，点击<a href=\"" + Constants.IOSAPP_DOWNLOAD
                                    + "\">红帽法律卫士官方APP</a>进行下载";
                        } else if (eventKey.equals("36")) {
                            // http://a.app.qq.com/o/simple.jsp?pkgname=com.hongmao.redhatlaw
                            // 应用宝
                            // http://zhushou.360.cn/detail/index/soft_id/1912592?recrefer=SE_D_%E7%BA%A2%E5%B8%BD%E6%B3%95%E5%BE%8B
                            // 360的
                            // http://shouji.360tpcdn.com/140906/5e3d4bdd3ab7d8949b5e514a060c5a53/com.hongmao.redhatlaw_2.apk
                            respContent = "红帽法律卫士Android版官方app现已上架应用宝、360、百度、小米等各大安卓平台，点击<a href=\""
                                    + Constants.MYAPP_DOWNLOAD + "\">红帽法律卫士官方APP</a>进行下载";
                        } else if (eventKey.equals("12")) {
                            respContent = "请直接输入您的问题，专业律师在线答疑解惑";
                        } else {
                            return EventHander.processSinglePicText(fromUserName, toUserName, eventKey);
                        }
                        // } else if (eventKey.equals("22")) {
                        // respContent = "经典游戏菜单项被点击！";
                        // } else if (eventKey.equals("23")) {
                        // respContent = "美女电台菜单项被点击！";
                        // } else if (eventKey.equals("24")) {
                        // respContent = "人脸识别菜单项被点击！";
                        // } else if (eventKey.equals("25")) {
                        // respContent = "聊天唠嗑菜单项被点击！";
                        // } else if (eventKey.equals("31")) {
                        // respContent = "Q友圈菜单项被点击！";
                        // } else if (eventKey.equals("32")) {
                        // respContent = "电影排行榜菜单项被点击！";
                        // } else if (eventKey.equals("33")) {
                        // respContent = "幽默笑话菜单项被点击！";
                        // }

                        break;
                }

                textMessage.setContent(respContent);
                respMessage = MessageUtil.textMessageToXml(textMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return respMessage;
    }
}
