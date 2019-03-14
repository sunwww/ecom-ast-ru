package ru.ecom.mis.ejb.domain.disability;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.util.DurationUtil;
import ru.ecom.mis.ejb.domain.disability.voc.VocRegimeViolationType;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;
/**
 * Запись о нарушении режима
 * @author azviagin,stkacheva
 *
 */
@Entity
@AIndexes({
	@AIndex(unique = false, properties= {"disabilityDocument"})
})
@Table(schema="SQLUser")
public class RegimeViolationRecord extends BaseEntity {
	
	/** Документ нетрудоспособности */
	@Comment("Документ нетрудоспособности")
	@ManyToOne
	public DisabilityDocument getDisabilityDocument() {return theDisabilityDocument;}
	public void setDisabilityDocument(DisabilityDocument aDisabilityDocument) {theDisabilityDocument = aDisabilityDocument;}
	
	/** Дата начала нарушения */
	@Comment("Дата начала нарушения")
	public Date getDateFrom() {return theDateFrom;}
	public void setDateFrom(Date aDateFrom) {theDateFrom = aDateFrom;}

	
	/** Дата окончания нарушения */
	@Comment("Дата окончания нарушения")
	public Date getDateTo() {return theDateTo;}
	public void setDateTo(Date aDateTo) {theDateTo = aDateTo;}

	/** Тип нарушения */
	@Comment("Тип нарушения")
	@OneToOne
	public VocRegimeViolationType getRegimeViolationType() {return theRegimeViolationType;}
	public void setRegimeViolationType(VocRegimeViolationType aRegimeViolationType) {theRegimeViolationType = aRegimeViolationType;}
	
	/** Комментарии */
	@Comment("Комментарии")
	public String getComment() {return theComment;}
	public void setComment(String aComment) {theComment = aComment;}
	
	@Transient
	public String getRegimeViolationTypeInfo() {
		return theRegimeViolationType!=null?theRegimeViolationType.getName():"" ;
	}
	
	/** Информация о нарушении режима */
	@Comment("Информация о нарушении режима")
	@Transient
	public String getInfo() {
		String ret = getRegimeViolationTypeInfo() + " " +
				DurationUtil.getDuration(getDateFrom(), getDateTo());
		return ret;
	}

	/** Документ нетрудоспособности */
	private DisabilityDocument theDisabilityDocument;
	/** Дата начала нарушения */
	private Date theDateFrom;
	/** Дата окончания нарушения */
	private Date theDateTo;
	/** Тип нарушения */
	private VocRegimeViolationType theRegimeViolationType;
	/** Комментарии */
	private String theComment;

}
