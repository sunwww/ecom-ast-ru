package ru.ecom.mis.ejb.domain.diet.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Метод приготовления блюда
 * @author azviagin
 *
 */
@Comment("Метод приготовления блюда")
@Entity
@Table(schema="SQLUser")
public class VocDishPreparationMethod extends VocBaseEntity{

}
