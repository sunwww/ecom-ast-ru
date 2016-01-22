package ru.ecom.mis.ejb.domain.claim;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;


@Comment("Заявка в техническую поддержку")
@Entity
@Table(schema="SQLUser")
public class Claim extends BaseEntity{

	/** Создатель */
	@Comment("Создатель")
	public String getUsername() {return theUsername;}
	public void setUsername(String aUsername) {theUsername = aUsername;}
	/** Создатель */
	private String theUsername;
	
	/** Рабочая функция создателя */
	@Comment("Рабочая функция создателя")
	public Long getWorkfunction() {return theWorkfunction;}
	public void setWorkfunction(Long aWorkfunction) {theWorkfunction = aWorkfunction;}
	/** Рабочая функция создателя */
	private Long theWorkfunction;
	
	/** Дата создания */
	@Comment("Дата создания")
	public Date getCreateDate() {return theCreateDate;}
	public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}
	/** Дата создания */
	private Date theCreateDate;
	
	/** Время создания */
	@Comment("Время создания")
	public Time getCreateTime() {return theCreateTime;}
	public void setCreateTime(Time aCreateTime) {theCreateTime = aCreateTime;}
	/** Время создания */
	private Time theCreateTime;
	
	/** Текст заявки */
	@Comment("Текст заявки")
	public String getDescription() {return theDescription;}
	public void setDescription(String aDescription) {theDescription = aDescription;}
	/** Текст заявки */
	private String theDescription;
	
	/** Дата просмотра оператором */
	@Comment("Дата просмотра оператором")
	public Date getViewDate() {return theViewDate;}
	public void setViewDate(Date aViewDate) {theViewDate = aViewDate;}
	/** Дата просмотра оператором */
	private Date theViewDate;
	
	/** Время просмотра оператором */
	@Comment("Время просмотра оператором")
	public Time getViewTime() {return theViewTime;}
	public void setViewTime(Time aViewTime) {theViewTime = aViewTime;}
	/** Время просмотра оператором */
	private Time theViewTime;
	
	/** Оператор */
	@Comment("Оператор")
	public String getOperatorUsername() {return theOperatorUsername;}
	public void setOperatorUsername(String aOperatorUsername) {theOperatorUsername = aOperatorUsername;}
	/** Оператор */
	private String theOperatorUsername;
	
	/** Дата получения в работу */
	@Comment("Дата получения в работу")
	public Date getStartWorkDate() {return theStartWorkDate;}
	public void setStartWorkDate(Date aStartWorkDate) {theStartWorkDate = aStartWorkDate;}
	/** Дата получения в работу */
	private Date theStartWorkDate;
	
	/** Время получения в работу */
	@Comment("Время получения в работу")
	public Time getStartWorkTime() {return theStartWorkTime;}
	public void setStartWorkTime(Time aStartWorkTime) {theStartWorkTime = aStartWorkTime;}
	/** Время получения в работу */
	private Time theStartWorkTime;
	
	/** Исполнитель */
	@Comment("Исполнитель")
	public String getExecutorUsername() {return theExecutorUsername;}
	public void setExecutorUsername(String aExecutorUsername) {theExecutorUsername = aExecutorUsername;}
	/** Исполнитель */
	private String theExecutorUsername;
	
	/** Комментарий исполнителя */
	@Comment("Комментарий исполнителя")
	public String getExecutorComment() {return theExecutorComment;}
	public void setExecutorComment(String aExecutorComment) {theExecutorComment = aExecutorComment;}
	/** Комментарий исполнителя */
	private String theExecutorComment;
	
	/** Дата отмены */
	@Comment("Дата отмены")
	public Date getCancelDate() {return theCancelDate;}
	public void setCancelDate(Date aCancelDate) {theCancelDate = aCancelDate;}
	/** Дата отмены */
	private Date theCancelDate;
	
	/** Время отмены */
	@Comment("Время отмены")
	public Time getCancelTime() {return theCancelTime;}
	public void setCancelTime(Time aCancelTime) {theCancelTime = aCancelTime;}
	/** Время отмены */
	private Time theCancelTime;
	
	/** Пользователь, отменивший заявку */
	@Comment("Пользователь, отменивший заявку")
	public String getCancelUsername() {return theCancelUsername;}
	public void setCancelUsername(String aCancelUsername) {theCancelUsername = aCancelUsername;}
	/** Пользователь, отменивший заявку */
	private String theCancelUsername;

	/** Было уведомление через СМС */
	@Comment("Было уведомление через СМС")
	public Boolean getSendMessage() {return theSendMessage;}
	public void setSendMessage(Boolean aSendMessage) {theSendMessage = aSendMessage;}
	/** Было уведомление через СМС */
	private Boolean theSendMessage;
	
	/** Тип заявки */
	@Comment("Тип заявки")
	public Long getClaimType() {return theClaimType;}
	public void setClaimType(Long aClaimType) {theClaimType = aClaimType;}
	/** Тип заявки */
	private Long theClaimType;
	
	/** Контактный телефон */
	@Comment("Контактный телефон")
	public String getPhone() {return thePhone;}
	public void setPhone(String aPhone) {thePhone = aPhone;}
	/** Контактный телефон */
	private String thePhone;
	
	/** Дата исполнения */
	@Comment("Дата исполнения")
	public Date getFinishDate() {return theFinishDate;}
	public void setFinishDate(Date aFinishDate) {theFinishDate = aFinishDate;}
	/** Дата исполнения */
	private Date theFinishDate;
	
	/** Время исполнения */
	@Comment("Время исполнения")
	public Time getFinishTime() {return theFinishTime;}
	public void setFinishTime(Time aFinishTime) {theFinishTime = aFinishTime;}
	/** Время исполнения */
	private Time theFinishTime;
	
	/** Пользователь, подтвердивший исполнение */
	@Comment("Пользователь, подтвердивший исполнение")
	public String getFinishUserame() {return theFinishUserame;}
	public void setFinishUserame(String aFinishUserame) {theFinishUserame = aFinishUserame;}
	/** Пользователь, подтвердивший исполнение */
	private String theFinishUserame;
}
