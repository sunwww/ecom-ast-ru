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

  private Long workFunctionId;
  private Long visitResultId;
  private Long visitReasonId;
  private Long primaryId;
  private Long workPlaceId;
  private Long mkbId;

  public Long getMkbId() {
    return mkbId;
  }

  public void setMkbId(Long mkbId) {
    this.mkbId = mkbId;
  }

  public Long getWorkFunctionId() {
    return workFunctionId;
  }

  public void setWorkFunctionId(Long workFunctionId) {
    this.workFunctionId = workFunctionId;
  }

  public Long getVisitResultId() {
    return visitResultId;
  }

  public void setVisitResultId(Long visitResultId) {
    this.visitResultId = visitResultId;
  }

  public Long getVisitReasonId() {
    return visitReasonId;
  }

  public void setVisitReasonId(Long visitReasonId) {
    this.visitReasonId = visitReasonId;
  }

  public Long getPrimaryId() {
    return primaryId;
  }

  public void setPrimaryId(Long primaryId) {
    this.primaryId = primaryId;
  }

  public Long getWorkPlaceId() {
    return workPlaceId;
  }

  public void setWorkPlaceId(Long workPlaceId) {
    this.workPlaceId = workPlaceId;
  }
}