package ru.ecom.oncological.ejb.domain;


import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.oncological.ejb.domain.voc.*;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;

/**
 * Диагностический блок
 * @author rkurbanov
 */
@Entity
@Table(schema="SQLUser")
public class OncologyDiagnostic extends BaseEntity {

    /**Случай онкологического лечения */
    private OncologyCase oncologyCase;
    /**Тип диагностического показателя*/
    private VocOncologyDiagType vocOncologyDiagType;
    /**Код диагностического показателя*/
    private VocOncologyN007 histiology;
    /**Код диагностического показателя*/
    private VocOncologyN010 markers;
    /**Код результата диагностики*/
    private VocOncologyN008 resultHistiology;
    /**Код результата диагностики*/
    private VocOncologyN011 valueMarkers;

    @Comment("Случай онкологического лечения")
    @ManyToOne(fetch = FetchType.LAZY)
    public OncologyCase getOncologyCase() {
        return oncologyCase;
    }
    public void setOncologyCase(OncologyCase oncologyCase) {
        this.oncologyCase = oncologyCase;
    }

    @Comment("Тип диагностического показателя")
    @OneToOne
    public VocOncologyDiagType getVocOncologyDiagType() {
        return vocOncologyDiagType;
    }
    public void setVocOncologyDiagType(VocOncologyDiagType vocOncologyDiagType) {
        this.vocOncologyDiagType = vocOncologyDiagType;
    }

    @Comment("Код диагностического показателя (если тип==1)")
    @OneToOne
    public VocOncologyN007 getHistiology() {
        return histiology;
    }
    public void setHistiology(VocOncologyN007 histiology) {
        this.histiology = histiology;
    }

    @Comment("Код диагностического показателя (если тип==2)")
    @OneToOne
    public VocOncologyN010 getMarkers() {
        return markers;
    }
    public void setMarkers(VocOncologyN010 markers) {
        this.markers = markers;
    }

    @Comment("Код результата диагностики (если тип==1)")
    @OneToOne
    public VocOncologyN008 getResultHistiology() {
        return resultHistiology;
    }
    public void setResultHistiology(VocOncologyN008 resultHistiology) {
        this.resultHistiology = resultHistiology;
    }

    @Comment("Код результата диагностики (если тип==2)")
    @OneToOne
    public VocOncologyN011 getValueMarkers() {
        return valueMarkers;
    }
    public void setValueMarkers(VocOncologyN011 valueMarkers) {
        this.valueMarkers = valueMarkers;
    }
}
