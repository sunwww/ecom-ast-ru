package ru.ecom.mis.ejb.domain.expert.voc;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
 /**
  * Справочник видов оценок качества
  */
 @Comment("Справочник видов оценок качества")
@Entity
@Table(schema="SQLUser")
 @Getter
 @Setter
public class VocQualityEstimationKind extends VocBaseEntity{
  /** Признак, можно ли создавать в госпитализации */
  private Boolean createInHMC;
}