package ru.ecom.expomc.ejb.services.voc.allvalues;

import ru.ecom.ejb.services.voc.helper.ArrayAllValue;
import ru.ecom.expomc.ejb.domain.check.Check;

/**
 * @author esinev 18.08.2006 1:28:06
 */
public class CheckTypeAllValues extends ArrayAllValue {

    public CheckTypeAllValues() {
        addValue(Check.TYPE_INFO+"", "Сигнальное сообщение");
        addValue(Check.TYPE_CHANGE+"", "Изменение");
        addValue(Check.TYPE_CRITICAL+"", "Критическая ошибка");
    }

}
