package ru.ecom.jaas.ejb.service;

import java.io.IOException;
import java.util.Collection;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MalformedObjectNameException;
import javax.management.ReflectionException;

import ru.ecom.jaas.ejb.form.SecRoleForm;

/**
 * 
 */
public interface ISecUserService {


    public void fhushJboss() throws ReflectionException, InstanceNotFoundException, MBeanException, MalformedObjectNameException;
    public void exportUsersProperties(String aFilename) throws IOException ;
    public void exportUsersProperties() throws IOException ;

    public void exportRolesProperties(String aFilename) throws IOException ;
    public void exportRolesProperties() throws IOException ;

    public Collection<SecRoleForm> listUserRoles(long aUserId) ;
    public void removeRoles(long aUserId, long[] aRoles) ;
    public void addRoles(long aUserId, long[] aRoles) ;
    public Collection<SecRoleForm> listRolesToAdd(long aUserId) ;
}
