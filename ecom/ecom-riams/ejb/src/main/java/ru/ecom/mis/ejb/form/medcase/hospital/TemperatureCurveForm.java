package ru.ecom.mis.ejb.form.medcase.hospital;

import lombok.Setter;
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
@Setter
public class TemperatureCurveForm extends IdEntityForm {

    /**
     * Время суток
     */
    @Comment("Время суток")
    @Persist
    @Required
    public Long getDayTime() {
        return dayTime;
    }

    /**
     * Время суток
     */
    private Long dayTime;

    /**
     * Температурный градус
     */
    @Comment("Температурный градус")
    @Persist
    @Required
    public String getDegree() {
        return degree;
    }

    /**
     * Температурный градус
     */
    private String degree;

    /**
     * Дата измерения температуры
     */
    @Comment("Дата измерения температуры")
    @Persist
    @Required
    @DateString
    @DoDateString
    public String getTakingDate() {
        return takingDate;
    }

    /**
     * Дата измерения температуры
     */
    private String takingDate;

    /**
     * Случай медицинского обслуживания
     */
    @Comment("Случай медицинского обслуживания")
    @Persist
    public Long getMedCase() {
        return medCase;
    }

    /**
     * Случай медицинского обслуживания
     */
    private Long medCase;

    /**
     * День пребывания в стационаре
     */
    @Comment("День пребывания в стационаре")
    @Persist
    public String getHospDayNumber() {
        return hospDayNumber;
    }

    /**
     * День пребывания в стационаре
     */
    private String hospDayNumber;

    /**
     * День болезни
     */
    @Comment("День болезни")
    @Persist
    public String getIllnessDayNumber() {
        return illnessDayNumber;
    }

    /**
     * День болезни
     */
    private String illnessDayNumber;


    /**
     * Время суток (текст)
     */
    @Comment("Время суток (текст)")
    @Persist
    public String getDayTimeText() {
        return dayTimeText;
    }

    /**
     * Время суток (текст)
     */
    private String dayTimeText;


    @Comment("Время создания")
    @Persist
    @TimeString
    @DoTimeString
    public String getTime() {
        return time;
    }

    /**
     * Дата редактирования
     */
    @Comment("Дата редактирования")
    @Persist
    @DoDateString
    @DateString
    public String getEditDate() {
        return editDate;
    }

    /**
     * Дата создания
     */
    @Comment("Дата создания")
    @Persist
    @DoDateString
    @DateString
    public String getDate() {
        return date;
    }

    /**
     * Пользователь последний, изменявший запись
     */
    @Comment("Пользователь последний, изменявший запись")
    @Persist
    public String getEditUsername() {
        return editUsername;
    }

    /**
     * Пользователь последний, изменявший запись
     */
    private String editUsername;
    /**
     * Дата редактирования
     */
    private String editDate;
    /**
     * Дата создания
     */
    private String date;
    /**
     * Время создания
     */
    private String time;

    @Comment("Время редактирования")
    @Persist
    @TimeString
    @DoTimeString
    public String getEditTime() {
        return editTime;
    }

    /**
     * Время редактирования
     */
    private String editTime;

    /**
     * Пользователь
     */
    @Comment("Пользователь")
    @Persist
    public String getUsername() {
        return username;
    }

    /**
     * Пользователь
     */
    private String username;

}