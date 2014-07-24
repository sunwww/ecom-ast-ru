package ru.ecom.mis.ejb.domain.lpu;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.jaas.ejb.domain.SecUser;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@AIndexes({
	@AIndex(properties={"remoteAddress"},table="WorkPlace")
})
@Table(schema="SQLUser")
public class UserComputer extends WorkPlace {
	/** Адрес */
	@Comment("Адрес")
	public String getRemoteAddress() {return theRemoteAddress;}
	public void setRemoteAddress(String aRemoteAddress) {theRemoteAddress = aRemoteAddress;}

	/** Пользователь */
	@Comment("Пользователь")
	@OneToOne
	public SecUser getUser() {return theUser;}
	public void setUser(SecUser aUser) {theUser = aUser;}

	/** Пользователь */
	private SecUser theUser;
	/** Адрес */
	private String theRemoteAddress;
}
