package cn.ease4j.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.CoderMalfunctionError;

/**
 * 编码与解码的工具类
 */
public class Code4jUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(CoderMalfunctionError.class);

    /**
     * 将URL编码
     * @param source
     * @return
     */
    public static String encodeURL(String source){
        String result;
        try {
            result = URLEncoder.encode(source,"UTF-8");
        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
            LOGGER.error("encode source failure ",e);
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 将URL解码
     * @param source
     * @return
     */
    public static String decodeURL(String source){
        String result;
        try {
            result = URLDecoder.decode(source,"UTF-8");
        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
            LOGGER.error("decode url failure ",e);
            throw new RuntimeException(e);
        }
        return result;
    }
}
