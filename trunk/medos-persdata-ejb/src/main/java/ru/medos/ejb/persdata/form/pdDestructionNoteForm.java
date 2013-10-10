package ru.medos.ejb.persdata.form;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.medos.ejb.persdata.domain.pdDestructionNote;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = pdDestructionNote.class)
@Comment("Справка об удалении персональных данных")
@WebTrail(comment = "Справка об удалении персональных данных", nameProperties= "id"
, view="entityParentView-pd_pdDestructionNote.do")
@Parent(property="person", parentForm=PersonForm.class)
@EntityFormSecurityPrefix("/Policy/PersData/OutgoingDocument")
public class pdDestructionNoteForm extends OutgoingDocumentForm {
}
