package ru.ecom.mis.ejb.form.medcase.hospital;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.medcase.hospital.TemperatureCurve;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

/**
 * Температурная кривая
 *
 * @author oegorova
 */
@EntityForm
@EntityFormPersistance(clazz = TemperatureCurve.class)
@Comment("Температурная кривая")
@WebTrail(comment = "Температурная кривая", nameProperties = "id", view = "entityParentView-stac_temperatureCurve.do")
@Parent(property = "medCase", parentForm = MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Stac/Ssl/TemperatureCurve")
public class TemperatureCurveForm extends IdEntityForm {

    /**
     * Время суток
     */
    @Comment("Время суток")
    @Persist
    @Required
    public Long getDayTime() {
        return theDayTime;
    }

    public void setDayTime(Long aDayTime) {
        theDayTime = aDayTime;
    }

    /**
     * Время суток
     */
    private Long theDayTime;

    /**
     * Температурный градус
     */
    @Comment("Температурный градус")
    @Persist
    @Required
    public String getDegree() {
        return theDegree;
    }

    public void setDegree(String aDegree) {
        theDegree = aDegree;
    }

    /**
     * Температурный градус
     */
    private String theDegree;

    /**
     * Дата измерения температуры
     */
    @Comment("Дата измерения температуры")
    @Persist
    @Required
    @DateString
    @DoDateString
    public String getTakingDate() {
        return theTakingDate;
    }

    public void setTakingDate(String aTakingDate) {
        theTakingDate = aTakingDate;
    }

    /**
     * Дата измерения температуры
     */
    private String theTakingDate;

    /**
     * Случай медицинского обслуживания
     */
    @Comment("Случай медицинского обслуживания")
    @Persist
    public Long getMedCase() {
        return theMedCase;
    }

    public void setMedCase(Long aMedCase) {
        theMedCase = aMedCase;
    }

    /**
     * Случай медицинского обслуживания
     */
    private Long theMedCase;

    /**
     * День пребывания в стационаре
     */
    @Comment("День пребывания в стационаре")
    @Persist
    public String getHospDayNumber() {
        return theHospDayNumber;
    }

    public void setHospDayNumber(String aHospDayNumber) {
        theHospDayNumber = aHospDayNumber;
    }

    /**
     * День пребывания в стационаре
     */
    private String theHospDayNumber;

    /**
     * Время суток (текст)
     */
    @Comment("Время суток (текст)")
    @Persist
    public String getDayTimeText() {
        return theDayTimeText;
    }

    public void setDayTimeText(String aDayTimeText) {
        theDayTimeText = aDayTimeText;
    }

    /**
     * Время суток (текст)
     */
    private String theDayTimeText;


    @Comment("Время создания")
    @Persist
    @TimeString
    @DoTimeString
    public String getTime() {
        return theTime;
    }

    public void setTime(String aTime) {
        theTime = aTime;
    }

    /**
     * Дата редактирования
     */
    @Comment("Дата редактирования")
    @Persist
    @DoDateString
    @DateString
    public String getEditDate() {
        return theEditDate;
    }

    public void setEditDate(String aEditDate) {
        theEditDate = aEditDate;
    }

    /**
     * Дата создания
     */
    @Comment("Дата создания")
    @Persist
    @DoDateString
    @DateString
    public String getDate() {
        return theDate;
    }

    public void setDate(String aDate) {
        theDate = aDate;
    }

    /**
     * Пользователь последний, изменявший запись
     */
    @Comment("Пользователь последний, изменявший запись")
    @Persist
    public String getEditUsername() {
        return theEditUsername;
    }

    public void setEditUsername(String aEditUsername) {
        theEditUsername = aEditUsername;
    }

    /**
     * Пользователь последний, изменявший запись
     */
    private String theEditUsername;
    /**
     * Дата редактирования
     */
    private String theEditDate;
    /**
     * Дата создания
     */
    private String theDate;
    /**
     * Время создания
     */
    private String theTime;

    @Comment("Время редактирования")
    @Persist
    @TimeString
    @DoTimeString
    public String getEditTime() {
        return theEditTime;
    }

    public void setEditTime(String aEditTime) {
        theEditTime = aEditTime;
    }

    /**
     * Время редактирования
     */
    private String theEditTime;

    /**
     * Пользователь
     */
    @Comment("Пользователь")
    @Persist
    public String getUsername() {
        return theUsername;
    }

    public void setUsername(String aUsername) {
        theUsername = aUsername;
    }

    /**
     * Пользователь
     */
    private String theUsername;

}