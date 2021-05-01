package ru.ecom.expert2.domain.voc;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class VocOmcMedServiceCost extends BaseEntity {

    /** Услуга */
    @Comment("Услуга")
    @OneToOne
    public VocMedService getMedService() {return medService;}
    private VocMedService medService;

    /** Цена */
    private BigDecimal cost;

    /** Дата начала действия цены */
    private Date startDate;

    /** Дата окончания действия цены */
    private Date finishDate;

    /** Рабочая функция врача по умолчанию */
    @Comment("Рабочая функция врача по умолчанию")
    @OneToOne
    public VocE2FondV021 getWorkFunction() {return workFunction;}
    private VocE2FondV021 workFunction;
}
