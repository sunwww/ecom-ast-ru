package ru.ecom.mis.ejb.form.equipment;

import lombok.Setter;
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
@Setter

public class MedicalEquipmentPositionForm extends IdEntityForm {
	
	/** Медицинский стандарт */
	@Comment("Медицинский стандарт")
	@Persist
	public Long getStandard() {return standard;}
	/** Медицинский стандарт */
	private Long standard;
	
	/** Тип оборудования */
	@Comment("Тип оборудования")
	@Persist @Required
	public Long getEquipmentType() {return equipmentType;}
	/** Оборудование */
	private Long equipmentType;
	
	/** Необходимое количество */
	@Comment("Необходимое количество")
	@Persist @Required
	public Long getAmount() {return amount;}
	/** Необходимое количество */
	private Long amount;
	
	/** Примечание */
	@Comment("Примечание")
	@Persist
	public String getComment() {return comment;}
	/** Примечание */
	private String comment;
}
