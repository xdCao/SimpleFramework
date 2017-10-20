/*
    created by xdCao on 2017/10/20
*/

import tx.DatabaseHelper;

import java.util.Set;

public class AppSecurity implements MySecurity {

    public String getPassword(String username) {
        String sql="SELECT password from user where username=?";
        return sql;
    }

    public Set<String> getRoleNameSet(String username) {
        return null;
    }

    public Set<String> getPermissionNameSet(String roleName) {
        return null;
    }
}
