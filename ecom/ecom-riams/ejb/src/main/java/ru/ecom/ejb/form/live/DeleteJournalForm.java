package ru.ecom.ejb.form.live;

import lombok.Getter;
import lombok.Setter;
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
@Setter
public class DeleteJournalForm extends IdEntityForm {
	/** Дата удаления */
	@Comment("Дата удаления")
	@DateString @DoDateString @Persist
	public String getDeleteDate() {
		return deleteDate;
	}

	/** Дата удаления */
	private String deleteDate;
	
	/** Время удаления */
	@Comment("Время удаления")
	@TimeString @DoTimeString @Persist
	public String getDeleteTime() {
		return deleteTime;
	}

	/** Время удаления */
	private String deleteTime;
	
	/** Логин удалившего */
	@Comment("Логин удалившего")
	 @Persist
	public String getLoginName() {
		return loginName;
	}

	/** Логин удалившего */
	private String loginName;
	
	/** Имя класса хранения */
	@Comment("Имя класса хранения")
	 @Persist
	public String getClassName() {
		return className;
	}

	/** Имя класса хранения */
	private String className;
	
	/** ИД объект хранения */
	@Comment("ИД объект хранения")
	 @Persist
	public String getObjectId() {
		return objectId;
	}

	/** ИД объект хранения */
	private String objectId;
	
	/** Комментарий */
	@Comment("Комментарий")
	 @Persist
	public String getComment() {
		return comment;
	}

	/** Комментарий */
	private String comment;
	
	/** Сериализация данных */
	@Comment("Сериализация данных")
	 @Persist
	public String getSerialization() {
		return serialization;
	}

	/** Сериализация данных */
	private String serialization;

}
