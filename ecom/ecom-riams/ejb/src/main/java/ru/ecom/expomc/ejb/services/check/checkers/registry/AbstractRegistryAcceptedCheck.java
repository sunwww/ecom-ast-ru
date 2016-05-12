package ru.ecom.expomc.ejb.services.check.checkers.registry;

import ru.ecom.expomc.ejb.domain.registry.RegistryEntry;
import ru.ecom.expomc.ejb.services.check.CheckException;
import ru.ecom.expomc.ejb.services.check.CheckResult;
import ru.ecom.expomc.ejb.services.check.ICheck;
import ru.ecom.expomc.ejb.services.check.ICheckContext;
import ru.ecom.expomc.ejb.services.check.ICheckLog;

/**
 * Проверка на записи реестра только на Accept
 */
public abstract class AbstractRegistryAcceptedCheck implements ICheck {

    public CheckResult check(ICheckContext aContext) throws CheckException {
        return CheckResult.createAccepted(isAccepted((RegistryEntry) aContext.getEntry(), aContext.getLog()));
    }

    public abstract boolean isAccepted(RegistryEntry aEntry, ICheckLog aLog) ;
}
