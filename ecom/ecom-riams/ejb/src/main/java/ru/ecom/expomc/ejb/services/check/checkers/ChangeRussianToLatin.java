package ru.ecom.expomc.ejb.services.check.checkers;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Замена похожих русских букв на латинские
 */
@Comment("Замена похожих русских букв на латинские")
public class ChangeRussianToLatin extends ChangeLatinToRussian {

    public ChangeRussianToLatin() {
        super() ;
        revert();
    }
}
