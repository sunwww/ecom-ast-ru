package ru.ecom.mis.ejb.form.birth;

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
public class MisbirthForm extends IdEntityForm {
    /** СМО */
    @Comment("СМО")
    @Persist
    @Required
    public Long getMedCase() {return theMedCase;}
    public void setMedCase(Long aMedCase) {theMedCase = aMedCase;}
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
    /** Срок беременности (нед) */
    @Comment("Срок беременности (нед)")
    @Persist @Required
    public String getDurationPregnancy() {return theDurationPregnancy;}
    public void setDurationPregnancy(String aDurationPregnancy) {theDurationPregnancy = aDurationPregnancy;}
    /** Тип выкидыша */
    @Comment("Тип выкидыша")
    @Persist @Required
    public Long getTypeMisbirth() {return theTypeMisbirth;}
    public void setTypeMisbirth(Long aTypeMisbirth) {theTypeMisbirth = aTypeMisbirth;}
    /** Дата выкидыша */
    @Comment("Дата выкидыша")
    @DateString
    @DoDateString
    @Persist @Required
    public String getMisbirthDate() {return theMisbirthDate;}
    public void setMisbirthDate(String aMisbirthDate) {theMisbirthDate = aMisbirthDate;}
    /** Тип выкидыша */
    @Comment("Тип выкидыша")
    @Persist @Required
    public Long getNumFetus() {return theNumFetus;}
    public void setNumFetus(Long aNumFetus) {theNumFetus = aNumFetus;}
    /** ЭКО? */
    @Comment("ЭКО?")
    @Persist
    public Boolean getIsECO() {return theIsECO;}
    public void setIsECO(Boolean aIsECO) {theIsECO = aIsECO;}
    /** Состояла на учёте в ЖК? */
    @Comment("Состояла на учёте в ЖК?")
    @Persist
    public Boolean getIsRegisteredWithWomenConsultation() {return theIsRegisteredWithWomenConsultation;}
    public void setIsRegisteredWithWomenConsultation(Boolean aIsRegisteredWithWomenConsultation) {theIsRegisteredWithWomenConsultation = aIsRegisteredWithWomenConsultation;}

    /** СМО */
    private Long theMedCase;
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
    /** Срок беременности (нед) */
    private String theDurationPregnancy;
    /** Тип выкидыша */
    private Long theTypeMisbirth;
    /** Дата выкидыша */
    private String theMisbirthDate;
    /** Кол-во плодов */
    private Long theNumFetus;
    /** ЭКО? */
    private Boolean theIsECO;
    /** Состояла на учёте в ЖК? */
    private Boolean theIsRegisteredWithWomenConsultation;
}