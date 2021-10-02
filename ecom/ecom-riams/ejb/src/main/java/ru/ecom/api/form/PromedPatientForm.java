package ru.ecom.api.form;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PromedPatientForm {

    private String lastName;
    private String firstName;
    private String middleName;
    private LocalDate birthDate;
    private String snils;
}
