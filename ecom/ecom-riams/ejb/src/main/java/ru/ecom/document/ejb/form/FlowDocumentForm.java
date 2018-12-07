package ru.ecom.document.ejb.form;

import ru.ecom.document.ejb.domain.FlowDocument;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.TimeString;

@EntityForm
@EntityFormPersistance (clazz=FlowDocument.class)
@Comment("Документооборот")
@WebTrail(comment="Документ для отправки",  nameProperties="id", view="entityView-doc_flow.do" )
@EntityFormSecurityPrefix("/Policy/Mis/Document/Flow")
public class FlowDocumentForm extends IdEntityForm{
	/** Дата создания */
	@Comment("Дата создания")
	@DateString @DoDateString @Persist
	public String getCreateDate() {return theCreateDate;}
	public void setCreateDate(String aCreateDate) {theCreateDate = aCreateDate;}
	
	/** Дата редактирования */
	@Comment("Дата редактирования")
	@DateString @DoDateString @Persist
	public String getEditDate() {return theEditDate;}
	public void setEditDate(String aEditDate) {theEditDate = aEditDate;}
	
	/** Время создания */
	@Comment("Время создания")
	@TimeString @DoTimeString @Persist
	public String getCreateTime() {return theCreateTime;}
	public void setCreateTime(String aCreateTime) {theCreateTime = aCreateTime;}
	
	/** Время редактрования */
	@Comment("Время редактрования")
	@TimeString @DoTimeString @Persist
	public String getEditTime() {return theEditTime;}
	public void setEditTime(String aEditTime) {theEditTime = aEditTime;}
	
	/** Пользователь, который создал запись */
	@Comment("Пользователь, который создал запись")
	@Persist
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
	
	/** Пользователь, который последний редактировал запись */
	@Comment("Пользователь, который последний редактировал запись")
	@Persist
	public String getEditUsername() {return theEditUsername;}
	public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}

	/** Дата отправления */
	@Comment("Дата отправления")
	@DateString @DoDateString @Persist
	public String getPostedDate() {return thePostedDate;}
	public void setPostedDate(String aPostedDate) {thePostedDate = aPostedDate;}
	
	/** Время отправления */
	@Comment("Время отправления")
	@TimeString @DoTimeString @Persist
	public String getPostedTime() {return thePostedTime;}
	public void setPostedTime(String aPostedTime) {thePostedTime = aPostedTime;}
	
	/** Пользователь отправитель */
	@Comment("Пользователь отправитель")
	@Persist
	public String getPostedUsername() {return thePostedUsername;}
	public void setPostedUsername(String aPostedUsername) {thePostedUsername = aPostedUsername;}

	/** Пользователь отправитель */
	private String thePostedUsername;
	/** Время отправления */
	private String thePostedTime;
	/** Дата отправления */
	private String thePostedDate;
	
	/** Дата получения */
	@Comment("Дата получения")
	@DateString @DoDateString @Persist
	public String getReceiptDate() {return theReceiptDate;}
	public void setReceiptDate(String aReceiptDate) {theReceiptDate = aReceiptDate;}

	/** Время получения */
	@Comment("Время получения")
	@TimeString @DoTimeString @Persist
	public String getReceiptTime() {return theReceiptTime;}
	public void setReceiptTime(String aReceiptTime) {theReceiptTime = aReceiptTime;}

	/** Получатель (пользователь) */
	@Comment("Получатель (пользователь)")
	@Persist
	public String getReceiptUsername() {return theReceiptUsername;}
	public void setReceiptUsername(String aReceiptUsername) {theReceiptUsername = aReceiptUsername;}

	/** Отправлено из */
	@Comment("Отправлено из")
	@Persist
	public Long getPlaceFrom() {return thePlaceFrom;}
	public void setPlaceFrom(Long aPlaceFrom) {thePlaceFrom = aPlaceFrom;}

	/** Отправлено в */
	@Comment("Отправлено в")
	@Persist
	public Long getPlaceTo() {return thePlaceTo;}
	public void setPlaceTo(Long aPlaceTo) {thePlaceTo = aPlaceTo;}

	/** Отправлено в */
	private Long thePlaceTo;
	/** Отправлено из */
	private Long thePlaceFrom;
	/** Получатель (пользователь) */
	private String theReceiptUsername;
	/** Время получения */
	private String theReceiptTime;
	/** Дата получения */
	private String theReceiptDate;
	
	/** Пользователь, который последний редактировал запись */
	private String theEditUsername;
	/** Пользователь, который создал запись */
	private String theCreateUsername;
	/** Время редактрования */
	private String theEditTime;
	/** Время создания */
	private String theCreateTime;
	/** Дата редактирования */
	private String theEditDate;
	/** Дата создания */
	private String theCreateDate;
	
	/** Вид документа */
	@Comment("Вид документа")
	@Persist
	public Long getType() {return theType;}
	public void setNAME(Long aType) {theType = aType;}

	/** Вид документа */
	private Long theType;
	
	/** СМО */
	@Comment("СМО")
	@Persist
	public Long getMedCase() {return theMedCase;}
	public void setMedCase(Long aMedCase) {theMedCase = aMedCase;}

	/** Мед.карта */
	@Comment("Мед.карта")
	@Persist
	public Long getMedcard() {return theMedcard;}
	public void setMedcard(Long aMedcard) {theMedcard = aMedcard;}

	
	/** Пациент */
	@Comment("Пациент")
	@Persist
	public Long getPatient() {return thePatient;}
	public void setPatient(Long aPatient) {thePatient = aPatient;}

	
	/** Отслеживать документ */
	@Comment("Отслеживать документ")
	@Persist
	public Boolean getIsTracking() {return theIsTracking;}
	public void setIsTracking(Boolean aIsTracking) {theIsTracking = aIsTracking;}

	/** Передача отслеж. документа */
	@Comment("Передача отслеж. документа")
	@Persist
	public Long getTrackingDoc() {return theTrackingDoc;}
	public void setTrackingDoc(Long aTrackingDoc) {theTrackingDoc = aTrackingDoc;}

	/** Передача отслеж. документа */
	private Long theTrackingDoc;
	/** Отслеживать документ */
	private Boolean theIsTracking;
	/** Пациент */
	private Long thePatient;
	/** Мед.карта */
	private Long theMedcard;
	/** СМО */
	private Long theMedCase;
	
	/** Фамилия */
	@Comment("Фамилия")
	public String getLastname() {
		return theLastname;
	}

	public void setLastname(String aLastname) {
		theLastname = aLastname;
	}
	
	/** Имя */
	@Comment("Имя")
	public String getFirstname() {
		return theFirstname;
	}

	public void setFirstname(String aFirstname) {
		theFirstname = aFirstname;
	}

	
	/** Отчество */
	@Comment("Отчество")
	public String getMiddlename() {
		return theMiddlename;
	}

	public void setMiddlename(String aMiddlename) {
		theMiddlename = aMiddlename;
	}

	/** Год */
	@Comment("Год")
	public String getYear() {
		return theYear;
	}

	public void setYear(String aYear) {
		theYear = aYear;
	}

	/** Год */
	private String theYear;
	/** Отчество */
	private String theMiddlename;
	/** Имя */
	private String theFirstname;
	/** Фамилия */
	private String theLastname;
}
