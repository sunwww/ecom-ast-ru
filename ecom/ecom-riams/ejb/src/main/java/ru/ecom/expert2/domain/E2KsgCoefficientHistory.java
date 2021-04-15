package ru.ecom.expert2.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.expomc.ejb.domain.med.VocKsg;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Getter
@Setter
@Accessors(prefix = "the")
public class E2KsgCoefficientHistory extends BaseEntity {

    /** КСГ */
    @OneToOne
    private VocKsg theKsg ;

    /** Дата начала действия */
    private Date theStartDate ;

    /** Дата окончания действия */
    private Date theFinishDate ;

    /** Управляющий коэффициент */
    @Column( precision = 8, scale = 5 )
    private BigDecimal theValue;
}
