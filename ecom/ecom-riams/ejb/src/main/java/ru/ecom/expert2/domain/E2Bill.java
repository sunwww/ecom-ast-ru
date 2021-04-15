package ru.ecom.expert2.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
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
@Accessors(prefix = "the")
public class E2Bill extends BaseEntity {

    /** Номер счета */
    private String theBillNumber ;

    /** Дата счета */
    private Date theBillDate ;

    /** Статус счета */
    @OneToOne
    private VocE2BillStatus theStatus ;

    /** Страховая компания */
    @OneToOne
    private RegInsuranceCompany theCompany ;

    /** Сумма счета */
    private BigDecimal theSum ;

    /** Примечание к счету */
    private String theComment ;

    /** Назначение счета */
    private String theBillProperty ;
}
