/*
    created by xdCao on 2017/10/18
*/

import app.App;
import helpers.BeanHelper;
import helpers.ClassHelper;
import org.junit.Test;
import utils.ReflectionUtil;

import java.util.Iterator;
import java.util.Set;

public class ReflectionUtilTest {

    @Test
    public void test(){
        Set<Class<?>> classSet = ClassHelper.getClassSet();
        Iterator<Class<?>> iterator = classSet.iterator();
        Class<?> next=null;
        while (iterator.hasNext()){
            next = iterator.next();
        }
        Object instance = ReflectionUtil.newInstance(next);
        ReflectionUtil.invokeMethod(instance,instance.getClass().getMethods()[0],null);
    }

    @Test
    public void getBean(){
        App bean = BeanHelper.getBean(App.class);
        System.out.println(bean);
    }

}
