package ru.ecom.mis.ejb.domain.medcase;

import ru.ecom.mis.ejb.domain.medcase.voc.*;
import ru.ecom.mis.ejb.domain.patient.voc.VocYesNo;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * Переливание крови и кровезаменителей
 * @author azviagin
 *
 */

@Comment("Переливание крови и кровезаменителей")
@Entity
public class BloodTransfusion extends Transfusion {
	
	/** Препарат крови */
	@Comment("Препарат крови")
	@OneToOne
	public VocBloodPreparation getBloodPreparation() {return theBloodPreparation;}
	public void setBloodPreparation(VocBloodPreparation aBloodPreparation) {theBloodPreparation = aBloodPreparation;}

	/** Группа крови пациента */
	@Comment("Группа крови пациента")
	@OneToOne
	public VocBloodGroup getPatientBloodGroup() {return thePatientBloodGroup;}
	public void setPatientBloodGroup(VocBloodGroup aPatientBloodGroup) {thePatientBloodGroup = aPatientBloodGroup;}
	
	/** Резус-фактор пациента */
	@Comment("Резус-фактор пациента")
	@OneToOne
	public VocRhesusFactor getPatientRhesusFactor() {return thePatientRhesusFactor;}
	public void setPatientRhesusFactor(VocRhesusFactor aPatientRhesusFactor) {thePatientRhesusFactor = aPatientRhesusFactor;}
	
	/** Группа крови препарата */
	@Comment("Группа крови препарата")
	@OneToOne
	public VocBloodGroup getPreparationBloodGroup() {return thePreparationBloodGroup;}
	public void setPreparationBloodGroup(VocBloodGroup aPreparationBloodGroup) {thePreparationBloodGroup = aPreparationBloodGroup;}

	/** Резус-фактор препарата */
	@Comment("Резус-фактор препарата")
	@OneToOne
	public VocRhesusFactor getPreparationRhesusFactor() {return thePreparationRhesusFactor;}
	public void setPreparationRhesusFactor(VocRhesusFactor aPreparationRhesusFactor) {thePreparationRhesusFactor = aPreparationRhesusFactor;}

	/** Донор */
	@Comment("Донор")
	public String getDonor() {return theDonor;}
	public void setDonor(String aDonor) {theDonor = aDonor;}
	
	/** Донор */
	private String theDonor;
	/** Препарат крови */
	private VocBloodPreparation theBloodPreparation;
	/** Группа крови пациента */
	private VocBloodGroup thePatientBloodGroup;
	/** Резус-фактор пациента */
	private VocRhesusFactor thePatientRhesusFactor;
	/** Группа крови препарата */
	private VocBloodGroup thePreparationBloodGroup;
	/** Резус-фактор препарата */
	private VocRhesusFactor thePreparationRhesusFactor;
	
	/** Проверка группы крови пациента */
	@Comment("Проверка группы крови пациента")
	@OneToOne
	public VocBloodGroup getPatBloodGroupCheck() {return thePatBloodGroupCheck;}
	public void setPatBloodGroupCheck(VocBloodGroup aPatBloodGroupCheck) {thePatBloodGroupCheck = aPatBloodGroupCheck;}

	/** Проверка группы крови препарата */
	@Comment("Проверка группы крови препарата")
	@OneToOne
	public VocBloodGroup getPrepBloodGroupCheck() {return thePrepBloodGroupCheck;}
	public void setPrepBloodGroupCheck(VocBloodGroup aPrepBloodGroupCheck) {thePrepBloodGroupCheck = aPrepBloodGroupCheck;}

	/** Проверка группы крови препарата */
	private VocBloodGroup thePrepBloodGroupCheck;
	/** Проверка группы крови пациента */
	private VocBloodGroup thePatBloodGroupCheck;
	
	/** Макроскопическая оценка крови */
	@Comment("Макроскопическая оценка крови")
	@OneToOne
	public VocYesNo getMacroBall() {return theMacroBall;}
	public void setMacroBall(VocYesNo aMacroBall) {theMacroBall = aMacroBall;}

