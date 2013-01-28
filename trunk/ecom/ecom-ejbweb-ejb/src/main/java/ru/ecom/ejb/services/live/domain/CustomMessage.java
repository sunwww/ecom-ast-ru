package ru.ecom.ejb.services.live.domain;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.util.ColumnConstants;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@AIndexes({
	@AIndex(properties= {"username"})
	, @AIndex(properties= {"recipient"})
	, @AIndex(properties= {"validityDate"})
    , @AIndex(properties= {"readDate"})
})
@Table(schema="SQLUser")
public class CustomMessage {
	 /** Идентификатор */
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() { return theId ; }
    public void setId(long aId) { theId = aId ; }
    /** Идентификатор */
    private long theId ;
    /** Пользователь */
	@Comment("Пользователь")
	public String getUsername() {return theUsername;}
	public void setUsername(String aUsername) {theUsername = aUsername;}

	/** Дата получения */
	@Comment("Дата получения")
	public Date getDateReceipt() {return theDateReceipt;}
	public void setDateReceipt(Date aDateReceipt) {theDateReceipt = aDateReceipt;}

	/** Время получения */
	@Comment("Время получения")
	public Time getTimeReceipt() {return theTimeReceipt;}
	public void setTimeReceipt(Time aTimeReceipt) {theTimeReceipt = aTimeReceipt;}

	/** Дата отправки сообщения */
	@Comment("Дата отправки сообщения")
	public Date getDispatchDate() {return theDispatchDate;}
	public void setDispatchDate(Date aDispatchDate) {theDispatchDate = aDispatchDate;}

	/** Время отправки сообщения */
	@Comment("Время отправки сообщения")
	public Time getDispatchTime() {return theDispatchTime;}
	public void setDispatchTime(Time aDispatchTime) {theDispatchTime = aDispatchTime;}

	/** Текст сообщения */
	@Comment("Текст сообщения")
	@Column(length=ColumnConstants.TEXT_MAXLENGHT)
	public String getMessageText() {return theMessageText;}
	public void setMessageText(String aMessageText) {theMessageText = aMessageText;}

	/** Заголовок */
	@Comment("Заголовок")
	public String getMessageTitle() {return theMessageTitle;}
	public void setMessageTitle(String aMessageTitle) {theMessageTitle = aMessageTitle;}

	/** Получатель */
	@Comment("Получатель")
	public String getRecipient() {return theRecipient;}
	public void setRecipient(String aRecipient) {theRecipient = aRecipient;}

	/** Срок действия */
	@Comment("Срок действия")
	public Date getValidityDate() {return theValidityDate;}
	public void setValidityDate(Date aValidityDate) {theValidityDate = aValidityDate;}
	
	/** Системное сообшение */
	@Comment("Системное сообшение")
	public Boolean getIsSystem() {return theIsSystem;}
	public void setIsSystem(Boolean aIsSystem) {theIsSystem = aIsSystem;}

	/** Прочтено */
	@Comment("Прочтено")
	public Boolean getTimeRead() {return theTimeRead;}
	public void setTimeRead(Boolean aIsReads) {theTimeRead = aIsReads;}

	/** Скрыть из общего списка */
	@Comment("Скрыть из общего списка")
	public Boolean getIsHidden() {return theIsHidden;}
	public void setIsHidden(Boolean aIsHidden) {theIsHidden = aIsHidden;}

	/** Дата чтения */
	@Comment("Дата чтения")
	public Date getReadDate() {return theReadDate;}
	public void setReadDate(Date aReadDate) {theReadDate = aReadDate;}

	/** Время чтения */
	@Comment("Время чтения")
	public Time getReadTime() {return theReadTime;}
	public void setReadTime(Time aReadTime) {theReadTime = aReadTime;}

	/** Url */
	@Comment("Url")
	public String getMessageUrl() {return theMessageUrl;}
	public void setMessageUrl(String aUrl) {theMessageUrl = aUrl;}

	/** Url */
	private String theMessageUrl;
	/** Время чтения */
	private Time theReadTime;
	/** Дата чтения */
	private Date theReadDate;
	/** Скрыть из общего списка */
	private Boolean theIsHidden;
	/** Прочтено */
	private Boolean theTimeRead;
	/** Системное сообшение */
	private Boolean theIsSystem;
	/** Срок действия */
	private Date theValidityDate;
	/** Получатель */
	private String theRecipient;
	/** Заголовок */
	private String theMessageTitle;
	/** Текст сообщения */
	private String theMessageText;
	/** Время отправки сообщения */
	private Time theDispatchTime;
	/** Дата отправки сообщения */
	private Date theDispatchDate;
	/** Время получения */
	private Time theTimeReceipt;
	/** Дата получения */
	private Date theDateReceipt;
	/** Пользователь */
	private String theUsername;
}

