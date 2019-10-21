package ru.ecom.mis.ejb.form.patient;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.medcase.Diagnosis;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Mkb;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz= Diagnosis.class)
@Comment("Диагноз по пациенту")
@WebTrail(comment = "Диагноз по пациенту", nameProperties= "diagnosisInfo", view="entityView-mis_diagnosis.do")
@Parent(property="patient", parentForm= PatientForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Diagnosis")
//@AParentPrepareCreateInterceptors(
//        @AParentEntityFormInterceptor(DiagnosisPolyPreCreate.class)
//)
public class DiagnosisForm extends IdEntityForm {
	
	/** Выявлен атеросклероз БЦА */
	@Comment("Выявлен атеросклероз БЦА")
	@Persist
	public Boolean getIsFoundAtherosclerosis() {return theIsFoundAtherosclerosis;}
	public void setIsFoundAtherosclerosis(Boolean aIsFoundAtherosclerosis) {theIsFoundAtherosclerosis = aIsFoundAtherosclerosis;}
	/** Выявлен атеросклероз БЦА */
	private Boolean theIsFoundAtherosclerosis;
	/** Информация по специалисту */
	@Comment("Информация по специалисту")
	@Persist
	public String getWorkerInfo() {return theWorkerInfo;}
	public void setWorkerInfo(String aWorkerInfo) {theWorkerInfo = aWorkerInfo ;}
	
	/** Наименование */
	@Comment("Наименование")
	@Persist  
	public String getName() {return theName;}
	public void setName(String aNewProperty) {theName = aNewProperty;}
	
	/** Приоритет */
	@Comment("Приоритет")
	@Persist @Required
	public Long getPriority() {return thePriority;	}
	public void setPriority(Long aNewProperty) {thePriority = aNewProperty;}
	
	/** Острота */
	@Comment("Острота")
	@Persist 
	public Long getAcuity() {return theAcuity;}
	public void setAcuity(Long aNewProperty) {theAcuity = aNewProperty;}
	
	/** Первичность */
	@Comment("Первичность")
	@Persist 
	public Long getPrimary() {return thePrimary;}
	public void setPrimary(Long aNewProperty) {thePrimary = aNewProperty;}
	
	/** Заменен диагнозом */
	@Comment("Заменен диагнозом")
	public Long getDiagnosisNext() {return theDiagnosisNext;}
	public void setDiagnosisNext(Long aNewProperty) {theDiagnosisNext = aNewProperty;}
	
	/** Тип регистрации диагноза */
	@Comment("Тип регистрации диагноза")
	@Persist 
	public Long getRegistrationType() {	return theRegistrationType;	}
	public void setRegistrationType(Long aRegistrationType) {theRegistrationType = aRegistrationType;}
	
	/** Тип травмы */
	@Comment("Тип травмы")
	@Persist
	public Long getTraumaType() {return theTraumaType;	}
	public void setTraumaType(Long aNewProperty) {	theTraumaType = aNewProperty;}
	
	/** Выявлен при профосмотре */
	@Comment("Выявлен при профосмотре")
	@Persist	
	public Boolean getProphylacticExamination() {return theProphylacticExamination;	}
	public void setProphylacticExamination(Boolean aNewProperty) {theProphylacticExamination = aNewProperty;}
	
	/** Пациент */
	@Comment("Пациент")
	@Persist   
	public Long getPatient() {return thePatient;}
	public void setPatient(Long aNewProperty) {thePatient = aNewProperty;}
	
	/** МКБ10 */
	@Comment("МКБ10")
	@Persist @Mkb @Required
	public Long getIdc10() {return theIdc10;	}
	public void setIdc10(Long aNewProperty) {theIdc10 = aNewProperty;}
	
	/** Дата объявления недействительным */
	@Comment("Дата объявления недействительным")
	@Persist    @DateString @DoDateString
	public String getDisableDate() {return theDisableDate;	}
	public void setDisableDate(String aNewProperty) {theDisableDate = aNewProperty;}
	
	/** Предыдущий диагноз*/
	@Comment("Предыдущий диагноз")
	@Persist
	public Long getDiagnosisPrior() {return theDiagnosisPrior;	}
	public void setDiagnosisPrior(Long aNewProperty) {theDiagnosisPrior = aNewProperty;}
	
	/** Медицинский специалист */
	@Comment("Медицинский специалист")
	@Persist     
	public Long getMedicalWorker() {return theMedicalWorker;}
	public void setMedicalWorker(Long aNewProperty) {theMedicalWorker = aNewProperty;}
	
	/** МКБ10 причины травмы */
	@Comment("МКБ10 причины травмы")
	@Persist
	public Long getIdc10Reason() {return theIdc10Reason;}
	public void setIdc10Reason(Long aNewProperty) {theIdc10Reason = aNewProperty;}
	
	/** Дата уточнения диагноза */
	@Comment("Дата уточнения диагноза")
	@Persist   @DateString @DoDateString
	public String getAccurationDate() {return theAccurationDate;}
	public void setAccurationDate(String aNewProperty) {theAccurationDate = aNewProperty;}
	
	
	/** СМО */
	@Comment("СМО")
	@Persist 
	public Long getMedCase() {return theMedCase;}
	public void setMedCase(Long aMedCase) {theMedCase = aMedCase;}
	
	/**
	 * Справочник по ДТП
	 */
	@Comment("Справочник по ДТП")
	@Persist   
	public Long  getRoadTrafficInjury() {return theRoadTrafficInjury;}
	public void setRoadTrafficInjury(Long aNewProperty) {theRoadTrafficInjury = aNewProperty;}
	
	
	/** Дата установления*/
	@Comment("Дата установления")
	@Persist @DateString @DoDateString
	@Required
	public String getEstablishDate() {return theEstablishDate;	}
	public void setEstablishDate(String aNewProperty) {theEstablishDate = aNewProperty;}
	
	/** Клинико-статистическая группа */
	@Comment("Клинико-статистическая группа")
	@Persist
	public Long getClinicStatisticGroup() {return theClinicStatisticGroup;}
	public void setClinicStatisticGroup(Long aClinicStatisticGroup) {theClinicStatisticGroup = aClinicStatisticGroup;}
	
	/** Клинико-статистическая группа инфо*/
	@Comment("Клинико-статистическая группа инфо")
	@Persist
	public String getClinicStatisticGroupText() {return theClinicStatisticGroupText;}
	public void setClinicStatisticGroupText(String aClinicStatisticGroupText) {theClinicStatisticGroupText=aClinicStatisticGroupText;}
	
	
	/** Пользователь, создавший диагноз */
	@Comment("Пользователь, создавший диагноз")
	@Persist 
	public String getUsername() {return theUsername;}
	public void setUsername(String aUsername) {theUsername = aUsername;}
	
	/** МКБ10 текст */
	@Comment("МКБ10 текст")
	@Persist
	public String getIdc10Text() {return theIdc10Text;}
	public void setIdc10Text(String aIdc10Text) {theIdc10Text = aIdc10Text;}
	
	/** Диагноз информация */
	@Comment("Диагноз информация")
	@Persist
	public String getDiagnosisInfo() {return theDiagnosisInfo;}
	public void setDiagnosisInfo(String aDiagnosisInfo) {theDiagnosisInfo = aDiagnosisInfo;}
	
	/** Тип регистрации информация */
	@Comment("Тип регистрации информация")
	@Persist
	public String getRegistrationTypeInfo() {return theRegistrationTypeInfo;}
	public void setRegistrationTypeInfo(String aRegistrationTypeInfo) {theRegistrationTypeInfo = aRegistrationTypeInfo;	}
	
	/** Приоритет(ИНФО) */
	@Comment("Приоритет(ИНФО)")
	@Persist
	public String getPriorityDiagnosisText() {return thePriorityDiagnosisText;}
	public void setPriorityDiagnosisText(String aPriorityDiagnosisText) {thePriorityDiagnosisText = aPriorityDiagnosisText;}
	
	/** Перичность инфо */
	@Comment("Перичность инфо")
	@Persist
	public String getPrimaryInfo() {return thePrimaryInfo;}
	public void setPrimaryInfo(String aPrimaryInfo) {thePrimaryInfo = aPrimaryInfo;}

	/** Перичность инфо */
	private String thePrimaryInfo;
	/** Приоритет(ИНФО) */
	private String thePriorityDiagnosisText;
	/** МКБ10 текст */
	private String theIdc10Text;
	/** Пользователь, создавший диагноз */
	private String theUsername;
	/** Клинико-статистическая группа инфо*/
	private String theClinicStatisticGroupText ;
	/** Клинико-статистическая группа */
	private Long theClinicStatisticGroup;
	/** СМО */
	private Long theMedCase;
	/** Дата установления */
	private String theEstablishDate;
	private Long theRoadTrafficInjury;
	
	/** Тип регистрации информация */
	private String theRegistrationTypeInfo;
	/** Диагноз информация */
	private String theDiagnosisInfo;
	/** Дата уточнения диагноза */
	private String theAccurationDate;
	/** МКБ10 причины травмы */
	private Long theIdc10Reason;
	/** Медицинский специалист */
	private Long theMedicalWorker;
	/** Предыдущий диагноз */
	private Long theDiagnosisPrior;
	/** Дата объявления недействительным */
	private String theDisableDate;
	/** МКБ10 */
	private Long theIdc10;
	/** Пациент */
	private Long thePatient;
	/** Выявлен при профосмотре */
	private Boolean theProphylacticExamination;
	/** Тип травмы */
	private Long theTraumaType;
	/** Тип регистрации диагноза */
	private Long theRegistrationType;
	/** Заменен диагнозом*/
	private Long theDiagnosisNext;
	/** Первичность	 */
	private Long thePrimary;
	/** Острота */
	private Long theAcuity;
	/** Приоритет*/
	private Long thePriority;
	/** Наименование */
	private String theName;
	/** Информация по специалисту */
	private String theWorkerInfo;
	
	/** Характер заболевания */
	@Comment("Характер заболевания")
	@Persist @Required
	public Long getIllnesPrimary() {return theIllnesPrimary;}
	public void setIllnesPrimary(Long aIllnesPrimary) {theIllnesPrimary = aIllnesPrimary;}
	
	/** Характер заболевания */
	private Long theIllnesPrimary;
	/** Дата создания */
	@Comment("Дата создания")
	@Persist @DoDateString @DateString
	public String getCreateDate() {return theCreateDate;}
	public void setCreateDate(String aCreateDate) {theCreateDate = aCreateDate;}
	
	/** Пользователь, создавший запись */
	@Comment("Пользователь, создавший запись")
	@Persist
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aUsername) {theCreateUsername = aUsername;}
	
	/** Дата редактирования */
	@Comment("Дата редактирования")
	@Persist @DoDateString @DateString
	public String getEditDate() {return theEditDate;}
	public void setEditDate(String aEditDate) {theEditDate = aEditDate;}
	
	/** Пользователь, последний изменявший запись */
	@Comment("Пользователь, последний изменявший запись")
	@Persist
	public String getEditUsername() {return theEditUsername;}
	public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}
	
	/** Пользователь, последний изменявший запись */
	private String theEditUsername;
	/** Дата редактирования */
	private String theEditDate;
	/** Пользователь, создавший запись */
	private String theCreateUsername;
	/** Дата создания */
	private String theCreateDate;
}