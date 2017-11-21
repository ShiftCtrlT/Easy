package cn.ease4j.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 属性文件工具类
 */
public class PropUtil {
    //获取日志对象
    public static final Logger LOGGER= LoggerFactory.getLogger(PropUtil.class);

    /**
     * 加载配置文件
     * @param fileName
     * @return
     */
    public static Properties loadProps(String fileName){
        Properties props=null;
        InputStream is=null;
        try {
            is=Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if(null==is){
                throw new FileNotFoundException(fileName+" is not found");
            }
            props=new Properties();
            props.load(is);
        }catch (IOException e){
            LOGGER.error("load properties file failure",e);
        }finally {
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    LOGGER.error("close inputstream failure",e);
                }
            }
        }
        return props;
    }

    /**
     * 获取字符型属性，指定默认值
     * @param prop
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getString(Properties prop, String key,String defaultValue){
        String value=defaultValue;
        if(prop.containsKey(key)){
            value=prop.getProperty(key);
        }
        return value;
    }

    /**
     * 获取字符型属性，默认值为空
     * @param prop
     * @param key
     * @return
     */
    public static String getString(Properties prop, String key){
        return getString(prop,key,"");
    }

    /**
     * 获取整型属性，指定默认值
     * @param prop
     * @param key
     * @param defaultValue
     * @return
     */
    public static Integer getInt(Properties prop,String key,Integer defaultValue){
        Integer value = defaultValue;
        if(prop.containsKey(key)){
            value=CastUtil.castInt(prop.getProperty(key));
        }
        return value;
    }

    /**
     * 获取整型属性，默认值0
     * @param prop
     * @param key
     * @return
     */
    public static Integer getInt(Properties prop,String key){
        return getInt(prop,key,0);
    }

    /**
     * 获取布尔型属性，指定默认值
     * @param prop
     * @param key
     * @param defaultValue
     * @return
     */
    public static Boolean getBoolean(Properties prop,String key,Boolean defaultValue){
        Boolean value=defaultValue;
        if(prop.containsKey(key)){
            value=CastUtil.castBoolean(prop.getProperty(key));
        }
        return value;
    }

    /**
     * 获取布尔型属性，默认值false
     * @param prop
     * @param key
     * @return
     */
    public static Boolean getBoolean(Properties prop,String key){
        return getBoolean(prop,key,false);
    }

}
