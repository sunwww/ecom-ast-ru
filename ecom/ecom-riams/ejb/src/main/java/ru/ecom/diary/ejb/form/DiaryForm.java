package ru.ecom.diary.ejb.form;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.diary.ejb.domain.Diary;
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

/**
 * Протоколы
 */
@EntityForm
@EntityFormPersistance(clazz= Diary.class)
@Comment("Протоколы")
@WebTrail(comment = "Протокол", nameProperties= "record", view="entityView-diary_protocol.do")
@EntityFormSecurityPrefix("/Policy/Diary/Diary")
@Setter
public class DiaryForm extends IdEntityForm {
   
	/** Дата создания */
	@Comment("Дата создания")
	@Persist @DateString @DoDateString
    public String getDate() { return date ; }

    /** Запись дневника (протокола) */
    @Comment("Запись дневника")
    @Persist
    public String getRecord() { return record ; }

    /** Ключевые слова */
    @Comment("Ключевые слова")
    @Persist
    public String getKeyWord() { return keyWord ; }

    /** Время создания */
	@Comment("Время создания")
	@Persist @TimeString @DoTimeString
	public String getTime() {return time;}
	/** Дата редактирования */
	@Comment("Дата редактирования")
	@Persist @DoDateString @DateString
	public String getEditDate() {return editDate;}

	/** Пользователь последний, изменявший запись */
	@Comment("Пользователь последний, изменявший запись")
	@Persist
	public String getEditUsername() {
		return editUsername;
	}

	/** Пользователь последний, изменявший запись */
	private String editUsername;
	/** Дата редактирования */
	private String editDate;
	/** Время создания */
	private String time;
    /** Ключевые слова */
    private String keyWord ;
    /** Запись дневника (протокол) */
    private String record ;
    /** Дата создания */
    private String date ;
	/** Тяжесть состояния */
	@Comment("Тяжесть состояния")
	@Persist 
	public Long getState() {return state;}

	/** Тяжесть состояния */
	private Long state;


}
