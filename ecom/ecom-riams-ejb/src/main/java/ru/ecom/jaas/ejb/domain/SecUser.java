package ru.ecom.jaas.ejb.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Пользователь
 */
@Entity
@AIndexes({
    @AIndex(properties= ("login"))
    ,@AIndex(properties= ("fullname"))
    ,@AIndex(properties="disable")
})
@Table(schema="SQLUser")
public class SecUser extends BaseEntity {


    /** Наименование пользователя */
    public String getLogin() { return theLogin ; }
    public void setLogin(String aLogin) { theLogin = aLogin ; }

    /** Полное имя пользователя */
    public String getFullname() { return theFullname ; }
    public void setFullname(String aFullname) { theFullname = aFullname ; }

    /** Комментарий */
    public String getComment() { return theComment ; }
    public void setComment(String aComment) { theComment = aComment ; }

    /** Пароль */
    public String getPassword() { return thePassword ; }
    public void setPassword(String aPassword) { thePassword = aPassword ; }

    /** Отключен */
    public boolean getDisabled() { return theDisabled ; }
    public void setDisabled(boolean aDisabled) { theDisabled = aDisabled ; }

    /** Роли пользователя */
    @ManyToMany
    public List<SecRole> getRoles() { return theRoles ; }
    public void setRoles(List<SecRole> aRoles) { theRoles = aRoles ; }

    /** Отключен */
	@Comment("Отключен")
	public Boolean getDisable() {return theDisable;}
	public void setDisable(Boolean aDisable) {theDisable = aDisable;}

	/** Закеширован */
	@Comment("Закеширован")
	public Boolean getIsHash() {return theIsHash;}
	public void setIsHash(Boolean aIsHash) {theIsHash = aIsHash;}

	/** Закеширован */
	private Boolean theIsHash;
	/** Отключен */
	private Boolean theDisable;
/** Роли пользователя */
    private List<SecRole> theRoles ;
    /** Отключен */
    private boolean theDisabled ;
    /** Комментарий */
    private String theComment ;
    /** Полное имя пользователя */
    private String theFullname ;
    /** Наименование пользователя */
    private String theLogin ;
    /** Пароль */
    private String thePassword ;

}
