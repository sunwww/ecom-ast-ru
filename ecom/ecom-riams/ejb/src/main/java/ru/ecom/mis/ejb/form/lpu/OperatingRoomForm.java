package ru.ecom.mis.ejb.form.lpu;

import lombok.Setter;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.lpu.OperatingRoom;
import ru.ecom.mis.ejb.form.worker.GroupWorkFunctionForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@Comment("Операционная")
@EntityForm
@EntityFormPersistance(clazz = OperatingRoom.class)
@WebTrail(comment = "Операционная", nameProperties = "groupName", view = "entityParentView-mis_operatingRoom.do")
@Parent(property = "lpu", parentForm = MisLpuForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MisLpu/OperatingRoom")
@Setter
public class OperatingRoomForm  extends GroupWorkFunctionForm {

	/** Лечебное учреждение */
	@Comment("Лечебное учреждение")
	@Persist
	public Long getLpu() {return lpu;}

	/** Лечебное учреждение */
	private Long lpu;
	
	/** Функция */
	@Comment("Функция")
	@Persist 
	public Long getWorkFunction() {return workFunction;}
	/** Функция */
	private Long workFunction;
}
