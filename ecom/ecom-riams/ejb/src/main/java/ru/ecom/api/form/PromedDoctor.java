package ru.ecom.api.form;

import lombok.Data;

@Data
public class PromedDoctor extends PromedPatientForm{
    /**
     * Код специальности. по какому справочнику //TODO
     */
    private String workfunctionCode;
    private Long promedWorkstaffId;// TODO постараться выпилить
}
