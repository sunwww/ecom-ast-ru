package ru.nuzmsh.logicpool.api;

import java.text.ParseException;
import java.util.Date;
import java.math.BigDecimal;

/**
 * Доступ к Cachè из Java. Основные операции над данными.
 *
 * @author ESinev
 *         Date: 17.10.2005
 *         Time: 13:06:50
 */
public interface ILogic {

    /**
     * Возвращает новый идентификатор объекта.
     * lastId + 1
     * @param aClassName имя класса Cache
     * @return новый идентификатор объекта
     */
    public Long newIdLongAndLock(String aClassName) throws LogicException ;


    public void releaseId(String aClassName, long aId) throws LogicException ;


    /**
     * Возвращает первый идентификатор объекта
     * @param aClassName имя класса Cache
     * @return первый идентификатор объекта
     */
    String getFirstId(String aClassName) throws LogicException;

    /**
     * Возвращает последний идентификатор объекта
     * @param aClassName имя класса Cache
     * @return последний идентификатор объекта
     */
    String getLastId(String aClassName) throws LogicException;

    /**
     * Возвращает последний идентификатор объекта
     * @param aClassName имя класса Cache
     * @param aVarList   проходные параметры
     * @return последний идентификатор объекта
     */
    String getLastId(String aClassName, String aVarList) throws LogicException;

    /**
     * Первый идентификатор объекта.
     * <h5>Пример:</h5>
     * <pre>
     * String firstId = logic.getFirstId("TmpKowResultRow", "%KowResultId:120") ;
     * </pre>
     * @param aClassName имя класса
     * @param aVarList   список переменных
     * @return первый идентификатор
     * @throws LogicException ошибка
     */
    String getFirstId(String aClassName, String aVarList) throws LogicException;

    /**
     * Возвращает первый идентификатор объекта
     * @param aClassName имя класса Cache
     * @return первый идентификатор объекта
     */
    long getFirstIdLong(String aClassName) throws LogicException, ParseException;

    /**
     * Возвращает следующий идентификатор
     * <h5>Например:</h5>
     * <pre>
     * String nextId = logic.getFirstId("TmpKowResultRow", "10405", "%KowResultId:120") ;
     * </pre>
     * @param aClassName имя класса Cache
     * @param aCurrentId текущий идентификатор
     * @param aVarList список переменных
     * @return следующий идентификатор объекта
     */
    String getNextId(String aClassName, String aCurrentId, String aVarList) throws LogicException;

    /**
     * Возвращает следующий идентификатор
     * <h5>Например:</h5>
     * <pre>
     * String nextId = logic.getNextId("TmpKowResultRow", "10405") ;
     * </pre>
     * @param aClassName имя класса Cache
     * @param aCurrentId текущий идентификатор
     * @return следующий идентификатор объекта
     */
    String getNextId(String aClassName, String aCurrentId) throws LogicException;

    /**
     * Возвращает предыдущий идентификатор
     * <h5>Например:</h5>
     * <pre>
     * String previousId = logic.getPreviousId("TmpKowResultRow", "10405") ;
     * </pre>
     * @param aClassName имя класса Cache
     * @param aCurrentId текущий идентификатор
     * @return предыдущий идентификатор объекта
     */
    String getPreviousId(String aClassName, String aCurrentId) throws LogicException;

    /**
     * Возвращает предыдущий идентификатор
     * <h5>Например:</h5>
     * <pre>
     * String previousId = logic.getPreviousId("Voc", id, "Voc:^ZVocStac(\"VocStacStreet\")") ;
     * </pre>
     * @param aClassName имя класса Cache
     * @param aCurrentId текущий идентификатор
     * @param aVarList   проходные переменные
     * @return предыдущий идентификатор объекта
     */
    String getPreviousId(String aClassName, String aCurrentId, String aVarList) throws LogicException;


    /**
     * Возвращает обратно в пул объектов ServerLogic
     */
    void close() throws LogicException;

    /**
     * Существует ли такой объект.
     * <h5>Например:</h5>
     * <pre>boolean ok = logic.isExists("Pat", 1) ;</pre>
     *
     * @param aClassName название класса
     * @param aObjectId  идентфикататор объекта
     * @return true, если оъект с таким идентификатором существует
     */
    boolean isExists(String aClassName, long aObjectId) throws LogicException;

    /**
     * Существует ли такой объект.
     * <h5>Например:</h5>
     * <pre>boolean ok = logic.isExists("Pat", "1") ;</pre>
     *
     * @param aClassName название класса
     * @param aObjectId  идентфикататор объекта
     * @return true, если объект с таким идентификатором существует
     */
    boolean isExists(String aClassName, String aObjectId) throws LogicException;

    /**
     * Существует ли такой объект.
     * <h5>Например:</h5>
     * <pre>boolean ok = logic.isExists("StacSTATN", "1", "%God:2006") ;</pre>
     *
     * @param aClassName название класса
     * @param aObjectId  идентфикататор объекта
     * @param aVarList   проходные переменные
     * @return true, если объект с таким идентификатором существует
     */
    boolean isExists(String aClassName, String aObjectId, String aVarList) throws LogicException;

