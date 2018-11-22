package ru.ecom.expomc.ejb.services.check.checkers.registry;

import ru.ecom.expomc.ejb.domain.registry.RegistryEntry;
import ru.ecom.expomc.ejb.services.check.ICheckLog;
import ru.ecom.expomc.ejb.services.check.checkers.BadPropertyUtil;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import java.util.Collection;

/**
 * Провизорность
 */
@Comment("Провизорность")
@Deprecated
public class CheckProvisor extends AbstractRegistryAcceptedCheck {

    public boolean isAccepted(RegistryEntry aEntry, ICheckLog aLog) {
        return isProvisor(aEntry.getRender()) ;
    }

    public static boolean isProvisor(String aUsluga) {
        boolean ret = false;
        if (aUsluga != null && aUsluga.length() > 2) {
            ret = aUsluga.charAt(3) == 'A';
        }
        return ret;

    }
    
    public Collection<String> getBadProperties() {
    	return BadPropertyUtil.create("render") ;
	}
    
}
