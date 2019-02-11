package ru.ecom.mis.ejb.form.licence;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.licence.ExternalDocument;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = ExternalDocument.class)
@Comment("Внешние документы")
@WebTrail(comment = "Внешние документы", nameProperties = "id",view = "entityParentView-doc_externalDocument.do"
		,shortView="entityShortView-doc_externalDocument.do")
@Parent(property = "medCase", parentForm = MedCase.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Document/External")
public class ExternalDocumentByMedCaseForm extends ExternalDocumentForm {
	
}

