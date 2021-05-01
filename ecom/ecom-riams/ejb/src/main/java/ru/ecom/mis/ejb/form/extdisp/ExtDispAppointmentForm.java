package ru.ecom.mis.ejb.form.extdisp;

import lombok.Setter;
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
@Setter
public class ExtDispAppointmentForm extends IdEntityForm{
	
	/** Назначение */
	@Comment("Назначение")
	@Persist @Required
	public Long getAppointment(){return appointment;}
	private Long appointment ;
	
	/** Вид обследования */
	@Comment("Вид обследования")
	@Persist
	public Long getKindSurvey(){return kindSurvey;}
	private Long kindSurvey;
	
	/** Профиль */
	@Comment("Профиль")
	@Persist
	public Long getProfile(){return profile;}
	private Long profile ;
	
	/** Дисп. карта */
	@Comment("Дисп. карта")
	@Persist
	public Long getDispCard(){return dispCard;}
	private Long dispCard;

}