	/** Макроскопическая оценка крови */
	private VocYesNo theMacroBall;
	
	
	/** Частота пульса */
	@Comment("Частота пульса")
	public Integer getPulseRateBT() {return thePulseRateBT;}
	public void setPulseRateBT(Integer aPulseRate) {thePulseRateBT = aPulseRate;}

	/** Температура */
	@Comment("Температура")
	public BigDecimal getTemperatureBT() {return theTemperatureBT;}
	public void setTemperatureBT(BigDecimal aTemperature) {theTemperatureBT = aTemperature;}

	/** Артериальное давление (верхнее) */
	@Comment("Артериальное давление (верхнее)")
	public Integer getBloodPressureTopBT() {return theBloodPressureTopBT;}
	public void setBloodPressureTopBT(Integer aBloodPressureTop) {theBloodPressureTopBT = aBloodPressureTop;}

	/** Артериальное давление (нижнее) */
	@Comment("Артериальное давление (нижнее)")
	public Integer getBloodPressureLowerBT() {return theBloodPressureLowerBT;}
	public void setBloodPressureLowerBT(Integer aBloodPressureLower) {theBloodPressureLowerBT = aBloodPressureLower;}
	
	/** Частота дыхательных движений */
	@Comment("Частота дыхательных движений")
	public Integer getRespiratoryRateBT() {return theRespiratoryRateBT;}
	public void setRespiratoryRateBT(Integer aRespiratoryRate) {theRespiratoryRateBT = aRespiratoryRate;}

	/** Цвет сыворотки (биол. проба) */
	@Comment("Цвет сыворотки (биол. проба)")
	@OneToOne
	public VocTransfusionTestSerumColor getSerumColorBT() {return theSerumColorBT;}
	public void setSerumColorBT(VocTransfusionTestSerumColor aSerumColor) {theSerumColorBT = aSerumColor;}

	/** Переливание прекращено */
	@Comment("Переливание прекращено")
	public Boolean getIsBreakBT() {return theIsBreakBT;}
	public void setIsBreakBT(Boolean aIsBreakBT) {theIsBreakBT = aIsBreakBT;}

	/** Состояние удовлетворительное */
	@Comment("Состояние удовлетворительное")
	@OneToOne
	public VocYesNo getStateBT() {return theStateBT;}
	public void setStateBT(VocYesNo aStateBT) {theStateBT = aStateBT;}

	/** Тяжелый боьлной */
	@Comment("Тяжелый боьлной")
	public Boolean getIsIllPatientsBT() {return theIsIllPatientsBT;}
	public void setIsIllPatientsBT(Boolean aIsIllPatients) {theIsIllPatientsBT = aIsIllPatients;}

	/** Жалобы */
	@Comment("Жалобы")
	public String getLamentBT() {return theLamentBT;}
	public void setLamentBT(String aLamentBT) {theLamentBT = aLamentBT;}

	/** Жалобы */
	private String theLamentBT;
	/** Тяжелый боьлной */
	private Boolean theIsIllPatientsBT;
	/** Состояние удовлетворительное */
	private VocYesNo theStateBT;
	/** Переливание прекращено */
	private Boolean theIsBreakBT;
	/** Цвет сыворотки (биол. проба) */
	private VocTransfusionTestSerumColor theSerumColorBT;
	/** Частота дыхательных движений */
	private Integer theRespiratoryRateBT;
	/** Артериальное давление (нижнее) */
	private Integer theBloodPressureLowerBT;
	/** Артериальное давление (верхнее) */
	private Integer theBloodPressureTopBT;
	/** Температура */
	private BigDecimal theTemperatureBT;
	/** Частота пульса */
	private Integer thePulseRateBT;
	
	/** Метод 1 по инд. совместимости */
	@Comment("Метод 1 по инд. совместимости")
	@OneToOne
	public VocTransfusionMethodPT getMethodPT1() {return theMethodPT1;}
	public void setMethodPT1(VocTransfusionMethodPT aMethodPT1) {theMethodPT1 = aMethodPT1;}

