package ru.ecom.mis.ejb.domain.lpu;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class UserComputer extends WorkPlace {

	/** Пользователь */
	@Comment("Пользователь")
	@OneToOne
	public SecUser getUser() {return user;}

	/** Динамический адрес */
	private String dynamicIp;
	/** Com порт */
	private String comPort;
	/** Пользователь */
	private SecUser user;
	/** Адрес */
	private String remoteAddress;
}
