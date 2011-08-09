package ru.ecom.mis.ejb.form.prescription;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.Subclasses;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.prescription.AbstractPrescriptionList;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
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
@EntityFormPersistance(clazz = AbstractPrescriptionList.class)
@Comment("Лист назначений")
@WebTrail(comment = "Лист назначений", nameProperties= "id", view="entitySubclassView-pres_list.do")
@Parent(property = "medCase", parentForm = MedCaseForm.class)
@Subclasses({ru.ecom.mis.ejb.form.prescription.PrescriptListForm.class, ru.ecom.mis.ejb.form.prescription.template.PrescriptListForm.class})
@EntityFormSecurityPrefix("/Policy/Mis/Prescription")
public class AbstractPrescriptionListForm extends IdEntityForm {
	/** Название шаблона */
	@Comment("Название шаблона")
	@Persist 
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}

	/** Комментарии */
	@Comment("Комментарии")
	@Persist
	public String getComments() {return theComments;}
	public void setComments(String aComments) {theComments = aComments;	}

	/** Дата создания */
	@Comment("Дата создания")
	@Persist
	@DateString @DoDateString
	public String getDate() {return theDate;}
	public void setDate(String aDate) {theDate = aDate;}

	/** Пользователь */
	@Comment("Пользователь")
	@Persist 
	public String getUsername() {return theUsername;}
	public void setUsername(String aUsername) {theUsername = aUsername;}

	/** Владелец */
	@Comment("Владелец")
	@Persist 
	public Long getOwner() {return theOwner;}
	public void setOwner(Long aOwner) {theOwner = aOwner;	}

	/** Владелец (инфо) */
	@Comment("Владелец (инфо)")
	@Persist
	public String getOwnerInfo() {return theOwnerInfo;}
	public void setOwnerInfo(String aOwnerInfo) {theOwnerInfo = aOwnerInfo;}

	/** Случай медицинского обслуживания */
	@Comment("Случай медицинского обслуживания")
	@Persist
	public Long getMedCase() {return theMedCase;	}
	public void setMedCase(Long aMedCase) {theMedCase = aMedCase;	}
	
	/** Тип назначения */
	@Comment("Тип назначения")
	@Persist 
	public Long getPrescriptType() {return thePrescriptType;}
	public void setPrescriptType(Long aPrescriptType) {thePrescriptType = aPrescriptType;}

	/** Тип назначений инфо */
	@Comment("Тип назначений инфо")
	@Persist
	public String getPrescriptTypeInfo() {return thePrescriptTypeInfo;}
	public void setPrescriptTypeInfo(String aPrescriptTypeInfo) {thePrescriptTypeInfo = aPrescriptTypeInfo;}

	/** Рабочая функция */
	@Comment("Рабочая функция")
	@Persist @Required
	public Long getWorkFunction() {return theWorkFunction;}
	public void setWorkFunction(Long aWorkFunction) {theWorkFunction = aWorkFunction;}

	/** Рабочая функция инфо */
	@Comment("Рабочая функция инфо")
	@Persist
	public String getWorkFunctionInfo() {return theWorkFunctionInfo;}
	public void setWorkFunctionInfo(String aWorkFunctionInfo) {theWorkFunctionInfo = aWorkFunctionInfo;}

	/** Рабочая функция инфо */
	private String theWorkFunctionInfo;
	/** Рабочая функция */
	private Long theWorkFunction;
	/** Тип назначений инфо */
	private String thePrescriptTypeInfo;
	/** Тип назначения */
	private Long thePrescriptType;
	/** Случай медицинского обслуживания */
	private Long theMedCase;	
	/** Владелец (инфо) */
	private String theOwnerInfo;
	/** Владелец */
	private Long theOwner;
	/** Пользователь */
	private String theUsername;
	/** Дата создания */
	private String theDate;
	/** Комментарии */
	private String theComments;
	/** Название шаблона */
	private String theName;

}
