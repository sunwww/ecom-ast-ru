package ru.ecom.ejb.form.simple;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;

/**
 * ИД + НАЗВАНИЕ + КОММЕНТАРИЙ
 */
@EntityForm
public abstract class IdNameCommentEntityForm extends IdNameEntityForm {
    /** Комментарий */
    @Comment("Комментарий")
    @Persist
    public String getComment() { return theComment ; }
    public void setComment(String aComment) { theComment = aComment ; }

    /** Комментарий */
    private String theComment ;
}