	/** 1. Реактив по инд. совместимости */
	@Comment("1. Реактив по инд. совместимости")
	public String getReagentPT1() {return theReagentPT1;}
	public void setReagentPT1(String aReagentPT1) {theReagentPT1 = aReagentPT1;}

	/** 1. Серия реактива по инд. совместимости */
	@Comment("1. Серия реактива по инд. совместимости")
	public String getReagentSeriesPT1() {return theReagentSeriesPT1;}
	public void setReagentSeriesPT1(String aReagentSeriesPT1) {theReagentSeriesPT1 = aReagentSeriesPT1;}

	/** 1. Срок годности */
	@Comment("1. Срок годности")
	public Date getReagentExpDatePT1() {return theReagentExpDatePT1;}
	public void setReagentExpDatePT1(Date aReagentExpDatePT1) {theReagentExpDatePT1 = aReagentExpDatePT1;}

	/** Результат. Совместима */
	@Comment("Результат. Совместима")
	@OneToOne
	public VocYesNo getResultGoodPT1() {return theResultGoodPT1;}
	public void setResultGoodPT1(VocYesNo aResultGoodPT1) {theResultGoodPT1 = aResultGoodPT1;}

	/** Результат. Совместима */
	private VocYesNo theResultGoodPT1;
	/** 1. Срок годности */
	private Date theReagentExpDatePT1;
	/** 1. Серия реактива по инд. совместимости */
	private String theReagentSeriesPT1;
	/** 1. Реактив по инд. совместимости */
	private String theReagentPT1;
	/** Метод 1 по инд. совместимости */
	private VocTransfusionMethodPT theMethodPT1;

	/** 3. Срок годности */
	private Date theReagentExpDatePT3;
	/** 3. Серия реактива по инд. совместимости */
	private String theReagentSeriesPT3;
	/** 3. Реактив по инд. совместимости */
	private String theReagentPT3;

	/** 3. Реактив по инд. совместимости */
	@Comment("3. Реактив по инд. совместимости")
	public String getReagentPT3() {return theReagentPT3;}
	public void setReagentPT3(String aReagentPT3) {theReagentPT3 = aReagentPT3;}

	/** 3. Серия реактива по инд. совместимости */
	@Comment("3. Серия реактива по инд. совместимости")
	public String getReagentSeriesPT3() {return theReagentSeriesPT3;}
	public void setReagentSeriesPT3(String aReagentSeriesPT3) {theReagentSeriesPT3 = aReagentSeriesPT3;}

	/** 3. Срок годности */
	@Comment("3. Срок годности")
	public Date getReagentExpDatePT3() {return theReagentExpDatePT3;}
	public void setReagentExpDatePT3(Date aReagentExpDatePT3) {theReagentExpDatePT3 = aReagentExpDatePT3;}
	
	/** Метод 2 по инд. совместимости */
	@Comment("Метод 2 по инд. совместимости")
	@OneToOne
	public VocTransfusionMethodPT getMethodPT2() {return theMethodPT2;}
	public void setMethodPT2(VocTransfusionMethodPT aMethodPT2) {theMethodPT2 = aMethodPT2;}

	/** 2. Реактив по инд. совместимости */
	@Comment("2. Реактив по инд. совместимости")
	public String getReagentPT2() {return theReagentPT2;}
	public void setReagentPT2(String aReagentPT2) {theReagentPT2 = aReagentPT2;}

	/** 2. Серия реактива по инд. совместимости */
	@Comment("2. Серия реактива по инд. совместимости")
	public String getReagentSeriesPT2() {return theReagentSeriesPT2;}
	public void setReagentSeriesPT2(String aReagentSeriesPT2) {theReagentSeriesPT2 = aReagentSeriesPT2;}

	/** 2. Срок годности */
	@Comment("2. Срок годности")
	public Date getReagentExpDatePT2() {return theReagentExpDatePT2;}
	public void setReagentExpDatePT2(Date aReagentExpDatePT2) {theReagentExpDatePT2 = aReagentExpDatePT2;}

