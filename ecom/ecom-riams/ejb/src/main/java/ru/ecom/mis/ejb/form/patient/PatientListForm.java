package ru.ecom.mis.ejb.form.patient;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.patient.PatientList;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz= PatientList.class)
@Comment("Список пациентов")
@WebTrail(comment = "Список пациентов", nameProperties= "name", view="entityView-mis_patientList.do")
@EntityFormSecurityPrefix("/Policy/Mis/Patient/PatientList")

public class PatientListForm extends IdEntityForm {

	/** Тип списка */
	@Comment("Тип списка")
	@Persist @Required
	public Long getType() {return theType;}
	public void setType(Long aType) {theType = aType;}
	/** Тип списка */
	private Long theType;
	
	/** Название списка */
	@Comment("Название списка")
	@Persist @Required
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}
	/** Название списка */
	private String theName;
	
	/** Цвет сообщения */
	@Comment("Цвет сообщения")
	@Persist @Required
	public String getColorName() {return theColorName;}
	public void setColorName(String aColorName) {theColorName = aColorName;}
	/** Цвет сообщения */
	private String theColorName;
	
	/** Сообщение */
	@Comment("Сообщение")
	@Persist
	public String getMessage() {return theMessage;}
	public void setMessage(String aMessage) {theMessage = aMessage;}
	/** Сообщение */
	private String theMessage;
	
	/** Цвет текста */
	@Comment("Цвет текста")
	@Persist
	public String getColorText() {return theColorText;}
	public void setColorText(String aColorText) {theColorText = aColorText;}

	
	/** Отображать цвет при поиске */
	@Comment("Отображать цвет при поиске")
	@Persist
	public Boolean getIsViewWhenSeaching() {return theIsViewWhenSeaching;}
	public void setIsViewWhenSeaching(Boolean aIsViewWhenSeaching) {theIsViewWhenSeaching = aIsViewWhenSeaching;}

	/** Отображать цвет в WebTrail */
	@Comment("Отображать цвет в WebTrail")
	@Persist
	public Boolean getIsViewInWebTrail() {return theIsViewInWebTrail;}
	public void setIsViewInWebTrail(Boolean aIsViewInWebTrail) {theIsViewInWebTrail = aIsViewInWebTrail;}

	/** Отображать цвет в WebTrail */
	private Boolean theIsViewInWebTrail;
	/** Отображать цвет при поиске */
	private Boolean theIsViewWhenSeaching;
	/** Цвет текста */
	private String theColorText;
	
}
