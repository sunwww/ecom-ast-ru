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
public class TemplateCategory extends BaseEntity {
	/** Родитель */
	@Comment("Родитель")
	@ManyToOne
	public TemplateCategory getParent() {return theParent;	}
	public void setParent(TemplateCategory aParent) {theParent = aParent;}

	/** Подкатегории */
	@Comment("Подкатегории")
	@OneToMany(mappedBy="parent", cascade = ALL )
	public List<TemplateCategory> getChild() {return theChild;}
	public void setChild(List<TemplateCategory> aChild) {theChild = aChild;}

	/** Название категории */
	@Comment("Название категории")
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}

	/** Комментарии */
	@Comment("Комментарии")
	public String getComments() {return theComments;}
	public void setComments(String aComments) {theComments = aComments;}

	/** Пользователь */
	@Comment("Пользователь")
	public String getUsername() {return theUsername;}
	public void setUsername(String aUsername) {theUsername = aUsername;}

	/** Дата создания */
	@Comment("Дата создания")
	public Date getDateCreate() {return theDateCreate;}
	public void setDateCreate(Date aDateCreate) {theDateCreate = aDateCreate;}

	/** Шаблоны листов назначений */
	@Comment("Шаблоны листов назначений")
	@ManyToMany(mappedBy="categories")
	public List<PrescriptListTemplate> getPrescriptLists() {return thePrescriptLists;}
	public void setPrescriptLists(List<PrescriptListTemplate> aPrescriptLists) {thePrescriptLists = aPrescriptLists;}

	/** Шаблоны протоколов */
	@Comment("Шаблоны протоколов")
	@ManyToMany(mappedBy="categories")
	public List<TemplateProtocol> getProtocols() {return theProtocols;	}
	public void setProtocols(List<TemplateProtocol> aProtocols) {theProtocols = aProtocols;}

	@Transient @Comment("Полное имя категории")
	public String getFullname() {
		StringBuilder ret = new StringBuilder() ;
		
		if (theParent!=null) {
			ret.append(getInfoParent(theParent,"")) ;
			//ret.append("->");
		}
		ret.append(theName) ;
		return ret.toString() ;
	}
	private String getInfoParent(TemplateCategory aCateg, String aAppend) {
		StringBuilder ret = new StringBuilder() ;
		ret.append(aCateg.getName()).append(" ->").append(aAppend) ;
		if (aCateg.getParent()!=null) ret.append(getInfoParent(aCateg.getParent(), ret.toString())) ;
		return ret.toString() ;
	}
	/** Шаблоны протоколов */
	private List<TemplateProtocol> theProtocols;
	/** Шаблоны листов назначений */
	private List<PrescriptListTemplate> thePrescriptLists;
	/** Дата создания */
	private Date theDateCreate;
	/** Пользователь */
	private String theUsername;
	/** Комментарии */
	private String theComments;
	/** Название категории */
	private String theName;
	/** Подкатегории */
	private List<TemplateCategory> theChild;
	/** Родитель */
	private TemplateCategory theParent;
}
