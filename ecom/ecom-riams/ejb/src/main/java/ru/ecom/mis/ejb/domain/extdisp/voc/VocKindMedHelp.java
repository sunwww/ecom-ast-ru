package ru.ecom.mis.ejb.domain.extdisp.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник профиля мед.помощи
 */
@Comment("Справочник профиля мед.помощи")
@Entity
@Table(schema="SQLUser")
public class VocKindMedHelp extends VocBaseEntity{

}
