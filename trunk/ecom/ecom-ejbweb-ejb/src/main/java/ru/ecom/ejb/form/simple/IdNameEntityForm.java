package ru.ecom.ejb.form.simple;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;

/**
 *  ИД + НАЗВАНИЕ
 */
@EntityForm
public abstract class IdNameEntityForm extends IdEntityForm {
    /** Название */
    @Comment("Название")
    @Persist
    public String getName() { return theName ; }
    public void setName(String aName) { theName = aName ; }

    /** Название */
    private String theName ;
}
