package ru.ecom.mis.ejb.domain.expert;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.expert.voc.VocQualityEstimationCrit;
import ru.ecom.mis.ejb.domain.expert.voc.VocQualityEstimationMark;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
 /**
  * 
  */
 @Comment("")
@Entity
@Table(schema="SQLUser")
@AIndexes({
	 @AIndex(properties={"estimation","mark"})
	 ,@AIndex(properties="estimation")
 })
public class QualityEstimationCrit extends BaseEntity{
 /**
  * Оценка качества
  */
 @Comment("Оценка качества")
 @ManyToOne
 public QualityEstimation getEstimation() {return theEstimation;}
 public void setEstimation(QualityEstimation aEstimation) {theEstimation = aEstimation;}
 /**
  * Оценка качества
  */
 private QualityEstimation theEstimation;
 /**
  * Балл оценки критерия качества
  */
 @Comment("Балл оценки критерия качества")
 @OneToOne
 public VocQualityEstimationMark getMark() {return theMark;}
 public void setMark(VocQualityEstimationMark aMark) {theMark = aMark;}
 /**
  * Балл оценки критерия качества
  */
 private VocQualityEstimationMark theMark;
 
	 /** Критерий оценки качества */
	@Comment("Критерий оценки качества")
	@OneToOne
	public VocQualityEstimationCrit getCriterion() {return theCriterion;}
	public void setCriterion(VocQualityEstimationCrit aCriterion) {theCriterion = aCriterion;}
	
	/** Критерий оценки качества */
	private VocQualityEstimationCrit theCriterion;
 
	 /** Оценка */
	@Comment("Оценка")
	public BigDecimal getMarkTransient() {return theMarkTransient;}
	public void setMarkTransient(BigDecimal aMarkTransient) {theMarkTransient = aMarkTransient;}
	
	/** Оценка */
	private BigDecimal theMarkTransient;
	
	/** Замечание */
	@Comment("Замечание")
	public String getComment() {return theComment;}
	public void setComment(String aComment) {theComment = aComment;}
	/** Замечание */
	private String theComment;
}
