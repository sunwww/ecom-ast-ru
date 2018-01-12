package ru.ecom.mis.ejb.uc.privilege.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.form.patient.PatientForm;
import ru.ecom.mis.ejb.uc.privilege.domain.Privilege;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz= Privilege.class)
@WebTrail(comment = "Льгота", nameProperties= "info", view="entityParentView-mis_privilege.do", shortView="entityShortView-mis_privilege.do")
@Parent(property="person", parentForm=PatientForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Person/Privilege")
public class PrivilegeForm extends IdEntityForm {

	private Long theCategory;
	private Long thePerson;
	private String theEndDate;
	private String theBeginDate;
	private String serialDoc;
	private String numberDoc;
	private Boolean isDelete;
	private String theInfo;
	/*private Long thePrivilegeCode;
	private Long theIdc10;
	private boolean theActive;
	private Boolean theTakeover;
	 ;*/

	@Comment("Серия документа")
	@Persist
	public String getSerialDoc() {
		return serialDoc;
	}
	public void setSerialDoc(String serialDoc) {
		this.serialDoc = serialDoc;
	}

	@Comment("Серия документа")
	@Persist
	public String getNumberDoc() {
		return numberDoc;
	}
	public void setNumberDoc(String numberDoc) {
		this.numberDoc = numberDoc;
	}

	@Comment("Персона")
	@Persist
	public Long getPerson() {
		return thePerson;
	}
	public void setPerson(Long aPerson) {
		thePerson = aPerson;
	}

	@Comment("Категория льготников")
	@Persist @Required
	public Long getCategory() {
		return theCategory;
	}
	public void setCategory(Long aCategory) {
		theCategory = aCategory;
	}

	@Comment("Дата начала")
	@DateString @DoDateString @Required
	@Persist
	public String getBeginDate() {
		return theBeginDate;
	}
	public void setBeginDate(String aBeginDate) {
		theBeginDate = aBeginDate;
	}

	@Comment("Дата исключения")
	@DateString @DoDateString
	@Persist
	public String getEndDate() {
		return theEndDate;
	}
	public void setEndDate(String aEndDate) {
		theEndDate = aEndDate;
	}

	@Comment("Признак удаления")
	@Persist
	public Boolean getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	@Comment("Информация")
	public String getInfo() {return theInfo;}
	public void setInfo(String aInfo) {
		theInfo = aInfo;
	}
	/*
	@Comment("Действительность")
	public boolean getActive() {
		return theActive;
	}
	public void setActive(boolean aActive) {
		theActive = aActive;
	}

	@Comment("МКБ 10")
	public Long getIdc10() {
		return theIdc10;
	}
	public void setIdc10(Long aIdc10) {
		theIdc10 = aIdc10;
	}

	@Comment("Код льготы")
	public Long getPrivilegeCode() {
		return thePrivilegeCode;
	}
	public void setPrivilegeCode(Long aPrivilegeCode) {
		thePrivilegeCode = aPrivilegeCode;
	}

	@Comment("Отказ от льготы")
	public Boolean getTakeover() {return theTakeover;}
	public void setTakeover(Boolean aTakeover) {theTakeover = aTakeover;}*/


}

