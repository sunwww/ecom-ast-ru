package ru.ecom.poly.ejb.form.interceptors;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.mis.ejb.form.medcase.PregProtocolForm;

import java.util.List;

public class ProtocolPregPreCreateInterceptor implements IParentFormInterceptor {

	public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
		MedCase parent = aContext.getEntityManager().find(MedCase.class, aParentId);

		String username = aContext.getSessionContext().getCallerPrincipal().getName();
		PregProtocolForm form = (PregProtocolForm) aForm;
		List<WorkFunction> listwf = aContext.getEntityManager().createQuery("from WorkFunction where secUser.login = :login")
				.setParameter("login", username).getResultList();
		if (!listwf.isEmpty()) {
			form.setSpecialist(listwf.get(0).getId());
		}
		if (parent.getServiceStream() == null)
			throw new IllegalStateException("Не указан поток обслуживания в СЛО!");
		else
			form.setServiceStream(parent.getServiceStream().getId());
	}
}