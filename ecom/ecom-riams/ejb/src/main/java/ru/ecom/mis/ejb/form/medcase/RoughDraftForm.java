package ru.ecom.mis.ejb.form.medcase;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.ACreateInterceptors;
import ru.ecom.ejb.services.entityform.interceptors.AEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.ASaveInterceptors;
import ru.ecom.poly.ejb.form.interceptors.ProtocolSaveInterceptor;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.TimeString;

@Comment("Заключение")
@EntityForm
@EntityFormPersistance(clazz = ru.ecom.poly.ejb.domain.protocol.RoughDraft.class)
@WebTrail(comment = "Заключение", nameProperties = "info", view = "entityParentView-smo_draftProtocol.do")
@Parent(property = "medCase", parentForm = MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Protocol")
@ASaveInterceptors(
        @AEntityFormInterceptor(ProtocolSaveInterceptor.class)
)
@ACreateInterceptors(
        @AEntityFormInterceptor(ProtocolSaveInterceptor.class)
)
public class RoughDraftForm extends VisitProtocolForm {
    /** Дата регистрации талона */
    @Persist 
    @Comment("Дата регистрации талона")
    @DateString @DoDateString 
    public String getDateRegistration() {    return theDateRegistration ;}
    public void setDateRegistration(String aDateRegistration ) {  theDateRegistration = aDateRegistration ; }
    
	/** Время регистрации */
	@Comment("Время регистрации")
	@Persist 
	@TimeString @DoTimeString
	public String getTimeRegistration() {return theTimeRegistration;}
	public void setTimeRegistration(String aTimeRegistration) {theTimeRegistration = aTimeRegistration;}
	
	/** Время регистрации */
	private String theTimeRegistration;	
    /** Дата регистрации талона */
    private String theDateRegistration ;

}
