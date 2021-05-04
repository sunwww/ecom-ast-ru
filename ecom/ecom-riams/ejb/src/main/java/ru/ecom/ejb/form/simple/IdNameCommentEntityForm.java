package ru.ecom.ejb.form.simple;

import lombok.Getter;
import lombok.Setter;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;

/**
 * ИД + НАЗВАНИЕ + КОММЕНТАРИЙ
 */
@EntityForm
@Setter
public abstract class IdNameCommentEntityForm extends IdNameEntityForm {
    /** Комментарий */
    @Comment("Комментарий")
    @Persist
    public String getComment() { return comment ; }

    /** Комментарий */
    private String comment ;
}
