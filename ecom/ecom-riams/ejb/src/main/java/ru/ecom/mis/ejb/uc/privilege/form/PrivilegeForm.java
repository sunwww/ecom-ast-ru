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

	/** Информация */
	@Comment("Информация")
	@Persist
	public String getInfo() {return theInfo;}

	public void setInfo(String aInfo) {
		theInfo = aInfo;
	}

	private String theInfo ;
	/** Категория льготников */
	@Comment("Категория льготников")
	@Persist
	@Required
	public Long getCategory() {
		return theCategory;
	}

	public void setCategory(Long aCategory) {
		theCategory = aCategory;
	}

	/** Категория льготников */
	private Long theCategory;
	/** Дата начала */
	@Comment("Дата начала")
	@DateString @DoDateString @Required
	@Persist
	public String getBeginDate() {
		return theBeginDate;
	}

	public void setBeginDate(String aBeginDate) {
		theBeginDate = aBeginDate;
	}

	/** Дата исключения */
	@Comment("Дата исключения")
	@DateString @DoDateString
	@Persist
	public String getEndDate() {
		return theEndDate;
	}

	
	public void setEndDate(String aEndDate) {
		theEndDate = aEndDate;
	}

	/** Действительность */
	@Comment("Действительность")
	@Persist
	public boolean getActive() {
		return theActive;
	}

	public void setActive(boolean aActive) {
		theActive = aActive;
	}

	/** МКБ 10 */
	@Comment("МКБ 10")
	@Persist
	public Long getIdc10() {
		return theIdc10;
	}

	/** Персона */
	@Comment("Персона")
	@Persist
	public Long getPerson() {
		return thePerson;
	}

	public void setPerson(Long aPerson) {
		thePerson = aPerson;
	}

	
	/** Персона */
	private Long thePerson;
	public void setIdc10(Long aIdc10) {
		theIdc10 = aIdc10;
	}

	/** Код льготы */
	@Comment("Код льготы")
	@Persist
	@Required
	public Long getPrivilegeCode() {
		return thePrivilegeCode;
	}

	public void setPrivilegeCode(Long aPrivilegeCode) {
		thePrivilegeCode = aPrivilegeCode;
	}

	/** Номер документа */
	@Comment("Номер документа")
	//@Persist
	public String getDocumentNumber() {
		return theDocumentNumber;
	}

	public void setDocumentNumber(String aDocumentNumber) {
		theDocumentNumber = aDocumentNumber;
	}

	/** Дата выдачи документа */
	@Comment("Дата выдачи документа")
	@DateString
	@DoDateString
	//@Persist
	public String getDocumentIssue() {
		return theDocumentIssue;
	}

	public void setDocumentIssue(String aDocumentIssue) {
		theDocumentIssue = aDocumentIssue;
	}

	/** Дата выдачи документа */
	private String theDocumentIssue;
	/** Номер документа */
	private String theDocumentNumber;
	/** Код льготы */
	private Long thePrivilegeCode;
	/** МКБ 10 */
	private Long theIdc10;
	/** Действительность */
	private boolean theActive;
	/** Дата исключения */
	private String theEndDate;
	/** Дата начала */
	private String theBeginDate;
	/** Отказ от льготы */
	@Comment("Отказ от льготы")
	@Persist
	public Boolean getTakeover() {return theTakeover;}
	public void setTakeover(Boolean aTakeover) {theTakeover = aTakeover;}

	/** Отказ от льготы */
	private Boolean theTakeover;	
}

