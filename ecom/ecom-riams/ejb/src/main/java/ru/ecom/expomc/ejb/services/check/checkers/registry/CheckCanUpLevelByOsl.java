package ru.ecom.expomc.ejb.services.check.checkers.registry;

import java.util.Collection;

import ru.ecom.expomc.ejb.domain.registry.RegistryEntry;
import ru.ecom.expomc.ejb.services.check.ICheckLog;
import ru.ecom.expomc.ejb.services.check.checkers.BadPropertyUtil;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.util.StringUtil;

/**
 * Нужно ли поднимать цену в зависимости от осложнений
 */
@Comment("Нужно ли поднимать цену в зависимости от осложнений")
public class CheckCanUpLevelByOsl extends AbstractRegistryAcceptedCheck {

    public boolean isAccepted(RegistryEntry aEntry, ICheckLog aLog) {
        String strOsl = aEntry.getOsl() ;
        if(StringUtil.isNullOrEmpty(strOsl)) {
            return false ;
        } else {
            long osl = Long.parseLong(strOsl) ;
            return canUpLevel(osl) ;
        }
    }

    public Collection<String> getBadProperties() {
    	return BadPropertyUtil.create("osl") ;
	}
    
    private static boolean canUpLevel(long aOsl) {
        boolean ret ;
        ret = aOsl==5 || aOsl==6 || aOsl==4 ;
        return ret ;

    }
}
