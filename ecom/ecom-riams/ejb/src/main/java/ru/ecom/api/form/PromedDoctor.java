package ru.ecom.api.form;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class PromedDoctor extends BasePromedForm {
    /**
     * Код специальности. по какому справочнику //TODO
     */
//    private String workfunctionCode;
    private Long promedWorkstaffId;
    private String lastName;
    private String firstName;
    private String middleName;
    private String snils;
}
