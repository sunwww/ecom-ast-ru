package ru.ecom.mis.ejb.form.worker;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.Subclasses;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.mis.ejb.form.lpu.MisLpuForm;
import ru.ecom.mis.ejb.form.lpu.OperatingRoomForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

/**
 * Рабочая функция
 */
@EntityForm
@EntityFormPersistance(clazz = WorkFunction.class)
@Comment("Рабочая функция")
@WebTrail(comment = "Рабочая функция", nameProperties = "info", view = "entitySubclassView-work_workFunction.do")
@Parent(property = "lpuRegister", parentForm = MisLpuForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Worker/WorkFunction")
@Subclasses({PersonalWorkFunctionForm.class, GroupWorkFunctionForm.class, OperatingRoomForm.class})
@Setter
public class WorkFunctionForm extends IdEntityForm {

    /**
     * Дата начала работы
     */
    @Comment("Дата начала работы")
    @Persist
    @DateString
    @DoDateString
    public String getStartDate() {
        return startDate;
    }

    /**
     * Дата начала работы
     */
    private String startDate;

    /**
     * Дата окончания работы
     */
    @Comment("Дата окончания работы")
    @Persist
    @DateString
    @DoDateString
    public String getFinishDate() {
        return finishDate;
    }

    /**
     * Дата окончания работы
     */
    private String finishDate;

    /**
     * Наименование
     */
    @Comment("Наименование")
    @Persist
    public String getName() {
        return name;
    }


    /**
     * Информация о рабочей фукции
     */
    @Comment("Информация о рабочей фукции")
    @Persist
    public String getWorkFunctionInfo() {
        return workFunctionInfo;
    }

    /**
     * Функция
     */
    @Comment("Функция")
    @Persist
    @Required
    public Long getWorkFunction() {
        return workFunction;
    }

    /**
     * ЛПУ
     */
    @Comment("ЛПУ")
    @Persist
    public Long getLpuRegister() {
        return lpuRegister;
    }

    /**
     * Информация
     */
    @Comment("Информация")
    @Persist
    public String getInfo() {
        return info;
    }

    /**
     * Поместить в архив?
     */
    @Comment("Поместить в архив?")
    @Persist
    public Boolean getArchival() {
        return archival;
    }


    /**
     * Код специалиста
     */
    @Comment("Код специалиста")
    @Persist
    public String getCode() {
        return code;
    }

    /**
     * Хирургическая специальность
     */
    @Comment("Хирургическая специальность")
    @Persist
    public Boolean getIsSurgical() {
        return isSurgical;
    }

    /**
     * Администратор
     */
    @Comment("Администратор")
    @Persist
    public Boolean getIsAdministrator() {
        return isAdministrator;
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
     * Интервал разрешенной регистрации
     */
    private Integer registrationInterval;
    /**
     * Администратор
     */
    private Boolean isAdministrator;
    /**
     * Хирургическая специальность
     */
    private Boolean isSurgical;
    /**
     * Код специалиста
     */
    private String code;
    /**
     * Поместить в архив?
     */
    private Boolean archival;
    /**
     * Информация
     */
    private String info;
    /**
     * Наименование
     */
    private String name;
    /**
     * Информация о рабочей фукции
     */
    private String workFunctionInfo;
    /**
     * Функция
     */
    private Long workFunction;
    /**
     * ЛПУ
     */
    private Long lpuRegister;

    /**
     * Операционная сестра
     */
    @Comment("Операционная сестра")
    @Persist
    public Boolean getIsInstrumentNurse() {
        return isInstrumentNurse;
    }

    /**
     * Операционная сестра
     */
    private Boolean isInstrumentNurse;

    /**
     * Комментарий
     */
    @Comment("Комментарий")
    @Persist
    public String getComment() {
        return comment;
    }

    /**
     * Комментарий
     */
    private String comment;

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
     * Принтер по умолчанию
     */
    private Long copyingEquipmentDefault;
    /**
     * Не показывать удаленным пользователям
     */
    private Boolean isNoViewRemoteUser;

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
     * Экстренность
     */
    @Comment("Экстренность")
    @Persist
    public Boolean getEmergency() {
        return emergency;
    }

    /**
     * Экстренность
     */
    private Boolean emergency;

    /**
     * Категория специалиста
     */
    @Comment("Категория специалиста")
    @Persist
    public Long getCategory() {
        return category;
    }

    /**
     * Категория специалиста
     */
    private Long category;

    /**
     * Импорт
     */
    @Comment("Импорт")
    @Persist
    public Boolean getIsImport() {
        return isImport;
    }

    /**
     * Импорт
     */
    private Boolean isImport;

    /**
     * Запрет на направление к себе
     */
    @Comment("Запрет на направление к себе")
    @Persist
    public Boolean getIsNoDirectSelf() {
        return isNoDirectSelf;
    }

    /**
     * Запрет на направление к себе
     */
    private Boolean isNoDirectSelf;

    /**
     * Ратация
     */
    @Comment("Ратация")
    public Boolean getIsRotation() {
        return isRotation;
    }

    /**
     * Ратация
     */
    private Boolean isRotation;

    /**
     * Не синхронизировать с ПАРУСом
     */
    @Comment("Не синхронизировать с ПАРУСом")
    @Persist
    public Boolean getIsNoP7Sync() {
        return isNoP7Sync;
    }

    /**
     * Не синхронизировать с ПАРУСом
     */
    private Boolean isNoP7Sync;

    /**
     * Доверенность
     */
    @Comment("Доверенность")
    @Persist
    public Long getAttorney() {
        return attorney;
    }

    /**
     * Доверенность
     */
    private Long attorney;

    /**
     * Разрешено записывать на дату без указания времени
     */
    @Comment("Разрешено записывать на дату без указания времени")
    @Persist
    public Boolean getIsDirectionNoTime() {
        return isDirectionNoTime;
    }

    /**
     * Разрешено записывать на дату без указания времени
     */
    private Boolean isDirectionNoTime;

    /**
     * ККМ по умолчанию
     */
    @Comment("ККМ по умолчанию")
    @Persist
    public Long getKkmEquipmentDefault() {
        return kkmEquipmentDefault;
    }

    /**
     * ККМ по умолчанию
     */
    private Long kkmEquipmentDefault;

    /**
     * Кабинет
     */
    @Comment("Кабинет")
    public String getCabinet() {
        return cabinet;
    }

    /**
     * Кабинет
     */
    private String cabinet;

    private Boolean promedExport;

    @Persist
    @Comment("Визиты к врачу выгружается в промед")
    public Boolean getPromedExport() {
        return promedExport;
    }
}

