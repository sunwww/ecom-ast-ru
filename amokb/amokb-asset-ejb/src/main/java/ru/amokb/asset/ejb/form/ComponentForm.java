package ru.amokb.asset.ejb.form;

import ru.amokb.asset.ejb.domain.Component;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.Subclasses;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = Component.class)
@Comment("Комплектующее")
@WebTrail(comment = "Комплектующее", nameProperties= "id", list="entityParentList-asset_component.do", view="entityParentView-asset_component.do")
//@Parent(property="parent", parentForm=PARENT.class)
@EntityFormSecurityPrefix("/Policy/Mis")
@Subclasses(value={CPUForm.class,MBForm.class,RAMForm.class,LaserDriveForm.class,
		NetworkCardForm.class,OtherComponentForm.class,FloppyDriveForm.class
		,VideoCardForm.class,HDDForm.class
		})
public class ComponentForm extends TransitoryAssetForm {
}
