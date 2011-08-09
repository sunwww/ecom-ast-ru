package ru.ecom.jaas.ejb.service;

import java.util.Collection;
import java.util.Map;

import ru.ecom.jaas.ejb.domain.SecPolicy;

/**
 * Импорт политик
 */
public interface ISecPolicyImportService {

    public void addToRole(long aRoleId, String aStr) ;

    public SecPolicy importPolicy(String aStr) ;

    public SecPolicy importPolicy(String aStr, Map<String, String> aNames) ;

    public void importPolicies(long aMonitorId, Collection<String> aPolicies, Map<String,String> aNames, Map<String,String> aComments) ;
    
    public void standartPolicyByParent(Long aParentPolicy) ;
    
    public Long addPolicy(String aPolicy, String aName) ;

}
