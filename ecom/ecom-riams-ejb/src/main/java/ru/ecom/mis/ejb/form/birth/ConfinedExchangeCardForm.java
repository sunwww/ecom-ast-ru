package ru.ecom.mis.ejb.form.birth;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.birth.ConfinedExchangeCard;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

/**
 * Обменная карта беременности (сведения о родильнице)
 *  @author stkacheva
 */
@EntityForm
@EntityFormPersistance(clazz= ConfinedExchangeCard.class)
@Comment("Обменная карта беременности (сведения о родильнице)")
@WebTrail(comment = "Обменная карта беременности (сведения о родильнице)", nameProperties= "id", view="entityView-preg_confinedCard.do")
@Parent(property="pregnancy", parentForm= PregnancyForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Pregnancy/ConfinedExchangeCard")
public class ConfinedExchangeCardForm extends IdEntityForm {
	/** Больница */
	@Comment("Больница")
	@Persist
	public Long getHospital() {return theHospital;}
	public void setHospital(Long aHospital) {theHospital = aHospital;}

	
	/** Дата поступления */
	@Comment("Дата поступления")
	@DateString @DoDateString @Persist
	public String getHospitalizationDate() {return theHospitalizationDate;}
	public void setHospitalizationDate(String aHospitalizationDate) {theHospitalizationDate = aHospitalizationDate;}
		
	
	/** Дата родов */
	@Comment("Дата родов")
	@DateString @DoDateString @Persist
	public String getBirthDate() {return theBirthDate;	}
	public void setBirthDate(String aBirthDate) {theBirthDate = aBirthDate;}

	/** Особенности течения родов */
	@Comment("Особенности течения родов")
	@Persist
	public String getBirthFeatures() {return theBirthFeatures;}
	public void setBirthFeatures(String aBirthFeatures) {theBirthFeatures = aBirthFeatures;}
	
	/** Оперативные пособия в родах */
	@Comment("Оперативные пособия в родах")
	@Persist
	public String getBirthOperations() {return theBirthOperations;}
	public void setBirthOperations(String aBirthOperations) {theBirthOperations = aBirthOperations;}

	/** Обезболивание */
	@Comment("Обезболивание")
	@Persist
	public String getAnesthetization() {return theAnesthetization;}
	public void setAnesthetization(String aAnesthetization) {theAnesthetization = aAnesthetization;}
	
	/** Течение послеродового периода */
	@Comment("Течение послеродового периода")
	@Persist
	public String getPostNatalFeatures() {return thePostNatalFeatures;}
	public void setPostNatalFeatures(String aPostNatalFeatures) {thePostNatalFeatures = aPostNatalFeatures;}
	
	/** Количество дней от родов до выписки */
	@Comment("Количество дней от родов до выписки")
	@Persist
	public Integer getBirthDischargeDays() {return theBirthDischargeDays;}
	public void setBirthDischargeDays(Integer aBirthDischargeDays) {	theBirthDischargeDays = aBirthDischargeDays;}

	/** Состояние матери при выписке */
	@Comment("Состояние матери при выписке")
	@Persist
	public String getDischargeMotherCondition() {return theDischargeMotherCondition;}
	public void setDischargeMotherCondition(String aDischargeMotherCondition) {theDischargeMotherCondition = aDischargeMotherCondition;}

	/** Нуждается в патронаже */
	@Comment("Нуждается в патронаже")
	@Persist
	public Boolean getPatronageNeeded() {return thePatronageNeeded;}
	public void setPatronageNeeded(Boolean aPatronageNeeded) {thePatronageNeeded = aPatronageNeeded;}

	/** Показания к патронажу */
	@Comment("Показания к патронажу")
	@Persist
	public String getPatronageStatement() {return thePatronageStatement;}
	public void setPatronageStatement(String aPatronageStatement) {thePatronageStatement = aPatronageStatement;}

	
	/** Особые замечания */
	@Comment("Особые замечания")
	@Persist
	public String getNotes() {return theNotes;	}
	public void setNotes(String aNotes) {theNotes = aNotes;}

	/** Дата заполнения */
	@Comment("Дата заполнения")
	@DateString @DoDateString @Persist
	public String getFillingDate() {return theFillingDate;}
	public void setFillingDate(String aFillingDate) {theFillingDate = aFillingDate;	}
	
	/** Беременность */
	@Comment("Беременность")
	@Persist
	public Long getPregnancy() {return thePregnancy;}
	public void setPregnancy(Long aPregnancy) {thePregnancy = aPregnancy;}

	/** Беременность */
	private Long thePregnancy;
	/** Количество дней от родов до выписки */
	private Integer theBirthDischargeDays;
	/** Состояние матери при выписке */
	private String theDischargeMotherCondition;
	/** Особые замечания */
	private String theNotes;
	/** Дата заполнения */
	private String theFillingDate;
	/** Больница */
	private Long theHospital;
	/** Показания к патронажу */
	private String thePatronageStatement;
	/** Нуждается в патронаже */
	private Boolean thePatronageNeeded;
	/** Обезболивание */
	private String theAnesthetization;
	/** Дата поступления */
	private String theHospitalizationDate;
	/** Дата родов */
	private String theBirthDate;
	/** Особенности течения родов */
	private String theBirthFeatures;
	/** Оперативные пособия в родах */
	private String theBirthOperations;	
	/** Течение послеродового периода */
	private String thePostNatalFeatures;
}
