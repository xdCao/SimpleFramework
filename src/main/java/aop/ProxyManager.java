package aop;
/*
    created by xdCao on 2017/10/18
*/

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

public class ProxyManager {

    public static <T> T createProxy(final Class<?> targetClass, final List<Proxy> proxyList){
        return (T) Enhancer.create(targetClass, new MethodInterceptor() {
            public Object intercept(Object targetObject, Method method, Object[] params, MethodProxy methodProxy) throws Throwable {
                return new ProxyChain(targetClass,targetObject,method,methodProxy,params,proxyList).doProxyChain();
            }
        });
    }

}
