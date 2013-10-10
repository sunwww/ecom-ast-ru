package ru.medos.ejb.persdata.form;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.medos.ejb.persdata.domain.CardTransferAct;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = CardTransferAct.class)
@Comment("Акт передачи идентификационных карт")
@WebTrail(comment = "Акт передачи идентификационных карт", nameProperties= "id"
, view="entityView-pd_cardTransferAct.do")
@EntityFormSecurityPrefix("/Policy/PersData/Act/CardTransfer")
public class CardTransferActForm extends ActForm {
}
