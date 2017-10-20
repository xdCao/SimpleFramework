package Framework.utils;
/*
    created by xdCao on 2017/10/18
*/

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class JsonUtil {

    private static final Logger LOGGER= LoggerFactory.getLogger(JsonUtil.class);

    private static final ObjectMapper mapper=new ObjectMapper();

    public static <T> String toJson(T obj){
        String json;
        try {
            json=mapper.writeValueAsString(obj);
        }catch (Exception e){
            LOGGER.error("对象转化为json时失败！！"+e);
            throw new RuntimeException(e);
        }
        return json;
    }


    public static <T> T fromJson(String json,Class<T> type){
        T pojo;
        try {
            pojo=mapper.readValue(json,type);
        }catch (Exception e){
            LOGGER.error("json转化为对象时失败！！"+e);
            throw new RuntimeException(e);
        }
        return pojo;
    }

}
