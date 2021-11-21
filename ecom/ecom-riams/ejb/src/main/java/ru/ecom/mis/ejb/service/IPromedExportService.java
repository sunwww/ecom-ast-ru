package ru.ecom.mis.ejb.service;

import ru.ecom.mis.ejb.domain.medcase.PolyclinicMedCase;

public interface IPromedExportService {

    /**
     * Экспорт поликлинического случая в риамс промед
     * @param medCase PolyclinicMedCase
     * @return ГУИД запроса
     */
    String exportPolyclinic(PolyclinicMedCase medCase);

    /**
     * Экспорт поликлинического случая в риамс промед
     * @param medCaseId - ИД СМО (PolyclinicMedCase)
     * @return ГУИД запроса
     */
    String exportPolyclinicById(Long medCaseId);

    /**
     *  Получить журнал экспорта по ИД СПО
     * @param spoId
     * @return
     */
    String getJournalToPromed(Long spoId);
}
