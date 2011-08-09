package ru.ecom.mis.ejb.domain.psychiatry;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.psychiatry.voc.VocPsychSuicideNature;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
 /**
  * Суицид
  */
 @Comment("Суицид")
@Entity
@AIndexes({
	@AIndex(properties={"careCard"})
})
@Table(schema="SQLUser")
public class Suicide extends BaseEntity{
 /**
  * Карта обратившегося за психиатрической помощью
  */
 @Comment("Карта обратившегося за психиатрической помощью")
 @ManyToOne
 public PsychiatricCareCard getCareCard() {
  return theCareCard;
 }
 public void setCareCard(PsychiatricCareCard aCareCard) {
  theCareCard = aCareCard;
 }
 /**
  * Карта обратившегося за психиатрической помощью
  */
 private PsychiatricCareCard theCareCard;
 /**
  * Дата совершения
  */
 @Comment("Дата совершения")
 public Date getFulfilmentDate() {
  return theFulfilmentDate;
 }
 public void setFulfilmentDate(Date aFulfilmentDate) {
  theFulfilmentDate = aFulfilmentDate;
 }
 	/** Характер суицида */
	@Comment("Характер суицида")
	@OneToOne
	public VocPsychSuicideNature getNature() {
		return theNature;
	}

	public void setNature(VocPsychSuicideNature aNature) {
		theNature = aNature;
	}
	
	/** Завершен? */
	@Comment("Завершен?")
	public Boolean getIsFinished() {
		return theIsFinished;
	}

	public void setIsFinished(Boolean aIsFinished) {
		theIsFinished = aIsFinished;
	}

	/** Завершен? */
	private Boolean theIsFinished;

	/** Характер суицида */
	private VocPsychSuicideNature theNature;
 /**
  * Дата совершения
  */
 private Date theFulfilmentDate;
 /**
  * Описание
  */
 @Comment("Описание")
 public String getNotes() {
  return theNotes;
 }
 public void setNotes(String aNotes) {
  theNotes = aNotes;
 }
 /**
  * Описание
  */
 private String theNotes;
}
