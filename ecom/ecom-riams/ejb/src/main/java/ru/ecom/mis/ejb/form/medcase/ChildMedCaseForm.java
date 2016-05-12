package ru.ecom.mis.ejb.form.medcase;

import ru.ecom.ejb.services.entityform.Subclasses;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.form.medcase.hospital.DepartmentMedCaseForm;
import ru.ecom.mis.ejb.form.medcase.poly.VisitMedCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

/**
 * Форма дочерних СМО
 * @author STkacheva
 *
 */
@EntityForm
@EntityFormPersistance(clazz = MedCase.class)
@Comment("Случай медицинского обслуживания")
@WebTrail(comment = "Случай медицинского обслуживания", nameProperties= "id", view="entitySubclassView-mis_childMedCase.do")
@Parent(property="parent", parentForm=MedCaseForm.class)
@Subclasses({VisitMedCaseForm.class, DepartmentMedCaseForm.class})
@EntityFormSecurityPrefix("/Policy/Mis/MedCase")
public class ChildMedCaseForm extends MedCaseForm {
}
