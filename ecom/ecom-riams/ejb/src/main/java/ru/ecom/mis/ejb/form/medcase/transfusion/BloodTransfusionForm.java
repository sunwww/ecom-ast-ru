package ru.ecom.mis.ejb.form.medcase.transfusion;

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
@Comment("Переливание донорской крови и её компонентов")
@WebTrail(comment = "Переливание донорской крови и её компонентов", nameProperties= "id", view="entityParentView-trans_blood.do",list = "entityParentList-trans_transfusion.do")
@Parent(property="medCase", parentForm= MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Transfusion/Blood")
@AViewInterceptors(
        @AEntityFormInterceptor(BloodTransfusionViewInterceptor.class)
)
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(TransfusionPreCreateInterceptor.class)
)
public class BloodTransfusionForm extends TransfusionForm{
	/** Препарат крови */
	@Comment("Препарат крови")
	@Persist @Required
	public Long getBloodPreparation() {return theBloodPreparation;}
	public void setBloodPreparation(Long aBloodPreparation) {theBloodPreparation = aBloodPreparation;}

	/** Группа крови пациента */
	@Comment("Группа крови пациента")
	@Persist @Required
	public Long getPatientBloodGroup() {return thePatientBloodGroup;}
	public void setPatientBloodGroup(Long aPatientBloodGroup) {thePatientBloodGroup = aPatientBloodGroup;}
	
	/** Резус-фактор пациента */
	@Comment("Резус-фактор пациента")
	@Persist @Required
	public Long getPatientRhesusFactor() {return thePatientRhesusFactor;}
	public void setPatientRhesusFactor(Long aPatientRhesusFactor) {thePatientRhesusFactor = aPatientRhesusFactor;}
	
	/** Группа крови препарата */
	@Comment("Группа крови препарата")
	@Persist @Required
	public Long getPreparationBloodGroup() {return thePreparationBloodGroup;}
	public void setPreparationBloodGroup(Long aPreparationBloodGroup) {thePreparationBloodGroup = aPreparationBloodGroup;}

	/** Резус-фактор препарата */
	@Comment("Резус-фактор препарата")
	@Persist @Required
	public Long getPreparationRhesusFactor() {return thePreparationRhesusFactor;}
	public void setPreparationRhesusFactor(Long aPreparationRhesusFactor) {thePreparationRhesusFactor = aPreparationRhesusFactor;}

	/** Донор */
	@Comment("Донор")
	@Persist @Required
	public String getDonor() {return theDonor;}
	public void setDonor(String aDonor) {theDonor = aDonor;}

	/** Донор */
	private String theDonor;
	/** Препарат крови */
	private Long theBloodPreparation;
	/** Группа крови пациента */
	private Long thePatientBloodGroup;
	/** Резус-фактор пациента */
	private Long thePatientRhesusFactor;
	/** Группа крови препарата */
	private Long thePreparationBloodGroup;
	/** Резус-фактор препарата */
	private Long thePreparationRhesusFactor;
	
	/** Проверка группы крови пациента */
	@Comment("Проверка группы крови пациента")
	@Persist
	public Long getPatBloodGroupCheck() {return thePatBloodGroupCheck;}
	public void setPatBloodGroupCheck(Long aPatBloodGroupCheck) {thePatBloodGroupCheck = aPatBloodGroupCheck;}

	/** Проверка группы крови препарата */
	@Comment("Проверка группы крови препарата")
	@Persist
	public Long getPrepBloodGroupCheck() {return thePrepBloodGroupCheck;}
	public void setPrepBloodGroupCheck(Long aPrepBloodGroupCheck) {thePrepBloodGroupCheck = aPrepBloodGroupCheck;}

	/** Проверка группы крови препарата */
	private Long thePrepBloodGroupCheck;
	/** Проверка группы крови пациента */
	private Long thePatBloodGroupCheck;
	
	/** Макроскопическая оценка крови */
	@Comment("Макроскопическая оценка крови")
	@Persist @Required
	public Long getMacroBall() {return theMacroBall;}
	public void setMacroBall(Long aMacroBall) {theMacroBall = aMacroBall;}

