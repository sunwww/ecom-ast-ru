package ru.ecom.mis.ejb.form.claim;

import java.sql.Date;
import java.sql.Time;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.claim.Claim;
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
@EntityFormPersistance(clazz= Claim.class)
@Comment("Заявка в тех. поддержку")
@WebTrail(comment = "Заявка в тех. поддержку", nameProperties= "id", view="entityView-mis_claim.do" ,list = "entityList-mis_claim.do")
@EntityFormSecurityPrefix("/Policy/Mis/Claim")
public class ClaimForm extends IdEntityForm{
	
	/** Создатель */
	@Comment("Создатель")
	@Persist
	public String getUsername() {return theUsername;}
	public void setUsername(String aUsername) {theUsername = aUsername;}
	/** Создатель */
	private String theUsername;
	
	/** Рабочая функция создателя */
	@Comment("Рабочая функция создателя")
	@Persist
	public Long getWorkfunction() {return theWorkfunction;}
	public void setWorkfunction(Long aWorkfunction) {theWorkfunction = aWorkfunction;}
	/** Рабочая функция создателя */
	private Long theWorkfunction;
	
	/** Дата создания */
	@Comment("Дата создания")
	@Persist
	@DateString @DoDateString
	public String getCreateDate() {return theCreateDate;}
	public void setCreateDate(String aCreateDate) {theCreateDate = aCreateDate;}
	/** Дата создания */
	private String theCreateDate;
	
	/** Время создания */
	@Comment("Время создания")
	@Persist
	@TimeString @DoTimeString
	public String getCreateTime() {return theCreateTime;}
	public void setCreateTime(String aCreateTime) {theCreateTime = aCreateTime;}
	/** Время создания */
	private String theCreateTime;
	
	/** Текст заявки */
	@Comment("Текст заявки")
	@Persist @Required
	public String getDescription() {return theDescription;}
	public void setDescription(String aDescription) {theDescription = aDescription;}
	/** Текст заявки */
	private String theDescription;
	
	/** Дата просмотра оператором */
	@Comment("Дата просмотра оператором")
	@Persist
	@DateString @DoDateString
	public String getViewDate() {return theViewDate;}
	public void setViewDate(String aViewDate) {theViewDate = aViewDate;}
	/** Дата просмотра оператором */
	private String theViewDate;
	
	/** Время просмотра оператором */
	@Comment("Время просмотра оператором")
	@Persist
	@TimeString @DoTimeString
	public String getViewTime() {return theViewTime;}
	public void setViewTime(String aViewTime) {theViewTime = aViewTime;}
	/** Время просмотра оператором */
	private String theViewTime;
	
	/** Оператор */
	@Comment("Оператор")
	@Persist
	public String getViewUsername() {return theViewUsername;}
	public void setViewUsername(String aViewUsername) {theViewUsername = aViewUsername;}
	/** Оператор */
	private String theViewUsername;
	
	/** Дата получения в работу */
	@Comment("Дата получения в работу")
	@Persist
	@DateString @DoDateString
	public String getStartWorkDate() {return theStartWorkDate;}
	public void setStartWorkDate(String aStartWorkDate) {theStartWorkDate = aStartWorkDate;}
	/** Дата получения в работу */
	private String theStartWorkDate;
	
	/** Время получения в работу */
	@Comment("Время получения в работу")
	@Persist
	@TimeString @DoTimeString
	public String getStartWorkTime() {return theStartWorkTime;}
	public void setStartWorkTime(String aStartWorkTime) {theStartWorkTime = aStartWorkTime;}
	/** Время получения в работу */
	private String theStartWorkTime;
	
	/** Исполнитель */
	@Comment("Исполнитель")
	@Persist
	public String getStartWorkUsername() {return theStartWorkUsername;}
	public void setStartWorkUsername(String aStartWorkUsername) {theStartWorkUsername = aStartWorkUsername;}
	/** Исполнитель */
	private String theStartWorkUsername;
	
	/** Комментарий исполнителя */
	@Comment("Комментарий исполнителя")
	@Persist
	public String getExecutorComment() {return theExecutorComment;}
	public void setExecutorComment(String aExecutorComment) {theExecutorComment = aExecutorComment;}
	/** Комментарий исполнителя */
	private String theExecutorComment;
	
	/** Дата отмены */
	@Comment("Дата отмены")
	@Persist
	@DateString @DoDateString
	public String getCancelDate() {return theCancelDate;}
	public void setCancelDate(String aCancelDate) {theCancelDate = aCancelDate;}
	/** Дата отмены */
	private String theCancelDate;
	
	/** Время отмены */
	@Comment("Время отмены")
	@Persist
	@TimeString @DoTimeString
	public String getCancelTime() {return theCancelTime;}
	public void setCancelTime(String aCancelTime) {theCancelTime = aCancelTime;}
	/** Время отмены */
	private String theCancelTime;
	
	/** Пользователь, отменивший заявку */
	@Comment("Пользователь, отменивший заявку")
	@Persist
	public String getCancelUsername() {return theCancelUsername;}
	public void setCancelUsername(String aCancelUsername) {theCancelUsername = aCancelUsername;}
	/** Пользователь, отменивший заявку */
	private String theCancelUsername;

	/** Было уведомление через СМС */
	@Comment("Было уведомление через СМС")
	@Persist
	public Boolean getSendMessage() {return theSendMessage;}
	public void setSendMessage(Boolean aSendMessage) {theSendMessage = aSendMessage;}
	/** Было уведомление через СМС */
	private Boolean theSendMessage;
	
	/** Тип заявки */
	@Comment("Тип заявки")
	@Persist @Required
	public Long getClaimType() {return theClaimType;}
	public void setClaimType(Long aClaimType) {theClaimType = aClaimType;}
	/** Тип заявки */
	private Long theClaimType;
	
	/** Контактный телефон */
	@Comment("Контактный телефон")
	@Persist @Required
	public String getPhone() {return thePhone;}
	public void setPhone(String aPhone) {thePhone = aPhone;}
	/** Контактный телефон */
	private String thePhone;
	
	/** Дата исполнения */
	@Comment("Дата исполнения")
	@Persist @DateString @DoDateString
	public String getFinishDate() {return theFinishDate;}
	public void setFinishDate(String aFinishDate) {theFinishDate = aFinishDate;}
	/** Дата исполнения */
	private String theFinishDate;
	
	/** Время исполнения */
	@Comment("Время исполнения")
	@Persist @TimeString @DoTimeString
	public String getFinishTime() {return theFinishTime;}
	public void setFinishTime(String aFinishTime) {theFinishTime = aFinishTime;}
	/** Время исполнения */
	private String theFinishTime;
	
	/** Пользователь, подтвердивший исполнение */
	@Comment("Пользователь, подтвердивший исполнение")
	@Persist
	public String getFinishUsername() {return theFinishUsername;}
	public void setFinishUsername(String aFinishUsername) {theFinishUsername = aFinishUsername;}
	/** Пользователь, подтвердивший исполнение */
	private String theFinishUsername;
	
}
