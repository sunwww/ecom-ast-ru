package ru.ecom.mis.ejb.domain.medcase;

import ru.ecom.diary.ejb.domain.protocol.template.TemplateProtocol;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.contract.ContractPerson;
import ru.ecom.mis.ejb.domain.medcase.voc.VocMedService;
import ru.ecom.mis.ejb.domain.medcase.voc.VocServiceSubType;
import ru.ecom.mis.ejb.domain.medcase.voc.VocServiceType;
import ru.ecom.mis.ejb.domain.patient.voc.VocColorIdentityPatient;
import ru.ecom.mis.ejb.domain.worker.WorkFunctionService;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

//import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream;

/**
 * Медицинская услуга
 * @author azviagin
 *
 */
@Comment("Медицинская услуга")
@Entity
@AIndexes({
    @AIndex(properties="parent"),
    @AIndex(properties={"parent","startDate","finishDate"}),
    @AIndex(properties={"startDate","finishDate","vocMedService"}),
    @AIndex(properties={"serviceType"})
    }) 
@Table(schema="SQLUser")
public class MedService extends BaseEntity {

	/** Отображать результат выполнения услуги в отчете по состоящим в отделении пациентам */
	@Comment("Отображать результат выполнения услуги в отчете по состоящим в отделении пациентам")
	public Boolean getShowInReport() {return theShowInReport;}
	public void setShowInReport(Boolean aShowInReport) {theShowInReport = aShowInReport;}
	/** Отображать результат выполнения услуги в отчете по состоящим в отделении пациентам */
	private Boolean theShowInReport ;

	/** Может назначаться врачом лаборатории */
	@Comment("Может назначаться врачом лаборатории")
	public Boolean getIsForLabDoctor() {return theIsForLabDoctor;}
	public void setIsForLabDoctor(Boolean aIsForLabDoctor) {theIsForLabDoctor = aIsForLabDoctor;}
	/** Может назначаться врачом лаборатории */
	private Boolean theIsForLabDoctor;
	
	
	/** Медицинская услуга по справочнику V001 */
	@Comment("Медицинская услуга по справочнику V001")
	@OneToOne
	public VocMedService getVocMedService() {return theVocMedService;}
	public void setVocMedService(VocMedService aVocMedService) {theVocMedService = aVocMedService;}
	
	/** Родитель */
	@Comment("Родитель")
	@ManyToOne
	public MedService getParent() {return theParent;}
	public void setParent(MedService aParent) {theParent = aParent;}

	/** Вложенные медицинские услуги */
	@Comment("Вложенные медицинские услуги")
	@OneToMany(mappedBy="parent", cascade=CascadeType.ALL)
	public List<MedService> getChildMedService() {return theChildMedService;}
	public void setChildMedService(List<MedService> aChildMedService) {theChildMedService = aChildMedService;}

	/** Справочная услуга инфо */
	@Comment("Справочная услуга инфо")
	@Transient
	public String getVocMedServiceInfo() {
		return theVocMedService!=null ? theVocMedService.getName() : "";
	}
	
	/** Шаблоны заключений */
	@Comment("Шаблоны заключений")
	@OneToMany(mappedBy="medService")
	public List<TemplateProtocol> getTemplates() {return theTemplates;}
	public void setTemplates(List<TemplateProtocol> aTemplates) {theTemplates = aTemplates;}

