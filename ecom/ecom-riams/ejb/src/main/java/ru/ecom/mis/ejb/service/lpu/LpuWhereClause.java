package ru.ecom.mis.ejb.service.lpu;

import java.util.List;

import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.services.quickquery.IWhereClause;
import ru.ecom.ejb.services.quickquery.QuickQueryContext;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.form.lpu.interceptors.MisLpuDynamicSecurity;

public class LpuWhereClause implements IWhereClause {

	public String createWhereClause(QuickQueryContext aContext) {
		List<MisLpu> list = aContext.getManager().createQuery("from MisLpu").getResultList() ;
		MisLpuDynamicSecurity sec = new MisLpuDynamicSecurity() ;
		boolean firstPassed = false ;
		StringBuilder sb = new StringBuilder() ;
		InterceptorContext ctx = new InterceptorContext(aContext.getManager(), aContext.getSessionContext()) ;
		
		for(MisLpu lpu : list) {
			try {
				sec.check("View", lpu.getId(), ctx) ;
				if(!firstPassed) {
					firstPassed = true ;
				} else {
					sb.append(" or ") ;
				}
				sb.append("mislpu.id="+lpu.getId()) ;
			} catch (IllegalStateException e) {
				//e.printStackTrace();
			}
		}
		return sb.toString() ; //"mislpu.id=1113579" ;
	}

}
