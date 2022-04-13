package ru.ecom.mis.ejb.domain.lpu;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.address.ejb.domain.address.Address;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.equipment.Equipment;
import ru.ecom.mis.ejb.domain.lpu.voc.VocLpuAccessEnterOperation;
import ru.ecom.mis.ejb.domain.lpu.voc.VocLpuFunction;
import ru.ecom.mis.ejb.domain.medcase.voc.VocKiliProfile;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPigeonHole;
import ru.ecom.mis.ejb.domain.medstandard.MedicalStandard;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.mis.ejb.domain.worker.Worker;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

/**
 * ЛПУ
 */
@Entity(name = "MisLpu")
@AIndexes({
        @AIndex(properties = {"lpuFunction"})
        , @AIndex(properties = {"parent"})
        , @AIndex(properties = {"name"})
        , @AIndex(properties = {"pigeonHole"})
        , @AIndex(properties = {"parent", "name"})
})
@Table(schema = "SQLUser")
@Getter
@Setter
public class MisLpu extends BaseEntity {

    /**
     * Признак мобильной поликлиники
     */
    private Boolean isMobilePolyclinic;
    /**
     * Стандарт оказания мед. помощи
     */
    private MedicalStandard medicalStandard;
    /**
     * Рабочие функции
     */
    private List<WorkFunction> workFunctions;
    /**
     * Подразделения ЛПУ
     */
    private List<MisLpu> subdivisions;
    /**
     * Родитель
     */
    private MisLpu parent;
    /**
     * Оборудование
     */
    private List<Equipment> equipment;
    /**
     * Коечный фонд
     */
    private List<BedFund> bedFund;
    /**
     * Адрес для печати
     */
    private String printAddress;
    /**
     * Название для печати
     */
    private String printName;
    /**
     * Не входит в оплату по ОМС
     */
    private Boolean isNoOmc;
    /**
     * Функция ЛПУ
     */
    private VocLpuFunction lpuFunction;
    /**
     * Телефон
     */
    private String phone;
    /**
     * Руководитель
     */
    private String director;
    /**
     * Срок действия лицензии
     */
    private Date licenseExpired;
    /**
     * Номер лицензии
     */
    private String licenseNumber;
    /**
     * Эл. адрес
     */
    private String email;
    /**
     * ОГРН
     */
    private Long ogrn;
    /**
     * ИНН
     */
    private Long inn;
    /**
     * Корпус
     */
    private String houseBuilding;
    /**
     * Номер дома
     */
    private String houseNumber;
    /**
     * Адрес
     */
    private Address address;
    /**
     * Код ОМС
     */
    private String omcCode;
    /**
     * Код адреса отделения
     */
    private String departmentAddressCode;
    /**
     * Терапевтические участки
     */
    private List<LpuArea> areas;
    /**
     * Наименование ЛПУ
     */
    private String name;
    /**
     * Комментарий
     */
    private String comment;
    /**
     * Работники ЛПУ
     */
    private List<Worker> worker;
    /**
     * Код федеральный
     */
    private String codef;
    /**
     * Руководитель
     */
    private WorkFunction manager;
    /**
     * Интервал разрешенной регистрации
     */
    private Integer registrationInterval;
    /**
     * Приемное отделение
     */
    private VocPigeonHole pigeonHole;
    /**
     * Не показывать удаленным пользователям
     */
    private Boolean isNoViewRemoteUser;
    /**
     * Принтер по умолчанию
     */
    private CopyingEquipment copyingEquipmentDefault;
    /**
     * Пользователь, который последний редактировал запись
     */
    private String editUsername;
    /**
     * Пользователь, который создал запись
     */
    private String createUsername;
    /**
     * Время редактрования
     */
    private Time editTime;
    /**
     * Время создания
     */
    private Time createTime;
    /**
     * Дата редактирования
     */
    private Date editDate;
    /**
     * Дата создания
     */
    private Date createDate;
    /**
     * Автогенерация расписания
     */
    private Boolean autoGeneration;
    /**
     * Номер в ФСС
     */
    private String socCode;
    /**
     * Возможен забор крови
     */
    private Boolean isIntakeBioMaterial;
    /**
     * Код подразделения
     */
    private String codeDepartment;
    /**
     * Короткое наименование
     */
    private String shortName;
    /**
     * Доступ на создание операций по отделению
     */
    private VocLpuAccessEnterOperation accessEnterOperation;
    /**
     * Префикс для шаблонов ЛН
     */
    private String prefixForLN;
    /**
     * Экстренный кабинет
     */
    private Long emergencyCabinet;
    /**
     * Отделения для новорожденных
     */
    private Boolean isNewBornDep;
    /**
     * Родильное отделение
     */
    private Boolean isMaternityWard;
    /**
     * Отделение патологии беременности
     */
    private Boolean isPatologyPregnant;
    /**
     * Уровень оказания медицинской помощи
     */
    private Integer lpuLevel;
    /**
     * Профиль КИЛИ
     */
    private VocKiliProfile kiliProfile;
    /**
     * В архиве
     */
    private Boolean isArchive;
    /**
     * Обсервационное?
     */
    private Boolean isObservable;
    /**
     * Палата новорождённых?
     */
    private Boolean isNewBorn;
    /**
     * Создают ли кадриоскрининг новорождённым?
     */
    private Boolean isCreateCardiacScreening;
    /**
     * Офтальмологическое?
     */
    private Boolean isOphthalmic;
    /**
     * Учитывать в отчёте по КР?
     */
    private Boolean isReportKMP;
    /**
     * Инфекционное?
     */
    private Boolean isForCovid;
    /**
     * Опер. блок?
     */
    private Boolean isOperBlock;

