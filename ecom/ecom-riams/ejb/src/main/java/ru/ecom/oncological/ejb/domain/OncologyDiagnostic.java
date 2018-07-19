package ru.ecom.oncological.ejb.domain;


import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.oncological.ejb.domain.voc.*;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
    private VocHistiology_N007 histiology;
    /**Код диагностического показателя*/
    private VocMarkers_N010 markers;
    /**Код результата диагностики*/
    private VocResultHistiology_N008 resultHistiology;
    /**Код результата диагностики*/
    private VocValueMarkers_N011 valueMarkers;

    @Comment("Случай онкологического лечения")
    @OneToOne
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
    public VocHistiology_N007 getHistiology() {
        return histiology;
    }
    public void setHistiology(VocHistiology_N007 histiology) {
        this.histiology = histiology;
    }

    @Comment("Код диагностического показателя (если тип==2)")
    @OneToOne
    public VocMarkers_N010 getMarkers() {
        return markers;
    }
    public void setMarkers(VocMarkers_N010 markers) {
        this.markers = markers;
    }

    @Comment("Код результата диагностики (если тип==1)")
    @OneToOne
    public VocResultHistiology_N008 getResultHistiology() {
        return resultHistiology;
    }
    public void setResultHistiology(VocResultHistiology_N008 resultHistiology) {
        this.resultHistiology = resultHistiology;
    }

    @Comment("Код результата диагностики (если тип==2)")
    @OneToOne
    public VocValueMarkers_N011 getValueMarkers() {
        return valueMarkers;
    }
    public void setValueMarkers(VocValueMarkers_N011 valueMarkers) {
        this.valueMarkers = valueMarkers;
    }
}
