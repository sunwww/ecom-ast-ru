package ru.ecom.mis.ejb.domain.worker;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.equipment.KkmEquipment;
import ru.ecom.mis.ejb.domain.lpu.CopyingEquipment;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.lpu.WorkPlace;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendar;
import ru.ecom.mis.ejb.domain.worker.voc.VocAcademicDegree;
import ru.ecom.mis.ejb.domain.worker.voc.VocCategory;
import ru.ecom.mis.ejb.domain.worker.voc.VocWorkFunction;
import ru.ecom.queue.domain.voc.VocQueue;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

/**
 * Место работы
 */
@Entity
@Comment("Рабочая функция")
@AIndexes({
        @AIndex(properties = "workFunction")
        , @AIndex(properties = {"code"})
})
@Table(schema = "SQLUser")
@Getter
@Setter
public abstract class WorkFunction extends BaseEntity {

    /**
     * Дата начала работы
     */
    private Date startDate;

    /**
     * Дата окончания работы
     */
    private Date finishDate;

    /**
     * Рабочий календарь
     */
    @Comment("Рабочий календарь")
    @OneToOne
    @Deprecated
    public WorkCalendar getWorkCalendar() {
        return workCalendar;
    }

    /**
     * Рабочий календарь
     */
    private WorkCalendar workCalendar;

    /**
     * Наименование
     */
    @Comment("Наименование")
    @Transient
    public String getName() {
        StringBuilder ret = new StringBuilder();
        if (degrees != null) {
            ret.append(degrees.getCode()).append(" ");
        }
        if (workFunction != null) ret.append(workFunction.getName());
        return ret.toString();
    }


    @Transient
    @Comment("Информация")
    public String getWorkFunctionInfo() {
        return getName();
    }

    @Transient
    @Comment("Информация по коду ОМС врача")
    public String getOmcCodeInfo() {
        String ret = "";
        if (workFunction != null && workFunction.getVocPost() != null
                && workFunction.getVocPost().getOmcDoctorPost() != null) {
            ret = workFunction.getVocPost().getOmcDoctorPost().getCode();
        }
        return ret;
    }

    @Transient
    public MisLpu getLpuRegister() {
        return null;
    }

    /**
     * Функция
     */
    @Comment("Функция")
    @OneToOne
    public VocWorkFunction getWorkFunction() {
        return workFunction;
    }


    /**
     * Функция
     */
    private VocWorkFunction workFunction;

    /**
     * Поместить в архив
     */
    @Comment("Поместить в архив")
    public Boolean getArchival() {
        return archival;
    }

    /**
     * Поместить в архив
     */
    private Boolean archival;

    @Transient
    public String getWorkerLpuInfo() {
        return "";
    }

    @Transient
    public String getInfo() {
        return "";
    }

    @Transient
    public boolean getViewDirect() {
        return true;
    }

    @Transient
    public String getWorkerInfo() {
        return "";
    }

    /**
     * ЛПУ
     */
    @Comment("ЛПУ")
    @ManyToOne
    public MisLpu getLpu() {
        return lpu;
    }

    /**
     * ЛПУ
     */
    private MisLpu lpu;

    /**
     * WorkPlace
     */
    @Comment("WorkPlace")
    @OneToOne
    public WorkPlace getWorkPlace() {
        return workPlace;
    }

    /**
     * WorkPlace
     */
    private WorkPlace workPlace;
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
     * Интервал разрешенной регистрации
     */
    private Integer registrationInterval;

    /**
     * Операционная сестра
     */
    private Boolean isInstrumentNurse;

    /**
     * Комментарий
     */
    private String comment;

    /**
     * Не показывать удаленным пользователям
     */
    private Boolean isNoViewRemoteUser;

    /**
     * Принтер по умолчанию
     */
    @Comment("Принтер по умолчанию")
    @OneToOne
    public CopyingEquipment getCopyingEquipmentDefault() {
        return copyingEquipmentDefault;
    }

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
     * Экстренность
     */
    private Boolean emergency;

    /**
     * Категория специалиста
     */
    @Comment("Категория специалиста")
    @OneToOne
    public VocAcademicDegree getDegrees() {
        return degrees;
    }

    /**
     * Категория специалиста
     */
    private VocAcademicDegree degrees;

    /**
     * Категория специалиста
     */
    @Comment("Категория специалиста")
    @OneToOne
    public VocCategory getCategory() {
        return category;
    }

    /**
     * Категория специалиста
     */
    private VocCategory category;

    /**
     * Импорт
     */
    @Comment("Импорт")
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
    private Boolean isNoDirectSelf;


    /**
     * Ротация
     */
    private Boolean isRotation;

    /**
     * Не синхронизировать с ПАРУСом
     */
    private Boolean isNoP7Sync;

    /**
     * Доверенность
     */
    @Comment("Доверенность")
    @OneToOne
    public Attorney getAttorney() {
        return attorney;
    }

    /**
     * Доверенность
     */
    private Attorney attorney;

    @Comment("Код рабочего места в промеде")
    @Column(name = "promedCode_workstaff")
    public String getPromedCodeWorkstaff() {
        return promedCodeWorkstaff;
    }

    private String promedCodeWorkstaff;


    @Comment("Код отделения в промеде")
    @Column(name = "promedCode_lpusection")
    public String getPromedCodeLpuSection() {
        return promedCodeLpuSection;
    }
    private String promedCodeLpuSection;

    /**
     * Разрешено записывать на дату без указания времени
     */
    private Boolean isDirectionNoTime = false;

    /**
     * Очередь, которую обслуживает раб. функция
     */
    @Comment("Очередь, которую обслуживает раб. функция")
    @OneToOne
    public VocQueue getQueue() {
        return queue;
    }

    /**
     * Очередь, которую обслуживает раб. функция
     */
    private VocQueue queue;

    /**
     * Номер окна в электронной очереди
     */
    private String windowNumber;

    /**
     * ККМ по умолчанию
     */
    @Comment("ККМ по умолчанию")
    @OneToOne
    public KkmEquipment getKkmEquipmentDefault() {
        return kkmEquipmentDefault;
    }

    /**
     * ККМ по умолчанию
     */
    private KkmEquipment kkmEquipmentDefault;

    //Визиты к врачу выгружается в промед
    private Boolean promedExport;
}
