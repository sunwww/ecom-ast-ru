package ru.ecom.jaas.ejb.service;

import ru.ecom.jaas.ejb.form.SecRoleForm;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MalformedObjectNameException;
import javax.management.ReflectionException;
import java.io.IOException;
import java.util.Collection;

/**
 * 
 */
public interface ISecUserService {


	String changePassword (String aNewPassword, String aOldPassword, String aUsername)throws IOException;
	void setDefaultPassword (String aNewPassword, String aUsername, String aUsernameChange)throws IOException;
    void fhushJboss() throws ReflectionException, InstanceNotFoundException, MBeanException, MalformedObjectNameException;
    void exportUsersProperties(String aFilename) throws IOException ;
    void exportUsersProperties() throws IOException ;

    void exportRolesProperties(String aFilename) throws IOException ;
    void exportRolesProperties() throws IOException ;

    Collection<SecRoleForm> listUserRoles(long aUserId, boolean aIsSystemView) ;
    void removeRoles(long aUserId, long[] aRoles) ;
    void addRoles(long aUserId, long[] aRoles) ;
    Collection<SecRoleForm> listRolesToAdd(long aUserId, boolean aIsSystemView) ;
    //Точка входа в короткое создание пользователя в отделение с должностью, логином, паролем, ролями через Персону
    String addUserToHospShort(Long aPatientId, Long aLpuId, Long avWfId, String newPsw, Long userCopy, String username, Long aUserId)  throws IOException;
}
