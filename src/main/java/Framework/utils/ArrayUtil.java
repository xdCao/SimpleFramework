package Framework.utils;
/*
    created by xdCao on 2017/10/18
*/

import org.apache.commons.lang3.ArrayUtils;

public class ArrayUtil {


    public static boolean isNotEmpty(Object[] array) {
        return !ArrayUtils.isEmpty(array);
    }

    public static boolean isEmpty(Object[] array){
        return ArrayUtils.isEmpty(array);
    }

}
