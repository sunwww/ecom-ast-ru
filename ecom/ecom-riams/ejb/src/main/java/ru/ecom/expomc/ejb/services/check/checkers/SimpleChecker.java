package ru.ecom.expomc.ejb.services.check.checkers;

import java.util.Collection;

import ru.ecom.expomc.ejb.services.check.CheckException;
import ru.ecom.expomc.ejb.services.check.CheckResult;
import ru.ecom.expomc.ejb.services.check.ICheck;
import ru.ecom.expomc.ejb.services.check.ICheckContext;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * @author esinev
 * Date: 23.08.2006
 * Time: 11:27:46
 */
public class SimpleChecker implements ICheck {

    public CheckResult check(ICheckContext aContext) throws CheckException {
        CheckResult result = new CheckResult() ;
        result.error("hello");
        result.info("ifno") ;
        result.warn("warn");
        result.debug("debug");
        result.setAccepted(true);
        return result ;
    }

    public Collection<String> getBadProperties() {
    	return null ;
	}
    
    /** Пробное свойство */
    @Comment("Пробное свойство")
    public String getProba() { return theProba ; }
    public void setProba(String aProba) { theProba = aProba ; }

    /** Вторая проба */
    @Comment("Вторая проба")
    public String getProba2() { return theProba2 ; }
    public void setProba2(String aProba2) { theProba2 = aProba2 ; }

    /** Вторая проба */
    private String theProba2 ;
    /** Пробное свойство */
    private String theProba ;

}
