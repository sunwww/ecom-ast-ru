package ru.ecom.mis.ejb.domain.medcase;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class BloodTransfusion extends Transfusion {
	
	/** Препарат крови */
	@Comment("Препарат крови")
	@OneToOne
	public VocBloodPreparation getBloodPreparation() {return bloodPreparation;}

	/** Группа крови пациента */
	@Comment("Группа крови пациента")
	@OneToOne
	public VocBloodGroup getPatientBloodGroup() {return patientBloodGroup;}

	/** Резус-фактор пациента */
	@Comment("Резус-фактор пациента")
	@OneToOne
	public VocRhesusFactor getPatientRhesusFactor() {return patientRhesusFactor;}

	/** Группа крови препарата */
	@Comment("Группа крови препарата")
	@OneToOne
	public VocBloodGroup getPreparationBloodGroup() {return preparationBloodGroup;}

	/** Резус-фактор препарата */
	@Comment("Резус-фактор препарата")
	@OneToOne
	public VocRhesusFactor getPreparationRhesusFactor() {return preparationRhesusFactor;}

	/** Донор */
	private String donor;
	/** Препарат крови */
	private VocBloodPreparation bloodPreparation;
	/** Группа крови пациента */
	private VocBloodGroup patientBloodGroup;
	/** Резус-фактор пациента */
	private VocRhesusFactor patientRhesusFactor;
	/** Группа крови препарата */
	private VocBloodGroup preparationBloodGroup;
	/** Резус-фактор препарата */
	private VocRhesusFactor preparationRhesusFactor;
	
	/** Проверка группы крови пациента */
	@Comment("Проверка группы крови пациента")
	@OneToOne
	public VocBloodGroup getPatBloodGroupCheck() {return patBloodGroupCheck;}

	/** Проверка группы крови препарата */
	@Comment("Проверка группы крови препарата")
	@OneToOne
	public VocBloodGroup getPrepBloodGroupCheck() {return prepBloodGroupCheck;}

	/** Проверка группы крови препарата */
	private VocBloodGroup prepBloodGroupCheck;
	/** Проверка группы крови пациента */
	private VocBloodGroup patBloodGroupCheck;
	
	/** Макроскопическая оценка крови */
	@Comment("Макроскопическая оценка крови")
	@OneToOne
	public VocYesNo getMacroBall() {return macroBall;}

	/** Макроскопическая оценка крови */
	private VocYesNo macroBall;

	/** Цвет сыворотки (биол. проба) */
	@Comment("Цвет сыворотки (биол. проба)")
	@OneToOne
	public VocTransfusionTestSerumColor getSerumColorBT() {return serumColorBT;}


	/** Состояние удовлетворительное */
	@Comment("Состояние удовлетворительное")
	@OneToOne
	public VocYesNo getStateBT() {return stateBT;}
	/** Жалобы */
	private String lamentBT;
	/** Тяжелый боьлной */
	private Boolean isIllPatientsBT;
	/** Состояние удовлетворительное */
	private VocYesNo stateBT;
	/** Переливание прекращено */
	private Boolean isBreakBT;
	/** Цвет сыворотки (биол. проба) */
	private VocTransfusionTestSerumColor serumColorBT;
	/** Частота дыхательных движений */
	private Integer respiratoryRateBT;
	/** Артериальное давление (нижнее) */
	private Integer bloodPressureLowerBT;
	/** Артериальное давление (верхнее) */
	private Integer bloodPressureTopBT;
	/** Температура */
	private BigDecimal temperatureBT;
	/** Частота пульса */
	private Integer pulseRateBT;
	
	/** Метод 1 по инд. совместимости */
	@Comment("Метод 1 по инд. совместимости")
	@OneToOne
	public VocTransfusionMethodPT getMethodPT1() {return methodPT1;}

	/** Результат. Совместима */
	@Comment("Результат. Совместима")
	@OneToOne
	public VocYesNo getResultGoodPT1() {return resultGoodPT1;}

	/** Результат. Совместима */
	private VocYesNo resultGoodPT1;
	/** 1. Срок годности */
	private Date reagentExpDatePT1;
	/** 1. Серия реактива по инд. совместимости */
	private String reagentSeriesPT1;
	/** 1. Реактив по инд. совместимости */
	private String reagentPT1;
	/** Метод 1 по инд. совместимости */
	private VocTransfusionMethodPT methodPT1;

	/** 3. Срок годности */
	private Date reagentExpDatePT3;
	/** 3. Серия реактива по инд. совместимости */
	private String reagentSeriesPT3;
	/** 3. Реактив по инд. совместимости */
	private String reagentPT3;


	/** Метод 2 по инд. совместимости */
	@Comment("Метод 2 по инд. совместимости")
	@OneToOne
	public VocTransfusionMethodPT getMethodPT2() {return methodPT2;}

	/** Результат. Совместима */
	@Comment("Результат. Совместима")
	@OneToOne
	public VocYesNo getResultGoodPT2() {return resultGoodPT2;}

	/** Справочник процедур в биологической пробе при переливаниях */
	@Comment("Справочник процедур в биологической пробе при переливаниях")
	@OneToOne
	public VocBloodBioProbProcedure getBloodBioProbProcedure() {return bloodBioProbProcedure;}

	/** Совместимость биопробы */
	@Comment("Совместимость биопробы")
	@OneToOne
	public VocYesNo getBioProbeCompatibility() {return bioProbeCompatibility;}

	/** Заключение совместимо/нет */
	@Comment("Заключение совместимо/нет")
	@OneToOne
	public VocYesNo getConclusion() {return conclusion;}

	/** Совместимость на плоскости */
	@Comment("Совместимость на плоскости")
	@OneToOne
	public VocYesNo getPlaneCompatibility() {return planeCompatibility;}

	/** Организация, осуществившая инд. подбор */
	@Comment("Организация, осуществившая инд. подбор")
	@OneToOne
	public VocPreparatorBlood getIndOrg() {return indOrg;}
	/** Организация, осуществившая инд. подбор */
	private VocPreparatorBlood indOrg;

	/** Дата исследования */
	private Date dateResearch;
	/** Результат. Совместима */
	private VocYesNo resultGoodPT2;
	/** 1. Срок годности */
	private Date reagentExpDatePT2;
	/** 1. Серия реактива по инд. совместимости */
	private String reagentSeriesPT2;
	/** 1. Реактив по инд. совместимости */
	private String reagentPT2;
	/** Метод 1 по инд. совместимости */
	private VocTransfusionMethodPT methodPT2;
	/** Справочник процедур в биологической пробе при переливаниях */
	private VocBloodBioProbProcedure bloodBioProbProcedure;
	/** Кровотечение усилилось или нет без видимой причины (при переливании под наркозом или в коме)*/
	private Boolean wasBleedingIncreased;
	/** АД снизилось или нет без видимой причины (при переливании под наркозом или в коме)*/
	private Boolean wasADDecreased;
	/** Пульс участился или нет без видимой причины (при переливании под наркозом или в коме)*/
	private Boolean wasPulseIncreased;
	/** Цвет мочи изменился или нет без видимой причины (при переливании под наркозом или в коме)*/
	private Boolean wasUrineColorChanged;
	/** Совместимость биопробы */
	private VocYesNo bioProbeCompatibility;
	/** Заключение совместимо/нет */
	private VocYesNo conclusion;
	/** Заключение совместимо/нет */
	private VocYesNo planeCompatibility;
	/** Основные симптомы */
	private String mainSymptoms;
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
}