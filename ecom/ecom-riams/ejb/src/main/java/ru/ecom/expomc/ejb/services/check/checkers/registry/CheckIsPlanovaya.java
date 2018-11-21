package ru.ecom.expomc.ejb.services.check.checkers.registry;

import ru.ecom.expomc.ejb.domain.registry.RegistryEntry;
import ru.ecom.expomc.ejb.services.check.ICheckLog;
import ru.ecom.expomc.ejb.services.check.checkers.BadPropertyUtil;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import java.util.Collection;

/**
 * Проверка на плановую госпитализацию
 */
@Comment("Проверка на плановую госпитализацию")
@Deprecated
public class CheckIsPlanovaya extends AbstractRegistryAcceptedCheck {

    public boolean isAccepted(RegistryEntry aEntry, ICheckLog aLog) {
        return isPlanovaya(aEntry.getDirectionType()) ;
    }

    private boolean isPlanovaya(String as) {
        return "П".equals(as) ;
    }
    
    public Collection<String> getBadProperties() {
    	return BadPropertyUtil.create("directionType") ;
	}
    
}
