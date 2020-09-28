package ru.ecom.expert2.domain;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.expert2.domain.voc.VocE2BillStatus;
import ru.ecom.expomc.ejb.domain.registry.RegInsuranceCompany;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
public class E2Bill extends BaseEntity {

    /** Номер счета */
    @Comment("Номер счета")
    public String getBillNumber() {return theBillNumber;}
    public void setBillNumber(String aBillNumber) {theBillNumber = aBillNumber;}
    private String theBillNumber ;

    /** Дата счета */
    @Comment("Дата счета")
    public Date getBillDate() {return theBillDate;}
    public void setBillDate(Date aBillDate) {theBillDate = aBillDate;}
    private Date theBillDate ;

    /** Статус счета */
    @Comment("Статус счета")
    @OneToOne
    public VocE2BillStatus getStatus() {return theStatus;}
    public void setStatus(VocE2BillStatus aStatus) {theStatus = aStatus;}
    private VocE2BillStatus theStatus ;

    /** Страховая компания */
    @Comment("Страховая компания")
    @OneToOne
    public RegInsuranceCompany getCompany() {return theCompany;}
    public void setCompany(RegInsuranceCompany aCompany) {theCompany = aCompany;}
    private RegInsuranceCompany theCompany ;

    /** Сумма счета */
    @Comment("Сумма счета")
    public BigDecimal getSum() {return theSum;}
    public void setSum(BigDecimal aSum) {theSum = aSum;}
    private BigDecimal theSum ;

    /** Примечание к счету */
    @Comment("Примечание к счету")
    public String getComment() {return theComment;}
    public void setComment(String aComment) {theComment = aComment;}
    private String theComment ;

    /** Назначение счета */
    @Comment("Назначение счета")
    public String getBillProperty() {return theBillProperty;}
    public void setBillProperty(String aBillProperty) {theBillProperty = aBillProperty;}
    private String theBillProperty ;
}
