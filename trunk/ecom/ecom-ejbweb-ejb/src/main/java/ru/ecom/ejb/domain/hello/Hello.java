package ru.ecom.ejb.domain.hello;

import static javax.persistence.CascadeType.ALL;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.NoLiveBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;


@Entity
@Table(schema="SQLUser")
public class Hello extends NoLiveBaseEntity {

	/** Свойство */
	@Comment("Свойство")
	public String getHello() {
		return theHello;
	}

	public void setHello(String aHello) {
		theHello = aHello;
	}

	/** Родитель */
	@Comment("Родитель")
	@ManyToOne
	public Hello getParent() {
		return theParent;
	}

	public void setParent(Hello aParent) {
		theParent = aParent;
	}
	
	/** Childs */
	@Comment("Childs")
	@OneToMany(mappedBy="parent", cascade=ALL)
	public List<Hello> getChilds() {
		return theChilds;
	}

	public void setChilds(List<Hello> aChilds) {
		theChilds = aChilds;
	}
	
	/** Связь один-к-одному */
	@Comment("Связь один-к-одному")
	@OneToOne
	public Hello getLink() {
		return theLink;
	}

	public void setLink(Hello aLink) {
		theLink = aLink;
	}

	/** Связь один-к-одному */
	private Hello theLink;

	/** Childs */
	private List<Hello> theChilds;

	/** Родитель */
	private Hello theParent;
	/** Свойство */
	private String theHello;
}
