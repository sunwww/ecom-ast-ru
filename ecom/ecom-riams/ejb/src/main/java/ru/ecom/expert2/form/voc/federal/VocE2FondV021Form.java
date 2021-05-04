package ru.ecom.expert2.form.voc.federal;

import lombok.Setter;
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
@WebTrail(comment = "Медицинская специальность V021", nameProperties = {"code","name"}, view = "entityView-e2_vocFondV021.do")
@EntityFormSecurityPrefix("/Policy/E2")
@Setter
public class VocE2FondV021Form extends VocBaseFederalForm {

    /** Название */
    @Comment("Название")
    @Persist @Required
    public String getName() {return name;}
    /** Название */
    private String name ;

    /** Код */
    @Comment("Код")
    @Persist @Required
    public String getCode() {return code;}
    /** Код */
    private String code ;

    /** Услуга по умолчанию для поликлиники */
    @Comment("Услуга по умолчанию для поликлиники")
    @Persist
    public Long getDefaultMedService() {return defaultMedService;}
    /** Услуга по умолчанию для поликлиники */
    private Long defaultMedService ;

    /** Услуга по умолчания (повторный визит) */
    @Comment("Услуга по умолчания (повторный визит)")
    @Persist
    public Long getRepeatMedService() {return repeatMedService;}
    /** Услуга по умолчания (повторный визит) */
    private Long repeatMedService ;

    /** Профиль мед. помощи для подачи по стационару */
    @Comment("Профиль мед. помощи для подачи по стационару")
    @Persist
    public Long getStacProfile() {return stacProfile;}
    /** Профиль мед. помощи для подачи по стационару */
    private Long stacProfile ;

    /** Профиль мед. помощи для подачи по поликлинике */
    @Comment("Профиль мед. помощи для подачи по поликлинике")
    @Persist
    public Long getPolicProfile() {return policProfile;}
    /** Профиль мед. помощи для подачи по поликлинике */
    private Long policProfile ;

    /** Признак подушевого финансирования специальности */
    @Comment("Признак подушевого финансирования специальности")
    @Persist
    public Boolean getIsPodushevoy() {return isPodushevoy;}
    private Boolean isPodushevoy ;
}
