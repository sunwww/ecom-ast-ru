package ru.ecom.expomc.ejb.services.check;

import java.util.Collection;
import java.util.Map;

import ru.ecom.expomc.ejb.services.form.check.CheckPropertyForm;

/**
 * Работа с проверками
 */
public interface ICheckService {

    void checkDocumentData(long aMonitorId, long aTime) throws CheckDocumentDataException ;

    Collection<CheckPropertyRow> listProperties(long aCheckId) throws ClassNotFoundException ;

    CheckPropertyForm loadForm(long aCheckId, String aProperty) throws ClassNotFoundException ;

    void saveForm(CheckPropertyForm aForm) throws ClassNotFoundException ;


    String checkByFormatHtml(String aFormatName, Map<String, Object> aMap) ;

}
