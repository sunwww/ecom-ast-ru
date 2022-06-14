package ru.ecom.mis.ejb.service.promed;

import ru.ecom.api.form.PromedParaclinicForm;
import ru.ecom.api.form.PromedPolyclinicTapForm;
import ru.ecom.mis.ejb.domain.medcase.PolyclinicMedCase;
import ru.ecom.mis.ejb.domain.medcase.Visit;
import ru.nuzmsh.forms.validator.ValidateException;

public interface IPromedExportService {

    /**
     * Маппим наш СПО на ДТО для промедатора
     *
     * @param polyclinicCase
     * @return
     */
    PromedPolyclinicTapForm getPolyclinicCase(PolyclinicMedCase polyclinicCase);

    /**
     * Экспорт поликлинического случая в риамс промед
     *
     * @param medCaseId - ИД СМО (PolyclinicMedCase)
     * @return ГУИД запроса
     */
    String exportPolyclinicById(Long medCaseId) throws ValidateException;

    /**
     * Экспорт стационарного случая в риамс промед
     *
     * @param hospitalMedCaseId - ИД СЛС (HospitalMedCase)
     * @return ГУИД запроса
     */
    String exportHospitalById(Long hospitalMedCaseId) throws ValidateException;

    /**
     * Получить журнал экспорта по ИД СПО
     *
     * @param spoId
     * @return
     */
    String getJournalToPromed(Long spoId);

    PromedParaclinicForm getParaclinicCase(Visit shortMedCase);
}
