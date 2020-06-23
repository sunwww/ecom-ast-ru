package ru.ecom.mis.ejb.domain.prescription;

import ru.ecom.diary.ejb.domain.category.TemplateCategory;
import ru.ecom.jaas.ejb.domain.SecGroup;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

/**
 * Шаблон листа назначений
 * @author stkacheva
 *
 */

@Comment("Шаблон листа назначений")
@Entity
public class PrescriptListTemplate extends AbstractPrescriptionList {
	/** Категории классификатора */
	@Comment("Категории классификатора")
	@ManyToMany
	public List<TemplateCategory> getCategories() {return theCategories;}
	public void setCategories(List<TemplateCategory> aCategories) {theCategories = aCategories;}

	/** Категории классификатора */
	private List<TemplateCategory> theCategories;
	/** Группы пользователей */
	@Comment("Группы пользователей")
	@ManyToMany
	public List<SecGroup> getSecGroups() {return theSecGroups;}
	public void setSecGroups(List<SecGroup> aSecGroups) {theSecGroups = aSecGroups;}

	/** Группы пользователей */
	private List<SecGroup> theSecGroups;
}