	/** Результат. Совместима */
	@Comment("Результат. Совместима")
	@OneToOne
	public VocYesNo getResultGoodPT2() {return theResultGoodPT2;}
	public void setResultGoodPT2(VocYesNo aResultGoodPT2) {theResultGoodPT2 = aResultGoodPT2;}

	/** Справочник процедур в биологической пробе при переливаниях */
	@Comment("Справочник процедур в биологической пробе при переливаниях")
	@OneToOne
	public VocBloodBioProbProcedure getBloodBioProbProcedure() {return theBloodBioProbProcedure;}
	public void setBloodBioProbProcedure(VocBloodBioProbProcedure aBloodBioProbProcedure) {theBloodBioProbProcedure=aBloodBioProbProcedure;}

	/** Кровотечение усилилось или нет без видимой причины*/
	@Comment("Кровотечение усилилось или нет без видимой причины")
	public Boolean getWasBleedingIncreased() {return theWasBleedingIncreased;}
	public void setWasBleedingIncreased(Boolean aWasBleedingIncreased) {theWasBleedingIncreased=aWasBleedingIncreased;}

	/** АД снизилось или нет без видимой причины*/
	@Comment("АД снизилось или нет без видимой причины")
	public Boolean getWasADDecreased() {return theWasADDecreased;}
	public void setWasADDecreased(Boolean aWasADDecreased) {theWasADDecreased=aWasADDecreased;}

	/** Пульс участился или нет без видимой причины*/
	@Comment("Пульс участился или нет без видимой причины")
	public Boolean getWasPulseIncreased() {return theWasPulseIncreased;}
	public void setWasPulseIncreased(Boolean aWasPulseIncreased) {theWasPulseIncreased=aWasPulseIncreased;}

	/** Пульс участился или нет без видимой причины*/
	@Comment("Пульс участился или нет без видимой причины")
	public Boolean getWasUrineColorChanged() {return theWasUrineColorChanged;}
	public void setWasUrineColorChanged(Boolean aWasUrineColorChanged) {theWasUrineColorChanged=aWasUrineColorChanged;}

	/** Совместимость биопробы */
	@Comment("Совместимость биопробы")
	@OneToOne
	public VocYesNo getBioProbeCompatibility() {return theBioProbeCompatibility;}
	public void setBioProbeCompatibility(VocYesNo aBioProbeCompatibility) {theBioProbeCompatibility = aBioProbeCompatibility;}

	/** Заключение совместимо/нет */
	@Comment("Заключение совместимо/нет")
	@OneToOne
	public VocYesNo getConclusion() {return theConclusion;}
	public void setConclusion(VocYesNo aConclusion) {theConclusion = aConclusion;}

	/** Совместимость на плоскости */
	@Comment("Совместимость на плоскости")
	@OneToOne
	public VocYesNo getPlaneCompatibility() {return thePlaneCompatibility;}
	public void setPlaneCompatibility(VocYesNo aPlaneCompatibility) {thePlaneCompatibility = aPlaneCompatibility;}

	/** Организация, осуществившая инд. подбор */
	@Comment("Организация, осуществившая инд. подбор")
	@OneToOne
	public VocPreparatorBlood getIndOrg() {return theIndOrg;}
	public void setIndOrg(VocPreparatorBlood aIndOrg) {theIndOrg = aIndOrg;}
	/** Организация, осуществившая инд. подбор */
	private VocPreparatorBlood theIndOrg;

	/** Дата исследования */
	private Date theDateResearch;
	@Comment("Дата исследования")
	public Date getDateResearch() {return theDateResearch;}
	public void setDateResearch(Date aDateResearch) {theDateResearch = aDateResearch;}

