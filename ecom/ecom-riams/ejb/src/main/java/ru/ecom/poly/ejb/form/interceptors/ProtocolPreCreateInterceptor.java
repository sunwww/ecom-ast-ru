package ru.ecom.poly.ejb.form.interceptors;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.medcase.ServiceMedCase;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.mis.ejb.form.medcase.VisitProtocolForm;

import java.util.List;

public class ProtocolPreCreateInterceptor implements IParentFormInterceptor {

	public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
		MedCase parent = aContext.getEntityManager().find(MedCase.class, aParentId);
		if (aContext.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Protocol/CreateOnlyInMedService") && !(parent instanceof ServiceMedCase)) {
			throw new IllegalArgumentException("У Вас стоит ограничение!! Создать заключения можно только в созданной услуге!!!");
		}
		String username = aContext.getSessionContext().getCallerPrincipal().getName();
		VisitProtocolForm form = (VisitProtocolForm) aForm;
		List<WorkFunction> listwf = aContext.getEntityManager().createQuery("from WorkFunction where secUser.login = :login")
				.setParameter("login", username).getResultList();
		if (!listwf.isEmpty()) {
			form.setSpecialist(listwf.get(0).getId());
		}
		if (parent.getServiceStream()==null)
			throw new IllegalStateException("Не указан поток обслуживания в СЛО!");
		else
			form.setServiceStream(parent.getServiceStream().getId());

		//Milamesher #137 28012019 - проверка на наличие кардиоскрининга новорождённых в случае если:
		//это - отделение новорождённых
		//первый дневник
		//скрининг ещё не был создан
		Object val = aContext.getEntityManager().createNativeQuery("select case when ml.IsCreateCardiacScreening='1' " +
				"and (select count(id) from diary where medcase_id='" + form.getMedCase() + "')=0 " +
				"and (select count(id) from screeningcardiac where dtype='ScreeningCardiacFirst' and medcase_id='" + form.getMedCase() + "')=0 then '1' else '0' end " +
				"from medcase slo left join mislpu ml on ml.id=slo.department_id where  slo.id='" + form.getMedCase() + "'").getSingleResult();
		if (String.valueOf(val).equals("1"))
			throw new IllegalStateException("<a href='entityParentPrepareCreate-stac_screeningCardiacFirst.do?id=" + form.getMedCase() + "'>I этап кардиоскрининга</a> в отд. новорождённых должен быть создан до создания первого дневника!");
	}
}