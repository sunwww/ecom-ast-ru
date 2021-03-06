package ru.ecom.expert2.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.domain.E2Bill;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 *
 */
@EntityForm
@EntityFormPersistance(clazz = E2Bill.class)
@Comment("Счет")
@WebTrail(comment = "Счет", nameProperties = {"billNumber", "billDate"}, view = "entityView-e2_bill.do")
@EntityFormSecurityPrefix("/Policy/E2")
public class E2BillForm extends IdEntityForm {

    /** Номер счета */
    @Comment("Номер счета")
    @Persist @Required
    public String getBillNumber() {return theBillNumber;}
    public void setBillNumber(String aBillNumber) {theBillNumber = aBillNumber;}
    /** Номер счета */
    private String theBillNumber ;

    /** Дата счета */
    @Comment("Дата счета")
    @Persist @Required
    @DateString @DoDateString
    public String getBillDate() {return theBillDate;}
    public void setBillDate(String aBillDate) {theBillDate = aBillDate;}
    /** Дата счета */
    private String theBillDate ;

    /** Статус счета */
    @Comment("Статус счета")
    @Persist @Required
    public Long getStatus() {return theStatus;}
    public void setStatus(Long aStatus) {theStatus = aStatus;}
    /** Статус счета */
    private Long theStatus ;

    /** Страховая компания */
    @Comment("Страховая компания")
    @Persist
    public Long getCompany() {return theCompany;}
    public void setCompany(Long aCompany) {theCompany = aCompany;}
    /** Страховая компания */
    private Long theCompany ;

    /** Сумма счета */
    @Comment("Сумма счета")
    @Persist
    public String getSum() {return theSum;}
    public void setSum(String aSum) {theSum = aSum;}
    /** Сумма счета */
    private String theSum ;

}
