package ru.medos.ejb.persdata.form;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.medos.ejb.persdata.domain.CopiesTransferAct;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = CopiesTransferAct.class)
@Comment("Акт передачи копий")
@WebTrail(comment = "Акт передачи копий", nameProperties= "id", list="entityList-pd_copiesTransferAct.do"
, view="entityView-pd_copiesTransferAct.do")
//@Parent(property="parent", parentForm=PARENT.class)
@EntityFormSecurityPrefix("/Policy/PersData/Act/CopiesTransfer")
public class CopiesTransferActForm extends ActForm{

}
