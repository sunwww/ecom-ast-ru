package ru.ecom.poly.ejb.form.interceptors;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.form.medcase.VisitProtocolForm;
import ru.ecom.poly.ejb.domain.protocol.Protocol;

public class ProtocolViewInterceptor implements IFormInterceptor{

	public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
		Protocol protocol=(Protocol)aEntity;
		VisitProtocolForm form = (VisitProtocolForm ) aForm;
		if (protocol.getServiceMedCase()!=null) {
			form.setMedService(protocol.getServiceMedCase().getMedService().getId());
		}
		if (protocol.getMedCase()!=null && protocol.getMedCase().getServiceStream()!=null) form.setServiceStream(protocol.getMedCase().getServiceStream().getId());
	}
}
