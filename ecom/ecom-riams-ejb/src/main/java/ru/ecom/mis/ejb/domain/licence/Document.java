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
    
    /** Дата создания */
	@Comment("Дата создания")
	public Date getCreateDate() {return theCreateDate;}
	public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}
	
	/** Пользователь, создавший запись */
	@Comment("Пользователь, создавший запись")
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
	
	/** Дата редактирования */
	@Comment("Дата редактирования")
	public Date getEditDate() {return theEditDate;}
	public void setEditDate(Date aEditDate) {theEditDate = aEditDate;}
	
	/** Пользователь, последний редактировавший запись */
	@Comment("Пользователь, последний редактировавший запись")
	public String getEditUsername() {return theEditUsername;}
	public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}

	/** Пользователь, последний редактировавший запись */
	private String theEditUsername;
	/** Дата редактирования */
	private Date theEditDate;
	/** Пользователь, создавший запись */
	private String theCreateUsername;
	/** Дата создания */
	private Date theCreateDate;

}