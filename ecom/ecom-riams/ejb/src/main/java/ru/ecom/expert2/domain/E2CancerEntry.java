package ru.ecom.expert2.domain;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Entity
/**Случай онкологического лечения*/
public class E2CancerEntry extends BaseEntity {

    /** Запись */
    @Comment("Запись")
    @ManyToOne
    public E2Entry getEntry() {return theEntry;}
    public void setEntry(E2Entry aEntry) {theEntry = aEntry;}
    /** Запись */
    private E2Entry theEntry ;

  /** Признак подозрения на на ЗО */
  @Comment("Признак подозрения на на ЗО")
  @Column(nullable=false, columnDefinition="boolean default false")
  public Boolean getMaybeCancer() {return theMaybeCancer;}
  public void setMaybeCancer(Boolean aMaybeCancer) {theMaybeCancer = aMaybeCancer;}
  /** Признак подозрения на на ЗО */
  private Boolean theMaybeCancer =false;
  
  /** Повод обращения */
  @Comment("Повод обращения")
  public String getOccasion() {return theOccasion;}
  public void setOccasion(String aOccasion) {theOccasion = aOccasion;}
  /** Повод обращения */
  private String theOccasion ;

  /** Стадия */
  @Comment("Стадия")
  public String getStage() {return theStage;}
  public void setStage(String aStage) {theStage = aStage;}
  /** Стадия */
  private String theStage ;

  /** Tumor */
  @Comment("Tumor")
  public String getTumor() {return theTumor;}
  public void setTumor(String aTumor) {theTumor = aTumor;}
  /** Tumor */
  private String theTumor ;

  /** Nodus */
  @Comment("Nodus")
  public String getNodus() {return theNodus;}
  public void setNodus(String aNodus) {theNodus = aNodus;}
  /** Nodus */
  private String theNodus ;

  /** Metastasis */
  @Comment("Metastasis")
  public String getMetastasis() {return theMetastasis;}
  public void setMetastasis(String aMetastasis) {theMetastasis = aMetastasis;}
  /** Metastasis */
  private String theMetastasis ;

  /** Выявлены отдаленные метастазы */
  @Comment("Выявлены отдаленные метастазы")
  @Column(nullable=false, columnDefinition="boolean default false")
  public Boolean getIsMetastasisFound() {return theIsMetastasisFound;}
  public void setIsMetastasisFound(Boolean aIsMetastasisFound) {theIsMetastasisFound = aIsMetastasisFound;}
  /** Выявлены отдаленные метастазы */
  private Boolean theIsMetastasisFound =false;

  /** Суммарная очаговая доза */
  @Comment("Суммарная очаговая доза")
  public BigDecimal getSod() {return theSod;}
  public void setSod(BigDecimal aSod) {theSod = aSod;}
  /** Суммарная очаговая доза */
  private BigDecimal theSod ;

  /** Сведения о решении консилиума */
  @Comment("Сведения о решении консилиума")
  public String getConsiliumResult() {return theConsiliumResult;}
  public void setConsiliumResult(String aConsiliumResult) {theConsiliumResult = aConsiliumResult;}
  /** Сведения о решении консилиума */
  private String theConsiliumResult ;

  /** Дата проведения консилиума */
  @Comment("Дата проведения консилиума")
  public Date getConsiliumDate() {return theConsiliumDate;}
  public void setConsiliumDate(Date aConsiliumDate) {theConsiliumDate = aConsiliumDate;}
  /** Дата проведения консилиума */
  private Date theConsiliumDate ;
  
  /** Тип услуги */
  @Comment("Тип услуги")
  public String getServiceType() {return theServiceType;}
  public void setServiceType(String aServiceType) {theServiceType = aServiceType;}
  /** Тип услуги */
  private String theServiceType ;
  
  /** Тип хирургического лечения */
  @Comment("Тип хирургического лечения")
  public String getSurgicalType() {return theSurgicalType;}
  public void setSurgicalType(String aSurgicalType) {theSurgicalType = aSurgicalType;}
  /** Тип хирургического лечения */
  private String theSurgicalType ;
  
  /** Линия лекарственной терапии */
  @Comment("Линия лекарственной терапии")
  public String getDrugLine() {return theDrugLine;}
  public void setDrugLine(String aDrugLine) {theDrugLine = aDrugLine;}
  /** Линия лекарственной терапии */
  private String theDrugLine ;
  
  /** Цикл лекарственной терапии */
  @Comment("Цикл лекарственной терапии")
  public String getDrugCycle() {return theDrugCycle;}
  public void setDrugCycle(String aDrugCycle) {theDrugCycle = aDrugCycle;}
  /** Цикл лекарственной терапии */
  private String theDrugCycle ;
  
  /** Тип лучевой терапии */
  @Comment("Тип лучевой терапии")
  public String getRadiationTherapy() {return theRadiationTherapy;}
  public void setRadiationTherapy(String aRadiationTherapy) {theRadiationTherapy = aRadiationTherapy;}
  /** Тип лучевой терапии */
  private String theRadiationTherapy ;

  public E2CancerEntry(E2Entry aEntry) {theEntry=aEntry;}
  public E2CancerEntry() {}

  /** Список направления */
  @Comment("Список направления")
  @OneToMany(mappedBy = "cancerEntry", cascade = CascadeType.ALL)
  public List<E2CancerDirection> getDirections() {return theDirections;}
  public void setDirections(List<E2CancerDirection> aDirections) {theDirections = aDirections;}
  /** Список направления */
  private List<E2CancerDirection> theDirections ;

  /** Диагностические блоки */
  @Comment("Диагностические блоки")
  @OneToMany(mappedBy = "cancerEntry", cascade = CascadeType.ALL)
  public List<E2CancerDiagnostic> getDiagnostics() {return theDiagnostics;}
  public void setDiagnostics(List<E2CancerDiagnostic> aDiagnostics) {theDiagnostics = aDiagnostics;}
  /** Диагностические блоки */
  private List<E2CancerDiagnostic> theDiagnostics ;

  /** Противопоказания */
  @Comment("Противопоказания")
  @OneToMany(mappedBy = "cancerEntry", cascade = CascadeType.ALL)
  public List<E2CancerRefusal> getRefusals() {return theRefusals;}
  public void setRefusals(List<E2CancerRefusal> aRefusals) {theRefusals = aRefusals;}
  /** Противопоказания */
  private List<E2CancerRefusal> theRefusals ;

  /** Лекарства */
  @Comment("Лекарства")
  @OneToMany(mappedBy = "cancerEntry", cascade = CascadeType.ALL)
  public List<E2CancerDrug> getDrugs() {return theDrugs;}
  public void setDrugs(List<E2CancerDrug> aDrugs) {theDrugs = aDrugs;}
  /** Лекарства */
  private List<E2CancerDrug> theDrugs ;

}