    /**
     * Ид отделения в промеде
     */
    private Long promedLpuSectionId;


    /**
     * Стандарт оказания мед. помощи
     */
    @Comment("Стандарт оказания мед. помощи")
    @OneToOne
    public MedicalStandard getMedicalStandard() {
        return medicalStandard;
    }

    /**
     * Рабочие функции
     */
    @Comment("Рабочие функции")
    @OneToMany(mappedBy = "lpu", cascade = CascadeType.ALL)
    public List<WorkFunction> getWorkFunctions() {
        return workFunctions;
    }

    /**
     * Родитель
     */
    @ManyToOne
    public MisLpu getParent() {
        return parent;
    }

    /**
     * Подразделения ЛПУ
     */
    @OneToMany(mappedBy = "parent", cascade = ALL)
    public List<MisLpu> getSubdivisions() {
        return subdivisions;
    }

    /**
     * Полное имя
     */
    @Transient
    public String getFullname() {
        if (parent != null) {
            return parent.getName() + " / " + name;
        } else {
            return name;
        }
    }

    public void setFullname(String aFullname) {
    }


    /**
     * Терапевтические участки
     */
    @OneToMany(mappedBy = "lpu", cascade = ALL)
    public List<LpuArea> getAreas() {
        return areas;
    }

    /**
     * Работники ЛПУ
     */
    @OneToMany(mappedBy = "lpu", cascade = ALL)
    public List<Worker> getWorker() {
        return worker;
    }


    /**
     * Адрес
     */
    @OneToOne
    public Address getAddress() {
        return address;
    }

    /**
     * Оборудование
     */
    @OneToMany(mappedBy = "lpu", cascade = ALL)
    public List<Equipment> getEquipment() {
        return equipment;
    }

    /**
     * Коечный фонд
     */
    @Comment("Коечный фонд")
    @OneToMany(mappedBy = "lpu", cascade = ALL)
    public List<BedFund> getBedFund() {
        return bedFund;
    }

    /**
     * Функция ЛПУ
     */
    @Comment("Функция ЛПУ")
    @OneToOne
    public VocLpuFunction getLpuFunction() {
        return lpuFunction;
    }

    /**
     * Руководитель
     */
    @Comment("Руководитель")
    @OneToOne
    public WorkFunction getManager() {
        return manager;
    }

    /**
     * Приемное отделение
     */
    @Comment("Приемное отделение")
    @OneToOne
    public VocPigeonHole getPigeonHole() {
        return pigeonHole;
    }

    /**
     * Принтер по умолчанию
     */
    @Comment("Принтер по умолчанию")
    @OneToOne
    public CopyingEquipment getCopyingEquipmentDefault() {
        return copyingEquipmentDefault;
    }

    /**
     * Доступ на создание операций по отделению
     */
    @Comment("Доступ на создание операций по отделению")
    @OneToOne
    public VocLpuAccessEnterOperation getAccessEnterOperation() {
        return accessEnterOperation;
    }

    /**
     * Профиль КИЛИ
     */
    @Comment("Профиль КИЛИ")
    @ManyToOne
    public VocKiliProfile getKiliProfile() {
        return kiliProfile;
    }
}