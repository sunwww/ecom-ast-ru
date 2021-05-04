package ru.ecom.ejb.services.live.domain.journal;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class DeleteJournal {
	
	 /** Идентификатор */
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() { return id ; }
    public void setId(long aId) { id = aId ; }
    /** Идентификатор */
    private long id ;

	/** Дата удаления */
	private Date deleteDate;
	
	/** Время удаления */
	private Time deleteTime;

	/** Логин удалившего */
	private String loginName;

	/** Имя класса хранения */
	private String className;

	/** ИД объект хранения */
	private String objectId;
	
	/** Комментарий */
	@Comment("Комментарий")
	@Column(length=255)
	public String getComment() {
		return comment;
	}

	/** Комментарий */
	private String comment;
	
	/** Сериализация данных */
	@Comment("Сериализация данных")
	@Column(length=80000)
	public String getSerialization() {
		return serialization;
	}

	/** Сериализация данных */
	private String serialization;
	
	/** Удалено */
	@Comment("Удалено")
	public Long getStatus() {
		return status;
	}

	/** Удалено */
	private Long status;

}
