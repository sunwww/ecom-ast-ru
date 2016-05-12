package ru.ecom.expomc.ejb.services.check.checkers;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Перевод в верхний регистр
 */
@Comment("Перевод в верхний регистр")
public class ChangeUpperCase extends AbstractChangeStringProperty {
    public String transform(String aStr) {
        return aStr.toUpperCase() ;
    }
}
