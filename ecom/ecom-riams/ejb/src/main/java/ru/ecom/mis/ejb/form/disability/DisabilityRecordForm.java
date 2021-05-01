package ru.ecom.mis.ejb.form.disability;


import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.mis.ejb.domain.disability.DisabilityRecord;
import ru.ecom.mis.ejb.form.disability.interceptors.RecordPreCreate;
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
 * Запись сроков нетрудоспособности 
 * @author stkacheva
 *
 */
@EntityForm
@EntityFormPersistance (clazz = DisabilityRecord.class)
@Comment("Запись сроков нетрудоспособности")
@WebTrail(comment = "Запись сроков нетрудоспособности", nameProperties= "info", view="entityParentView-dis_record.do")
@Parent(property="disabilityDocument",orderBy="dateFrom", parentForm=DisabilityDocumentForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Disability/Case/Document/Record")
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(RecordPreCreate.class)
)
@Setter
public class DisabilityRecordForm extends IdEntityForm {

	/** Документ нетрудоспособности */
	@Comment("Документ нетрудоспособности")
	@Persist @Required
	public Long getDisabilityDocument() {return disabilityDocument;}

	/** Дата создания */
	@Comment("Дата создания")
	@Persist @DateString @DoDateString
	public String getCreateDate() {return createDate;}
	private String createDate;
	
	/** Время создания */
	@Comment("Время создания")
	@Persist @DateString @DoDateString
	public String getCreateTime() {return createTime;}
	private String createTime;
	
	/** Дата начала нетрудоспособности */
	@Comment("Дата начала нетрудоспособности")
	@Persist @DateString @DoDateString @Required
	public String getDateFrom() {return dateFrom;}

	/** Дата окончания нетрудоспособности */
	@Comment("Дата окончания нетрудоспособности")
	@Persist @DateString @DoDateString 
	public String getDateTo() {return dateTo;}

	/** Режим нетрудоспособности */
	@Comment("Режим нетрудоспособности")
	@Persist
	public Long getRegime() {return regime;}

	/** СМО, создавшего запись */
	@Comment("СМО, создавшего запись")
	@Persist
	public Long getCreateMedCase() {return createMedCase;}

	/** Специалист */
	@Comment("Специалист")
	@Persist 
	public Long getWorkFunction() {return workFunction;}

	/** Режим нетрудоспособности (текст) */
	@Comment("Режим нетрудоспособности (текст)")
	@Persist
	public String getRegimeText() {return regimeText;}

	/** Информация о записи */
	@Comment("Информация о записи")
	@Persist
	public String getInfo() {return info;}

	/** Рабочая функция инфо */
	@Comment("Рабочая функция инфо")
	@Persist
	public String getWorkFunctionInfo() {return workFunctionInfo;}

	/** Доп. рабочая функция */
	@Comment("Доп. рабочая функция")
	@Persist
	public Long getWorkFunctionAdd() {return workFunctionAdd;}

	/** Доп. рабочая функция */
	private Long workFunctionAdd;
	/** Рабочая функция инфо */
	private String workFunctionInfo;
	/** Информация о записи */
	private String info;
	/** Режим нетрудоспособности (текст) */
	private String regimeText;
	/** Специалист */
	private Long workFunction;
	/** Документ нетрудоспособности */
	private Long disabilityDocument;
	/** Дата начала нетрудоспособности */
	private String dateFrom;
	/** Дата окончания нетрудоспособности */
	private String dateTo;
	/** Режим нетрудоспособности */
	private Long regime;
	/** СМО, создавшего запись */
	private Long createMedCase;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	@Persist
	public String getWorkFunctionAddInfo() {return workFunctionAddInfo;}
	private String workFunctionAddInfo;

}
