package ru.ecom.mis.ejb.form.licence;

import lombok.Setter;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.mis.ejb.domain.licence.RequitDirectionDocument;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

@EntityForm
@EntityFormPersistance(clazz = RequitDirectionDocument.class)
@Comment("Акт в военкомат")
@WebTrail(comment = "Акт в военкомат", nameProperties = "id"
, view = "entityParentView-doc_requitDirection.do"
, shortView="entitySubclassShortView-doc_requitDirection.do")
@Parent(property = "medCase", parentForm = MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Document/Internal/RequitDirection")
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(DocumentPrepareCreateInterceptor.class)
)
@Setter
public class RequitDirectionDocumentForm extends InternalDocumentsForm{
	/** Номер направления */
	@Comment("Номер направления")
	@Persist
	public String getOrderNumber() {return orderNumber;}
	/** Номер направления */
	private String orderNumber;

	/** Дата направления */
	@Comment("Дата направления")
	@Persist @DateString @DoDateString
	public String getOrderDate() {return orderDate;}
	/** Дата направления */
	private String orderDate;
	
	/** Направитель */
	@Comment("Направитель")
	@Persist
	public String getOrderOffice() {return orderOffice;}
	/** Направитель */
	private String orderOffice;

	/** Данные объективного исследования */
	@Comment("Данные объективного исследования")
	@Persist
	public String getResearch() {return research;}
	/** Данные объективного исследования */
	private String research;
	
	/** Результаты диагностических исследований */
	@Comment("Результаты диагностических исследований")
	@Persist
	public String getLabResearch() {return labResearch;}
	/** Результаты диагностических исследований */
	private String labResearch;
	
	/** Жалобы */
	@Comment("Жалобы")
	@Persist
	public String getAbuses() {return abuses;}
	/** Жалобы */
	private String abuses;

}
