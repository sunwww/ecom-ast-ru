package ru.ecom.diary.ejb.form;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import ru.ecom.diary.ejb.domain.Diary;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.util.ColumnConstants;
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

public class DiaryForm extends IdEntityForm {
   
	/** Дата создания */
	@Comment("Дата создания")
	@Persist @DateString @DoDateString
    public String getDate() { return theDate ; }
    public void setDate(String aDate) { theDate = aDate ; }

    /** Запись дневника (протокола) */
    @Comment("Запись дневника")
    @Persist
    public String getRecord() { return theRecord ; }
    public void setRecord(String aRecord) { theRecord = aRecord ; }
    
    /** Ключевые слова */
    @Comment("Ключевые слова")
    @Persist
    public String getKeyWord() { return theKeyWord ; }
    public void setKeyWord(String aKeyWord) { theKeyWord = aKeyWord ; }

    /** Время создания */
	@Comment("Время создания")
	@Persist @TimeString @DoTimeString
	public String getTime() {return theTime;}
	public void setTime(String aTime) {theTime = aTime;}
	/** Дата редактирования */
	@Comment("Дата редактирования")
	@Persist @DoDateString @DateString
	public String getEditDate() {return theEditDate;}
	public void setEditDate(String aEditDate) {theEditDate = aEditDate;}

	/** Пользователь последний, изменявший запись */
	@Comment("Пользователь последний, изменявший запись")
	@Persist
	public String getEditUsername() {
		return theEditUsername;
	}

	public void setEditUsername(String aEditUsername) {
		theEditUsername = aEditUsername;
	}

	/** Пользователь последний, изменявший запись */
	private String theEditUsername;
	/** Дата редактирования */
	private String theEditDate;
	/** Время создания */
	private String theTime;
    /** Ключевые слова */
    private String theKeyWord ;
    /** Запись дневника (протокол) */
    private String theRecord ;
    /** Дата создания */
    private String theDate ;
	/** Тяжесть состояния */
	@Comment("Тяжесть состояния")
	@Persist 
	public Long getState() {return theState;}
	public void setState(Long aState) {theState = aState;}

	/** Тяжесть состояния */
	private Long theState;


}