	/** Результат. Совместима */
	private VocYesNo theResultGoodPT2;
	/** 1. Срок годности */
	private Date theReagentExpDatePT2;
	/** 1. Серия реактива по инд. совместимости */
	private String theReagentSeriesPT2;
	/** 1. Реактив по инд. совместимости */
	private String theReagentPT2;
	/** Метод 1 по инд. совместимости */
	private VocTransfusionMethodPT theMethodPT2;
	/** Справочник процедур в биологической пробе при переливаниях */
	private VocBloodBioProbProcedure theBloodBioProbProcedure;
	/** Кровотечение усилилось или нет без видимой причины (при переливании под наркозом или в коме)*/
	private Boolean theWasBleedingIncreased;
	/** АД снизилось или нет без видимой причины (при переливании под наркозом или в коме)*/
	private Boolean theWasADDecreased;
	/** Пульс участился или нет без видимой причины (при переливании под наркозом или в коме)*/
	private Boolean theWasPulseIncreased;
	/** Цвет мочи изменился или нет без видимой причины (при переливании под наркозом или в коме)*/
	private Boolean theWasUrineColorChanged;
	/** Совместимость биопробы */
	private VocYesNo theBioProbeCompatibility;
	/** Заключение совместимо/нет */
	private VocYesNo theConclusion;
	/** Заключение совместимо/нет */
	private VocYesNo thePlaneCompatibility;

	/** Основные симптомы */
	@Comment("Основные симптомы")
	public String getMainSymptoms() {return theMainSymptoms;}
	public void setMainSymptoms(String aMainSymptoms) {theMainSymptoms = aMainSymptoms;}

	/** Основные симптомы */
	private String theMainSymptoms;

	/** Фенотип донора */
	@Comment("Фенотип донора")
	public String getPhenotypeDon() {return thePhenotypeDon;}
	public void setPhenotypeDon(String aPhenotypeDon) {thePhenotypeDon = aPhenotypeDon;}

	/** Фенотип донора C */
	@Comment("Фенотип донора C")
	public Boolean getPhenotypeDonC() {return thePhenotypeDonC;}
	public void setPhenotypeDonC(Boolean aPhenotypeDonC) {thePhenotypeDonC = aPhenotypeDonC;}

	/** Фенотип донора с */
	@Comment("Фенотип донора с")
	public Boolean getPhenotypeDonc1() {return thePhenotypeDonc1;}
	public void setPhenotypeDonc1(Boolean aPhenotypeDonc1) {thePhenotypeDonc1 = aPhenotypeDonc1;}

	/** Фенотип донора Е */
	@Comment("Фенотип донора Е")
	public Boolean getPhenotypeDonD() {return thePhenotypeDonD;}
	public void setPhenotypeDonD(Boolean aPhenotypeDonD) {thePhenotypeDonD = aPhenotypeDonD;}

	/** Фенотип донора e */
	@Comment("Фенотип донора e")
	public Boolean getPhenotypeDonE() {return thePhenotypeDonE;}
	public void setPhenotypeDonE(Boolean aPhenotypeDonE) {thePhenotypeDonE = aPhenotypeDonE;}

	/** Фенотип донора E */
	@Comment("Фенотип донора E")
	public Boolean getPhenotypeDone1() {return thePhenotypeDone1;}
	public void setPhenotypeDone1(Boolean aPhenotypeDone1) {thePhenotypeDone1 = aPhenotypeDone1;}

	/** Фенотип донора не определялся */
	@Comment("Фенотип донора не определялся")
	public Boolean getPhenotypeDonNone() {return thePhenotypeDonNone;}
	public void setPhenotypeDonNone(Boolean aPhenotypeDonNone) {thePhenotypeDonNone = aPhenotypeDonNone;}

	/** Фенотип донора E */
	private Boolean thePhenotypeDone1;
	/** Фенотип донора e */
	private Boolean thePhenotypeDonE;
	/** Фенотип донора Е */
	private Boolean thePhenotypeDonD;
	/** Фенотип донора с */
	private Boolean thePhenotypeDonc1;
	/** Фенотип донора C */
	private Boolean thePhenotypeDonC;
	/** Фенотип донора не определялся*/
	private Boolean thePhenotypeDonNone;
	/** Фенотип донора */
	private String thePhenotypeDon;
}