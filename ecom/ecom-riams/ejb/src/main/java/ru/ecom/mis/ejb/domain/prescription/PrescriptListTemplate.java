package ru.ecom.mis.ejb.domain.prescription;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class PrescriptListTemplate extends AbstractPrescriptionList {
	/** Категории классификатора */
	@Comment("Категории классификатора")
	@ManyToMany
	public List<TemplateCategory> getCategories() {return categories;}
	private List<TemplateCategory> categories;

	/** Группы пользователей */
	@Comment("Группы пользователей")
	@ManyToMany
	public List<SecGroup> getSecGroups() {return secGroups;}
	private List<SecGroup> secGroups;
}
