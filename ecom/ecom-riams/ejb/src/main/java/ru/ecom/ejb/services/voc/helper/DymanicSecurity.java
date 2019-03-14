package ru.ecom.ejb.services.voc.helper;

import ru.ecom.ejb.services.entityform.interceptors.IDynamicParentSecurityInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.IDynamicSecurityInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.services.util.ConvertSql;

import javax.ejb.SessionContext;
import java.util.List;

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
    		.createNativeQuery("select count(*),parent_id from " + theClazz + "where id=:id").setParameter("id", aId).getResultList() ;
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
        	.createNativeQuery("select count(*),parent_id from " + theClazz + "where id=:id").setParameter("id", aId).getResultList() ;
        Object[] obj = list.isEmpty() ? null : list.get(0) ;
        Long count =obj!=null ? ConvertSql.parseLong(obj[0]) : null;
     //   Long parent = ConvertSql.parseLong(obj[1]);
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
                //aLpu = aLpu.getParent() ;
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
    	return aContext.isCallerInRole(thePolicy+"/Disable") ;
    }
    
    
	/** Динамическая политика безопасности */
	public String getPolicy() {return thePolicy;}
	public void setPolicy(String aPolicy) {thePolicy = aPolicy;}

	/** Поле родителя */
	public String getParentField() {return theParentField;	}
	public void setParentField(String aParentField) {theParentField = aParentField;}

	/** Класс */
	public String getClazz() {return theClazz;}
	public void setClazz(String aClazz) {theClazz = aClazz;}

	/** Класс */
	private String theClazz;
	/** Поле родителя */
	private String theParentField;
	/** Динамическая политика безопасности */
	private String thePolicy;

}
