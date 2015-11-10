package ru.ecom.mis.ejb.form.lpu;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.lpu.OperatingRoom;
import ru.ecom.mis.ejb.form.worker.GroupWorkFunctionForm;
import ru.ecom.mis.ejb.form.worker.WorkFunctionForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@Comment("Операционная")
@EntityForm
@EntityFormPersistance(clazz = OperatingRoom.class)
@WebTrail(comment = "Операционная", nameProperties = "name", view = "entityView-mis_operatingRoom.do")
@Parent(property = "lpu", parentForm = FloorBuildingForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MisLpu/OperatingRoom")
public class OperatingRoomForm  extends GroupWorkFunctionForm {
	/** Название */
	@Comment("Название")
	@Persist
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}

	/** Лечебное учреждение */
	@Comment("Лечебное учреждение")
	@Persist
	public Long getLpu() {return theLpu;}
	public void setLpu(Long aLpu) {theLpu = aLpu;}

	/** Лечебное учреждение */
	private Long theLpu;/** Название */
	private String theName;
}
