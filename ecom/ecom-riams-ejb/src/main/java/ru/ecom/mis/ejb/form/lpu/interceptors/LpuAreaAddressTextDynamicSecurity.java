package ru.ecom.mis.ejb.form.lpu.interceptors;

import ru.ecom.ejb.services.entityform.interceptors.IDynamicParentSecurityInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.IDynamicSecurityInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.domain.lpu.LpuAreaAddressText;

/**
 * @author  azviagin
 */
public class LpuAreaAddressTextDynamicSecurity implements IDynamicParentSecurityInterceptor, IDynamicSecurityInterceptor {

    public void check(String aPolicyAction, Object aId, InterceptorContext aContext) {
        LpuAreaAddressText at = aContext.getEntityManager().find(LpuAreaAddressText.class, aId) ;
        checkParent(aPolicyAction, at.getArea().getId(), aContext);
    }

    public void checkParent(String aPolicyAction, Object aParentId, InterceptorContext aContext) {
        theLpuAreaDynamicSecurity.check(aPolicyAction, aParentId, aContext);
    }

    private final LpuAreaDynamicSecurity theLpuAreaDynamicSecurity = new LpuAreaDynamicSecurity();
}
