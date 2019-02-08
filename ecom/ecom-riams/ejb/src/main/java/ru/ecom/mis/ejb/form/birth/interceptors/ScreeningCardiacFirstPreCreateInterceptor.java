package ru.ecom.mis.ejb.form.birth.interceptors;


import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.form.birth.ScreeningCardiacFirstForm;
import ru.ecom.mis.ejb.form.birth.ScreeningCardiacSecondForm;

import java.util.List;

public class ScreeningCardiacFirstPreCreateInterceptor implements IParentFormInterceptor {
	
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
    	if (aForm instanceof ScreeningCardiacFirstForm) {
			ScreeningCardiacFirstForm form = (ScreeningCardiacFirstForm) aForm ;
	    	List<Object[]> list=aContext.getEntityManager().createNativeQuery("select slo.id,ml.id from medcase slo left join mislpu ml on ml.id=slo.department_id where  slo.id='"+form.getMedCase()+"' and ml.IsCreateCardiacScreening='1'").getResultList() ;
	    	if (list.isEmpty()) throw new IllegalStateException("Кардиоскрининги новорождённых можно создавать только в отделении новорождённых!!!") ;
	    	//если было сделано ЭКГ, проставвляем ЭКГ
			if (aForm instanceof ScreeningCardiacSecondForm) {
				ScreeningCardiacSecondForm iiform = (ScreeningCardiacSecondForm) aForm ;
				list = aContext.getEntityManager().createNativeQuery("select d.record " +
						"from Prescription p " +
						"left join PrescriptionList pl on pl.id=p.prescriptionList_id " +
						"left join medcase slo on slo.id=pl.medcase_id " +
						"left join medservice ms on ms.id=p.medService_id\n" +
						"left join vocservicetype as vms on vms.id=ms.serviceType_id " +
						"left join workcalendartime wct on wct.id=p.calendartime_id " +
						"left join medcase m on m.id=wct.medcase_id " +
						"left join diary d on d.medcase_id=m.id " +
						"where slo.id='"+iiform.getMedCase()+"' and p.DTYPE='ServicePrescription' " +
						"and (vms.code='DIAGNOSTIC' or vms.code='SERVICE' or (vms.id is null and ms.id is not null)) " +
						"and canceldate is null " +
						"and d.record is not null " +
						"and ms.code='A05.10.006' " +
						"order by p.planStartDate desc limit 1").getResultList() ;
				if (!list.isEmpty())
					iiform.setECG(String.valueOf(list.get(0)));
				//Запрет на создание II этапа скрининга без I
				if (aContext.getSessionContext().isCallerInRole("/Policy/Mis/Pregnancy/CardiacScreening/ChekFirstExistBeforeCreateSecond")){
					list = aContext.getEntityManager().createNativeQuery("select count(id) from screeningcardiac  where medcase_id='"+form.getMedCase()+"'").getResultList() ;
					if (!list.isEmpty() && String.valueOf(list.get(0)).equals("0"))
						throw new IllegalStateException("<a href='entityParentPrepareCreate-stac_screeningCardiacFirst.do?id=" + form.getMedCase() + "'>I этап кардиоскрининга</a> в отд. новорождённых должен быть создан до II этапа!") ;
				}
			}
    	}
    }
}