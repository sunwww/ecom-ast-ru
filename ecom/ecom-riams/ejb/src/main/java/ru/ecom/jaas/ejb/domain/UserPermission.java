package ru.ecom.jaas.ejb.domain;

import ru.ecom.ejb.services.entityform.annotation.UnDeletable;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@AIndexes({
	//@AIndex(properties= {"object","permission","dateFrom","dateTo","username"},table="Permission")
	@AIndex(properties= {"dateFrom","dateTo","username"},table="Permission")
	,@AIndex(properties= {"dateFrom","dateTo","username","object","permission"},table="Permission")
    //,@AIndex(properties= {"dateFrom","dateTo","username","object","permission","idObject"},table="Permission")
})
@Table(schema="SQLUser")
@UnDeletable
public class UserPermission extends Permission {
	/** Пользователь */
	@Comment("Пользователь")
	@OneToOne
	public SecUser getUsername() {return theUsername;}
	public void setUsername(SecUser aUsername) {theUsername = aUsername;}

	/** Пользователь инфо */
	@Comment("Пользователь инфо")
	@Transient
	public String getUserInfo() {
		return theUsername!=null?theUsername.getLogin():"";
	}	/** Пользователь */
	private SecUser theUsername;

}
