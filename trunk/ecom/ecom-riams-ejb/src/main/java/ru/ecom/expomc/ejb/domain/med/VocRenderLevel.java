package ru.ecom.expomc.ejb.domain.med;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocIdCodeName;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Уровень оказания услуги
 */
@Entity
@Comment("Уровень оказания услуги")
@Table(schema="SQLUser")
public class VocRenderLevel extends VocIdCodeName {
}
