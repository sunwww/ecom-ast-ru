package ru.ecom.mis.ejb.form.medcase;

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
public class VisitProtocolForm extends ProtocolForm {
	
	/** Медицинская услуга */
	@Comment("Медицинская услуга")
	public Long getMedService() {return theMedService;}
	public void setMedService(Long aMedService) {theMedService = aMedService;}
	/** Медицинская услуга */
	private Long theMedService;

	/** Шаблон, на основе которого создано заключение */
	@Comment("Шаблон, на основе которого создано заключение")
	@Persist
	public Long getTemplateProtocol() {return theTemplateProtocol;}
	public void setTemplateProtocol(Long aTemplateProtocol) {theTemplateProtocol = aTemplateProtocol;}
	/** Шаблон, на основе которого создано заключение */
	private Long theTemplateProtocol;
	
	/** Параметры шаблона */
	@Comment("Параметры шаблона")
	public String getParams() {return theParams;}
	public void setParams(String aParams) {theParams = aParams;}
	/** Параметры шаблона */
	private String theParams;

	/** ВИзит */
	@Comment("ВИзит")
	@Persist
	public Long getMedCase() {return theMedCase;}
	public void setMedCase(Long aMedCase) {theMedCase = aMedCase;}

	/** Тип протокола */
	@Comment("Тип протокола")
	@Persist 
	public Long getType() {return theType;}
	public void setType(Long aType) {theType = aType;}

	/** Тип протокола инфо */
	@Comment("Тип протокола инфо")
	@Persist
	public String getTypeInfo() {return theTypeInfo;}
	public void setTypeInfo(String aTypeInfo) {theTypeInfo = aTypeInfo;}

	/** Для выписки */
	@Comment("Для выписки")
	@Persist
	public Boolean getIsDischarge() {return theIsDischarge;}
	public void setIsDischarge(Boolean aIsDischange) {theIsDischarge = aIsDischange;}

	/** Время регистрации */
	@Comment("Время регистрации")
	@Persist @Required
	@TimeString @DoTimeString
	public String getTimeRegistration() {return theTimeRegistration;}
	public void setTimeRegistration(String aTimeRegistration) {theTimeRegistration = aTimeRegistration;}
	
	/** Дата печати */
	@Comment("Дата печати")
	@Persist @DateString @DoDateString
	public String getPrintDate() {return thePrintDate;}
	public void setPrintDate(String aPrintDate) {thePrintDate = aPrintDate;}
	
	/** Время печати */
	@Comment("Время печати")
	@Persist @DoTimeString @TimeString
	public String getPrintTime() {return thePrintTime;}
	public void setPrintTime(String aPrintTime) {thePrintTime = aPrintTime;}

	/** Время печати */
	private String thePrintTime;
	/** Дата печати */
	private String thePrintDate;
	/** Время регистрации */
	private String theTimeRegistration;	
	/** Для выписки */
	private Boolean theIsDischarge;
	/** Тип протокола инфо */
	private String theTypeInfo;
	/** Тип протокола */
	private Long theType;
	/** ВИзит */
	private Long theMedCase;
	/** Дата редактирования */
	@Comment("Дата редактирования")
	@Persist @DoDateString @DateString
	public String getEditDate() {return theEditDate;}
	public void setEditDate(String aEditDate) {theEditDate = aEditDate;}

	/** Пользователь последний, изменявший запись */
	@Comment("Пользователь последний, изменявший запись")
	@Persist
	public String getEditUsername() {
		return theEditUsername;
	}

	public void setEditUsername(String aEditUsername) {
		theEditUsername = aEditUsername;
	}

	/** Пользователь последний, изменявший запись */
	private String theEditUsername;
	/** Дата редактирования */
	private String theEditDate;
	
	/** Тяжесть состояния */
	@Comment("Тяжесть состояния")
	@Persist
	public Long getState() {return theState;}
	public void setState(Long aState) {theState = aState;}

	/** Тяжесть состояния */
	private Long theState;
	
	/** Режим */
	@Comment("Режим")
	@Persist
	public Long getMode() {return theMode;}
	public void setMode(Long aMode) {theMode = aMode;}

	/** Режим */
	private Long theMode;

	/** Приоритет диагноза */
	@Comment("Приоритет диагноза")
	public Long getDiagnosisPriority() {return theDiagnosisPriority;}
	public void setDiagnosisPriority(Long aDiagnosisPriority) {theDiagnosisPriority = aDiagnosisPriority;}
	/** Приоритет диагноза */
	private Long theDiagnosisPriority ;

	/** Код МКБ-10 диагноза */
	@Comment("Код МКБ-10 диагноза")
	@Mkb
	public Long getDiagnosisIdc10() {return theDiagnosisIdc10;}
	public void setDiagnosisIdc10(Long aDiagnosisIdc10) {theDiagnosisIdc10 = aDiagnosisIdc10;}
	/** Код МКБ-10 диагноза */
	private Long theDiagnosisIdc10 ;

	/** Текст диагноза */
	@Comment("Текст диагноза")
	public String getDiagnosisText() {return theDiagnosisText;}
	public void setDiagnosisText(String aDiagnosisText) {theDiagnosisText = aDiagnosisText;}
	/** Текст диагноза */
	private String theDiagnosisText ;

	/** Характер заболевания */
	@Comment("Характер заболевания")
	public Long getDiagnosisIllnessPrimary() {return theDiagnosisIllnessPrimary;}
	public void setDiagnosisIllnessPrimary(Long aDiagnosisIllnessPrimary) {theDiagnosisIllnessPrimary = aDiagnosisIllnessPrimary;}
	/** Характер заболевания */
	private Long theDiagnosisIllnessPrimary ;

	/** Тип регистрации диагноза */
	@Comment("Тип регистрации диагноза")
	public Long getDiagnosisRegistrationType() {return theDiagnosisRegistrationType;}
	public void setDiagnosisRegistrationType(Long aDiagnosisRegistrationType) {theDiagnosisRegistrationType = aDiagnosisRegistrationType;}
	/** Тип регистрации диагноза */
	private Long theDiagnosisRegistrationType ;
	
	/** isCreateDiagnosis */
	@Comment("isCreateDiagnosis")
	public Boolean getIsCreateDiagnosis() {
		return theIsCreateDiagnosis;
	}

	public void setIsCreateDiagnosis(Boolean aIsCreateDiagnosis) {
		theIsCreateDiagnosis = aIsCreateDiagnosis;
	}

	/** isCreateDiagnosis */
	private Boolean theIsCreateDiagnosis;

	/** Поток обслуживания случая */
	@Comment("Поток обслуживания случая")
	public Long getServiceStream() {return theServiceStream;}
	public void setServiceStream(Long aServiceStream) {theServiceStream = aServiceStream;}
	/** Поток обслуживания случая */
	private Long theServiceStream ;

	/** Заголовок дневника */
	@Comment("Заголовок дневника")
	public String getTitle() {return theTitle;}
	public void setTitle(String aTitle) {theTitle = aTitle;}
	private String theTitle ="";
}