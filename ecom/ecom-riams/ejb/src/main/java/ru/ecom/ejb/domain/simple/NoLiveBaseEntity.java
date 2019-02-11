package ru.ecom.ejb.domain.simple;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Основной класс сущности.
 * Все сущности без поддержки механизма Live нужно наследовать от него!
 */
@SuppressWarnings("serial")
@MappedSuperclass
public class NoLiveBaseEntity implements Serializable {
	
	/** Время окончания актуальности */
	@Comment("Время окончания актуальности")
	public Timestamp getVTE() {
		return theVTE;
	}

	public void setVTE(Timestamp aVTE) {
		theVTE = aVTE;
	}

	/** Время окончания актуальности */
	private Timestamp theVTE;
	
	/** Время начала актуальности */
	@Comment("Время начала актуальности")
	public Timestamp getVTS() {
		return theVTS;
	}

	public void setVTS(Timestamp aVTS) {
		theVTS = aVTS;
	}

	/** Время начала актуальности */
	private Timestamp theVTS;

    /** Идентификатор */
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() { return theId ; }
    public void setId(long aId) { theId = aId ; }
	
    
    /** Идентификатор */
    private long theId ;


	/* 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return (int) (theId % Integer.MAX_VALUE) ;
	}
	/* 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		final NoLiveBaseEntity other = (NoLiveBaseEntity) obj;
		if (theId != other.theId) return false;
		return true;
	}
    
	@Override 
	public String toString() {
		return getClass().getName()+":"+theId ;
	}
}
