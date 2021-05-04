package ru.ecom.diary.ejb.domain.category;


import static javax.persistence.CascadeType.ALL;

import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.diary.ejb.domain.protocol.template.TemplateProtocol;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.prescription.PrescriptListTemplate;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
/**
 * Классификатор
 * @author STkacheva
 */
@Entity
@AIndexes({
	@AIndex(properties={"parent"})
})
@Table(schema="SQLUser")
@Getter
@Setter
public class TemplateCategory extends BaseEntity {
	/** Родитель */
	@Comment("Родитель")
	@ManyToOne
	public TemplateCategory getParent() {return parent;	}

	/** Подкатегории */
	@Comment("Подкатегории")
	@OneToMany(mappedBy="parent", cascade = ALL )
	public List<TemplateCategory> getChild() {return child;}

	/** Шаблоны листов назначений */
	@Comment("Шаблоны листов назначений")
	@ManyToMany(mappedBy="categories")
	public List<PrescriptListTemplate> getPrescriptLists() {return prescriptLists;}

	/** Шаблоны протоколов */
	@Comment("Шаблоны протоколов")
	@ManyToMany(mappedBy="categories")
	public List<TemplateProtocol> getProtocols() {return protocols;	}

	@Transient @Comment("Полное имя категории")
	public String getFullname() {
		StringBuilder ret = new StringBuilder() ;
		
		if (parent!=null) {
			ret.append(getInfoParent(parent,"")) ;
			//ret.append("->");
		}
		ret.append(name) ;
		return ret.toString() ;
	}
	private String getInfoParent(TemplateCategory aCateg, String aAppend) {
		StringBuilder ret = new StringBuilder() ;
		ret.append(aCateg.getName()).append(" ->").append(aAppend) ;
		if (aCateg.getParent()!=null) ret.append(getInfoParent(aCateg.getParent(), ret.toString())) ;
		return ret.toString() ;
	}
	/** Шаблоны протоколов */
	private List<TemplateProtocol> protocols;
	/** Шаблоны листов назначений */
	private List<PrescriptListTemplate> prescriptLists;
	/** Дата создания */
	private Date dateCreate;
	/** Пользователь */
	private String username;
	/** Комментарии */
	private String comments;
	/** Название категории */
	private String name;
	/** Подкатегории */
	private List<TemplateCategory> child;
	/** Родитель */
	private TemplateCategory parent;
}
