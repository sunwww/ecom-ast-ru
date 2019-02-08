package ru.ecom.mis.ejb.form.psychiatry.interceptor;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.ejb.util.IFormInterceptor;
import ru.ecom.mis.ejb.form.psychiatry.LpuAreaPsychCareCardForm;
import ru.nuzmsh.util.format.DateFormat;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

public class LpuAreaPsychPreCreateInterceptor implements IParentFormInterceptor, IFormInterceptor {

	public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
		LpuAreaPsychCareCardForm form = (LpuAreaPsychCareCardForm) aForm ;
		String sql = "select count(*) as cnt1,count(case when transferDate is null and finishDate is null then 1 else null end) as cnt2,to_char(max(coalesce(transferDate,finishDate)),'dd.mm.yyyy') as maxdat from LpuAreaPsychCareCard where careCard_id='"+aParentId+"'" ;
		List<Object[]> list = aContext.getEntityManager().createNativeQuery(sql)
				.setMaxResults(1).getResultList() ;
		if (!list.isEmpty()) {
			Object[] objs = list.get(0) ;
			Long cnt = ConvertSql.parseLong(objs[0]) ;
			Long cntConsisting = ConvertSql.parseLong(objs[1]) ;
			if (cnt==null || cnt.equals(Long.valueOf(0))) {
				sql = "select coalesce(to_char(pcc.startDate,'dd.mm.yyyy'),to_char(pcc.firstPsychiatricVisitDate,'dd.mm.yyyy')) as dateStart,pcc.observationReason_id as vporid from PsychiatricCareCard pcc  where pcc.id='"+aParentId+"'" ;
				List<Object[]> list1 = aContext.getEntityManager().createNativeQuery(sql)
						.setMaxResults(1).getResultList() ;
				if (!list1.isEmpty()) {
					Object[] objs1 = list1.get(0) ;
					if (objs1[0]!=null && objs1[1]!=null) {
						form.setStartDate(""+objs1[0]) ;
						form.setObservationReason(ConvertSql.parseLong(objs1[1])) ;
					} else {
						throw new IllegalArgumentException("Перед созданием движения по участкам необходимо указать причину наблюдения и либо дату взятия на учет, либо дату первого обращения") ;
					}
				}
			} else {
				if (cntConsisting==null || cntConsisting.equals(Long.valueOf(0))) {
					form.setStartDate(""+objs[2]) ;
				} else if (cntConsisting.equals(Long.valueOf(1))) {
					
				} else {
					throw new IllegalArgumentException("Более 2х открытых движений по участкам!!!") ;
				}
			}
		}
		Date dateThis =new Date() ;
		form.setCreateDate(DateFormat.formatToDate(dateThis)) ;
		form.setCreateUsername(aContext.getSessionContext().getCallerPrincipal().toString());
	}

	public void intercept(IEntityForm aForm, Object aEntity, EntityManager aManager) {
		intercept(aForm, aEntity, null, null);
		
	}
}