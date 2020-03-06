package com.timeblog.framework.system.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Dong.Chao
 * @Classname Md5Utils
 * @Description
 * @Date 2020/3/6 10:37
 * @Version V1.0
 */
public class Md5Utils {

    /**
     * 字符串的MD5
     * @param plainText
     * @return
     */
    public static String encode(String plainText) throws NoSuchAlgorithmException {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0){ i += 256;}
                if (i < 16){buf.append("0");}
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
            //System.out.println("result: " + buf.toString());// 32位的加密
            //System.out.println("result: " + buf.toString().substring(8, 24));// 16位的加密
    }
}
