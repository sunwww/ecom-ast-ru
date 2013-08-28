package ru.medos.ejb.persdata.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.medos.ejb.persdata.domain.ExternalCarrierDestructionAct;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = ExternalCarrierDestructionAct.class)
@Comment("Акт уничтожения внешних носителей")
@WebTrail(comment = "Акт уничтожения внешних носителей", nameProperties= "id", list="entityParentList-personaldata_externalCarrierDestructionAct.do", view="entityParentView-personaldata_externalCarrierDestructionAct.do")
//@Parent(property="parent", parentForm=PARENT.class)
@EntityFormSecurityPrefix("/Policy/Mis")
public class ExternalCarrierDestructionActForm extends IdEntityForm{
}
