package utils;
/*
    created by xdCao on 2017/10/18
*/

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public final class ClassUtil {

    private static final Logger LOGGER= LoggerFactory.getLogger(ClassUtil.class);

    public static ClassLoader getClassLoader(){
        return Thread.currentThread().getContextClassLoader();
    }

    public static Class<?> loadClass(String className,boolean initialized){

    }

    public Set<Class<?>> getClassSet(String packageName){

    }

}
