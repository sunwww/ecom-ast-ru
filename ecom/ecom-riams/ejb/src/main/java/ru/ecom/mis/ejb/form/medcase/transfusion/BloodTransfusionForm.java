package ru.ecom.mis.ejb.form.medcase.transfusion;

import lombok.Setter;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.ejb.services.entityform.interceptors.AViewInterceptors;
import ru.ecom.mis.ejb.domain.medcase.BloodTransfusion;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.ecom.mis.ejb.form.medcase.transfusion.interceptor.BloodTransfusionViewInterceptor;
import ru.ecom.mis.ejb.form.medcase.transfusion.interceptor.TransfusionPreCreateInterceptor;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoIntegerString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.IntegerString;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz= BloodTransfusion.class)
@Comment("Протокол трансфузии")
@WebTrail(comment = "Протокол трансфузии", nameProperties= "id", view="entityParentView-trans_blood.do",list = "entityParentList-trans_transfusion.do")
@Parent(property="medCase", parentForm= MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Transfusion/Blood")
@AViewInterceptors(
        @AEntityFormInterceptor(BloodTransfusionViewInterceptor.class)
)
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(TransfusionPreCreateInterceptor.class)
)
@Setter
public class BloodTransfusionForm extends TransfusionForm{
	/** Препарат крови */
	@Comment("Препарат крови")
	@Persist @Required
	public Long getBloodPreparation() {return bloodPreparation;}

	/** Группа крови пациента */
	@Comment("Группа крови пациента")
	@Persist @Required
	public Long getPatientBloodGroup() {return patientBloodGroup;}

	/** Резус-фактор пациента */
	@Comment("Резус-фактор пациента")
	@Persist @Required
	public Long getPatientRhesusFactor() {return patientRhesusFactor;}

	/** Группа крови препарата */
	@Comment("Группа крови препарата")
	@Persist @Required
	public Long getPreparationBloodGroup() {return preparationBloodGroup;}

	/** Резус-фактор препарата */
	@Comment("Резус-фактор препарата")
	@Persist @Required
	public Long getPreparationRhesusFactor() {return preparationRhesusFactor;}

	/** Препарат крови */
	private Long bloodPreparation;
	/** Группа крови пациента */
	private Long patientBloodGroup;
	/** Резус-фактор пациента */
	private Long patientRhesusFactor;
	/** Группа крови препарата */
	private Long preparationBloodGroup;
	/** Резус-фактор препарата */
	private Long preparationRhesusFactor;
	
	/** Проверка группы крови пациента */
	@Comment("Проверка группы крови пациента")
	@Persist
	public Long getPatBloodGroupCheck() {return patBloodGroupCheck;}

	/** Проверка группы крови препарата */
	@Comment("Проверка группы крови препарата")
	@Persist
	public Long getPrepBloodGroupCheck() {return prepBloodGroupCheck;}

	/** Проверка группы крови препарата */
	private Long prepBloodGroupCheck;
	/** Проверка группы крови пациента */
	private Long patBloodGroupCheck;
	
	/** Наблюдение сразу после переливания */
	@Comment("Наблюдение сразу после переливания")
	public TransfusionMonitoringForm getMonitorForm0() {return monitorForm0;}

	/** Наблюдение через 1 час */
	@Comment("Наблюдение через 1 час")
	public TransfusionMonitoringForm getMonitorForm1() {return monitorForm1;}

	/** Наблюдение через 2 часа */
	@Comment("Наблюдение через 2 часа")
	public TransfusionMonitoringForm getMonitorForm2() {return monitorForm2;}

	/** Наблюдение через 2 часа */
	private TransfusionMonitoringForm monitorForm2 = new TransfusionMonitoringForm();
	/** Наблюдение через 1 час */
	private TransfusionMonitoringForm monitorForm1 = new TransfusionMonitoringForm();
	/** Наблюдение сразу после переливания */
	private TransfusionMonitoringForm monitorForm0 = new TransfusionMonitoringForm();
	
	/** Реактив 1 */
	@Comment("Реактив 1")
	public TransfusionReagentForm getReagentForm1() {return reagentForm1;}

	/** Реактив 2 */
	@Comment("Реактив 2")
	public TransfusionReagentForm getReagentForm2() {return reagentForm2;}

	/** Реактив 3 */
	@Comment("Реактив 3")
	public TransfusionReagentForm getReagentForm3() {return reagentForm3;}

	/** Реактив 3 */
	private TransfusionReagentForm reagentForm3 = new TransfusionReagentForm();
	/** Реактив 2 */
	private TransfusionReagentForm reagentForm2 = new TransfusionReagentForm();
	/** Реактив 1 */
	private TransfusionReagentForm reagentForm1 = new TransfusionReagentForm();
	
	/** Частота пульса */
	@Comment("Частота пульса")
	@Persist
	public Integer getPulseRateBT() {return pulseRateBT;}

	/** Температура */
	@Comment("Температура")
	@Persist @DoIntegerString @IntegerString
	public String getTemperatureBT() {return temperatureBT;}

