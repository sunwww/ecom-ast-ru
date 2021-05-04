package ru.ecom.expomc.ejb.services.check.checkers;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Проверка на значение
 */
@Comment("Проверка на значение")
public class CheckStringEqual extends AbstractCheckStringProperty {

    public boolean accept(String aStr) {
        return value.equals(aStr) ;
    }

    /** Значение */
    @Comment("Значение")
    @Required
    public String getValue() { return value ; }
    public void setValue(String aValue) { value = aValue ; }

    /** Значение */
    private String value ;
}
