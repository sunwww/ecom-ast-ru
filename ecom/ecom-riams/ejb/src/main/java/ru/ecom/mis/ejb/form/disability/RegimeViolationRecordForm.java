package ru.ecom.mis.ejb.form.disability;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.mis.ejb.domain.disability.RegimeViolationRecord;
import ru.ecom.mis.ejb.form.disability.interceptors.RegimeViolationRecordPreCreate;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Запись о нарушении режима
 * @author stkacheva
 *
 */
@EntityForm
@EntityFormPersistance (clazz = RegimeViolationRecord.class)
@Comment("Запись о нарушении режима")
@WebTrail(comment = "Запись о нарушении режима", nameProperties= "info", view="entityParentView-dis_regimeViolation.do")
@Parent(property="disabilityDocument", parentForm=DisabilityDocumentForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Disability/Case/Document/RegimeViolationRecord")
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(RegimeViolationRecordPreCreate.class)
)
public class RegimeViolationRecordForm extends IdEntityForm {

	/** Документ нетрудоспособности */
	@Comment("Документ нетрудоспособности")
	@Persist @Required
	public Long getDisabilityDocument() {return theDisabilityDocument;}
	public void setDisabilityDocument(Long aDisabilityDocument) {theDisabilityDocument = aDisabilityDocument;}
	
	/** Дата начала нарушения */
	@Comment("Дата начала нарушения")
	@Persist @DateString @DoDateString @Required
	public String getDateFrom() {return theDateFrom;}
	public void setDateFrom(String aDateFrom) {theDateFrom = aDateFrom;}
	
	/** Дата окончания нарушения */
	@Comment("Дата окончания нарушения")
	@Persist @DateString @DoDateString
	public String getDateTo() {return theDateTo;}
	public void setDateTo(String aDateTo) {theDateTo = aDateTo;}

	/** Тип нарушения */
	@Comment("Тип нарушения")
	@Persist @Required
	public Long getRegimeViolationType() {return theRegimeViolationType;}
	public void setRegimeViolationType(Long aRegimeViolationType) {theRegimeViolationType = aRegimeViolationType;}
	
	/** Комментарии */
	@Comment("Комментарии")
	@Persist
	public String getComment() {return theComment;}
	public void setComment(String aComment) {theComment = aComment;}
	
	/** Тип нарушения инфо */
	@Comment("Тип нарушения инфо")
	@Persist
	public String getRegimeViolationTypeInfo() {return theRegimeViolationTypeInfo;	}
	public void setRegimeViolationTypeInfo(String aRegimeViolationTypeInfo) {theRegimeViolationTypeInfo = aRegimeViolationTypeInfo;}

	/** Информация о нарушении режима */
	@Comment("Информация о нарушении режима")
	@Persist
	public String getInfo() {return theInfo;}
	public void setInfo(String aInfo) {theInfo = aInfo;}

	/** Информация о нарушении режима */
	private String theInfo;
	/** Тип нарушения инфо */
	private String theRegimeViolationTypeInfo;
	/** Документ нетрудоспособности */
	private Long theDisabilityDocument;
	/** Дата начала нарушения */
	private String theDateFrom;
	/** Дата окончания нарушения */
	private String theDateTo;
	/** Тип нарушения */
	private Long theRegimeViolationType;
	/** Комментарии */
	private String theComment;

}
