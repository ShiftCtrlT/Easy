package cn.ease4j.util;

/**
 * 转换操作工具类
 * 除了String，其他数据类型转化都是通过先转化成String，再转换成对应的数据类型
 */
public class CastUtil {

    /**
     * 转换成字符型，默认值为空
     * @param obj
     * @return
     */
    public static String castString(Object obj){
        return castString(obj,"");
    }

    /**
     * 转换成字符型，指定默认值
     * @param obj
     * @param defaultValue
     * @return
     */
    public static String castString(Object obj,String defaultValue){
        return obj!=null ? String.valueOf(obj) : defaultValue;
    }

    /**
     * 转化成整型，默认值为0
     * @param obj
     * @return
     */
    public static Integer castInt(Object obj){

        return castInt(obj,0);
    }

    /**
     * 转换成整型，指定默认值
     * @param obj
     * @param defaultValue
     * @return
     */
    public static Integer castInt(Object obj,Integer defaultValue){
        Integer intValue=defaultValue;
        String strValue=castString(obj);
        if(StringUtil.isNotEmpty(strValue)){
            try {
                intValue = Integer.valueOf(strValue);
            } catch (NumberFormatException e) {
//                e.printStackTrace();
                intValue = defaultValue;
            }
        }
        return intValue;
    }

    /**
     * 转换成布尔型，默认值为false
     * @param obj
     * @return
     */
    public static Boolean castBoolean(Object obj){
        return castBoolean(obj,false);
    }


    /**
     * 转换成布尔型，指定默认值
     * @param obj
     * @param defaultValue
     * @return
     */
    public static Boolean castBoolean(Object obj,Boolean defaultValue){
        Boolean value=defaultValue;
        String booValue = castString(obj);
        if(null!=obj) {
            value = Boolean.parseBoolean(booValue);
        }
        return value;
    }

    /**
     * 转换成长整形，指定默认值
     * @param obj
     * @param defaultValue
     * @return
     */
    public static Long castLong(Object obj,Long defaultValue){
        Long value = defaultValue;
        if(null != obj){
            String strValue=castString(obj);
            if(StringUtil.isNotEmpty(strValue)){
                try {
                    value=Long.parseLong(strValue);
                } catch (NumberFormatException e) {
//                    e.printStackTrace();
                    value=defaultValue;
                }
            }
        }
        return value;
    }

    /**
     * 转换成长整形，默认值为0
     * @param obj
     * @return
     */
    public static Long castLong(Object obj){
        return castLong(obj,0L);
    }

    /**
     * 转换成双精度浮点，指定默认值
     * @param obj
     * @param defaultValue
     * @return
     */
    public static Double castDouble(Object obj,Double defaultValue){
        Double value = defaultValue;
        if(null != obj){
            String strValue = castString(obj);
            if(StringUtil.isNotEmpty(strValue)){
                try {
                    value=Double.parseDouble(strValue);
                } catch (NumberFormatException e) {
//                    e.printStackTrace();
                    value=defaultValue;
                }
            }
        }
        return value;
    }


    /**
     * 转换成双精度浮点，默认值为0
     * @param obj
     * @return
     */
    public static Double castDouble(Object obj){
        return castDouble(obj,0D);
    }
}
