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
		return VTE;
	}

	public void setVTE(Timestamp aVTE) {
		VTE = aVTE;
	}

	/** Время окончания актуальности */
	private Timestamp VTE;
	
	/** Время начала актуальности */
	@Comment("Время начала актуальности")
	public Timestamp getVTS() {
		return VTS;
	}

	public void setVTS(Timestamp aVTS) {
		VTS = aVTS;
	}

	/** Время начала актуальности */
	private Timestamp VTS;

    /** Идентификатор */
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() { return id ; }
    public void setId(long aId) { id = aId ; }
	
    
    /** Идентификатор */
    private long id ;


	/* 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return (int) (id % Integer.MAX_VALUE) ;
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
		return id == other.id;
	}
    
	@Override 
	public String toString() {
		return getClass().getName()+":"+id ;
	}
}
