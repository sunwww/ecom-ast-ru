package ru.ecom.api.form;

import lombok.Data;

import java.util.List;

/**
 * Поликлинический случай мед. обслуживания (аналог PolyclinicMedCase)
 */
@Data
public class PromedPolyclinicTapForm {
    private PromedPatientForm patient;
    List<PromedPolyclinicVisitForm> visits;
    private PromedDiagnosis diagnosis;
    private String ticketNumber; //EvnPL_NumCard
    private Boolean isFinished; //EvnPL_IsFinish

    /**
     * результат случая
     */
    private String tapResult;

}
