package helpers;
/*
    created by xdCao on 2017/10/18
*/

import annotations.Controller;
import annotations.Service;
import jdk.internal.dynalink.support.ClassMap;
import utils.ClassUtil;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

public final class ClassHelper {

    //存放所加载的类
    private static final Set<Class<?>> CLASS_SET;

    static {
        String basePackage=ConfigHelper.getAppBasePackage();
        CLASS_SET= ClassUtil.getClassSet(basePackage);
    }

    //获取应用包名下的所有类
    public static Set<Class<?>> getClassSet(){
        return CLASS_SET;
    }

    //获取所有service类
    public static Set<Class<?>> getServiceClassSet(){
        Set<Class<?>> serviceClassSet=new HashSet<Class<?>>();
        for (Class<?> cls:CLASS_SET){
            if (cls.isAnnotationPresent(Service.class)){
                serviceClassSet.add(cls);
            }
        }
        return serviceClassSet;
    }

    //获取所有controller类
    public static Set<Class<?>> getControllerClassSet(){
        Set<Class<?>> controllerClassSet=new HashSet<Class<?>>();
        for (Class<?> cls:CLASS_SET){
            if (cls.isAnnotationPresent(Controller.class)){
                controllerClassSet.add(cls);
            }
        }
        return controllerClassSet;
    }

    //获取所有Bean（包括service、controller）
    public static Set<Class<?>> getBeanClassSet(){
        Set<Class<?>> beanClassSet=new HashSet<Class<?>>();
        beanClassSet.addAll(getControllerClassSet());
        beanClassSet.addAll(getServiceClassSet());
        return beanClassSet;
    }

    //获取某父类或接口的所有子类
    public static Set<Class<?>> getClassSetBySuper(Class<?> superClass){
        Set<Class<?>> classSet=new HashSet<Class<?>>();
        for (Class<?> cls:CLASS_SET){
            if (superClass.isAssignableFrom(cls)&&!superClass.equals(cls)){
                classSet.add(cls);
            }
        }
        return classSet;
    }

    //获取带有某注解的所有类
    public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotation){
        Set<Class<?>> classSet=new HashSet<Class<?>>();
        for (Class<?> cls:CLASS_SET){
            if (cls.isAnnotationPresent(annotation)){
                classSet.add(cls);
            }
        }
        return classSet;
    }

}
