package ru.ecom.expert2.domain.voc;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.expert2.domain.voc.federal.VocE2FondV021;
import ru.ecom.mis.ejb.domain.medcase.voc.VocMedService;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@NamedQueries({
        @NamedQuery( name="VocOmcMedServiceCost.getByCodeAndDate"
                , query="from VocOmcMedServiceCost where medService.code=:code and (finishDate is null or finishDate>=:finishDate)")
})
public class VocOmcMedServiceCost extends BaseEntity {

    /** Услуга */
    @Comment("Услуга")
    @OneToOne
    public VocMedService getMedService() {return theMedService;}
    public void setMedService(VocMedService aMedService) {theMedService = aMedService;}
    private VocMedService theMedService ;

    /** Цена */
    @Comment("Цена")
    public BigDecimal getCost() {return theCost;}
    public void setCost(BigDecimal aCost) {theCost = aCost;}
    private BigDecimal theCost ;

    /** Дата начала действия цены */
    @Comment("Дата начала действия цены")
    public Date getStartDate() {return theStartDate;}
    public void setStartDate(Date aStartDate) {theStartDate = aStartDate;}
    private Date theStartDate ;

    /** Дата окончания действия цены */
    @Comment("Дата окончания действия цены")
    public Date getFinishDate() {return theFinishDate;}
    public void setFinishDate(Date aFinishDate) {theFinishDate = aFinishDate;}
    private Date theFinishDate ;

    /** Рабочая функция врача по умолчанию */
    @Comment("Рабочая функция врача по умолчанию")
    @OneToOne
    public VocE2FondV021 getWorkFunction() {return theWorkFunction;}
    public void setWorkFunction(VocE2FondV021 aWorkFunction) {theWorkFunction = aWorkFunction;}
    private VocE2FondV021 theWorkFunction ;
}
