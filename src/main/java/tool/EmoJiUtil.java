package tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * EmoJi表情转换
 * Created by Administrator on 2016/11/12.
 */
@SuppressWarnings("unused")
public class EmoJiUtil {

    public final static Logger logger = LoggerFactory.getLogger(EmoJiUtil.class);

    /**
     * @param str 待转换字符串
     * @return 转换后字符串
     * @catch UnsupportedEncodingException exception
     * @Description 将字符串中的emoji表情转换成可以在utf-8字符集数据库中保存的格式（表情占4个字节，需要utf8mb4字符集）
     */
    public static String convertEmoJi(String str) {
        String patternString = "([\\x{10000}-\\x{10ffff}\ud800-\udfff])";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            try {
                matcher.appendReplacement(sb, "[[" + URLEncoder.encode(matcher.group(1), "UTF-8") + "]]");
            } catch (UnsupportedEncodingException e) {
                logger.error("emoJiConvert error", e.getMessage());
            }
        }
        matcher.appendTail(sb);
        logger.debug("emoJiConvert " + str + " to " + sb.toString() + ", len：" + sb.length());
        return sb.toString();
    }

    /**
     * @param str 转换后的字符串
     * @return 转换前的字符串
     * @catch UnsupportedEncodingException exception
     * @Description 还原utf8数据库中保存的含转换后emoJi表情的字符串
     */
    public static String recoveryEmoJi(String str) {
        String patternString = "\\[\\[(.*?)\\]\\]";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            try {
                matcher.appendReplacement(sb, URLDecoder.decode(matcher.group(1), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                logger.error("emoJiRecovery error", e.getMessage());
            }
        }
        matcher.appendTail(sb);
        logger.debug("emoJiRecovery " + str + " to " + sb.toString());
        return sb.toString();
    }
}
