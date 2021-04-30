package ru.ecom.expert2.form.voc.federal;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

/**
 * Условия оказания медицинской помощи
 */
@EntityForm
@Setter
public class VocE2FondV006Form extends IdEntityForm {
    /** Дата начала действия */
    @Comment("Дата начала действия")
    @Persist
    @DateString
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
}
