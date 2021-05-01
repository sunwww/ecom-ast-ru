package ru.ecom.mis.ejb.form.birth;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.mis.ejb.domain.birth.Misbirth;
import ru.ecom.mis.ejb.form.birth.interceptors.MisbirthPreCreateInterceptor;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

/**
 * Created by Milamesher on 21.12.2018.
 */
@EntityForm
@EntityFormPersistance(clazz= Misbirth.class)
@Comment("Выкидыш")
@WebTrail(comment = "Выкидыш", nameProperties= "id", view="entityParentView-preg_misbirth.do")
@Parent(property="medCase", parentForm= MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Pregnancy/ChildBirth")
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(MisbirthPreCreateInterceptor.class)
)
@Setter
public class MisbirthForm extends IdEntityForm {
    /** СМО */
    @Comment("СМО")
    @Persist
    @Required
    public Long getMedCase() {return medCase;}
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
    /** Срок беременности (нед) */
    @Comment("Срок беременности (нед)")
    @Persist @Required
    public String getDurationPregnancy() {return durationPregnancy;}
    /** Тип выкидыша */
    @Comment("Тип выкидыша")
    @Persist @Required
    public Long getTypeMisbirth() {return typeMisbirth;}
    /** Дата выкидыша */
    @Comment("Дата выкидыша")
    @DateString
    @DoDateString
    @Persist @Required
    public String getMisbirthDate() {return misbirthDate;}
    /** Тип выкидыша */
    @Comment("Тип выкидыша")
    @Persist @Required
    public Long getNumFetus() {return numFetus;}
    /** ЭКО? */
    @Comment("ЭКО?")
    @Persist
    public Boolean getIsECO() {return isECO;}
    /** Состояла на учёте в ЖК? */
    @Comment("Состояла на учёте в ЖК?")
    @Persist
    public Boolean getIsRegisteredWithWomenConsultation() {return isRegisteredWithWomenConsultation;}

    /** СМО */
    private Long medCase;
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
    /** Срок беременности (нед) */
    private String durationPregnancy;
    /** Тип выкидыша */
    private Long typeMisbirth;
    /** Дата выкидыша */
    private String misbirthDate;
    /** Кол-во плодов */
    private Long numFetus;
    /** ЭКО? */
    private Boolean isECO;
    /** Состояла на учёте в ЖК? */
    private Boolean isRegisteredWithWomenConsultation;
}