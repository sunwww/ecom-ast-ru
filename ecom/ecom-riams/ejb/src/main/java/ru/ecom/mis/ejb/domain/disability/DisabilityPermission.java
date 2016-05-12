package ru.ecom.mis.ejb.domain.disability;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.util.DurationUtil;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Разрешение по документу нетрудоспособности
 * @author azviagin,stkacheva
 *
 */
@Comment("Разрешение по документу нетрудоспособности")
@Entity
@AIndexes({
	@AIndex(unique = false, properties= {"disabilityDocument"})
	,@AIndex(unique = false, properties= {"dateFrom"})
    ,@AIndex(unique = false, properties= {"dateTo"})
})
@Table(schema="SQLUser")
public class DisabilityPermission extends BaseEntity {
	
	/** Комментарии */
	@Comment("Комментарии")
	public String getComment() {return theComment;}
	public void setComment(String aComment) {theComment = aComment;}

	
	/** Разрешено */
	@Comment("Разрешено")
	public Boolean getPermission() {return thePermission;}
	public void setPermission(Boolean aPermission) {thePermission = aPermission;}


	/** Дата начала разрешения */
	@Comment("Дата начала разрешения")
	public Date getDateFrom() {return theDateFrom;}
	public void setDateFrom(Date aDateFrom) {theDateFrom = aDateFrom;}
	
	/** Дата окончания разрешения */
	@Comment("Дата окончания разрешения")
	public Date getDateTo() {return theDateTo;}
	public void setDateTo(Date aDateTo) {theDateTo = aDateTo;}

	/** Документ нетрудоспособности */
	@Comment("Документ нетрудоспособности")
	public DisabilityDocument getDisabilityDocument() {return theDisabilityDocument;}
	public void setDisabilityDocument(DisabilityDocument aDisabilityDocument) {theDisabilityDocument = aDisabilityDocument;}

	@Comment("Информация о разрешении")
	@Transient
	public String getInfo() {
		
		StringBuilder ret = new StringBuilder() ;
		if (thePermission!=null && thePermission==true) {
			ret.append("РАЗРЕШЕНО ") ;
		}  else {
			ret.append("ЗАПРЕЩЕНО ") ;
		}
		ret.append(DurationUtil.getDuration(getDateFrom(), getDateTo())) ;
		return ret.toString() ;
	}
	/** Комментарии */
	private String theComment;
	/** Разрешено */
	private Boolean thePermission;
	/** Дата начала разрешения */
	private Date theDateFrom;
	/** Дата окончания разрешения */
	private Date theDateTo;
	/** Документ нетрудоспособности */
	private DisabilityDocument theDisabilityDocument;
}
