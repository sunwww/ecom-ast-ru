package ru.ecom.mis.ejb.form.birth;

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
public class PregnancyForm extends IdEntityForm {
	/** Пациент */
	@Comment("Пациент")
	@Persist
	public Long getPatient() {return thePatient;}
	public void setPatient(Long aPatient) {thePatient = aPatient;}

	
	/** Обменная карта беременной */
	@Comment("Обменная карта беременной")
	@Persist
	public Long getPregnanExchangeCard() {return thePregnanExchangeCard;}
	public void setPregnanExchangeCard(Long aPregnanExchangeCard) {thePregnanExchangeCard = aPregnanExchangeCard;}

	
	/** Обменная карта родильницы */
	@Comment("Обменная карта родильницы")
	@Persist
	public Long getConfinedExchangeCard() {return theConfinedExchangeCard;}
	public void setConfinedExchangeCard(Long aConfinedExchangeCard) {theConfinedExchangeCard = aConfinedExchangeCard;}

	/** Дата создания */
	@Comment("Дата создания")
	@Persist @DateString @DoDateString
	public String getDateCreate() {return theDateCreate;}
	public void setDateCreate(String aDateCreate) {theDateCreate = aDateCreate;}

	/** Заведена пользователем */
	@Comment("Заведена пользователем")
	@Persist
	public String getUsername() {return theUsername;	}
	public void setUsername(String aUsername) {theUsername = aUsername;}

	/** Какая по счету беременность */
	@Comment("Какая по счету беременность")
	@Persist @Required
	public Integer getOrderNumber() {return theOrderNumber;}
	public void setOrderNumber(Integer aNAME) {theOrderNumber = aNAME;	}

	/** В каком случае медицинского обслуживания заведена */
	@Comment("В каком случае медицинского обслуживания заведена")
	public Long getMedCase() {return theMedCase;}
	public void setMedCase(Long aMedCase) {theMedCase = aMedCase;}

	@Comment("Количество родов")
	@Required @Persist
	public Integer getChildbirthAmount() {return theChildbirthAmount;}
	public void setChildbirthAmount(Integer aChildbirthAmount) {theChildbirthAmount = aChildbirthAmount;}

	/** Информация */
	@Comment("Информация") @Persist
	public String getInformation() {return theInformation;}
	public void setInformation(String aInformation) {theInformation = aInformation;}

	/** Информация */
	private String theInformation;
	/** Количество родов */
	private Integer theChildbirthAmount;
	/** В каком случае медицинского обслуживания заведена */
	private Long theMedCase;
	/** Какая по счету беременность */
	private Integer theOrderNumber;
	/** Заведена пользователем */
	private String theUsername;
	/** Дата создания */
	private String theDateCreate;	/** Обменная карта родильницы */
	private Long theConfinedExchangeCard;
	/** Обменная карта беременной */
	private Long thePregnanExchangeCard;
	/** Пациент */
	private Long thePatient;
}
