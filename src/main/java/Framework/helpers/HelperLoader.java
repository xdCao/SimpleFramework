package Framework.helpers;
/*
    created by xdCao on 2017/10/18
*/

import Framework.aop.AopHelper;
import Framework.utils.ClassUtil;

public final class HelperLoader {

    public static void init(){
        Class<?>[] classes={
                ClassHelper.class,
                BeanHelper.class,
                AopHelper.class,
                IOCHelper.class,
                ControllerHelper.class
        };
        for (Class<?> cls:classes){
            ClassUtil.loadClass(cls.getName(),false);
        }
    }

}
