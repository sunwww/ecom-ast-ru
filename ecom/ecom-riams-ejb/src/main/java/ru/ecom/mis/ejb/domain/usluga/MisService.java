package ru.ecom.mis.ejb.domain.usluga;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Created by IntelliJ IDEA. User: vax Date: 15.11.2006 Time: 4:45:34 To change this template use File | Settings | File Templates.
 */
@Entity
@Table(schema="SQLUser")
public class MisService extends BaseEntity {


    /** Тип услуги */
    @Comment("Тип услуги")
	public VocServiceType getServiceType() { return theServiceType ; }

    public void setServiceType(VocServiceType aServiceType) { theServiceType = aServiceType ; }

    /** Тип услуги */
    private VocServiceType theServiceType ;
    
    /** Название */
	@Comment("Название")
	public String getName() {
		return theName;
	}

	public void setName(String aName) {
		theName = aName;
	}

	/** Название */
	private String theName;
	

}
