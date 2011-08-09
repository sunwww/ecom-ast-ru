package ru.ecom.expomc.ejb.services.check.checkers;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Перевод в нижний регистр
 */
@Comment("Перевод в нижний регистр")
public class ChangeLowerCase extends AbstractChangeStringProperty {
    public String transform(String aStr) {
        return aStr.toLowerCase() ;
    }
}
