package ru.ecom.expomc.ejb.services.check.checkers.registry;

import java.util.Collection;

import ru.ecom.expomc.ejb.domain.registry.RegistryEntry;
import ru.ecom.expomc.ejb.services.check.ICheckLog;
import ru.ecom.expomc.ejb.services.check.checkers.BadPropertyUtil;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Длительность провизорного случая больше 4 дней
 */
@Comment("Проверка длительность провизорного случая больше 4 дней")
public class CheckProvisorMoreThan4 extends AbstractRegistryAcceptedCheck {

    public boolean isAccepted(RegistryEntry aEntry, ICheckLog aLog) {
        boolean isProvisor = theCheckProvisor.isAccepted(aEntry, aLog) ;
        boolean ret = false ;

        return isProvisor && aEntry.getBedDays()>4 ;
    }

    public Collection<String> getBadProperties() {
    	return BadPropertyUtil.create("render","bedDays") ;
	}

    private CheckProvisor theCheckProvisor = new CheckProvisor();
}
