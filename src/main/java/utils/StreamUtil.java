package utils;
/*
    created by xdCao on 2017/10/18
*/

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

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

    public static void copyStream(InputStream inputStream, OutputStream outputStream) {
        try {
            int length;
            byte[] buffer=new byte[4*1024];
            while ((length=inputStream.read(buffer,0,buffer.length))!=-1){
                outputStream.write(buffer,0,length);
            }
            outputStream.flush();
        }catch (Exception e){
            LOGGER.error("复制流出错！！",e);
            throw new RuntimeException(e);
        }finally {
            try {
                inputStream.close();
                outputStream.close();
            }catch (Exception e){
                LOGGER.error("关闭流失败！！",e);
            }
        }
    }
}
