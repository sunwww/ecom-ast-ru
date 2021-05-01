package ru.ecom.mis.ejb.domain.disability;

import lombok.Getter;
import lombok.Setter;
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
	@AIndex(properties= {"disabilityDocument"})
})
@Table(schema="SQLUser")
@Getter
@Setter
public class RegimeViolationRecord extends BaseEntity {
	
	/** Документ нетрудоспособности */
	@Comment("Документ нетрудоспособности")
	@ManyToOne
	public DisabilityDocument getDisabilityDocument() {return disabilityDocument;}

	/** Тип нарушения */
	@Comment("Тип нарушения")
	@OneToOne
	public VocRegimeViolationType getRegimeViolationType() {return regimeViolationType;}


	@Transient
	public String getRegimeViolationTypeInfo() {
		return regimeViolationType!=null?regimeViolationType.getName():"" ;
	}
	
	/** Информация о нарушении режима */
	@Comment("Информация о нарушении режима")
	@Transient
	public String getInfo() {
		return getRegimeViolationTypeInfo() + " " +
				DurationUtil.getDuration(getDateFrom(), getDateTo());
	}

	/** Документ нетрудоспособности */
	private DisabilityDocument disabilityDocument;
	/** Дата начала нарушения */
	private Date dateFrom;
	/** Дата окончания нарушения */
	private Date dateTo;
	/** Тип нарушения */
	private VocRegimeViolationType regimeViolationType;
	/** Комментарии */
	private String comment;

}
