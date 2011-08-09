package ru.amokb.asset.ejb.form;

import ru.amokb.asset.ejb.domain.FloppyDrive;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = FloppyDrive.class)
@Comment("Дисковод гибких дисков")
@WebTrail(comment = "Дисковод гибких дисков", nameProperties= "id", list="entityParentList-asset_floppyDrive.do", view="entityParentView-asset_floppyDrive.do")
//@Parent(property="parent", parentForm=PARENT.class)
@EntityFormSecurityPrefix("/Policy/Mis")
public class FloppyDriveForm extends ComponentForm{
}
