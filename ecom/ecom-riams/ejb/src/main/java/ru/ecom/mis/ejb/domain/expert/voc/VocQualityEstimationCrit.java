package ru.ecom.mis.ejb.domain.expert.voc;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
 /**
  * Справочник критериев оценки качества
  */
 @Comment("Справочник критериев оценки качества")
@Entity
@Table(schema="SQLUser")
@AIndexes({
	@AIndex(properties="kind")
	, @AIndex(properties="parent")
 })
public class VocQualityEstimationCrit extends VocBaseEntity{
 /**
  * Короткое название
  */
 @Comment("Короткое название")
 public String getShortName() {
  return theShortName;
 }
 public void setShortName(String aShortName) {
  theShortName = aShortName;
 }
 /**
  * Короткое название
  */
 private String theShortName;
 /**
  * Вид оценки качества
  */
 @Comment("Вид оценки качества")
 @OneToOne
 public VocQualityEstimationKind getKind() {
  return theKind;
 }
 public void setKind(VocQualityEstimationKind aKind) {
  theKind = aKind;
 }
 /** Тип критерия */
@Comment("Тип критерия")
@OneToOne
public VocQualityEstimationCritType getType() {
	return theType;
}

public void setType(VocQualityEstimationCritType aType) {
	theType = aType;
}

/** Родитель */
@Comment("Родитель")
@OneToOne
public VocQualityEstimationCrit getParent() {return theParent;}
public void setParent(VocQualityEstimationCrit aParent) {theParent = aParent;}

/** Родитель */
private VocQualityEstimationCrit theParent;
/** Тип критерия */
private VocQualityEstimationCritType theType;
 /**
  * Вид оценки качества
  */
 private VocQualityEstimationKind theKind;

 /** Коды медицинских услуг */
  @Comment("Коды медицинских услуг")
  public String getMedServiceCodes() {
   return theMedServiceCodes;
  }
  public void setMedServiceCodes(String medServiceCodes) {
   theMedServiceCodes = medServiceCodes;
  }
  /** Коды медицинских услуг */
  private String theMedServiceCodes;

  /** Логический тип критерия? */
  @Comment("Логический тип критерия?")
  public Boolean getIsBoolean() {
   return theIsBoolean;
  }
  public void setIsBoolean(Boolean aIsBoolean) {
   theIsBoolean = aIsBoolean;
  }

  /** Логический тип критерия? */
  private Boolean theIsBoolean;

  /** Для взрослых? */
  @Comment("Для взрослых?")
  public Boolean getIsGrownup() {
   return theIsGrownup;
  }
  public void setIsGrownup(Boolean aIsGrownup) {
   theIsGrownup = aIsGrownup;
  }

  /** Для взрослых? */
  private Boolean theIsGrownup;

  /** Для детей? */
  @Comment("Для детей?")
  public Boolean getIsChild() {
   return theIsChild;
  }
  public void setIsChild(Boolean aIsChild) {
   theIsChild = aIsChild;
  }

  /** Для детей? */
  private Boolean theIsChild;
 }
