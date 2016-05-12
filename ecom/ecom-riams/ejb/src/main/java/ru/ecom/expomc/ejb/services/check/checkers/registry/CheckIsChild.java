package ru.ecom.expomc.ejb.services.check.checkers.registry;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import ru.ecom.expomc.ejb.domain.registry.RegistryEntry;
import ru.ecom.expomc.ejb.services.check.ICheckLog;
import ru.ecom.expomc.ejb.services.check.checkers.BadPropertyUtil;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Ребенок?
 */
@Comment("Ребенок?")
public class CheckIsChild extends AbstractRegistryAcceptedCheck {

    public boolean isAccepted(RegistryEntry aEntry, ICheckLog aLog) {
        return isChild(aLog, aEntry.getBirthDate(), aEntry.getAdmissionDate(), aEntry.getDischargeDate()) ;
    }

    public static boolean isChild(ICheckLog aLog, Date aBirthDate, Date aAdmissionDate, Date aDischargeDate) {
        Calendar cal = Calendar.getInstance();
        boolean ret;
        if (aAdmissionDate == null && aDischargeDate == null) {
            throw new IllegalArgumentException("Дата выписки и дата поступления не определены. Возраст: взрослый.");
        } else {
            if (aAdmissionDate == null) {
                aLog.warn("Дата поступления не определена. Возраст будет опреляться по дате выписки: "+ aDischargeDate);
                cal.setTime(aDischargeDate);
            } else {
                cal.setTime(aAdmissionDate);
            }
            cal.add(Calendar.YEAR, -15);
            ret = aBirthDate.getTime() > cal.getTime().getTime();
        }
        return ret;
    }

    public Collection<String> getBadProperties() {
    	return BadPropertyUtil.create("birthDate","admissionDate","dischargeDate") ;
	}

}
