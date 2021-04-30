package ru.ecom.ejb.services.voc.helper;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.services.entityform.interceptors.IDynamicParentSecurityInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.IDynamicSecurityInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.services.util.ConvertSql;

import javax.ejb.SessionContext;
import java.util.List;
@Getter
@Setter
public class DymanicSecurity implements IDynamicSecurityInterceptor, IDynamicParentSecurityInterceptor{

	public DymanicSecurity (String aClazz, String aParentField, String aPolicy) {
		setPolicy(aPolicy) ;
		setParentField(aParentField) ;
		setClazz(aClazz) ;
	}
	public void check(String aPolicyAction, Object aId, InterceptorContext aContext) {

	}
	
	private StringBuilder getPolicy(StringBuilder aAppend,Long aId, InterceptorContext aContext) {
		StringBuilder result = new StringBuilder() ;
		List<Object[]> list = aContext.getEntityManager()
    		.createNativeQuery("select count(*),parent_id from " + clazz + "where id=:id").setParameter("id", aId).getResultList() ;
		Object[] obj = null ;
		if (list.size()>0) {
			obj = list.get(0) ;
			result.append("/").append(obj[1]).append(aAppend) ;
		}
		if (obj!=null && obj[1]!=null) {
			result = getPolicy(result,ConvertSql.parseLong(obj[1]),aContext) ;
		}
		return result ;
	}

	public void checkParent(String aPolicyAction, Object aId, InterceptorContext aContext) {
		if(isDisabled(aContext.getSessionContext())) return ;
        StringBuilder sb = new StringBuilder();
		List<Object[]> list = aContext.getEntityManager()
        	.createNativeQuery("select count(*),parent_id from " + clazz + "where id=:id").setParameter("id", aId).getResultList() ;
        Object[] obj = list.isEmpty() ? null : list.get(0) ;
        Long count =obj!=null ? ConvertSql.parseLong(obj[0]) : null;
        while(count!=null && count>0) {
            StringBuilder policy = new StringBuilder("/Policy/Mis/MisLpuDynamic/");
            policy.append(aId) ;
            policy.append('/') ;
            policy.append(aPolicyAction) ;
            if(aContext.getSessionContext().isCallerInRole(policy.toString())) {
                return ;
            } else {
                if(sb.length()!=0) {
                    sb.append(" или ") ;
                }
                sb.append(policy) ;
            }
        }
        throw new IllegalStateException("Нет политик безопасности "+sb) ;
		
	}

	/**
     * Отключена ли проверка
     * @param aContext
     * @return
     */
    private boolean isDisabled(SessionContext aContext) {
    	return aContext.isCallerInRole(policy+"/Disable") ;
    }
    

	/** Класс */
	private String clazz;
	/** Поле родителя */
	private String parentField;
	/** Динамическая политика безопасности */
	private String policy;

}
