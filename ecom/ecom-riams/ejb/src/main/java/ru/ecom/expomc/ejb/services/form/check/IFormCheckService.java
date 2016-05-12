package ru.ecom.expomc.ejb.services.form.check;

import java.util.LinkedList;

/**
 * Работа с формой проверок
 */
public interface IFormCheckService {

    ChecksTableForm loadFormChecksTableForm(long aFormatId) ;

    LinkedList<Long> save(ChecksTableForm aForm, long aFormat) ;

    void deleteChecks(LinkedList<Long> aDeleted) ;

}
