package ru.ecom.mis.ejb.domain.identification.voc;
import javax.persistence.Entity;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
 /**
  * Кодирующая система
  */
 @Comment("Кодирующая система")
@Entity
@Table(schema="SQLUser")
public class VocEncodingSystem extends VocBaseEntity{
	 /** Название таблицы */
	@Comment("Название таблицы")
	public String getTableName() {return theTableName;}
	public void setTableName(String aTableName) {theTableName = aTableName;}
	
	/** Название ключевого атрибута */
	@Comment("Название ключевого атрибута")
	public String getCodeName() {return theCodeName;}
	public void setCodeName(String aCodeName) {theCodeName = aCodeName;}
	
	/** Атрибут комментария */
	@Comment("Атрибут комментария")
	public String getCodeComment() {return theCodeComment;}
	public void setCodeComment(String aCodeComment) {theCodeComment = aCodeComment;}
	
	/** Data Source Name */
	@Comment("Data Source Name")
	public String getDsn() {return theDsn;}
	public void setDsn(String aDns) {theDsn = aDns;}

	/** Data Source Name */
	private String theDsn;
	/** Атрибут комментария */
	private String theCodeComment;
	/** Название ключевого атрибута */
	private String theCodeName;
	/** Название таблицы */
	private String theTableName;
}
