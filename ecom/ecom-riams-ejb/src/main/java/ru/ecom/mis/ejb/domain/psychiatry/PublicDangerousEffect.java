package ru.ecom.mis.ejb.domain.psychiatry;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.psychiatry.voc.VocCriminalCodeArticle;
import ru.ecom.mis.ejb.domain.psychiatry.voc.VocPublicDangerousEffect;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
 /**
  * Общественно опасное действие
  */
 @Comment("Общественно опасное действие")
@Entity
@AIndexes({
	@AIndex(properties={"careCard"})
})
@Table(schema="SQLUser")
public class PublicDangerousEffect extends BaseEntity{
 /**
  * Дата действия
  */
 @Comment("Дата действия")
 public Date getEffectDate() {
  return theEffectDate;
 }
 public void setEffectDate(Date aEffectDate) {
  theEffectDate = aEffectDate;
 }
 /**
  * Дата действия
  */
 private Date theEffectDate;
 /**
  * Примечания
  */
 @Comment("Примечания")
 public String getNotes() {
  return theNotes;
 }
 public void setNotes(String aNotes) {
  theNotes = aNotes;
 }
 /**
  * Примечания
  */
 private String theNotes;
 /**
  * Статья уголовного кодекса
  */
 @Comment("Статья уголовного кодекса")
 @OneToOne
 public VocCriminalCodeArticle getCriminalCodeArtical() {
  return theCriminalCodeArtical;
 }
 public void setCriminalCodeArtical(VocCriminalCodeArticle aCriminalCodeArtical) {
  theCriminalCodeArtical = aCriminalCodeArtical;
 }
 /**
  * Статья уголовного кодекса
  */
 private VocCriminalCodeArticle theCriminalCodeArtical;
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
 
 /** Статья уголовного кодекса (ИНФО) */
@Comment("Статья уголовного кодекса (ИНФО)")
@Transient
public String getCriminalCodeArticalInfo() {
	return theCriminalCodeArtical!=null?theCriminalCodeArtical.getName():"";
}
/** Номер ООД */
@Comment("Номер ООД")
public Integer getEffectNumber() {return theEffectNumber;}
public void setEffectNumber(Integer aEffectNumber) {theEffectNumber = aEffectNumber;}

/** Номер ООД */
private Integer theEffectNumber;
/** Тип общественно опасных действий */
@Comment("Тип общественно опасных действий")
@OneToOne
public VocPublicDangerousEffect getEffectType() {
	return theEffectType;
}

public void setEffectType(VocPublicDangerousEffect aEffectType) {
	theEffectType = aEffectType;
}

/** Тип общественно опасных действий */
private VocPublicDangerousEffect theEffectType;
}
