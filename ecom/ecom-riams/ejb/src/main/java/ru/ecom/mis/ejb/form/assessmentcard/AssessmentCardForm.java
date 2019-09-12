package ru.ecom.mis.ejb.form.assessmentcard;

import javax.persistence.Column;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.ACreateInterceptors;
import ru.ecom.ejb.services.entityform.interceptors.AEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.ASaveInterceptors;
import ru.ecom.ejb.services.util.ColumnConstants;
import ru.ecom.mis.ejb.domain.assessmentcard.AssessmentCard;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.form.patient.PatientForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;


@EntityForm
@EntityFormPersistance(clazz= AssessmentCard.class)
@Comment("Карта оценки")
@WebTrail(comment = "Карта оценки", nameProperties= "id", view="entityParentView-mis_assessmentCard.do" ,list = "entityList-mis_assessmentCard.do")
@Parent(property = "patient", parentForm=PatientForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/AssessmentCard")
@ACreateInterceptors(
		@AEntityFormInterceptor(AssessmentCardSaveInterceptor.class)
)
@ASaveInterceptors(
		@AEntityFormInterceptor(AssessmentCardSaveInterceptor.class)
)
public class AssessmentCardForm extends IdEntityForm{
	/** Тип карты оценки */
	@Comment("Тип карты оценки")
	@Persist
	public Long getTemplate() {return theTemplate;}
	public void setTemplate(Long aTemplate) {theTemplate = aTemplate;}
	/** Тип карты оценки */
	private Long theTemplate;
	
	/** Пациент */
	@Comment("Пациент")
	@Persist
	public Long getPatient() {return thePatient;}
	public void setPatient(Long aPatient) {thePatient = aPatient;}
	/** Пациент */
	private Long thePatient;
	
	/** Комментарий */
	@Comment("Комментарий")
	@Persist
	@Column(length=ColumnConstants.TEXT_MAXLENGHT)
	public String getComment() {return theComment;}
	public void setComment(String aComment) {theComment = aComment;}
	/** Комментарий */
	private String theComment;

	/** Сумма баллов по карте */
	@Comment("Сумма баллов по карте")
	@Persist
	public Long getBallSum() {return theBallSum;}
	public void setBallSum(Long aBallSum) {theBallSum = aBallSum;}
	/** Сумма баллов по карте */
	private Long theBallSum;
	
	/** Дата создания */
	@Comment("Дата создания")
	@Persist
	public String getCreateDate() {return theCreateDate;}
	public void setCreateDate(String aCreateDate) {theCreateDate = aCreateDate;}

	/** Пользователь создавший запись */
	@Comment("Пользователь создавший запись")
	@Persist
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}

	/** Пользователь создавший запись */
	private String theCreateUsername;
	/** Дата создания */
	private String theCreateDate;
	
	
	/** Параметры */
	@Comment("Параметры")
	public String getParams() {return theParams;}
	public void setParams(String aParams) {theParams = aParams;}
	/** Параметры */
	private String theParams;
	
	/** Рабочая функция врача */
	@Comment("Рабочая функция врача")
	@Persist
	public Long getWorkFunction() {return theWorkFunction;}
	public void setWorkFunction(Long aWorkFunction) {theWorkFunction = aWorkFunction;}
	/** Рабочая функция врача */
	private Long theWorkFunction;
	
	/** Дата приема */
	@Comment("Дата приема")
	@Persist 
	@DateString @DoDateString
	public String getStartDate() {return theStartDate;}
	public void setStartDate(String aStartDate) {theStartDate = aStartDate;}
	/** Дата приема */
	private String theStartDate;


	/** СЛО/визит создания */
	@Comment("СЛО/визит создания")
	@Persist
	public Long getMedcase() {return theMedcase;}
	public void setMedcase(Long aMedcase) {theMedcase = aMedcase;}
	/** СЛО/визит создания */
	private Long theMedcase;
}