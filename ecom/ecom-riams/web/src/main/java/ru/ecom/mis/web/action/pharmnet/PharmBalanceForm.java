package ru.ecom.mis.web.action.pharmnet;

import ru.nuzmsh.forms.validator.BaseValidatorForm;

/**
 * Created by rkurbanov on 28.02.2018.
 */
public class PharmBalanceForm extends BaseValidatorForm {

    private Long vocStorage;
    public Long getVocStorage() {
        return vocStorage;
    }
    public void setVocStorage(Long vocStorage) {
        this.vocStorage = vocStorage;
    }
}