	/** Макроскопическая оценка крови */
	private Long theMacroBall;
	
	/** Наблюдение сразу после переливания */
	@Comment("Наблюдение сразу после переливания")
	public TransfusionMonitoringForm getMonitorForm0() {return theMonitorForm0;}
	public void setMonitorForm0(TransfusionMonitoringForm aMonitorForm0) {theMonitorForm0 = aMonitorForm0;}

	/** Наблюдение через 1 час */
	@Comment("Наблюдение через 1 час")
	public TransfusionMonitoringForm getMonitorForm1() {return theMonitorForm1;}
	public void setMonitorForm1(TransfusionMonitoringForm aMonitorForm1) {theMonitorForm1 = aMonitorForm1;}

	/** Наблюдение через 2 часа */
	@Comment("Наблюдение через 2 часа")
	public TransfusionMonitoringForm getMonitorForm2() {return theMonitorForm2;}
	public void setMonitorForm2(TransfusionMonitoringForm aMonitorForm2) {theMonitorForm2 = aMonitorForm2;}

	/** Наблюдение через 2 часа */
	private TransfusionMonitoringForm theMonitorForm2 = new TransfusionMonitoringForm();
	/** Наблюдение через 1 час */
	private TransfusionMonitoringForm theMonitorForm1 = new TransfusionMonitoringForm();
	/** Наблюдение сразу после переливания */
	private TransfusionMonitoringForm theMonitorForm0 = new TransfusionMonitoringForm();
	
	/** Реактив 1 */
	@Comment("Реактив 1")
	public TransfusionReagentForm getReagentForm1() {return theReagentForm1;}
	public void setReagentForm1(TransfusionReagentForm aReagentForm1) {theReagentForm1 = aReagentForm1;}

	/** Реактив 2 */
	@Comment("Реактив 2")
	public TransfusionReagentForm getReagentForm2() {return theReagentForm2;}
	public void setReagentForm2(TransfusionReagentForm aReagentForm2) {theReagentForm2 = aReagentForm2;}

	/** Реактив 2 */
	private TransfusionReagentForm theReagentForm2 = new TransfusionReagentForm();
	/** Реактив 1 */
	private TransfusionReagentForm theReagentForm1 = new TransfusionReagentForm();
	
	/** Частота пульса */
	@Comment("Частота пульса")
	@Persist
	public Integer getPulseRateBT() {return thePulseRateBT;}
	public void setPulseRateBT(Integer aPulseRate) {thePulseRateBT = aPulseRate;}

	/** Температура */
	@Comment("Температура")
	@Persist @DoIntegerString @IntegerString
	public String getTemperatureBT() {return theTemperatureBT;}
	public void setTemperatureBT(String aTemperature) {theTemperatureBT = aTemperature;}

	/** Артериальное давление (верхнее) */
	@Comment("Артериальное давление (верхнее)")
	@Persist
	public Integer getBloodPressureTopBT() {return theBloodPressureTopBT;}
	public void setBloodPressureTopBT(Integer aBloodPressureTop) {theBloodPressureTopBT = aBloodPressureTop;}

	/** Артериальное давление (нижнее) */
	@Comment("Артериальное давление (нижнее)")
	@Persist
	public Integer getBloodPressureLowerBT() {return theBloodPressureLowerBT;}
	public void setBloodPressureLowerBT(Integer aBloodPressureLower) {theBloodPressureLowerBT = aBloodPressureLower;}
	
	/** Частота дыхательных движений */
	@Comment("Частота дыхательных движений")
	@Persist
	public Integer getRespiratoryRateBT() {return theRespiratoryRateBT;}
	public void setRespiratoryRateBT(Integer aRespiratoryRate) {theRespiratoryRateBT = aRespiratoryRate;}

	/** Цвет сыворотки (биол. проба) */
	@Comment("Цвет сыворотки (биол. проба)")
	@Persist
	public Long getSerumColorBT() {return theSerumColorBT;}
	public void setSerumColorBT(Long aSerumColor) {theSerumColorBT = aSerumColor;}

	/** Переливание прекращено */
	@Comment("Переливание прекращено")
	@Persist
	public Boolean getIsBreakBT() {return theIsBreakBT;}
	public void setIsBreakBT(Boolean aIsBreakBT) {theIsBreakBT = aIsBreakBT;}