	/** Шаблоны заключений */
	private List<TemplateProtocol> theTemplates;
	/** Наименование */
	@Comment("Наименование")
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}

	/** Код услуги */
	@Comment("Код услуги")
	public String getCode() {return theCode;}
	public void setCode(String aCode) {theCode = aCode;}

	/** Пользователь */
	@Comment("Пользователь")
	@Persist
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}

	/** Дата создания */
	@Comment("Дата создания")
	public Date getCreateDate() {return theCreateDate;}
	public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}

	/** Дата начала актуальности */
	@Comment("Дата начала актуальности")
	public Date getStartDate() {return theStartDate;}
	public void setStartDate(Date aStartDate) {theStartDate = aStartDate;}

	/** Дата окончания актуальности */
	@Comment("Дата окончания актуальности")
	public Date getFinishDate() {return theFinishDate;}
	public void setFinishDate(Date aFinishDate) {theFinishDate = aFinishDate;}

	@Transient
	public String getCategoryInfo() {
		return getParent()!=null?getParent().getName():"" ;
	}
	
	/** Тип услуги */
	@Comment("Тип услуги")
	@OneToOne
	public VocServiceType getServiceType() {return theServiceType;}
	public void setServiceType(VocServiceType aServiceType) {theServiceType = aServiceType;}

	@Transient
	public String getServiceTypeInfo() {
		return theServiceType!=null?theServiceType.getName():"" ;
	}
	/** Рабочие функции */
	@Comment("Рабочие функции")
	@OneToMany(mappedBy="medService", cascade=CascadeType.ALL)
	public List<WorkFunctionService> getWorkFunctionServices() {return theWorkFunctionServices;}
	public void setWorkFunctionServices(List<WorkFunctionService> aWorkFunctionServices) {	theWorkFunctionServices = aWorkFunctionServices;}

	/** Доп. код услуги */
	@Comment("Доп. код услуги")
	public String getAdditionCode() {return theAdditionCode;}
	public void setAdditionCode(String aAdditionCode) {theAdditionCode = aAdditionCode;}

	/** Доп. код услуги */
	private String theAdditionCode;
	/** Рабочие функции */
	private List<WorkFunctionService> theWorkFunctionServices;
	/** Тип услуги */
	private VocServiceType theServiceType;
	/** Дата окончания актуальности */
	private Date theFinishDate;
	/** Дата начала актуальности */
	private Date theStartDate;
	/** Дата создания */
	private Date theCreateDate;
	/** Пользователь */
	private String theCreateUsername;
	/** Код услуги */
	private String theCode;
	/** Наименование */
	private String theName;
	/** Вложенные медицинские услуги */
	private List<MedService> theChildMedService;
	/** Родитель */
	private MedService theParent;
	/** Справочная услуга */
	private VocMedService theVocMedService;
	
	/** Уровонь сложности */
	@Comment("Уровонь сложности")
	public Long getComplexity() {return theComplexity;}
	public void setComplexity(Long aComplexity) {theComplexity = aComplexity;}
	/** Уровонь сложности */
	private Long theComplexity;
	
	/** Поликлиническая услуга */
	@Comment("Поликлиническая услуга")
	public Boolean getIsPoliclinic() {return theIsPoliclinic;}
	public void setIsPoliclinic(Boolean aIsPoliclinic) {theIsPoliclinic = aIsPoliclinic;}

	/** Поликлиническая услуга */
	private Boolean theIsPoliclinic;
	
	/** Круглосуточный стационар */
	@Comment("Круглосуточный стационар")
	public Boolean getIsHospital() {return theIsHospital;}
	public void setIsHospital(Boolean aHospital) {theIsHospital = aHospital;}

	/** Круглосуточный стационар */
	private Boolean theIsHospital;
	
	/** Дневной стационар */
	@Comment("Дневной стационар")
	public Boolean getIsDayHospital() {return theIsDayHospital;}
	public void setIsDayHospital(Boolean aIsDayHospital) {theIsDayHospital = aIsDayHospital;}

	/** Дневной стационар */
	private Boolean theIsDayHospital;
	
	/** Не входит в ОМС */
	@Comment("Не входит в ОМС")
	public Boolean getIsNoOmc() {return theIsNoOmc;}
	public void setIsNoOmc(Boolean aIsNoOmc) {theIsNoOmc = aIsNoOmc;}

	/** Не входит в ОМС */
	private Boolean theIsNoOmc;
	
	/** Подтип назначения */
	@Comment("Подтип назначения")
	@OneToOne
	public VocServiceSubType getServiceSubType() {return theServiceSubType;}
	public void setServiceSubType(VocServiceSubType aServiceSubType) {theServiceSubType = aServiceSubType;}

	/** Подтип назначения */
	private VocServiceSubType theServiceSubType;
	
	/** Короткое наименование */
	@Comment("Короткое наименование")
	public String getShortName() {return theShortName;}
	public void setShortName(String aShortName) {theShortName = aShortName;}

	/** Короткое наименование */
	private String theShortName;
	
	/** В другом ЛПУ выполняется */
	@Comment("В другом ЛПУ выполняется")
	public Boolean getIsOtherLpu() {return theIsOtherLpu;}
	public void setIsOtherLpu(Boolean aIsOtherLpu) {theIsOtherLpu = aIsOtherLpu;}

	/** Префикс шаблона печати */
	@Comment("Префикс шаблона печати")
	public String getPrefixTemplate() {return thePrefixTemplate;}
	public void setPrefixTemplate(String aPrefixTemplate) {thePrefixTemplate = aPrefixTemplate;}

	/** Организация */
	@Comment("Организация")
	@OneToOne
	public ContractPerson getOrganization() {return theOrganization;}
	public void setOrganization(ContractPerson aOrganization) {theOrganization = aOrganization;}

	/** Организация */
	private ContractPerson theOrganization;
	/** Префикс шаблона печати */
	private String thePrefixTemplate;
	/** В другом ЛПУ выполняется */
	private Boolean theIsOtherLpu;
	/** Обязательное заполнение комментария */
	@Comment("Обязательное заполнение комментария")
	public String getIsReqComment() {return theIsReqComment;}
	public void setIsReqComment(String aIsReqComment) {theIsReqComment = aIsReqComment;}

	/** Обязательное заполнение комментария */
	private String theIsReqComment;
	
	/** Не федеральный код */
	@Comment("Не федеральный код")
	@Deprecated
	public Boolean getIsNoFederal() {return theIsNoFederal;}
	public void setIsNoFederal(Boolean aIsNoFederal) {theIsNoFederal = aIsNoFederal;}

	/** Не федеральный код */
	private Boolean theIsNoFederal;
	
	/** УЕТ */
	@Comment("УЕТ")
	public BigDecimal getUet() {return theUet;}
	public void setUet(BigDecimal aUet) {theUet = aUet;}

	/** УЕТ */
	private BigDecimal theUet;

	private String promedCode;
	@Comment("Код в промеде")
	public String getPromedCode() {
		return promedCode;
	}
	public void setPromedCode(String promedCode) {
		this.promedCode = promedCode;
	}

	/** Отображать код услуги при печати в реестре назначений для лаборатории */
	private Boolean printCodeLabReestr;
	@Comment("Отображать код услуги при печати в реестре назначений для лаборатории")
	public Boolean getPrintCodeLabReestr() {
		return printCodeLabReestr;
	}
	public void setPrintCodeLabReestr(Boolean printCodeLabReestr) {
		this.printCodeLabReestr = printCodeLabReestr;
	}

	/** Указывать тип аборта при создании операции */
	@Comment("Указывать тип аборта при создании операции")
	public Boolean getIsAbortRequired() {return theIsAbortRequired;}
	public void setIsAbortRequired(Boolean aIsAbortRequired) {theIsAbortRequired = aIsAbortRequired;}
	/** Указывать тип аборта при создании операции */
	private Boolean theIsAbortRequired =false;

	/** Отображать на сайте как услугу по умолчанию у специалиста*/
	@Comment("Отображать на сайте как услугу по умолчанию у специалиста")
	public Boolean getIsShowSiteAsDefault() {return theIsShowSiteAsDefault;}
	public void setIsShowSiteAsDefault(Boolean aIsShowSiteAsDefault) {theIsShowSiteAsDefault = aIsShowSiteAsDefault;}
	/** Отображать на сайте как услугу по умолчанию у специалиста */
	private Boolean theIsShowSiteAsDefault =false;

	/** Браслет, который автоматически регистрируется при пустых даты-времени окончания операциии */
	@Comment("Браслет, который автоматически регистрируется при пустых даты-времени окончания операциии")
	@OneToOne
	public VocColorIdentityPatient getVocColorIdentity() {return theVocColorIdentity;}
	public void setVocColorIdentity(VocColorIdentityPatient aVocColorIdentity) {theVocColorIdentity = aVocColorIdentity;}
	/** Браслет, который автоматически регистрируется при пустых даты-времени окончания операциии */
	private VocColorIdentityPatient theVocColorIdentity ;

	/** Всегда выполняется для реанимаций */
	@Comment("Всегда выполняется для реанимаций")
	public Boolean getIsAvailableReanimAlways() {return theIsAvailableReanimAlways;}
	public void setIsAvailableReanimAlways(Boolean aIsAvailableReanimAlways) {theIsAvailableReanimAlways = aIsAvailableReanimAlways;}

	/** Всегда выполняется для реанимаций */
	private Boolean theIsAvailableReanimAlways;
}