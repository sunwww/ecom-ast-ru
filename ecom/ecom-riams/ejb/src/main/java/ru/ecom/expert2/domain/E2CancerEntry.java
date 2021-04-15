package ru.ecom.expert2.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Accessors(prefix = "the")
/**Случай онкологического лечения*/
public class E2CancerEntry extends BaseEntity {

    /**
     * Запись
     */
    @ManyToOne
    private E2Entry theEntry;

    /**
     * Признак подозрения на на ЗО
     */
    private Boolean theMaybeCancer = false;

    /**
     * Повод обращения
     */
    private String theOccasion;

    /**
     * Стадия
     */
    private String theStage;

    /**
     * Tumor
     */
    private String theTumor;

    /**
     * Nodus
     */
    private String theNodus;

    /**
     * Metastasis
     */
    private String theMetastasis;

    /**
     * Выявлены отдаленные метастазы
     */
    private Boolean theIsMetastasisFound = false;

    /**
     * Суммарная очаговая доза
     */
    private BigDecimal theSod;

    /**
     * Сведения о решении консилиума
     */
    private String theConsiliumResult;

    /**
     * Дата проведения консилиума
     */
    private Date theConsiliumDate;

    /**
     * Тип услуги
     */
    private String theServiceType;

    /**
     * Тип хирургического лечения
     */
    private String theSurgicalType;

    /**
     * Линия лекарственной терапии
     */
    private String theDrugLine;

    /**
     * Цикл лекарственной терапии
     */
    private String theDrugCycle;

    /**
     * Тип лучевой терапии
     */
    private String theRadiationTherapy;

    public E2CancerEntry(E2Entry aEntry) {
        theEntry = aEntry;
    }

    public E2CancerEntry(E2CancerEntry aCancerEntry, E2Entry aEntry) {
        theEntry = aEntry;
        theMaybeCancer = aCancerEntry.theMaybeCancer;
        theOccasion = aCancerEntry.theOccasion;
        theStage = aCancerEntry.theStage;
        theTumor = aCancerEntry.theTumor;
        theNodus = aCancerEntry.theNodus;
        theMetastasis = aCancerEntry.theMetastasis;
        theIsMetastasisFound = aCancerEntry.theIsMetastasisFound;
        theSod = aCancerEntry.theSod;
        theConsiliumResult = aCancerEntry.theConsiliumResult;
        theConsiliumDate = aCancerEntry.theConsiliumDate;
        theServiceType = aCancerEntry.theServiceType;
        theSurgicalType = aCancerEntry.theSurgicalType;
        theDrugLine = aCancerEntry.theDrugLine;
        theDrugCycle = aCancerEntry.theDrugCycle;
        theRadiationTherapy = aCancerEntry.theRadiationTherapy;
        if (aCancerEntry.theDirections != null && !aCancerEntry.theDirections.isEmpty()) {
            List<E2CancerDirection> directions = new ArrayList<>();
            for (E2CancerDirection direction : aCancerEntry.getDirections()) {
                directions.add(new E2CancerDirection(direction, this));
            }
            theDirections = directions;
        }
        if (aCancerEntry.theDiagnostics != null && !aCancerEntry.theDiagnostics.isEmpty()) {
            List<E2CancerDiagnostic> diagnostics = new ArrayList<>();
            for (E2CancerDiagnostic diagnostic : aCancerEntry.getDiagnostics()) {
                diagnostics.add(new E2CancerDiagnostic(diagnostic, this));
            }
            theDiagnostics = diagnostics;
        }
        if (aCancerEntry.theRefusals != null && !aCancerEntry.theRefusals.isEmpty()) {
            List<E2CancerRefusal> refusals = new ArrayList<>();
            for (E2CancerRefusal refusal : aCancerEntry.getRefusals()) {
                refusals.add(new E2CancerRefusal(refusal, this));
            }
            theRefusals = refusals;

        }
        if (aCancerEntry.theDrugs != null && !aCancerEntry.theDrugs.isEmpty()) {
            List<E2CancerDrug> drugs = new ArrayList<>();
            for (E2CancerDrug drug : aCancerEntry.getDrugs()) {
                drugs.add(new E2CancerDrug(drug, this));
            }
            theDrugs = drugs;
        }
    }

    public E2CancerEntry() {
    }

    /**
     * Список направления
     */
    @Comment("Список направления")
    @OneToMany(mappedBy = "cancerEntry", cascade = CascadeType.ALL)
    public List<E2CancerDirection> getDirections() {
        return theDirections;
    }

    private List<E2CancerDirection> theDirections;

    /**
     * Диагностические блоки
     */
    @Comment("Диагностические блоки")
    @OneToMany(mappedBy = "cancerEntry", cascade = CascadeType.ALL)
    public List<E2CancerDiagnostic> getDiagnostics() {
        return theDiagnostics;
    }

    private List<E2CancerDiagnostic> theDiagnostics;

    /**
     * Противопоказания
     */
    @Comment("Противопоказания")
    @OneToMany(mappedBy = "cancerEntry", cascade = CascadeType.ALL)
    public List<E2CancerRefusal> getRefusals() {
        return theRefusals;
    }

    private List<E2CancerRefusal> theRefusals;

    /**
     * Лекарства
     */
    @Comment("Лекарства")
    @OneToMany(mappedBy = "cancerEntry", cascade = CascadeType.ALL)
    public List<E2CancerDrug> getDrugs() {
        return theDrugs;
    }

    private List<E2CancerDrug> theDrugs;

}