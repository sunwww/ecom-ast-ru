package ru.ecom.expomc.ejb.services.check.checkers;

import lombok.Getter;
import lombok.Setter;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Замена по значению
 */
@Comment("Замена по значению")
@Getter
@Setter
public class ChangeStringEquals extends AbstractChangeStringProperty {

    public String transform(String aValue) {
        if(aValue.equals(equalValue)) {
            aValue = changeTo ;
        }
        return aValue ;
    }

    /** Если значение равно */
    private String equalValue ;
    /** То заменить на */
     private String changeTo ;

}
