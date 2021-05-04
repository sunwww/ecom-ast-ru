package ru.ecom.ejb.services.live.domain.journal;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class ViewJournal extends BaseEntity {

	/** Комментарий */
	private String comment;

	/** Уровень */
	private Integer levelWebTrail;
	/** Время просмотра */
	private Time viewTime;
	/** Дата просмотра */
	private Date viewDate;

	/** Класс объекта */
	private String classObject;

	/** Id объекта */
	private Long idObject;

	/** Пользователь */
	private String username;
}
