package com.sprout.frame.baseframe.utils.coder;

import com.sprout.frame.baseframe.utils.LogUtil;

import java.net.URLEncoder;

/**
 * Create by Sprout at 2017/8/15
 */
public class CoderUtil {

    private static final String TAG = CoderUtil.class.getSimpleName();
    private static final String cset = "UTF-8";
    private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDgFGdDEn7ZPGVk8OOrnakY9Z+N8D9GF3o29rX/tnFj+iSbZw+h7tLQmWaMZOvi+UIfDQGMZgosnSN6eEIXtNtUWo0anWuiLx0S/U6dDTuTHFxLU+5pLiWUa4hk+bOGTxAszv5PJ3j2ye51n/3TPOCY1TciJa6BJ1+8KRFv9ejOWwIDAQAB";

    public static String encode(String msg) {
        try {
            return BaseCoder.encryptBASE64(RSACoder.encryptByPublicKey(msg.getBytes(cset), publicKey));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将byte[]转为String
     */
    public static String byteArrayToStr(byte[] byteArray) {
        return byteArray == null ? null : new String(byteArray);
    }

    /**
     * 对URL请求参数编码
     */
    public static String urlEncode(String msg) {
        try {
            return URLEncoder.encode(msg);
        } catch (Exception e) {
            LogUtil.debug(TAG, e.getMessage());
            return null;
        }
    }
}
