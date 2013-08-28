package ru.medos.ejb.persdata.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.medos.ejb.persdata.domain.CardTransferAct;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = CardTransferAct.class)
@Comment("Акт передачи идентификационных карт")
@WebTrail(comment = "Акт передачи идентификационных карт", nameProperties= "id", list="entityParentList-personaldata_cardTransferAct.do", view="entityParentView-personaldata_cardTransferAct.do")
//@Parent(property="parent", parentForm=PARENT.class)
@EntityFormSecurityPrefix("/Policy/Mis")
public class CardTransferActForm extends IdEntityForm{
}