    /**
     * Установить свойтво у объекта (String).
     * <h5>Например:</h5>
     * <pre>logic.setString("Pat", 1, "Patname", "Иванов") ;</pre>
     *
     * @param aClassName название класса
     * @param aFieldName название поля
     * @param aObjectId  идентификатор объекта
     * @param aValue     значение
     * @throws LogicException ошибка
     */
    void setString(String aClassName, String aObjectId, String aFieldName, String aValue) throws LogicException;

    /**
     * Установить свойтво у объекта (String).
     * <h5>Например:</h5>
     * <pre>logic.setString("StacSTATN", "1", "Kod", "1", "%Year:2005") ;</pre>
     *
     * @param aClassName название класса
     * @param aFieldName название поля
     * @param aObjectId  идентификатор объекта
     * @param aValue     значение
     * @throws LogicException ошибка
     */
    void setString(String aClassName, String aObjectId, String aFieldName, String aValue, String aVarList) throws LogicException;

    /**
     * Установить параметры у объекта (Long).
     * <h5>Например:</h5>
     * <pre>logic.setVarList("DRUGACT", 1, "%IdPr:2005") ;</pre>
     *
     * @param aClassName название класса
     * @param aObjectId  идентификатор объекта
     * @param aVarList   параметры для установки
     * @throws LogicException ошибка
     */
    void setIndexByVarList(String aClassName, long aObjectId, String aVarList) throws LogicException;

    /**
     * Установить параметры у объекта (String).
     * <h5>Например:</h5>
     * <pre>logic.setVarList("DRUGACT", "1", "%IdPr:2005") ;</pre>
     *
     * @param aClassName название класса
     * @param aObjectId  идентификатор объекта
     * @param aVarList   параметры для установки
     * @throws LogicException ошибка
     */
    void setIndexByVarList(String aClassName, String aObjectId, String aVarList) throws LogicException;

    /**
     * Установить Boolean
     * @param  aClassName название класса
     * @param  aFieldName название поля
     * @param  aObjectId  идентификатор объекта
     * @param  aValue     значение
     * @throws LogicException ошибка
     */
    void setBoolean(String aClassName, String aObjectId, String aFieldName, Boolean aValue) throws LogicException ;

    /**
     * Установить Date
     * @param aClassName название класса
     * @param aFieldName название поля
     * @param aObjectId  идентификатор объекта
     * @param aValue     значение
     * @throws LogicException ошибка
     */
    void setDate(String aClassName, String aObjectId, String aFieldName, Date aValue) throws LogicException ;

    /**
     * Установить Long
     * @param aClassName название класса
     * @param aFieldName название поля
     * @param aObjectId  идентификатор объекта
     * @param aValue     значение
     * @throws LogicException ошибка
     */
    void setLong(String aClassName, String aObjectId, String aFieldName, java.lang.Long aValue) throws LogicException ;

    /**
     * Установить BigDecimal
     * @param aClassName название класса
     * @param aFieldName название поля
     * @param aObjectId  идентификатор объекта
     * @param aValue     значение
     * @throws LogicException ошибка
     */
    void setBigDecimal(String aClassName, String aObjectId, String aFieldName, BigDecimal aValue) throws LogicException ;

    /**
     * Получить значение свойтва у объекта (String).
     * <h5>Например:</h5>
     * <pre>String agpz = logic.getString("Pat", 1, "Patname") ;</pre>
     *
     * @param aClassName название класса
     * @param aObjectId  идентификатор объекта
     * @return значение свойтства
     */
    String getString(String aClassName, String aObjectId, String aFieldName) throws LogicException;


    /**
     * Получить значение свойтва у объекта (Boolean).
     * <h5>Например:</h5>
     * <pre>Boolean firstname = logic.getBoolean("StacPSN", "121", "PSNagpz") ;</pre>
     *
     * @param aClassName название класса
     * @param aObjectId  идентификатор объекта
     * @return значение свойcтва
     */
    Boolean getBoolean(String aClassName, String aObjectId, String aFieldName) throws LogicException;

    /**
     * Получить значение свойтва у объекта (Boolean).
     * <h5>Например:</h5>
     * <pre>Boolean agpz = logic.getBoolean("StacPSN", 121, "PSNagpz") ;</pre>
     *
     * @param aClassName название класса
     * @param aObjectId  идентификатор объекта
     * @return значение свойcтва
     */
    Boolean getBoolean(String aClassName, long aObjectId, String aFieldName) throws LogicException;

    /**
     * Получить значение свойтва у объекта (String).
     * <h5>Например:</h5>
     * <pre>String firstname = logic.getValue("Pat", 1, "Patname") ;</pre>
     *
     * @param aClassName название класса
     * @param aObjectId  идентификатор объекта
     * @param aVarList   список проходных индексов
     * @return значение свойтства
     */
    String getString(String aClassName, String aObjectId, String aFieldName, String aVarList) throws LogicException ;

