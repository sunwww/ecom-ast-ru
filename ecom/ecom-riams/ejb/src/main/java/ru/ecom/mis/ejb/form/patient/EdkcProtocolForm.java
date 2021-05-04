package ru.ecom.mis.ejb.form.patient;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.*;
import ru.ecom.poly.ejb.form.interceptors.EdkcProtocolCreateInterceptor;
import ru.ecom.poly.ejb.form.interceptors.EdkcProtocolPreCreateInterceptor;
import ru.ecom.poly.ejb.form.interceptors.EdkcProtocolSaveInterceptor;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.MaxDateCurrent;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

import javax.persistence.Id;

/**
 * Created by Milamesher on 01.08.2019.
 */
@Comment("Протокол ЕДКЦ")
@EntityForm
@EntityFormPersistance(clazz = ru.ecom.poly.ejb.domain.protocol.Protocol.class)
@Setter
public class EdkcProtocolForm extends IdEntityForm {
    /** Лист наблюдения */
    @Comment("Лист наблюдения")
    @Persist
    public Long getObsSheet() {return obsSheet;}
    /** Лист наблюдения */
    private Long obsSheet;

    /** Дата регистрации */
    @Persist @Required
    @Comment("Дата регистрации")
    @DateString
    @DoDateString
    @MaxDateCurrent
    public String getDateRegistration() {    return dateRegistration ;}
    /** Дата регистрации */
    private String dateRegistration ;

    /** Дата */
    @Persist @DateString @DoDateString
    @Comment("Дата")
    public String getDate() {    return date ;}
    /** Дата */
    private String date ;

    /** Запись */
    @Persist @Required
    @Comment("Запись")
    public String getRecord() {    return record ;}
    /** Запись */
    private String record ;

    /** Специалист */
    @Comment("Специалист")
    @Persist
    public Long getSpecialist() {return specialist;}
    /** Специалист */
    private Long specialist;

    /** Пользователь */
    @Comment("Пользователь")
    @Persist
    public String getUsername() {return username;}
    /** Пользователь */
    private String username;

    /** Время регистрации */
    @Comment("Время регистрации")
    @Persist @Required
    @TimeString
    @DoTimeString
    public String getTimeRegistration() {return timeRegistration;}
    /** Время регистрации */
    private String timeRegistration;

    /** Время создания */
    @Comment("Время создания")
    @Persist @TimeString @DoTimeString
    public String getTime() {return time;}
    /** Время создания */
    private String time;

    /** Дата редактирования*/
    @Persist @DateString @DoDateString
    @Comment("Дата редактирования")
    public String getEditDate() {    return editDate ;}
    /** Дата редактирования */
    private String  editDate;

    /** Время редактирования */
    @Comment("Время редактирования")
    @Persist
    @TimeString @DoTimeString
    public String getEditTime() {
        return editTime;
    }
    /** Время редактирования */
    private String  editTime;

    /** Пользователь последний, изменявший запись */
    @Comment("Пользователь последний, изменявший запись")
    @Persist
    public String getEditUsername() {
        return editUsername;
    }

    /** Пользователь последний, изменявший запись */
    private String editUsername;

    /** Шаблон, на основе которого создано заключение */
    @Comment("Шаблон, на основе которого создано заключение")
    @Persist
    public Long getTemplateProtocol() {return templateProtocol;}
    /** Шаблон, на основе которого создано заключение */
    private Long templateProtocol;

    /** Параметры шаблона */
    @Comment("Параметры шаблона")
    public String getParams() {return params;}
    /** Параметры шаблона */
    private String params;

    /** Тип протокола */
    @Comment("Тип протокола")
    @Persist
    public Long getType() {return type;}
    /** Тип протокола */
    private Long type;

    /** ИД */
    @Id
    @Comment("ИД")
    public long getId() {    return id ;}
    private long id ;

    /** Дата печати */
    @Comment("Дата печати")
    @Persist @DateString @DoDateString
    public String getPrintDate() {return printDate;}

    /** Время печати */
    @Comment("Время печати")
    @Persist @DoTimeString @TimeString
    public String getPrintTime() {return printTime;}

    /** Время печати */
    private String printTime;
    /** Дата печати */
    private String printDate;
}
