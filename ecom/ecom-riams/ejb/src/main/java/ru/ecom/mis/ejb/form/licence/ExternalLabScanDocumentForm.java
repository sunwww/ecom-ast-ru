package ru.ecom.mis.ejb.form.licence;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.licence.ExternalDocument;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = ExternalDocument.class)
@Comment("Внешние документы лаборатории")
@WebTrail(comment = "Внешние документы лаборатории", nameProperties = "id",view = "entityParentView-doc_externalDocument_lab.do"
		,shortView="entityView-doc_externalDocument_lab.do?short=Short")
//@Parent(property = "patient", parentForm = PatientForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Document/ExternalLabScan")
public class ExternalLabScanDocumentForm extends ExternalDocumentForm {

}
