package ru.ecom.mis.ejb.domain.psychiatry;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
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
 @Getter
 @Setter
public class PublicDangerousEffect extends BaseEntity{
 /**
  * Дата действия
  */
 private Date effectDate;
 /**
  * Примечания
  */
 private String notes;
 /**
  * Статья уголовного кодекса
  */
 @Comment("Статья уголовного кодекса")
 @OneToOne
 public VocCriminalCodeArticle getCriminalCodeArtical() {
  return criminalCodeArtical;
 }
 /**
  * Статья уголовного кодекса
  */
 private VocCriminalCodeArticle criminalCodeArtical;
 /**
  * Карта обратившегося за психиатрической помощью
  */
 @Comment("Карта обратившегося за психиатрической помощью")
 @ManyToOne
 public PsychiatricCareCard getCareCard() {
  return careCard;
 }
 /**
  * Карта обратившегося за психиатрической помощью
  */
 private PsychiatricCareCard careCard;
 
 /** Статья уголовного кодекса (ИНФО) */
@Comment("Статья уголовного кодекса (ИНФО)")
@Transient
public String getCriminalCodeArticalInfo() {
	return criminalCodeArtical!=null?criminalCodeArtical.getName():"";
}

/** Номер ООД */
private Integer effectNumber;
/** Тип общественно опасных действий */
@Comment("Тип общественно опасных действий")
@OneToOne
public VocPublicDangerousEffect getEffectType() {
	return effectType;
}

/** Тип общественно опасных действий */
private VocPublicDangerousEffect effectType;
}
