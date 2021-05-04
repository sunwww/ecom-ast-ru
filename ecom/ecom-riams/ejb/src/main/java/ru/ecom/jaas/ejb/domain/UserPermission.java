package ru.ecom.jaas.ejb.domain;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.services.entityform.annotation.UnDeletable;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
@AIndexes({
	//@AIndex(properties= {"object","permission","dateFrom","dateTo","username"},table="Permission")
	@AIndex(properties= {"dateFrom","dateTo","username"},table="Permission")
	,@AIndex(properties= {"dateFrom","dateTo","username","object","permission"},table="Permission")
    //,@AIndex(properties= {"dateFrom","dateTo","username","object","permission","idObject"},table="Permission")
})
@UnDeletable
@Setter
@Getter
public class UserPermission extends Permission {
	/** Пользователь */
	@Comment("Пользователь")
	@OneToOne
	public SecUser getUsername() {return username;}
	private SecUser username;

	/** Пользователь инфо */
	@Comment("Пользователь инфо")
	@Transient
	public String getUserInfo() {
		return username!=null?username.getLogin():"";
	}
}
