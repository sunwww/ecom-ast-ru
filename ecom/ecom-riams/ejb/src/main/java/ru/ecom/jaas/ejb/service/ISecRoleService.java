package ru.ecom.jaas.ejb.service;

import ru.ecom.jaas.ejb.form.SecUserForm;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: esinev
 * Date: 15.10.2006
 * Time: 3:22:59
 * To change this template use File | Settings | File Templates.
 */
public interface ISecRoleService {
    CheckNode loadPoliciesByRole(long aRoleId);

    CheckNode loadPolicies();

    void saveRolePolicies(long aRoleId, long[] aAdded, long[] aRemoved);

    //Получить список пользователей у которых, есть данная роль
    List<SecUserForm> listUsersByRole(long aRoleId, boolean aIsSystemView);

    //Получить список пользователей, которым можно добавить роль
    List<SecUserForm> listUsersToAdd(long aRoleId, boolean aIsSystemView);

    //Добавить список пользователей к роли
    void addUsersToRole(long aRoleId, long[] aUsersId);

    //Удалить список пользователей из роли
    void removeUsersFromRole(long aRoleId, long[] aUsersIs);

    //Получить информацию по ИД роли
    String getRoleInfo(long aRoleId);

}
