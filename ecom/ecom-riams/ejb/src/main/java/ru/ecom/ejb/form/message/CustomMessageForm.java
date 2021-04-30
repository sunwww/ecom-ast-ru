package ru.ecom.ejb.form.message;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.live.domain.CustomMessage;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

@EntityForm
@EntityFormPersistance(clazz= CustomMessage.class)
@Comment("Сообщения")
@EntityFormSecurityPrefix("/Policy/Mis/CustomMessage")
@WebTrail(comment="Сообщения", nameProperties="messageTitle", view="entityView-mis_customMessage.do")
@Setter
public class CustomMessageForm extends IdEntityForm{
    /** Пользователь */
	@Comment("Пользователь")
	@Persist
	public String getUsername() {return username;}

	/** Дата получения */
	@Comment("Дата получения")
	@DateString @DoDateString @Persist
	public String getDateReceipt() {return dateReceipt;}

	/** Время получения */
	@Comment("Время получения")
	@TimeString @DoTimeString @Persist
	public String getTimeReceipt() {return timeReceipt;}

	/** Дата отправки сообщения */
	@Comment("Дата отправки сообщения")
	@DateString @DoDateString @Persist
	public String getDispatchDate() {return dispatchDate;}

	/** Время отправки сообщения */
	@Comment("Время отправки сообщения")
	@TimeString @DoTimeString @Persist
	public String getDispatchTime() {return dispatchTime;}

	/** Текст сообщения */
	@Comment("Текст сообщения")
	@Persist @Required
	public String getMessageText() {return messageText;}

	/** Заголовок */
	@Comment("Заголовок")
	@Persist @Required
	public String getMessageTitle() {return messageTitle;}

	/** Получатель */
	@Comment("Получатель")
	@Persist
	public String getRecipient() {return recipient;}

	/** Срок действия */
	@Comment("Срок действия")
	@DateString @DoDateString @Persist
	public String getValidityDate() {return validityDate;}

	/** Системное сообшение */
	@Comment("Системное сообшение")
	@Persist
	public Boolean getIsSystem() {return isSystem;}

	/** Всем пользователям */
	@Comment("Всем пользователям")
	public Boolean getIsAllUsers() {return isAllUsers;}

	/** Роль */
	@Comment("Роль")
	public Long getSecRole() {return secRole;}

	/** Пользователь */
	@Comment("Пользователь")
	public Long getSecUser() {return secUser;}

	/** Url */
	@Comment("Url")
	@Persist
	public String getMessageUrl() {return messageUrl;}

	/** Url */
	private String messageUrl;
	/** Пользователь */
	private Long secUser;
	/** Роль */
	private Long secRole;
	/** Всем пользователям */
	private Boolean isAllUsers;
	/** Системное сообшение */
	private Boolean isSystem;
	/** Срок действия */
	private String validityDate;
	/** Получатель */
	private String recipient;
	/** Заголовок */
	private String messageTitle;
	/** Текст сообщения */
	private String messageText;
	/** Время отправки сообщения */
	private String dispatchTime;
	/** Дата отправки сообщения */
	private String dispatchDate;
	/** Время получения */
	private String timeReceipt;
	/** Дата получения */
	private String dateReceipt;
	/** Пользователь */
	private String username;
	/** Эксренное */
	@Comment("Эксренное")
	@Persist
	public Boolean getIsEmergency() {return isEmergency;}

	/** Эксренное */
	private Boolean isEmergency;
	
	/** Время действия */
	@Comment("Время действия")
	@Persist @TimeString @DoTimeString
	public String getValidityTime() {return validityTime;}

	/** Время действия */
	private String validityTime;

	/** Список отделений */
	@Comment("Список отделений")
	public String getLpus() {return lpus;}
	/** Список отделений */
	private String lpus;
}