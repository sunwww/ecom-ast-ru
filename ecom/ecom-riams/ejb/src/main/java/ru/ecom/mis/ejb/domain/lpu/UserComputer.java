package ru.ecom.mis.ejb.domain.lpu;

import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.jaas.ejb.domain.SecUser;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@AIndexes({
	@AIndex(properties={"remoteAddress"},table="WorkPlace"),
	@AIndex(properties={"user"},table="WorkPlace")
})
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

	/** Com порт */
	@Comment("Com порт")
	public String getComPort() {return theComPort;}
	public void setComPort(String aComPort) {theComPort = aComPort;}

	/** Динамический адрес */
	@Comment("Динамический адрес")
	public String getDynamicIp() {return theDynamicIp;}
	public void setDynamicIp(String aDynamicIp) {theDynamicIp = aDynamicIp;}

	/** Динамический адрес */
	private String theDynamicIp;
	/** Com порт */
	private String theComPort;
	/** Пользователь */
	private SecUser theUser;
	/** Адрес */
	private String theRemoteAddress;
}
