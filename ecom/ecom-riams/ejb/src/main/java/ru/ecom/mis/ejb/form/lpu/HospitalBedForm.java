package ru.ecom.mis.ejb.form.lpu;

import lombok.Setter;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.lpu.HospitalBed;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@Comment("Больничная койка")
@EntityForm
@EntityFormPersistance(clazz = HospitalBed.class)
@WebTrail(comment = "Больничная койка", nameProperties = "name", view = "entityParentView-mis_hospitalBed.do")
@Parent(property = "parent", parentForm = HospitalRoomForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/WorkPlace/HospitalRoom/HospitalBed")
@Setter
public class HospitalBedForm extends HospitalRoomForm {
	/** Дополнительные */
	@Comment("Дополнительные")
	@Persist
	public Boolean getIsAddition() {return isAddition;}
	/** Дополнительные */
	private Boolean isAddition;

	/**
	 * Лечебное учреждение
	 */
	@Comment("Лечебное учреждение")
	@Persist
	public Long getLpu() {
		return lpu;
	}
	private Long lpu;
}
