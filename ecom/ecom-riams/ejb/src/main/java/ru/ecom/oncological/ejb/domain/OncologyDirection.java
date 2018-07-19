package ru.ecom.oncological.ejb.domain;


import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.medcase.voc.VocMedService;
import ru.ecom.oncological.ejb.domain.voc.VocOncologyMethodDiagTreat;
import ru.ecom.oncological.ejb.domain.voc.VocOncologyTypeDirection;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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

    @Comment("Случай окологического лечения")
    @OneToOne
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
}
