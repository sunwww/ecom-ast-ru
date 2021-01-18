package ru.ecom.mis.ejb.form.medcase;

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
public class PregProtocolForm extends ProtocolForm {
    /** Шаблон, на основе которого создано заключение */
    @Comment("Шаблон, на основе которого создано заключение")
    @Persist
    public Long getTemplateProtocol() {return theTemplateProtocol;}
    public void setTemplateProtocol(Long aTemplateProtocol) {theTemplateProtocol = aTemplateProtocol;}
    /** Шаблон, на основе которого создано заключение */
    private Long theTemplateProtocol;

    /** Параметры шаблона */
    @Comment("Параметры шаблона")
    public String getParams() {return theParams;}
    public void setParams(String aParams) {theParams = aParams;}
    /** Параметры шаблона */
    private String theParams;

    /** Визит */
    @Comment("Визит")
    @Persist
    public Long getMedCase() {return theMedCase;}
    public void setMedCase(Long aMedCase) {theMedCase = aMedCase;}
    /** Визит */
    private Long theMedCase;

    /** Тип протокола */
    @Comment("Тип протокола")
    @Persist @Required
    public Long getType() {return theType;}
    public void setType(Long aType) {theType = aType;}
    /** Тип протокола */
    private Long theType;

    /** Время регистрации */
    @Comment("Время регистрации")
    @Persist @Required
    @TimeString
    @DoTimeString
    public String getTimeRegistration() {return theTimeRegistration;}
    public void setTimeRegistration(String aTimeRegistration) {theTimeRegistration = aTimeRegistration;}
    /** Время регистрации */
    private String theTimeRegistration;

    /** Дата печати */
    @Comment("Дата печати")
    @Persist @DateString
    @DoDateString
    public String getPrintDate() {return thePrintDate;}
    public void setPrintDate(String aPrintDate) {thePrintDate = aPrintDate;}
    /** Дата печати */
    private String thePrintDate;

    /** Время печати */
    @Comment("Время печати")
    @Persist @DoTimeString @TimeString
    public String getPrintTime() {return thePrintTime;}
    public void setPrintTime(String aPrintTime) {thePrintTime = aPrintTime;}
    /** Время печати */
    private String thePrintTime;

    /** Дата редактирования */
    @Comment("Дата редактирования")
    @Persist @DoDateString @DateString
    public String getEditDate() {return theEditDate;}
    public void setEditDate(String aEditDate) {theEditDate = aEditDate;}
    /** Дата редактирования */
    private String theEditDate;

    /** Пользователь последний, изменявший запись */
    @Comment("Пользователь последний, изменявший запись")
    @Persist
    public String getEditUsername() {
        return theEditUsername;
    }
    public void setEditUsername(String aEditUsername) {
        theEditUsername = aEditUsername;
    }
    /** Пользователь последний, изменявший запись */
    private String theEditUsername;

    /** Поток обслуживания случая */
    @Comment("Поток обслуживания случая")
    public Long getServiceStream() {return theServiceStream;}
    public void setServiceStream(Long aServiceStream) {theServiceStream = aServiceStream;}
    /** Поток обслуживания случая */
    private Long theServiceStream ;
}
