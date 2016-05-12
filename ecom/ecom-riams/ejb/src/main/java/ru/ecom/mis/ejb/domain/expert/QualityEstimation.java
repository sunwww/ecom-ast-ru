package ru.ecom.mis.ejb.domain.expert;
import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
public class QualityEstimation extends BaseEntity{
	 /**
	  * Карта оценки качества
	  */
	 @Comment("Карта оценки качества")
	 @ManyToOne
	 public QualityEstimationCard getCard() {
	  return theCard;
	 }
	 public void setCard(QualityEstimationCard aCard) {
	  theCard = aCard;
	 }
	 /**
	  * Карта оценки качества
	  */
	 private QualityEstimationCard theCard;
	 /**
	  * Критерии оценки качества
	  */
	 @Comment("Критерии оценки качества")
	 @OneToMany(mappedBy="estimation", cascade=CascadeType.ALL)
	 public List<QualityEstimationCrit> getCriterions() {
	  return theCriterions;
	 }
	 public void setCriterions(List<QualityEstimationCrit> aCriterions) {
	  theCriterions = aCriterions;
	 }
	 /**
	  * Критерии оценки качества
	  */
	 private List<QualityEstimationCrit> theCriterions;
	 /**
	  * Эсперт
	  */
	 @Comment("Эсперт")
	 @OneToOne
	 public WorkFunction getExpert() {
	  return theExpert;
	 }
	 public void setExpert(WorkFunction aExpert) {
	  theExpert = aExpert;
	 }

 	/** Тип эксперта */
	@Comment("Тип эксперта")
	public String getExpertType() {
		return theExpertType;
	}

	public void setExpertType(String aExpertType) {
		theExpertType = aExpertType;
	}
	/** Дата создания */
	@Comment("Дата создания")
	public Date getCreateDate() {
		return theCreateDate;
	}

	public void setCreateDate(Date aNAME) {
		theCreateDate = aNAME;
	}
	/** Пользователь, создавший экспертную карту */
	@Comment("Пользователь, создавший экспертную карту")
	public String getCreateUsername() {
		return theCreateUsername;
	}

	public void setCreateUsername(String aCreateUsername) {
		theCreateUsername = aCreateUsername;
	}
	private String theCreateUsername;
	private Date theCreateDate;
	/** Тип эксперта */
	private String theExpertType;
 
	 /**
	  * Эсперт
	  */
	 private WorkFunction theExpert;
}
