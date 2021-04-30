package ru.ecom.expert2.domain;

import lombok.Getter;
import lombok.Setter;
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
/**Случай онкологического лечения*/
public class E2CancerEntry extends BaseEntity {

    /** Запись */
    @Comment("Запись")
    @ManyToOne
    public E2Entry getEntry() {return entry;}
    private E2Entry entry;

  /** Признак подозрения на на ЗО */
  private Boolean maybeCancer =false;
  
  /** Повод обращения */
  private String occasion;

  /** Стадия */
  private String stage;

  /** Tumor */
  private String tumor;

  /** Nodus */
  private String nodus;

  /** Metastasis */
  private String metastasis;

  /** Выявлены отдаленные метастазы */
  private Boolean isMetastasisFound =false;

  /** Суммарная очаговая доза */
  private BigDecimal sod;

  /** Сведения о решении консилиума */
  private String consiliumResult;

  /** Дата проведения консилиума */
  private Date consiliumDate;
  
  /** Тип услуги */
  private String serviceType;
  
  /** Тип хирургического лечения */
  private String surgicalType;
  
  /** Линия лекарственной терапии */
  private String drugLine;
  
  /** Цикл лекарственной терапии */
  private String drugCycle;
  
  /** Тип лучевой терапии */
  private String radiationTherapy;

  public E2CancerEntry(E2Entry aEntry) {
    entry =aEntry;}
  public E2CancerEntry(E2CancerEntry aCancerEntry, E2Entry aEntry) {
    entry = aEntry;
    maybeCancer = aCancerEntry.maybeCancer;
    occasion =aCancerEntry.occasion;
    stage =aCancerEntry.stage;
    tumor =aCancerEntry.tumor;
    nodus =aCancerEntry.nodus;
    metastasis =aCancerEntry.metastasis;
    isMetastasisFound =aCancerEntry.isMetastasisFound;
    sod =aCancerEntry.sod;
    consiliumResult =aCancerEntry.consiliumResult;
    consiliumDate =aCancerEntry.consiliumDate;
    serviceType =aCancerEntry.serviceType;
    surgicalType =aCancerEntry.surgicalType;
    drugLine =aCancerEntry.drugLine;
    drugCycle =aCancerEntry.drugCycle;
    radiationTherapy =aCancerEntry.radiationTherapy;
    if (aCancerEntry.directions !=null && !aCancerEntry.directions.isEmpty()) {
      List<E2CancerDirection> directions = new ArrayList<>();
      for (E2CancerDirection direction : aCancerEntry.getDirections()) {
        directions.add(new E2CancerDirection(direction, this));
      }
      this.directions = directions;
    }
    if (aCancerEntry.diagnostics !=null && !aCancerEntry.diagnostics.isEmpty()) {
      List<E2CancerDiagnostic> diagnostics = new ArrayList<>();
      for (E2CancerDiagnostic diagnostic : aCancerEntry.getDiagnostics()) {
        diagnostics.add(new E2CancerDiagnostic(diagnostic, this));
      }
      this.diagnostics = diagnostics;
    }
    if (aCancerEntry.refusals !=null && !aCancerEntry.refusals.isEmpty()) {
      List<E2CancerRefusal> refusals = new ArrayList<>();
      for (E2CancerRefusal refusal : aCancerEntry.getRefusals()) {
        refusals.add(new E2CancerRefusal(refusal,this));
      }
      this.refusals = refusals;

    }
    if (aCancerEntry.drugs !=null && !aCancerEntry.drugs.isEmpty()) {
      List<E2CancerDrug> drugs = new ArrayList<>();
      for (E2CancerDrug drug : aCancerEntry.getDrugs()) {
        drugs.add(new E2CancerDrug(drug,this));
      }
      this.drugs = drugs;
    }
  }

  public E2CancerEntry() {}

  /** Список направления */
  @Comment("Список направления")
  @OneToMany(mappedBy = "cancerEntry", cascade = CascadeType.ALL)
  public List<E2CancerDirection> getDirections() {return directions;}
  private List<E2CancerDirection> directions;

  /** Диагностические блоки */
  @Comment("Диагностические блоки")
  @OneToMany(mappedBy = "cancerEntry", cascade = CascadeType.ALL)
  public List<E2CancerDiagnostic> getDiagnostics() {return diagnostics;}
  private List<E2CancerDiagnostic> diagnostics;

  /** Противопоказания */
  @Comment("Противопоказания")
  @OneToMany(mappedBy = "cancerEntry", cascade = CascadeType.ALL)
  public List<E2CancerRefusal> getRefusals() {return refusals;}
  private List<E2CancerRefusal> refusals;

  /** Лекарства */
  @Comment("Лекарства")
  @OneToMany(mappedBy = "cancerEntry", cascade = CascadeType.ALL)
  public List<E2CancerDrug> getDrugs() {return drugs;}
  private List<E2CancerDrug> drugs;

}