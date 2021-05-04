package ru.ecom.mis.ejb.form.medcase;

import lombok.Setter;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.poly.ejb.form.interceptors.ProtocolPregPreCreateInterceptor;
import ru.ecom.poly.ejb.form.protocol.ProtocolForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

@Comment("Протокол для роддома")
@EntityForm
@EntityFormPersistance(clazz = ru.ecom.poly.ejb.domain.protocol.Protocol.class)
@WebTrail(comment = "Протокол для роддома", nameProperties = "info"
        , view = "entityParentView-pregProtocol.do"
        ,list = "entityParentList-pregProtocol.do"
)
@Parent(property = "medCase", parentForm = MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Protocol")
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(ProtocolPregPreCreateInterceptor.class)
)
@Setter
public class PregProtocolForm extends ProtocolForm {
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

    /** Визит */
    @Comment("Визит")
    @Persist
    public Long getMedCase() {return medCase;}
    /** Визит */
    private Long medCase;

    /** Тип протокола */
    @Comment("Тип протокола")
    @Persist @Required
    public Long getType() {return type;}
    /** Тип протокола */
    private Long type;

    /** Время регистрации */
    @Comment("Время регистрации")
    @Persist @Required
    @TimeString
    @DoTimeString
    public String getTimeRegistration() {return timeRegistration;}
    /** Время регистрации */
    private String timeRegistration;

    /** Дата печати */
    @Comment("Дата печати")
    @Persist @DateString
    @DoDateString
    public String getPrintDate() {return printDate;}
    /** Дата печати */
    private String printDate;

    /** Время печати */
    @Comment("Время печати")
    @Persist @DoTimeString @TimeString
    public String getPrintTime() {return printTime;}
    /** Время печати */
    private String printTime;

    /** Дата редактирования */
    @Comment("Дата редактирования")
    @Persist @DoDateString @DateString
    public String getEditDate() {return editDate;}
    /** Дата редактирования */
    private String editDate;

    /** Пользователь последний, изменявший запись */
    @Comment("Пользователь последний, изменявший запись")
    @Persist
    public String getEditUsername() {
        return editUsername;
    }
    /** Пользователь последний, изменявший запись */
    private String editUsername;

    /** Поток обслуживания случая */
    @Comment("Поток обслуживания случая")
    public Long getServiceStream() {return serviceStream;}
    /** Поток обслуживания случая */
    private Long serviceStream ;
}
