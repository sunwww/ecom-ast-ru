package ru.ecom.expert2.form.voc.federal;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.domain.voc.federal.VocE2FondV010;
import ru.ecom.expert2.domain.voc.federal.VocE2FondV012;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

import javax.persistence.Entity;

/**
 * Классификатор исходов заболевания (ISHOD)
 */
@EntityForm
@EntityFormPersistance(clazz = VocE2FondV012.class)
@Comment("Исход заболевания")
@WebTrail(comment = "Исход заболевания", nameProperties = "code", view = "entityView-e2_vocFondV012.do")
@EntityFormSecurityPrefix("/Policy/E2")
@Setter
public class VocE2FondV012Form extends IdEntityForm {

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
    /** Условия оказания помощи (v006)*/
    @Comment("Условия оказания помощи (v006)")
    @Persist
    public String getUsl() {return usl;}
    /** Условия оказания помощи */
    private String usl ;
}
