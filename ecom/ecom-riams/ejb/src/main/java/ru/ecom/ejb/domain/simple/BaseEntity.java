package ru.ecom.ejb.domain.simple;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.services.live.LiveListener;

import javax.persistence.*;
import java.io.Serializable;

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
    public long getId() { return id ; }
    public void setId(long aId) { id = aId ; }
	
    
    /** Идентификатор */
    private long id ;


	/* 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return (int)(id ^ (id >>> 32));
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
		return id == other.id;
	}
    
	@Override 
	public String toString() {
		return getClass().getName()+":"+id ;
	}

}
