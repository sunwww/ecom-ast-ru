package ru.ecom.mis.ejb.form.medcase;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.mis.ejb.domain.medcase.CovidMark;
import ru.ecom.mis.ejb.form.medcase.hospital.HospitalMedCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.TimeString;

/**
 *
 */
@EntityForm
@EntityFormPersistance(clazz = CovidMark.class)
@Comment("Форма оценки тяжести COVID-19")
@WebTrail(comment = "Форма оценки тяжести COVID-19"
        , nameProperties = "id", view = "entityParentView-smo_covidMark.do")
@EntityFormSecurityPrefix("/Policy/Mis/MedCase")
@Parent(parentForm = HospitalMedCaseForm.class,  property = "medCase")
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(CovidMarkPreCreateInterceptor.class)
)
public class CovidMarkForm  extends IdEntityForm {
    /** СМО */
    @Comment("СМО")
    @Persist
    public Long getMedCase() {return theMedCase;}
    public void setMedCase(Long aMedCase) {theMedCase = aMedCase;}
    private Long theMedCase ;

    /** Дата создания */
    @Comment("Дата создания")
    @Persist
    @DateString
    @DoDateString
    public String getCreateDate() {return theCreateDate;}
    public void setCreateDate(String aCreateDate) {theCreateDate = aCreateDate;}
    private String theCreateDate ;

    /** Время создания */
    @Comment("Время создания")
    @Persist
    @TimeString
    @DoTimeString
    public String getCreateTime() {return theCreateTime;}
    public void setCreateTime(String aCreateTime) {theCreateTime = aCreateTime;}
    private String theCreateTime ;

    /** Создатель */
    @Comment("Создатель")
    @Persist
    public String getCreateUsername() {return theCreateUsername;}
    public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
    private String theCreateUsername ;

    /** Дата редактирования */
    @Comment("Дата редактирования")
    @Persist @DoDateString @DateString
    public String getEditDate() {return theEditDate;}
    public void setEditDate(String aEditDate) {theEditDate = aEditDate;}
    /** Дата редактирования */
    private String theEditDate;

    /** Время редактирования */
    @Comment("Время редактирования")
    @Persist @DoTimeString @TimeString
    public String getEditTime() {return theEditTime;}
    public void setEditTime(String aEditTime) {theEditTime = aEditTime;}
    /** Время редактирования */
    private String theEditTime;
    
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

    /** Изменение в лёгких в оценке ковида */
    @Comment("Изменение в лёгких в оценке ковида")
    @Persist
    public Long getChangeLungs() {return theChangeLungs;}
    public void setChangeLungs(Long aChangeLungs) {theChangeLungs = aChangeLungs;}
    private Long theChangeLungs ;

    /** Частота дыхательных движений в оценке ковида */
    @Comment("Частота дыхательных движений в оценке ковида")
    @Persist
    public Long getChdd() {return theChdd;}
    public void setChdd(Long aChdd) {theChdd = aChdd;}
    private Long theChdd ;

    /** Пульсоксиметрия в оценке ковида */
    @Comment("Пульсоксиметрия в оценке ковида")
    @Persist
    public Long getPuls() {return thePuls;}
    public void setPuls(Long aPuls) {thePuls = aPuls;}
    private Long thePuls ;

    /** Температура тела в оценке ковида */
    @Comment("Температура тела в оценке ковида")
    @Persist
    public Long getTemp() {return theTemp;}
    public void setTemp(Long aTemp) {theTemp = aTemp;}
    private Long theTemp ;

    /** Тяжесть заболевания в оценке ковида */
    @Comment("Тяжесть заболевания в оценке ковида")
    @Persist
    public Long getSost() {return theSost;}
    public void setSost(Long aSost) {theSost = aSost;}
    private Long theSost ;

    /** Нарушение сознания (3) */
    @Comment("Нарушение сознания (3)")
    @Persist
    public Boolean getIsBadSozn() {return theIsBadSozn;}
    public void setIsBadSozn(Boolean aIsBadSozn) {theIsBadSozn = aIsBadSozn;}
    private Boolean theIsBadSozn ;

    /** Строка с признаками тяжёлого состояния */
    public String getBadSostString() {
        return badSostString;
    }
    public void setBadSostString(String badSostString) {
        this.badSostString = badSostString;
    }
    private String badSostString ;
}
