package ru.ecom.mis.ejb.form.disability;


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
public class DisabilityRecordForm extends IdEntityForm {

	/** Документ нетрудоспособности */
	@Comment("Документ нетрудоспособности")
	@Persist @Required
	public Long getDisabilityDocument() {return theDisabilityDocument;}
	public void setDisabilityDocument(Long aDisabilityDocument) {theDisabilityDocument = aDisabilityDocument;}

	/** Дата создания */
	@Comment("Дата создания")
	@Persist @DateString @DoDateString
	public String getCreateDate() {return theCreateDate;}
	public void setCreateDate(String aCreateDate) {theCreateDate = aCreateDate;}
	private String theCreateDate;
	
	/** Время создания */
	@Comment("Время создания")
	@Persist @DateString @DoDateString
	public String getCreateTime() {return theCreateTime;}
	public void setCreateTime(String aCreateTime) {theCreateTime = aCreateTime;}
	private String theCreateTime;
	
	/** Дата начала нетрудоспособности */
	@Comment("Дата начала нетрудоспособности")
	@Persist @DateString @DoDateString @Required
	public String getDateFrom() {return theDateFrom;}
	public void setDateFrom(String aDateFrom) {theDateFrom = aDateFrom;}
	
	/** Дата окончания нетрудоспособности */
	@Comment("Дата окончания нетрудоспособности")
	@Persist @DateString @DoDateString 
	public String getDateTo() {return theDateTo;}
	public void setDateTo(String aDateTo) {theDateTo = aDateTo;	}
	
	/** Режим нетрудоспособности */
	@Comment("Режим нетрудоспособности")
	@Persist
	public Long getRegime() {return theRegime;}
	public void setRegime(Long aRegime) {theRegime = aRegime;}
	
	/** СМО, создавшего запись */
	@Comment("СМО, создавшего запись")
	@Persist
	public Long getCreateMedCase() {return theCreateMedCase;}
	public void setCreateMedCase(Long aCreateMedCase) {theCreateMedCase = aCreateMedCase;}

	/** Специалист */
	@Comment("Специалист")
	@Persist 
	public Long getWorkFunction() {return theWorkFunction;}
	public void setWorkFunction(Long aWorkFunction) {theWorkFunction = aWorkFunction;}

	/** Режим нетрудоспособности (текст) */
	@Comment("Режим нетрудоспособности (текст)")
	@Persist
	public String getRegimeText() {return theRegimeText;}
	public void setRegimeText(String aRegimeText) {theRegimeText = aRegimeText;}

	/** Информация о записи */
	@Comment("Информация о записи")
	@Persist
	public String getInfo() {return theInfo;}
	public void setInfo(String aInfo) {theInfo = aInfo;}

	/** Рабочая функция инфо */
	@Comment("Рабочая функция инфо")
	@Persist
	public String getWorkFunctionInfo() {return theWorkFunctionInfo;}
	public void setWorkFunctionInfo(String aWorkFunctionInfo) {theWorkFunctionInfo = aWorkFunctionInfo;}

	/** Доп. рабочая функция */
	@Comment("Доп. рабочая функция")
	@Persist
	public Long getWorkFunctionAdd() {return theWorkFunctionAdd;}
	public void setWorkFunctionAdd(Long aWorkFunctionAdd) {theWorkFunctionAdd = aWorkFunctionAdd;}

	/** Доп. рабочая функция */
	private Long theWorkFunctionAdd;
	/** Рабочая функция инфо */
	private String theWorkFunctionInfo;
	/** Информация о записи */
	private String theInfo;
	/** Режим нетрудоспособности (текст) */
	private String theRegimeText;
	/** Специалист */
	private Long theWorkFunction;
	/** Документ нетрудоспособности */
	private Long theDisabilityDocument;
	/** Дата начала нетрудоспособности */
	private String theDateFrom;
	/** Дата окончания нетрудоспособности */
	private String theDateTo;
	/** Режим нетрудоспособности */
	private Long theRegime;
	/** СМО, создавшего запись */
	private Long theCreateMedCase;
	/**
	 * Новое свойство
	 */
	@Comment("Новое свойство")
	@Persist
	public String getWorkFunctionAddInfo() {return theWorkFunctionAddInfo;}
	/** Новое свойство */
	public void setWorkFunctionAddInfo(String a_Property) {theWorkFunctionAddInfo = a_Property;}

	/** Новое свойство */
	private String theWorkFunctionAddInfo;

}
