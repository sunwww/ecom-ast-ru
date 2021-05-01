package ru.ecom.expert2.web.form;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.jaas.web.action.policy.ExportPolicyForm;
import ru.nuzmsh.forms.validator.validators.DateString;

@Getter
@Setter
public class ImportFileForm extends ExportPolicyForm {
        /** Номер счета */
  private String billNumber ;

    /** Дата счета */
    @DateString
  public String getBillDate() { return billDate;}
  private String billDate;

  private Long workFunctionId;
  private Long visitResultId;
  private Long visitReasonId;
  private Long primaryId;
  private Long workPlaceId;
  private Long mkbId;

}