package ru.ecom.mis.ejb.domain.expert;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
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
 @Getter
 @Setter
public class QualityEstimationCrit extends BaseEntity{
 /**
  * Оценка качества
  */
 @Comment("Оценка качества")
 @ManyToOne
 public QualityEstimation getEstimation() {return estimation;}
 /**
  * Оценка качества
  */
 private QualityEstimation estimation;
 /**
  * Балл оценки критерия качества
  */
 @Comment("Балл оценки критерия качества")
 @OneToOne
 public VocQualityEstimationMark getMark() {return mark;}
 /**
  * Балл оценки критерия качества
  */
 private VocQualityEstimationMark mark;
 
	 /** Критерий оценки качества */
	@Comment("Критерий оценки качества")
	@OneToOne
	public VocQualityEstimationCrit getCriterion() {return criterion;}

	/** Критерий оценки качества */
	private VocQualityEstimationCrit criterion;
 

	/** Оценка */
	private BigDecimal markTransient;
	
	/** Замечание */
	private String comment;
}
