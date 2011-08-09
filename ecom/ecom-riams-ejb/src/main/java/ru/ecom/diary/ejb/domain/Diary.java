package ru.ecom.diary.ejb.domain;


import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.ejb.services.util.ColumnConstants;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;


/**
 * Дневник специалиста (протокол)
 */
@Entity
@EntityListeners(DeleteListener.class)
@Table(schema="SQLUser")
public class Diary extends BaseEntity {
    /** Дата создания */
	@Comment("Дата создания")
    public Date getDate() { return theDate ; }
    public void setDate(Date aDate) { theDate = aDate ; }

    /** Запись дневника (протокола) */
    @Comment("Запись дневника")
    @Column(length=ColumnConstants.TEXT_MAXLENGHT)
    public String getRecord() { return theRecord ; }
    public void setRecord(String aRecord) { theRecord = aRecord ; }
    
    /** Ключевые слова */
    @Comment("Ключевые слова")
    public String getKeyWord() { return theKeyWord ; }
    public void setKeyWord(String aKeyWord) { theKeyWord = aKeyWord ; }

    /** Время создания */
	@Comment("Время создания")
	public Time getTime() {return theTime;}
	public void setTime(Time aTime) {theTime = aTime;}

	/** Время создания */
	private Time theTime;
    /** Ключевые слова */
    private String theKeyWord ;
    /** Запись дневника (протокол) */
    private String theRecord ;
    /** Дата создания */
    private Date theDate ;
 
 }