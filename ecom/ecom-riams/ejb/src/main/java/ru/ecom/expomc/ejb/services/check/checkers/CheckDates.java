package ru.ecom.expomc.ejb.services.check.checkers;

import java.sql.Date;
import java.util.Collection;
import java.util.StringTokenizer;

import ru.ecom.expomc.ejb.services.check.CheckException;
import ru.ecom.expomc.ejb.services.check.CheckResult;
import ru.ecom.expomc.ejb.services.check.ICheck;
import ru.ecom.expomc.ejb.services.check.ICheckContext;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Проверка порядка дат (от меньшей к большей)
 */
@Comment("Проверка порядка дат (от меньшей к большей)")
public class CheckDates implements ICheck {

    public CheckResult check(ICheckContext aContext) throws CheckException {
        long lastDate = 0 ;
        StringTokenizer st = new StringTokenizer(theProperties, ", ;");
        boolean accepted = false ;
        while(st.hasMoreTokens()) {
            Date date = aContext.getDate(st.nextToken());
            long d = date!=null ? date.getTime() : 0 ;
            if(d<lastDate) {
                accepted = true ;
                break ;
            }
        }
        return CheckResult.createAccepted(accepted) ;
    }

    public Collection<String> getBadProperties() {
    	return BadPropertyUtil.createTokenized(getProperties(), " ,;") ;
	}
    
    /** Свойства с датами (от меньшей к большей) через запятую*/
    @Comment("Свойства с датами (от меньшей к большей)")
    public String getProperties() { return theProperties ; }
    public void setProperties(String aProperties) { theProperties = aProperties ; }

    /** Свойства с датами (от меньшей к большей) */
    private String theProperties ;
}
