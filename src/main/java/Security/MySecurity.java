/*
    created by xdCao on 2017/10/20
*/


import java.util.Set;

public interface MySecurity {

    String getPassword(String username);

    Set<String> getRoleNameSet(String username);

    Set<String> getPermissionNameSet(String roleName);

}
