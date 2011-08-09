package ru.ecom.ejb.services.entityform.interceptors;


/**
 *
 */
public interface IDynamicParentSecurityInterceptor {

    void checkParent(String aPolicyAction, Object aParentId, InterceptorContext aContext) ;
}
