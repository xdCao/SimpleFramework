package Framework.utils;
/*
    created by xdCao on 2017/10/18
*/

import java.util.Map;

public class CollectionUtil {

    public static boolean isNotEmpty(Map beanMap) {
        if (beanMap.isEmpty()){
            return false;
        }else {
            return true;
        }
    }

}
