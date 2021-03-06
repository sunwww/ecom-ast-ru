package ru.ecom.expert2.form.voc.federal;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.domain.voc.federal.VocE2FondV021;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Классификатор медицинских специальностей V021
 */
@EntityForm
@EntityFormPersistance(clazz = VocE2FondV021.class)
@Comment("Медицинская специальность V021")
@WebTrail(comment = "Медицинская специальность V021", nameProperties = "code", view = "entityView-e2_vocFondV021.do")
@EntityFormSecurityPrefix("/Policy/E2")
public class VocE2FondV021Form extends VocBaseFederalForm {

    /** Название */
    @Comment("Название")
    @Persist @Required
    public String getName() {return theName;}
    public void setName(String aName) {theName = aName;}
    /** Название */
    private String theName ;

    /** Код */
    @Comment("Код")
    @Persist @Required
    public String getCode() {return theCode;}
    public void setCode(String aCode) {theCode = aCode;}
    /** Код */
    private String theCode ;

    /** Услуга по умолчанию для поликлиники */
    @Comment("Услуга по умолчанию для поликлиники")
    @Persist
    public Long getDefaultMedService() {return theDefaultMedService;}
    public void setDefaultMedService(Long aDefaultMedService) {theDefaultMedService = aDefaultMedService;}
    /** Услуга по умолчанию для поликлиники */
    private Long theDefaultMedService ;

    /** Услуга по умолчания (повторный визит) */
    @Comment("Услуга по умолчания (повторный визит)")
    @Persist
    public Long getRepeatMedService() {return theRepeatMedService;}
    public void setRepeatMedService(Long aRepeatMedService) {theRepeatMedService = aRepeatMedService;}
    /** Услуга по умолчания (повторный визит) */
    private Long theRepeatMedService ;
}
