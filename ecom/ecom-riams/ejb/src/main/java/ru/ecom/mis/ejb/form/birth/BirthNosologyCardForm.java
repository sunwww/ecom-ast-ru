package ru.ecom.mis.ejb.form.birth;

import lombok.Setter;
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
@Setter
public class BirthNosologyCardForm extends IdEntityForm {
    /** Дата создания */
    @Comment("Дата создания")
    @DateString
    @DoDateString
    @Persist
    public String getCreateDate() {return createDate;}
    /** Дата создания */
    private String createDate;

    /** Дата редактирования */
    @Comment("Дата редактирования")
    @DateString @DoDateString @Persist
    public String getEditDate() {return editDate;}
    /** Дата редактирования */
    private String editDate;

    /** Время создания */
    @Comment("Время создания")
    @TimeString
    @DoTimeString
    @Persist
    public String getCreateTime() {return createTime;}
    /** Время создания */
    private String createTime;
    
    /** Время редактрования */
    @Comment("Время редактирования")
    @TimeString @DoTimeString @Persist
    public String getEditTime() {return editTime;}
    /** Время редактрования */
    private String editTime;
    
    /** Пользователь, который создал запись */
    @Comment("Пользователь, который создал запись")
    @Persist
    public String getCreateUsername() {return createUsername;}
    /** Пользователь, который создал запись */
    private String createUsername;
    
    /** Пользователь, который последний редактировал запись */
    @Comment("Пользователь, который последний редактировал запись")
    @Persist
    public String getEditUsername() {return editUsername;}
    /** Пользователь, который последний редактировал запись */
    private String editUsername;

    /** СМО */
    @Comment("СМО")
    @Persist
    public Long getMedCase() {return medCase;}
    /** СМО */
    private Long medCase;

    /** Пользователь */
    @Comment("Пользователь")
    @Persist
    public Long getCreator() {return creator;}
    /** Пользователь */
    private Long creator;

    /** Пользователь, который последний отредактировал */
    @Comment("Пользователь, который последний отредактировал")
    @Persist
    public Long getEditor() {return editor;}
    /** Пользователь, который последний отредактировал */
    private Long editor;
}
