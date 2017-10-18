package helpers;
/*
    created by xdCao on 2017/10/18
*/

import utils.ClassUtil;

public final class HelperLoader {

    public static void init(){
        Class<?>[] classes={
                ClassHelper.class,
                BeanHelper.class,
                IOCHelper.class,
                ControllerHelper.class
        };
        for (Class<?> cls:classes){
            ClassUtil.loadClass(cls.getName(),false);
        }
    }

}
