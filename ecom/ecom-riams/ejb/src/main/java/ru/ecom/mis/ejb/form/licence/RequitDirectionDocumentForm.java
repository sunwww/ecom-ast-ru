package ru.ecom.mis.ejb.form.licence;

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
public class RequitDirectionDocumentForm extends InternalDocumentsForm{
	/** Номер направления */
	@Comment("Номер направления")
	@Persist
	public String getOrderNumber() {return theOrderNumber;}
	public void setOrderNumber(String aOrderNumber) {theOrderNumber = aOrderNumber;}
	/** Номер направления */
	private String theOrderNumber;

	/** Дата направления */
	@Comment("Дата направления")
	@Persist @DateString @DoDateString
	public String getOrderDate() {return theOrderDate;}
	public void setOrderDate(String aOrderDate) {theOrderDate = aOrderDate;}
	/** Дата направления */
	private String theOrderDate;
	
	/** Направитель */
	@Comment("Направитель")
	@Persist
	public String getOrderOffice() {return theOrderOffice;}
	public void setOrderOffice(String aOrderOffice) {theOrderOffice = aOrderOffice;}
	/** Направитель */
	private String theOrderOffice;

	/** Данные объективного исследования */
	@Comment("Данные объективного исследования")
	@Persist
	public String getResearch() {return theResearch;}
	public void setResearch(String aResearch) {theResearch = aResearch;}
	/** Данные объективного исследования */
	private String theResearch;
	
	/** Результаты диагностических исследований */
	@Comment("Результаты диагностических исследований")
	@Persist
	public String getLabResearch() {return theLabResearch;}
	public void setLabResearch(String aLabResearch) {theLabResearch = aLabResearch;}
	/** Результаты диагностических исследований */
	private String theLabResearch;
	
	/** Жалобы */
	@Comment("Жалобы")
	@Persist
	public String getAbuses() {return theAbuses;}
	public void setAbuses(String aAbuses) {theAbuses = aAbuses;}
	/** Жалобы */
	private String theAbuses;

}
