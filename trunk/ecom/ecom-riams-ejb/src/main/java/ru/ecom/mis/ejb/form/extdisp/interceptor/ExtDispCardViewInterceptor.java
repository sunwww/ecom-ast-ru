package ru.ecom.mis.ejb.form.extdisp.interceptor;

import java.util.List;

import javax.persistence.EntityManager;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.mis.ejb.domain.extdisp.ExtDispCard;
import ru.ecom.mis.ejb.domain.medcase.ShortMedCase;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPriorityDiagnosis;
import ru.ecom.mis.ejb.form.extdisp.ExtDispCardForm;
import ru.ecom.mis.ejb.form.medcase.DiagnosisForm;
import ru.ecom.mis.ejb.form.medcase.hospital.interceptors.DischargeMedCaseSaveInterceptor;
import ru.ecom.mis.ejb.form.medcase.hospital.interceptors.DischargeMedCaseViewInterceptor;
import ru.ecom.mis.ejb.form.medcase.ticket.TicketMedCaseForm;
import ru.ecom.mis.ejb.form.medcase.ticket.interceptors.TicketMedCaseViewInterceptor;

public class ExtDispCardViewInterceptor implements IFormInterceptor {
	public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {

		ExtDispCardForm form = (ExtDispCardForm)aForm ;
		EntityManager manager = aContext.getEntityManager() ;
		//ExtDispCard card = (ExtDispCard)aEntity ;
	
		long id = form.getId() ;
		List<Object> list = manager.createNativeQuery(new StringBuilder().append(" select list(''||dispRisk_id) from ExtDispRisk where card_id='").append(id).append("' group by card_id").toString()).getResultList();
		if (list.size()>0) {
			form.setRisks( new StringBuilder().append(list.get(0)).toString()
					) ;
		}

		
	
	}
}