    /**
     * Получить значение свойтва у объекта (String).
     * <h5>Например:</h5>
     * <pre>String firstname = logic.getValue("Pat", 1, "Patname") ;</pre>
     *
     * @param aClassName название класса
     * @param aObjectId  идентификатор объекта
     * @return значение свойтства
     */
    String getString(String aClassName, long aObjectId, String aFieldName) throws LogicException;

    /**
     * Получить значение свойтва у объекта (long).
     * <h5>Например:</h5>
     * <pre>long idCountry = logic.getValue("Pat", 1, "Country") ;</pre>
     *
     * @param aClassName название класса
     * @param aObjectId  идентификатор объекта
     * @return значение свойтства
     */
    Long getLong(String aClassName, long aObjectId, String aFieldName) throws NumberFormatException, LogicException;



    /**
     * Получить значение свойтва у объекта (long).
     * <h5>Например:</h5>
     * <pre>long idCountry = logic.getValue("Pat", 1, "Country") ;</pre>
     *
     * @param aClassName название класса
     * @param aObjectId  идентификатор объекта
     * @return значение свойтства
     */
    Long getLong(String aClassName, String aObjectId, String aFieldName) throws NumberFormatException, LogicException;


    /**
     * Получить значение свойтва у объекта (Date).
     * <h5>Например:</h5>
     * <pre>Date birthday = logic.getValue("Pat", 1, "Birthday") ;</pre>
     *
     * @param aClassName название класса
     * @param aObjectId  идентификатор объекта
     * @return значение свойтства
     */
    Date getDate(String aClassName, long aObjectId, String aFieldName) throws LogicException, ParseException;

    /**
     * Поиск с использованием ковшей.
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
     * @param aClassName имя класса
     * @param aQuery     запрос. Например: <em>SLSdatawip:=20030321</em>
     * @return выборку идентификаторов объектов
     */
    IKowResultSet kowFind(String aClassName, String aQuery) throws LogicException;

    /**
     * Удаление объекта
     * @param aClassName имя класса
     * @param aObjectId  идентификатор объекта
     * @throws LogicException ошибка
     */
    void delete(String aClassName, String aObjectId) throws LogicException ;

    /**
     * Удаление объекта
     * @param aClassName имя класса
     * @param aObjectId  идентификатор объекта
     * @param aVarList   список проходных индексов
     * @throws LogicException ошибка
     */
    void delete(String aClassName, String aObjectId, String aVarList) throws LogicException ;

    /**
     * Вызов метода класса
     * @param aClassName  имя класса
     * @param aMethodName имя метода
     * @param aVarList    список параметров
     * @return результат
     */
    String invokeStatic(String aClassName, String aMethodName, String aVarList) throws LogicException ;


    /**
     * Начать транзакцию
     */
    void txStart() throws LogicException ;

    /**
     * Подтвердить транзакцию
     */
    void txCommit() throws LogicException ;

    /**
     * Отменить транзакцию
     */
    void txRollback() throws LogicException ;

}

/**
 * Получение String-значения родительских (проходных) индексов.
 * Например.
 * <pre>String countryName = logic.getParentString("^VocStac", "Voc", "Stran", "name", 1) ;</pre>
 * Получает имя страны в справочнике стран, который был определен следующим образом:
 * <table border='1'><tr><td>VOCCOUNTRY</td><td>^ZVoc("Stran")#Справочник по странам мира##1105004909</td></tr></table>
 * Описание полей:
 * <table border='1'><tr><th>Название атрибута</th><th>Описание атрибута</th></tr>
 * <tr><td>VOCCOUNTRYkod</td><td>2##Код страны по общесоюз справочнику#1105004911</td>
 * <tr><td>VOCCOUNTRYname</td><td>1##Наим. страны#1105004910</td></tr>
 * </table>
 *
 * @param aGlobaleName имя глобала
 * @param aParentField имя родительской переменной
 * @param aParentValue значение родительской переменной
 * @param aFieldName   имя поля
 * @param aIndex       индекс
 * @return значение
 */
//String getParentString(String aGlobaleName, String aParentField, String aParentValue, String aFieldName, long aIndex) throws LogicException;

/**
 * Получение long-значения родительских (проходных) индексов.
 * Например.
 * <pre>String countryCode = logic.getParentString("^VocStac", "Voc", "Stran", "kod", 1) ;</pre>
 * @param aGlobaleName имя глобала
 * @param aParentField имя родительской переменной
 * @param aParentValue значение родительской переменной
 * @param aFieldName   имя поля
 * @param aIndex       индекс
 * @return значение
 * @see #getParentString(String, String, String, String, long)
 */
//long getParentLong(String aGlobaleName, String aParentField, String aParentValue, String aFieldName, long aIndex) throws LogicException;

