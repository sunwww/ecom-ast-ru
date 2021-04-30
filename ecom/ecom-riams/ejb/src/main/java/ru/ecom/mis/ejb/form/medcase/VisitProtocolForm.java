package ru.ecom.mis.ejb.form.medcase;

import lombok.Setter;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.ejb.services.entityform.interceptors.AViewInterceptors;
import ru.ecom.poly.ejb.form.interceptors.ProtocolPreCreateInterceptor;
import ru.ecom.poly.ejb.form.interceptors.ProtocolViewInterceptor;
import ru.ecom.poly.ejb.form.protocol.ProtocolForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Mkb;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

@Comment("Протокол")
@EntityForm
@EntityFormPersistance(clazz = ru.ecom.poly.ejb.domain.protocol.Protocol.class)
@WebTrail(comment = "Протокол", nameProperties = "info"
	, view = "entityParentView-smo_visitProtocol.do"
		,list = "entityParentList-smo_visitProtocol.do"
		)
@Parent(property = "medCase", parentForm = MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Protocol")
/*
@ASaveInterceptors(
        @AEntityFormInterceptor(ProtocolSaveInterceptor.class)
)
@ACreateInterceptors(
        @AEntityFormInterceptor(ProtocolSaveInterceptor.class)
)
*/
@AViewInterceptors (
		@AEntityFormInterceptor(ProtocolViewInterceptor.class)
)
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(ProtocolPreCreateInterceptor.class)
)
@Setter
public class VisitProtocolForm extends ProtocolForm {
	
	/** Медицинская услуга */
	@Comment("Медицинская услуга")
	public Long getMedService() {return medService;}
	/** Медицинская услуга */
	private Long medService;

	/** Шаблон, на основе которого создано заключение */
	@Comment("Шаблон, на основе которого создано заключение")
	@Persist
	public Long getTemplateProtocol() {return templateProtocol;}
	/** Шаблон, на основе которого создано заключение */
	private Long templateProtocol;
	
	/** Параметры шаблона */
	@Comment("Параметры шаблона")
	public String getParams() {return params;}
	/** Параметры шаблона */
	private String params;

	/** Визит */
	@Comment("Визит")
	@Persist
	public Long getMedCase() {return medCase;}

	/** Тип протокола */
	@Comment("Тип протокола")
	@Persist 
	public Long getType() {return type;}

	/** Тип протокола инфо */
	@Comment("Тип протокола инфо")
	@Persist
	public String getTypeInfo() {return typeInfo;}

	/** Для выписки */
	@Comment("Для выписки")
	@Persist
	public Boolean getIsDischarge() {return isDischarge;}

	/** Время регистрации */
	@Comment("Время регистрации")
	@Persist @Required
	@TimeString @DoTimeString
	public String getTimeRegistration() {return timeRegistration;}

	/** Дата печати */
	@Comment("Дата печати")
	@Persist @DateString @DoDateString
	public String getPrintDate() {return printDate;}

	/** Время печати */
	@Comment("Время печати")
	@Persist @DoTimeString @TimeString
	public String getPrintTime() {return printTime;}

	/** Время печати */
	private String printTime;
	/** Дата печати */
	private String printDate;
	/** Время регистрации */
	private String timeRegistration;	
	/** Для выписки */
	private Boolean isDischarge;
	/** Тип протокола инфо */
	private String typeInfo;
	/** Тип протокола */
	private Long type;
	/** Визит */
	private Long medCase;
	/** Дата редактирования */
	@Comment("Дата редактирования")
	@Persist @DoDateString @DateString
	public String getEditDate() {return editDate;}

	/** Пользователь последний, изменявший запись */
	@Comment("Пользователь последний, изменявший запись")
	@Persist
	public String getEditUsername() {
		return editUsername;
	}

	/** Пользователь последний, изменявший запись */
	private String editUsername;
	/** Дата редактирования */
	private String editDate;
	
	/** Тяжесть состояния */
	@Comment("Тяжесть состояния")
	@Persist
	public Long getState() {return state;}

	/** Тяжесть состояния */
	private Long state;
	
	/** Режим */
	@Comment("Режим")
	@Persist
	public Long getMode() {return mode;}

	/** Режим */
	private Long mode;

	/** Приоритет диагноза */
	@Comment("Приоритет диагноза")
	public Long getDiagnosisPriority() {return diagnosisPriority;}
	/** Приоритет диагноза */
	private Long diagnosisPriority ;

	/** Код МКБ-10 диагноза */
	@Comment("Код МКБ-10 диагноза")
	@Mkb
	public Long getDiagnosisIdc10() {return diagnosisIdc10;}
	/** Код МКБ-10 диагноза */
	private Long diagnosisIdc10 ;

	/** Текст диагноза */
	@Comment("Текст диагноза")
	public String getDiagnosisText() {return diagnosisText;}
	/** Текст диагноза */
	private String diagnosisText ;

	/** Характер заболевания */
	@Comment("Характер заболевания")
	public Long getDiagnosisIllnessPrimary() {return diagnosisIllnessPrimary;}
	/** Характер заболевания */
	private Long diagnosisIllnessPrimary ;

	/** Тип регистрации диагноза */
	@Comment("Тип регистрации диагноза")
	public Long getDiagnosisRegistrationType() {return diagnosisRegistrationType;}
	/** Тип регистрации диагноза */
	private Long diagnosisRegistrationType ;
	
	/** isCreateDiagnosis */
	@Comment("isCreateDiagnosis")
	public Boolean getIsCreateDiagnosis() {
		return isCreateDiagnosis;
	}

	/** isCreateDiagnosis */
	private Boolean isCreateDiagnosis;

	/** Поток обслуживания случая */
	@Comment("Поток обслуживания случая")
	public Long getServiceStream() {return serviceStream;}
	/** Поток обслуживания случая */
	private Long serviceStream ;

	/** Заголовок дневника */
	@Comment("Заголовок дневника")
	public String getTitle() {return title;}
	private String title ="";
}