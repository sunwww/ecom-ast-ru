package ru.ecom.mis.ejb.uc.privilege.domain;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Льготный рецепт
 * @author azviagin
 *
 */

@Comment("Льготный рецепт")
@Entity
@AIndexes(value = { @AIndex(properties = { "privilege" },table="Recipe") })
@Getter
@Setter
public class PrivilegeRecipe extends Recipe{

	/** Льгота */
	@Comment("Льгота")
	@OneToOne
	public Privilege getPrivilege() {
		return privilege;
	}

	/** Льгота */
	private Privilege privilege;
	
	/** Решение КЭК */
	private boolean kekDesicion;
}
