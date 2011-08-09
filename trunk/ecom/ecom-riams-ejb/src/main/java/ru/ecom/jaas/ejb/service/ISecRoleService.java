package ru.ecom.jaas.ejb.service;

import java.util.List;

import ru.ecom.jaas.ejb.form.SecUserForm;

/**
 * Created by IntelliJ IDEA.
 * User: esinev
 * Date: 15.10.2006
 * Time: 3:22:59
 * To change this template use File | Settings | File Templates.
 */
public interface ISecRoleService {
	public CheckNode loadPoliciesByRole(long aRoleId) ;
    public CheckNode loadPolicies() ;
    public void saveRolePolicies(long aRoleId, long[] aAdded, long[] aRemoved) ;
    //Получить список пользователей у которых, есть данная роль
    public List<SecUserForm>listUsersByRole(long aRoleId) ;
    //Получить список пользователей, которым можно добавить роль
    public List<SecUserForm> listUsersToAdd(long aRoleId) ;
    //Добавить список пользователей к роли
    public void addUsersToRole(long aRoleId, long[] aUsersId);
    //Удалить список пользователей из роли
    public void removeUsersFromRole(long aRoleId, long[] aUsersIs);
    //Получить информацию по ИД роли
    public String getRoleInfo(long aRoleId) ;

}
