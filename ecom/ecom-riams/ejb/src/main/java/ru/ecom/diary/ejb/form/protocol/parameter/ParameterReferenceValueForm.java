package ru.ecom.diary.ejb.form.protocol.parameter;

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
public class ParameterReferenceValueForm extends IdEntityForm {
    /** Параметр */
    @Comment("Параметр")
    @Persist @Required
    public Long getParameter() {return theParameter;}
    public void setParameter(Long aParameter) {theParameter = aParameter;}
    /** Параметр */
    private Long theParameter ;

    /** Пол */
    @Comment("Пол")
    @Persist
    public Long getSex() {return theSex;}
    public void setSex(Long aSex) {theSex = aSex;}
    /** Пол */
    private Long theSex ;

    /** Возраст от */
    @Comment("Возраст от")
    @Persist
    public Integer getAgeFrom() {return theAgeFrom;}
    public void setAgeFrom(Integer aAgeFrom) {theAgeFrom = aAgeFrom;}
    /** Возраст от */
    private Integer theAgeFrom ;

    /** Возраст до */
    @Comment("Возраст до")
    @Persist @Required
    public Integer getAgeTo() {return theAgeTo;}
    public void setAgeTo(Integer aAgeTo) {theAgeTo = aAgeTo;}
    /** Возраст до */
    private Integer theAgeTo ;

    /** Мин норма */
    @Comment("Мин норма")
    @Persist @Required
    public String getNormaMin() {return theNormaMin;}
    public void setNormaMin(String aNormaMin) {theNormaMin = aNormaMin;}
    /** Мин норма */
    private String theNormaMin ;

    /** Мин максимум */
    @Comment("Мин максимум")
    @Persist @Required
    public String getNormaMax() {return theNormaMax;}
    public void setNormaMax(String aNormaMax) {theNormaMax = aNormaMax;}
    /** Мин максимум */
    private String theNormaMax ;

    /** Максимально возможное значение */
    @Comment("Максимально возможное значение")
    @Persist @Required
    public Long getSuperMax() {return theSuperMax;}
    public void setSuperMax(Long aSuperMax) {theSuperMax = aSuperMax;}
    /** Максимально возможное значение */
    private Long theSuperMax ;

    /** Минимально возможное значение */
    @Comment("Минимально возможное значение")
    @Persist @Required
    public Long getSuperMin() {return theSuperMin;}
    public void setSuperMin(Long aSuperMin) {theSuperMin = aSuperMin;}
    /** Минимально возможное значение */
    private Long theSuperMin ;
}