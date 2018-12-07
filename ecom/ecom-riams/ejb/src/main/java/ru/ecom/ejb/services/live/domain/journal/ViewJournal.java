package ru.ecom.ejb.services.live.domain.journal;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Date;
import java.sql.Time;

@Entity
@AIndexes({
    @AIndex(unique = false, properties= {"classObject","username"})
    , @AIndex(unique = false, properties= {"idObject"})
})
@Table(schema="SQLUser")
public class ViewJournal extends BaseEntity {
	/** Пользователь */
	@Comment("Пользователь")
	public String getUsername() {
		return theUsername;
	}

	public void setUsername(String aUsername) {
		theUsername = aUsername;
	}
	
	/** Id объекта */
	@Comment("Id объекта")
	public Long getIdObject() {
		return theIdObject;
	}

	public void setIdObject(Long aNAME) {
		theIdObject = aNAME;
	}
	
	/** Класс объекта */
	@Comment("Класс объекта")
	public String getClassObject() {
		return theClassObject;
	}

	public void setClassObject(String aClassObject) {
		theClassObject = aClassObject;
	}
	
	/** Дата просмотра */
	@Comment("Дата просмотра")
	public Date getViewDate() {
		return theViewDate;
	}

	public void setViewDate(Date aViewDate) {
		theViewDate = aViewDate;
	}
	
	/** Время просмотра */
	@Comment("Время просмотра")
	public Time getViewTime() {
		return theViewTime;
	}

	public void setViewTime(Time aViewTime) {
		theViewTime = aViewTime;
	}
	
	/** Уровень */
	@Comment("Уровень")
	public Integer getLevelWebTrail() {
		return theLevelWebTrail;
	}

	public void setLevelWebTrail(Integer aLevelWebTrail) {
		theLevelWebTrail = aLevelWebTrail;
	}
	
	/** Комментарий */
	@Comment("Комментарий")
	public String getComment() {
		return theComment;
	}

	public void setComment(String aComment) {
		theComment = aComment;
	}

	/** Комментарий */
	private String theComment;

	/** Уровень */
	private Integer theLevelWebTrail;
	/** Время просмотра */
	private Time theViewTime;
	/** Дата просмотра */
	private Date theViewDate;

	/** Класс объекта */
	private String theClassObject;

	/** Id объекта */
	private Long theIdObject;

	/** Пользователь */
	private String theUsername;
}
