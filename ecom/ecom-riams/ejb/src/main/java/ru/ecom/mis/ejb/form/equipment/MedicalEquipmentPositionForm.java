package ru.ecom.mis.ejb.form.equipment;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.equipment.MedicalEquipmentPosition;
import ru.ecom.mis.ejb.form.medstandard.MedicalStandardForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz=MedicalEquipmentPosition.class)
@Comment("Позиция мед. стандарта")
@WebTrail(comment = "Позиция мед. стандарта", nameProperties= "equipmentType", view="entityView-mis_medicalEquipmentPosition.do")
@Parent(property="standard", parentForm= MedicalStandardForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Equipment/Equipment")

public class MedicalEquipmentPositionForm extends IdEntityForm {
	
	/** Медицинский стандарт */
	@Comment("Медицинский стандарт")
	@Persist
	public Long getStandard() {return theStandard;}
	public void setStandard(Long aStandard) {theStandard = aStandard;}
	/** Медицинский стандарт */
	private Long theStandard;
	
	/** Тип оборудования */
	@Comment("Тип оборудования")
	@Persist @Required
	public Long getEquipmentType() {return theEquipmentType;}
	public void setEquipmentType(Long aEquipmentType) {theEquipmentType = aEquipmentType;}
	/** Оборудование */
	private Long theEquipmentType;
	
	/** Необходимое количество */
	@Comment("Необходимое количество")
	@Persist @Required
	public Long getAmount() {return theAmount;}
	public void setAmount(Long aAmount) {theAmount = aAmount;}
	/** Необходимое количество */
	private Long theAmount;
	
	/** Примечание */
	@Comment("Примечание")
	@Persist
	public String getComment() {return theComment;}
	public void setComment(String aComment) {theComment = aComment;}
	/** Примечание */
	private String theComment;
}
