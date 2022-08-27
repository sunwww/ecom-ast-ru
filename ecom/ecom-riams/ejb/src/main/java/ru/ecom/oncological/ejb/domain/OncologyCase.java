package ru.ecom.oncological.ejb.domain;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.oncological.ejb.domain.voc.*;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;


/**
 * Cлучай лечения онк. заболевания
 * @author rkurbanov
 */
@Entity
@Table(schema="SQLUser")
@Getter
@Setter
@AIndexes({
        @AIndex(properties = {"medCase"})
})
public class OncologyCase extends BaseEntity {

    private MedCase medCase;

    /**Сведения о случае лечения онкологического заболевания */
    /**Повод обращения*/
    private VocOncologyReasonTreat vocOncologyReasonTreat;
    /**Стадия заболевания*/
    private VocOncologyN002 stad;
    /**Значение Tumor*/
    private VocOncologyN003 tumor;
    /**Значение Nodus*/
    private VocOncologyN004 nodus;
    /**Значение Metastasis_*/
    private VocOncologyN005 metastasis;
    /**Признак отдаленных метастазов*/
    private Boolean distantMetastasis;
    /**Суммарная очаговая доза*/
    private BigDecimal sumDose;
    /**Подозрение на онкологию*/
    private Boolean suspicionOncologist;

    /**Сведения о проведении консилиума*/
    private VocOncologyConsilium consilium;
    /**Тип услуги*/
    private VocOncologyN013 typeTreatment;
    /**Тип хирургического лечения*/
    private VocOncologyN014 surgTreatment;
    /**Линия лекарственной терамии*/
    private VocOncologyN015 lineDrugTherapy;
    /**Цикл лекарственной терапии*/
    private VocOncologyN016 cycleDrugTherapy;
    /**Тип лучевой терапии*/
    private VocOncologyN017 typeRadTherapy;
    /**Выявлено впервые?*/
    private Boolean isFirst;
    /** дата взятия биопсийного материала */
    private Date dateBiops;
    /** дата проведения консилиума*/
    private Date dateCons;
    /** диагноз, с которым создана онкологическая форма*/
    private String mKB;
    /**Признак проведения профилактики тошноты и рвотного рефлекса*/
    private Boolean isNauseaAndGagReflexPrev;


    @Comment("Случай лечения")
    @OneToOne
    public MedCase getMedCase() {
        return medCase;
    }

    private Long medCaseId;

    @Column(name = "medcase_id", updatable = false, insertable = false)
    public Long getMedCaseId() {
        return medCaseId;
    }

    @Comment("Повод обращения")
    @OneToOne
    public VocOncologyReasonTreat getVocOncologyReasonTreat() {
        return vocOncologyReasonTreat;
    }

    @Comment("Стадия заболевания")
    @OneToOne
    public VocOncologyN002 getStad() {
        return stad;
    }

    @Comment("Значение Tumor")
    @OneToOne
    public VocOncologyN003 getTumor() {
        return tumor;
    }

    @Comment("Значение Nodus")
    @OneToOne
    public VocOncologyN004 getNodus() {
        return nodus;
    }

    @Comment("Значение Metastasis")
    @OneToOne
    public VocOncologyN005 getMetastasis() {
        return metastasis;
    }

    @Comment("Сведения о проведении консилиума")
    @OneToOne
    public VocOncologyConsilium getConsilium() {
        return consilium;
    }

    @Comment("Тип услуги")
    @OneToOne
    public VocOncologyN013 getTypeTreatment() {
        return typeTreatment;
    }

    @Comment("Тип хирургического лечения")
    @OneToOne
    public VocOncologyN014 getSurgTreatment() {
        return surgTreatment;
    }

    @Comment("Линия лекарственной терамии")
    @OneToOne
    public VocOncologyN015 getLineDrugTherapy() {
        return lineDrugTherapy;
    }

    @Comment("Цикл лекарственной терапии")
    @OneToOne
    public VocOncologyN016 getCycleDrugTherapy() {
        return cycleDrugTherapy;
    }

    @Comment("Тип лучевой терапии")
    @OneToOne
    public VocOncologyN017 getTypeRadTherapy() {
        return typeRadTherapy;
    }

    /** Направления */
    @Comment("Направления")
    @OneToMany(mappedBy = "oncologyCase", cascade = CascadeType.ALL)
    public List<OncologyDirection> getDirections() {return directions;}
    /** Направления */
    private List<OncologyDirection> directions ;

    /** Противопоказания */
    @Comment("Противопоказания")
    @OneToMany(mappedBy = "oncologyCase", cascade = CascadeType.ALL)
    public List<OncologyContra> getContras() {return contras;}
    /** Противопоказания */
    private List<OncologyContra> contras ;

    /** Диагностические блоки */
    @Comment("Диагностические блоки")
    @OneToMany(mappedBy = "oncologyCase", cascade = CascadeType.ALL)
    public List<OncologyDiagnostic> getDiagnostics() {return diagnostics;}
    /** Диагностические блоки */
    private List<OncologyDiagnostic> diagnostics ;
    
    /** Лекарственные препараты */
    @Comment("Лекарственные препараты")
    @OneToMany(mappedBy = "oncologyCase", cascade = CascadeType.ALL)
    public List<OncologyDrug> getDrugs() {return drugs;}
    /** Лекарственные препараты */
    private List<OncologyDrug> drugs ;
}
