package ru.ecom.mis.ejb.form.licence;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.mis.ejb.domain.licence.DischargeDiagnostDocument;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = DischargeDiagnostDocument.class)
@Comment("Выписка из карты (диагн. услуги)")
@WebTrail(comment = "Выписка из карты (диагн. услуги)", nameProperties = "id"
, view = "entityParentView-doc_discharge.do"
, shortView="entitySubclassShortView-doc_discharge.do")
@Parent(property = "medCase", parentForm = MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Document/Internal/DischargeDiagnost")
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(DocumentPrepareCreateInterceptor.class)
)
public class DischargeDiagnostDocumentForm extends InternalDocumentsForm {
	/** Услуги */
	@Comment("Услуги")
	@Persist @Required
	public String getServicies() {return theServicies;}
	public void setServicies(String aServicies) {theServicies = aServicies;}

	/** Обоснование */
	@Comment("Обоснование")
	@Persist @Required
	public String getHistory() {return theHistory;}
	public void setHistory(String aHistory) {theHistory = aHistory;}

	/** Обоснование */
	private String theHistory;
	/** Услуги */
	private String theServicies;
}
