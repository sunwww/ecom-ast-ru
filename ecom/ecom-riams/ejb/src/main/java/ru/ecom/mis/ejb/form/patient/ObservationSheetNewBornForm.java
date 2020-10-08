package ru.ecom.mis.ejb.form.patient;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.patient.ObservationSheet;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = ObservationSheet.class)
@Comment("Лист наблюдения новорождённого ЕДКЦ")
@WebTrail(comment = "Лист наблюдения новорождённого ЕДКЦ", nameProperties= "id", list="entityParentList-edkcObsSheet.do", view="entityView-edkcObsSheet.do")
@Parent(property="patient", parentForm=PatientForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Patient/MobileAnestResNeo/ObservationSheet")
public class ObservationSheetNewBornForm extends ObservationSheetForm  {
}
