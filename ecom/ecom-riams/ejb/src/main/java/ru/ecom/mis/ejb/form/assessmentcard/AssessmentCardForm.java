package ru.ecom.mis.ejb.form.assessmentcard;

import javax.persistence.Column;

import lombok.Setter;
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
@Setter
public class AssessmentCardForm extends IdEntityForm{
	/** Тип карты оценки */
	@Comment("Тип карты оценки")
	@Persist
	public Long getTemplate() {return template;}
	/** Тип карты оценки */
	private Long template;
	
	/** Пациент */
	@Comment("Пациент")
	@Persist
	public Long getPatient() {return patient;}
	/** Пациент */
	private Long patient;
	
	/** Комментарий */
	@Comment("Комментарий")
	@Persist
	@Column(length=ColumnConstants.TEXT_MAXLENGHT)
	public String getComment() {return comment;}
	/** Комментарий */
	private String comment;

	/** Сумма баллов по карте */
	@Comment("Сумма баллов по карте")
	@Persist
	public Long getBallSum() {return ballSum;}
	/** Сумма баллов по карте */
	private Long ballSum;
	
	/** Дата создания */
	@Comment("Дата создания")
	@Persist
	public String getCreateDate() {return createDate;}

	/** Пользователь создавший запись */
	@Comment("Пользователь создавший запись")
	@Persist
	public String getCreateUsername() {return createUsername;}

	/** Пользователь создавший запись */
	private String createUsername;
	/** Дата создания */
	private String createDate;
	
	
	/** Параметры */
	@Comment("Параметры")
	public String getParams() {return params;}
	/** Параметры */
	private String params;
	
	/** Рабочая функция врача */
	@Comment("Рабочая функция врача")
	@Persist
	public Long getWorkFunction() {return workFunction;}
	/** Рабочая функция врача */
	private Long workFunction;
	
	/** Дата приема */
	@Comment("Дата приема")
	@Persist 
	@DateString @DoDateString
	public String getStartDate() {return startDate;}
	/** Дата приема */
	private String startDate;


	/** СЛО/визит создания */
	@Comment("СЛО/визит создания")
	@Persist
	public Long getMedcase() {return medcase;}
	/** СЛО/визит создания */
	private Long medcase;
}