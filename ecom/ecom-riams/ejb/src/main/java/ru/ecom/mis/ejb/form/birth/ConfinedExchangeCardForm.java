package ru.ecom.mis.ejb.form.birth;

import lombok.Setter;
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
@Setter
public class ConfinedExchangeCardForm extends IdEntityForm {
	/** Больница */
	@Comment("Больница")
	@Persist
	public Long getHospital() {return hospital;}

	
	/** Дата поступления */
	@Comment("Дата поступления")
	@DateString @DoDateString @Persist
	public String getHospitalizationDate() {return hospitalizationDate;}

	
	/** Дата родов */
	@Comment("Дата родов")
	@DateString @DoDateString @Persist
	public String getBirthDate() {return birthDate;	}

	/** Особенности течения родов */
	@Comment("Особенности течения родов")
	@Persist
	public String getBirthFeatures() {return birthFeatures;}

	/** Оперативные пособия в родах */
	@Comment("Оперативные пособия в родах")
	@Persist
	public String getBirthOperations() {return birthOperations;}

	/** Обезболивание */
	@Comment("Обезболивание")
	@Persist
	public String getAnesthetization() {return anesthetization;}

	/** Течение послеродового периода */
	@Comment("Течение послеродового периода")
	@Persist
	public String getPostNatalFeatures() {return postNatalFeatures;}

	/** Количество дней от родов до выписки */
	@Comment("Количество дней от родов до выписки")
	@Persist
	public Integer getBirthDischargeDays() {return birthDischargeDays;}

	/** Состояние матери при выписке */
	@Comment("Состояние матери при выписке")
	@Persist
	public String getDischargeMotherCondition() {return dischargeMotherCondition;}

	/** Нуждается в патронаже */
	@Comment("Нуждается в патронаже")
	@Persist
	public Boolean getPatronageNeeded() {return patronageNeeded;}

	/** Показания к патронажу */
	@Comment("Показания к патронажу")
	@Persist
	public String getPatronageStatement() {return patronageStatement;}

	
	/** Особые замечания */
	@Comment("Особые замечания")
	@Persist
	public String getNotes() {return notes;	}

	/** Дата заполнения */
	@Comment("Дата заполнения")
	@DateString @DoDateString @Persist
	public String getFillingDate() {return fillingDate;}

	/** Беременность */
	@Comment("Беременность")
	@Persist
	public Long getPregnancy() {return pregnancy;}

	/** Беременность */
	private Long pregnancy;
	/** Количество дней от родов до выписки */
	private Integer birthDischargeDays;
	/** Состояние матери при выписке */
	private String dischargeMotherCondition;
	/** Особые замечания */
	private String notes;
	/** Дата заполнения */
	private String fillingDate;
	/** Больница */
	private Long hospital;
	/** Показания к патронажу */
	private String patronageStatement;
	/** Нуждается в патронаже */
	private Boolean patronageNeeded;
	/** Обезболивание */
	private String anesthetization;
	/** Дата поступления */
	private String hospitalizationDate;
	/** Дата родов */
	private String birthDate;
	/** Особенности течения родов */
	private String birthFeatures;
	/** Оперативные пособия в родах */
	private String birthOperations;	
	/** Течение послеродового периода */
	private String postNatalFeatures;
}
