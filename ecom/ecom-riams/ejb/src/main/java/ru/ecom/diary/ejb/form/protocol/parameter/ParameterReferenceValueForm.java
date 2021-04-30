package ru.ecom.diary.ejb.form.protocol.parameter;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.diary.ejb.domain.protocol.parameter.ParameterReferenceValue;
import ru.ecom.diary.ejb.service.protocol.ParameterReferenceValueSaveInterceptor;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.ACreateInterceptors;
import ru.ecom.ejb.services.entityform.interceptors.AEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.ASaveInterceptors;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 *
 */
@EntityForm
@EntityFormPersistance(clazz = ParameterReferenceValue.class)
@Comment("Референтные значения для параметра")
@WebTrail(comment = "Референтные значения для параметра", nameProperties = "id", view = "entityView-diary_parameterRef.do")
@EntityFormSecurityPrefix("/Policy/Diary/ParameterGroup/Parameter")
@Parent(parentForm = ParameterForm.class, property = "parameter")
@ACreateInterceptors(@AEntityFormInterceptor(ParameterReferenceValueSaveInterceptor.class))
@ASaveInterceptors(@AEntityFormInterceptor(ParameterReferenceValueSaveInterceptor.class))
@Setter
public class ParameterReferenceValueForm extends IdEntityForm {
    /** Параметр */
    @Comment("Параметр")
    @Persist @Required
    public Long getParameter() {return parameter;}
    /** Параметр */
    private Long parameter ;

    /** Пол */
    @Comment("Пол")
    @Persist
    public Long getSex() {return sex;}
    /** Пол */
    private Long sex ;

    /** Возраст от */
    @Comment("Возраст от")
    @Persist
    public Integer getAgeFrom() {return ageFrom;}
    /** Возраст от */
    private Integer ageFrom ;

    /** Возраст до */
    @Comment("Возраст до")
    @Persist @Required
    public Integer getAgeTo() {return ageTo;}
    /** Возраст до */
    private Integer ageTo ;

    /** Мин норма */
    @Comment("Мин норма")
    @Persist @Required
    public String getNormaMin() {return normaMin;}
    /** Мин норма */
    private String normaMin ;

    /** Мин максимум */
    @Comment("Мин максимум")
    @Persist @Required
    public String getNormaMax() {return normaMax;}
    /** Мин максимум */
    private String normaMax ;

    /** Максимально возможное значение */
    @Comment("Максимально возможное значение")
    @Persist @Required
    public Long getSuperMax() {return superMax;}
    /** Максимально возможное значение */
    private Long superMax ;

    /** Минимально возможное значение */
    @Comment("Минимально возможное значение")
    @Persist @Required
    public Long getSuperMin() {return superMin;}
    /** Минимально возможное значение */
    private Long superMin ;
}