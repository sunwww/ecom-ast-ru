package ru.ecom.mis.ejb.form.licence;

import lombok.Setter;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.mis.ejb.domain.licence.DischargeDocument;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = DischargeDocument.class)
@Comment("Выписки из карты")
@WebTrail(comment = "Выписки из карты", nameProperties = "id"
, view = "entityParentView-doc_discharge.do"
, shortView="entitySubclassShortView-doc_discharge.do")
@Parent(property = "medCase", parentForm = MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Document/Internal/Discharge")
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(DocumentPrepareCreateInterceptor.class)
)
@Setter
public class DischargeDocumentForm extends InternalDocumentsForm{
	/** Диагноз */
	@Comment("Диагноз")
	@Persist @Required
	public String getDiagnosis() {return diagnosis;}

	/** Рекомендации */
	@Comment("Рекомендации")
	@Persist
	public String getRecommendations() {return recommendations;}

	/** Обоснование */
	@Comment("Обоснование")
	@Persist @Required
	public String getHistory() {return history;}

	/** Обоснование */
	private String history;
	/** Рекомендации */
	private String recommendations;

	/** Диагноз */
	private String diagnosis;

}
