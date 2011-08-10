package ru.nuzmsh.logicpool.api;

import java.util.Collection;

/**
 * Результат выбора объектов.
 * <h5>Пример</h5>
 * <pre>
 *    IResultSet rs = logic.findByAll("StacSLS") ;
 *    for( Object o: rs) {
 *         String id = (String)o ;
 *    }
 * </pre>
 *
 * @author ESinev
 *         Date: 21.10.2005
 *         Time: 15:46:54
 */
public interface IResultSet extends Iterable, Collection {

    /**
     * Устанавливает конвертор первичный ключей.
     * По-умолчания первичный ключ возв
     * @param aConverter
     */
    void setPkConverter(IPkConverter aConverter) ;



}

