package cn.m4.chapter2.util;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {

    /**
     * 判断字符串是否为空
     * @param strValue
     * @return
     */
    public static boolean isEmpty(String strValue) {
        if(null!=strValue){
            strValue=strValue.trim();
        }
        return StringUtils.isEmpty(strValue);
    }

    /**
     * 判断字符串是否为空
     * @param strValue
     * @return
     */
    public static boolean isNotEmpty(String strValue){
        return !isEmpty(strValue);
    }
}
