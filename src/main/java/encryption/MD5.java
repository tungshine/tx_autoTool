package encryption;


import org.apache.commons.codec.digest.DigestUtils;

public class MD5 {

    public static String md5(String content) {
        return DigestUtils.md5Hex(content);
    }
}
