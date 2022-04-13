package ru.ecom.mis.ejb.form.lpu;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdNameEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.ADynamicParentSecurityInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.ADynamicSecurityInterceptor;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.form.lpu.interceptors.MisLpuDynamicSecurity;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.transforms.DoUpperCase;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;


/**
 * ЛПУ
 */
@Comment("ЛПУ")
@EntityForm
@EntityFormPersistance(clazz = MisLpu.class)
@WebTrail(comment = "ЛПУ", nameProperties = "name", view = "entityView-mis_lpu.do")
@Parent(property = "parent", parentForm = MisLpuForm.class)

@EntityFormSecurityPrefix("/Policy/Mis/MisLpu")

@ADynamicSecurityInterceptor(MisLpuDynamicSecurity.class)
@ADynamicParentSecurityInterceptor(MisLpuDynamicSecurity.class)
@Setter
public class MisLpuForm extends IdNameEntityForm {

    /**
     * Признак мобильной поликлиники
     */
    private Boolean isMobilePolyclinic;
    /**
     * Стандарт оказания мед. помощи
     */
    private Long medicalStandard;
    /**
     * Не входит в оплату по ОМС
     */
    private Boolean isNoOmc;
    /**
     * Участки
     */
    private String areas;
    /**
     * Полное имя
     */
    private String fullname;
    /**
     * Родитель
     */
    private Long parent;
    /**
     * Функция ЛПУ
     */
    private Long lpuFunction;
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
    private String licenseExpired;
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
     * Код адреса отделения
     */
    private String departmentAddressCode;
    /**
     * Адрес
     */
    private Long address;
    /**
     * Код ОМС
     */
    private String omcCode;
    /**
     * Адрес для печати
     */
    private String printAddress;
    /**
     * Название для печати
     */
    private String printName;
    /**
     * Руководитель
     */
    private Long manager;
    /**
     * Приемное отделение
     */
    private Long pigeonHole;
    /**
     * Принтер по умолчанию
     */
    private Long copyingEquipmentDefault;
    /**
     * Не показывать удаленным пользователям
     */
    private Boolean isNoViewRemoteUser;
    /**
     * Интервал разрешенной регистрации
     */
    private Integer registrationInterval;
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
    private String editTime;
    /**
     * Время создания
     */
    private String createTime;
    /**
     * Дата редактирования
     */
    private String editDate;
    /**
     * Дата создания
     */
    private String createDate;
    /**
     * Автогенерация расписания
     */
    private Boolean autoGeneration;
    /**
     * Код федеральный
     */
    private String codef;
    /**
     * Номер в ФСС
     */
    private String socCode;
    /**
     * Возможен забор крови
     */
    private Boolean isIntakeBioMaterial;
    /**
     * По умолчанию снилс врача генерации направлений для 263 приказа
     */
    private String snilsDoctorDirect263;
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
    private Long accessEnterOperation;
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
    private Long kiliProfile;
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

    private Long promedLpuSectionId;

    @Persist
    @Comment("ИД отделения в Промеде")
    public Long getPromedLpuSectionId() {
        return promedLpuSectionId;
    }

    @Comment("Код адреса отделения")
    @Persist
    public String getDepartmentAddressCode() {
        return departmentAddressCode;
    }


    /**
     * Признак мобильной поликлиники
     */
    @Comment("Признак мобильной поликлиники")
    @Persist
    public Boolean getIsMobilePolyclinic() {
        return isMobilePolyclinic;
    }

    /**
     * Стандарт оказания мед. помощи
     */
    @Comment("Стандарт оказания мед. помощи")
    @Persist
    public Long getMedicalStandard() {
        return medicalStandard;
    }

    /**
     * Не входит в оплату по ОМС
     */
    @Comment("Не входит в оплату по ОМС")
    @Persist
    public Boolean getIsNoOmc() {
        return isNoOmc;
    }

    /**
     * Участки
     */
    public String getAreas() {
        return areas;
    }

    @Comment("Название")
    @Persist
    @Required
    @DoUpperCase
    public String getName() {
        return super.getName();
    }

    /**
     * Полное имя
     */
    @Comment("Полное имя")
    @Persist
    public String getFullname() {
        return fullname;
    }

