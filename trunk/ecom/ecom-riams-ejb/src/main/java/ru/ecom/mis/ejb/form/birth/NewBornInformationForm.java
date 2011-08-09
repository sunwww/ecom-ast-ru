package ru.ecom.mis.ejb.form.birth;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.birth.NewBornInformation;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

/**
 * Информация о новорожденном
 * @author stkacheva
 *
 */
@EntityForm
@EntityFormPersistance(clazz= NewBornInformation.class)
@Comment("Информация о новорожденном")
@WebTrail(comment = "Информация о новорожденном", nameProperties= "id", view="entityView-preg_newBornInformation.do")
@Parent(property="confinedExchangeCard", parentForm= ConfinedExchangeCardForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Pregnancy/NewBornInformation")
public class NewBornInformationForm extends IdEntityForm{
	/** Обменная карта родильницы */
	@Comment("Обменная карта родильницы")
	@Persist
	public Long getConfinedExchangeCard() {return theConfinedExchangeCard;}
	public void setConfinedExchangeCard(Long aConfinedExchangeCard) {theConfinedExchangeCard = aConfinedExchangeCard;}

	@Comment("Состояние при рождении")
	@Persist
	public String getBirthCondition() {return theBirthCondition;}
	public void setBirthCondition(String aBirthCondition) {theBirthCondition = aBirthCondition;}

	/** Состояние при выписке */
	@Comment("Состояние при выписке")
	@Persist
	public String getDischargeCondition() {return theDischargeCondition;}
	public void setDischargeCondition(String aDischargeCondition) {theDischargeCondition = aDischargeCondition;}

	/** Вес при рождении */
	@Comment("Вес при рождении")
	@Persist
	public String getBirthWeight() {return theBirthWeight;}
	public void setBirthWeight(String aBirthWeight) {theBirthWeight = aBirthWeight;}

	/** Вес при выписке */
	@Comment("Вес при выписке")
	@Persist
	public String getDischargeWeight() {return theDischargeWeight;}
	public void setDischargeWeight(String aDischargeWeight) {theDischargeWeight = aDischargeWeight;}

	/** Рост при рождении */
	@Comment("Рост при рождении")
	@Persist
	public String getBirthHeight() {return theBirthHeight;}
	public void setBirthHeight(String aBIrthHeight) {theBirthHeight = aBIrthHeight;}

	/** Особенности течения родов */
	@Comment("Особенности течения родов")
	@Persist
	public String getBirthFeatures() {return theBirthFeatures;}
	public void setBirthFeatures(String aBirthFeatures) {theBirthFeatures = aBirthFeatures;}

	/** Особенности течения послеродового периода */
	@Comment("Особенности течения послеродового периода")
	@Persist
	public String getPostNatalFeatures() {return thePostNatalFeatures;}
	public void setPostNatalFeatures(String aPostNatalFeatures) {thePostNatalFeatures = aPostNatalFeatures;}

	/** Противотуберкулезная вакцинация */
	@Comment("Противотуберкулезная вакцинация")
	@Persist
	public Boolean getVcgVaccination() {return theVcgVaccination;}
	public void setVcgVaccination(Boolean aVCGVaccination) {theVcgVaccination = aVCGVaccination;}

	/** Причины отказа в противотуберкулезной вакцинации */
	@Comment("Причины отказа в противотуберкулезной вакцинации")
	@Persist
	public String getVcgEstop() {return theVcgEstop;}
	public void setVcgEstop(String aVCGEstop) {theVcgEstop = aVCGEstop;}

	
	/** Другие мероприятия */
	@Comment("Другие мероприятия")
	@Persist
	public String getOtherActions() {return theOtherActions;}
	public void setOtherActions(String aOtherActions) {theOtherActions = aOtherActions;
	}

	/** Особые замечания */
	@Comment("Особые замечания")
	@Persist
	public String getNotes() {return theNotes;}
	public void setNotes(String aNotes) {theNotes = aNotes;}
	
	/** Дата заполнения */
	@Comment("Дата заполнения")
	@Persist @DateString @DoDateString
	public String getFillingDate() {return theFillingDate;}
	public void setFillingDate(String aFillingDate) {theFillingDate = aFillingDate;}

	/** Другие мероприятия */
	private String theOtherActions;
	/** Особые замечания */
	private String theNotes;
	/** Дата заполнения */
	private String theFillingDate;
	/** Обменная карта родильницы */
	private Long theConfinedExchangeCard;
	/** Состояние при рождении */
	private String theBirthCondition;
	/** Состояние при выписке */
	private String theDischargeCondition;
	/** Противотуберкулезная вакцинация */
	private Boolean theVcgVaccination;
	/** Рост при рождении */
	private String theBirthHeight;
	/** Вес при рождении */
	private String theBirthWeight;
	/** Вес при выписке */
	private String theDischargeWeight;
	/** Особенности течения родов */
	private String theBirthFeatures;
	/** Особенности течения послеродового периода */
	private String thePostNatalFeatures;
	/** Причины отказа в противотуберкулезной вакцинации */
	private String theVcgEstop;

}
