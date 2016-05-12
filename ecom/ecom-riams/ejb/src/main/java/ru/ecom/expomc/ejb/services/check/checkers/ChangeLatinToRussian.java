package ru.ecom.expomc.ejb.services.check.checkers;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Замена латинских букв на похожие русские
 */
@Comment("Замена латинских букв на похожие русские")
public class ChangeLatinToRussian extends AbstractChangeLetters {

    public ChangeLatinToRussian() {
        super() ;
        put('A', 'А');        put('a', 'а');

        put('B', 'В');

        put('H', 'Н');

        put('P', 'Р');

        put('O', 'О');        put('o', 'о');

        put('E', 'Е');        put('e', 'е');

        put('T', 'Т');

        put('Y', 'У');        put('y', 'у');

        put('K', 'К');        put('k', 'к');

        put('X', 'Х');        put('x', 'х');

        put('C', 'С');        put('c', 'с');

        put('M', 'М');
    }
}
