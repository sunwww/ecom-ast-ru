package ru.ecom.mis.ejb.domain.medcase;

import lombok.Getter;
import lombok.Setter;
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
 *
 * @author azviagin
 */
@Comment("Медицинская услуга")
@Entity
@AIndexes({
        @AIndex(properties = "parent"),
        @AIndex(properties = {"parent", "startDate", "finishDate"}),
        @AIndex(properties = {"startDate", "finishDate", "vocMedService"}),
        @AIndex(properties = {"serviceType"})
})
@Table(schema = "SQLUser")
@Getter
@Setter
public class MedService extends BaseEntity {


    /**
     * Отображать результат выполнения услуги в отчете по состоящим в отделении пациентам
     */
    private Boolean showInReport;

    /**
     * Может назначаться врачом лаборатории
     */
    private Boolean isForLabDoctor;


    /**
     * Медицинская услуга по справочнику V001
     */
    @Comment("Медицинская услуга по справочнику V001")
    @OneToOne
    public VocMedService getVocMedService() {
        return vocMedService;
    }

    /**
     * Родитель
     */
    @Comment("Родитель")
    @ManyToOne
    public MedService getParent() {
        return parent;
    }

    /**
     * Вложенные медицинские услуги
     */
    @Comment("Вложенные медицинские услуги")
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    public List<MedService> getChildMedService() {
        return childMedService;
    }

    /**
     * Справочная услуга инфо
     */
    @Comment("Справочная услуга инфо")
    @Transient
    public String getVocMedServiceInfo() {
        return vocMedService != null ? vocMedService.getName() : "";
    }

    /**
     * Шаблоны заключений
     */
    @Comment("Шаблоны заключений")
    @OneToMany(mappedBy = "medService")
    public List<TemplateProtocol> getTemplates() {
        return templates;
    }

    /**
     * Шаблоны заключений
     */
    private List<TemplateProtocol> templates;

    /**
     * Пользователь
     */
    @Comment("Пользователь")
    @Persist
    public String getCreateUsername() {
        return createUsername;
    }

    @Transient
    public String getCategoryInfo() {
        return getParent() != null ? getParent().getName() : "";
    }

    /**
     * Тип услуги
     */
    @Comment("Тип услуги")
    @OneToOne
    public VocServiceType getServiceType() {
        return serviceType;
    }

    @Transient
    public String getServiceTypeInfo() {
        return serviceType != null ? serviceType.getName() : "";
    }

    /**
     * Рабочие функции
     */
    @Comment("Рабочие функции")
    @OneToMany(mappedBy = "medService", cascade = CascadeType.ALL)
    public List<WorkFunctionService> getWorkFunctionServices() {
        return workFunctionServices;
    }

    /**
     * Доп. код услуги
     */
    private String additionCode;
    /**
     * Рабочие функции
     */
    private List<WorkFunctionService> workFunctionServices;
    /**
     * Тип услуги
     */
    private VocServiceType serviceType;
    /**
     * Дата окончания актуальности
     */
    private Date finishDate;
    /**
     * Дата начала актуальности
     */
    private Date startDate;
    /**
     * Дата создания
     */
    private Date createDate;
    /**
     * Пользователь
     */
    private String createUsername;
    /**
     * Код услуги
     */
    private String code;
    /**
     * Наименование
     */
    private String name;
    /**
     * Вложенные медицинские услуги
     */
    private List<MedService> childMedService;
    /**
     * Родитель
     */
    private MedService parent;
    /**
     * Справочная услуга
     */
    private VocMedService vocMedService;

    /**
     * Уровонь сложности
     */
    private Long complexity;

    /**
     * Поликлиническая услуга
     */
    private Boolean isPoliclinic;

    /**
     * Круглосуточный стационар
     */
    private Boolean isHospital;

    /**
     * Дневной стационар
     */
    private Boolean isDayHospital;

    /**
     * Не входит в ОМС
     */
    private Boolean isNoOmc;

    /**
     * Подтип назначения
     */
    @Comment("Подтип назначения")
    @OneToOne
    public VocServiceSubType getServiceSubType() {
        return serviceSubType;
    }

    /**
     * Подтип назначения
     */
    private VocServiceSubType serviceSubType;

    /**
     * Короткое наименование
     */
    private String shortName;

    /**
     * Организация
     */
    @Comment("Организация")
    @OneToOne
    public ContractPerson getOrganization() {
        return organization;
    }

    /**
     * Организация
     */
    private ContractPerson organization;
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
    private String isReqComment;

    /**
     * Не федеральный код
     */
    @Deprecated
    private Boolean isNoFederal;


    /**
     * УЕТ
     */
    private BigDecimal uet;

    private String promedCode;

    /**
     * Отображать код услуги при печати в реестре назначений для лаборатории
     */
    private Boolean printCodeLabReestr;

    /**
     * Указывать тип аборта при создании операции
     */
    private Boolean isAbortRequired = false;

    /**
     * Отображать на сайте как услугу по умолчанию у специалиста
     */
    private Boolean isShowSiteAsDefault = false;

    /**
     * Браслет, который автоматически регистрируется при пустых даты-времени окончания операциии
     */
    @Comment("Браслет, который автоматически регистрируется при пустых даты-времени окончания операциии")
    @OneToOne
    public VocColorIdentityPatient getVocColorIdentity() {
        return vocColorIdentity;
    }

    /**
     * Браслет, который автоматически регистрируется при пустых даты-времени окончания операциии
     */
    private VocColorIdentityPatient vocColorIdentity;

    /**
     * Всегда выполняется для реанимаций (при типе назначения 24Ч)
     */
    private Boolean isAvailableReanimAlways;

    /**
     * Обязательно указывать статус пациентки?
     */
    private Boolean isAskStatusWomen;

    /**
     * Обязательно указывать вид биоматериала?
     */
    private Boolean isAskBioType;

    /**
     * Обязательно указывать рост, вес и возраст?
     */
    private Boolean isAskHWA;
}