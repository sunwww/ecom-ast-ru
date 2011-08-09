package ru.ecom.jaas.ejb.domain;

import static javax.persistence.CascadeType.ALL;

import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Роль
 */
@Entity
@AIndexes({
	@AIndex(properties={"key"})
})
@Table(schema="SQLUser")
public class SecRole extends BaseEntity {

    /** Название */
    public String getName() { return theName ; }
    public void setName(String aName) { theName = aName ; }

    /** Комментарий */
    public String getComment() { return theComment ; }
    public void setComment(String aComment) { theComment = aComment ; }

    /** Политики безопасности */
    @ManyToMany(cascade = ALL)
    public Collection<SecPolicy> getSecPolicies() { return theSecPolicies ; }
    public void setSecPolicies(Collection<SecPolicy> aSecPolicies) { theSecPolicies = aSecPolicies ; }

    /** Ключ */
	@Comment("Ключ")
	public String getKey() {
		return theKey;
	}

	public void setKey(String aKey) {
		theKey = aKey;
	}
	
	/** Зависимые роли */
	@Comment("Зависимые роли")
	@ManyToMany
	public List<SecRole> getChildren() {return theChildren;}
	public void setChildren(List<SecRole> aChildren) {theChildren = aChildren;}

	/** Зависимые роли */
	private List<SecRole> theChildren;

	/** Ключ */
	private String theKey;
    /** Политики безопасности */
    private Collection<SecPolicy> theSecPolicies ;
    /** Комментарий */
    private String theComment ;
    /** Название */
    private String theName ;

}
