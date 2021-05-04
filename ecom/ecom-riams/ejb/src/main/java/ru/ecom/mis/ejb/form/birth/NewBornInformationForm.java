package ru.ecom.mis.ejb.form.birth;

import lombok.Setter;
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
@Setter
public class NewBornInformationForm extends IdEntityForm{
	/** Обменная карта родильницы */
	@Comment("Обменная карта родильницы")
	@Persist
	public Long getConfinedExchangeCard() {return confinedExchangeCard;}

	@Comment("Состояние при рождении")
	@Persist
	public String getBirthCondition() {return birthCondition;}

	/** Состояние при выписке */
	@Comment("Состояние при выписке")
	@Persist
	public String getDischargeCondition() {return dischargeCondition;}

	/** Вес при рождении */
	@Comment("Вес при рождении")
	@Persist
	public String getBirthWeight() {return birthWeight;}

	/** Вес при выписке */
	@Comment("Вес при выписке")
	@Persist
	public String getDischargeWeight() {return dischargeWeight;}

	/** Рост при рождении */
	@Comment("Рост при рождении")
	@Persist
	public String getBirthHeight() {return birthHeight;}

	/** Особенности течения родов */
	@Comment("Особенности течения родов")
	@Persist
	public String getBirthFeatures() {return birthFeatures;}

	/** Особенности течения послеродового периода */
	@Comment("Особенности течения послеродового периода")
	@Persist
	public String getPostNatalFeatures() {return postNatalFeatures;}

	/** Противотуберкулезная вакцинация */
	@Comment("Противотуберкулезная вакцинация")
	@Persist
	public Boolean getVcgVaccination() {return vcgVaccination;}

	/** Причины отказа в противотуберкулезной вакцинации */
	@Comment("Причины отказа в противотуберкулезной вакцинации")
	@Persist
	public String getVcgEstop() {return vcgEstop;}

	
	/** Другие мероприятия */
	@Comment("Другие мероприятия")
	@Persist
	public String getOtherActions() {return otherActions;}

	/** Особые замечания */
	@Comment("Особые замечания")
	@Persist
	public String getNotes() {return notes;}

	/** Дата заполнения */
	@Comment("Дата заполнения")
	@Persist @DateString @DoDateString
	public String getFillingDate() {return fillingDate;}

	/** Другие мероприятия */
	private String otherActions;
	/** Особые замечания */
	private String notes;
	/** Дата заполнения */
	private String fillingDate;
	/** Обменная карта родильницы */
	private Long confinedExchangeCard;
	/** Состояние при рождении */
	private String birthCondition;
	/** Состояние при выписке */
	private String dischargeCondition;
	/** Противотуберкулезная вакцинация */
	private Boolean vcgVaccination;
	/** Рост при рождении */
	private String birthHeight;
	/** Вес при рождении */
	private String birthWeight;
	/** Вес при выписке */
	private String dischargeWeight;
	/** Особенности течения родов */
	private String birthFeatures;
	/** Особенности течения послеродового периода */
	private String postNatalFeatures;
	/** Причины отказа в противотуберкулезной вакцинации */
	private String vcgEstop;

}
