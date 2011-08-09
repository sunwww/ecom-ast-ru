package ru.ecom.mis.ejb.domain.prescription;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import ru.ecom.diary.ejb.domain.category.TemplateCategory;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Шаблон листа назначений
 * @author stkacheva
 *
 */

@Comment("Шаблон листа назначений")
@Entity
@Table(schema="SQLUser")
public class PrescriptListTemplate extends AbstractPrescriptionList {
	/** Категории классификатора */
	@Comment("Категории классификатора")
	@ManyToMany
	public List<TemplateCategory> getCategories() {return theCategories;}
	public void setCategories(List<TemplateCategory> aCategories) {theCategories = aCategories;}

	/** Категории классификатора */
	private List<TemplateCategory> theCategories;
}
