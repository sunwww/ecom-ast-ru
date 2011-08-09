package ru.ecom.expomc.ejb.services.check.checkers;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Замена букв O (русскую) и O (латинскую) на 0 (ноль)
 */
@Comment("Замена букв O (русскую) и O (латинскую) на 0 (ноль)")
public class ChangeOToZero extends AbstractChangeLetters {

    public ChangeOToZero() {
        super() ;
        put('О', '0');
        put('O', '0');
    }
}
