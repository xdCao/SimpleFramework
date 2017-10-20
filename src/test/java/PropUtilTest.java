/*
    created by xdCao on 2017/10/18
*/

import Framework.helpers.ConfigHelper;
import org.junit.Test;

public class PropUtilTest {

    @Test
    public void test(){
        System.out.println(ConfigHelper.getJdbcDriver());
        System.out.println(ConfigHelper.getJdbcPassword());
        System.out.println(ConfigHelper.getJdbcUrl());
        System.out.println(ConfigHelper.getJdbcUserName());

        System.out.println(ConfigHelper.getAppJspPath());
        System.out.println(ConfigHelper.getAppAssetPath());
    }

}
