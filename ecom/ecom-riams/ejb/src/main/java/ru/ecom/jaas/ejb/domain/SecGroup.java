package ru.ecom.jaas.ejb.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(schema="SQLUser")
public class SecGroup extends BaseEntity {
	/** Название группы */
	@Comment("Название группы")
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}

	/** Список пользователей */
	@Comment("Список пользователей")
	@ManyToMany
	public List<SecUser> getSecUsers() {return theSecUsers;}
	public void setSecUsers(List<SecUser> aUsers) {theSecUsers = aUsers;}
	
	/** Комментарий */
	@Comment("Комментарий")
	public String getComment() {return theComment;}
	public void setComment(String aComment) {theComment = aComment;}

	/** Комментарий */
	private String theComment;
	/** Список пользователей */
	private List<SecUser> theSecUsers;
	/** Название группы */
	private String theName;
}
