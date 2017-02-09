package ru.ecom.mis.ejb.form.worker;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.worker.Attorney;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz= Attorney.class)
@Comment("Доверенность")
@WebTrail(comment = "Доверенность", nameProperties= "id", view="entityView-work_attorney.do")
@EntityFormSecurityPrefix("/Policy/Mis/Worker/Attorney")
public class AttorneyForm extends IdEntityForm{

	/** Лицо, на которое выдана доверенность */
	@Comment("Лицо, на которое выдана доверенность")
	@Persist @Required
	public Long getPerson() {return thePerson;}
	public void setPerson(Long aPerson) {thePerson = aPerson;}
	/** Лицо, на которое выдана доверенность */
	private Long thePerson;
	
	/** ФИО в родительном падеже */
	@Comment("ФИО в родительном падеже")
	@Persist @Required
	public String getAltPersonInfo() {return theAltPersonInfo;}
	public void setAltPersonInfo(String aAltPersonInfo) {theAltPersonInfo = aAltPersonInfo;}
	/** ФИО в родительном падеже */
	private String theAltPersonInfo;
		
	/** Номер доверенности */
	@Comment("Номер доверенности")
	@Persist @Required
	public String getAttorneyNumber() {return theAttorneyNumber;}
	public void setAttorneyNumber(String aAttorneyNumber) {theAttorneyNumber = aAttorneyNumber;}
	/** Номер доверенности */
	private String theAttorneyNumber;
	
	/** Дата выдачи доверенности */
	@Comment("Дата выдачи доверенности")
	@Persist @DateString @DoDateString
	@Required
	public String getAttorneyStartDate() {return theAttorneyStartDate;}
	public void setAttorneyStartDate(String aAttorneyStartDate) {theAttorneyStartDate = aAttorneyStartDate;}
	/** Дата выдачи доверенности */
	private String theAttorneyStartDate;
	
	/** Недействительна */
	@Comment("Недействительна")
	@Persist
	public Boolean getIsArchive() {return theIsArchive;}
	public void setIsArchive(Boolean aIsArchive) {theIsArchive = aIsArchive;}
	/** Недействительна */
	private Boolean theIsArchive;
	
	/** Тип доверенности */
	@Comment("Тип доверенности")
	@Persist @Required
	public Long getType() {return theType;}
	public void setType(Long aType) {theType = aType;}
	/** Тип доверенности */
	private Long theType;
	
	/** Использовать в системе по умолчанию */
	@Comment("Использовать в системе по умолчанию")
	@Persist
	public Boolean getIsDefault() {return theIsDefault;}
	public void setIsDefault(Boolean aIsDefault) {theIsDefault = aIsDefault;}
	/** Использовать в системе по умолчанию */
	private Boolean theIsDefault;
}
