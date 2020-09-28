package ru.ecom.mis.ejb.form.birth;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.mis.ejb.domain.birth.BirthNosologyCard;
import ru.ecom.mis.ejb.form.medcase.hospital.HospitalMedCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.TimeString;

/**
 * Created by Milamesher on 23.12.2019.
 */
@EntityForm
@EntityFormPersistance(clazz= BirthNosologyCard.class)
@Comment("Карта нозологий в акушерстве")
@Parent(property="medCase", parentForm= HospitalMedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Pregnancy/BirthNosologyCard")
public class BirthNosologyCardForm extends IdEntityForm {
    /** Дата создания */
    @Comment("Дата создания")
    @DateString
    @DoDateString
    @Persist
    public String getCreateDate() {return theCreateDate;}
    public void setCreateDate(String aCreateDate) {theCreateDate = aCreateDate;}
    /** Дата создания */
    private String theCreateDate;

    /** Дата редактирования */
    @Comment("Дата редактирования")
    @DateString @DoDateString @Persist
    public String getEditDate() {return theEditDate;}
    public void setEditDate(String aEditDate) {theEditDate = aEditDate;}
    /** Дата редактирования */
    private String theEditDate;

    /** Время создания */
    @Comment("Время создания")
    @TimeString
    @DoTimeString
    @Persist
    public String getCreateTime() {return theCreateTime;}
    public void setCreateTime(String aCreateTime) {theCreateTime = aCreateTime;}
    /** Время создания */
    private String theCreateTime;
    
    /** Время редактрования */
    @Comment("Время редактирования")
    @TimeString @DoTimeString @Persist
    public String getEditTime() {return theEditTime;}
    public void setEditTime(String aEditTime) {theEditTime = aEditTime;}
    /** Время редактрования */
    private String theEditTime;
    
    /** Пользователь, который создал запись */
    @Comment("Пользователь, который создал запись")
    @Persist
    public String getCreateUsername() {return theCreateUsername;}
    public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
    /** Пользователь, который создал запись */
    private String theCreateUsername;
    
    /** Пользователь, который последний редактировал запись */
    @Comment("Пользователь, который последний редактировал запись")
    @Persist
    public String getEditUsername() {return theEditUsername;}
    public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}
    /** Пользователь, который последний редактировал запись */
    private String theEditUsername;

    /** СМО */
    @Comment("СМО")
    @Persist
    public Long getMedCase() {return theMedCase;}
    public void setMedCase(Long aMedCase) {theMedCase = aMedCase;}
    /** СМО */
    private Long theMedCase;

    /** Пользователь */
    @Comment("Пользователь")
    @Persist
    public Long getCreator() {return theCreator;}
    public void setCreator(Long aCreator) {theCreator = aCreator;}
    /** Пользователь */
    private Long theCreator;

    /** Пользователь, который последний отредактировал */
    @Comment("Пользователь, который последний отредактировал")
    @Persist
    public Long getEditor() {return theEditor;}
    public void setEditor(Long aEditor) {theEditor = aEditor;}
    /** Пользователь, который последний отредактировал */
    private Long theEditor;
}
