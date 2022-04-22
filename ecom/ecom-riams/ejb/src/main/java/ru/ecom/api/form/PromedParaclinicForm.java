package ru.ecom.api.form;

import lombok.*;

import java.util.List;
import java.util.UUID;

/**
 * Случай госпитализации
 */
@Data
@Builder
@ToString

@NoArgsConstructor
@AllArgsConstructor
public class PromedParaclinicForm extends PromedEntityForm {
    private UUID guid;
    private String internalSpoId;
    private PromedPatientForm patient;
    private String startDate; //Дата и время начала выполнения услуги
    private String finishDate; //Дата и время окончания выполнения услуги
    private List<PromedMedService> medServices;
    private String diary;
}
