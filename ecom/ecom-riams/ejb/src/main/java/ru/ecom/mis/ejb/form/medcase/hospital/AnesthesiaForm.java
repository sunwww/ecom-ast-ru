package ru.ecom.mis.ejb.form.medcase.hospital;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.medcase.Anesthesia;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.IntegerString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

@Comment("Анестезия")
@EntityForm
@EntityFormPersistance(clazz= Anesthesia.class)
@WebTrail(comment = "Анестезия", nameProperties= "id", view="entityParentView-stac_anesthesia.do")
@Parent(property="surgicalOperation", parentForm= SurgicalOperationForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Stac/Ssl/SurOper/Anesthesia")
public class AnesthesiaForm extends IdEntityForm {

	/** Метод */
	@Comment("Метод")
	@Persist @Required
	public Long getMethod() {
		return theMethod;
	}

	public void setMethod(Long aMethod) {
		theMethod = aMethod;
	}
	
	/** Вид анестезии */
	@Comment("Вид анестезии")
	@Persist
	public Long getType() {return theType;}
	public void setType(Long aType) {theType = aType;}

	/** Вид анестезии */
	private Long theType;
	/** Метод */
	private Long theMethod;
	
	/** Длительность в минутах */
	@Comment("Длительность в минутах")
	@Persist @Required @IntegerString
	public Integer getDuration() {
		return theDuration;
	}

	public void setDuration(Integer aDuration) {
		theDuration = aDuration;
	}

	/** Длительность в минутах */
	private Integer theDuration;
	
	/** Описание */
	@Comment("Описание")
	@Persist 
	public String getDescription() {
		return theDescription;
	}

	public void setDescription(String aDescription) {
		theDescription = aDescription;
	}

	/** Описание */
	private String theDescription;
	
	/** Хирургическая операция */
	@Comment("Хирургическая операция")
	@Persist @Required
	public Long getSurgicalOperation() {
		return theSurgicalOperation;
	}

	public void setSurgicalOperation(Long aSurgicalOperation) {
		theSurgicalOperation = aSurgicalOperation;
	}

	/** Хирургическая операция */
	private Long theSurgicalOperation;
	
	/** Анестезиолог */
	@Comment("Анестезист")
	@Persist @Required
	public Long getAnesthesist() {
		return theAnesthesist;
	}

	public void setAnesthesist(Long aAnesthesist) {
		theAnesthesist = aAnesthesist;
	}

	/** Анестезиолог */
	private Long theAnesthesist;
	
	/** Анестезист инфо */
	@Comment("Анестезист инфо")
	@Persist
	public String getAnesthesistInfo() {
		return theAnesthesistInfo;
	}

	public void setAnesthesistInfo(String aAnesthesistInfo) {
		theAnesthesistInfo = aAnesthesistInfo;
	}

	/** Анестезист инфо */
	private String theAnesthesistInfo;
	
	/** Метод инфо */
	@Comment("Метод инфо")
	@Persist
	public String getMethodInfo() {
		return theMethodInfo;
	}

	public void setMethodInfo(String aMethodInfo) {
		theMethodInfo = aMethodInfo;
	}

	/** Метод инфо */
	private String theMethodInfo;
	
	/** Дата создания */
	@Comment("Дата создания")
	@Persist
	public String getCreateDate() {
		return theCreateDate;
	}

	public void setCreateDate(String aCreateDate) {
		theCreateDate = aCreateDate;
	}

	/** Пользователь создавший запись */
	@Comment("Пользователь создавший запись")
	@Persist
	public String getCreateUsername() {
		return theCreateUsername;
	}

	public void setCreateUsername(String aCreateUsername) {
		theCreateUsername = aCreateUsername;
	}

	/** Пользователь создавший запись */
	private String theCreateUsername;
	/** Дата создания */
	private String theCreateDate;
	
	/** Мед.услуга */
	@Comment("Мед.услуга")
	@Persist
	public Long getMedService() {
		return theMedService;
	}

	public void setMedService(Long aMedService) {
		theMedService = aMedService;
	}

	/** Мед.услуга */
	private Long theMedService;
	
	/** Время создания */
	@Comment("Время создания")
	@Persist @DoTimeString @TimeString
	public String getCreateTime() {return theCreateTime;}
	public void setCreateTime(String aCreateTime) {theCreateTime = aCreateTime;}

	/** Время создания */
	private String theCreateTime;

}
