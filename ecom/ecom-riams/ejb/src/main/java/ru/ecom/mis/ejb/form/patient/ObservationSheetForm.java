package ru.ecom.mis.ejb.form.patient;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.patient.ObservationSheet;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

/**
 * Created by Milamesher on 01.08.2019.
 */
@EntityForm
@EntityFormPersistance(clazz = ObservationSheet.class)
@Comment("Абстрактный лист наблюдения ЕДКЦ")
@Parent(property="patient", parentForm=PatientForm.class)
@Setter
public class ObservationSheetForm extends IdEntityForm {
    /** Дата установки */
    @Comment("Дата установки")
    @DateString
    @DoDateString
    @Persist
    public String getStartDate() {return startDate;}
    /** Дата установки */
    private String startDate ;

    /** Дата снятия */
    @Comment("Дата снятия")
    @DateString
    @DoDateString
    @Persist
    public String getFinishDate() {return finishDate;}
    /** Дата снятия */
    private String finishDate ;

    /** Пользователь, который последний редактировал запись */
    @Comment("Пользователь, который последний редактировал запись")
    @Persist
    public String getEditUsername() {return editUsername;}
    /** Пользователь, который последний редактировал запись */
    private String editUsername;

    /** Пользователь, который создал запись */
    @Comment("Пользователь, который создал запись")
    @Persist
    public String getCreateUsername() {return createUsername;}
    /** Пользователь, который создал запись */
    private String createUsername;

    /** Специалист, открывший ЛН */
    @Comment("Специалист, открывший ЛН")
    @Persist
    public Long getSpecialistStart() {return specialistStart;}
    /** Специалист, открывший ЛН */
    private Long specialistStart;

    /** Специалист, закрывший ЛН */
    @Comment("Специалист, закрывший ЛН")
    @Persist
    public Long getSpecialistFin() {return specialistFin;}
    /** Специалист, закрывший ЛН */
    private Long specialistFin;

    /** Результат наблюдения */
    @Comment("Результат наблюдения")
    @Persist
    public Long getObservResult() {return observResult;}
    /** Результат наблюдения */
    private Long observResult;

    /** Пациент */
    @Comment("Пациент")
    @Persist
    public Long getPatient() {return patient;}
    /**Пациент */
    private Long patient;
}
