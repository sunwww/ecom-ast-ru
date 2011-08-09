package ru.ecom.mis.ejb.uc.privilege.domain.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Статус рецепта
 * @author azviagin
 *
 */

@Comment("Статус рецепта")
@Entity
@Table(schema="SQLUser")
public class VocRecipeStatus extends VocBaseEntity{

}
