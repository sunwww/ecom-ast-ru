package ru.medos.ejb.persdata.form;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.medos.ejb.persdata.domain.pdProcessingNote;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = pdProcessingNote.class)
@Comment("Справка по обработке персональных данных")
@WebTrail(comment = "Справка по обработке персональных данных", nameProperties= "id", list="entityParentList-pd_pdProcessingNote.do", view="entityParentView-personaldata_pdProcessingNote.do")
@Parent(property="person", parentForm=PersonForm.class)
@EntityFormSecurityPrefix("/Policy/Mis")
public class pdProcessingNoteForm extends OutgoingDocumentForm{
}
