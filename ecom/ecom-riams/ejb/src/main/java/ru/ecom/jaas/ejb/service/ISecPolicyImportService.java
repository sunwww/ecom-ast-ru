package ru.ecom.jaas.ejb.service;

import java.util.Collection;
import java.util.Map;

import ru.ecom.jaas.ejb.domain.SecPolicy;

/**
 * Импорт политик
 */
public interface ISecPolicyImportService {

    void addToRole(long aRoleId, String aStr) ;

    SecPolicy importPolicy(String aStr) ;

    SecPolicy importPolicy(String aStr, Map<String, String> aNames) ;

    void importPolicies(long aMonitorId, Collection<String> aPolicies, Map<String, String> aNames, Map<String, String> aComments) ;
    
    void standartPolicyByParent(Long aParentPolicy) ;
    
    Long addPolicy(String aPolicy, String aName) ;

}
