package ru.ecom.ejb.services.live.domain.journal;

import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

/**
 * Журнал удалений
 * @author azviagin
 *
 */

@Comment("Журнал удалений")
@Entity
@Table(schema="SQLUser")
@AIndexes(value = { 
		@AIndex(properties = { "objectId","className","loginName" })
		,@AIndex(properties = { "objectId","className" })
		,@AIndex(properties = { "className" })
	}
)
public class DeleteJournal {
	
	 /** Идентификатор */
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() { return theId ; }
    public void setId(long aId) { theId = aId ; }
    /** Идентификатор */
    private long theId ;
	/** Дата удаления */
	@Comment("Дата удаления")
	public Date getDeleteDate() {
		return theDeleteDate;
	}

	public void setDeleteDate(Date aDeleteDate) {
		theDeleteDate = aDeleteDate;
	}

	/** Дата удаления */
	private Date theDeleteDate;
	
	/** Время удаления */
	@Comment("Время удаления")
	public Time getDeleteTime() {
		return theDeleteTime;
	}

	public void setDeleteTime(Time aDeleteTime) {
		theDeleteTime = aDeleteTime;
	}

	/** Время удаления */
	private Time theDeleteTime;
	
	/** Логин удалившего */
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
	public String getSerialization() {
		return theSerialization;
	}

	public void setSerialization(String aSerialization) {
		theSerialization = aSerialization;
	}

	/** Сериализация данных */
	private String theSerialization;
	
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

}
