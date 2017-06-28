package ru.ecom.document.ejb.domain;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.document.ejb.domain.voc.VocFlowDocmentPlace;
import ru.ecom.document.ejb.domain.voc.VocFlowDocumentType;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(schema="SQLUser")
public class FlowDocument extends BaseEntity {
	/** Дата создания */
	@Comment("Дата создания")
	public Date getCreateDate() {return theCreateDate;}
	public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}
	
	/** Дата редактирования */
	@Comment("Дата редактирования")
	public Date getEditDate() {return theEditDate;}
	public void setEditDate(Date aEditDate) {theEditDate = aEditDate;}
	
	/** Время создания */
	@Comment("Время создания")
	public Time getCreateTime() {return theCreateTime;}
	public void setCreateTime(Time aCreateTime) {theCreateTime = aCreateTime;}
	/** Время редактрования */
	@Comment("Время редактрования")
	public Time getEditTime() {return theEditTime;}
	public void setEditTime(Time aEditTime) {theEditTime = aEditTime;}
	/** Пользователь, который создал запись */
	@Comment("Пользователь, который создал запись")
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
	/** Пользователь, который последний редактировал запись */
	@Comment("Пользователь, который последний редактировал запись")
	public String getEditUsername() {return theEditUsername;}
	public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}

	/** Дата отправления */
	@Comment("Дата отправления")
	public Date getPostedDate() {return thePostedDate;}
	public void setPostedDate(Date aPostedDate) {thePostedDate = aPostedDate;}
	
	/** Время отправления */
	@Comment("Время отправления")
	public Time getPostedTime() {return thePostedTime;}
	public void setPostedTime(Time aPostedTime) {thePostedTime = aPostedTime;}
	
	/** Пользователь отправитель */
	@Comment("Пользователь отправитель")
	public String getPostedUsername() {return thePostedUsername;}
	public void setPostedUsername(String aPostedUsername) {thePostedUsername = aPostedUsername;}

	/** Пользователь отправитель */
	private String thePostedUsername;
	/** Время отправления */
	private Time thePostedTime;
	/** Дата отправления */
	private Date thePostedDate;
	
	/** Дата получения */
	@Comment("Дата получения")
	public Date getReceiptDate() {return theReceiptDate;}
	public void setReceiptDate(Date aReceiptDate) {theReceiptDate = aReceiptDate;}

	/** Время получения */
	@Comment("Время получения")
	public Time getReceiptTime() {return theReceiptTime;}
	public void setReceiptTime(Time aReceiptTime) {theReceiptTime = aReceiptTime;}

	/** Получатель (пользователь) */
	@Comment("Получатель (пользователь)")
	public String getReceiptUsername() {return theReceiptUsername;}
	public void setReceiptUsername(String aReceiptUsername) {theReceiptUsername = aReceiptUsername;}

	/** Отправлено из */
	@Comment("Отправлено из")
	@OneToOne
	public VocFlowDocmentPlace getPlaceFrom() {return thePlaceFrom;}
	public void setPlaceFrom(VocFlowDocmentPlace aPlaceFrom) {thePlaceFrom = aPlaceFrom;}

	/** Отправлено в */
	@Comment("Отправлено в")
	@OneToOne
	public VocFlowDocmentPlace getPlaceTo() {return thePlaceTo;}
	public void setPlaceTo(VocFlowDocmentPlace aPlaceTo) {thePlaceTo = aPlaceTo;}

	/** Отправлено в */
	private VocFlowDocmentPlace thePlaceTo;
	/** Отправлено из */
	private VocFlowDocmentPlace thePlaceFrom;
	/** Получатель (пользователь) */
	private String theReceiptUsername;
	/** Время получения */
	private Time theReceiptTime;
	/** Дата получения */
	private Date theReceiptDate;
	
	/** Пользователь, который последний редактировал запись */
	private String theEditUsername;
	/** Пользователь, который создал запись */
	private String theCreateUsername;
	/** Время редактрования */
	private Time theEditTime;
	/** Время создания */
	private Time theCreateTime;
	/** Дата редактирования */
	private Date theEditDate;
	/** Дата создания */
	private Date theCreateDate;
	
	/** Вид документа */
	@Comment("Вид документа")
	@OneToOne
	public VocFlowDocumentType getType() {return theType;}
	public void setType(VocFlowDocumentType aType) {theType = aType;}

	/** Вид документа */
	private VocFlowDocumentType theType;
	
	/** СМО */
	@Comment("СМО")
	public Long getMedCase() {return theMedCase;}
	public void setMedCase(Long aMedCase) {theMedCase = aMedCase;}

	/** Мед.карта */
	@Comment("Мед.карта")
	public Long getMedcard() {return theMedcard;}
	public void setMedcard(Long aMedcard) {theMedcard = aMedcard;}

	
	/** Пациент */
	@Comment("Пациент")
	public Long getPatient() {return thePatient;}
	public void setPatient(Long aPatient) {thePatient = aPatient;}
	
	/** Отслеживать документ */
	@Comment("Отслеживать документ")
	public Boolean getIsTracking() {return theIsTracking;}
	public void setIsTracking(Boolean aIsTracking) {theIsTracking = aIsTracking;}

	/** Передача отслеж. документа */
	@Comment("Передача отслеж. документа")
	@OneToOne
	public FlowDocument getTrackingDoc() {return theTrackingDoc;}
	public void setTrackingDoc(FlowDocument aTrackingDoc) {theTrackingDoc = aTrackingDoc;}

	/** Передача отслеж. документа */
	private FlowDocument theTrackingDoc;
	/** Отслеживать документ */
	private Boolean theIsTracking;
	/** Пациент */
	private Long thePatient;
	/** Мед.карта */
	private Long theMedcard;
	/** СМО */
	private Long theMedCase;
}
