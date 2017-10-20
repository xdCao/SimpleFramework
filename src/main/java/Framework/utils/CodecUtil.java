package Framework.utils;
/*
    created by xdCao on 2017/10/18
*/

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;
import java.net.URLEncoder;

public final class CodecUtil {

    private static final Logger LOGGER= LoggerFactory.getLogger(CodecUtil.class);

    public static String decodeUrl(String source) {
        String target;
        try {
            target= URLDecoder.decode(source,"utf-8");
        }catch (Exception e){
            LOGGER.error("url解码失败！！"+e);
            throw new RuntimeException(e);
        }
        return target;
    }

    public static String encodeUrl(String source){
        String target;
        try {
            target= URLEncoder.encode(source,"utf-8");
        }catch (Exception e){
            LOGGER.error("url编码失败！！"+e);
            throw new RuntimeException(e);
        }
        return target;
    }

}
