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
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

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

	/** Дата создания */
	@Comment("Дата создания")
	@DateString @DoDateString @Persist
	public String getCreateDate() {return theCreateDate;}
	public void setCreateDate(String aCreateDate) {theCreateDate = aCreateDate;}
	
	/** Дата редактирования */
	@Comment("Дата редактирования")
	@DateString @DoDateString @Persist
	public String getEditDate() {return theEditDate;}
	public void setEditDate(String aEditDate) {theEditDate = aEditDate;}
	
	/** Время создания */
	@Comment("Время создания")
	@TimeString @DoTimeString @Persist
	public String getCreateTime() {return theCreateTime;}
	public void setCreateTime(String aCreateTime) {theCreateTime = aCreateTime;}
	/** Время редактрования */
	@Comment("Время редактрования")
	@TimeString @DoTimeString @Persist
	public String getEditTime() {return theEditTime;}
	public void setEditTime(String aEditTime) {theEditTime = aEditTime;}
	/** Пользователь, который создал запись */
	@Comment("Пользователь, который создал запись")
	@Persist
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
	/** Пользователь, который последний редактировал запись */
	@Comment("Пользователь, который последний редактировал запись")
	@Persist
	public String getEditUsername() {return theEditUsername;}
	public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}

	/** Пользователь, который последний редактировал запись */
	private String theEditUsername;
	/** Пользователь, который создал запись */
	private String theCreateUsername;
	/** Время редактрования */
	private String theEditTime;
	/** Время создания */
	private String theCreateTime;
	/** Дата редактирования */
	private String theEditDate;
	/** Дата создания */
	private String theCreateDate;
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
	/** Комментарии */
	private String theComments;
	/** Название шаблона */
	private String theName;

}
