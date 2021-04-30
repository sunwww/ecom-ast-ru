package ru.ecom.expert2.domain;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.expert2.domain.voc.VocE2BillStatus;
import ru.ecom.expomc.ejb.domain.registry.RegInsuranceCompany;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Getter
@Setter
public class E2Bill extends BaseEntity {

    /** Номер счета */
    private String billNumber;

    /** Дата счета */
    private Date billDate;

    /** Статус счета */
    @Comment("Статус счета")
    @OneToOne
    public VocE2BillStatus getStatus() {return status;}
    private VocE2BillStatus status;

    /** Страховая компания */
    @Comment("Страховая компания")
    @OneToOne
    public RegInsuranceCompany getCompany() {return company;}
    private RegInsuranceCompany company;

    /** Сумма счета */
    @Comment("Сумма счета")
    public BigDecimal getSum() {return sum;}
    private BigDecimal sum;

    /** Примечание к счету */
    private String comment;

    /** Назначение счета */
    private String billProperty;
}
