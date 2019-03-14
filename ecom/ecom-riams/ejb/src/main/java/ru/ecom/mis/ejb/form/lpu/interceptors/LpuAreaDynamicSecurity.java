package ru.ecom.mis.ejb.form.lpu.interceptors;

import ru.ecom.ejb.services.entityform.interceptors.IDynamicParentSecurityInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.IDynamicSecurityInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.domain.lpu.LpuArea;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;

/**
 *
 */
public class LpuAreaDynamicSecurity implements IDynamicParentSecurityInterceptor, IDynamicSecurityInterceptor {


    public void check(String aPolicyAction, Object aId, InterceptorContext aContext) {
        LpuArea area = aContext.getEntityManager().find(LpuArea.class, aId);
        String policy = "/Policy/Mis/MisLpuDynamic/" + area.getLpu().getId() + "/Areas/" + aId + "/" + aPolicyAction;
        if (!aContext.getSessionContext().isCallerInRole(policy)) {
            try {
                MisLpuDynamicSecurity.checkByParent(aPolicyAction
                        , area.getLpu(), aContext.getSessionContext());
            } catch (IllegalStateException e) {
                throw new IllegalStateException("А также  " + policy, e);
            }
        }
    }

    public void checkParent(String aPolicyAction, Object aParentId, InterceptorContext aContext) {
        MisLpu lpu = aContext.getEntityManager().find(MisLpu.class, aParentId);
        MisLpuDynamicSecurity.checkByParent(aPolicyAction
                , lpu, aContext.getSessionContext());
    }

}
