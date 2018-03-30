package ru.ecom.expert2.domain;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.expomc.ejb.domain.med.VocKsg;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
public class E2KsgCoefficientHistory extends BaseEntity {

    /** КСГ */
    @Comment("КСГ")
    @OneToOne
    public VocKsg getKsg() {return theKsg;}
    public void setKsg(VocKsg aKsg) {theKsg = aKsg;}
    /** КСГ */
    private VocKsg theKsg ;

    /** Дата начала действия */
    @Comment("Дата начала действия")
    public Date getStartDate() {return theStartDate;}
    public void setStartDate(Date aStartDate) {theStartDate = aStartDate;}
    /** Дата начала действия */
    private Date theStartDate ;

    /** Дата окончания действия */
    @Comment("Дата окончания действия")
    public Date getFinishDate() {return theFinishDate;}
    public void setFinishDate(Date aFinishDate) {theFinishDate = aFinishDate;}
    /** Дата окончания действия */
    private Date theFinishDate ;

    /** Управляющий коэффициент */
    @Comment("Управляющий коэффициент")
    @Column( precision = 8, scale = 5 )
    public BigDecimal getValue() {return theValue;}
    public void setValue(BigDecimal aValue) {theValue = aValue;}
    /** Управляющий коэффициент */
    private BigDecimal theValue;
}