    /**
     * Родитель
     */
    @Persist
    public Long getParent() {
        return parent;
    }

    /**
     * Код ОМС
     */
    @Comment("Код ОМС")
    @Persist
    public String getOmcCode() {
        return omcCode;
    }

    /**
     * Адрес
     */
    @Persist
    @Comment("Адрес")
    public Long getAddress() {
        return address;
    }

    /**
     * Номер дома
     */
    @Comment("Номер дома")
    @Persist
    public String getHouseNumber() {
        return houseNumber;
    }

    /**
     * Корпус
     */
    @Comment("Корпус")
    @Persist
    public String getHouseBuilding() {
        return houseBuilding;
    }

    /**
     * ИНН
     */
    @Comment("ИНН")
    @Persist
    public Long getInn() {
        return inn;
    }

    /**
     * ОГРН
     */
    @Comment("ОГРН")
    @Persist
    public Long getOgrn() {
        return ogrn;
    }

    /**
     * Эл. адрес
     */
    @Comment("Эл. адрес")
    @Persist
    public String getEmail() {
        return email;
    }

    /**
     * Номер лицензии
     */
    @Comment("Номер лицензии")
    @Persist
    public String getLicenseNumber() {
        return licenseNumber;
    }

    /**
     * Срок действия лицензии
     */
    @Comment("Срок действия лицензии")
    @Persist
    @DateString
    @DoDateString
    public String getLicenseExpired() {
        return licenseExpired;
    }

    /**
     * Руководитель
     */
    @Comment("Руководитель")
    @Persist
    public String getDirector() {
        return director;
    }

    /**
     * Телефон
     */
    @Comment("Телефон")
    @Persist
    public String getPhone() {
        return phone;
    }

    /**
     * Функция ЛПУ
     */
    @Comment("Функция ЛПУ")
    @Persist
    @Required
    public Long getLpuFunction() {
        return lpuFunction;
    }

    /**
     * Название для печати
     */
    @Comment("Название для печати")
    @Persist
    public String getPrintName() {
        return printName;
    }

    /**
     * Адрес для печати
     */
    @Comment("Адрес для печати")
    @Persist
    public String getPrintAddress() {
        return printAddress;
    }

    /**
     * Руководитель
     */
    @Comment("Руководитель")
    @Persist
    public Long getManager() {
        return manager;
    }

    /**
     * Приемное отделение
     */
    @Comment("Приемное отделение")
    @Persist
    public Long getPigeonHole() {
        return pigeonHole;
    }

    /**
     * Интервал разрешенной регистрации
     */
    @Comment("Интервал разрешенной регистрации")
    @Persist
    public Integer getRegistrationInterval() {
        return registrationInterval;
    }

    /**
     * Не показывать удаленным пользователям
     */
    @Comment("Не показывать удаленным пользователям")
    @Persist
    public Boolean getIsNoViewRemoteUser() {
        return isNoViewRemoteUser;
    }

    /**
     * Принтер по умолчанию
     */
    @Comment("Принтер по умолчанию")
    @Persist
    public Long getCopyingEquipmentDefault() {
        return copyingEquipmentDefault;
    }

    /**
     * Дата создания
     */
    @Comment("Дата создания")
    @DateString
    @DoDateString
    @Persist
    public String getCreateDate() {
        return createDate;
    }

    /**
     * Дата редактирования
     */
    @Comment("Дата редактирования")
    @DateString
    @DoDateString
    @Persist
    public String getEditDate() {
        return editDate;
    }

    /**
     * Время создания
     */
    @Comment("Время создания")
    @TimeString
    @DoTimeString
    @Persist
    public String getCreateTime() {
        return createTime;
    }

    /**
     * Время редактрования
     */
    @Comment("Время редактрования")
    @TimeString
    @DoTimeString
    @Persist
    public String getEditTime() {
        return editTime;
    }

    /**
     * Пользователь, который создал запись
     */
    @Comment("Пользователь, который создал запись")
    @Persist
    public String getCreateUsername() {
        return createUsername;
    }

    /**
     * Пользователь, который последний редактировал запись
     */
    @Comment("Пользователь, который последний редактировал запись")
    @Persist
    public String getEditUsername() {
        return editUsername;
    }

