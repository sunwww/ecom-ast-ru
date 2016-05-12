package ru.ecom.mis.ejb.form.patient;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.patient.IdentityCardPensionSertificate;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

/**
 * Паспорт
 */
@EntityForm
@EntityFormPersistance(clazz = IdentityCardPensionSertificate.class)
@Comment("Пенсионное свидетельство")
@WebTrail(comment = "Пенсионное свидетельство", nameProperties = "text", view = "entityParentView-mis_identityCardPensionCertificate.do")
@Parent(property = "patient", parentForm =PatientForm.class)
public class IdentityCardPensionCertificateForm extends IdentityCardForm {

}