	/** Артериальное давление (верхнее) */
	@Comment("Артериальное давление (верхнее)")
	@Persist
	public Integer getBloodPressureTopBT() {return bloodPressureTopBT;}

	/** Артериальное давление (нижнее) */
	@Comment("Артериальное давление (нижнее)")
	@Persist
	public Integer getBloodPressureLowerBT() {return bloodPressureLowerBT;}

	/** Частота дыхательных движений */
	@Comment("Частота дыхательных движений")
	@Persist
	public Integer getRespiratoryRateBT() {return respiratoryRateBT;}

	/** Цвет сыворотки (биол. проба) */
	@Comment("Цвет сыворотки (биол. проба)")
	@Persist
	public Long getSerumColorBT() {return serumColorBT;}

	/** Переливание прекращено */
	@Comment("Переливание прекращено")
	@Persist
	public Boolean getIsBreakBT() {return isBreakBT;}

	/** Состояние удовлетворительное */
	@Comment("Состояние удовлетворительное")
	@Persist
	public Long getStateBT() {return stateBT;}

	/** Тяжелый боьлной */
	@Comment("Тяжелый боьлной")
	@Persist
	public Boolean getIsIllPatientsBT() {return isIllPatientsBT;}

	/** Жалобы */
	@Comment("Жалобы")
	@Persist
	public String getLamentBT() {return lamentBT;}

	/** Совместимость биопробы */
	@Comment("Совместимость биопробы")
	@Persist @Required
	public Long getBioProbeCompatibility() {return bioProbeCompatibility;}

	/** Организация, осуществившая инд. подбор */
	@Comment("Организация, осуществившая инд. подбор")
	@Persist @Required
	public Long getIndOrg() {return indOrg;}
	/** Организация, осуществившая инд. подбор */
	private Long indOrg;

	/** Совместимость биопробы */
	private Long bioProbeCompatibility;
	/** Жалобы */
	private String lamentBT;
	/** Тяжелый боьлной */
	private Boolean isIllPatientsBT;
	/** Состояние удовлетворительное */
	private Long stateBT;
	/** Переливание прекращено */
	private Boolean isBreakBT;
	/** Цвет сыворотки (биол. проба) */
	private Long serumColorBT;
	/** Частота дыхательных движений */
	private Integer respiratoryRateBT;
	/** Артериальное давление (нижнее) */
	private Integer bloodPressureLowerBT;
	/** Артериальное давление (верхнее) */
	private Integer bloodPressureTopBT;
	/** Температура */
	private String temperatureBT;
	/** Частота пульса */
	private Integer pulseRateBT;
	
	
	/** Метод 1 по инд. совместимости */
	@Comment("Метод 1 по инд. совместимости")
	@Persist
	public Long getMethodPT1() {return methodPT1;}

	/** 1. Реактив по инд. совместимости */
	@Comment("1. Реактив по инд. совместимости")
	@Persist
	public String getReagentPT1() {return reagentPT1;}

	/** 1. Серия реактива по инд. совместимости */
	@Comment("1. Серия реактива по инд. совместимости")
	@Persist
	public String getReagentSeriesPT1() {return reagentSeriesPT1;}

	/** 1. Срок годности */
	@Comment("1. Срок годности")
	@Persist @DateString @DoDateString
	public String getReagentExpDatePT1() {return reagentExpDatePT1;}

	/** Результат. Совместима */
	@Comment("Результат. Совместима")
	@Persist
	public Long getResultGoodPT1() {return resultGoodPT1;}

	/** Результат. Совместима */
	private Long resultGoodPT1;
	/** 1. Срок годности */
	private String reagentExpDatePT1;
	/** 1. Серия реактива по инд. совместимости */
	private String reagentSeriesPT1;
	/** 1. Реактив по инд. совместимости */
	private String reagentPT1;
	/** Метод 1 по инд. совместимости */
	private Long methodPT1;
	
	/** Метод 2 по инд. совместимости */
	@Comment("Метод 2 по инд. совместимости")
	@Persist
	public Long getMethodPT2() {return methodPT2;}

	/** 2. Реактив по инд. совместимости */
	@Comment("2. Реактив по инд. совместимости")
	@Persist
	public String getReagentPT2() {return reagentPT2;}

	/** 2. Серия реактива по инд. совместимости */
	@Comment("2. Серия реактива по инд. совместимости")
	@Persist
	public String getReagentSeriesPT2() {return reagentSeriesPT2;}

	/** 2. Срок годности */
	@Comment("2. Срок годности")
	@Persist @DateString @DoDateString
	public String getReagentExpDatePT2() {return reagentExpDatePT2;}

	/** Результат. Совместима */
	@Comment("Результат. Совместима")
	@Persist
	public Long getResultGoodPT2() {return resultGoodPT2;}

	/** Результат. Совместима */
	private Long resultGoodPT2;
	/** 1. Срок годности */
	private String reagentExpDatePT2;
	/** 1. Серия реактива по инд. совместимости */
	private String reagentSeriesPT2;
	/** 1. Реактив по инд. совместимости */
	private String reagentPT2;
	/** Метод 1 по инд. совместимости */
	private Long methodPT2;
	