    /**
     * Автогенерация расписания
     */
    @Comment("Автогенерация расписания")
    @Persist
    public Boolean getAutoGeneration() {
        return autoGeneration;
    }

    /**
     * Код федеральный
     */
    @Comment("Код федеральный")
    @Persist
    public String getCodef() {
        return codef;
    }

    /**
     * Номер в ФСС
     */
    @Persist
    public String getSocCode() {
        return socCode;
    }

    /**
     * Возможен забор крови
     */
    @Persist
    @Comment("Возможен забор крови")
    public Boolean getIsIntakeBioMaterial() {
        return isIntakeBioMaterial;
    }

    /**
     * По умолчанию снилс врача генерации направлений для 263 приказа
     */
    @Comment("По умолчанию снилс врача генерации направлений для 263 приказа")
    public String getSnilsDoctorDirect263() {
        return snilsDoctorDirect263;
    }

    /**
     * Код подразделения
     */
    @Comment("Код подразделения")
    @Persist
    public String getCodeDepartment() {
        return codeDepartment;
    }

    /**
     * Короткое наименование
     */
    @Comment("Короткое наименование")
    @Persist
    public String getShortName() {
        return shortName;
    }

    /**
     * Доступ на создание операций по отделению
     */
    @Comment("Доступ на создание операций по отделению")
    @Persist
    public Long getAccessEnterOperation() {
        return accessEnterOperation;
    }

    /**
     * Префикс для шаблонов ЛН
     */
    @Comment("Префикс для шаблонов ЛН")
    @Persist
    public String getPrefixForLN() {
        return prefixForLN;
    }

    /**
     * Экстренный кабинет
     */
    @Comment("Экстренный кабинет")
    @Persist
    public Long getEmergencyCabinet() {
        return emergencyCabinet;
    }

    /**
     * Отделения для новорожденных
     */
    @Comment("Отделения для новорожденных")
    @Persist
    public Boolean getIsNewBornDep() {
        return isNewBornDep;
    }

    /**
     * Родильное отделение
     */
    @Comment("Родильное отделение")
    @Persist
    public Boolean getIsMaternityWard() {
        return isMaternityWard;
    }

    /**
     * Отделение патологии беременности
     */
    @Comment("Отделение патологии беременности")
    @Persist
    public Boolean getIsPatologyPregnant() {
        return isPatologyPregnant;
    }

    /**
     * Уровень оказания медицинской помощи
     */
    @Comment("Уровень оказания медицинской помощи")
    @Persist
    public Integer getLpuLevel() {
        return lpuLevel;
    }

    /**
     * Профиль КИЛИ
     */
    @Comment("Профиль КИЛИ")
    @Persist
    public Long getKiliProfile() {
        return kiliProfile;
    }

    /**
     * В архиве
     */
    @Comment("В архиве")
    @Persist
    public Boolean getIsArchive() {
        return isArchive;
    }

    /**
     * Обсервационное?
     */
    @Comment("Обсервационное?")
    @Persist
    public Boolean getIsObservable() {
        return isObservable;
    }

    /**
     * Палата новорождённых?
     */
    @Comment("Палата новорождённых?")
    @Persist
    public Boolean getIsNewBorn() {
        return isNewBorn;
    }

    /**
     * Создают ли кадриоскрининг новорождённым?
     */
    @Comment("Создают ли кадриоскрининг новорождённым?")
    @Persist
    public Boolean getIsCreateCardiacScreening() {
        return isCreateCardiacScreening;
    }

    /**
     * Офтальмологическое?
     */
    @Comment("Офтальмологическое?")
    @Persist
    public Boolean getIsOphthalmic() {
        return isOphthalmic;
    }

    /**
     * Учитывать в отчёте по КР?
     */
    @Comment("Учитывать в отчёте по КР?")
    @Persist
    public Boolean getIsReportKMP() {
        return isReportKMP;
    }

    /**
     * Инфекционное?
     */
    @Comment("Инфекционное?")
    @Persist
    public Boolean getIsForCovid() {
        return isForCovid;
    }

    /**
     * Опер. блок?
     */
    @Comment("Опер. блок?")
    @Persist
    public Boolean getIsOperBlock() {
        return isOperBlock;
    }
}
