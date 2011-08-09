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
}
