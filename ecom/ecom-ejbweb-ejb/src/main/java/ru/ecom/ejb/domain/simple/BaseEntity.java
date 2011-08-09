package ru.ecom.ejb.domain.simple;

import java.io.Serializable;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import ru.ecom.ejb.services.live.LiveListener;

/**
 * Основной класс сущности.
 * Все сущности нужно наследовать от него!
 */
@SuppressWarnings("serial")
@MappedSuperclass
@EntityListeners(LiveListener.class)
public class BaseEntity implements Serializable {

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
		return (int)(theId ^ (theId >>> 32));
	}
	/* 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		final BaseEntity other = (BaseEntity) obj;
		if (theId != other.theId) return false;
		return true;
	}
    
	@Override 
	public String toString() {
		return getClass().getName()+":"+theId ;
	}

}
