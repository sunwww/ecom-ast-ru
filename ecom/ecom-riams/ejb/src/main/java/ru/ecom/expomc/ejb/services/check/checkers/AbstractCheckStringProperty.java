package ru.ecom.expomc.ejb.services.check.checkers;

import java.util.Collection;

import ru.ecom.expomc.ejb.services.check.CheckException;
import ru.ecom.expomc.ejb.services.check.CheckResult;
import ru.ecom.expomc.ejb.services.check.ICheck;
import ru.ecom.expomc.ejb.services.check.ICheckContext;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.util.StringUtil;

/**
 * Проверка по свойству
 */
public abstract class AbstractCheckStringProperty implements ICheck {
    public CheckResult check(ICheckContext aContext) throws CheckException {
        String str = aContext.getString(theProperty);

        CheckResult result = new CheckResult();
        if(!StringUtil.isNullOrEmpty(str)) {
            result.setAccepted(accept(str)) ;
        }

        return result ;
    }

    
    public Collection<String> getBadProperties() {
    	return BadPropertyUtil.create(getProperty()) ;
	}


	public abstract boolean accept(String aStr) ;

    /** Свойство */
    @Comment("Свойство")
    public String getProperty() { return theProperty ; }
    public void setProperty(String aProperty) { theProperty = aProperty ; }

    /** Свойство */
    private String theProperty ;
}
