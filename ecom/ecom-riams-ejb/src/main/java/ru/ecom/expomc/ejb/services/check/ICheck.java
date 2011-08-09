package ru.ecom.expomc.ejb.services.check;

import java.util.Collection;

/**
 * Проверка
 */
public interface ICheck {

    /**
     * Проверка
     * @param aContext параметры проверки
     * @return результат проверки
     */
    CheckResult check(ICheckContext aContext) throws CheckException ;
    
    /**
     * Поля, на которые следует обратить внимание
     * @return .
     */
    Collection<String> getBadProperties() ;
}
