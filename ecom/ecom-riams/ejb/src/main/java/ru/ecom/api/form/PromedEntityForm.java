package ru.ecom.api.form;

import lombok.Data;

@Data
public class PromedEntityForm extends BasePromedForm {

    private String promedCode; //ИД в промеде
    private Long medosId;
    private String serviceStream; //поток ослуживания
}
