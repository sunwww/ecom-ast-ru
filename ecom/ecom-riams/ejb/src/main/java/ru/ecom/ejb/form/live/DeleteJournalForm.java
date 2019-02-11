package ru.ecom.ejb.form.live;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.live.domain.journal.DeleteJournal;
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
@EntityFormPersistance(clazz = DeleteJournal.class)
@Comment("Журнал удаленных случаев")
@WebTrail(comment = "Журнал удаленных случаев", nameProperties = "className", view = "entityView-ecom_deleteJournal.do")
@EntityFormSecurityPrefix("/Policy/IdeMode/Hello")
public class DeleteJournalForm extends IdEntityForm {
	/** Дата удаления */
	@Comment("Дата удаления")
	@DateString @DoDateString @Persist
	public String getDeleteDate() {
		return theDeleteDate;
	}

	public void setDeleteDate(String aDeleteDate) {
		theDeleteDate = aDeleteDate;
	}

	/** Дата удаления */
	private String theDeleteDate;
	
	/** Время удаления */
	@Comment("Время удаления")
	@TimeString @DoTimeString @Persist
	public String getDeleteTime() {
		return theDeleteTime;
	}

	public void setDeleteTime(String aDeleteTime) {
		theDeleteTime = aDeleteTime;
	}

	/** Время удаления */
	private String theDeleteTime;
	
	/** Логин удалившего */
	@Comment("Логин удалившего")
	 @Persist
	public String getLoginName() {
		return theLoginName;
	}

	public void setLoginName(String aLoginName) {
		theLoginName = aLoginName;
	}

	/** Логин удалившего */
	private String theLoginName;
	
	/** Имя класса хранения */
	@Comment("Имя класса хранения")
	 @Persist
	public String getClassName() {
		return theClassName;
	}

	public void setClassName(String aClassName) {
		theClassName = aClassName;
	}

	/** Имя класса хранения */
	private String theClassName;
	
	/** ИД объект хранения */
	@Comment("ИД объект хранения")
	 @Persist
	public String getObjectId() {
		return theObjectId;
	}

	public void setObjectId(String aObjectId) {
		theObjectId = aObjectId;
	}

	/** ИД объект хранения */
	private String theObjectId;
	
	/** Комментарий */
	@Comment("Комментарий")
	 @Persist
	public String getComment() {
		return theComment;
	}

	public void setComment(String aComment) {
		theComment = aComment;
	}

	/** Комментарий */
	private String theComment;
	
	/** Сериализация данных */
	@Comment("Сериализация данных")
	 @Persist
	public String getSerialization() {
		return theSerialization;
	}

	public void setSerialization(String aSerialization) {
		theSerialization = aSerialization;
	}

	/** Сериализация данных */
	private String theSerialization;

}
