package ru.ecom.mis.ejb.form.birth;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.mis.ejb.domain.birth.RobsonClass;
import ru.ecom.mis.ejb.form.birth.interceptors.RobsonClassPreCreateInterceptor;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.TimeString;

/**
 * Created by Milamesher on 10.12.2018.
 */
@EntityForm
@EntityFormPersistance(clazz= RobsonClass.class)
@Comment("Классификация Робсона")
@WebTrail(comment = "Классификация Робсона", nameProperties= "id", view="entityParentView-preg_robsonClass.do")
@Parent(property="medCase", parentForm= MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Pregnancy/ChildBirth")
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(RobsonClassPreCreateInterceptor.class)
)
public class RobsonClassForm extends IdEntityForm {
    /** СМО */
    @Comment("СМО")
    @Persist
    public Long getMedCase() {return theMedCase;}
    public void setMedCase(Long aMedCase) {theMedCase = aMedCase;}
    /** Классификация */
    @Comment("Классификация")
    @Persist
    public Long getRobsonType() {return theRobsonType;}
    public void setRobsonType(Long aRobsonType) {theRobsonType = aRobsonType;}
    /** Дата создания */
    @Comment("Дата создания")
    @DateString
    @DoDateString
    @Persist
    public String getCreateDate() {return theCreateDate;}
    public void setCreateDate(String aCreateDate) {theCreateDate = aCreateDate;}
    /** Дата редактирования */
    @Comment("Дата редактирования")
    @DateString
    @DoDateString
    @Persist
    public String getEditDate() {return theEditDate;}
    public void setEditDate(String aEditDate) {theEditDate = aEditDate;}
    /** Время создания */
    @Comment("Время создания")
    @TimeString
    @DoTimeString
    @Persist
    public String getCreateTime() {return theCreateTime;}
    public void setCreateTime(String aCreateTime) {theCreateTime = aCreateTime;}
    /** Время редактрования */
    @Comment("Время редактирования")
    @TimeString @DoTimeString @Persist
    public String getEditTime() {return theEditTime;}
    public void setEditTime(String aEditTime) {theEditTime = aEditTime;}
    /** Пользователь, который создал запись */
    @Comment("Пользователь, который создал запись")
    @Persist
    public String getCreateUsername() {return theCreateUsername;}
    public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
    /** Пользователь, который последний редактировал запись */
    @Comment("Пользователь, который последний редактировал запись")
    @Persist
    public String getEditUsername() {return theEditUsername;}
    public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}
    /** Подгруппа классификации */
    @Comment("Подгруппа классификации")
    @Persist
    public Long getRobsonSub() {return theRobsonSub;}
    public void setRobsonSub(Long aRobsonSub) {theRobsonSub = aRobsonSub;}

    /** СМО */
    private Long theMedCase;
    /** Классификация */
    private Long theRobsonType;
    /** Пользователь, который последний редактировал запись */
    private String theEditUsername;
    /** Пользователь, который создал запись */
    private String theCreateUsername;
    /** Время редактрования */
    private String theEditTime;
    /** Время создания */
    private String theCreateTime;
    /** Дата редактирования */
    private String theEditDate;
    /** Дата создания */
    private String theCreateDate;
    /** Подгруппа классификации */
    private Long theRobsonSub;
}