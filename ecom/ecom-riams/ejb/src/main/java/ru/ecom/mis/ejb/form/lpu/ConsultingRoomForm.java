package ru.ecom.mis.ejb.form.lpu;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.annotation.PersistManyToManyOneProperty;
import ru.ecom.mis.ejb.domain.lpu.ConsultingRoom;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@Comment("Кабинет врача")
@EntityForm
@EntityFormPersistance(clazz = ConsultingRoom.class)
@WebTrail(comment = "Кабинет врача", nameProperties = "name", view = "entityView-mis_consultingRoom.do")
@Parent(property = "parent", parentForm = FloorBuildingForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/WorkPlace/ConsultingRoom")
public class ConsultingRoomForm extends WorkPlaceForm{
	/** Специалист */
	@Comment("Специалист")
	@Persist @PersistManyToManyOneProperty(collectionGenericType = WorkFunction.class)
	public String getWorkFunctions() {return theWorkFunctions;}
	public void setWorkFunctions(String aWorkFunction) {theWorkFunctions = aWorkFunction;}

	/** Специалист */
	private String theWorkFunctions;
}
