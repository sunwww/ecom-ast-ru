package ru.ecom.mis.ejb.service;

import ru.ecom.api.form.PromedPolyclinicTapForm;
import ru.ecom.mis.ejb.domain.medcase.PolyclinicMedCase;

public interface IPromedExportService {

    /**
     * Экспорт поликлинического случая в риамс промед
     * @param medCase PolyclinicMedCase
     * @return ГУИД запроса
     */
    String exportPolyclinic(PolyclinicMedCase medCase);

    /**
     * Маппим наш СПО на ДТО для промедатора
     * @param polyclinicCase
     * @return
     */
    PromedPolyclinicTapForm getPolyclinicCase(PolyclinicMedCase polyclinicCase);

    /**
     * Экспорт поликлинического случая в риамс промед
     * @param medCaseId - ИД СМО (PolyclinicMedCase)
     * @return ГУИД запроса
     */
    String exportPolyclinicById(Long medCaseId);

    /**
     * Экспорт поликлинического случая в риамс промед
     * @param hospitalMedCaseId - ИД СЛС (HospitalMedCase)
     * @return ГУИД запроса
     */
    String exportHospitalById(Long hospitalMedCaseId);

    /**
     *  Получить журнал экспорта по ИД СПО
     * @param spoId
     * @return
     */
    String getJournalToPromed(Long spoId);
}
