package ru.ecom.mis.ejb.form.medcase.hospital;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;

import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.mis.ejb.domain.medcase.HitechMedicalCase;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.ecom.mis.ejb.form.medcase.hospital.interceptors.HitechMedicalCasePreCreateInterceptor;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;


@Comment("Случай оказания ВМП")
@EntityForm
@EntityFormPersistance(clazz=HitechMedicalCase.class)
@WebTrail(comment = "Случай ВМП", nameProperties= "id", view="entityParentView-stac_vmpCase.do",shortView="entityShortView-stac_vmpCase.do"
	,shortList="entityParentShortList-stac_slo.do",list="entityParentList-stac_slo.do")
@Parent(property="medCase", parentForm= MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Stac/Ssl/HitechMedCase")
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(HitechMedicalCasePreCreateInterceptor.class)
)
@Setter

public class HitechMedicalCaseForm extends IdEntityForm{
	/** Случай оказания мед. помощи */
	@Persist
	@Comment("Случай оказания мед. помощи")
	public Long getMedCase() {return medCase;}
	/** Случай оказания мед. помощи */
	private Long  medCase;
	
	/** Вид ВМП */
	@Comment("Вид ВМП")
	@Persist @Required
	public Long  getKind() {return kind;}
	/** Вид ВМП */
	private Long  kind;
	
	/** Метод ВМП */
	@Comment("Метод ВМП")
	@Persist @Required
	public Long  getMethod() {return method;}
	/** Метод ВМП */
	private Long  method;
	
	/** Дата выдачи талона нв ВМП */
	@Comment("Дата выдачи талона нв ВМП")
	@Persist
	@DateString @DoDateString
	public String getTicketDate() {return ticketDate;}
	/** Дата выдачи талона нв ВМП */
	private String ticketDate;
	
	/** Дата планируемой госпитализации */
	@Comment("Дата планируемой госпитализации")
	@Persist
	@DateString @DoDateString
	public String getPlanHospDate() {return planHospDate;}
	/** Дата планируемой госпитализации */
	private String planHospDate;
	
	/** Дата создания */
	@Comment("Дата создания")
	@Persist
	@DateString @DoDateString
	public String getCreateDate() {return createDate;}
	/** Дата создания */
	private String createDate;
	
	/** Дата редактирования */
	@Comment("Дата редактирования")
	@Persist
	@DateString @DoDateString
	public String getEditDate() {return editDate;}
	/** Дата редактирования */
	private String editDate;
	
	/** Пользователь, создавший запись */
	@Comment("Пользователь, создавший запись")
	@Persist
	public String getCreateUsername() {return createUsername;}
	/** Пользователь, создавший запись */
	private String createUsername;
	
	/** Пользователь, редактировавший запись */
	@Comment("Пользователь, редактировавший запись")
	@Persist
	public String getEditUsername() {return editUsername;}
	/** Пользователь, редактировавший запись */
	private String editUsername;
	
	/** Источник финансировани */
	@Comment("Источник финансировани")
	public Long getFinanceSource() {return financeSource;}
	/** Источник финансировани */
	private Long financeSource;

	/** Количество установленных стентов */
	@Comment("Количество установленных стентов")
	@Persist
	public Long getStantAmount() {return stantAmount;}
	/** Количество установленных стентов */
	private Long stantAmount ;

	/** Номер талона ВМП */
	@Comment("Номер талона ВМП")
	@Persist
	public String getTicketNumber() {return ticketNumber;}
	/** Номер талона ВМП */
	private String ticketNumber ;

	/** Список диагнозов */
	@Comment("Список диагнозов")
	public String getDiagnosis() {return diagnosis;}
	/** Список диагнозов */
	private String diagnosis ;

	/** Модель пациента */
	@Comment("Модель пациента")
	public String getPatientModel() {return patientModel;}
	/** Модель пациента */
	private String patientModel ;
	
}
