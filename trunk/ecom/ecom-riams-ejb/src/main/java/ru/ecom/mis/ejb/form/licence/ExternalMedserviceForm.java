package ru.ecom.mis.ejb.form.licence;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.licence.ExternalMedservice;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = ExternalMedservice.class)
@Comment("")
@WebTrail(comment = "Внешние исследования", nameProperties= "id", list="entityParentList-doc_externalMedservice.do", view="entityParentView-licence_externalMedservice.do")
//@Parent(property="parent", parentForm=PARENT.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Document/External/Medservice")
public class ExternalMedserviceForm extends ExternalDocumentForm{
}