	/** Состояние удовлетворительное */
	@Comment("Состояние удовлетворительное")
	@Persist
	public Long getStateBT() {return theStateBT;}
	public void setStateBT(Long aStateBT) {theStateBT = aStateBT;}

	/** Тяжелый боьлной */
	@Comment("Тяжелый боьлной")
	@Persist
	public Boolean getIsIllPatientsBT() {return theIsIllPatientsBT;}
	public void setIsIllPatientsBT(Boolean aIsIllPatients) {theIsIllPatientsBT = aIsIllPatients;}

	/** Жалобы */
	@Comment("Жалобы")
	@Persist
	public String getLamentBT() {return theLamentBT;}
	public void setLamentBT(String aLamentBT) {theLamentBT = aLamentBT;}

	/** Жалобы */
	private String theLamentBT;
	/** Тяжелый боьлной */
	private Boolean theIsIllPatientsBT;
	/** Состояние удовлетворительное */
	private Long theStateBT;
	/** Переливание прекращено */
	private Boolean theIsBreakBT;
	/** Цвет сыворотки (биол. проба) */
	private Long theSerumColorBT;
	/** Частота дыхательных движений */
	private Integer theRespiratoryRateBT;
	/** Артериальное давление (нижнее) */
	private Integer theBloodPressureLowerBT;
	/** Артериальное давление (верхнее) */
	private Integer theBloodPressureTopBT;
	/** Температура */
	private String theTemperatureBT;
	/** Частота пульса */
	private Integer thePulseRateBT;
	
	
	/** Метод 1 по инд. совместимости */
	@Comment("Метод 1 по инд. совместимости")
	@Persist
	public Long getMethodPT1() {return theMethodPT1;}
	public void setMethodPT1(Long aMethodPT1) {theMethodPT1 = aMethodPT1;}

	/** 1. Реактив по инд. совместимости */
	@Comment("1. Реактив по инд. совместимости")
	@Persist
	public String getReagentPT1() {return theReagentPT1;}
	public void setReagentPT1(String aReagentPT1) {theReagentPT1 = aReagentPT1;}

	/** 1. Серия реактива по инд. совместимости */
	@Comment("1. Серия реактива по инд. совместимости")
	@Persist
	public String getReagentSeriesPT1() {return theReagentSeriesPT1;}
	public void setReagentSeriesPT1(String aReagentSeriesPT1) {theReagentSeriesPT1 = aReagentSeriesPT1;}

	/** 1. Срок годности */
	@Comment("1. Срок годности")
	@Persist @DateString @DoDateString
	public String getReagentExpDatePT1() {return theReagentExpDatePT1;}
	public void setReagentExpDatePT1(String aReagentExpDatePT1) {theReagentExpDatePT1 = aReagentExpDatePT1;}

	/** Результат. Совместима */
	@Comment("Результат. Совместима")
	@Persist
	public Long getResultGoodPT1() {return theResultGoodPT1;}
	public void setResultGoodPT1(Long aResultGoodPT1) {theResultGoodPT1 = aResultGoodPT1;}

	/** Результат. Совместима */
	private Long theResultGoodPT1;
	/** 1. Срок годности */
	private String theReagentExpDatePT1;
	/** 1. Серия реактива по инд. совместимости */
	private String theReagentSeriesPT1;
	/** 1. Реактив по инд. совместимости */
	private String theReagentPT1;
	/** Метод 1 по инд. совместимости */
	private Long theMethodPT1;
	
	/** Метод 2 по инд. совместимости */
	@Comment("Метод 2 по инд. совместимости")
	@Persist
	public Long getMethodPT2() {return theMethodPT2;}
	public void setMethodPT2(Long aMethodPT2) {theMethodPT2 = aMethodPT2;}

	/** 2. Реактив по инд. совместимости */
	@Comment("2. Реактив по инд. совместимости")
	@Persist
	public String getReagentPT2() {return theReagentPT2;}
	public void setReagentPT2(String aReagentPT2) {theReagentPT2 = aReagentPT2;}

