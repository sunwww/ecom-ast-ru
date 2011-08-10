package ru.nuzmsh.logicpool.api.pkconverter;

import ru.nuzmsh.logicpool.api.IPkConverter;

/**
 * Преобразование из String в java.lang.Long
 */
public class LongPkConverter implements IPkConverter {

    public Object convert(String aStr) {
        return Long.parseLong(aStr) ;
    }
}