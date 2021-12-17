package ru.ecom.api.form;

import lombok.*;

import java.util.List;
import java.util.UUID;

/**
 * Поликлинический случай мед. обслуживания (аналог PolyclinicMedCase)
 */
@Data
@Builder
@ToString
public class PromedPolyclinicTapForm extends BasePromedForm {
    private UUID guid;
    private PromedPatientForm patient;
    List<PromedPolyclinicVisitForm> visits;
    private PromedDiagnosis diagnosis;
    private String ticketNumber; //EvnPL_NumCard
    private Boolean isFinished; //EvnPL_IsFinish
    private Boolean isEmergency;
    private String promedCode; //ИД ТАП в промеде
    private String serviceStream; //поток обслуживания
    /**
     * Код направившей ЛПУ (6 значный код ОМС)
     */
    private String directLpuCode;

    /**
     * результат случая
     */
    private String tapResult;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TapList {
        private List<PromedPolyclinicTapForm> taps;
    }

}
