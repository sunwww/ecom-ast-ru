package ru.ecom.expomc.ejb.services.check.checkers.registry;

import java.util.Collection;

import ru.ecom.expomc.ejb.domain.registry.RegistryEntry;
import ru.ecom.expomc.ejb.services.check.ICheckLog;
import ru.ecom.expomc.ejb.services.check.checkers.BadPropertyUtil;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Проверка на поликлинику
 */
@Comment("Проверка на поликлинику")
public class CheckPoliclinic extends AbstractRegistryAcceptedCheck {

    public boolean isAccepted(RegistryEntry aEntry, ICheckLog aLog) {
        return isPoliklinika(aEntry.getRender()) ;
    }

    public Collection<String> getBadProperties() {
    	return BadPropertyUtil.create("render") ;
	}

    public static boolean isPoliklinika(String aUsluga) {
        boolean ret;

        String letters = "CDLKEF";
        if (aUsluga != null) {
            if (aUsluga.length() > 3) {
                char code = aUsluga.charAt(3);
                ret = false;
                for (int i = 0; i < letters.length(); i++) {
                    char ch = letters.charAt(i);
                    if (ch == code) {
                        ret = true;
                        break;
                    }
                }
            } else {
                ret = false;
            }
        } else {
            ret = false;
        }
        return ret;
    }

}
