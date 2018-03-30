package ru.ecom.expert2.domain;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.expert2.domain.voc.VocE2MedSpec;
import ru.ecom.mis.ejb.domain.worker.voc.VocWorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class VocWorkFunctionMedSpec  extends BaseEntity{
    /** Справочник рабочих функций */
    @Comment("Справочник рабочих функций")
    @OneToOne
    public VocWorkFunction getWorkFunction() {return theWorkFunction;}
    public void setWorkFunction(VocWorkFunction aWorkFunction) {theWorkFunction = aWorkFunction;}
    /** Справочник рабочих функций */
    private VocWorkFunction theWorkFunction ;

    /** Медицинская специалность */
    @Comment("Медицинская специалность")
    @OneToOne
    public VocE2MedSpec getSpec() {return theSpec;}
    public void setSpec(VocE2MedSpec aSpec) {theSpec = aSpec;}
    /** Медицинская специалность */
    private VocE2MedSpec theSpec ;
}
