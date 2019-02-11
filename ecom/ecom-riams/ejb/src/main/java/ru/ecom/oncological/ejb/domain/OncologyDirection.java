package ru.ecom.oncological.ejb.domain;


import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.medcase.voc.VocMedService;
import ru.ecom.oncological.ejb.domain.voc.VocOncologyMethodDiagTreat;
import ru.ecom.oncological.ejb.domain.voc.VocOncologyTypeDirection;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;

/**
 * Направление
 * @author rkurbanov
 */
@Entity
@Table(schema="SQLUser")
public class OncologyDirection extends BaseEntity {

    /** Случай окологического лечения */
    private OncologyCase oncologyCase;
    /** Метод диагностического лечения */
    private VocOncologyMethodDiagTreat methodDiagTreat;
    /** Вид направления */
    private VocOncologyTypeDirection typeDirection;
    /** мед услуга по V001 */
    private VocMedService medService;
    /** дата направления */
    private Date date;

    @Comment("Случай окологического лечения")
    @ManyToOne(fetch = FetchType.LAZY)
    public OncologyCase getOncologyCase() {
        return oncologyCase;
    }
    public void setOncologyCase(OncologyCase oncologyCase) {
        this.oncologyCase = oncologyCase;
    }

    @Comment("Метод диагностического лечения")
    @OneToOne
    public VocOncologyMethodDiagTreat getMethodDiagTreat() {
        return methodDiagTreat;
    }
    public void setMethodDiagTreat(VocOncologyMethodDiagTreat methodDiagTreat) {
        this.methodDiagTreat = methodDiagTreat;
    }

    @Comment("Вид направления")
    @OneToOne
    public VocOncologyTypeDirection getTypeDirection() {
        return typeDirection;
    }
    public void setTypeDirection(VocOncologyTypeDirection typeDirection) {
        this.typeDirection = typeDirection;
    }

    @Comment("мед услуга по V001")
    @OneToOne
    public VocMedService getMedService() {
        return medService;
    }
    public void setMedService(VocMedService medService) {
        this.medService = medService;
    }

    @Comment("Дата направления")
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    /** ЛПУ, куда сделано направление */
    @Comment("ЛПУ, куда сделано направление")
    @OneToOne
    public MisLpu getDirectLpu() {return theDirectLpu;}
    public void setDirectLpu(MisLpu aDirectLpu) {theDirectLpu = aDirectLpu;}
    /** ЛПУ, куда сделано направление */
    private MisLpu theDirectLpu ;
}
