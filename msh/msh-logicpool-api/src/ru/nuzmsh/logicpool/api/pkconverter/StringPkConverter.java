package ru.nuzmsh.logicpool.api.pkconverter;

import ru.nuzmsh.logicpool.api.IPkConverter;

/**
 * Преобразование из String -> String
 */
public class StringPkConverter implements IPkConverter {

    public Object convert(String aStr) {
        return aStr ;
    }
}
