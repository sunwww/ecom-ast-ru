package ru.ecom.mis.ejb.domain.psychiatry.voc;
import javax.persistence.Entity;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
 /**
  * Справочник видов амбулаторного психиатрического наблюдения
  */
 @Comment("Справочник видов амбулаторного психиатрического наблюдения")
@Entity
@Table(schema="SQLUser")
public class VocPsychAmbulatoryCare extends VocBaseEntity{
}
