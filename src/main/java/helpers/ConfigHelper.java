package helpers;
/*
    created by xdCao on 2017/10/18
*/

import constans.ConfigConstants;
import utils.PropUtil;

import java.util.Properties;

public final class ConfigHelper {

    private static Properties CONFIG_PROPS= PropUtil.load(ConfigConstants.CONFIG_FILE);

    public static String getJdbcDriver(){
        return PropUtil.getString(CONFIG_PROPS,ConfigConstants.JDBC_DRIVER);
    }

    public static String getJdbcUrl(){
        return PropUtil.getString(CONFIG_PROPS,ConfigConstants.JDBC_URL);
    }

    public static String getJdbcUserName(){
        return PropUtil.getString(CONFIG_PROPS,ConfigConstants.JDBC_USERNAME);
    }

    public static String getJdbcPassword(){
        return PropUtil.getString(CONFIG_PROPS,ConfigConstants.JDBC_PASSWORD);
    }

    public static String getAppBasePackage(){
        return PropUtil.getString(CONFIG_PROPS,ConfigConstants.APP_BASE_PACKAGE);
    }

    public static String getAppJspPath(){
        return PropUtil.getString(CONFIG_PROPS,ConfigConstants.APP_JSP_PATH,"/WEB-INF/view/");
    }

    public static String getAppAssetPath(){
        return PropUtil.getString(CONFIG_PROPS,ConfigConstants.APP_ASSET_PATH,"/asset/");
    }

}
