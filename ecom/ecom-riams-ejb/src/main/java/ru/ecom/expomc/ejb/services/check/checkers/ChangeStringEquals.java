package ru.ecom.expomc.ejb.services.check.checkers;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Замена по значению
 */
@Comment("Замена по значению")
public class ChangeStringEquals extends AbstractChangeStringProperty {

    public String transform(String aValue) {
        if(aValue.equals(theEqualValue)) {
            aValue = theChangeTo ;
        }
        return aValue ;
    }

    /** То заменить на */
    @Comment("То заменить на")
    public String getChangeTo() { return theChangeTo ; }
    public void setChangeTo(String aChangeTo) { theChangeTo = aChangeTo ; }

    /** Если значение равно */
    @Comment("Если значение равно")
    public String getEqualValue() { return theEqualValue ; }
    public void setEqualValue(String aEqualValue) { theEqualValue = aEqualValue ; }

    /** Если значение равно */
    private String theEqualValue ;
    /** То заменить на */
     private String theChangeTo ;

}
