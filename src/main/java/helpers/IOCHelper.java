package helpers;
/*
    created by xdCao on 2017/10/18
*/


import annotations.Inject;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ArrayUtil;
import utils.CollectionUtil;
import utils.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

public final class IOCHelper {

    static {
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (CollectionUtil.isNotEmpty(beanMap)){
            for (Map.Entry<Class<?>,Object> entry:beanMap.entrySet()){
                Class<?> beanClass = entry.getKey();
                Object beanInstance=entry.getValue();
                Field[] fields = beanClass.getDeclaredFields();
                if (ArrayUtil.isNotEmpty(fields)){
                    for (Field beanField:fields){
                        if (beanField.isAnnotationPresent(Inject.class)){
                            Class<?> type = beanField.getType();
                            Object fieldInstance=beanMap.get(type);
                            if (fieldInstance!=null){
                                ReflectionUtil.setField(beanInstance,beanField,fieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }

}
