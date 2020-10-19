package ru.ecom.mis.ejb.form.patient;

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
public class ObservationSheetForm extends IdEntityForm {
    /** Дата установки */
    @Comment("Дата установки")
    @DateString
    @DoDateString
    @Persist
    public String getStartDate() {return theStartDate;}
    public void setStartDate(String aStartDate) {theStartDate = aStartDate;}
    /** Дата установки */
    private String theStartDate ;

    /** Дата снятия */
    @Comment("Дата снятия")
    @DateString
    @DoDateString
    @Persist
    public String getFinishDate() {return theFinishDate;}
    public void setFinishDate(String aFinishDate) {theFinishDate = aFinishDate;}
    /** Дата снятия */
    private String theFinishDate ;

    /** Пользователь, который последний редактировал запись */
    @Comment("Пользователь, который последний редактировал запись")
    @Persist
    public String getEditUsername() {return theEditUsername;}
    public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}
    /** Пользователь, который последний редактировал запись */
    private String theEditUsername;

    /** Пользователь, который создал запись */
    @Comment("Пользователь, который создал запись")
    @Persist
    public String getCreateUsername() {return theCreateUsername;}
    public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
    /** Пользователь, который создал запись */
    private String theCreateUsername;

    /** Специалист, открывший ЛН */
    @Comment("Специалист, открывший ЛН")
    @Persist
    public Long getSpecialistStart() {return theSpecialistStart;}
    public void setSpecialistStart(Long aSpecialistStart) {theSpecialistStart = aSpecialistStart;}
    /** Специалист, открывший ЛН */
    private Long theSpecialistStart;

    /** Специалист, закрывший ЛН */
    @Comment("Специалист, закрывший ЛН")
    @Persist
    public Long getSpecialistFin() {return theSpecialistFin;}
    public void setSpecialistFin(Long aSpecialistFin) {theSpecialistFin = aSpecialistFin;}
    /** Специалист, закрывший ЛН */
    private Long theSpecialistFin;

    /** Результат наблюдения */
    @Comment("Результат наблюдения")
    @Persist
    public Long getObservResult() {return theObservResult;}
    public void setObservResult(Long aObservResult) {theObservResult = aObservResult;}
    /** Результат наблюдения */
    private Long theObservResult;

    /** Пациент */
    @Comment("Пациент")
    @Persist
    public Long getPatient() {return thePatient;}
    public void setPatient(Long aNewProperty) {thePatient = aNewProperty;}
    /**Пациент */
    private Long thePatient;
}
