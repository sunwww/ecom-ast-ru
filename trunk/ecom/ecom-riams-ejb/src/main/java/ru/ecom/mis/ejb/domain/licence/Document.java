package ru.ecom.mis.ejb.domain.licence;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Документ
 */

@Entity
@Comment("Документ")
@Table(schema="SQLUser")
public class Document extends BaseEntity {

    /** Серия документа */
    public String getSeriaDoc() { return theSeriaDoc ; }
    public void setSeriaDoc(String aSeriaDoc) { theSeriaDoc = aSeriaDoc ; }

    /** Номер документа */
    public String getNumberDoc() { return theNumberDoc ; }
    public void setNumberDoc(String aNumberDoc) { theNumberDoc = aNumberDoc ; }
    
    /** Дата выдачи */
	@Comment("Дата выдачи")
	public Date getDateIssued() {return theDateIssued;}
	public void setDateIssued(Date aDateIssued) {theDateIssued = aDateIssued;}

	/** Кем выдан */
	@Comment("Кем выдан")
	public String getWhomIssued() {return theWhomIssued;}
	public void setWhomIssued(String aWhomIssued) {theWhomIssued = aWhomIssued;}

	/** Кем выдан */
	private String theWhomIssued;
	/** Дата выдачи */
	private Date theDateIssued;
  
    /** Серия документа */
    private String theSeriaDoc ;
    /** Номер документа */
    private String theNumberDoc ;

}