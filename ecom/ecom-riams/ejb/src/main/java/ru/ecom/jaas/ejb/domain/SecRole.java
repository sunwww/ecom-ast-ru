package ru.ecom.jaas.ejb.domain;

import static javax.persistence.CascadeType.ALL;

import java.sql.Date;
import java.sql.Time;
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

	/** Системный? */
	@Comment("Системный?")
	public Boolean getIsSystems() {return theIsSystems;}
	public void setIsSystems(Boolean aIsSystems) {theIsSystems = aIsSystems;}

	/** Дата создания */
	@Comment("Дата создания")
	public Date getCreateDate() {return theCreateDate;}
	public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}
	
	/** Дата редактирования */
	@Comment("Дата редактирования")
	public Date getEditDate() {return theEditDate;}
	public void setEditDate(Date aEditDate) {theEditDate = aEditDate;}
	
	/** Время создания */
	@Comment("Время создания")
	public Time getCreateTime() {return theCreateTime;}
	public void setCreateTime(Time aCreateTime) {theCreateTime = aCreateTime;}
	/** Время редактрования */
	@Comment("Время редактрования")
	public Time getEditTime() {return theEditTime;}
	public void setEditTime(Time aEditTime) {theEditTime = aEditTime;}
	/** Пользователь, который создал запись */
	@Comment("Пользователь, который создал запись")
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
	/** Пользователь, который последний редактировал запись */
	@Comment("Пользователь, который последний редактировал запись")
	public String getEditUsername() {return theEditUsername;}
	public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}

	/** Пользователь, который последний редактировал запись */
	private String theEditUsername;
	/** Пользователь, который создал запись */
	private String theCreateUsername;
	/** Время редактрования */
	private Time theEditTime;
	/** Время создания */
	private Time theCreateTime;
	/** Дата редактирования */
	private Date theEditDate;
	/** Дата создания */
	private Date theCreateDate;
	/** Системный? */
	private Boolean theIsSystems;
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
