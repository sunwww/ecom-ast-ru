package ru.ecom.expomc.ejb.services.form.format;

/**
 * @author esinev
 * Date: 22.08.2006
 * Time: 12:15:07
 */
public interface IFormatService {


    public void removeFieldsWithEmptyProperty(long aFormatId) ;

    /**
     * Добавляет поля из таблицы Word
     * @param aFormatId
     * @param aText
     */
    void addFieldFromWord(long aFormatId, String aText) ;


    /**
     * Получение документа для формата
     * @param aFormatId ид. формата
     * @return ид документа
     */
    long getDocumentForFormat(long aFormatId) ;


    PropertySuggest findPropertySuggest(String aFieldname, long aFormatId) throws ClassNotFoundException ;

}
