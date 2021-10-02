package ru.ecom.api.form;

import lombok.Builder;
import lombok.Data;

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

    @Deprecated //TODO не использовать
    private String promedId;
}
