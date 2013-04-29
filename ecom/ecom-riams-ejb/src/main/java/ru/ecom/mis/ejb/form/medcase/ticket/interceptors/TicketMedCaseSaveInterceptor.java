package ru.ecom.mis.ejb.form.medcase.ticket.interceptors;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.ejb.util.IFormInterceptor;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.mis.ejb.domain.medcase.Diagnosis;
import ru.ecom.mis.ejb.domain.medcase.ShortMedCase;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPriorityDiagnosis;
import ru.ecom.mis.ejb.domain.medcase.voc.VocTraumaType;
import ru.ecom.mis.ejb.form.medcase.DiagnosisForm;
import ru.ecom.mis.ejb.form.medcase.hospital.interceptors.DischargeMedCaseSaveInterceptor;
import ru.ecom.mis.ejb.form.medcase.hospital.interceptors.DischargeMedCaseViewInterceptor;
import ru.ecom.mis.ejb.form.medcase.ticket.TicketMedCaseForm;
import ru.ecom.poly.ejb.domain.voc.VocIllnesPrimary;
import ru.nuzmsh.util.StringUtil;

public class TicketMedCaseSaveInterceptor implements IFormInterceptor {
	 
	private final static Logger LOG = Logger.getLogger(DischargeMedCaseSaveInterceptor.class);
    private final static boolean CAN_DEBUG = LOG.isDebugEnabled();
	
    public void intercept(IEntityForm aForm, Object aEntity, EntityManager aManager) {
    	TicketMedCaseForm form = (TicketMedCaseForm)aForm ;
		ShortMedCase smc = (ShortMedCase)aEntity ;
		long id = form.getId() ;
		DiagnosisForm frm ;
		//Clinical
		VocPriorityDiagnosis vocPriorType = (VocPriorityDiagnosis)DischargeMedCaseSaveInterceptor.getVocByCode(aManager,"VocPriorityDiagnosis","1") ;
		
		frm = DischargeMedCaseViewInterceptor.getDiagnosis(aManager, id, null, "1", false) ;
		VocIllnesPrimary vip = isEmpty(form.getConcludingActuity())?null:aManager.find(VocIllnesPrimary.class, form.getConcludingActuity()) ;
		VocTraumaType vtt = isEmpty(form.getConcludingTrauma())?null:aManager.find(VocTraumaType.class, form.getConcludingTrauma()) ;
		VocIdc10 mkb = isEmpty(form.getConcludingMkb())?null:aManager.find(VocIdc10.class, form.getConcludingMkb()) ;
		Diagnosis diag = null ;
		if (frm!=null && frm.getId()>Long.valueOf(0)){
			diag = aManager.find(Diagnosis.class, frm.getId()) ;
		} else {
			diag = new Diagnosis() ;
			diag.setMedCase(smc) ;
			diag.setPriority(vocPriorType) ;
		}
		diag.setIllnesPrimary(vip) ;
		diag.setTraumaType(vtt) ;
		diag.setIdc10(mkb) ;
		diag.setName(form.getConcludingDiagnos()) ;
		diag.setEstablishDate(smc.getDateStart()) ;
		aManager.persist(diag) ;
		
    }

	public static boolean isEmpty(Long aLong) {
	    return (aLong == null)||(aLong == 0) ;
	}
	public static boolean isEmpty(String aString) {
	    return (aString == null)||(aString.trim().equals("")) ;
	}


}
