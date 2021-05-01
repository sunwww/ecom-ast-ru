package ru.ecom.expert2.domain.price;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.expert2.domain.voc.federal.VocE2FondV016;
import ru.ecom.mis.ejb.domain.patient.voc.VocSex;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Entity
@Getter
@Setter
/*Спрввочник цен для доп. диспансеризации */
public class ExtDispPrice extends BaseEntity {

    /** Тип диспансеризации */
    @Comment("Тип диспансеризации")
    @OneToOne
    public VocE2FondV016 getDispType() {return dispType;}
    private VocE2FondV016 dispType;

    /** Пол */
    @Comment("Пол")
    @OneToOne
    public VocSex getSex() {return sex;}
    private VocSex sex;

    /** Цена полного случая */
    @Comment("Цена полного случая")
    @Column( precision = 15, scale = 5 )
    public BigDecimal getCost() {return cost;}
    private BigDecimal cost;

    /** Возраст с (мес) */
    private Integer ageFrom;

    /** Возраст по (мес) */
    private Integer ageTo;

    /** Ценовая (социальная) группа */
    private Integer ageGroup;

    /** Дата начала действия цены */
    private Date dateFrom;

    /** Дата окончания действия */
    private Date dateTo;

    /** Возраста  */
    private String ages;

    /** Услуги по цене */
    @Comment("Услуги по цене")
    @OneToMany(mappedBy = "price", cascade = CascadeType.ALL)
    public List<ExtDispPriceMedService> getServiceList() {return serviceList;}
    private List<ExtDispPriceMedService> serviceList;

    /** Минимальное кол-во услуг */
    private Integer minServices;
}