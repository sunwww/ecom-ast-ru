package ru.ecom.api.form;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class PromedDiagnosis extends BasePromedForm {
    /**
     * Код МКБ
     */
    private String mkbCode;

    /**
     * Справочник остроты диагноза (1 -острое, 2- впервые установленное, 3- хроническое)
     */
    private String acuity; //DeseaseType_id

    private String type; //фоновое, сопутствующее,...
    private LocalDate diagnosisDate; //Дата установления
    private String comment; //описание диагноза

    @Deprecated //TODO не использовать
    private String promedId;

    /**
     * Внешняя причина
     */
    private String diagReasonMkb;
}
