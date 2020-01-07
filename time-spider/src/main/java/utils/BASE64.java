package utils;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import	java.io.InputStream;
import java.net.URLEncoder;

/**
 * @author: dong.chao
 * @create: 2020-01-04 16:45
 * @description:
 **/
public class BASE64 {

    /**
     * 将本地图片进行Base64位编码
     *
     * @param imageFile 图片的url路径，如D:\\photo\\1.png
     *
     * @return
     */
    public static String encodeImgageToBase64(File imageFile) {
        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        // 其进行Base64编码处理
        byte[] data = null;
        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream(imageFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        // 返回Base64编码过的字节数组字符串

        return   URLEncoder.encode(encoder.encode(data));
    }

}
