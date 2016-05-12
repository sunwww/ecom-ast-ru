package ru.ecom.mis.ejb.form.lpu;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.lpu.BuildingPlace;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@Comment("Здание")
@EntityForm
@EntityFormPersistance(clazz = BuildingPlace.class)
@WebTrail(comment = "Здание", nameProperties = "name", view = "entityView-mis_buildingPlace.do")
@Parent(property = "parent", parentForm = WorkPlaceForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/WorkPlace/BuildingPlace")
public class BuildingPlaceForm extends WorkPlaceForm {

}
