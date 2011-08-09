package ru.ecom.expomc.ejb.services.check.checkers;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Замена нуля на букву O (латинскию)
 */
@Comment("Замена нуля на букву O (латинскию)")
public class ChangeZeroToLatinO extends AbstractChangeLetters {

    public ChangeZeroToLatinO() {
        super() ;
        put('0', 'O');
    }
}
