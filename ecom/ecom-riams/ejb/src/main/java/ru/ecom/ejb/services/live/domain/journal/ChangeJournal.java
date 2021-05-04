package ru.ecom.ejb.services.live.domain.journal;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class ChangeJournal  {
	 /** Идентификатор */
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() { return id ; }
    /** Идентификатор */
    private long id ;

	/** Дата  */
	private Date changeDate;

	/** Время  */
	private Time changeTime;
	
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
	public String getSerializationBefore() {
		return serializationBefore;
	}

	/** Сериализация данных */
	private String serializationBefore;

	/** Удалено */
	private Long status;
	
	/** Сериализация данных после */
	@Comment("Сериализация данных после")
	@Column(length=80000)
	public String getSerializationAfter() {
		return serializationAfter;
	}

	/** Сериализация данных после */
	private String serializationAfter;
}
