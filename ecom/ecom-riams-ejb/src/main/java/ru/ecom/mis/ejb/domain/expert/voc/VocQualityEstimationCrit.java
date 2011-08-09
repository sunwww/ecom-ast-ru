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

/** Тип критерия */
private VocQualityEstimationCritType theType;
 /**
  * Вид оценки качества
  */
 private VocQualityEstimationKind theKind;
}
