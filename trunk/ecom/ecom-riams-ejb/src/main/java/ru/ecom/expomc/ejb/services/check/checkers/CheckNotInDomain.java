package ru.ecom.expomc.ejb.services.check.checkers;

import ru.ecom.expomc.ejb.services.check.CheckException;
import ru.ecom.expomc.ejb.services.check.CheckResult;
import ru.ecom.expomc.ejb.services.check.ICheckContext;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Проверка на НЕвхохдение в домен
 */
@Comment("Проверка на НЕвхохдение в домен")
public class CheckNotInDomain extends CheckInDomain {
    public CheckResult check(ICheckContext aContext) throws CheckException {
        return CheckResult.createAccepted(super.check(aContext).isAccepted());
    }
}
