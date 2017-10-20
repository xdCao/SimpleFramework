package Framework.aop;

import java.lang.annotation.*;

/*
    created by xdCao on 2017/10/18
*/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {

    Class<? extends Annotation> value();

}
