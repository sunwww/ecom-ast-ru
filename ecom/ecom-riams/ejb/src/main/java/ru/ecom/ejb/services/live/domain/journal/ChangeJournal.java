package ru.ecom.ejb.services.live.domain.journal;

import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Comment("Журнал изменений")
@Entity
@Table(schema="SQLUser")
@AIndexes(value = { 
		@AIndex(properties = { "objectId","className","loginName" })
		,@AIndex(properties = { "objectId","className" })
		,@AIndex(properties = { "className" })
	}
)
public class ChangeJournal  {
	 /** Идентификатор */
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() { return theId ; }
    public void setId(long aId) { theId = aId ; }
    /** Идентификатор */
    private long theId ;
	/** Дата  */
	@Comment("Дата удаления")
	public Date getChangeDate() {
		return theChangeDate;
	}

	public void setChangeDate(Date aDeleteDate) {
		theChangeDate = aDeleteDate;
	}

	/** Дата  */
	private Date theChangeDate;
	
	/** Время  */
	@Comment("Время удаления")
	public Time getChangeTime() {
		return theChangeTime;
	}

	public void setChangeTime(Time aDeleteTime) {
		theChangeTime = aDeleteTime;
	}

	/** Время  */
	private Time theChangeTime;
	
	/** Логин  */
	@Comment("Логин удалившего")
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
	@Column(length=255)
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
	@Column(length=80000)
	public String getSerializationBefore() {
		return theSerializationBefore;
	}

	public void setSerializationBefore(String aSerialization) {
		theSerializationBefore = aSerialization;
	}

	/** Сериализация данных */
	private String theSerializationBefore;
	
	/** Удалено */
	@Comment("Удалено")
	public Long getStatus() {
		return theStatus;
	}

	public void setStatus(Long aStatus) {
		theStatus = aStatus;
	}

	/** Удалено */
	private Long theStatus;
	
	/** Сериализация данных после */
	@Comment("Сериализация данных после")
	@Column(length=80000)
	public String getSerializationAfter() {
		return theSerializationAfter;
	}

	public void setSerializationAfter(String aSerializationAfter) {
		theSerializationAfter = aSerializationAfter;
	}

	/** Сериализация данных после */
	private String theSerializationAfter;
}
