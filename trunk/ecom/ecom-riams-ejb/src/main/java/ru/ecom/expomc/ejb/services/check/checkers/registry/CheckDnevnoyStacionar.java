package ru.ecom.expomc.ejb.services.check.checkers.registry;

import java.util.Collection;

import ru.ecom.expomc.ejb.domain.registry.RegistryEntry;
import ru.ecom.expomc.ejb.services.check.ICheckLog;
import ru.ecom.expomc.ejb.services.check.checkers.BadPropertyUtil;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Проверка на дневной стационар
 */
@Comment("Проверка на дневной стационар")
public class CheckDnevnoyStacionar extends AbstractRegistryAcceptedCheck {

    public boolean isAccepted(RegistryEntry aEntry, ICheckLog aLog) {
        return isDnevnoyStacionar(aEntry.getRender()) ;
    }

    public static boolean isDnevnoyStacionar(String aUsluga) {
        boolean ret = false;
        if (aUsluga != null && aUsluga.length() > 3) {
            ret = aUsluga.toUpperCase().charAt(3) == 'J';
        }
        return ret;
    }
    public Collection<String> getBadProperties() {
    	return BadPropertyUtil.create("render") ;
	}

}
