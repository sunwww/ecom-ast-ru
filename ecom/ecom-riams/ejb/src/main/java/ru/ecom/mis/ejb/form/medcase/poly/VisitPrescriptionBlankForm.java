package ru.ecom.mis.ejb.form.medcase.poly;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.ecom.poly.ejb.domain.PrescriptionBlank;
import ru.ecom.poly.ejb.form.PrescriptionBlankForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = PrescriptionBlank.class)
@Comment("Рецептурный бланк")
@WebTrail(comment = "Рецептурный бланк", nameProperties = "id", view = "entityView-smo_visitPrescriptionBlank.do")
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/PrescriptionBlank")
@Parent(property = "medCase", parentForm = VisitMedCaseForm.class)
public class VisitPrescriptionBlankForm extends PrescriptionBlankForm {

}