	/** 2. Серия реактива по инд. совместимости */
	@Comment("2. Серия реактива по инд. совместимости")
	@Persist
	public String getReagentSeriesPT2() {return theReagentSeriesPT2;}
	public void setReagentSeriesPT2(String aReagentSeriesPT2) {theReagentSeriesPT2 = aReagentSeriesPT2;}

	/** 2. Срок годности */
	@Comment("2. Срок годности")
	@Persist @DateString @DoDateString
	public String getReagentExpDatePT2() {return theReagentExpDatePT2;}
	public void setReagentExpDatePT2(String aReagentExpDatePT2) {theReagentExpDatePT2 = aReagentExpDatePT2;}

	/** Результат. Совместима */
	@Comment("Результат. Совместима")
	@Persist
	public Long getResultGoodPT2() {return theResultGoodPT2;}
	public void setResultGoodPT2(Long aResultGoodPT2) {theResultGoodPT2 = aResultGoodPT2;}

	/** Результат. Совместима */
	private Long theResultGoodPT2;
	/** 1. Срок годности */
	private String theReagentExpDatePT2;
	/** 1. Серия реактива по инд. совместимости */
	private String theReagentSeriesPT2;
	/** 1. Реактив по инд. совместимости */
	private String theReagentPT2;
	/** Метод 1 по инд. совместимости */
	private Long theMethodPT2;
	
	/** Биологическая проба */
	@Comment("Биологическая проба")
	public String getBiologicTest() {return theBiologicTest;}
	public void setBiologicTest(String aBiologicTest) {theBiologicTest = aBiologicTest;}

	/** Биологическая проба */
	private String theBiologicTest;

	/** Справочник процедур в биологической пробе при переливаниях */
	@Comment("Справочник процедур в биологической пробе при переливаниях")
	@Persist
	public Long getBloodBioProbProcedure() {return theBloodBioProbProcedure;}
	public void setBloodBioProbProcedure(Long aBloodBioProbProcedure) {theBloodBioProbProcedure=aBloodBioProbProcedure;}

	/** Кровотечение усилилось или нет без видимой причины*/
	@Comment("Кровотечение усилилось или нет без видимой причины")
	@Persist
	public Boolean getWasBleedingIncreased() {return theWasBleedingIncreased;}
	public void setWasBleedingIncreased(Boolean aWasBleedingIncreased) {theWasBleedingIncreased=aWasBleedingIncreased;}

	/** АД снизилось или нет без видимой причины*/
	@Comment("АД снизилось или нет без видимой причины")
	@Persist
	public Boolean getWasADDecreased() {return theWasADDecreased;}
	public void setWasADDecreased(Boolean aWasADDecreased) {theWasADDecreased=aWasADDecreased;}

	/** Пульс участился или нет без видимой причины*/
	@Comment("Пульс участился или нет без видимой причины")
	@Persist
	public Boolean getWasPulseIncreased() {return theWasPulseIncreased;}
	public void setWasPulseIncreased(Boolean aWasPulseIncreased) {theWasPulseIncreased=aWasPulseIncreased;}

	/** Пульс участился или нет без видимой причины*/
	@Comment("Пульс участился или нет без видимой причины")
	@Persist
	public Boolean getWasUrineColorChanged() {return theWasUrineColorChanged;}
	public void setWasUrineColorChanged(Boolean aWasUrineColorChanged) {theWasUrineColorChanged=aWasUrineColorChanged;}
	/** Справочник процедур в биологической пробе при переливаниях */
	private Long theBloodBioProbProcedure;
	/** Кровотечение усилилось или нет без видимой причины (при переливании под наркозом или в коме)*/
	private Boolean theWasBleedingIncreased;
	/** АД снизилось или нет без видимой причины (при переливании под наркозом или в коме)*/
	private Boolean theWasADDecreased;
	/** Пульс участился или нет без видимой причины (при переливании под наркозом или в коме)*/
	private Boolean theWasPulseIncreased;
	/** Цвет мочи изменился или нет без видимой причины (при переливании под наркозом или в коме)*/
	private Boolean theWasUrineColorChanged;
}
//lastrelease milamesher 30.03.2018 #95
//added fields