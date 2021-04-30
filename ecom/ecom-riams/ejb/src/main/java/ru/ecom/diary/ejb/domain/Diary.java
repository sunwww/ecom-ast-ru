package ru.ecom.diary.ejb.domain;


import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.ejb.services.util.ColumnConstants;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPhoneMessageState;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;


/**
 * Дневник специалиста (протокол)
 */
@Entity
@EntityListeners(DeleteListener.class)
@Table(schema="SQLUser")
@Setter
@Getter
public class Diary extends BaseEntity {

    /** Запись дневника (протокола) */
    @Comment("Запись дневника")
    @Column(length=ColumnConstants.TEXT_MAXLENGHT)
    public String getRecord() { return record ; }


	/** Пользователь последний, изменявший запись */
	private String editUsername;
	/** Дата редактирования */
	private Date editDate;
	/** Время создания */
	private Time time;
    /** Ключевые слова */
    private String keyWord ;
    /** Запись дневника (протокол) */
    private String record ;
    /** Дата создания */
    private Date date ;
	/** Тяжесть состояния */
	@Comment("Тяжесть состояния")
	@OneToOne
	public VocPhoneMessageState getState() {return state;}

	/** Тяжесть состояния */
	private VocPhoneMessageState state;
	
	/** Время редактирования */
	private Time editTime;
 
 }