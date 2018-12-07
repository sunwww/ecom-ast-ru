package ru.ecom.mis.ejb.form.extdisp;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AViewInterceptors;
import ru.ecom.mis.ejb.domain.extdisp.ExtDispAppointment;
import ru.ecom.mis.ejb.form.extdisp.interceptor.ExtDispAppointmentInterceptor;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;


@EntityForm
@EntityFormPersistance(clazz = ExtDispAppointment.class)
@Comment("Форма назначения для диспансеризации")
@WebTrail(comment = "Форма назначения для диспансеризации"
, nameProperties= "id", list="entityParentList-extDisp_appointment.do", view="entityParentView-extDisp_appointment.do")
@Parent(property="dispCard", parentForm=ExtDispCardForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/ExtDisp/Card")
@AViewInterceptors(
        @AEntityFormInterceptor(ExtDispAppointmentInterceptor.class)
)
public class ExtDispAppointmentForm extends IdEntityForm{
	
	/** Назначение */
	@Comment("Назначение")
	@Persist @Required
	public Long getAppointment(){return theAppointment;}
	public void setAppointment(Long aAppointment){theAppointment = aAppointment;}
	private Long theAppointment ;
	
	/** Вид обследования */
	@Comment("Вид обследования")
	@Persist
	public Long getKindSurvey(){return theKindSurvey;}
	public void setKindSurvey(Long aKindSurvey){theKindSurvey = aKindSurvey;}
	private Long theKindSurvey;
	
	/** Профиль */
	@Comment("Профиль")
	@Persist
	public Long getProfile(){return theProfile;}
	public void setProfile(Long aProfile){theProfile = aProfile;}
	private Long theProfile ;
	
	/** Дисп. карта */
	@Comment("Дисп. карта")
	@Persist
	public Long getDispCard(){return theDispCard;}
	public void setDispCard(Long aDispCard){theDispCard = aDispCard;}
	private Long theDispCard;

}
