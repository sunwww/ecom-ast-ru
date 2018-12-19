package ru.ecom.expert2.web.form;

import ru.ecom.jaas.web.action.policy.ExportPolicyForm;
import ru.nuzmsh.forms.validator.validators.DateString;

public class ImportFileForm extends ExportPolicyForm {
        /** Номер счета */
  public String getBillNumber() { return theBillNumber;}
  public void setBillNumber(String aBillNumber) { theBillNumber = aBillNumber;}
  private String theBillNumber ;

    /** Дата счета */
    @DateString
  public String getBillDate() { return theBillDate;}
  public void setBillDate(String aBillDate) { theBillDate = aBillDate;}
  private String theBillDate;
}