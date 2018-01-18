package ru.ecom.mis.ejb.form.medcase.hospital;

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

public class HitechMedicalCaseForm extends IdEntityForm{
	/** Случай оказания мед. помощи */
	@Persist
	@Comment("Случай оказания мед. помощи")
	public Long getMedCase() {return theMedCase;}
	public void setMedCase(Long  aMedCase) {theMedCase = aMedCase;}
	/** Случай оказания мед. помощи */
	private Long  theMedCase;
	
	/** Вид ВМП */
	@Comment("Вид ВМП")
	@Persist @Required
	public Long  getKind() {return theKind;}
	public void setKind(Long  aKind) {theKind = aKind;}
	/** Вид ВМП */
	private Long  theKind;
	
	/** Метод ВМП */
	@Comment("Метод ВМП")
	@Persist @Required
	public Long  getMethod() {return theMethod;}
	public void setMethod(Long  aMethod) {theMethod = aMethod;}
	/** Метод ВМП */
	private Long  theMethod;
	
	/** Дата выдачи талона нв ВМП */
	@Comment("Дата выдачи талона нв ВМП")
	@Persist
	@DateString @DoDateString
	public String getTicketDate() {return theTicketDate;}
	public void setTicketDate(String aTicketDate) {theTicketDate = aTicketDate;}
	/** Дата выдачи талона нв ВМП */
	private String theTicketDate;
	
	/** Дата планируемой госпитализации */
	@Comment("Дата планируемой госпитализации")
	@Persist
	@DateString @DoDateString
	public String getPlanHospDate() {return thePlanHospDate;}
	public void setPlanHospDate(String aPlanHospDate) {thePlanHospDate = aPlanHospDate;}
	/** Дата планируемой госпитализации */
	private String thePlanHospDate;
	
	/** Дата создания */
	@Comment("Дата создания")
	@Persist
	@DateString @DoDateString
	public String getCreateDate() {return theCreateDate;}
	public void setCreateDate(String aCreateDate) {theCreateDate = aCreateDate;}
	/** Дата создания */
	private String theCreateDate;
	
	/** Дата редактирования */
	@Comment("Дата редактирования")
	@Persist
	@DateString @DoDateString
	public String getEditDate() {return theEditDate;}
	public void setEditDate(String aEditDate) {theEditDate = aEditDate;}
	/** Дата редактирования */
	private String theEditDate;
	
	/** Пользователь, создавший запись */
	@Comment("Пользователь, создавший запись")
	@Persist
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
	/** Пользователь, создавший запись */
	private String theCreateUsername;
	
	/** Пользователь, редактировавший запись */
	@Comment("Пользователь, редактировавший запись")
	@Persist
	public String getEditUsername() {return theEditUsername;}
	public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}
	/** Пользователь, редактировавший запись */
	private String theEditUsername;
	
	/** Источник финансировани */
	@Comment("Источник финансировани")
	public Long getFinanceSource() {return theFinanceSource;}
	public void setFinanceSource(Long aFinanceSource) {theFinanceSource = aFinanceSource;}
	/** Источник финансировани */
	private Long theFinanceSource;

	/** Количество установленных стентов */
	@Comment("Количество установленных стентов")
	@Persist
	public Long getStantAmount() {return theStantAmount;}
	public void setStantAmount(Long aStantAmount) {theStantAmount = aStantAmount;}
	/** Количество установленных стентов */
	private Long theStantAmount ;

	/** Номер талона ВМП */
	@Comment("Номер талона ВМП")
	@Persist
	public String getTicketNumber() {return theTicketNumber;}
	public void setTicketNumber(String aTicketNumber) {theTicketNumber = aTicketNumber;}
	/** Номер талона ВМП */
	private String theTicketNumber ;

	/** Список диагнозов */
	@Comment("Список диагнозов")
	public String getDiagnosis() {return theDiagnosis;}
	public void setDiagnosis(String aDiagnosis) {theDiagnosis = aDiagnosis;}
	/** Список диагнозов */
	private String theDiagnosis ;

	/** Модель пациента */
	@Comment("Модель пациента")
	public String getPatientModel() {return thePatientModel;}
	public void setPatientModel(String aPatientModel) {thePatientModel = aPatientModel;}
	/** Модель пациента */
	private String thePatientModel ;
	
}
