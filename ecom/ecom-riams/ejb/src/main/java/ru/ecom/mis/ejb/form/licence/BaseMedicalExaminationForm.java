package ru.ecom.mis.ejb.form.licence;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.mis.ejb.domain.licence.BaseMedicalExamination;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = BaseMedicalExamination.class)
@Comment("Паспорт здоровья")
@WebTrail(comment = "Предварительный медосмотр", nameProperties = "id"
, view = "entityParentView-doc_baseMedicalExamination.do"
, shortView="entitySubclassShortView-doc_baseMedicalExamination.do")
@Parent(property = "medCase", parentForm = MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Document/Internal/BaseMedicalExamination")
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(DocumentPrepareCreateInterceptor.class)
)
public class BaseMedicalExaminationForm extends InternalDocumentsForm {
	/** Профессия */
	@Comment("Профессия")
	@Persist @Required
	public Long getProfession() {return theProfession;}
	public void setProfession(Long aProfession) {theProfession = aProfession;}

	/** Отделение */
	@Comment("Отделение")
	@Persist @Required
	public Long getDepartmentWork() {return theDepartmentWork;}
	public void setDepartmentWork(Long aDepartmentWork) {theDepartmentWork = aDepartmentWork;}

	/** Отделение */
	private Long theDepartmentWork;
	/** Профессия */
	private Long theProfession;
}
