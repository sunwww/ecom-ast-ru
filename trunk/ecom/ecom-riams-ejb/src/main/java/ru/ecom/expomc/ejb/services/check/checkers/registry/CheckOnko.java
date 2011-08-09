package ru.ecom.expomc.ejb.services.check.checkers.registry;

import java.util.Collection;

import ru.ecom.expomc.ejb.domain.registry.RegistryEntry;
import ru.ecom.expomc.ejb.services.check.ICheckLog;
import ru.ecom.expomc.ejb.services.check.checkers.BadPropertyUtil;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Онокопатология
 */
@Comment("Онкопатология")
public class CheckOnko extends AbstractRegistryAcceptedCheck {

    public boolean isAccepted(RegistryEntry aEntry, ICheckLog aLog) {
        return isOnko(aEntry.getDiagnosisMain()) ;
    }

    public static boolean isOnko(String aMkb) {
        return aMkb!=null && aMkb.length()>1 && ( aMkb.charAt(0)=='C' || aMkb.charAt(0)=='c') ;
    }
    
    public Collection<String> getBadProperties() {
    	return BadPropertyUtil.create("miagnosisMain") ;
	}
    
}
