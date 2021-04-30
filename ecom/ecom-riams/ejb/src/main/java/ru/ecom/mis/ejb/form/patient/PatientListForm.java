package ru.ecom.mis.ejb.form.patient;

import lombok.Setter;
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
@Setter
public class PatientListForm extends IdEntityForm {

	/** Тип списка */
	@Comment("Тип списка")
	@Persist @Required
	public Long getType() {return type;}
	/** Тип списка */
	private Long type;
	
	/** Название списка */
	@Comment("Название списка")
	@Persist @Required
	public String getName() {return name;}
	/** Название списка */
	private String name;
	
	/** Цвет сообщения */
	@Comment("Цвет сообщения")
	@Persist @Required
	public String getColorName() {return colorName;}
	/** Цвет сообщения */
	private String colorName;
	
	/** Сообщение */
	@Comment("Сообщение")
	@Persist
	public String getMessage() {return message;}
	/** Сообщение */
	private String message;
	
	/** Цвет текста */
	@Comment("Цвет текста")
	@Persist
	public String getColorText() {return colorText;}

	/** Отображать цвет при поиске */
	@Comment("Отображать цвет при поиске")
	@Persist
	public Boolean getIsViewWhenSeaching() {return isViewWhenSeaching;}

	/** Отображать цвет в WebTrail */
	@Comment("Отображать цвет в WebTrail")
	@Persist
	public Boolean getIsViewInWebTrail() {return isViewInWebTrail;}

	/** Отображать цвет в WebTrail */
	private Boolean isViewInWebTrail;
	/** Отображать цвет при поиске */
	private Boolean isViewWhenSeaching;
	/** Цвет текста */
	private String colorText;
	
}
