package ru.ecom.ejb.services.entityform.interceptors;


/**
 *
 */
public interface IDynamicSecurityInterceptor {

    void check(String aPolicyAction, Object aId, InterceptorContext aContext) ;
}
