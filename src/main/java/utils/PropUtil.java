package utils;
/*
    created by xdCao on 2017/10/18
*/

import constans.ConfigConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropUtil {

    private static final Logger LOGGER= LoggerFactory.getLogger(PropUtil.class);

    public static Properties load(String configFile) {
        Properties properties=new Properties();
        InputStream resource = PropUtil.class.getResourceAsStream("/"+ConfigConstants.CONFIG_FILE);
        try {
            properties.load(resource);
        } catch (IOException e) {
            LOGGER.error("加载配置文件： "+ConfigConstants.CONFIG_FILE+" 失败！！");
            e.printStackTrace();
        }
        return properties;
    }


    public static String getString(Properties configProps, String prefix) {
        return configProps.getProperty(prefix);
    }

    public static String getString(Properties configProps, String prefix, String defaultValue) {
        if (null==configProps.getProperty(prefix)|| StringUtils.isEmpty(configProps.getProperty(prefix))){
            return defaultValue;
        }else {
            return configProps.getProperty(prefix);
        }
    }

    public static Integer getInt(Properties configProps,String prefix,int defaultValue){
        if (null==configProps.getProperty(prefix)||StringUtils.isEmpty(configProps.getProperty(prefix))){
            return defaultValue;
        }else{
            return Integer.parseInt(configProps.getProperty(prefix));
        }
    }

}
