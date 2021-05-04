package ru.ecom.mis.ejb.form.disability;

import lombok.Setter;
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
@Setter
public class RegimeViolationRecordForm extends IdEntityForm {

	/** Документ нетрудоспособности */
	@Comment("Документ нетрудоспособности")
	@Persist @Required
	public Long getDisabilityDocument() {return disabilityDocument;}

	/** Дата начала нарушения */
	@Comment("Дата начала нарушения")
	@Persist @DateString @DoDateString @Required
	public String getDateFrom() {return dateFrom;}

	/** Дата окончания нарушения */
	@Comment("Дата окончания нарушения")
	@Persist @DateString @DoDateString
	public String getDateTo() {return dateTo;}

	/** Тип нарушения */
	@Comment("Тип нарушения")
	@Persist @Required
	public Long getRegimeViolationType() {return regimeViolationType;}

	/** Комментарии */
	@Comment("Комментарии")
	@Persist
	public String getComment() {return comment;}

	/** Тип нарушения инфо */
	@Comment("Тип нарушения инфо")
	@Persist
	public String getRegimeViolationTypeInfo() {return regimeViolationTypeInfo;	}

	/** Информация о нарушении режима */
	@Comment("Информация о нарушении режима")
	@Persist
	public String getInfo() {return info;}

	/** Информация о нарушении режима */
	private String info;
	/** Тип нарушения инфо */
	private String regimeViolationTypeInfo;
	/** Документ нетрудоспособности */
	private Long disabilityDocument;
	/** Дата начала нарушения */
	private String dateFrom;
	/** Дата окончания нарушения */
	private String dateTo;
	/** Тип нарушения */
	private Long regimeViolationType;
	/** Комментарии */
	private String comment;

}
