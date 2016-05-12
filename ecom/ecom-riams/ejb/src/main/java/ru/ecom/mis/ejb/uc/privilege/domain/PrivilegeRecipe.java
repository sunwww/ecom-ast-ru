package ru.ecom.mis.ejb.uc.privilege.domain;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Льготный рецепт
 * @author azviagin
 *
 */

@Comment("Льготный рецепт")
@Entity
@AIndexes(value = { @AIndex(properties = { "privilege" },table="Recipe") })
@Table(schema="SQLUser")
public class PrivilegeRecipe extends Recipe{

	/** Льгота */
	@Comment("Льгота")
	@OneToOne
	public Privilege getPrivilege() {
		return thePrivilege;
	}

	public void setPrivilege(Privilege aPrivilege) {
		thePrivilege = aPrivilege;
	}
	/** Льгота */
	private Privilege thePrivilege;
	
	/** Решение КЭК */
	@Comment("Решение КЭК")
	public boolean getKekDesicion() {
		return theKekDesicion;
	}

	public void setKekDesicion(boolean aKekDesicion) {
		theKekDesicion = aKekDesicion;
	}
	
	/** Решение КЭК */
	private boolean theKekDesicion;
}
