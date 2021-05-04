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
import ru.ecom.diary.ejb.domain.voc.VocDefectDiary;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.ejb.services.util.ColumnConstants;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@EntityListeners(DeleteListener.class)
@Table(schema="SQLUser")
@AIndexes(value = { @AIndex(properties = { "diary" }) })
@Setter
@Getter
public class DiaryMessage extends BaseEntity {
	/** Дневник */
	@Comment("Дневник")
	@OneToOne
	public Diary getDiary() {return diary;}


	/** Справочник дефектов */
	@Comment("Справочник дефектов")
	@OneToOne
	public VocDefectDiary getDefect() {
		return defect;
	}

    /** Запись дневника (протокола) */
    @Comment("Запись дневника")
    @Column(length=ColumnConstants.TEXT_MAXLENGHT)
    public String getRecord() { return record ; }

	/** Справочник дефектов */
	private VocDefectDiary defect;
	/** Комментарий */
	private String comment;
	private String record;
	/** Дневник */
	private Diary diary;
	

	/** Отредактирован врачом */
	private Boolean isDoctorCheck;
	/** Дата создания */
	private Date createDate;
	/** Пользователь */
	private String createUsername;
	
	/** Время создания */
	private Time createTime;
	
	/** Время действия */
	private Time validityTime;
	
	/** Срок действия */
	private Date validityDate;
	
	/** Врачебная комиссия */
	private Boolean isVk;
}
