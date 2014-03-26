package ru.ecom.diary.ejb.domain.protocol.template;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.jaas.ejb.domain.SecGroup;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Created by IntelliJ IDEA.
 * User: STkacheva
 * Date: 21.12.2006
 * Time: 10:55:14
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(schema="SQLUser")
public class TemplateWord extends BaseEntity {

    /** Сокращение */
    @Column(unique = true)
    public String getReduction() { return theReduction ; }
    public void setReduction(String aReduction) { theReduction = aReduction ; }

    /** Расшифровка */
    public String getDecryption() { return theDecryption ; }
    public void setDecryption(String aDecryption) { theDecryption = aDecryption ; }

    /** Расшифровка */
    private String theDecryption ;
    /** Сокращение */
    private String theReduction ;
    
    /** Пользователь */
    @Comment("Пользователь")
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}

	/** Дата создания */
	@Comment("Дата создания")
	public Date getCreateCreateDate() {return theCreateDate;}
	public void setCreateCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}
	
	/** Дата редактирования */
	@Comment("Дата редактирования")
	public Date getEditDate() {return theEditDate;}
	public void setEditDate(Date aEditDate) {theEditDate = aEditDate;}

	/** Группы пользователей */
	@Comment("Группы пользователей")
	@ManyToMany
	public List<SecGroup> getSecGroups() {return theSecGroups;}
	public void setSecGroups(List<SecGroup> aSecGroups) {theSecGroups = aSecGroups;}

	/** Группы пользователей */
	private List<SecGroup> theSecGroups;
	/** Дата редактирования */
	private Date theEditDate;
	/** Дата создания */
	private Date theCreateDate;
	/** Пользователь */
	private String theCreateUsername;
}
