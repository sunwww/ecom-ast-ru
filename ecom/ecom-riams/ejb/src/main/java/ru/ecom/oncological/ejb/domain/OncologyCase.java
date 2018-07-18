package ru.ecom.oncological.ejb.domain;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.oncological.ejb.domain.voc.*;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 * Cлучай лечения онк. заболевания
 * @author rkurbanov
 */
@Entity
@Table(schema="SQLUser")
public class OncologyCase extends BaseEntity {

    private MedCase medCase;

    /**Сведения о случае лечения онкологического заболевания */
    /**Повод обращения*/
    private VocOncologyReasonTreat vocOncologyReasonTreat;
    /**Стадия заболевания*/
    private VocStad_N002 stad;
    /**Значение Tumor*/
    private VocTumor_N003 tumor;
    /**Значение Nodus*/
    private VocNodus_N004 nodus;
    /**Значение Metastasis_*/
    private VocMetastasis_N005 metastasis;
    /**Признак отдаленных метастазов*/
    private Boolean isDistantMetastasis;
    /**Суммарная очаговая доза*/
    private String sumDose;
    /**Подозрение на онкологию*/
    private Boolean isSuspicionOncologist;

    /**Сведения об услуге при онкологического заболевания */
    /**Сведения о проведении консилиума*/
    private VocOncologyConsilium consilium;
    /**Тип услуги*/
    private VocTypeTreatment_N013 typeTreatment;
    /**Тип хирургического лечения*/
    private VocTypeSurgTreatment_N014 surgTreatment;
    /**Линия лекарственной терамии*/
    private VocLineDrugTherapy_N015 lineDrugTherapy;
    /**Цикл лекарственной терапии*/
    private VocCycleDrugTherapy_N016 cycleDrugTherapy;
    /**Тип лучевой терапии*/
    private VocTypeRadTherapy_N017 typeRadTherapy;

    @Comment("Подозрение на онкологию")
    public Boolean getSuspicionOncologist() {
        return isSuspicionOncologist;
    }
    public void setSuspicionOncologist(Boolean suspicionOncologist) {
        isSuspicionOncologist = suspicionOncologist;
    }

    @Comment("Случай лечения")
    @OneToOne
    public MedCase getMedCase() {
        return medCase;
    }
    public void setMedCase(MedCase medCase) {
        this.medCase = medCase;
    }

    @Comment("Повод обращения")
    @OneToOne
    public VocOncologyReasonTreat getVocOncologyReasonTreat() {
        return vocOncologyReasonTreat;
    }
    public void setVocOncologyReasonTreat(VocOncologyReasonTreat vocOncologyReasonTreat) {
        this.vocOncologyReasonTreat = vocOncologyReasonTreat;
    }

    @Comment("Стадия заболевания")
    @OneToOne
    public VocStad_N002 getStad() {
        return stad;
    }
    public void setStad(VocStad_N002 stad) {
        this.stad = stad;
    }

    @Comment("Значение Tumor")
    @OneToOne
    public VocTumor_N003 getTumor() {
        return tumor;
    }
    public void setTumor(VocTumor_N003 tumor) {
        this.tumor = tumor;
    }

    @Comment("Значение Nodus")
    @OneToOne
    public VocNodus_N004 getNodus() {
        return nodus;
    }
    public void setNodus(VocNodus_N004 nodus) {
        this.nodus = nodus;
    }

    @Comment("Значение Metastasis")
    @OneToOne
    public VocMetastasis_N005 getMetastasis() {
        return metastasis;
    }
    public void setMetastasis(VocMetastasis_N005 metastasis) {
        this.metastasis = metastasis;
    }

    @Comment("Признак отдаленных метастазов")
    public Boolean getDistantMetastasis() {
        return isDistantMetastasis;
    }
    public void setDistantMetastasis(Boolean distantMetastasis) {
        isDistantMetastasis = distantMetastasis;
    }

    @Comment("Суммарная очаговая доза")
    public String getSumDose() {
        return sumDose;
    }
    public void setSumDose(String sumDose) {
        this.sumDose = sumDose;
    }

    @Comment("Сведения о проведении консилиума")
    @OneToOne
    public VocOncologyConsilium getConsilium() {
        return consilium;
    }
    public void setConsilium(VocOncologyConsilium consilium) {
        this.consilium = consilium;
    }

    @Comment("Тип услуги")
    @OneToOne
    public VocTypeTreatment_N013 getTypeTreatment() {
        return typeTreatment;
    }
    public void setTypeTreatment(VocTypeTreatment_N013 typeTreatment) {
        this.typeTreatment = typeTreatment;
    }

    @Comment("Тип хирургического лечения")
    @OneToOne
    public VocTypeSurgTreatment_N014 getSurgTreatment() {
        return surgTreatment;
    }
    public void setSurgTreatment(VocTypeSurgTreatment_N014 surgTreatment) {
        this.surgTreatment = surgTreatment;
    }

    @Comment("Линия лекарственной терамии")
    @OneToOne
    public VocLineDrugTherapy_N015 getLineDrugTherapy() {
        return lineDrugTherapy;
    }
    public void setLineDrugTherapy(VocLineDrugTherapy_N015 lineDrugTherapy) {
        this.lineDrugTherapy = lineDrugTherapy;
    }

    @Comment("Цикл лекарственной терапии")
    @OneToOne
    public VocCycleDrugTherapy_N016 getCycleDrugTherapy() {
        return cycleDrugTherapy;
    }
    public void setCycleDrugTherapy(VocCycleDrugTherapy_N016 cycleDrugTherapy) {
        this.cycleDrugTherapy = cycleDrugTherapy;
    }

    @Comment("Тип лучевой терапии")
    @OneToOne
    public VocTypeRadTherapy_N017 getTypeRadTherapy() {
        return typeRadTherapy;
    }
    public void setTypeRadTherapy(VocTypeRadTherapy_N017 typeRadTherapy) {
        this.typeRadTherapy = typeRadTherapy;
    }

}
