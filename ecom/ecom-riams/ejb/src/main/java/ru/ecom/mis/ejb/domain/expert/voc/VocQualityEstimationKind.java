package ru.ecom.mis.ejb.domain.expert.voc;
import javax.persistence.Entity;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
 /**
  * Справочник видов оценок качества
  */
 @Comment("Справочник видов оценок качества")
@Entity
@Table(schema="SQLUser")
public class VocQualityEstimationKind extends VocBaseEntity{
  /** Признак, можно ли создавать в госпитализации */
  @Comment("Признак, можно ли создавать в госпитализации")
  public Boolean getCreateInHMC() {return theCreateInHMC;}
  public void setCreateInHMC(Boolean aCreateInHMC) {theCreateInHMC = aCreateInHMC;}
  /** Признак, можно ли создавать в госпитализации */
  private Boolean theCreateInHMC;
}