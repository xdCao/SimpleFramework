package Framework.tx;
/*
    created by xdCao on 2017/10/19
*/

import Framework.aop.Proxy;
import Framework.aop.ProxyChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class TransactionProxy implements Proxy{

    private static final Logger LOGGER= LoggerFactory.getLogger(TransactionProxy.class);

    private static final ThreadLocal<Boolean> FLAG_HOLDER=new ThreadLocal<Boolean>(){
        @Override
        protected Boolean initialValue() {
            return false;
        }
    };

    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result;
        boolean flag=FLAG_HOLDER.get();
        Method method=proxyChain.getTargetMethod();
        if (!flag&&method.isAnnotationPresent(Transaction.class)){
            FLAG_HOLDER.set(true);
            try{
                DatabaseHelper.beginTransaction();
                LOGGER.debug("-----------开启事务------------");
                result=proxyChain.doProxyChain();
                DatabaseHelper.commitTransaction();
                LOGGER.debug("-----------提交事务------------");
            }catch (Exception e){
                DatabaseHelper.rollbackTransaction();
                LOGGER.debug("-----------回滚事务-------------");
                throw e;
            }finally {
                FLAG_HOLDER.remove();
            }
        }else {
            result=proxyChain.doProxyChain();
        }
        return result;
    }
}
