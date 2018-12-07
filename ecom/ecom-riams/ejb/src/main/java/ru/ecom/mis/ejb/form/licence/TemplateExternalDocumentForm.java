package ru.ecom.mis.ejb.form.licence;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.licence.TemplateExternalDocument;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = TemplateExternalDocument.class)
@Comment("Шаблоны выходных форм для протоколов")
@WebTrail(comment = "Шаблоны выходных форм для протоколов", nameProperties = "id",view = "entityView-template_externalDocument.do"
		,shortView="entityShortView-template_externalDocument.do")
@EntityFormSecurityPrefix("/Policy/Diary/Template")
public class TemplateExternalDocumentForm extends ExternalDocumentForm{


}
