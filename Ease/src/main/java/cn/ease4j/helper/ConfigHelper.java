package cn.ease4j.helper;


import cn.ease4j.constant.ConfigConstant;
import cn.ease4j.util.PropUtil;

import java.util.Properties;

public final class ConfigHelper {

    private static final Properties CONFIG_PROPS =
            PropUtil.loadProps(ConfigConstant.CONFIG_FILE);

    /**
     * 获取jdbc驱动
     * @return
     */
    public static String getJdbcDriver(){
        return PropUtil.getString(CONFIG_PROPS,ConfigConstant.JDBC_DRIVER);
    }

    /**
     * 获取jdbc URL
     * @return
     */
    public static String getJdbcUrl(){
        return PropUtil.getString(CONFIG_PROPS,ConfigConstant.JDBC_URL);
    }

    /**
     * 获取jdbc用户名
     * @return
     */
    public static String getJdbcUsername(){
        return PropUtil.getString(CONFIG_PROPS,ConfigConstant.JDBC_USERNAME);
    }

    /**
     * 获取jdbc密码
     * @return
     */
    public static String getJdbcPassword(){
        return PropUtil.getString(CONFIG_PROPS,ConfigConstant.JDBC_PASSWORD);
    }

    /**
     * 获取应用的基本包路径
     * @return
     */
    public static String getAppBasePackage(){
        return PropUtil.getString(CONFIG_PROPS,ConfigConstant.APP_BASE_PACKAGE);
    }

    /**
     * 获取应用jsp路径（含默认值）
     * @return
     */
    public static String getAppJspPath(){
        return PropUtil.getString(CONFIG_PROPS,ConfigConstant.APP_JSP_PATH,"/WEB-INF/view/");
    }

    /**
     * 获取静态资源的路径（含默认值）
     * @return
     */
    public static String getAppAssetPath(){
        return PropUtil.getString(CONFIG_PROPS,ConfigConstant.APP_ASSET_PATH,"/asset/");
    }


}
