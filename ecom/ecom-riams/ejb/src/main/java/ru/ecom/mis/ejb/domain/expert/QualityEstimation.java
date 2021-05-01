package ru.ecom.mis.ejb.domain.expert;
import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
 /**
  * 
  */
 @Comment("")
@Entity
@Table(schema="SQLUser")
@AIndexes({
	@AIndex(properties="card")
	, @AIndex(properties={"card","expertType"})
 })
 @Getter
 @Setter
public class QualityEstimation extends BaseEntity{
	 /**
	  * Карта оценки качества
	  */
	 @Comment("Карта оценки качества")
	 @ManyToOne
	 public QualityEstimationCard getCard() {
	  return card;
	 }
	 /**
	  * Карта оценки качества
	  */
	 private QualityEstimationCard card;
	 /**
	  * Критерии оценки качества
	  */
	 @Comment("Критерии оценки качества")
	 @OneToMany(mappedBy="estimation", cascade=CascadeType.ALL)
	 public List<QualityEstimationCrit> getCriterions() {
	  return criterions;
	 }
	 /**
	  * Критерии оценки качества
	  */
	 private List<QualityEstimationCrit> criterions;
	 /**
	  * Эсперт
	  */
	 @Comment("Эсперт")
	 @OneToOne
	 public WorkFunction getExpert() {
	  return expert;
	 }

	private String createUsername;
	private Date createDate;
	/** Тип эксперта */
	private String expertType;
 
	 /**
	  * Эсперт
	  */
	 private WorkFunction expert;

	 /** Является ли черновиком */
	 private Boolean isDraft;
}