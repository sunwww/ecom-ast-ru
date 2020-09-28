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
}
//lastrelease milamesher 30.03.2018 #95
//added fields