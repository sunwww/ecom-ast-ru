package ru.ecom.mis.ejb.domain.expert.voc;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
 /**
  * Справочник баллов критериев оценки качества
  */
 @Comment("Справочник баллов критериев оценки качества")
@Entity
@Table(schema="SQLUser")
@AIndexes({
	 @AIndex(properties="criterion")
	 ,@AIndex(properties={"criterion","mark"})
	 })
public class VocQualityEstimationMark extends VocBaseEntity{
 /**
  * Оценочный балл
  */
 @Comment("Оценочный балл")
 public Double getMark() {
  return theMark;
 }
 public void setMark(Double aMark) {
  theMark = aMark;
 }
 /** Не учитывать */
@Comment("Не учитывать")
public Boolean getIsIgnore() {return theIsIgnore;}
public void setIsIgnore(Boolean aIsIgnore) {theIsIgnore = aIsIgnore;}

/** Не учитывать */
private Boolean theIsIgnore;
 
 /** Полное название */
@Comment("Полное название")
public String getFullname() {
	return theFullname;
}

public void setFullname(String aFullname) {
	theFullname = aFullname;
}

/** Полное название */
private String theFullname;
 /**
  * Оценочный балл
  */
 private Double theMark;
 /**
  * Критерий оценки качества
  */
 @Comment("Критерий оценки качества")
 @OneToOne
 public VocQualityEstimationCrit getCriterion() {
  return theCriterion;
 }
 public void setCriterion(VocQualityEstimationCrit aCriterion) {
  theCriterion = aCriterion;
 }
 /**
  * Критерий оценки качества
  */
 private VocQualityEstimationCrit theCriterion;
 
 /** Обязательно указывать примечание */
@Comment("Обязательно указывать примечание")
public Boolean getIsNeedComment() {return theIsNeedComment;}
public void setIsNeedComment(Boolean aIsNeedComment) {theIsNeedComment = aIsNeedComment;}
/** Обязательно указывать примечание */
private Boolean theIsNeedComment;
}
