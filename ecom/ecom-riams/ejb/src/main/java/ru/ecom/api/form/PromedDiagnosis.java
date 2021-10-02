package ru.ecom.api.form;

import lombok.Data;

@Data
public class PromedDiagnosis {
    /**
     * Код МКБ
     */
    private String mkbCode;

    /**
     * Справочник остроты диагноза (1 -острое, 2- впервые установленное, 3- хроническое)
     */
    private String acuity; //DeseaseType_id

    @Deprecated //TODO не использовать
    private Long promedId;
}
