package Framework.helpers;
/*
    created by xdCao on 2017/10/18
*/

import Framework.utils.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class BeanHelper {

    //存放bean类与实例的关系
    private static final Map<Class<?>,Object> BEAN_MAP=new HashMap<Class<?>,Object>();

    static {
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        for (Class<?> beanClass:beanClassSet){
            Object object = ReflectionUtil.newInstance(beanClass);
            BEAN_MAP.put(beanClass,object);
        }
    }

    public static Map<Class<?>,Object> getBeanMap(){
        return BEAN_MAP;
    }

    public static <T> T getBean(Class<T> cls){
        if (!BEAN_MAP.containsKey(cls)){
            throw new RuntimeException("找不到与class： "+cls.getName()+"对应的bean实例");
        }
        return (T)BEAN_MAP.get(cls);
    }

    public static void setBeanMap(Class<?> cls,Object object){
        BEAN_MAP.put(cls,object);
    }

}
