package ru.ecom.mis.ejb.domain.expert.voc;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
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
 @Getter
 @Setter
public class VocQualityEstimationCrit extends VocBaseEntity{
 /**
  * Короткое название
  */
 private String shortName;
 /**
  * Вид оценки качества
  */
 @Comment("Вид оценки качества")
 @OneToOne
 public VocQualityEstimationKind getKind() {
  return kind;
 }
 /** Тип критерия */
@Comment("Тип критерия")
@OneToOne
public VocQualityEstimationCritType getType() {
	return type;
}

/** Родитель */
@Comment("Родитель")
@OneToOne
public VocQualityEstimationCrit getParent() {return parent;}

/** Родитель */
private VocQualityEstimationCrit parent;
/** Тип критерия */
private VocQualityEstimationCritType type;
 /**
  * Вид оценки качества
  */
 private VocQualityEstimationKind kind;

  /** Коды медицинских услуг */
  private String medServiceCodes;

  /** Логический тип критерия? */
  private Boolean isBoolean;

  /** Для взрослых? */
  private Boolean isGrownup;

  /** Для детей? */
  private Boolean isChild;
 }
