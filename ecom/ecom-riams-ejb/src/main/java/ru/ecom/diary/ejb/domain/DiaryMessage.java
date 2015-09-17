package ru.ecom.diary.ejb.domain;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.diary.ejb.domain.voc.VocDefectDiary;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@EntityListeners(DeleteListener.class)
@Table(schema="SQLUser")
public class DiaryMessage extends BaseEntity {
	/** Дневник */
	@Comment("Дневник")
	@OneToOne
	public Diary getDiary() {return theDiary;}
	public void setDiary(Diary aDiary) {theDiary = aDiary;}

	/** Комментарий */
	@Comment("Комментарий")
	public String getComment() {return theComment;}
	public void setComment(String aComment) {theComment = aComment;}

	/** Справочник дефектов */
	@Comment("Справочник дефектов")
	@OneToOne
	public VocDefectDiary getDefect() {
		return theDefect;
	}

	public void setDefect(VocDefectDiary aDefect) {
		theDefect = aDefect;
	}

	/** Справочник дефектов */
	private VocDefectDiary theDefect;
	/** Комментарий */
	private String theComment;
	/** Дневник */
	private Diary theDiary;
}
