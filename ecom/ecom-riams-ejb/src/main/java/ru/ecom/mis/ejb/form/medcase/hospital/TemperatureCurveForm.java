package ru.ecom.mis.ejb.form.medcase.hospital;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.medcase.hospital.TemperatureCurve;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Температурная кривая
 * @author oegorova
 *
 */
@EntityForm
@EntityFormPersistance(clazz = TemperatureCurve.class)
@Comment("Температурная кривая")
@WebTrail(comment = "Температурная кривая", nameProperties= "id", view="entityParentView-stac_temperatureCurve.do")
@Parent(property="medCase", parentForm=MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Stac/Ssl/TemperatureCurve")
public class TemperatureCurveForm extends IdEntityForm {

	/** Время суток */
	@Comment("Время суток")
	@Persist  @Required
	public Long getDayTime() {
		return theDayTime;
	}

	public void setDayTime(Long aDayTime) {
		theDayTime = aDayTime;
	}

	/** Время суток */
	private Long theDayTime;
	
	/** Температурный градус */
	@Comment("Температурный градус")
	@Persist @Required
	public String getDegree() {
		return theDegree;
	}

	public void setDegree(String aDegree) {
		theDegree = aDegree;
	}

	/** Температурный градус */
	private String theDegree;
	
	/** Пульс */
	@Comment("Пульс")
	@Persist
	public String getPulse() {
		return thePulse;
	}

	public void setPulse(String aPulse) {
		thePulse = aPulse;
	}

	/** Пульс */
	private String thePulse;
	
	/** Частота дыхания */
	@Comment("Частота дыхания")
	@Persist
	public String getRespirationRate() {
		return theRespirationRate;
	}

	public void setRespirationRate(String aRespirationRate) {
		theRespirationRate = aRespirationRate;
	}

	/** Частота дыхания */
	private String theRespirationRate;
	
	/** Артериальное давление - верхнее */
	@Comment("Артериальное давление - верхнее")
	@Persist
	public String getBloodPressureUp() {
		return theBloodPressureUp;
	}

	public void setBloodPressureUp(String aBloodPressureUp) {
		theBloodPressureUp = aBloodPressureUp;
	}

	/** Артериальное давление - верхнее */
	private String theBloodPressureUp;
	
	/** Артериальное давление - нижнее */
	@Comment("Артериальное давление - нижнее")
	@Persist
	public String getBloodPressureDown() {
		return theBloodPressureDown;
	}

	public void setBloodPressureDown(String aBloodPressureDown) {
		theBloodPressureDown = aBloodPressureDown;
	}

	/** Артериальное давление - нижнее */
	private String theBloodPressureDown;
	
	/** Стул */
	@Comment("Стул")
	@Persist
	public Long getStool() {
		return theStool;
	}

	public void setStool(Long aStool) {
		theStool = aStool;
	}

	/** Стул */
	private Long theStool;
	
	/** Вес */
	@Comment("Вес")
	@Persist
	public String getWeight() {
		return theWeight;
	}

	public void setWeight(String aWeight) {
		theWeight = aWeight;
	}

	/** Вес */
	private String theWeight;
	
	
	/** Дата измерения температуры */
	@Comment("Дата измерения температуры")
	@Persist @Required
	@DateString @DoDateString
	public String getTakingDate() {
		return theTakingDate;
	}

	public void setTakingDate(String aTakingDate) {
		theTakingDate = aTakingDate;
	}

	/** Дата измерения температуры */
	private String theTakingDate;
	
	/** Случай медицинского обслуживания */
	@Comment("Случай медицинского обслуживания")
	@Persist
	public Long getMedCase() {
		return theMedCase;
	}

	public void setMedCase(Long aMedCase) {
		theMedCase = aMedCase;
	}

	/** Случай медицинского обслуживания */
	private Long theMedCase;

	/** День пребывания в стационаре */
	@Comment("День пребывания в стационаре")
	@Persist
	public String getHospDayNumber() {
		return theHospDayNumber;
	}

	public void setHospDayNumber(String aHospDayNumber) {
		theHospDayNumber = aHospDayNumber;
	}

	/** День пребывания в стационаре */
	private String theHospDayNumber;
		
	/** День болезни */
	@Comment("День болезни")
	@Persist
	public String getIllnessDayNumber() {
		return theIllnessDayNumber;
	}

	public void setIllnessDayNumber(String aIllnessDayNumber) {
		theIllnessDayNumber = aIllnessDayNumber;
	}

	/** День болезни */
	private String theIllnessDayNumber;
	
	
	/** Время суток (текст) */
	@Comment("Время суток (текст)")
	@Persist
	public String getDayTimeText() {
		return theDayTimeText;
	}

	public void setDayTimeText(String aDayTimeText) {
		theDayTimeText = aDayTimeText;
	}

	/** Время суток (текст) */
	private String theDayTimeText;
	
	/** АД */
	@Comment("АД")
	@Persist
	public String getBloodPressureInfo() {
		return theBloodPressureInfo;
	}

	public void setBloodPressureInfo(String aBloodPressureInfo) {
		theBloodPressureInfo = aBloodPressureInfo;
	}

	/** АД */
	private String theBloodPressureInfo;
	
}
