package ru.ecom.mis.ejb.form.birth;

import lombok.Setter;
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
@Setter
public class RobsonClassForm extends IdEntityForm {
    /** СМО */
    @Comment("СМО")
    @Persist
    public Long getMedCase() {return medCase;}
    /** Классификация */
    @Comment("Классификация")
    @Persist
    public Long getRobsonType() {return robsonType;}
    /** Дата создания */
    @Comment("Дата создания")
    @DateString
    @DoDateString
    @Persist
    public String getCreateDate() {return createDate;}
    /** Дата редактирования */
    @Comment("Дата редактирования")
    @DateString
    @DoDateString
    @Persist
    public String getEditDate() {return editDate;}
    /** Время создания */
    @Comment("Время создания")
    @TimeString
    @DoTimeString
    @Persist
    public String getCreateTime() {return createTime;}
    /** Время редактрования */
    @Comment("Время редактирования")
    @TimeString @DoTimeString @Persist
    public String getEditTime() {return editTime;}
    /** Пользователь, который создал запись */
    @Comment("Пользователь, который создал запись")
    @Persist
    public String getCreateUsername() {return createUsername;}
    /** Пользователь, который последний редактировал запись */
    @Comment("Пользователь, который последний редактировал запись")
    @Persist
    public String getEditUsername() {return editUsername;}
    /** Подгруппа классификации */
    @Comment("Подгруппа классификации")
    @Persist
    public Long getRobsonSub() {return robsonSub;}

    /** СМО */
    private Long medCase;
    /** Классификация */
    private Long robsonType;
    /** Пользователь, который последний редактировал запись */
    private String editUsername;
    /** Пользователь, который создал запись */
    private String createUsername;
    /** Время редактрования */
    private String editTime;
    /** Время создания */
    private String createTime;
    /** Дата редактирования */
    private String editDate;
    /** Дата создания */
    private String createDate;
    /** Подгруппа классификации */
    private Long robsonSub;
}