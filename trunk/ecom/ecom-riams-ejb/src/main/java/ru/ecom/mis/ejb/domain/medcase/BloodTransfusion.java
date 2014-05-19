package ru.ecom.mis.ejb.domain.medcase;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.mis.ejb.domain.medcase.voc.VocBloodPreparation;
import ru.ecom.mis.ejb.domain.medcase.voc.VocBloodGroup;
import ru.ecom.mis.ejb.domain.medcase.voc.VocRhesusFactor;
import ru.ecom.mis.ejb.domain.medcase.voc.VocTransfusionTestSerumColor;
import ru.ecom.mis.ejb.domain.patient.voc.VocYesNo;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Переливание крови и кровезаменителей
 * @author azviagin
 *
 */

@Comment("Переливание крови и кровезаменителей")
@Entity
@Table(schema="SQLUser")
public class BloodTransfusion extends Transfusion{
	
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
	public Integer getTemperatureBT() {return theTemperatureBT;}
	public void setTemperatureBT(Integer aTemperature) {theTemperatureBT = aTemperature;}

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
	private Integer theTemperatureBT;
	/** Частота пульса */
	private Integer thePulseRateBT;
}