	/** Биологическая проба */
	@Comment("Биологическая проба")
	public String getBiologicTest() {return biologicTest;}

	/** Биологическая проба */
	private String biologicTest;

	/** Справочник процедур в биологической пробе при переливаниях */
	@Comment("Справочник процедур в биологической пробе при переливаниях")
	@Persist
	public Long getBloodBioProbProcedure() {return bloodBioProbProcedure;}

	/** Кровотечение усилилось или нет без видимой причины*/
	@Comment("Кровотечение усилилось или нет без видимой причины")
	@Persist
	public Boolean getWasBleedingIncreased() {return wasBleedingIncreased;}

	/** АД снизилось или нет без видимой причины*/
	@Comment("АД снизилось или нет без видимой причины")
	@Persist
	public Boolean getWasADDecreased() {return wasADDecreased;}

	/** Пульс участился или нет без видимой причины*/
	@Comment("Пульс участился или нет без видимой причины")
	@Persist
	public Boolean getWasPulseIncreased() {return wasPulseIncreased;}

	/** Пульс участился или нет без видимой причины*/
	@Comment("Пульс участился или нет без видимой причины")
	@Persist
	public Boolean getWasUrineColorChanged() {return wasUrineColorChanged;}

	/** 3. Реактив по инд. совместимости */
	@Comment("3. Реактив по инд. совместимости")
	@Persist
	public String getReagentPT3() {return reagentPT3;}

	/** 3. Серия реактива по инд. совместимости */
	@Comment("3. Серия реактива по инд. совместимости")
	@Persist
	public String getReagentSeriesPT3() {return reagentSeriesPT3;}

	/** 3. Срок годности */
	@Comment("3. Срок годности")
	@Persist @DateString @DoDateString
	public String getReagentExpDatePT3() {return reagentExpDatePT3;}

	@Persist @DateString @DoDateString
	public String getDateResearch() {return dateResearch;}

	/** Справочник процедур в биологической пробе при переливаниях */
	private Long bloodBioProbProcedure;
	/** Кровотечение усилилось или нет без видимой причины (при переливании под наркозом или в коме)*/
	private Boolean wasBleedingIncreased;
	/** АД снизилось или нет без видимой причины (при переливании под наркозом или в коме)*/
	private Boolean wasADDecreased;
	/** Пульс участился или нет без видимой причины (при переливании под наркозом или в коме)*/
	private Boolean wasPulseIncreased;
	/** Цвет мочи изменился или нет без видимой причины (при переливании под наркозом или в коме)*/
	private Boolean wasUrineColorChanged;
	/** 3. Срок годности */
	private String reagentExpDatePT3;
	/** 3. Серия реактива по инд. совместимости */
	private String reagentSeriesPT3;
	/** 3. Реактив по инд. совместимости */
	private String reagentPT3;
	/** Дата исследования */
	private String dateResearch;

	/** Фенотип донора */
	@Comment("Фенотип донора")
	@Persist
	public String getPhenotypeDon() {return phenotypeDon;}

	/** Фенотип донора C */
	@Comment("Фенотип донора C")
	@Persist
	public Boolean getPhenotypeDonC() {return phenotypeDonC;}

	/** Фенотип донора с */
	@Comment("Фенотип донора с")
	@Persist
	public Boolean getPhenotypeDonc1() {return phenotypeDonc1;}

	/** Фенотип донора Е */
	@Comment("Фенотип донора Е")
	@Persist
	public Boolean getPhenotypeDonD() {return phenotypeDonD;}

	/** Фенотип донора e */
	@Comment("Фенотип донора e")
	@Persist
	public Boolean getPhenotypeDonE() {return phenotypeDonE;}

	/** Фенотип донора E */
	@Comment("Фенотип донора E")
	@Persist
	public Boolean getPhenotypeDone1() {return phenotypeDone1;}

	/** Фенотип донора не определялся */
	@Comment("Фенотип донора не определялся")
	@Persist
	public Boolean getPhenotypeDonNone() {return phenotypeDonNone;}

	/** Заключение совместимо/нет */
	@Comment("Заключение совместимо/нет")
	@Persist
	public Long getConclusion() {return conclusion;}

	/** Совместимость на плоскости */
	@Comment("Совместимость на плоскости")
	@Persist
	public Long getPlaneCompatibility() {return planeCompatibility;}

	/** Основные симптомы */
	@Comment("Основные симптомы")
	@Persist
	public String getMainSymptoms() {return mainSymptoms;}

	/** Заключение совместимо/нет */
	private Long conclusion;
	/** Заключение совместимо/нет */
	private Long planeCompatibility;
	/** Фенотип донора E */
	private Boolean phenotypeDone1;
	/** Фенотип донора e */
	private Boolean phenotypeDonE;
	/** Фенотип донора Е */
	private Boolean phenotypeDonD;
	/** Фенотип донора с */
	private Boolean phenotypeDonc1;
	/** Фенотип донора C */
	private Boolean phenotypeDonC;
	/** Фенотип донора не определялся*/
	private Boolean phenotypeDonNone;
	/** Фенотип донора */
	private String phenotypeDon;
	/** Основные симптомы */
	private String mainSymptoms;
}