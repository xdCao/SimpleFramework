package utils;
/*
    created by xdCao on 2017/10/18
*/

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public final class StreamUtil {

    private static final Logger LOGGER= LoggerFactory.getLogger(StreamUtil.class);

    //从流中获取字符串
    public static String getString(ServletInputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line=reader.readLine())!=null){
                stringBuilder.append(line);
            }
        }catch (Exception e){
            LOGGER.error("从流中读取字符串失败！！"+e);
            throw new RuntimeException(e);
        }
        return stringBuilder.toString();
    }
}
