package ru.ecom.mis.ejb.form.medcase;

import lombok.Setter;
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
@Setter
public class CovidMarkForm  extends IdEntityForm {
    /** СМО */
    @Comment("СМО")
    @Persist
    public Long getMedCase() {return medCase;}
    private Long medCase ;

    /** Дата создания */
    @Comment("Дата создания")
    @Persist
    @DateString
    @DoDateString
    public String getCreateDate() {return createDate;}
    private String createDate ;

    /** Время создания */
    @Comment("Время создания")
    @Persist
    @TimeString
    @DoTimeString
    public String getCreateTime() {return createTime;}
    private String createTime ;

    /** Создатель */
    @Comment("Создатель")
    @Persist
    public String getCreateUsername() {return createUsername;}
    private String createUsername ;

    /** Дата редактирования */
    @Comment("Дата редактирования")
    @Persist @DoDateString @DateString
    public String getEditDate() {return editDate;}
    /** Дата редактирования */
    private String editDate;

    /** Время редактирования */
    @Comment("Время редактирования")
    @Persist @DoTimeString @TimeString
    public String getEditTime() {return editTime;}
    /** Время редактирования */
    private String editTime;
    
    /** Пользователь последний, изменявший запись */
    @Comment("Пользователь последний, изменявший запись")
    @Persist
    public String getEditUsername() {
        return editUsername;
    }
    /** Пользователь последний, изменявший запись */
    private String editUsername;

    /** Изменение в лёгких в оценке ковида */
    @Comment("Изменение в лёгких в оценке ковида")
    @Persist
    public Long getChangeLungs() {return changeLungs;}
    private Long changeLungs ;

    /** Частота дыхательных движений в оценке ковида */
    @Comment("Частота дыхательных движений в оценке ковида")
    @Persist
    public Long getChdd() {return chdd;}
    private Long chdd ;

    /** Пульсоксиметрия в оценке ковида */
    @Comment("Пульсоксиметрия в оценке ковида")
    @Persist
    public Long getPuls() {return puls;}
    private Long puls ;

    /** Температура тела в оценке ковида */
    @Comment("Температура тела в оценке ковида")
    @Persist
    public Long getTemp() {return temp;}
    private Long temp ;

    /** Тяжесть заболевания в оценке ковида */
    @Comment("Тяжесть заболевания в оценке ковида")
    @Persist
    public Long getSost() {return sost;}
    private Long sost ;

    /** Нарушение сознания (3) */
    @Comment("Нарушение сознания (3)")
    @Persist
    public Boolean getIsBadSozn() {return isBadSozn;}
    private Boolean isBadSozn ;

    /** Строка с признаками тяжёлого состояния */
    public String getBadSostString() {
        return badSostString;
    }
    private String badSostString ;
}
