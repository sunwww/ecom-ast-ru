package ru.ecom.mis.ejb.form.lpu.interceptors;

import javax.ejb.SessionContext;

import ru.ecom.ejb.services.entityform.interceptors.IDynamicParentSecurityInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.IDynamicSecurityInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;

/**
 *
 */
public class MisLpuDynamicSecurity
        implements IDynamicSecurityInterceptor, IDynamicParentSecurityInterceptor {


    protected static void checkByParent(String aPolicyAction, MisLpu aLpu, SessionContext aContext) {
    	if(isDisabled(aContext)) return ;
        StringBuilder sb = new StringBuilder();
        while(aLpu!=null) {
            StringBuilder policy = new StringBuilder("/Policy/Mis/MisLpuDynamic/");
            policy.append(aLpu.getId()) ;
            policy.append('/') ;
            policy.append(aPolicyAction) ;
            if(aContext.isCallerInRole(policy.toString())) {
                return ;
            } else {
                if(sb.length()!=0) {
                    sb.append(" или ") ;
                }
                sb.append(policy) ;
                aLpu = aLpu.getParent() ;
            }
        }
        throw new IllegalStateException("Нет политик безопасности "+sb) ;

    }

    /**
     * Отключена ли проверка
     * @param aContext
     * @return
     */
    private static boolean isDisabled(SessionContext aContext) {
    	return aContext.isCallerInRole("/Policy/Mis/MisLpuDynamic/Disable") ;
    }

    
    public void check(String aPolicyToExtend, Object aId, InterceptorContext aContext) {
    	if(isDisabled(aContext.getSessionContext())) return ;
        MisLpu lpu = aContext.getEntityManager().find(MisLpu.class, aId) ;
        checkByParent(aPolicyToExtend, lpu, aContext.getSessionContext());
    }

    public void checkParent(String aPolicyToExtend, Object aParentId, InterceptorContext aContext) {
    	if(isDisabled(aContext.getSessionContext())) return ;
        Long parentId = (Long) aParentId ;
        if(parentId>0) {
            MisLpu lpu = aContext.getEntityManager().find(MisLpu.class, aParentId) ;
             checkByParent(aPolicyToExtend, lpu, aContext.getSessionContext());
        }
    }
}
