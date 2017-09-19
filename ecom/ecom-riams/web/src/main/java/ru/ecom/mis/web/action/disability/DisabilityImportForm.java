package ru.ecom.mis.web.action.disability;

import ru.nuzmsh.forms.validator.BaseValidatorForm;

/**
 * Created by rkurbanov on 01.09.2017.
 */
public class DisabilityImportForm extends BaseValidatorForm {
    private String elnNumber;
    private String snils;
    private String ogrn;
    private String patient_id;

    public String getSnils() {
        return snils;
    }
    public void setSnils(String snils) {
        this.snils = snils;
    }

    public String getOgrn() {
        return ogrn;
    }
    public void setOgrn(String ogrn) {
        this.ogrn = ogrn;
    }

    public String getPatient_id() {
        return patient_id;
    }
    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public String getElnNumber() {
        return elnNumber;
    }
    public void setElnNumber(String elnNumber) {
        this.elnNumber = elnNumber;
    }
}
