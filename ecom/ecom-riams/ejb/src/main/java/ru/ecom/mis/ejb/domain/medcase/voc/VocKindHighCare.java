package ru.ecom.mis.ejb.domain.medcase.voc;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Виды ВМП
 */
@Entity
@Table(schema="SQLUser")
@Getter
@Setter
public class VocKindHighCare extends VocBaseEntity {

	/** Код потока обслуживания */
	private String serviceStreamCode;
	
	/** Дата начала */
	private Date dateFrom;
	/** Дата окончания */
	private Date dateTo;
	
	/** Необходимо указывать количество стентов */
	private Boolean isStentRequired ;

	/** Цена */
	private BigDecimal cost ;
}
