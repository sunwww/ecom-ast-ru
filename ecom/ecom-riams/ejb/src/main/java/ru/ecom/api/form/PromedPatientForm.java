package ru.ecom.api.form;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class PromedPatientForm extends BasePromedForm {

    private String lastName;
    private String firstName;
    private String middleName;
    private Date birthDate;
    private String snils;
}
