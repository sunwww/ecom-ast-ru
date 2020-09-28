package ru.ecom.ejb.services.live.domain;

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
public class LiveProperty {
	 /** Идентификатор */
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() { return theId ; }
    public void setId(long aId) { theId = aId ; }
    
    /** Сущность */
	@Comment("Сущность")
	@ManyToOne
	public LiveEntity getEntity() {
		return theEntity;
	}

	public void setEntity(LiveEntity aEntity) {
		theEntity = aEntity;
	}

	/** Сущность */
	private LiveEntity theEntity;
    /** Название свойство */
	@Comment("Название свойство")
	public String getProperty() {
		return theProperty;
	}

	public void setProperty(String aProperty) {
		theProperty = aProperty;
	}

	/** Значение */
	@Comment("Значение")
	@Column(length=ColumnConstants.TEXT_MAXLENGHT)
	public String getPropertyValue() {
		return thePropertyValue;
	}

	public void setPropertyValue(String aPropertyValue) {
		thePropertyValue = aPropertyValue;
	}

	/** Значение */
	private String thePropertyValue;
	/** Название свойство */
	private String theProperty;
    /** Идентификатор */
    private long theId ;
    
    /** Поиск значения */
	@Comment("Поиск значения")
	public String getShortValue() {
		int len = thePropertyValue!=null ? thePropertyValue.length() : 0;
		if (len>255) len=255 ;
		return theShortValue!=null ? theShortValue : thePropertyValue!=null ? thePropertyValue.substring(0, len) : null;
	}

	public void setShortValue(String aShortValue) {
		if (thePropertyValue!=null) {
		theShortValue = getShortValue();
		}
	}

	/** Поиск значения */
	private String theShortValue;
    
}
