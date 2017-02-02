package ru.ecom.mis.ejb.form.extdisp;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AViewInterceptors;
import ru.ecom.mis.ejb.domain.extdisp.ExtDispAppointment;
import ru.ecom.mis.ejb.domain.extdisp.voc.VocExtDispAppointment;
import ru.ecom.mis.ejb.form.extdisp.interceptor.ExtDispAppointmentInterceptor;
import ru.ecom.mis.ejb.form.patient.PatientForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
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
	
	/** Специальнось врача */
	@Comment("Специальнось врача")
	@Persist
	public Long getWorkFunction(){return theWorkFunction ;}
	public void setWorkFunction (Long aWorkFunction ){theWorkFunction  = aWorkFunction ;}
	private Long theWorkFunction;
	
	/** Вид обследования */
	@Comment("Вид обследования")
	@Persist
	public Long getKindSurvey(){return theKindSurvey;}
	public void setKindSurvey(Long aKindSurvey){theKindSurvey = aKindSurvey;}
	private Long theKindSurvey;
	
	/** Профиль мед. помощи */
	@Comment("Профиль мед. помощи")
	@Persist
	public Long getKindMedHelp(){return theKindMedHelp;}
	public void setKindMedHelp(Long aKindMedHelp){theKindMedHelp = aKindMedHelp;}
	private Long theKindMedHelp ;
	
	/** Профиль коек */
	@Comment("Профиль коек")
	@Persist
	public Long getBedType(){return theBedType;}
	public void setBedType(Long aBedType){theBedType = aBedType;}
	private Long theBedType ;
	
	/** Дисп. карта */
	@Comment("Дисп. карта")
	@Persist
	public Long getDispCard(){return theDispCard;}
	public void setDispCard(Long aDispCard){theDispCard = aDispCard;}
	private Long theDispCard;
	
}
