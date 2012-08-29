package ru.ecom.mis.ejb.form.lpu;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.lpu.FloorBuilding;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@Comment("Этаж здания")
@EntityForm
@EntityFormPersistance(clazz = FloorBuilding.class)
@WebTrail(comment = "Этаж здания", nameProperties = "name", view = "entityView-mis_floorBuilding.do")
@Parent(property = "parent", parentForm = BuildingPlaceForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/WorkPlace/FloorBuilding")
public class FloorBuildingForm extends WorkPlaceForm {

}
