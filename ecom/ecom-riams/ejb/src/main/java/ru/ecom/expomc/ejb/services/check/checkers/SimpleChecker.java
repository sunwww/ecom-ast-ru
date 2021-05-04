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
        result.setAccepted(true);
        return result ;
    }

    public Collection<String> getBadProperties() {
    	return null ;
	}
    
    /** Пробное свойство */
    @Comment("Пробное свойство")
    public String getProba() { return proba ; }
    public void setProba(String aProba) { proba = aProba ; }

    /** Вторая проба */
    @Comment("Вторая проба")
    public String getProba2() { return proba2 ; }
    public void setProba2(String aProba2) { proba2 = aProba2 ; }

    /** Вторая проба */
    private String proba2 ;
    /** Пробное свойство */
    private String proba ;

}
