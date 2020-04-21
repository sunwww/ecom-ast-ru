package ru.ecom.ejb.form.message;

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
public class CustomMessageForm extends IdEntityForm{
    /** Пользователь */
	@Comment("Пользователь")
	@Persist
	public String getUsername() {return theUsername;}
	public void setUsername(String aUsername) {theUsername = aUsername;}

	/** Дата получения */
	@Comment("Дата получения")
	@DateString @DoDateString @Persist
	public String getDateReceipt() {return theDateReceipt;}
	public void setDateReceipt(String aDateReceipt) {theDateReceipt = aDateReceipt;}

	/** Время получения */
	@Comment("Время получения")
	@TimeString @DoTimeString @Persist
	public String getTimeReceipt() {return theTimeReceipt;}
	public void setTimeReceipt(String aTimeReceipt) {theTimeReceipt = aTimeReceipt;}

	/** Дата отправки сообщения */
	@Comment("Дата отправки сообщения")
	@DateString @DoDateString @Persist
	public String getDispatchDate() {return theDispatchDate;}
	public void setDispatchDate(String aDispatchDate) {theDispatchDate = aDispatchDate;}

	/** Время отправки сообщения */
	@Comment("Время отправки сообщения")
	@TimeString @DoTimeString @Persist
	public String getDispatchTime() {return theDispatchTime;}
	public void setDispatchTime(String aDispatchTime) {theDispatchTime = aDispatchTime;}

	/** Текст сообщения */
	@Comment("Текст сообщения")
	@Persist @Required
	public String getMessageText() {return theMessageText;}
	public void setMessageText(String aMessageText) {theMessageText = aMessageText;}

	/** Заголовок */
	@Comment("Заголовок")
	@Persist @Required
	public String getMessageTitle() {return theMessageTitle;}
	public void setMessageTitle(String aMessageTitle) {theMessageTitle = aMessageTitle;}

	/** Получатель */
	@Comment("Получатель")
	@Persist
	public String getRecipient() {return theRecipient;}
	public void setRecipient(String aRecipient) {theRecipient = aRecipient;}

	/** Срок действия */
	@Comment("Срок действия")
	@DateString @DoDateString @Persist
	public String getValidityDate() {return theValidityDate;}
	public void setValidityDate(String aValidityDate) {theValidityDate = aValidityDate;}
	
	/** Системное сообшение */
	@Comment("Системное сообшение")
	@Persist
	public Boolean getIsSystem() {return theIsSystem;}
	public void setIsSystem(Boolean aIsSystem) {theIsSystem = aIsSystem;}

	/** Всем пользователям */
	@Comment("Всем пользователям")
	public Boolean getIsAllUsers() {return theIsAllUsers;}
	public void setIsAllUsers(Boolean aIsAllUsers) {theIsAllUsers = aIsAllUsers;}

	/** Роль */
	@Comment("Роль")
	public Long getSecRole() {return theSecRole;}
	public void setSecRole(Long aSecRole) {theSecRole = aSecRole;}

	/** Пользователь */
	@Comment("Пользователь")
	public Long getSecUser() {return theSecUser;}
	public void setSecUser(Long aSecUser) {theSecUser = aSecUser;}

	/** Url */
	@Comment("Url")
	@Persist
	public String getMessageUrl() {return theMessageUrl;}
	public void setMessageUrl(String aUrl) {theMessageUrl = aUrl;}

	/** Url */
	private String theMessageUrl;
	/** Пользователь */
	private Long theSecUser;
	/** Роль */
	private Long theSecRole;
	/** Всем пользователям */
	private Boolean theIsAllUsers;
	/** Системное сообшение */
	private Boolean theIsSystem;
	/** Срок действия */
	private String theValidityDate;
	/** Получатель */
	private String theRecipient;
	/** Заголовок */
	private String theMessageTitle;
	/** Текст сообщения */
	private String theMessageText;
	/** Время отправки сообщения */
	private String theDispatchTime;
	/** Дата отправки сообщения */
	private String theDispatchDate;
	/** Время получения */
	private String theTimeReceipt;
	/** Дата получения */
	private String theDateReceipt;
	/** Пользователь */
	private String theUsername;
	/** Эксренное */
	@Comment("Эксренное")
	@Persist
	public Boolean getIsEmergency() {return theIsEmergency;}
	public void setIsEmergency(Boolean aIsEmergency) {theIsEmergency = aIsEmergency;}

	/** Эксренное */
	private Boolean theIsEmergency;
	
	/** Время действия */
	@Comment("Время действия")
	@Persist @TimeString @DoTimeString
	public String getValidityTime() {return theValidityTime;}
	public void setValidityTime(String aValidityTime) {theValidityTime = aValidityTime;}

	/** Время действия */
	private String theValidityTime;

	/** Список отделений */
	@Comment("Список отделений")
	public String getLpus() {return theLpus;}
	public void setLpus(String aLpus) {theLpus = aLpus;}
	/** Список отделений */
	private String theLpus;
}