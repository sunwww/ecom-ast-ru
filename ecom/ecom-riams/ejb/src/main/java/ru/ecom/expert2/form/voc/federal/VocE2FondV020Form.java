package ru.ecom.expert2.form.voc.federal;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.domain.voc.federal.VocE2FondV020;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

/**
 * Профиль койки
 */
@EntityForm
@EntityFormPersistance(clazz = VocE2FondV020.class)
@Comment("Профиль койки")
@WebTrail(comment = "Профиль койки", nameProperties = "code", view = "entityView-e2_vocFondV020.do")
@EntityFormSecurityPrefix("/Policy/E2")
@Setter
public class VocE2FondV020Form extends IdEntityForm {

    /** Название */
    @Comment("Название")
    @Persist
    public String getName() {return name;}
    /** Название */
    private String name ;

    /** Код */
    @Comment("Код")
    @Persist
    public String getCode() {return code;}
    /** Код */
    private String code ;

    /** Дата начала действия */
    @Comment("Дата начала действия")
    @Persist @DateString
    @DoDateString
    public String getStartDate() {return startDate;}
    /** Дата начала действия */
    private String startDate ;

    /** Дата окончания действия */
    @Comment("Дата окончания действия")
    @Persist @DateString @DoDateString
    public String getFinishDate() {return finishDate;}
    /** Дата окончания действия */
    private String finishDate ;


    /** Услуга по профилю для стационар по умолчанию */
    @Comment("Услуга по профилю для стационар по умолчанию")
    @Persist
    public Long getDefaultStacMedService() {return defaultStacMedService;}
    /** Услуга по профилю для стационар по умолчанию */
    private Long defaultStacMedService ;
}
