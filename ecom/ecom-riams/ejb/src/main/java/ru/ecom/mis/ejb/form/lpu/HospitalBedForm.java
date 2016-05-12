package ru.ecom.mis.ejb.form.lpu;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.lpu.HospitalBed;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@Comment("Больничная койка")
@EntityForm
@EntityFormPersistance(clazz = HospitalBed.class)
@WebTrail(comment = "Больничная койка", nameProperties = "name", view = "entityParentView-mis_hospitalBed.do")
@Parent(property = "parent", parentForm = HospitalRoomForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/WorkPlace/HospitalRoom/HospitalBed")
public class HospitalBedForm extends HospitalRoomForm {
	/** Дополнительные */
	@Comment("Дополнительные")
	@Persist
	public Boolean getIsAddition() {return theIsAddition;}
	public void setIsAddition(Boolean aIsAddition) {theIsAddition = aIsAddition;}

	/** Дополнительные */
	private Boolean theIsAddition;
}
