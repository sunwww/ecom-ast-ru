package ru.ecom.expert2.domain.price;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.expert2.domain.voc.federal.VocE2FondV016;
import ru.ecom.mis.ejb.domain.patient.voc.VocSex;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.sql.Date;

@Entity

/*Спрввочник цен для доп. диспансеризации */
public class ExtDispPrice extends BaseEntity {

    /** Тип диспансеризации */
    @Comment("Тип диспансеризации")
    @OneToOne
    public VocE2FondV016 getDispType() {return theDispType;}
    public void setDispType(VocE2FondV016 aDispType) {theDispType = aDispType;}
    /** Тип диспансеризации */
    private VocE2FondV016 theDispType ;

    /** Пол */
    @Comment("Пол")
    @OneToOne
    public VocSex getSex() {return theSex;}
    public void setSex(VocSex aSex) {theSex = aSex;}
    /** Пол */
    private VocSex theSex ;

    /** Цена полного случая */
    @Comment("Цена полного случая")
    @Column( precision = 15, scale = 5 )
    public BigDecimal getCost() {return theCost;}
    public void setCost(BigDecimal aCost) {theCost = aCost;}
    /** Цена полного случая */
    private BigDecimal theCost ;

    /** Возраста  */
    @Comment("Возраста ")
    public String getAges() {return theAges;}
    public void setAges(String aAges) {theAges = aAges;}
    /** Возраста  */
    private String theAges ;

    /** Ценовая (социальная) группа */
    @Comment("Ценовая (социальная) группа")
    public Integer getAgeGroup() {return theAgeGroup;}
    public void setAgeGroup(Integer aAgeGroup) {theAgeGroup = aAgeGroup;}
    /** Ценовая (социальная) группа */
    private Integer theAgeGroup ;

    /** Дата начала действия цены */
    @Comment("Дата начала действия цены")
    public Date getDateFrom() {return theDateFrom;}
    public void setDateFrom(Date aDateFrom) {theDateFrom = aDateFrom;}
    /** Дата начала действия цены */
    private Date theDateFrom ;

    /** Дата окончания действия */
    @Comment("Дата окончания действия")
    public Date getDateTo() {return theDateTo;}
    public void setDateTo(Date aDateTo) {theDateTo = aDateTo;}
    /** Дата окончания действия */
    private Date theDateTo ;


}
