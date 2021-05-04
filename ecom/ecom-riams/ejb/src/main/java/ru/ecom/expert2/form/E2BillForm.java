package ru.ecom.expert2.form;

import lombok.Getter;
import lombok.Setter;
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
@Setter
public class E2BillForm extends IdEntityForm {

    /** Номер счета */
    @Comment("Номер счета")
    @Persist @Required
    public String getBillNumber() {return billNumber;}
    private String billNumber ;

    /** Дата счета */
    @Comment("Дата счета")
    @Persist @Required
    @DateString @DoDateString
    public String getBillDate() {return billDate;}
    private String billDate ;

    /** Статус счета */
    @Comment("Статус счета")
    @Persist @Required
    public Long getStatus() {return status;}
    private Long status ;

    /** Страховая компания */
    @Comment("Страховая компания")
    @Persist
    public Long getCompany() {return company;}
    private Long company ;

    /** Сумма счета */
    @Comment("Сумма счета")
    @Persist
    public String getSum() {return sum;}
    private String sum ;

    /** Примечание к счету */
    @Comment("Примечание к счету")
    @Persist
    public String getComment() {return comment;}
    private String comment ;

    /** Назначение счета */
    @Comment("Назначение счета")
    @Persist
    public String getBillProperty() {return billProperty;}
    private String billProperty ;
}
