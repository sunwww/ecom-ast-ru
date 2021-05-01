package ru.ecom.expert2.domain;

import lombok.Getter;
import lombok.Setter;
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
public class E2KsgCoefficientHistory extends BaseEntity {

    /** КСГ */
    @OneToOne
    public VocKsg getKsg() {return ksg;}
    private VocKsg ksg ;

    /** Дата начала действия */
    private Date startDate ;

    /** Дата окончания действия */
    private Date finishDate ;

    /** Управляющий коэффициент */
    @Comment("Управляющий коэффициент")
    @Column( precision = 8, scale = 5 )
    public BigDecimal getValue() {return value;}
    private BigDecimal value;
}
