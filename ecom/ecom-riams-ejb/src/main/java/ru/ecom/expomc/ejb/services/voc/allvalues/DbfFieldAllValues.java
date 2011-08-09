package ru.ecom.expomc.ejb.services.voc.allvalues;

import ru.ecom.ejb.services.voc.helper.ArrayAllValue;
import ru.ecom.expomc.ejb.domain.format.Field;

/**
 * @author esinev 18.08.2006 1:28:06
 */
public class DbfFieldAllValues extends ArrayAllValue {
    public DbfFieldAllValues() {
        addValue(Field.TEXT+"", "Текст");
        addValue(Field.NUMERIC+"", "Число");
        addValue(Field.DATE+"", "Дата");
    }

}
