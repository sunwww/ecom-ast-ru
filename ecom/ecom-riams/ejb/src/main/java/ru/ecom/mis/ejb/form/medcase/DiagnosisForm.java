package ru.ecom.mis.ejb.form.medcase;


import lombok.Setter;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.mis.ejb.domain.medcase.Diagnosis;
import ru.ecom.mis.ejb.form.medcase.interceptor.DiagnosisPolyPreCreate;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Mkb;
import ru.nuzmsh.forms.validator.validators.Required;

/**
*
*/
@EntityForm
@EntityFormPersistance(clazz= Diagnosis.class)
@Comment("Диагноз")
@WebTrail(comment = "Диагноз", nameProperties= "diagnosisInfo", view="entityView-mis_diagnosis.do")
@Parent(property="medCase", parentForm= MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Diagnosis")
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(DiagnosisPolyPreCreate.class)
)
@Setter
public class DiagnosisForm extends ru.ecom.mis.ejb.form.medcase.hospital.DiagnosisForm {
	
	/** Выявлен атеросклероз БЦА */
	@Comment("Выявлен атеросклероз БЦА")
	@Persist
	public Boolean getIsFoundAtherosclerosis() {return isFoundAtherosclerosis;}
	/** Выявлен атеросклероз БЦА */
	private Boolean isFoundAtherosclerosis;
	
	/** Информация по специалисту */
	@Comment("Информация по специалисту")
	@Persist
	public String getWorkerInfo() {return workerInfo;}

	/** Наименование */
	@Comment("Наименование")
	@Persist  @Required
	public String getName() {return name;}

	/** Приоритет */
	@Comment("Приоритет")
    @Persist @Required
	public Long getPriority() {return priority;	}

	/** Острота */
	@Comment("Острота")
    @Persist 
	public Long getAcuity() {return acuity;}

	/** Первичность */
	@Comment("Первичность")
    @Persist 
    public Long getPrimary() {return primary;}

	/** Заменен диагнозом */
	@Comment("Заменен диагнозом")
	public Long getDiagnosisNext() {return diagnosisNext;}

	/** Тип регистрации диагноза */
	@Comment("Тип регистрации диагноза")
	@Persist 
	public Long getRegistrationType() {	return registrationType;	}

	/** Тип травмы */
	@Comment("Тип травмы")
    @Persist
	public Long getTraumaType() {return traumaType;	}

	/** Выявлен при профосмотре */
	@Comment("Выявлен при профосмотре")
    @Persist	
	public Boolean getProphylacticExamination() {return prophylacticExamination;	}

	/** Пациент */
	@Comment("Пациент")
    @Persist   
	public Long getPatient() {return patient;}

	/** МКБ10 */
	@Comment("МКБ10")
    @Persist @Required @Mkb
	public Long getIdc10() {return idc10;	}

	/** Дата объявления недействительным */
	@Comment("Дата объявления недействительным")
	@Persist    @DateString @DoDateString
	public String getDisableDate() {return disableDate;	}

	/** Предыдущий диагноз*/
	@Comment("Предыдущий диагноз")
    @Persist
	public Long getDiagnosisPrior() {return diagnosisPrior;	}

	/** Медицинский специалист */
	@Comment("Медицинский специалист")
    @Persist     
	public Long getMedicalWorker() {return medicalWorker;}

	/** МКБ10 причины травмы */
	@Comment("МКБ10 причины травмы")
    @Persist
	public Long getIdc10Reason() {return idc10Reason;}

	/** Дата уточнения диагноза */
	@Comment("Дата уточнения диагноза")
	@Persist   @DateString @DoDateString
	public String getAccurationDate() {return accurationDate;}

	/** СМО */
	@Comment("СМО")
	@Persist @Required
	public Long getMedCase() {return medCase;}

	/**
	 * Справочник по ДТП
	 */
	@Comment("Справочник по ДТП")
	@Persist   
	public Long  getRoadTrafficInjury() {return roadTrafficInjury;}

	/** Дата установления*/
	@Comment("Дата установления")
	@Persist @DateString @DoDateString
	@Required
	public String getEstablishDate() {return establishDate;	}

	/** Клинико-статистическая группа */
	@Comment("Клинико-статистическая группа")
	@Persist
	public Long getClinicStatisticGroup() {return clinicStatisticGroup;}

	/** Клинико-статистическая группа инфо*/
	@Comment("Клинико-статистическая группа инфо")
	@Persist
	public String getClinicStatisticGroupText() {return clinicStatisticGroupText;}

	/** Пользователь, создавший диагноз */
	@Comment("Пользователь, создавший диагноз")
	@Persist 
	public String getUsername() {return username;}

	/** МКБ10 текст */
	@Comment("МКБ10 текст")
	@Persist
	public String getIdc10Text() {return idc10Text;}

	/** Диагноз информация */
	@Comment("Диагноз информация")
	@Persist
	public String getDiagnosisInfo() {return diagnosisInfo;}

	/** Тип регистрации информация */
	@Comment("Тип регистрации информация")
	@Persist
	public String getRegistrationTypeInfo() {return registrationTypeInfo;}

	/** Приоритет(ИНФО) */
	@Comment("Приоритет(ИНФО)")
	@Persist
	public String getPriorityDiagnosisText() {return priorityDiagnosisText;}

	/** Перичность инфо */
	@Comment("Перичность инфо")
	@Persist
	public String getPrimaryInfo() {return primaryInfo;}

	/** Перичность инфо */
	private String primaryInfo;
	/** Приоритет(ИНФО) */
	private String priorityDiagnosisText;
	/** МКБ10 текст */
	private String idc10Text;
	/** Пользователь, создавший диагноз */
	private String username;
	/** Клинико-статистическая группа инфо*/
	private String clinicStatisticGroupText ;
	/** Клинико-статистическая группа */
	private Long clinicStatisticGroup;
	/** СМО */
	private Long medCase;
	/** Дата установления */
	private String establishDate;
	private Long roadTrafficInjury;

	/** Тип регистрации информация */
	private String registrationTypeInfo;
	/** Диагноз информация */
	private String diagnosisInfo;
	/** Дата уточнения диагноза */
	private String accurationDate;
	/** МКБ10 причины травмы */
	private Long idc10Reason;
	/** Медицинский специалист */
	private Long medicalWorker;
	/** Предыдущий диагноз */
	private Long diagnosisPrior;
	/** Дата объявления недействительным */
	private String disableDate;
	/** МКБ10 */
	private Long idc10;
	/** Пациент */
	private Long patient;
	/** Выявлен при профосмотре */
	private Boolean prophylacticExamination;
	/** Тип травмы */
	private Long traumaType;
	/** Тип регистрации диагноза */
	private Long registrationType;
	/** Заменен диагнозом*/
	private Long diagnosisNext;
	/** Первичность	 */
	private Long primary;
	/** Острота */
	private Long acuity;
	/** Приоритет*/
	private Long priority;
	/** Наименование */
	private String name;
	/** Информация по специалисту */
	private String workerInfo;
	
	/** Доп.код мкб */
	@Comment("Доп.код мкб")
	@Persist
	public String getMkbAdc() {return mkbAdc;}

	/** Доп.код мкб */
	private String mkbAdc;
}
