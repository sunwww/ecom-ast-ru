package ru.ecom.diary.ejb.domain;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.diary.ejb.domain.voc.VocDefectDiary;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@EntityListeners(DeleteListener.class)
@Table(schema="SQLUser")
@AIndexes(value = { @AIndex(properties = { "diary" }) })
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
	
	/** Пользователь */
	@Comment("Пользователь")
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}

	/** Дата создания */
	@Comment("Дата создания")
	public Date getCreateDate() {return theCreateDate;}
	public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}

	/** Отредактирован врачом */
	@Comment("Отредактирован врачом")
	public Boolean getIsDoctorCheck() {return theIsDoctorCheck;}
	public void setIsDoctorCheck(Boolean aIsDoctorCheck) {theIsDoctorCheck = aIsDoctorCheck;}

	/** Отредактирован врачом */
	private Boolean theIsDoctorCheck;
	/** Дата создания */
	private Date theCreateDate;
	/** Пользователь */
	private String theCreateUsername;
	
	/** Время создания */
	@Comment("Время создания")
	public Time getCreateTime() {return theCreateTime;}
	public void setCreateTime(Time aCreateTime) {theCreateTime = aCreateTime;}

	/** Время создания */
	private Time theCreateTime;
}
