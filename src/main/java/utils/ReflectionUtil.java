package utils;
/*
    created by xdCao on 2017/10/18
*/

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.event.ObjectChangeListener;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionUtil {

    private static final Logger LOGGER= LoggerFactory.getLogger(ReflectionUtil.class);

    public static Object newInstance(Class<?> cls){
        Object instance;
        try {
            instance=cls.newInstance();
        }catch (Exception e){
            LOGGER.error("创建实例"+cls.getName()+"失败！！");
            throw new RuntimeException(e);
        }
        return instance;
    }

    public static Object invokeMethod(Object object, Method method,Object[] args){
        Object result;
        try {
            method.setAccessible(true);
            result=method.invoke(object,args);
        }catch (Exception e){
            LOGGER.error("调用方法： "+method.getName()+" 失败！！");
            throw new RuntimeException(e);
        }
        return result;
    }


    public static void setField(Object object, Field field, Object value){
        try {
            field.setAccessible(true);
            field.set(object,value);
        }catch (Exception e){
            LOGGER.error("为对象： "+object.getClass().getName()+"设置属性： "+field.getName()+" 失败！！");
            throw new RuntimeException(e);
        }
    }

}
