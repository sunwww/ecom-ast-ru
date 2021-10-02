package ru.ecom.api.form;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Поликлинический случай мед. обслуживания (аналог PolyclinicMedCase)
 */
@Data
@Builder
@ToString
public class PromedPolyclinicTapForm extends BasePromedForm {
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
