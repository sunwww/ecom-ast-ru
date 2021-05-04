package ru.ecom.ejb.form.simple;

import lombok.Getter;
import lombok.Setter;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;

/**
 *  ИД + НАЗВАНИЕ
 */
@EntityForm
@Setter
public abstract class IdNameEntityForm extends IdEntityForm {
    /** Название */
    @Comment("Название")
    @Persist
    public String getName() { return name ; }

    /** Название */
    private String name ;
}
