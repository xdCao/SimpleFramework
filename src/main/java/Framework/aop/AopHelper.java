package Framework.aop;
/*
    created by xdCao on 2017/10/18
*/

import Framework.helpers.BeanHelper;
import Framework.helpers.ClassHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import Framework.tx.TransactionProxy;

import java.lang.annotation.Annotation;
import java.util.*;

public final class AopHelper {

    private static final Logger LOGGER= LoggerFactory.getLogger(AopHelper.class);

    static {
        try {
            Map<Class<?>,Set<Class<?>>> proxyMap=createProxyMap();
            Map<Class<?>,List<Proxy>> targetMap=createTargetMap(proxyMap);
            for (Map.Entry<Class<?>,List<Proxy>> entry:targetMap.entrySet()){
                Class<?> targetClass=entry.getKey();
                List<Proxy> proxyList=entry.getValue();
                Object proxy=ProxyManager.createProxy(targetClass,proxyList);
                BeanHelper.setBeanMap(targetClass,proxy);
            }
        }catch (Exception e){
            LOGGER.error("aop加载失败！！",e);
        }
    }

    //根据aspect注解获取带有其针对的注解的类
    private static Set<Class<?>> createTargetClassSet(Aspect aspect){
        Set<Class<?>> targetClassSet=new HashSet<Class<?>>();
        Class<? extends Annotation> annotation=aspect.value();
        if (annotation!=null&&!annotation.equals(Aspect.class)){
            targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(annotation));
        }
        return targetClassSet;
    }

    //切面类和目标类集合之间的关系
    private static Map<Class<?>,Set<Class<?>>> createProxyMap(){
        Map<Class<?>,Set<Class<?>>> proxyMap=new HashMap<Class<?>, Set<Class<?>>>();
        addAspectProxy(proxyMap);
        addTransactionProxy(proxyMap);
        return proxyMap;
    }

    private static void addTransactionProxy(Map<Class<?>, Set<Class<?>>> proxyMap) {

        Set<Class<?>> serviceClassSet=ClassHelper.getServiceClassSet();
        proxyMap.put(TransactionProxy.class,serviceClassSet);

    }

    private static void addAspectProxy(Map<Class<?>, Set<Class<?>>> proxyMap) {
        Set<Class<?>> proxyClassSet= ClassHelper.getClassSetBySuper(AspectProxy.class);
        for (Class<?> proxyClass:proxyClassSet){
            if (proxyClass.isAnnotationPresent(Aspect.class)){
                Aspect aspect=proxyClass.getAnnotation(Aspect.class);
                Set<Class<?>> targetClassSet=createTargetClassSet(aspect);
                proxyMap.put(proxyClass,targetClassSet);
            }
        }
    }

    private static Map<Class<?>,List<Proxy>> createTargetMap(Map<Class<?>,Set<Class<?>>> proxyMap) throws IllegalAccessException, InstantiationException {
        Map<Class<?>,List<Proxy>> targetMap=new HashMap<Class<?>, List<Proxy>>();
        for (Map.Entry<Class<?>,Set<Class<?>>> entry:proxyMap.entrySet()){
            Class<?> proxyClass=entry.getKey();
            Set<Class<?>> targetClassSet=entry.getValue();
            for (Class<?> targetClass:targetClassSet){
                Proxy proxy= (Proxy) proxyClass.newInstance();
                if (targetMap.containsKey(targetClass)){
                    targetMap.get(targetClass).add(proxy);
                }else{
                    List<Proxy> proxyList=new ArrayList<Proxy>();
                    proxyList.add(proxy);
                    targetMap.put(targetClass,proxyList);
                }
            }
        }
        return targetMap;
    }



}
