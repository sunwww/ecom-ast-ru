package ru.ecom.mis.ejb.form.medcase;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.ACreateInterceptors;
import ru.ecom.ejb.services.entityform.interceptors.AEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.ASaveInterceptors;
import ru.ecom.ejb.services.entityform.interceptors.AViewInterceptors;
import ru.ecom.mis.ejb.domain.medcase.MedService;
import ru.ecom.mis.ejb.form.medcase.interceptor.MedServiceSaveInterceptor;
import ru.ecom.mis.ejb.form.medcase.interceptor.MedServiceViewInterceptor;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoUpperCase;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Медицинская услуга
 *
 * @author stkacheva
 */
@EntityForm
@EntityFormPersistance(clazz = MedService.class)
@Comment("Медицинская услуга")
@WebTrail(comment = "Медицинская услуга"
        , nameProperties = "name"
        , view = "entityParentView-mis_medService.do"
)
@Parent(property = "parent", parentForm = MedServiceGroupForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedService")
@ASaveInterceptors(
        @AEntityFormInterceptor(MedServiceSaveInterceptor.class)
)
@AViewInterceptors(
        @AEntityFormInterceptor(MedServiceViewInterceptor.class)
)
@ACreateInterceptors({
        @AEntityFormInterceptor(MedServiceSaveInterceptor.class)
})
@Setter
public class MedServiceForm extends IdEntityForm {

    /**
     * Отображать результат выполнения услуги в отчете по состоящим в отделении пациентам
     */
    @Comment("Отображать результат выполнения услуги в отчете по состоящим в отделении пациентам")
    @Persist
    public Boolean getShowInReport() {
        return showInReport;
    }

    /**
     * Отображать результат выполнения услуги в отчете по состоящим в отделении пациентам
     */
    private Boolean showInReport;

    /**
     * Может назначаться врачом лаборатории
     */
    @Comment("Может назначаться врачом лаборатории")
    @Persist
    public Boolean getIsForLabDoctor() {
        return isForLabDoctor;
    }

    /**
     * Может назначаться врачом лаборатории
     */
    private Boolean isForLabDoctor;

    /**
     * Справочная услуга
     */
    @Comment("Справочная услуга")
    @Persist
    public Long getVocMedService() {
        return vocMedService;
    }

    /**
     * Родитель
     */
    @Comment("Родитель")
    @Persist
    public Long getParent() {
        return parent;
    }

    /**
     * Справочная услуга
     */
    @Comment("Справочная услуга")
    @Persist
    public String getVocMedServiceInfo() {
        return vocMedServiceInfo;
    }

    /**
     * Название
     */
    @Comment("Название")
    @Persist
    @Required
    @DoUpperCase
    public String getName() {
        return name;
    }

    /**
     * Проба дерева
     */
    @Comment("Проба дерева")
    public Long getProbaTree() {
        return probaTree;
    }

    /**
     * Код услуги
     */
    @Comment("Код услуги")
    @Persist
    @Required
    @DoUpperCase
    public String getCode() {
        return code;
    }

    /**
     * Пользователь
     */
    @Comment("Пользователь")
    @Persist
    public String getCreateUsername() {
        return createUsername;
    }

    /**
     * Дата создания
     */
    @Comment("Дата создания")
    @Persist
    @DoDateString
    @DateString
    public String getCreateDate() {
        return createDate;
    }

    /**
     * Дата начала актуальности
     */
    @Comment("Дата начала актуальности")
    @Persist
    @DoDateString
    @DateString
    public String getStartDate() {
        return startDate;
    }

    /**
     * Дата окончания актуальности
     */
    @Comment("Дата окончания актуальности")
    @Persist
    @DoDateString
    @DateString
    public String getFinishDate() {
        return finishDate;
    }

    /**
     * Тип услуги
     */
    @Comment("Тип услуги")
    @Persist
    public Long getServiceType() {
        return serviceType;
    }

    /**
     * Тип услуги (инфо)
     */
    @Comment("Тип услуги (инфо)")
    @Persist
    public String getServiceTypeInfo() {
        return serviceTypeInfo;
    }


    @Comment("Доп. код услуги")
    @Persist
    public String getAdditionCode() {
        return additionCode;
    }

    /**
     * Доп. код услуги
     */
    private String additionCode;
    /**
     * Тип услуги (инфо)
     */
    private String serviceTypeInfo;
    /**
     * Тип услуги
     */
    private Long serviceType;
    /**
     * Дата окончания актуальности
     */
    private String finishDate;
    /**
     * Дата начала актуальности
     */
    private String startDate;
    /**
     * Дата создания
     */
    private String createDate;
    /**
     * Пользователь
     */
    private String createUsername;
    /**
     * Код услуги
     */
    private String code;
    /**
     * Проба дерева
     */
    private Long probaTree;
    /**
     * Название
     */
    private String name;
    /**
     * Справочная услуга
     */
    private String vocMedServiceInfo;
    /**
     * Родитель
     */
    private Long parent;
    /**
     * Справочная услуга
     */
    private Long vocMedService;

    /**
     * Поликлиническая услуга
     */
    @Comment("Поликлиническая услуга")
    @Persist
    public Boolean getIsPoliclinic() {
        return isPoliclinic;
    }

    /**
     * Поликлиническая услуга
     */
    private Boolean isPoliclinic;

    /**
     * Круглосуточный стационар
     */
    @Comment("Круглосуточный стационар")
    @Persist
    public Boolean getIsHospital() {
        return isHospital;
    }

    /**
     * Круглосуточный стационар
     */
    private Boolean isHospital;

    /**
     * Дневной стационар
     */
    @Comment("Дневной стационар")
    @Persist
    public Boolean getIsDayHospital() {
        return isDayHospital;
    }

    /**
     * Дневной стационар
     */
    private Boolean isDayHospital;

    /**
     * Уровонь сложности
     */
    @Comment("Уровонь сложности")
    @Persist
    public Long getComplexity() {
        return complexity;
    }

    /**
     * Уровонь сложности
     */
    private Long complexity;

    /**
     * Не входит в ОМС
     */
    @Comment("Не входит в ОМС")
    @Persist
    public Boolean getIsNoOmc() {
        return isNoOmc;
    }

    /**
     * Не входит в ОМС
     */
    private Boolean isNoOmc;

    /**
     * Создать услугу во внеш. справочник
     */
    @Comment("Создать услугу во внеш. справочник")
    public Boolean getVocMedServiceIsCreate() {
        return vocMedServiceIsCreate;
    }

    /**
     * Создать услугу во внеш. справочник
     */
    private Boolean vocMedServiceIsCreate;

    @Comment("Подтип назначения")
    @Persist
    public Long getServiceSubType() {
        return serviceSubType;
    }

    /**
     * Подтип назначения
     */
    private Long serviceSubType;

    /**
     * Короткое наименование
     */
    @Comment("Короткое наименование")
    @Persist
    public String getShortName() {
        return shortName;
    }

    /**
     * Короткое наименование
     */
    private String shortName;

    /**
     * В другом ЛПУ выполняется
     */
    @Comment("В другом ЛПУ выполняется")
    @Persist
    public Boolean getIsOtherLpu() {
        return isOtherLpu;
    }

    /**
     * Префикс шаблона печати
     */
    @Comment("Префикс шаблона печати")
    @Persist
    public String getPrefixTemplate() {
        return prefixTemplate;
    }

    /**
     * Организация
     */
    @Comment("Организация")
    @Persist
    public Long getOrganization() {
        return organization;
    }

    /**
     * Организация
     */
    private Long organization;
    /**
     * Префикс шаблона печати
     */
    private String prefixTemplate;
    /**
     * В другом ЛПУ выполняется
     */
    private Boolean isOtherLpu;

    /**
     * Обязательное заполнение комментария
     */
    @Comment("Обязательное заполнение комментария")
    @Persist
    public String getIsReqComment() {
        return isReqComment;
    }

    /**
     * Обязательное заполнение комментария
     */
    private String isReqComment;

    /**
     * Не федеральный код
     */
    @Comment("Не федеральный код")
    @Persist
    public Boolean getIsNoFederal() {
        return isNoFederal;
    }

    /**
     * Не федеральный код
     */
    private Boolean isNoFederal;

    /**
     * УЕТ
     */
    @Comment("УЕТ")
    @Persist
    public String getUet() {
        return uet;
    }

    /**
     * УЕТ
     */
    private String uet;

    /**
     * Указывать тип аборта при создании операции
     */
    @Persist
    @Comment("Указывать тип аборта при создании операции")
    public Boolean getIsAbortRequired() {
        return isAbortRequired;
    }

    /**
     * Указывать тип аборта при создании операции
     */
    private Boolean isAbortRequired = false;

    /**
     * Отображать код услуги при печати в реестре назначений для лаборатории
     */
    private Boolean printCodeLabReestr;

    @Persist
    @Comment("Отображать код услуги при печати в реестре назначений для лаборатории")
    public Boolean getPrintCodeLabReestr() {
        return printCodeLabReestr;
    }

    /**
     * Отображать на сайте как услугу по умолчанию у специалиста
     */
    @Persist
    @Comment("Отображать на сайте как услугу по умолчанию у специалиста")
    public Boolean getIsShowSiteAsDefault() {
        return isShowSiteAsDefault;
    }

    /**
     * Отображать на сайте как услугу по умолчанию у специалиста
     */
    private Boolean isShowSiteAsDefault = false;

    /**
     * Браслет, который автоматически регистрируется при пустых даты-времени окончания операциии
     */
    @Comment("Браслет, который автоматически регистрируется при пустых даты-времени окончания операциии")
    @Persist
    public Long getVocColorIdentity() {
        return vocColorIdentity;
    }

    /**
     * Браслет, который автоматически регистрируется при пустых даты-времени окончания операциии
     */
    private Long vocColorIdentity;

    /**
     * Всегда выполняется для реанимаций
     */
    @Comment("Всегда выполняется для реанимаций (при типе назначения 24Ч)")
    @Persist
    public Boolean getIsAvailableReanimAlways() {
        return isAvailableReanimAlways;
    }

    /**
     * Всегда выполняется для реанимаций (24Ч)
     */
    private Boolean isAvailableReanimAlways;

    /**
     * Обязательно указывать статус пациентки?
     */
    @Comment("Обязательно указывать статус пациентки?")
    @Persist
    public Boolean getIsAskStatusWomen() {
        return isAskStatusWomen;
    }

    /**
     * Обязательно указывать статус пациентки?
     */
    private Boolean isAskStatusWomen;

    /**
     * Обязательно указывать вид биоматериала?
     */
    @Comment("Обязательно указывать вид биоматериала?")
    @Persist
    public Boolean getIsAskBioType() {
        return isAskBioType;
    }

    /**
     * Обязательно указывать вид биоматериала?
     */
    private Boolean isAskBioType;

    /**
     * Обязательно указывать рост, вес и возраст?
     */
    @Comment("Обязательно указывать рост, вес и возраст?")
    @Persist
    public Boolean getIsAskHWA() {
        return isAskHWA;
    }
    /**
     * Обязательно указывать рост, вес и возраст?
     */
    private Boolean isAskHWA;
}
