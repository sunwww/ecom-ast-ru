package ru.nuzmsh.logicpool.api;

/**
 * Результат выполнения запроса kowFind.
 *
 * <h5>Пример 1:</h5>
 * <pre>
 *    IKowResultSet rs = logic.kowFind("StacSLS", "SLSdatawip:=20030321") ;
 *    for( Object o: rs) {
 *         String id = (String)o ;
 *    }
 * </pre>
 *
 * <h5>Пример 2:</h5>
 * <pre>
 *    IKowResultSet rs = logic.kowFind("StacSLS", "SLSdatawip:=20030321") ;
 *    Itarator it = rs.iterator() ;
 *    for(int i=0; i&lt;it.hasNext() && i&lt;20; i++) {
 *         String id = it.next() ;
 *    }
 *    rs.close() ;
 * </pre>
 * @author ESinev
 *         Date: 24.10.2005
 *         Time: 8:39:38
 */
public interface IKowResultSet extends IResultSet {
    /**
     * Количество элементов в выборке
     * @return размер
     */
    int size();

    /**
     * Удаляет времменный глобал, где находились результаты выборки
     * @throws LogicException
     */
    void close() throws LogicException;

}
