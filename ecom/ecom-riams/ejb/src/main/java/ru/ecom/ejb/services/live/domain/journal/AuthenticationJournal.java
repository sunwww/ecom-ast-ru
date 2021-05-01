package ru.ecom.ejb.services.live.domain.journal;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Date;
import java.sql.Time;

@Entity
@AIndexes({
    @AIndex(properties= {"username"})
})
@Table(schema="SQLUser")
@Getter
@Setter
public class AuthenticationJournal extends BaseEntity {

	/** Server name */
	private String serverName;
	/** Local Add */
	private String localAdd;
	/** Remote Add */
	private String remoteAdd;
	/** Прошел проверку */
	private Boolean isChecked;
	/** Время аутентификации */
	private Time authTime;
	/** Дата аутентификации */
	private Date authDate;
	/** Пользователь */
	private String username;
	
	/** Описание ошибки */
	@Comment("Описание ошибки")
	@Column(length=400)
	public String getErrorMessage() {return errorMessage;}

	/** Ошибочный пароль */
	@Comment("Ошибочный пароль")
	public String getErrorPassword() {return errorPassword;}

	/** Ошибочный пароль */
	private String errorPassword;
	/** Описание ошибки */
	private String errorMessage;
}
