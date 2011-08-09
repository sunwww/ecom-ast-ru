package ru.ecom.mis.ejb.domain.diet.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Тип блюда
 * @author azviagin
 *
 */

@Comment("Тип блюда")
@Entity
@Table(schema="SQLUser")
public class VocDishType extends VocBaseEntity{

}
