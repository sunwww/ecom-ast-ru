package ru.ecom.mis.ejb.form.patient;


import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.patient.ObservationSheetPregnant;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

/**
 * Created by Milamesher on 07.10.2020.
 */
@EntityForm
@EntityFormPersistance(clazz = ObservationSheetPregnant.class)
@Comment("Лист наблюдения беременной ЕДКЦ")
@WebTrail(comment = "Лист наблюдения беременной ЕДКЦ", nameProperties= "id", list="entityParentList-edkcObsSheetPregnant.do", view="entityView-edkcObsSheetPregnant.do")
@Parent(property="patient", parentForm=PatientForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Patient/MobileAnestResNeo/ObservationSheetPregnant")
public class ObservationSheetPregnantForm extends ObservationSheetForm {
}
