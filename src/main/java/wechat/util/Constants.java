package wechat.util;

/**
 * @author: TangXu
 * @date: 2018/10/29 19:38
 * @description: 
 */
public class Constants {

    public static final String unified_order_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    public static final String query_order_url = "https://api.mch.weixin.qq.com/pay/orderquery";

    // 正式服务器
    public static final String appid = "wx516eef49182545c8";
    public static final String appSercet = "194df1c280bcfe95e5a74d64ac1d2821";
    // 正式服务器商户号
    public static final String mch_id = "1405422202";
    public static final String api_key = "NU6F77J7IOUHQJTH7CRFUOH0H8DGOOXG";
    // 正式服务器地址
    public static final String notify_url = "http://yp.10novo.com/sflc-manager/pay/payNotify.htm";

//	// 测试服务器
//	public static final String appid = "wx0139474ca893deaf";
//	public static final String appSercet = "a3d9a1f629b6dd353bf03821a271ea87";
//	// 测试服务器商户号
//	public static final String mch_id = "1340141101";
//	public static final String api_key = "NU6F77J7IOUHQJTH7CRFUOH0H8DGOOXG";
//	// 测试服务器地址
//	public static final String notify_url = "http://ceshi.10novo.com/sflc-manager/pay/payNotify.htm";

//    // 巾帼云创
//    public static final String appid = "wxdf7e741ce24be1ee";
//    public static final String appSercet = "bbf47d031129f74951c1002630741cd2";
//    // 巾帼云创商户号
//    public static final String mch_id = "1407878402";
//    public static final String api_key = "NU6F77J7IOUHQJTH7CRFUOH0H8DGOOXG";
//    // 巾帼云创地址
//    public static final String notify_url = "http://ceshi.10novo.com/jgyc-manager/pay/payNotify.htm";


    public static final int MAX_BUTTON_SIZE = 8; // 微信公众号最大按钮个数
    public static final int MAX_MENU_SIZE = 3;// 微信公众号最大菜单个数
    public static final String SERVER_HOST = "http://server.hongmaofalv.com";
    public static final String SERVER_WECHAT = SERVER_HOST + "/hmwsmvc";
    public static final String STATIC_WECHAT = SERVER_HOST + "/wechat";
    public static final String MYAPP_DOWNLOAD = "http://android.myapp.com/myapp/detail.htm?apkName=com.hongmao.redhatlaw";
    public static final String IOSAPP_DOWNLOAD = "https://itunes.apple.com/us/app/hong-mao-fa-lu-wei-shi/id876381850?l=zh&ls=1&mt=8";
}
