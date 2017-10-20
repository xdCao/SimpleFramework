package Framework.aop;
/*
    created by xdCao on 2017/10/18
*/

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public abstract class AspectProxy implements Proxy{

    private static final Logger LOGGER= LoggerFactory.getLogger(AspectProxy.class);

    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result=null;
        Class<?> cls=proxyChain.getTargetClass();
        Method method=proxyChain.getTargetMethod();
        Object[] params=proxyChain.getMethodParams();
        begin();
        try {
            if (intercept(cls,method,params)){
                before(cls,method,params);
                result=proxyChain.doProxyChain();
                after(cls,method,params,result);
            }else {
                result=proxyChain.doProxyChain();
            }
        }catch (Exception e){
            LOGGER.error("代理出错",e);
            error(cls,method,params,e);
            throw e;
        }finally {
            end();
        }
        return result;
    }


    protected void begin() {

    }

    protected boolean intercept(Class<?> cls, Method method, Object[] params) {
        return true;
    }

    protected void before(Class<?> cls, Method method, Object[] params) {

    }

    protected void after(Class<?> cls, Method method, Object[] params, Object result) {

    }

    protected void end() {
    }

    protected void error(Class<?> cls, Method method, Object[] params, Exception e) {

    }




}
