package ru.ecom.ejb.services.live.domain;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.util.ColumnConstants;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;

@Entity
@AIndexes({
    @AIndex(name = "property", properties = {"property"})
})
@Table(schema="SQLUser")
@Getter
@Setter
public class LiveProperty {
	 /** Идентификатор */
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() { return id ; }

    /** Сущность */
	@Comment("Сущность")
	@ManyToOne
	public LiveEntity getEntity() {
		return entity;
	}

	/** Сущность */
	private LiveEntity entity;

	/** Значение */
	@Comment("Значение")
	@Column(length=ColumnConstants.TEXT_MAXLENGHT)
	public String getPropertyValue() {
		return propertyValue;
	}

	/** Значение */
	private String propertyValue;
	/** Название свойство */
	private String property;
    /** Идентификатор */
    private long id ;
    
    /** Поиск значения */
	@Comment("Поиск значения")
	public String getShortValue() {
		int len = propertyValue!=null ? propertyValue.length() : 0;
		if (len>255) len=255 ;
		return shortValue!=null ? shortValue : propertyValue!=null ? propertyValue.substring(0, len) : null;
	}

	public void setShortValue(String aShortValue) {
		if (propertyValue!=null) {
		shortValue = getShortValue();
		}
	}

	/** Поиск значения */
	private String shortValue;
    
}
