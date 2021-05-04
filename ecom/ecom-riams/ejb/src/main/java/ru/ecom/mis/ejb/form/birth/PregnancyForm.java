package ru.ecom.mis.ejb.form.birth;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.birth.Pregnancy;
import ru.ecom.mis.ejb.form.patient.PatientForm;
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
 * Беременность
 * @author stkacheva
 */
@EntityForm
@EntityFormPersistance(clazz= Pregnancy.class)
@Comment("Беременность")
@WebTrail(comment = "Беременность", nameProperties= "id", view="entityParentView-preg_pregnancy.do")
@Parent(property="patient", parentForm= PatientForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Pregnancy")
@Setter
public class PregnancyForm extends IdEntityForm {
	/** Пациент */
	@Comment("Пациент")
	@Persist
	public Long getPatient() {return patient;}

	
	/** Обменная карта беременной */
	@Comment("Обменная карта беременной")
	@Persist
	public Long getPregnanExchangeCard() {return pregnanExchangeCard;}

	
	/** Обменная карта родильницы */
	@Comment("Обменная карта родильницы")
	@Persist
	public Long getConfinedExchangeCard() {return confinedExchangeCard;}

	/** Дата создания */
	@Comment("Дата создания")
	@Persist @DateString @DoDateString
	public String getDateCreate() {return dateCreate;}

	/** Заведена пользователем */
	@Comment("Заведена пользователем")
	@Persist
	public String getUsername() {return username;	}

	/** Какая по счету беременность */
	@Comment("Какая по счету беременность")
	@Persist @Required
	public Integer getOrderNumber() {return orderNumber;}

	/** В каком случае медицинского обслуживания заведена */
	@Comment("В каком случае медицинского обслуживания заведена")
	public Long getMedCase() {return medCase;}

	@Comment("Количество родов")
	@Required @Persist
	public Integer getChildbirthAmount() {return childbirthAmount;}

	/** Информация */
	@Comment("Информация") @Persist
	public String getInformation() {return information;}

	/** Информация */
	private String information;
	/** Количество родов */
	private Integer childbirthAmount;
	/** В каком случае медицинского обслуживания заведена */
	private Long medCase;
	/** Какая по счету беременность */
	private Integer orderNumber;
	/** Заведена пользователем */
	private String username;
	/** Дата создания */
	private String dateCreate;	/** Обменная карта родильницы */
	private Long confinedExchangeCard;
	/** Обменная карта беременной */
	private Long pregnanExchangeCard;
	/** Пациент */
	private Long patient;
}
