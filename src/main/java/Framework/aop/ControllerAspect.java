package Framework.aop;

import Framework.annotations.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/*
    created by xdCao on 2017/10/18
*/
@Aspect(Controller.class)
public class ControllerAspect extends AspectProxy{

    private static final Logger LOGGER= LoggerFactory.getLogger(ControllerAspect.class);

    private long begin;

    @Override
    protected void before(Class<?> cls, Method method, Object[] params) {
        LOGGER.debug("-----------------begin---------------------");
        LOGGER.debug(String.format("class: %s",cls.getName()));
        LOGGER.debug(String.format("method: %s",method.getName()));
        begin=System.currentTimeMillis();
    }

    @Override
    protected void after(Class<?> cls, Method method, Object[] params, Object result) {
        LOGGER.debug(String.format("time: %dms",System.currentTimeMillis()-begin));
        LOGGER.debug("----------------------end-------------------");
    }
}
