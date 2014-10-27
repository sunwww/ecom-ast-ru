package ru.ecom.mis.ejb.form.medcase;

import javax.persistence.OneToOne;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.ACreateInterceptors;
import ru.ecom.ejb.services.entityform.interceptors.AEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.ASaveInterceptors;
import ru.ecom.ejb.services.entityform.interceptors.AViewInterceptors;
import ru.ecom.mis.ejb.domain.medcase.MedService;
import ru.ecom.mis.ejb.domain.medcase.voc.VocServiceSubType;
import ru.ecom.mis.ejb.form.medcase.interceptor.MedServiceSaveInterceptor;
import ru.ecom.mis.ejb.form.medcase.interceptor.MedServiceViewInterceptor;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoUpperCase;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Медицинская услуга
 * @author stkacheva
 */
@EntityForm
@EntityFormPersistance(clazz=MedService.class)
@Comment("Медицинская услуга")
@WebTrail(comment = "Медицинская услуга"
	, nameProperties= "name"
		, view="entityParentView-mis_medService.do"
//			,list = "entityParentList-mis_medService.do"
				)
@Parent(property="parent", parentForm= MedServiceGroupForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedService")
@ASaveInterceptors(
        @AEntityFormInterceptor(MedServiceSaveInterceptor.class)
)
@AViewInterceptors(
        @AEntityFormInterceptor(MedServiceViewInterceptor.class)
)
@ACreateInterceptors( {
	@AEntityFormInterceptor(MedServiceSaveInterceptor.class)
})
public class MedServiceForm extends IdEntityForm  {
	/** Справочная услуга */
	@Comment("Справочная услуга")
	@Persist
	public Long getVocMedService() {return theVocMedService;}
	public void setVocMedService(Long aVocMedService) {theVocMedService = aVocMedService;}
	
	/** Родитель */
	@Comment("Родитель")
	@Persist
	public Long getParent() {return theParent;}
	public void setParent(Long aParent) {theParent = aParent;}

	/** Справочная услуга */
	@Comment("Справочная услуга")
	@Persist
	public String getVocMedServiceInfo() {return theVocMedServiceInfo;}
	public void setVocMedServiceInfo(String aVocMedServiceInfo) {theVocMedServiceInfo = aVocMedServiceInfo;}

	/** Название */
	@Comment("Название")
	@Persist @Required @DoUpperCase
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}

	
	/** Проба дерева */
	@Comment("Проба дерева")
	public Long getProbaTree() {return theProbaTree;}
	public void setProbaTree(Long aProbaTree) {theProbaTree = aProbaTree;}

	/** Код услуги */
	@Comment("Код услуги")
	@Persist @Required @DoUpperCase
	public String getCode() {return theCode;}
	public void setCode(String aCode) {theCode = aCode;}

	/** Пользователь */
	@Comment("Пользователь")
	@Persist
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}

	/** Дата создания */
	@Comment("Дата создания")
	@Persist @DoDateString @DateString
	public String getCreateDate() {return theCreateDate;}
	public void setCreateDate(String aCreateDate) {theCreateDate = aCreateDate;}

	/** Дата начала актуальности */
	@Comment("Дата начала актуальности")
	@Persist @DoDateString @DateString
	public String getStartDate() {return theStartDate;}
	public void setStartDate(String aStartDate) {theStartDate = aStartDate;}

	/** Дата окончания актуальности */
	@Comment("Дата окончания актуальности")
	@Persist @DoDateString @DateString
	public String getFinishDate() {return theFinishDate;}
	public void setFinishDate(String aFinishDate) {theFinishDate = aFinishDate;}

	/** Тип услуги */
	@Comment("Тип услуги")
	@Persist
	public Long getServiceType() {return theServiceType;}
	public void setServiceType(Long aServiceType) {theServiceType = aServiceType;}

	/** Тип услуги (инфо) */
	@Comment("Тип услуги (инфо)")
	@Persist
	public String getServiceTypeInfo() {return theServiceTypeInfo;}
	public void setServiceTypeInfo(String aServiceTypeInfo) {theServiceTypeInfo = aServiceTypeInfo;}



	@Comment("Доп. код услуги")
	@Persist
	public String getAdditionCode() {return theAdditionCode;}
	public void setAdditionCode(String aAdditionCode) {theAdditionCode = aAdditionCode;}

	/** Доп. код услуги */
	private String theAdditionCode;
	/** Тип услуги (инфо) */
	private String theServiceTypeInfo;
	/** Тип услуги */
	private Long theServiceType;
	/** Дата окончания актуальности */
	private String theFinishDate;
	/** Дата начала актуальности */
	private String theStartDate;
	/** Дата создания */
	private String theCreateDate;
	/** Пользователь */
	private String theCreateUsername;
	/** Код услуги */
	private String theCode;	
	/** Проба дерева */
	private Long theProbaTree;
	/** Название */
	private String theName;
	/** Справочная услуга */
	private String theVocMedServiceInfo;
	/** Родитель */
	private Long theParent;
	/** Справочная услуга */
	private Long theVocMedService;
	
	/** Поликлиническая услуга */
	@Comment("Поликлиническая услуга")
	@Persist
	public Boolean getIsPoliclinic() {return theIsPoliclinic;}
	public void setIsPoliclinic(Boolean aIsPoliclinic) {theIsPoliclinic = aIsPoliclinic;}

	/** Поликлиническая услуга */
	private Boolean theIsPoliclinic;
	
	/** Круглосуточный стационар */
	@Comment("Круглосуточный стационар")
	@Persist
	public Boolean getIsHospital() {return theIsHospital;}
	public void setIsHospital(Boolean aHospital) {theIsHospital = aHospital;}

	/** Круглосуточный стационар */
	private Boolean theIsHospital;
	
	/** Дневной стационар */
	@Comment("Дневной стационар")
	@Persist
	public Boolean getIsDayHospital() {return theIsDayHospital;}
	public void setIsDayHospital(Boolean aIsDayHospital) {theIsDayHospital = aIsDayHospital;}

	/** Дневной стационар */
	private Boolean theIsDayHospital;
	/** Уровонь сложности */
	@Comment("Уровонь сложности")
	@Persist
	public Long getComplexity() {return theComplexity;}
	public void setComplexity(Long aComplexity) {theComplexity = aComplexity;}
	/** Уровонь сложности */
	private Long theComplexity;
	
	/** Не входит в ОМС */
	@Comment("Не входит в ОМС")
	@Persist
	public Boolean getIsNoOmc() {return theIsNoOmc;}
	public void setIsNoOmc(Boolean aIsNoOmc) {theIsNoOmc = aIsNoOmc;}

	/** Не входит в ОМС */
	private Boolean theIsNoOmc;
	
	/** Создать услугу во внеш. справочник */
	@Comment("Создать услугу во внеш. справочник")
	public Boolean getVocMedServiceIsCreate() {return theVocMedServiceIsCreate;}
	public void setVocMedServiceIsCreate(Boolean aVocMedServiceIsCreate) {theVocMedServiceIsCreate = aVocMedServiceIsCreate;}

	/** Создать услугу во внеш. справочник */
	private Boolean theVocMedServiceIsCreate;
	
	@Comment("Подтип назначения")
	@Persist
	public Long getServiceSubType() {return theServiceSubType;}
	public void setServiceSubType(Long aServiceSubType) {theServiceSubType = aServiceSubType;}

	/** Подтип назначения */
	private Long theServiceSubType;
}
